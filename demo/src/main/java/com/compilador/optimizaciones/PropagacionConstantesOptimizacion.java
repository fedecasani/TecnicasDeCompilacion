package com.compilador.optimizaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PropagacionConstantesOptimizacion implements OptimizacionCodigo {
    private static final Pattern ASIGNACION = Pattern.compile("^(\\w+) = (.+)$");

    @Override
    public String getNombre() {
        return "Propagacion de constantes";
    }

    @Override
    public List<String> aplicar(List<String> entrada) {
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
}
