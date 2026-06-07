package com.compilador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class AppTest {
    @Test
    public void compilaEjemploCorrecto() throws Exception {
        Path archivo = Files.createTempFile("ejemplo_correcto", ".cpp");
        Files.write(archivo, (
                "int contadorGlobal;\n" +
                "double valorPi;\n" +
                "char inicial;\n" +
                "bool activo;\n" +
                "int sumar(int a, int b) {\n" +
                "  int resultado;\n" +
                "  resultado = a + b;\n" +
                "  contadorGlobal = contadorGlobal + 1;\n" +
                "  return resultado;\n" +
                "}\n" +
                "int main() {\n" +
                "  int estado;\n" +
                "  int temp;\n" +
                "  int numeros[3];\n" +
                "  contadorGlobal = 0;\n" +
                "  valorPi = 3.14;\n" +
                "  inicial = 'M';\n" +
                "  numeros[0] = 10;\n" +
                "  numeros[1] = 20;\n" +
                "  temp = numeros[0] + numeros[1];\n" +
                "  estado = sumar(temp, 5);\n" +
                "  if (estado > 0) { estado = estado + 10; }\n" +
                "  return estado;\n" +
                "}\n").getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        int codigo = App.compilar(archivo, false, new PrintStream(salida), System.err);
        String texto = salida.toString("UTF-8");
        assertEquals(0, codigo);
        assertTrue(texto.contains("COMPILACION Y OPTIMIZACION EXITOSA"));
        assertTrue(Files.exists(salida(archivo, "_codigo_intermedio.txt")));
        assertTrue(Files.exists(salida(archivo, "_codigo_optimizado.txt")));
    }

    @Test
    public void detectaVariableNoDeclarada() throws Exception {
        Path archivo = Files.createTempFile("ejemplo_error", ".cpp");
        Files.write(archivo, "int main() { fantasma = 1; return 0; }\n"
                .getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        int codigo = App.compilar(archivo, false, new PrintStream(salida), System.err);
        assertEquals(4, codigo);
        assertTrue(salida.toString("UTF-8").contains("Variable 'fantasma' no declarada"));
    }

    @Test
    public void aplicaOptimizacionesRequeridas() {
        List<String> optimizado = new Optimizador().optimizar(Arrays.asList(
                "t1 = 5 + 3",
                "x = x",
                "goto FIN",
                "t2 = 999",
                "FIN:",
                "return t1"));
        assertTrue(optimizado.contains("t1 = 8"));
        assertTrue(!optimizado.contains("x = x"));
        assertTrue(!optimizado.contains("t2 = 999"));
    }

    private Path salida(Path entrada, String sufijo) {
        String nombre = entrada.getFileName().toString();
        return entrada.getParent().resolve(nombre.substring(0, nombre.lastIndexOf('.')) + sufijo);
    }
}
