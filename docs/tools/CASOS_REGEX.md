# Casos de regex utiles

## Identificador

```antlr
ID : [A-Za-z_] [A-Za-z0-9_]*;
```

## Entero

```antlr
INTEGER : [0-9]+;
```

## Decimal

```antlr
DECIMAL : [0-9]+ '.' [0-9]+;
```

## Hexadecimal

```antlr
HEX : '0' [xX] [0-9a-fA-F]+;
```

## Binario

```antlr
BINARIO : '0' [bB] [01]+;
```

## Octal

```antlr
OCTAL : '0' [0-7]+;
```

## Notacion cientifica

```antlr
CIENTIFICO : [0-9]+ ('.' [0-9]+)? [eE] [+-]? [0-9]+;
```

## Cadena

```antlr
CADENA : '"' (~["\\\r\n] | '\\' .)* '"';
```

## Char

```antlr
CHARACTER : '\'' (~['\\\r\n] | '\\' .) '\'';
```

## Comentario de linea

```antlr
COMENTARIO_LINEA : '//' ~[\r\n]* -> skip;
```

## Comentario de bloque

```antlr
COMENTARIO_BLOQUE : '/*' .*? '*/' -> skip;
```

## Ruta de modulo

```antlr
RUTA_MODULO : '"' [A-Za-z_./] [A-Za-z0-9_./-]* '"';
```

## Operadores comunes que pueden pedir

```antlr
INCREMENTO  : '++';
DECREMENTO  : '--';
MAS_IGUAL   : '+=';
MENOS_IGUAL : '-=';
POR_IGUAL   : '*=';
DIV_IGUAL   : '/=';
```
