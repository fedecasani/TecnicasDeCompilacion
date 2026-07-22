package com.compilador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Copia de apoyo.
// Si queres que una construccion nueva aparezca prolija en tabla,
// muchas veces hay que agregar una categoria nueva aca.
public final class Simbolo {
    public enum Categoria { VARIABLE, PARAMETRO, FUNCION, IMPORT }

    private final String nombre;
    private final String tipo;
    private final Categoria categoria;
    private final int linea;
    private final int columna;
    private final String ambito;
    private final Integer tamanioArray;
    private final List<String> parametros;
    private boolean usado;

    public Simbolo(String nombre, String tipo, Categoria categoria, int linea, int columna,
                   String ambito, Integer tamanioArray, List<String> parametros) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.categoria = categoria;
        this.linea = linea;
        this.columna = columna;
        this.ambito = ambito;
        this.tamanioArray = tamanioArray;
        this.parametros = parametros == null
                ? Collections.<String>emptyList()
                : Collections.unmodifiableList(new ArrayList<>(parametros));
    }

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public Categoria getCategoria() { return categoria; }
    public int getLinea() { return linea; }
    public int getColumna() { return columna; }
    public String getAmbito() { return ambito; }
    public Integer getTamanioArray() { return tamanioArray; }
    public List<String> getParametros() { return parametros; }
    public boolean isUsado() { return usado; }
    public void marcarUsado() { usado = true; }

    public String detalles() {
        if (categoria == Categoria.FUNCION) {
            return "[private] " + parametros;
        }
        if (categoria == Categoria.IMPORT) {
            return "[modulo]";
        }
        if (tamanioArray != null) {
            return "[arr:" + tamanioArray + "] [private]";
        }
        return categoria == Categoria.VARIABLE ? "[private]" : "";
    }
}
