grammar MiLenguaje;
// Reglas del parser (simplificadas)
program : token* EOF ;
token   : ID | INTEGER | STRING | KEYWORD | OPERATOR | SEPARATOR ;

// Lexer rules (reglas léxicas)
// Identificadores
ID          : [a-zA-Z][a-zA-Z0-9_]* ;

// Literales
INTEGER     : [0-9]+ ('L'|'l')? ;
FLOAT       : [0-9]+ '.' [0-9]* ('f'|'F'|'d'|'D')? | '.' [0-9]+ ('f'|'F'|'d'|'D')? | [0-9]+ ('f'|'F'|'d'|'D') ;
STRING      : '"' (~["\r\n] | '\\"')* '"' ;
CHAR        : '\'' (~['\r\n\\] | '\\' .) '\'' ;
BOOLEAN     : 'true' | 'false' ;
NULL        : 'null' ;

// Palabras clave básicas de Java
KEYWORD     : 'class' | 'public' | 'private' | 'protected' | 'static' 
            | 'void' | 'int' | 'boolean' | 'String' | 'char' | 'float' | 'double'
            | 'if' | 'else' | 'for' | 'while' | 'do' | 'switch' | 'case' | 'break'
            | 'return' | 'new' | 'this' | 'super' | 'import' | 'package' ;

// Operadores de Java
OPERATOR    : '+' | '-' | '*' | '/' | '%'                      // Aritméticos
            | '=' | '+=' | '-=' | '*=' | '/=' | '%='           // Asignación
            | '++' | '--'                                      // Incremento/Decremento
            | '==' | '!=' | '<' | '>' | '<=' | '>='            // Comparación
            | '&&' | '||' | '!'                                // Lógicos
            | '&' | '|' | '^' | '~' | '<<' | '>>' | '>>>'      // Bit a bit
            | '&=' | '|=' | '^=' | '<<=' | '>>=' | '>>>='      // Asignación bit a bit
            | '?' | ':'                                        // Ternario
            | 'instanceof'                                     // Tipo
            ;

// Separadores de Java
SEPARATOR   : ';' | '(' | ')' | '{' | '}' | '[' | ']' | ',' | '.' | '@' ;

// Ignorar espacios en blanco y comentarios
WS          : [ \t\r\n]+ -> skip ;
COMMENT     : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT : '/*' .*? '*/' -> skip ;