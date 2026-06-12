# Manual de usuario

## Instalacion

1. Instalar JDK 8 o superior.
2. Instalar Maven 3.8 o superior.
3. Desde `demo`, ejecutar `mvn clean package`.

El JAR ejecutable queda en:

```text
demo/target/demo-1.0-jar-with-dependencies.jar
```

## Uso

```bash
java -jar target/demo-1.0-jar-with-dependencies.jar archivo.cpp
```

Si el archivo se encuentra dentro de la carpeta `ejemplos`, debe indicarse la
ruta relativa:

```bash
java -jar target/demo-1.0-jar-with-dependencies.jar ejemplos/ejemplo1.cpp
```

Agregar `--gui` para abrir el arbol sintactico con Swing:

```bash
java -jar target/demo-1.0-jar-with-dependencies.jar archivo.cpp --gui
```

## Salidas

El compilador muestra siete etapas:

1. Analisis lexico.
2. Analisis sintactico.
3. Visualizacion del AST/arbol sintactico.
4. Analisis semantico y tabla de simbolos.
5. Generacion de codigo intermedio.
6. Optimizacion.
7. Resumen.

Tambien crea:

```text
archivo_codigo_intermedio.txt
archivo_codigo_optimizado.txt
```

## Mensajes

- Verde: fase completada correctamente.
- Amarillo: warning no critico, por ejemplo una variable local no utilizada.
- Rojo: error que detiene la compilacion.

## Lenguaje soportado

```cpp
int sumar(int a, int b) {
    return a + b;
}

int main() {
    int numeros[3];
    int resultado;
    numeros[0] = 10;
    resultado = sumar(numeros[0], 5);

    if (resultado > 0) {
        cout << resultado;
    }
    return resultado;
}
```

Se soportan `if/else`, `while`, `for`, `break`, `continue`, arrays,
funciones, llamadas, retornos y expresiones aritmeticas, relacionales y logicas.

## Casos de prueba incluidos

- `ejemplo_correcto.cpp`: programa valido basado en el ejemplo del profesor.
- `ejemplo_semantico_error.cpp`: caso con errores semanticos y warnings.
- `ejemplo_error.txt`: caso con errores sintacticos.
- `ejemplos/ejemplo1.cpp`: ejemplo amplio con funciones, arrays y `cout`.
- `ejemplos/ejemplo2_basico.cpp`: ejemplo simple con declaraciones y `if/else`.
- `ejemplos/ejemplo3_funciones.cpp`: ejemplo centrado en funciones y retornos.
- `ejemplos/ejemplo4_bucles.cpp`: ejemplo centrado en `for`, `while`, `break` y `continue`.
- `ejemplos/ejemplo5_completo.cpp`: ejemplo combinado para demostracion integral.

## Solucion de problemas

- `mvn` no reconocido: agregar Maven al `PATH`.
- `java` no reconocido: instalar el JDK y configurar `JAVA_HOME`.
- No aparece la ventana: ejecutar con `--gui` en un entorno grafico.
- Error lexico: revisar caracteres fuera del subconjunto soportado.
- Error sintactico: revisar `;`, parentesis, corchetes y llaves.
- Error semantico: revisar declaraciones, tipos, ambitos y argumentos.
