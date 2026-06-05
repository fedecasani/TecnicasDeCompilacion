package com.compilador;

import java.util.ArrayList;
import java.util.List;

public final class GeneradorCodigo extends MiLenguajeBaseVisitor<String> {
    private final List<String> codigo = new ArrayList<>();
    private int temporal;
    private int etiqueta;
    private String etiquetaBreak;
    private String etiquetaContinue;
    private boolean enGlobal;

    public List<String> generar(MiLenguajeParser.ProgramaContext programa) {
        codigo.clear();
        temporal = 0;
        etiqueta = 0;
        emitir("// Codigo de tres direcciones generado");
        emitir("PROGRAMA_INICIO:");
        enGlobal = true;
        for (MiLenguajeParser.ElementoContext elemento : programa.elemento()) {
            if (elemento.declaracion() != null) visit(elemento.declaracion());
        }
        enGlobal = false;
        for (MiLenguajeParser.ElementoContext elemento : programa.elemento()) {
            if (elemento.funcion() != null) visit(elemento.funcion());
        }
        emitir("PROGRAMA_FIN:");
        return new ArrayList<>(codigo);
    }

    @Override
    public String visitFuncion(MiLenguajeParser.FuncionContext ctx) {
        emitir("func_" + ctx.ID().getText() + ":");
        if (ctx.parametros() != null) {
            for (MiLenguajeParser.ParametroContext parametro : ctx.parametros().parametro()) {
                emitir("PARAM " + parametro.ID().getText() + " " + parametro.tipo().getText());
            }
        }
        visit(ctx.bloque());
        return null;
    }

    @Override
    public String visitDeclaracion(MiLenguajeParser.DeclaracionContext ctx) {
        String nombre = ctx.ID().getText();
        if (ctx.INTEGER() != null) nombre += "[" + ctx.INTEGER().getText() + "]";
        emitir("DECLARE " + nombre + " " + ctx.tipo().getText());
        if (ctx.expresion() != null) {
            emitir(ctx.ID().getText() + " = " + visit(ctx.expresion()));
        } else if (enGlobal && "bool".equals(ctx.tipo().getText())) {
            emitir(ctx.ID().getText() + " = false");
        }
        return null;
    }

    @Override
    public String visitAsignacion(MiLenguajeParser.AsignacionContext ctx) {
        emitir(textoAcceso(ctx.acceso()) + " = " + visit(ctx.expresion()));
        return null;
    }

    @Override
    public String visitAsignacionFor(MiLenguajeParser.AsignacionForContext ctx) {
        emitir(textoAcceso(ctx.acceso()) + " = " + visit(ctx.expresion()));
        return null;
    }

    @Override
    public String visitSentenciaReturn(MiLenguajeParser.SentenciaReturnContext ctx) {
        emitir(ctx.expresion() == null ? "return" : "return " + visit(ctx.expresion()));
        return null;
    }

    @Override
    public String visitSentenciaCout(MiLenguajeParser.SentenciaCoutContext ctx) {
        for (MiLenguajeParser.ExpresionContext expresion : ctx.expresion()) {
            emitir("PRINT " + visit(expresion));
        }
        return null;
    }

    @Override
    public String visitLlamada(MiLenguajeParser.LlamadaContext ctx) {
        generarLlamada(ctx);
        return null;
    }

    @Override
    public String visitSentenciaIf(MiLenguajeParser.SentenciaIfContext ctx) {
        String entonces = nuevaEtiqueta("THEN");
        String sino = ctx.bloque().size() > 1 ? nuevaEtiqueta("ELSE") : null;
        String fin = nuevaEtiqueta("END_IF");
        String condicion = visit(ctx.expresion());
        emitir("if " + condicion + " goto " + entonces);
        emitir("goto " + (sino == null ? fin : sino));
        emitir(entonces + ":");
        visit(ctx.bloque(0));
        if (sino != null) {
            emitir("goto " + fin);
            emitir(sino + ":");
            visit(ctx.bloque(1));
        }
        emitir(fin + ":");
        return null;
    }

    @Override
    public String visitSentenciaWhile(MiLenguajeParser.SentenciaWhileContext ctx) {
        String inicio = nuevaEtiqueta("WHILE");
        String cuerpo = nuevaEtiqueta("WHILE_BODY");
        String fin = nuevaEtiqueta("END_WHILE");
        String breakAnterior = etiquetaBreak;
        String continueAnterior = etiquetaContinue;
        etiquetaBreak = fin;
        etiquetaContinue = inicio;
        emitir(inicio + ":");
        String condicion = visit(ctx.expresion());
        emitir("if " + condicion + " goto " + cuerpo);
        emitir("goto " + fin);
        emitir(cuerpo + ":");
        visit(ctx.bloque());
        emitir("goto " + inicio);
        emitir(fin + ":");
        etiquetaBreak = breakAnterior;
        etiquetaContinue = continueAnterior;
        return null;
    }

