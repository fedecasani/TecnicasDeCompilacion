package com.compilador.optimizaciones;

import java.util.ArrayList;
import java.util.List;

// Si aparece un `goto` o `return`, las instrucciones siguientes
// quedan inalcanzables hasta la proxima etiqueta.
public final class EliminacionCodigoMuertoOptimizacion implements OptimizacionCodigo {
    @Override
    public String getNombre() {
        return "Eliminacion de codigo muerto";
    }

    @Override
    public List<String> aplicar(List<String> entrada) {
        List<String> salida = new ArrayList<>();
        boolean inalcanzable = false;
        for (String linea : entrada) {
            if (linea.endsWith(":")) {
                inalcanzable = false;
            }
            if (!inalcanzable) {
                salida.add(linea);
            }
            if (linea.startsWith("goto ") || linea.startsWith("return")) {
                inalcanzable = true;
            }
        }
        return salida;
    }
}
