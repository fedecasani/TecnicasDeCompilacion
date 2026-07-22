# Plantillas de codigo intermedio

## Idea general

El generador toma el AST y lo baja a codigo de tres direcciones.
No ejecuta el programa: lo reescribe en una forma mas simple.

## 1. Declaracion simple

Codigo fuente:

```cpp
int x;
```

Codigo intermedio:

```text
DECLARE x int
```

## 2. Declaracion con inicializacion

Codigo fuente:

```cpp
int x = 5;
```

Codigo intermedio:

```text
DECLARE x int
x = 5
```

## 3. Expresion aritmetica

Codigo fuente:

```cpp
x = a + b;
```

Codigo intermedio:

```text
t1 = a + b
x = t1
```

## 4. If simple

Codigo fuente:

```cpp
if (x > 0) {
    y = 1;
}
```

Codigo intermedio:

```text
t1 = x > 0
if t1 goto THEN_1
goto END_IF_2
THEN_1:
y = 1
END_IF_2:
```

## 5. If / else

Codigo fuente:

```cpp
if (x > 0) {
    y = 1;
} else {
    y = 2;
}
```

Codigo intermedio:

```text
t1 = x > 0
if t1 goto THEN_1
goto ELSE_2
THEN_1:
y = 1
goto END_IF_3
ELSE_2:
y = 2
END_IF_3:
```

## 6. While

Codigo fuente:

```cpp
while (x < 10) {
    x = x + 1;
}
```

Codigo intermedio:

```text
WHILE_1:
t1 = x < 10
if t1 goto WHILE_BODY_2
goto END_WHILE_3
WHILE_BODY_2:
t2 = x + 1
x = t2
goto WHILE_1
END_WHILE_3:
```

## 7. For

Codigo fuente:

```cpp
for (int i = 0; i < 5; i = i + 1) {
    suma = suma + i;
}
```

Codigo intermedio:

```text
DECLARE i int
i = 0
FOR_1:
t1 = i < 5
if t1 goto FOR_BODY_2
goto END_FOR_4
FOR_BODY_2:
t2 = suma + i
suma = t2
FOR_STEP_3:
t3 = i + 1
i = t3
goto FOR_1
END_FOR_4:
```

## 8. Llamada a funcion como sentencia

Codigo fuente:

```cpp
mostrar();
```

Codigo intermedio:

```text
CALL func_mostrar
```

## 9. Llamada a funcion como expresion

Codigo fuente:

```cpp
x = sumar(a, b);
```

Codigo intermedio:

```text
CALL func_sumar, a, b
t1 = RETURN_VALUE
x = t1
```

## 10. Return

Codigo fuente:

```cpp
return x;
```

Codigo intermedio:

```text
return x
```

## 11. Break y continue

`break` se traduce a un `goto` hacia la etiqueta de fin del bucle.

`continue` se traduce a un `goto` hacia la etiqueta de paso o reevaluacion.

## 12. Si agregas una construccion nueva

Pensar siempre:

1. Que labels necesito
2. Si necesito temporales
3. Si necesito una instruccion nueva tipo `READ`, `IMPORT`, `PRINT`
4. Si afecta `break` o `continue`
5. Si cambia el flujo con `goto`

## 13. Do while

Codigo fuente:

```cpp
do {
    x = x + 1;
} while (x < 3);
```

Codigo intermedio posible:

```text
DO_1:
t1 = x + 1
x = t1
t2 = x < 3
if t2 goto DO_1
```

## 14. Read

Codigo fuente:

```cpp
read(x);
```

Codigo intermedio posible:

```text
READ x
```

## 15. Import

Codigo fuente:

```cpp
import "math";
```

Codigo intermedio posible si el profe lo quiere reflejado:

```text
IMPORT math
```

## 16. Incremento

Codigo fuente:

```cpp
x++;
```

Codigo intermedio posible:

```text
t1 = x + 1
x = t1
```

## 17. Asignacion compuesta

Codigo fuente:

```cpp
x += 5;
```

Codigo intermedio posible:

```text
t1 = x + 5
x = t1
```

## 18. Switch

Forma simple de pensarlo:

1. comparar la expresion con cada `case`
2. saltar al label que corresponda
3. tener un label final de salida