    @Override
    public String visitSentenciaFor(MiLenguajeParser.SentenciaForContext ctx) {
        if (ctx.declaracion() != null) visit(ctx.declaracion());
        else if (ctx.asignacion() != null) visit(ctx.asignacion());
        String inicio = nuevaEtiqueta("FOR");
        String cuerpo = nuevaEtiqueta("FOR_BODY");
        String paso = nuevaEtiqueta("FOR_STEP");
        String fin = nuevaEtiqueta("END_FOR");
        String breakAnterior = etiquetaBreak;
        String continueAnterior = etiquetaContinue;
        etiquetaBreak = fin;
        etiquetaContinue = paso;
        emitir(inicio + ":");
        if (ctx.expresion() != null) {
            emitir("if " + visit(ctx.expresion()) + " goto " + cuerpo);
            emitir("goto " + fin);
        } else {
            emitir("goto " + cuerpo);
        }
        emitir(cuerpo + ":");
        visit(ctx.bloque());
        emitir(paso + ":");
        if (ctx.asignacionFor() != null) visit(ctx.asignacionFor());
        emitir("goto " + inicio);
        emitir(fin + ":");
        etiquetaBreak = breakAnterior;
        etiquetaContinue = continueAnterior;
        return null;
    }

    @Override
    public String visitTerminal(org.antlr.v4.runtime.tree.TerminalNode node) {
        if (node.getSymbol().getType() == MiLenguajeParser.BREAK && etiquetaBreak != null) {
            emitir("goto " + etiquetaBreak);
        } else if (node.getSymbol().getType() == MiLenguajeParser.CONTINUE && etiquetaContinue != null) {
            emitir("goto " + etiquetaContinue);
        }
        return null;
    }

    @Override public String visitExprEntero(MiLenguajeParser.ExprEnteroContext ctx) { return ctx.getText(); }
    @Override public String visitExprDecimal(MiLenguajeParser.ExprDecimalContext ctx) { return ctx.getText(); }
    @Override public String visitExprCaracter(MiLenguajeParser.ExprCaracterContext ctx) { return ctx.getText(); }
    @Override public String visitExprCadena(MiLenguajeParser.ExprCadenaContext ctx) { return ctx.getText(); }
    @Override public String visitExprVerdadero(MiLenguajeParser.ExprVerdaderoContext ctx) { return "true"; }
    @Override public String visitExprFalso(MiLenguajeParser.ExprFalsoContext ctx) { return "false"; }
    @Override public String visitExprAcceso(MiLenguajeParser.ExprAccesoContext ctx) { return textoAcceso(ctx.acceso()); }
    @Override public String visitExprAgrupada(MiLenguajeParser.ExprAgrupadaContext ctx) { return visit(ctx.expresion()); }
    @Override public String visitExprNegativo(MiLenguajeParser.ExprNegativoContext ctx) { return unaria("-", visit(ctx.expresion())); }
    @Override public String visitExprNot(MiLenguajeParser.ExprNotContext ctx) { return unaria("!", visit(ctx.expresion())); }
    @Override public String visitExprAditiva(MiLenguajeParser.ExprAditivaContext ctx) { return binaria(ctx.expresion(0), ctx.getChild(1).getText(), ctx.expresion(1)); }
    @Override public String visitExprMultiplicativa(MiLenguajeParser.ExprMultiplicativaContext ctx) { return binaria(ctx.expresion(0), ctx.getChild(1).getText(), ctx.expresion(1)); }
    @Override public String visitExprRelacional(MiLenguajeParser.ExprRelacionalContext ctx) { return binaria(ctx.expresion(0), ctx.getChild(1).getText(), ctx.expresion(1)); }
    @Override public String visitExprIgualdad(MiLenguajeParser.ExprIgualdadContext ctx) { return binaria(ctx.expresion(0), ctx.getChild(1).getText(), ctx.expresion(1)); }
    @Override public String visitExprAnd(MiLenguajeParser.ExprAndContext ctx) { return binaria(ctx.expresion(0), "&&", ctx.expresion(1)); }
    @Override public String visitExprOr(MiLenguajeParser.ExprOrContext ctx) { return binaria(ctx.expresion(0), "||", ctx.expresion(1)); }
    @Override public String visitExprLlamada(MiLenguajeParser.ExprLlamadaContext ctx) { return generarLlamada(ctx.llamada()); }

    private String generarLlamada(MiLenguajeParser.LlamadaContext ctx) {
        List<String> argumentos = new ArrayList<>();
        if (ctx.argumentos() != null) {
            for (MiLenguajeParser.ExpresionContext expresion : ctx.argumentos().expresion()) {
                argumentos.add(visit(expresion));
            }
        }
        emitir("CALL func_" + ctx.ID().getText()
                + (argumentos.isEmpty() ? "" : ", " + String.join(", ", argumentos)));
        String temp = nuevoTemporal();
        emitir(temp + " = RETURN_VALUE");
        return temp;
    }

    private String binaria(MiLenguajeParser.ExpresionContext izquierda, String operador,
                           MiLenguajeParser.ExpresionContext derecha) {
        String temp = nuevoTemporal();
        emitir(temp + " = " + visit(izquierda) + " " + operador + " " + visit(derecha));
        return temp;
    }

    private String unaria(String operador, String valor) {
        String temp = nuevoTemporal();
        emitir(temp + " = " + operador + valor);
        return temp;
    }

    private String textoAcceso(MiLenguajeParser.AccesoContext ctx) {
        return ctx.expresion() == null
                ? ctx.ID().getText()
                : ctx.ID().getText() + "[" + visit(ctx.expresion()) + "]";
    }

    private String nuevoTemporal() { return "t" + (++temporal); }
    private String nuevaEtiqueta(String prefijo) { return prefijo + "_" + (++etiqueta); }
    private void emitir(String instruccion) { codigo.add(instruccion); }
}
