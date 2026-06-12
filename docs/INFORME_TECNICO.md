# Informe tecnico

## Introduccion

El proyecto implementa un compilador educativo para un subconjunto de C++.
Fue desarrollado en Java y utiliza ANTLR4 para generar el lexer, el parser y
el patron Visitor. El flujo cubre las fases solicitadas: analisis lexico,
sintactico, semantico, codigo intermedio y optimizacion.

## Subconjunto implementado

Tipos:

- `int`, `float`, `double`, `char`, `string`, `bool`, `void`.

Construcciones:

- Variables globales y locales.
- Arrays unidimensionales de tamano constante.
- Funciones con parametros y valor de retorno.
- Asignaciones, llamadas y `cout`.
- `if/else`, `while`, `for`, `break`, `continue`, `return`.
- Operadores aritmeticos, relacionales, de igualdad y logicos.

## Arquitectura

```text
Fuente C++
   |
   v
Lexer ANTLR -> tokens -> Parser ANTLR -> arbol sintactico
                                      |
                                      v
                              AnalizadorSemantico
                                      |
                              Tabla de simbolos
                                      |
                                      v
                              GeneradorCodigo
                                      |
                         codigo de tres direcciones
                                      |
                                      v
                                Optimizador
```

Estructura principal del proyecto:

```text
demo/
|-- ejemplos/
|   `-- ejemplo1.cpp
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

## Analisis lexico y sintactico

La gramatica `MiLenguaje.g4` contiene reglas combinadas de lexer y parser.
Los comentarios y espacios se omiten. La regla `OTRO` conserva caracteres no
reconocidos para producir diagnosticos con linea y columna. La precedencia de
expresiones se define mediante alternativas recursivas etiquetadas.

## Analisis semantico

`AnalizadorSemantico` recorre el arbol y construye `TablaSimbolos`.
Cada simbolo guarda nombre, tipo, categoria, ubicacion, ambito, parametros y
tamano de array.

Validaciones:

- Declaraciones duplicadas en el mismo ambito.
- Uso de variables y funciones no declaradas.
- Compatibilidad en asignaciones y retornos.
- Operandos numericos y booleanos.
- Indices de array enteros.
- Cantidad y tipos de argumentos.
- Uso de funciones como si fueran variables.
- Variables locales declaradas pero no utilizadas.

## Codigo intermedio

`GeneradorCodigo` produce instrucciones de tres direcciones con temporales y
etiquetas. Ejemplo:

```text
t1 = a + b
resultado = t1
if t2 goto THEN_1
goto END_IF_2
CALL func_sumar, temp, 5
t3 = RETURN_VALUE
```

Las estructuras de control se traducen a saltos y etiquetas. Las funciones se
representan mediante etiquetas `func_<nombre>` y parametros `PARAM`.

## Optimizaciones

`Optimizador` coordina tres tecnicas principales agrupadas en el paquete
`com.compilador.optimizaciones`:

1. Simplificacion de expresiones:
   plegado de constantes y eliminacion de asignaciones redundantes como `x = x`.
2. Propagacion de constantes dentro de bloques basicos.
3. Eliminacion de codigo inalcanzable despues de `goto` o `return`.

Las barreras de flujo limpian el mapa de constantes para evitar propagar
valores a traves de llamadas, saltos o etiquetas.

## Pruebas

Las pruebas JUnit verifican:

- Compilacion completa del ejemplo correcto.
- Creacion de ambos archivos de salida.
- Deteccion de una variable no declarada.

Ademas se incluyen entradas manuales para errores sintacticos y semanticos,
y una carpeta `ejemplos/` con programas mas completos para demostracion.

## Decisiones de diseno

- Visitors separados por responsabilidad.
- Tabla de simbolos determinista y ordenada por ubicacion.
- Ejecucion sin GUI por defecto para funcionar en CI.
- Colores ANSI para mantener la interfaz portable.
- Archivos generados junto al fuente para facilitar la demostracion.

## Dificultades y soluciones

La rama inicial solo soportaba declaraciones, asignaciones, `if`, `while` y
`cout`. Se amplio la gramatica cuidando la precedencia y se reemplazo el
visitor demostrativo por componentes con responsabilidades concretas. La
visualizacion Swing se hizo opcional para evitar bloqueos en entornos sin
pantalla.

## Conclusion

La implementacion recorre el proceso completo de compilacion requerido y deja
puntos de extension claros para agregar mas dimensiones de arrays, clases,
punteros o generacion de codigo maquina en trabajos futuros.
