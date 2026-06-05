package com.compilador;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Optimizador {
    private static final Pattern BINARIA = Pattern.compile(
            "^(\\w+) = (-?\\d+(?:\\.\\d+)?) ([+\\-*/%]) (-?\\d+(?:\\.\\d+)?)$");
    private static final Pattern ASIGNACION = Pattern.compile("^(\\w+) = (.+)$");

    public List<String> optimizar(List<String> original) {
        List<String> actual = plegarConstantes(original);
        actual = propagarConstantes(actual);
        actual = eliminarRedundancias(actual);
        actual = eliminarCodigoInalcanzable(actual);
        return actual;
    }

    private List<String> plegarConstantes(List<String> entrada) {
        List<String> salida = new ArrayList<>();
        for (String linea : entrada) {
            Matcher matcher = BINARIA.matcher(linea);
            if (!matcher.matches()) {
                salida.add(linea);
                continue;
            }
            BigDecimal izquierda = new BigDecimal(matcher.group(2));
            BigDecimal derecha = new BigDecimal(matcher.group(4));
            BigDecimal valor;
            switch (matcher.group(3)) {
                case "+": valor = izquierda.add(derecha); break;
                case "-": valor = izquierda.subtract(derecha); break;
                case "*": valor = izquierda.multiply(derecha); break;
                case "/":
                    if (derecha.compareTo(BigDecimal.ZERO) == 0) { salida.add(linea); continue; }
                    valor = izquierda.divide(derecha, 8, RoundingMode.HALF_UP).stripTrailingZeros();
                    break;
                case "%":
                    if (derecha.compareTo(BigDecimal.ZERO) == 0) { salida.add(linea); continue; }
                    valor = izquierda.remainder(derecha);
                    break;
                default: salida.add(linea); continue;
            }
            salida.add(matcher.group(1) + " = " + valor.stripTrailingZeros().toPlainString());
        }
        return salida;
    }

    private List<String> propagarConstantes(List<String> entrada) {
        Map<String, String> constantes = new HashMap<>();
        List<String> salida = new ArrayList<>();
        for (String linea : entrada) {
            if (linea.endsWith(":") || linea.startsWith("CALL") || linea.startsWith("if ")
                    || linea.startsWith("goto ") || linea.startsWith("return")) {
                constantes.clear();
                salida.add(linea);
                continue;
            }
            Matcher asignacion = ASIGNACION.matcher(linea);
            if (asignacion.matches()) {
                String destino = asignacion.group(1);
                String valor = reemplazar(asignacion.group(2), constantes);
                salida.add(destino + " = " + valor);
                if (valor.matches("-?\\d+(?:\\.\\d+)?|true|false|'(?:\\\\.|[^'])'")) {
                    constantes.put(destino, valor);
                } else {
                    constantes.remove(destino);
                }
            } else {
                salida.add(reemplazar(linea, constantes));
            }
        }
        return salida;
    }

    private String reemplazar(String linea, Map<String, String> constantes) {
        String resultado = linea;
        for (Map.Entry<String, String> entry : constantes.entrySet()) {
            resultado = resultado.replaceAll("\\b" + Pattern.quote(entry.getKey()) + "\\b",
                    Matcher.quoteReplacement(entry.getValue()));
        }
        return resultado;
    }

    private List<String> eliminarRedundancias(List<String> entrada) {
        List<String> salida = new ArrayList<>();
        for (String linea : entrada) {
            Matcher matcher = ASIGNACION.matcher(linea);
            if (matcher.matches() && matcher.group(1).equals(matcher.group(2))) continue;
            salida.add(linea);
        }
        return salida;
    }

    private List<String> eliminarCodigoInalcanzable(List<String> entrada) {
        List<String> salida = new ArrayList<>();
        boolean inalcanzable = false;
        for (String linea : entrada) {
            if (linea.endsWith(":")) inalcanzable = false;
            if (!inalcanzable) salida.add(linea);
            if (linea.startsWith("goto ") || linea.startsWith("return")) inalcanzable = true;
        }
        return salida;
    }
}
