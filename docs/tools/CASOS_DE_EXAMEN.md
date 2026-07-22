# Casos de examen probables

Esta lista junta cambios que un profe suele pedir en un final practico.
La idea es que te sirva como mapa rapido para decidir que archivos tocar.

## Nivel muy probable

- Agregar `import "math";`
- Agregar una palabra reservada nueva
- Agregar una sentencia nueva
- Agregar un tipo nuevo
- Hacer que algo aparezca en la tabla de simbolos
- Agregar una columna nueva en la tabla
- Validar una regla semantica puntual
- Hacer que una construccion nueva tambien genere codigo intermedio

## Keywords o sentencias nuevas

- `import "modulo";`
- `read(x);`
- `print(x);`
- `scan(x);`
- `do { ... } while (...);`
- `switch (...) { ... }`
- `case`
- `default`
- `repeat { ... } until (...);`
- `foreach (x in arreglo) { ... }`
- `exit;`
- `halt;`
- `assert(expresion);`

## Tipos nuevos o modificadores

- `long`
- `short`
- `byte`
- `const`
- `unsigned`
- `signed`

## Operadores nuevos

- `++`
- `--`
- `+=`
- `-=`
- `*=`
- `/=`
- `^`
- `?:`
- casteo `(int) x`

## Declaraciones y variables

- `int a, b, c;`
- arrays de mas de una dimension
- inicializacion de arrays
- constantes
- variables con valor por defecto
- variables solo lectura

## Reglas semanticas tipicas

- variable usada sin declarar
- variable declarada dos veces
- parametro duplicado
- variable no usada
- parametro no usado
- funcion no declarada
- llamada con cantidad incorrecta de argumentos
- `break` fuera de un bucle
- `continue` fuera de un bucle
- `return` fuera de una funcion
- funcion sin `return` cuando debe devolver valor
- `main` obligatorio
- `main` debe devolver `int`
- condicion de `if`, `while` o `for` debe ser `bool`
- array debe indexarse con `int`
- no permitir asignar a una funcion

## Tabla de simbolos

- categoria `IMPORT`
- categoria `CONSTANTE`
- categoria `TIPO`
- categoria `BUCLE`
- columna `usado`
- columna `inicializado`
- columna `modificador`
- columna `cantidadParametros`

## Codigo intermedio

- emitir `IMPORT math`
- emitir `READ x`
- emitir `PRINT x`
- generar labels para `if / else`
- generar labels para `while`
- generar labels para `for`
- generar labels para `do while`
- generar codigo para `break`
- generar codigo para `continue`
- generar llamada a funcion con `CALL`
- capturar retorno en `RETURN_VALUE`

## Optimizaciones

- simplificar `x = 2 + 3`
- propagar `x = 5` dentro de otra expresion
- eliminar `x = x`
- eliminar codigo despues de `goto`
- eliminar codigo despues de `return`
- simplificar `x + 0`
- simplificar `x * 1`
- eliminar temporales no usados

## Mapa rapido: que tocar

- Nueva sintaxis: `MiLenguaje.g4`
- Nueva validacion: `AnalizadorSemantico.java`
- Nuevo simbolo o categoria: `Simbolo.java`
- Nuevo formato de tabla: `TablaSimbolos.java`
- Nueva salida intermedia: `GeneradorCodigo.java`
- Nueva optimizacion: `Optimizador.java` y `optimizaciones/`

## Extras que tambien podrian pedir

- permitir `import` solo global
- permitir `import` tambien dentro de bloques
- agregar una expresion literal nueva
- cambiar la precedencia de operadores
- mostrar warnings nuevos
- distinguir entre error y warning
- guardar resultados en archivos distintos
- activar o desactivar una optimizacion
