package com.compilador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResultadoSemantico {
    private final TablaSimbolos tabla;
    private final List<String> errores = new ArrayList<>();
    private final List<String> warnings = new ArrayList<>();

    public ResultadoSemantico(TablaSimbolos tabla) {
        this.tabla = tabla;
    }

    public TablaSimbolos getTabla() { return tabla; }
    public List<String> getErrores() { return Collections.unmodifiableList(errores); }
    public List<String> getWarnings() { return Collections.unmodifiableList(warnings); }
    public boolean esValido() { return errores.isEmpty(); }
    public void error(String mensaje) { errores.add(mensaje); }
    public void warning(String mensaje) { warnings.add(mensaje); }
}
