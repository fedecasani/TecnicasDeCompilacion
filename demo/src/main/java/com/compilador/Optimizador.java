package com.compilador;

import com.compilador.optimizaciones.EliminacionCodigoMuertoOptimizacion;
import com.compilador.optimizaciones.OptimizacionCodigo;
import com.compilador.optimizaciones.PropagacionConstantesOptimizacion;
import com.compilador.optimizaciones.SimplificacionExpresionesOptimizacion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Optimizador {
    private final List<OptimizacionCodigo> optimizaciones;

    public Optimizador() {
        this(Arrays.asList(
                new SimplificacionExpresionesOptimizacion(),
                new PropagacionConstantesOptimizacion(),
                new EliminacionCodigoMuertoOptimizacion()));
    }

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
