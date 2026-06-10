package com.compilador.optimizaciones;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SimplificacionExpresionesOptimizacion implements OptimizacionCodigo {
    private static final Pattern BINARIA = Pattern.compile(
            "^(\\w+) = (-?\\d+(?:\\.\\d+)?) ([+\\-*/%]) (-?\\d+(?:\\.\\d+)?)$");
    private static final Pattern ASIGNACION = Pattern.compile("^(\\w+) = (.+)$");

    @Override
    public String getNombre() {
        return "Simplificacion de expresiones";
    }

    @Override
    public List<String> aplicar(List<String> entrada) {
        return eliminarRedundancias(plegarConstantes(entrada));
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
                case "+":
                    valor = izquierda.add(derecha);
                    break;
                case "-":
                    valor = izquierda.subtract(derecha);
                    break;
                case "*":
                    valor = izquierda.multiply(derecha);
                    break;
                case "/":
                    if (derecha.compareTo(BigDecimal.ZERO) == 0) {
                        salida.add(linea);
                        continue;
                    }
                    valor = izquierda.divide(derecha, 8, RoundingMode.HALF_UP).stripTrailingZeros();
                    break;
                case "%":
                    if (derecha.compareTo(BigDecimal.ZERO) == 0) {
                        salida.add(linea);
                        continue;
                    }
                    valor = izquierda.remainder(derecha);
                    break;
                default:
                    salida.add(linea);
                    continue;
            }
            salida.add(matcher.group(1) + " = " + valor.stripTrailingZeros().toPlainString());
        }
        return salida;
    }

    private List<String> eliminarRedundancias(List<String> entrada) {
        List<String> salida = new ArrayList<>();
        for (String linea : entrada) {
            Matcher matcher = ASIGNACION.matcher(linea);
            if (matcher.matches() && matcher.group(1).equals(matcher.group(2))) {
                continue;
            }
            salida.add(linea);
        }
        return salida;
    }
}
