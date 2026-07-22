package com.compilador.optimizaciones;

import java.util.List;

// Contrato comun para cualquier optimizacion sobre codigo intermedio.
public interface OptimizacionCodigo {
    String getNombre();
    List<String> aplicar(List<String> entrada);
}
