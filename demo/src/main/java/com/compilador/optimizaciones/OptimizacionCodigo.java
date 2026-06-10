package com.compilador.optimizaciones;

import java.util.List;

public interface OptimizacionCodigo {
    String getNombre();

    List<String> aplicar(List<String> entrada);
}
