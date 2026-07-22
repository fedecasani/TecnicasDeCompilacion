# FAQ final

## Que es ANTLR

Es una herramienta que, a partir del `.g4`, te genera el lexer y el parser.

## Que es el AST

Es el arbol que representa la estructura del programa ya parseado.

## Diferencia entre lexer y parser

- lexer: corta el texto en tokens
- parser: usa esos tokens para verificar la estructura

## La tabla de simbolos se llena sola

No. Se llena desde el analizador semantico.

## De donde sale `visitImportacion`

Del nombre de la regla del parser `importacion`.

## Si agrego un token nuevo ya alcanza

No siempre. Normalmente falta conectarlo en una regla del parser.

## Cuando tengo que tocar el semantico

Cuando haya que validar significado o guardar algo en la tabla.

## Cuando tengo que tocar el generador

Cuando la construccion nueva deba emitir codigo intermedio.

## Que es codigo intermedio

Una version simplificada del programa, pensada para analizar u optimizar.

## Por que el codigo intermedio usa `goto`

Porque es una forma simple de representar saltos y flujo de control.

## Por que el optimizador trabaja sobre codigo intermedio

Porque es mucho mas simple optimizar una lista de instrucciones que el
arbol fuente completo.
