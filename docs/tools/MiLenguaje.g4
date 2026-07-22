// =====================================================================
// KIT DE APOYO PARA EXAMEN
// Copia comentada de la gramatica principal.
//
// Idea central:
// - Reglas en minuscula  -> parser -> estructura del lenguaje
// - Reglas en MAYUSCULA  -> lexer  -> tokens
//
// Donde conectas una regla define donde se puede usar:
// - en elemento  -> nivel global
// - en sentencia -> dentro de bloques
// - en expresion -> dentro de expresiones
// =====================================================================

grammar MiLenguaje;

// -------------------------------------------------------------------
// REGLA RAIZ
// -------------------------------------------------------------------
programa
    : elemento* EOF
    ;

// -------------------------------------------------------------------
// ELEMENTOS GLOBALES
// -------------------------------------------------------------------
// Si el profe pide una construccion global nueva, normalmente se toca aca.
// Ejemplo ya practicado: importacion.
elemento
    : funcion
    | declaracion
    | importacion
    ;

// -------------------------------------------------------------------
// IMPORT GLOBAL
// -------------------------------------------------------------------
// Ejemplo:
// import "math";
//
// Si tambien quisieras permitirlo dentro de bloques,
// habria que agregar `importacion` en `sentencia`.
importacion
    : IMPORT CADENA PYC
    ;

// -------------------------------------------------------------------
// FUNCIONES Y PARAMETROS
// -------------------------------------------------------------------
funcion
    : tipo ID PA parametros? PC bloque
    ;

parametros
    : parametro (COMA parametro)*
    ;

parametro
    : tipo ID
    ;

// -------------------------------------------------------------------
// SENTENCIAS
// -------------------------------------------------------------------
// Si el profe pide agregar una instruccion nueva como:
// - do while
// - switch
// - read
// - print
// se suele crear una regla nueva y colgarla aca.
sentencia
    : declaracion
    | asignacion
    | llamada PYC
    | sentenciaCout
    | sentenciaIf
    | sentenciaWhile
    | sentenciaFor
    | sentenciaReturn
    | BREAK PYC
    | CONTINUE PYC
    | bloque
    ;

// -------------------------------------------------------------------
// DECLARACIONES Y ASIGNACIONES
// -------------------------------------------------------------------
declaracion
    : tipo ID (CA INTEGER CC)? (IGUAL expresion)? PYC
    ;

asignacion
    : acceso IGUAL expresion PYC
    ;

sentenciaCout
    : COUT SHIFT_L expresion (SHIFT_L expresion)* PYC
    ;

// -------------------------------------------------------------------
// ESTRUCTURAS DE CONTROL
// -------------------------------------------------------------------
sentenciaIf
    : IF PA expresion PC bloque (ELSE bloque)?
    ;

sentenciaWhile
    : WHILE PA expresion PC bloque
    ;

sentenciaFor
    : FOR PA (declaracion | asignacion | PYC) expresion? PYC asignacionFor? PC bloque
    ;

// Plantilla util para examen:
//
// sentenciaDoWhile
//     : DO bloque WHILE PA expresion PC PYC
//     ;
//
// Si la agregas, no olvides conectarla en `sentencia`.

asignacionFor
    : acceso IGUAL expresion
    ;

sentenciaReturn
    : RETURN expresion? PYC
    ;

bloque
    : LA sentencia* LC
    ;

// -------------------------------------------------------------------
// TIPOS
// -------------------------------------------------------------------
// Si el profe pide un tipo nuevo, agregar:
// 1. el token abajo
// 2. el nombre aca
tipo
    : INT
    | FLOAT
    | DOUBLE
    | CHAR
    | STRING_TYPE
    | BOOL
    | VOID
    ;

// -------------------------------------------------------------------
// EXPRESIONES
// -------------------------------------------------------------------
// Esta regla controla que cosas pueden actuar como expresion
// y tambien la precedencia de operadores.
expresion
    : RES expresion                                                     # exprNegativo
    | NOT expresion                                                     # exprNot
    | expresion (MUL | DIV | MOD) expresion                             # exprMultiplicativa
    | expresion (SUM | RES) expresion                                   # exprAditiva
    | expresion (MAYOR | MENOR | MAYOR_IGUAL | MENOR_IGUAL) expresion  # exprRelacional
    | expresion (EQL | DISTINTO) expresion                              # exprIgualdad
    | expresion AND expresion                                           # exprAnd
    | expresion OR expresion                                            # exprOr
    | PA expresion PC                                                   # exprAgrupada
    | llamada                                                           # exprLlamada
    | acceso                                                            # exprAcceso
    | INTEGER                                                           # exprEntero
    | DECIMAL                                                           # exprDecimal
    | CHARACTER                                                         # exprCaracter
    | CADENA                                                            # exprCadena
    | VERDADERO                                                         # exprVerdadero
    | FALSO                                                             # exprFalso
    ;

