package com.compilador;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TablaSimbolos {
    private final Map<String, LinkedHashMap<String, Simbolo>> ambitos = new LinkedHashMap<>();
    private final List<Simbolo> simbolos = new ArrayList<>();

    public TablaSimbolos() {
        ambitos.put("global", new LinkedHashMap<String, Simbolo>());
    }

    public boolean declarar(Simbolo simbolo) {
        LinkedHashMap<String, Simbolo> tabla = ambitos.computeIfAbsent(
                simbolo.getAmbito(), k -> new LinkedHashMap<String, Simbolo>());
        if (tabla.containsKey(simbolo.getNombre())) {
            return false;
        }
        tabla.put(simbolo.getNombre(), simbolo);
        simbolos.add(simbolo);
        return true;
    }

    public Simbolo buscar(String nombre, String ambito) {
        Map<String, Simbolo> local = ambitos.get(ambito);
        if (local != null && local.containsKey(nombre)) {
            return local.get(nombre);
        }
        return ambitos.get("global").get(nombre);
    }

    public List<Simbolo> todos() {
        List<Simbolo> ordenados = new ArrayList<>(simbolos);
        ordenados.sort(Comparator.comparingInt(Simbolo::getLinea)
                .thenComparingInt(Simbolo::getColumna));
        return ordenados;
    }

    public void imprimir(PrintStream out) {
        out.println("=== TABLA DE SIMBOLOS ===");
        out.printf("%-18s %-10s %-12s %-8s %-10s %-16s %s%n",
                "NOMBRE", "TIPO", "CATEGORIA", "LINEA", "COLUMNA", "AMBITO", "DETALLES");
        out.println("------------------------------------------------------------------------------------------------");
        for (Simbolo simbolo : todos()) {
            out.printf("%-18s %-10s %-12s %-8d %-10d %-16s %s%n",
                    simbolo.getNombre(),
                    simbolo.getTipo(),
                    simbolo.getCategoria().name().toLowerCase(),
                    simbolo.getLinea(),
                    simbolo.getColumna(),
                    simbolo.getAmbito(),
                    simbolo.detalles());
        }
    }
}
