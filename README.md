# Compilador de un subconjunto de C++

Trabajo final de Tecnicas de Compilacion implementado en Java con ANTLR4.

## Funcionalidades

- Analisis lexico con reporte de caracteres no reconocidos.
- Analisis sintactico y visualizacion opcional del arbol ANTLR.
- Tipos `int`, `float`, `double`, `char`, `string`, `bool` y `void`.
- Variables, arrays, funciones, parametros, llamadas y retornos.
- `if/else`, `while`, `for`, `break`, `continue` y `cout`.
- Tabla de simbolos por ambito.
- Validacion de declaraciones, tipos, arrays, llamadas y retornos.
- Errores y warnings diferenciados con colores ANSI.
- Codigo intermedio de tres direcciones.
- Optimizacion por plegado/propagacion de constantes, eliminacion de
  asignaciones redundantes y codigo inalcanzable.
- Archivos de codigo intermedio y optimizado.
- Pruebas automatizadas con JUnit.

## Requisitos

- JDK 8 o superior.
- Maven 3.8 o superior.

## Compilar

```bash
cd demo
mvn clean package
```

## Ejecutar

```bash
java -jar target/demo-1.0-jar-with-dependencies.jar ejemplo_correcto.cpp
```

Para abrir el arbol sintactico:

```bash
java -jar target/demo-1.0-jar-with-dependencies.jar ejemplo_correcto.cpp --gui
```

La ejecucion genera, junto al archivo fuente:

- `<nombre>_codigo_intermedio.txt`
- `<nombre>_codigo_optimizado.txt`

## Pruebas

```bash
cd demo
mvn clean test
```

Casos incluidos:

- `ejemplo_correcto.cpp`: programa valido basado en el ejemplo del profesor.
- `ejemplo_semantico_error.cpp`: duplicados, variable no declarada y warning.
- `ejemplo_error.txt`: errores sintacticos.
- `ejemplos/ejemplo1.cpp`: caso ampliado con funciones, arrays, `for`, `while` y `cout`.
- `ejemplos/ejemplo2_basico.cpp`: declaraciones, asignaciones, `if/else` y expresiones.
- `ejemplos/ejemplo3_funciones.cpp`: funciones `int`, `double` y `void` con llamadas y retornos.
- `ejemplos/ejemplo4_bucles.cpp`: uso intensivo de `while`, `for`, `break` y `continue`.
- `ejemplos/ejemplo5_completo.cpp`: caso combinado con funciones, bucles y expresiones logicas.

## Estructura

```text
demo/
|-- ejemplos/
|   |-- ejemplo1.cpp
|   |-- ejemplo2_basico.cpp
|   |-- ejemplo3_funciones.cpp
|   |-- ejemplo4_bucles.cpp
|   `-- ejemplo5_completo.cpp
|-- src/main/antlr4/com/compilador/MiLenguaje.g4
|-- src/main/java/com/compilador/
|   |-- App.java
|   |-- AnalizadorSemantico.java
|   |-- TablaSimbolos.java
|   |-- Simbolo.java
|   |-- ResultadoSemantico.java
|   |-- GeneradorCodigo.java
|   |-- Optimizador.java
|   `-- optimizaciones/
|       |-- OptimizacionCodigo.java
|       |-- SimplificacionExpresionesOptimizacion.java
|       |-- PropagacionConstantesOptimizacion.java
|       `-- EliminacionCodigoMuertoOptimizacion.java
|-- src/test/java/com/compilador/AppTest.java
|-- ejemplo_correcto.cpp
|-- ejemplo_semantico_error.cpp
|-- ejemplo_error.txt
`-- pom.xml
```

La documentacion ampliada esta en [docs/INFORME_TECNICO.md](docs/INFORME_TECNICO.md)
y [docs/MANUAL_USUARIO.md](docs/MANUAL_USUARIO.md).
La version PDF del informe tecnico debe ubicarse en `docs/Informe Técnico TC.pdf`.
