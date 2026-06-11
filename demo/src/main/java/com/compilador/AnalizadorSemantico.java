package com.compilador;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public final class AnalizadorSemantico extends MiLenguajeBaseVisitor<String> {
    private final TablaSimbolos tabla = new TablaSimbolos();
    private final ResultadoSemantico resultado = new ResultadoSemantico(tabla);
    private String ambito = "global";
    private String tipoRetorno = null;

    public ResultadoSemantico analizar(MiLenguajeParser.ProgramaContext programa) {
        for (MiLenguajeParser.ElementoContext elemento : programa.elemento()) {
            if (elemento.funcion() != null) {
                declararFuncion(elemento.funcion());
            }
        }
        visit(programa);
        for (Simbolo simbolo : tabla.todos()) {
            if (simbolo.getCategoria() == Simbolo.Categoria.VARIABLE
                    && !"global".equals(simbolo.getAmbito())
                    && !simbolo.isUsado()) {
                resultado.warning("Variable '" + simbolo.getNombre()
                        + "' declarada pero nunca utilizada en el ambito '"
                        + simbolo.getAmbito() + "' (linea " + simbolo.getLinea() + ")");
            }
        }
        return resultado;
    }

    // Declara cada funcion en la tabla antes de analizar cuerpos para permitir
    // llamadas posteriores y detectar duplicados en el ambito global.
    private void declararFuncion(MiLenguajeParser.FuncionContext ctx) {
        List<String> parametros = new ArrayList<>();
        if (ctx.parametros() != null) {
            for (MiLenguajeParser.ParametroContext parametro : ctx.parametros().parametro()) {
                parametros.add(parametro.tipo().getText());
            }
        }
        Simbolo funcion = simbolo(ctx.ID().getText(), ctx.tipo().getText(),
                Simbolo.Categoria.FUNCION, ctx, "global", null, parametros);
        if (!tabla.declarar(funcion)) {
            error(ctx, "La funcion '" + funcion.getNombre() + "' ya esta declarada");
        }
    }

    @Override
    // Cambia al ambito de la funcion, declara sus parametros y valida semanticamente
    // todas las sentencias de su bloque.
    public String visitFuncion(MiLenguajeParser.FuncionContext ctx) {
        String anterior = ambito;
        String retornoAnterior = tipoRetorno;
        ambito = ctx.ID().getText();
        tipoRetorno = ctx.tipo().getText();
        if (ctx.parametros() != null) {
            for (MiLenguajeParser.ParametroContext parametro : ctx.parametros().parametro()) {
                Simbolo simbolo = simbolo(parametro.ID().getText(), parametro.tipo().getText(),
                        Simbolo.Categoria.PARAMETRO, parametro, ambito, null, null);
                if (!tabla.declarar(simbolo)) {
                    error(parametro, "El parametro '" + simbolo.getNombre() + "' esta duplicado");
                }
            }
        }
        visit(ctx.bloque());
        ambito = anterior;
        tipoRetorno = retornoAnterior;
        return null;
    }

    @Override
    // Registra una variable o array en la tabla y valida que su inicializacion,
    // si existe, sea compatible con el tipo declarado.
    public String visitDeclaracion(MiLenguajeParser.DeclaracionContext ctx) {
        Integer tamanio = ctx.INTEGER() == null ? null : Integer.valueOf(ctx.INTEGER().getText());
        Simbolo simbolo = simbolo(ctx.ID().getText(), ctx.tipo().getText(),
                Simbolo.Categoria.VARIABLE, ctx, ambito, tamanio, null);
        if (!tabla.declarar(simbolo)) {
            error(ctx, "La variable '" + simbolo.getNombre()
                    + "' ya esta declarada en el ambito '" + ambito + "'");
        }
        if (ctx.expresion() != null) {
            String tipoExpresion = visit(ctx.expresion());
            validarAsignacion(ctx, simbolo.getTipo(), tipoExpresion);
        }
        return null;
    }

    @Override
    // Verifica que el destino exista, que se use como variable/array valido y
    // que el tipo de la expresion pueda asignarse al destino.
    public String visitAsignacion(MiLenguajeParser.AsignacionContext ctx) {
        Simbolo destino = resolverAcceso(ctx.acceso(), true);
        String tipoValor = visit(ctx.expresion());
        if (destino != null) {
            validarAsignacion(ctx, destino.getTipo(), tipoValor);
        }
        return null;
    }

    @Override
    // Aplica las mismas validaciones semanticas de una asignacion normal dentro
    // de la seccion de incremento/paso del for.
    public String visitAsignacionFor(MiLenguajeParser.AsignacionForContext ctx) {
        Simbolo destino = resolverAcceso(ctx.acceso(), true);
        String tipoValor = visit(ctx.expresion());
        if (destino != null) {
            validarAsignacion(ctx, destino.getTipo(), tipoValor);
        }
        return null;
    }

    @Override
    // Controla que el return aparezca dentro de una funcion y que el valor
    // retornado sea compatible con el tipo declarado por esa funcion.
    public String visitSentenciaReturn(MiLenguajeParser.SentenciaReturnContext ctx) {
        if (tipoRetorno == null) {
            error(ctx, "Sentencia return fuera de una funcion");
        } else if (ctx.expresion() == null && !"void".equals(tipoRetorno)) {
            error(ctx, "La funcion debe retornar un valor de tipo " + tipoRetorno);
        } else if (ctx.expresion() != null) {
            validarAsignacion(ctx, tipoRetorno, visit(ctx.expresion()));
        }
        return null;
    }

    @Override
    // Valida accesos a variables y arrays dentro de expresiones y devuelve el
    // tipo resultante para seguir chequeando la expresion envolvente.
    public String visitExprAcceso(MiLenguajeParser.ExprAccesoContext ctx) {
        Simbolo simbolo = resolverAcceso(ctx.acceso(), false);
        return simbolo == null ? "error" : simbolo.getTipo();
    }

    @Override
    // Reusa la validacion de llamadas y propaga el tipo de retorno de la funcion.
    public String visitExprLlamada(MiLenguajeParser.ExprLlamadaContext ctx) {
        return validarLlamada(ctx.llamada());
    }

    @Override
    // Valida una llamada usada como sentencia: existencia, cantidad de argumentos
    // y compatibilidad de tipos en cada parametro.
    public String visitLlamada(MiLenguajeParser.LlamadaContext ctx) {
        return validarLlamada(ctx);
    }

    // Comprueba que la funcion exista, que se invoque con la aridad correcta y
    // que cada argumento respete el tipo esperado por su parametro.
    private String validarLlamada(MiLenguajeParser.LlamadaContext ctx) {
        Simbolo funcion = tabla.buscar(ctx.ID().getText(), ambito);
        if (funcion == null || funcion.getCategoria() != Simbolo.Categoria.FUNCION) {
            error(ctx, "Funcion '" + ctx.ID().getText() + "' no declarada");
            return "error";
        }
        funcion.marcarUsado();
        List<MiLenguajeParser.ExpresionContext> argumentos = ctx.argumentos() == null
                ? new ArrayList<MiLenguajeParser.ExpresionContext>()
                : ctx.argumentos().expresion();
        if (argumentos.size() != funcion.getParametros().size()) {
            error(ctx, "La funcion '" + funcion.getNombre() + "' espera "
                    + funcion.getParametros().size() + " argumentos y recibio " + argumentos.size());
        }
        for (int i = 0; i < argumentos.size(); i++) {
            String actual = visit(argumentos.get(i));
            if (i < funcion.getParametros().size()) {
                validarAsignacion(ctx, funcion.getParametros().get(i), actual);
            }
        }
        return funcion.getTipo();
    }

    // Resuelve una variable o acceso a array, valida declaracion, categoria,
    // uso correcto del indice y diferencia entre lectura y escritura.
    private Simbolo resolverAcceso(MiLenguajeParser.AccesoContext ctx, boolean escritura) {
        Simbolo simbolo = tabla.buscar(ctx.ID().getText(), ambito);
        if (simbolo == null) {
            error(ctx, "Variable '" + ctx.ID().getText()
                    + "' no declarada en el ambito '" + ambito + "'");
            if (ctx.expresion() != null) visit(ctx.expresion());
            return null;
        }
        if (simbolo.getCategoria() == Simbolo.Categoria.FUNCION) {
            error(ctx, "No se puede asignar o acceder a '" + simbolo.getNombre()
                    + "' como variable");
            return null;
        }
        if (!escritura) simbolo.marcarUsado();
        if (ctx.expresion() != null) {
            if (simbolo.getTamanioArray() == null) {
                error(ctx, "'" + simbolo.getNombre() + "' no es un array");
            }
            if (!"int".equals(visit(ctx.expresion()))) {
                error(ctx, "El indice de un array debe ser int");
            }
        } else if (simbolo.getTamanioArray() != null && escritura) {
            error(ctx, "La asignacion al array '" + simbolo.getNombre() + "' requiere un indice");
        }
        return simbolo;
    }

    @Override public String visitExprEntero(MiLenguajeParser.ExprEnteroContext ctx) { return "int"; }
    @Override public String visitExprDecimal(MiLenguajeParser.ExprDecimalContext ctx) { return "double"; }
    @Override public String visitExprCaracter(MiLenguajeParser.ExprCaracterContext ctx) { return "char"; }
    @Override public String visitExprCadena(MiLenguajeParser.ExprCadenaContext ctx) { return "string"; }
    @Override public String visitExprVerdadero(MiLenguajeParser.ExprVerdaderoContext ctx) { return "bool"; }
    @Override public String visitExprFalso(MiLenguajeParser.ExprFalsoContext ctx) { return "bool"; }
    @Override public String visitExprAgrupada(MiLenguajeParser.ExprAgrupadaContext ctx) { return visit(ctx.expresion()); }
    // Exige un operando numerico y conserva su tipo para la expresion resultante.
    @Override public String visitExprNegativo(MiLenguajeParser.ExprNegativoContext ctx) {
        String tipo = visit(ctx.expresion());
        if (!esNumerico(tipo)) error(ctx, "El operador '-' requiere un operando numerico");
        return tipo;
    }
    // Exige un operando booleano y produce siempre una expresion de tipo bool.
    @Override public String visitExprNot(MiLenguajeParser.ExprNotContext ctx) {
        String tipo = visit(ctx.expresion());
        if (!"bool".equals(tipo)) error(ctx, "El operador '!' requiere un operando bool");
        return "bool";
    }
    @Override public String visitExprAditiva(MiLenguajeParser.ExprAditivaContext ctx) { return binariaNumerica(ctx, ctx.expresion()); }
    @Override public String visitExprMultiplicativa(MiLenguajeParser.ExprMultiplicativaContext ctx) { return binariaNumerica(ctx, ctx.expresion()); }
    @Override public String visitExprRelacional(MiLenguajeParser.ExprRelacionalContext ctx) {
        binariaNumerica(ctx, ctx.expresion());
        return "bool";
    }
    // Permite comparar tipos compatibles y produce un booleano como resultado.
    @Override public String visitExprIgualdad(MiLenguajeParser.ExprIgualdadContext ctx) {
        String izquierda = visit(ctx.expresion(0));
        String derecha = visit(ctx.expresion(1));
        if (!compatibles(izquierda, derecha)) error(ctx, "Tipos incompatibles en comparacion");
        return "bool";
    }
    @Override public String visitExprAnd(MiLenguajeParser.ExprAndContext ctx) { return binariaLogica(ctx, ctx.expresion()); }
    @Override public String visitExprOr(MiLenguajeParser.ExprOrContext ctx) { return binariaLogica(ctx, ctx.expresion()); }

    // Verifica operaciones aritmeticas/relacionales entre operandos numericos y
    // calcula el tipo resultante de la promocion numerica.
    private String binariaNumerica(ParserRuleContext ctx, List<MiLenguajeParser.ExpresionContext> expresiones) {
        String izquierda = visit(expresiones.get(0));
        String derecha = visit(expresiones.get(1));
        if (!esNumerico(izquierda) || !esNumerico(derecha)) {
            error(ctx, "La operacion aritmetica requiere operandos numericos");
            return "error";
        }
        return "double".equals(izquierda) || "double".equals(derecha)
                || "float".equals(izquierda) || "float".equals(derecha) ? "double" : "int";
    }

    // Exige operandos booleanos para operadores logicos como && y ||.
    private String binariaLogica(ParserRuleContext ctx, List<MiLenguajeParser.ExpresionContext> expresiones) {
        String izquierda = visit(expresiones.get(0));
        String derecha = visit(expresiones.get(1));
        if (!"bool".equals(izquierda) || !"bool".equals(derecha)) {
            error(ctx, "La operacion logica requiere operandos bool");
        }
        return "bool";
    }

    // Centraliza el chequeo de compatibilidad entre el tipo destino y el tipo
    // de la expresion que se intenta asignar o retornar.
    private void validarAsignacion(ParserRuleContext ctx, String destino, String origen) {
        if (!compatibles(destino, origen)) {
            error(ctx, "No se puede asignar un valor de tipo " + origen + " a " + destino);
        }
    }

    // Define la regla de compatibilidad semantica: mismo tipo o conversion entre numericos.
    private boolean compatibles(String destino, String origen) {
        if ("error".equals(origen) || destino.equals(origen)) return true;
        return esNumerico(destino) && esNumerico(origen);
    }

    // Resume que tipos participan en operaciones y conversiones numericas.
    private boolean esNumerico(String tipo) {
        return "int".equals(tipo) || "float".equals(tipo) || "double".equals(tipo);
    }

    // Crea el objeto simbolo con la informacion semantica minima que se guarda en tabla.
    private Simbolo simbolo(String nombre, String tipo, Simbolo.Categoria categoria,
                            ParserRuleContext ctx, String scope, Integer tamanio,
                            List<String> parametros) {
        return new Simbolo(nombre, tipo, categoria, ctx.getStart().getLine(),
                ctx.getStart().getCharPositionInLine(), scope, tamanio, parametros);
    }

    // Registra errores semanticos con ubicacion para mostrarlos luego en la salida final.
    private void error(ParserRuleContext ctx, String mensaje) {
        resultado.error(mensaje + " (linea " + ctx.getStart().getLine()
                + ", columna " + ctx.getStart().getCharPositionInLine() + ")");
    }
}
