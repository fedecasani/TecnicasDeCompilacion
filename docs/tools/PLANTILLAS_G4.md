# Plantillas para tocar el g4

## Regla mental principal

- Si lo agregas en `elemento`, queda global
- Si lo agregas en `sentencia`, queda dentro de bloques
- Si lo agregas en `expresion`, se puede usar dentro de expresiones

## 1. Agregar una keyword nueva

```antlr
IMPORT : 'import';
```

Despues esa keyword se usa en una regla del parser.

## 2. Agregar una construccion global

```antlr
elemento
    : funcion
    | declaracion
    | importacion
    ;

importacion
    : IMPORT CADENA PYC
    ;
```

## 3. Agregar una sentencia nueva

```antlr
sentencia
    : declaracion
    | asignacion
    | sentenciaDoWhile
    | bloque
    ;

sentenciaDoWhile
    : DO bloque WHILE PA expresion PC PYC
    ;
```

## 4. Agregar un tipo nuevo

```antlr
LONG : 'long';

tipo
    : INT
    | DOUBLE
    | LONG
    ;
```

## 5. Agregar un operador nuevo

```antlr
POT : '^';

expresion
    : expresion POT expresion # exprPotencia
    | ...
    ;
```

Despues hay que tocar semantico y posiblemente generador.

## 6. Agregar una funcion builtin

Si la sintaxis es igual a una llamada comun, tal vez no haga falta tocar
el parser. Podria resolverse solo en el semantico si se la trata como
funcion especial.

## 7. Agregar multiples variables

```antlr
declaracion
    : tipo ID (COMA ID)* PYC
    ;
```

Despues toca rearmar el semantico porque ya no habria un solo `ID`.

## 8. Agregar arrays multidimensionales

```antlr
declaracion
    : tipo ID (CA INTEGER CC)+ PYC
    ;
```

## 9. Agregar lectura

```antlr
READ : 'read';

sentenciaRead
    : READ PA acceso PC PYC
    ;
```

Y colgar `sentenciaRead` de `sentencia`.

## 10. Despues de tocar el g4

1. Regenerar ANTLR
2. Ver que metodo `visit...` nuevo aparece
3. Tocar semantico si hace falta
4. Tocar generador si hace falta

## 11. Regex utiles que te pueden pedir

Identificador:

```antlr
ID : [A-Za-z_] [A-Za-z0-9_]*;
```

Entero:

```antlr
INTEGER : [0-9]+;
```

Decimal:

```antlr
DECIMAL : [0-9]+ '.' [0-9]+;
```

Cadena:

```antlr
CADENA : '"' (~["\\\r\n] | '\\' .)* '"';
```

Char:

```antlr
CHARACTER : '\'' (~['\\\r\n] | '\\' .) '\'';
```

Hexadecimal:

```antlr
HEX : '0' [xX] [0-9a-fA-F]+;
```

Binario:

```antlr
BINARIO : '0' [bB] [01]+;
```

Notacion cientifica:

```antlr
CIENTIFICO : [0-9]+ ('.' [0-9]+)? [eE] [+-]? [0-9]+;
```

Comentario de linea:

```antlr
COMENTARIO_LINEA : '//' ~[\r\n]* -> skip;
```

Comentario de bloque:

```antlr
COMENTARIO_BLOQUE : '/*' .*? '*/' -> skip;
```
