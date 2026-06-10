package com.compilador;

import com.compilador.optimizaciones.EliminacionCodigoMuertoOptimizacion;
import com.compilador.optimizaciones.PropagacionConstantesOptimizacion;
import com.compilador.optimizaciones.SimplificacionExpresionesOptimizacion;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

public final class App {
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String ROJO = "\u001B[31m";
    private static final String RESET = "\u001B[0m";
    // Comente cualquiera de estas lineas para desactivar una optimizacion puntual.
    private static final Optimizador OPTIMIZADOR = new Optimizador(Arrays.asList(
            new SimplificacionExpresionesOptimizacion(),
            new PropagacionConstantesOptimizacion(),
            new EliminacionCodigoMuertoOptimizacion()
    ));

    private App() {}

    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            System.out.println("Uso: java -jar demo-1.0-jar-with-dependencies.jar <archivo.cpp> [--gui]");
            System.exit(1);
        }
        boolean gui = args.length == 2 && "--gui".equals(args[1]);
        int salida = compilar(Paths.get(args[0]), gui, System.out, System.err);
        if (salida != 0) System.exit(salida);
    }

    public static int compilar(Path archivo, boolean mostrarGui, PrintStream out, PrintStream err) {
        try {
            out.println("Iniciando compilacion de: " + archivo.getFileName());
            out.println(repetir("=", 60));

            CharStream input = CharStreams.fromPath(archivo, StandardCharsets.UTF_8);
            MiLenguajeLexer lexer = new MiLenguajeLexer(input);
            List<String> erroresLexicos = new ArrayList<>();
            lexer.removeErrorListeners();
            lexer.addErrorListener(listener(erroresLexicos));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill();
            for (Token token : tokens.getTokens()) {
                if (token.getType() == MiLenguajeLexer.OTRO) {
                    erroresLexicos.add(posicion(token) + " caracter no reconocido '" + token.getText() + "'");
                }
            }

            out.println("\n=== 1. ANALISIS LEXICO ===");
            if (!erroresLexicos.isEmpty()) {
                imprimirMensajes(out, ROJO, "ERRORES LEXICOS", erroresLexicos);
                return 2;
            }
            int cantidadTokens = Math.max(0, tokens.getTokens().size() - 1);
            exito(out, "Analisis lexico completado sin errores.");
            out.println("   Tokens procesados: " + cantidadTokens);

            out.println("\n=== 2. ANALISIS SINTACTICO ===");
            tokens.seek(0);
            MiLenguajeParser parser = new MiLenguajeParser(tokens);
            List<String> erroresSintacticos = new ArrayList<>();
            parser.removeErrorListeners();
            parser.addErrorListener(listener(erroresSintacticos));
            MiLenguajeParser.ProgramaContext arbol = parser.programa();
            if (!erroresSintacticos.isEmpty()) {
                imprimirMensajes(out, ROJO, "ERRORES SINTACTICOS", erroresSintacticos);
                return 3;
            }
            exito(out, "Analisis sintactico completado sin errores.");
            out.println("   Arbol sintactico generado correctamente");

            out.println("\n=== 3. VISUALIZACION DEL AST ===");
            if (mostrarGui && !GraphicsEnvironment.isHeadless()) {
                mostrarArbol(arbol, parser);
                out.println("   Ventana del arbol sintactico abierta");
            } else {
                out.println("   Visualizacion omitida (use --gui para abrirla)");
            }

            out.println("\n=== 4. ANALISIS SEMANTICO ===");
            ResultadoSemantico semantica = new AnalizadorSemantico().analizar(arbol);
            out.println("   Tabla de simbolos construida:\n");
            semantica.getTabla().imprimir(out);
            if (!semantica.getWarnings().isEmpty()) {
                imprimirMensajes(out, AMARILLO, "WARNINGS SEMANTICOS", semantica.getWarnings());
            }
            if (!semantica.esValido()) {
                imprimirMensajes(out, ROJO, "ERRORES SEMANTICOS", semantica.getErrores());
                out.println(ROJO + "\nCompilacion detenida debido a errores semanticos." + RESET);
                return 4;
            }
            exito(out, "Analisis semantico completado sin errores.");

            out.println("\n=== 5. GENERACION DE CODIGO INTERMEDIO ===");
            List<String> intermedio = new GeneradorCodigo().generar(arbol);
            imprimirCodigo(out, intermedio);
            Path archivoIntermedio = rutaSalida(archivo, "_codigo_intermedio.txt");
            guardarCodigo(archivoIntermedio, intermedio);
            exito(out, "Codigo intermedio guardado en: " + archivoIntermedio.getFileName());

            out.println("\n=== 6. OPTIMIZACION DE CODIGO ===");
            out.println("   Optimizaciones activas: " + String.join(", ",
                    OPTIMIZADOR.nombresOptimizaciones()));
            out.println("   Para desactivar una, comente su linea en App.java");
            List<String> optimizado = OPTIMIZADOR.optimizar(intermedio);
            imprimirCodigo(out, optimizado);
            Path archivoOptimizado = rutaSalida(archivo, "_codigo_optimizado.txt");
            guardarCodigo(archivoOptimizado, optimizado);
            int eliminadas = intermedio.size() - optimizado.size();
            double reduccion = intermedio.isEmpty() ? 0 : eliminadas * 100.0 / intermedio.size();
            exito(out, "Optimizacion completada.");
            out.printf("   Instrucciones originales: %d%n", intermedio.size());
            out.printf("   Instrucciones optimizadas: %d%n", optimizado.size());
            out.printf("   Reduccion de codigo: %.2f%%%n", reduccion);
            exito(out, "Codigo optimizado guardado en: " + archivoOptimizado.getFileName());

            out.println("\n=== 7. RESUMEN DE COMPILACION ===");
            out.println("   Archivo procesado: " + archivo.getFileName());
            out.println("   Tokens analizados: " + cantidadTokens);
            out.println("   Simbolos en tabla: " + semantica.getTabla().todos().size());
            out.println("   Instrucciones generadas: " + intermedio.size());
            out.println("   Instrucciones optimizadas: " + optimizado.size());
            out.println(VERDE + "\nCOMPILACION Y OPTIMIZACION EXITOSA" + RESET);
            return 0;
        } catch (IOException e) {
            err.println(ROJO + "No se pudo procesar el archivo: " + e.getMessage() + RESET);
            return 1;
        } catch (RuntimeException e) {
            err.println(ROJO + "Error inesperado: " + e.getMessage() + RESET);
            e.printStackTrace(err);
            return 1;
        }
    }

    private static BaseErrorListener listener(final List<String> errores) {
        return new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object simbolo, int linea,
                                    int columna, String mensaje, RecognitionException error) {
                errores.add("[linea " + linea + ", columna " + columna + "] " + mensaje);
            }
        };
    }

    private static String posicion(Token token) {
        return "[linea " + token.getLine() + ", columna " + token.getCharPositionInLine() + "]";
    }

    private static void imprimirMensajes(PrintStream out, String color, String titulo,
                                         List<String> mensajes) {
        out.println(color + "\n" + titulo + ":" + RESET);
        for (String mensaje : mensajes) out.println(color + "   - " + mensaje + RESET);
    }

    private static void exito(PrintStream out, String mensaje) {
        out.println(VERDE + "[OK] " + mensaje + RESET);
    }

    private static void imprimirCodigo(PrintStream out, List<String> codigo) {
        out.println("   Codigo de tres direcciones:");
        for (int i = 0; i < codigo.size(); i++) {
            out.printf("%3d: %s%n", i, codigo.get(i));
        }
    }

    private static Path rutaSalida(Path entrada, String sufijo) {
        String nombre = entrada.getFileName().toString();
        int punto = nombre.lastIndexOf('.');
        String base = punto > 0 ? nombre.substring(0, punto) : nombre;
        Path padre = entrada.toAbsolutePath().getParent();
        return padre.resolve(base + sufijo);
    }

    private static void guardarCodigo(Path archivo, List<String> codigo) throws IOException {
        List<String> numerado = new ArrayList<>();
        for (int i = 0; i < codigo.size(); i++) {
            numerado.add(String.format("%3d: %s", i, codigo.get(i)));
        }
        Files.write(archivo, numerado, StandardCharsets.UTF_8);
    }

    private static String repetir(String texto, int cantidad) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < cantidad; i++) resultado.append(texto);
        return resultado.toString();
    }

    private static void mostrarArbol(ParseTree tree, MiLenguajeParser parser) {
        JFrame frame = new JFrame("Arbol Sintactico");
        JPanel panel = new JPanel();
        TreeViewer viewer = new TreeViewer(Arrays.asList(parser.getRuleNames()), tree);
        viewer.setScale(1.0);
        panel.add(viewer);
        frame.add(new JScrollPane(panel));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setVisible(true);
    }
}
