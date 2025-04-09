grammar MiLenguaje;

// Regla parser mínima (requerida por ANTLR)
programa : (token)* EOF ;
token : PA | PC | CA | CC | LA | LC | PYC | COMA | IGUAL | MAYOR | MAYOR_IGUAL 
      | MENOR | MENOR_IGUAL | EQL | DISTINTO | SUM | RES | MUL | DIV | MOD
      | OR | AND | NOT | FOR | WHILE | IF | ELSE | INT | CHAR | DOUBLE | VOID
      | RETURN | ID | INTEGER | DECIMAL | CHARACTER | OTRO 
      ;

fragment LETRA : [A-Za-z];
fragment DIGITO : [0-9];

// TOKENS 
PA   : '(' ;
PC   : ')' ;
CA   : '[' ;
CC   : ']' ;
LA   : '{' ;
LC   : '}' ;

PYC  : ';' ;
COMA : ',' ;

IGUAL : '=' ;

MAYOR  : '>' ;
MAYOR_IGUAL: '>=';
MENOR  : '<' ;
MENOR_IGUAL: '<=';
EQL  : '==';
DISTINTO  : '!=';

SUM  : '+' ;
RES  : '-' ;
MUL  : '*' ;
DIV  : '/' ;
MOD  : '%' ;

OR   : '||' ;
AND  : '&&' ;
NOT  : '!'  ;

FOR  : 'for';
WHILE: 'while';

IF   : 'if' ;
ELSE : 'else' ;

INT     : 'int' ;
CHAR    : 'char' ;
DOUBLE  : 'double' ;
VOID    : 'void' ;

RETURN : 'return';

ID : (LETRA | '_') (LETRA | DIGITO | '_')*;

INTEGER : DIGITO+;
DECIMAL : INTEGER'.'INTEGER;
CHARACTER: '\'' (~['\r\n] | '\\' .) '\'' ;

// Comentarios - Se ignoran durante el análisis
//COMENTARIO_LINEA : '//' ~[\r\n]*; sin ocultar
COMENTARIO_LINEA : '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE : '/*' .*? '*/' -> skip;


HOLA_MUNDO :  '"Hola, mundo!"';
WS : [ \r\n\t] -> skip ;
OTRO : . ;