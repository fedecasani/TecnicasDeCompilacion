// =====================================================================
//  ANÁLISIS SINTÁCTICO - Mini Lenguaje tipo C++
//  Proyecto educativo de Técnicas de Compilación
//
//  Un archivo .g4 contiene DOS tipos de reglas:
//    - Reglas del PARSER  (en minúscula): definen la ESTRUCTURA
//    - Reglas del LEXER   (en MAYÚSCULA): definen los TOKENS
//
//  ANTLR4 genera automáticamente el Lexer y el Parser a partir
//  de este archivo. ¡No hay que escribirlos a mano!
// =====================================================================

grammar MiLenguaje;


// -------------------------------------------------------------------
// REGLA INICIAL DEL PROGRAMA
// -------------------------------------------------------------------
// Si el profe pide agregar algo a nivel global, normalmente se toca:
// - elemento
// - o se agrega una nueva regla y se la referencia desde elemento
programa
    : elemento* EOF
    ;

// -------------------------------------------------------------------
// ELEMENTOS GLOBALES
// -------------------------------------------------------------------
// Hoy un programa puede tener funciones o declaraciones globales.
// Si agregas algo como importaciones u otra construccion global,
// suele agregarse aca.
// Regla mental importante:
// - si una construccion se agrega en elemento, se permite a nivel global
// - si se agrega en sentencia, se permite dentro de bloques
// - si se agrega en expresion, se puede usar dentro de expresiones
elemento
    : funcion
    | declaracion
    ;

// -------------------------------------------------------------------
// FUNCIONES Y PARAMETROS
// -------------------------------------------------------------------
// Esta zona define como se escribe una funcion.
// Si cambia la sintaxis de funciones o parametros, suele tocarse aca.
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
// Esta es una de las reglas mas importantes.
// Si agregas una sentencia nueva (por ejemplo do while o switch),
// normalmente:
// 1. creas una regla nueva
// 2. la agregas aca
// Todo lo que este conectado aca podra aparecer dentro de un bloque `{ ... }`.
// Si algo no queres permitir dentro de funciones o bloques, no debe agregarse aca.
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
// Si cambia como se declara una variable, array o asignacion,
// normalmente se modifica esta zona.
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
// Si el cambio pedido afecta if / while / for / return, se toca aca.
// Para agregar una estructura nueva, suele crearse una regla similar.
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

// -------------------------------------------------------------------
// TIPOS DE DATOS
// -------------------------------------------------------------------
// Si agregas un tipo nuevo (por ejemplo long), normalmente:
// 1. agregas un token nuevo abajo en la parte lexica
// 2. lo agregas aca en tipo
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
// Esta regla define operadores, precedencia y literales.
// Si agregas:
// - un operador nuevo
// - una forma nueva de expresion
// - un literal nuevo
// probablemente se toca esta zona y tambien la parte lexica.
// Todo lo que conectes aca podra usarse dentro de otras expresiones,
// asignaciones, condiciones de if/while, argumentos, etc.
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
// LLAMADAS Y ARGUMENTOS
// -------------------------------------------------------------------
// Si cambia como se invocan funciones, se toca esta zona.
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
// TOKENS DE PUNTUACION Y DELIMITADORES
// -------------------------------------------------------------------
// Esta parte ya es del lexer.
// Si agregas simbolos nuevos como :, ++, --, etc., se tocan estas reglas.
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
// PALABRAS RESERVADAS
// -------------------------------------------------------------------
// Si agregas una keyword nueva como import, switch, case, do, etc.,
// normalmente se crea un token aca y luego se usa arriba en una regla
// sintactica.
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

// -------------------------------------------------------------------
// IDENTIFICADORES Y LITERALES
// -------------------------------------------------------------------
// Si el cambio es sobre numeros, strings, chars o formato de nombres,
// se toca esta zona del lexer.
ID        : [A-Za-z_] [A-Za-z0-9_]*;
DECIMAL   : [0-9]+ '.' [0-9]+;
INTEGER   : [0-9]+;
CHARACTER : '\'' (~['\\\r\n] | '\\' .) '\'';
CADENA    : '"' (~["\\\r\n] | '\\' .)* '"';

// -------------------------------------------------------------------
// ESPACIOS, COMENTARIOS Y ERRORES LEXICOS
// -------------------------------------------------------------------
// WS y comentarios se ignoran.
// OTRO captura cualquier caracter no reconocido y permite reportar
// errores lexicos en App.java.
COMENTARIO_LINEA  : '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE : '/*' .*? '*/' -> skip;
WS                : [ \r\n\t]+ -> skip;
OTRO              : .;
