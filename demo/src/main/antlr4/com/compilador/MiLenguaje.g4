grammar MiLenguaje;

programa
    : elemento* EOF
    ;

elemento
    : funcion
    | declaracion
    ;

funcion
    : tipo ID PA parametros? PC bloque
    ;

parametros
    : parametro (COMA parametro)*
    ;

parametro
    : tipo ID
    ;

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

declaracion
    : tipo ID (CA INTEGER CC)? (IGUAL expresion)? PYC
    ;

asignacion
    : acceso IGUAL expresion PYC
    ;

sentenciaCout
    : COUT SHIFT_L expresion (SHIFT_L expresion)* PYC
    ;

sentenciaIf
    : IF PA expresion PC bloque (ELSE bloque)?
    ;

sentenciaWhile
    : WHILE PA expresion PC bloque
    ;

sentenciaFor
    : FOR PA (declaracion | asignacion | PYC) expresion? PYC asignacionFor? PC bloque
    ;

asignacionFor
    : acceso IGUAL expresion
    ;

sentenciaReturn
    : RETURN expresion? PYC
    ;

bloque
    : LA sentencia* LC
    ;

tipo
    : INT
    | FLOAT
    | DOUBLE
    | CHAR
    | STRING_TYPE
    | BOOL
    | VOID
    ;

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

llamada
    : ID PA argumentos? PC
    ;

argumentos
    : expresion (COMA expresion)*
    ;

acceso
    : ID (CA expresion CC)?
    ;

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

FOR      : 'for';
WHILE    : 'while';
IF       : 'if';
ELSE     : 'else';
RETURN   : 'return';
BREAK    : 'break';
CONTINUE : 'continue';
COUT     : 'cout';

INT         : 'int';
FLOAT       : 'float';
DOUBLE      : 'double';
CHAR        : 'char';
STRING_TYPE : 'string';
BOOL        : 'bool';
VOID        : 'void';
VERDADERO   : 'true';
FALSO       : 'false';

ID        : [A-Za-z_] [A-Za-z0-9_]*;
DECIMAL   : [0-9]+ '.' [0-9]+;
INTEGER   : [0-9]+;
CHARACTER : '\'' (~['\\\r\n] | '\\' .) '\'';
CADENA    : '"' (~["\\\r\n] | '\\' .)* '"';

COMENTARIO_LINEA  : '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE : '/*' .*? '*/' -> skip;
WS                : [ \r\n\t]+ -> skip;
OTRO              : .;