// -------------------------------------------------------------------
// LLAMADAS Y ACCESOS
// -------------------------------------------------------------------
llamada
    : ID PA argumentos? PC
    ;

argumentos
    : expresion (COMA expresion)*
    ;

acceso
    : ID (CA expresion CC)?
    ;

// -------------------------------------------------------------------
// PUNTUACION
// -------------------------------------------------------------------
PA   : '(';
PC   : ')';
CA   : '[';
CC   : ']';
LA   : '{';
LC   : '}';
PYC  : ';';
COMA : ',';

SHIFT_L    : '<<';
EQL        : '==';
DISTINTO   : '!=';
MAYOR_IGUAL: '>=';
MENOR_IGUAL: '<=';
IGUAL      : '=';
MAYOR      : '>';
MENOR      : '<';
SUM        : '+';
RES        : '-';
MUL        : '*';
DIV        : '/';
MOD        : '%';
OR         : '||';
AND        : '&&';
NOT        : '!';

// -------------------------------------------------------------------
// KEYWORDS
// -------------------------------------------------------------------
// Si agregas una palabra reservada nueva, normalmente:
// 1. agregas el token aca
// 2. la usas en una regla del parser arriba
FOR      : 'for';
WHILE    : 'while';
IF       : 'if';
ELSE     : 'else';
RETURN   : 'return';
BREAK    : 'break';
CONTINUE : 'continue';
COUT     : 'cout';
IMPORT   : 'import';

// Plantillas para cambios tipicos:
// DO       : 'do';
// SWITCH   : 'switch';
// CASE     : 'case';
// DEFAULT  : 'default';
// LONG     : 'long';
// READ     : 'read';
// PRINT    : 'print';
// CONST    : 'const';

INT         : 'int';
FLOAT       : 'float';
DOUBLE      : 'double';
CHAR        : 'char';
STRING_TYPE : 'string';
BOOL        : 'bool';
VOID        : 'void';
VERDADERO   : 'true';
FALSO       : 'false';

// -------------------------------------------------------------------
// IDENTIFICADORES Y LITERALES
// -------------------------------------------------------------------
ID        : [A-Za-z_] [A-Za-z0-9_]*;
DECIMAL   : [0-9]+ '.' [0-9]+;
INTEGER   : [0-9]+;
CHARACTER : '\'' (~['\\\r\n] | '\\' .) '\'';
CADENA    : '"' (~["\\\r\n] | '\\' .)* '"';

// Plantillas utiles de regex lexica para examen:
//
// Hexadecimal:
// HEX       : '0' [xX] [0-9a-fA-F]+;
//
// Binario:
// BINARIO   : '0' [bB] [01]+;
//
// Octal:
// OCTAL     : '0' [0-7]+;
//
// Decimal con signo:
// ENTERO_SIGNADO : [+-]? [0-9]+;
//
// Real con exponente:
// CIENTIFICO : [0-9]+ ('.' [0-9]+)? [eE] [+-]? [0-9]+;
//
// Identificador que permita $:
// ID_DOLAR  : [A-Za-z_$] [A-Za-z0-9_$]*;
//
// Ruta de import mas restringida:
// RUTA_MODULO : '"' [A-Za-z_./] [A-Za-z0-9_./-]* '"';
//
// String sin escapes:
// CADENA_SIMPLE : '"' ~["\r\n]* '"';
//
// Bool como token unico:
// BOOLEANO  : 'true' | 'false';

// Operadores nuevos que podrian pedir:
// INCREMENTO   : '++';
// DECREMENTO   : '--';
// MAS_IGUAL    : '+=';
// MENOS_IGUAL  : '-=';
// POR_IGUAL    : '*=';
// DIV_IGUAL    : '/=';
// POTENCIA     : '^';
// DOS_PUNTOS   : ':';
// PREGUNTA     : '?';

// -------------------------------------------------------------------
// COMENTARIOS, ESPACIOS Y TOKEN DE ERROR
// -------------------------------------------------------------------
COMENTARIO_LINEA  : '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE : '/*' .*? '*/' -> skip;
WS                : [ \r\n\t]+ -> skip;
OTRO              : .;
