package com.compilador;

import com.compilador.optimizaciones.EliminacionCodigoMuertoOptimizacion;
import com.compilador.optimizaciones.OptimizacionCodigo;
import com.compilador.optimizaciones.PropagacionConstantesOptimizacion;
import com.compilador.optimizaciones.SimplificacionExpresionesOptimizacion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Copia de apoyo.
// Esta clase orquesta las optimizaciones sobre el codigo intermedio.
public final class Optimizador {
    private final List<OptimizacionCodigo> optimizaciones;

    public Optimizador() {
        this(Arrays.asList(
                new SimplificacionExpresionesOptimizacion(),
                new PropagacionConstantesOptimizacion(),
                new SimplificacionExpresionesOptimizacion(),
                new EliminacionCodigoMuertoOptimizacion()));
    }

    // Orden mental:
    // 1. simplificacion inicial
    // 2. propagacion de constantes
    // 3. nueva simplificacion despues de propagar
    // 4. limpieza final de codigo muerto

    public Optimizador(List<OptimizacionCodigo> optimizaciones) {
        this.optimizaciones = new ArrayList<>(optimizaciones);
    }

    public List<String> optimizar(List<String> original) {
        List<String> actual = new ArrayList<>(original);
        for (OptimizacionCodigo optimizacion : optimizaciones) {
            actual = optimizacion.aplicar(actual);
        }
        return actual;
    }

    public List<String> nombresOptimizaciones() {
        List<String> nombres = new ArrayList<>();
        for (OptimizacionCodigo optimizacion : optimizaciones) {
            nombres.add(optimizacion.getNombre());
        }
        return nombres;
    }
}
