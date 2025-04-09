# 🚀 Compilador TC25 - Proyecto de Técnicas de Compilación

## ⚙️ Configuración Inicial del Proyecto

### 🏗️ Creación del Proyecto Maven
Para desarrollar nuestro compilador, comenzamos creando la estructura básica del proyecto con Maven, que facilitará la gestión de dependencias y el ciclo de vida de construcción.

```bash
mvn org.apache.maven.plugins:maven-archetype-plugin:3.1.2:generate \
    -DarchetypeArtifactId="maven-archetype-quickstart" \
    -DarchetypeGroupId="org.apache.maven.archetypes" \
    -DarchetypeVersion="1.4" \
    -DgroupId="com.compilador" \
    -DartifactId="demo"
```

### 🔧 Configuraciones durante la ejecución:

- 📦 `groupId`: `com.compilador`  
- 📂 `artifactId`: `demo`  
- 🔢 `version`: `1.0`  
- 📁 `package`: `com.compilador`  

Esto genera la siguiente estructura de directorios:

```
📁 demo/
├── 📜 pom.xml
├── 📂 src/
│   ├── 📂 main/
│   │   └── 📂 java/
│   │       └── 📂 com/
│   │           └── 📂 compilador/
│   │               └── 📄 App.java
│   └── 📂 test/
│       └── 📂 java/
│           └── 📂 com/
│               └── 📂 compilador/
│                   └── 📄 AppTest.java
```

---

## 🛠️ Configuración de ANTLR para el Análisis Léxico

### 1️⃣ Modificación del `pom.xml`

Añadimos las siguientes dependencias y plugins:

```xml
<properties>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <maven.compiler.source>1.8</maven.compiler.source>
  <maven.compiler.target>1.8</maven.compiler.target>
  <antlr.version>4.9.3</antlr.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.antlr</groupId>
    <artifactId>antlr4-runtime</artifactId>
    <version>${antlr.version}</version>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.antlr</groupId>
      <artifactId>antlr4-maven-plugin</artifactId>
      <version>${antlr.version}</version>
      <executions>
        <execution>
          <goals>
            <goal>antlr4</goal>
          </goals>
        </execution>
      </executions>
      <configuration>
        <sourceDirectory>${basedir}/src/main/antlr4</sourceDirectory>
        <outputDirectory>${basedir}/src/main/java</outputDirectory>
        <visitor>true</visitor>
        <listener>true</listener>
      </configuration>
    </plugin>
    
    <plugin>
      <artifactId>maven-assembly-plugin</artifactId>
      <configuration>
        <archive>
          <manifest>
            <mainClass>com.compilador.App</mainClass>
          </manifest>
        </archive>
        <descriptorRefs>
          <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
      </configuration>
      <executions>
        <execution>
          <id>make-assembly</id>
          <phase>package</phase>
          <goals>
            <goal>single</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

---

### 2️⃣ Crear estructura para archivos ANTLR

```bash
mkdir -p src/main/antlr4/com/compilador
```

---

### 3️⃣ Crear archivo de gramática `MiniLenguaje.g4`

```antlr
grammar MiniLenguaje;

program : token* EOF ;
token   : ID | INTEGER | STRING | KEYWORD | OPERATOR | SEPARATOR ;

ID          : [a-zA-Z][a-zA-Z0-9_]* ;
INTEGER     : [0-9]+ ;
STRING      : '"' (~["\r\n] | '\"')* '"' ;
BOOLEAN     : 'true' | 'false' ;

KEYWORD     : 'var' | 'if' | 'else' | 'print' | 'while' | 'function' | 'return' ;

OPERATOR    : '+' | '-' | '*' | '/' | '%' | '=' | '==' | '!=' | '<' | '>' | '<=' | '>=' | '&&' | '||' | '!' ;

SEPARATOR   : ';' | '(' | ')' | '{' | '}' | ',' | '.' ;

WS          : [ \t\r\n]+ -> skip ;
COMMENT     : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT : '/*' .*? '*/' -> skip ;
```

---

## 📊 Gramática Léxica Mejorada

```antlr
grammar MiLenguaje;

programa : (token)* EOF ;

token : PA | PC | CA | CC | LA | LC | PYC | COMA | IGUAL | MAYOR | MAYOR_IGUAL 
      | MENOR | MENOR_IGUAL | EQL | DISTINTO | SUM | RES | MUL | DIV | MOD
      | OR | AND | NOT | FOR | WHILE | IF | ELSE | INT | CHAR | DOUBLE | VOID
      | RETURN | ID | INTEGER | DECIMAL | CHARACTER | OTRO ;

fragment LETRA : [A-Za-z];
fragment DIGITO : [0-9];

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
MAYOR_IGUAL: '>=' ;
MENOR  : '<' ;
MENOR_IGUAL: '<=' ;
EQL  : '==' ;
DISTINTO  : '!=' ;

SUM  : '+' ;
RES  : '-' ;
MUL  : '*' ;
DIV  : '/' ;
MOD  : '%' ;

OR   : '||' ;
AND  : '&&' ;
NOT  : '!' ;

FOR  : 'for' ;
WHILE: 'while' ;

IF   : 'if' ;
ELSE : 'else' ;

INT     : 'int' ;
CHAR    : 'char' ;
DOUBLE  : 'double' ;
VOID    : 'void' ;

RETURN : 'return' ;

ID : (LETRA | '_') (LETRA | DIGITO | '_')* ;

INTEGER : DIGITO+ ;
DECIMAL : INTEGER '.' INTEGER ;
CHARACTER: '\'' (~['\r\n] | '\\' .) '\'' ;

COMENTARIO_LINEA : '//' ~[\r\n]* -> skip ;
COMENTARIO_BLOQUE : '/*' .*? '*/' -> skip ;

WS : [ \r\n\t] -> skip ;
OTRO : . ;
```

---

## 💡 Características añadidas

- 🔡 Soporte para literales de tipo decimal
- 📝 Soporte para caracteres con comillas simples
- 🔄 Operadores de comparación extendidos
- 🧮 Operadores lógicos (`&&`, `||`)
- 📌 Nuevas palabras clave: `VOID`, `INT`, `CHAR`, `DOUBLE`, `RETURN`
- 💬 Manejo completo de comentarios

---

## 📝 Ejemplo Actualizado

### 📌 Código de entrada

```java
// Variables con diferentes tipos de datos
int numero = 42;
double pi = 3.14159;
String mensaje = "Hola, mundo!";
char letra = 'A';
boolean condicion = true;

// Estructura de control if-else
if (numero > 10) {
    System.out.println(mensaje);
} else {
    System.out.println("Número pequeo");
}
```

### 📊 Salida del Análisis Léxico

```
Análisis léxico completado.
SEPARATOR            (                              5          3
ID                   x                              5          4
OPERATOR             >                              5          6
INTEGER              5                              5          8
SEPARATOR            )                              5          9
SEPARATOR            {                              5          11
ID                   print                          6          4
ID                   mensaje                        6          10
SEPARATOR            ;                              6          17
SEPARATOR            }                              7          0

Análisis léxico completado.
```

---

## 🚀 ¡Hora de Compilar y Ejecutar!

### 📦 Compilar el proyecto

```bash
mvn clean package
```

### ▶️ Ejecutar el compilador

```bash
mvn package assembly:single
java -jar target/demo-1.0-jar-with-dependencies.jar ejemplo.txt   
java -jar target/demo-1.0-jar-with-dependencies.jar ejemplo_error.txt
```


## 📤 Salida por Consola del Análisis Léxico

```
Analizando archivo: ejemplo.txt

=== ANÁLISIS LÉXICO ===
TIPO                 LEXEMA                         LÍNEA      COLUMNA   
-------------------------------------------------------------------
INT                  int                            3          0
ID                   numero                         3          4
IGUAL                =                              3          11
INTEGER              42                             3          13
PYC                  ;                              3          15
DOUBLE               double                         4          0
ID                   pi                             4          7
IGUAL                =                              4          10
DECIMAL              3.14159                        4          12
PYC                  ;                              4          19
ID                   String                         5          0
ID                   mensaje                        5          7
IGUAL                =                              5          15
HOLA_MUNDO           "Hola, mundo!"                 5          17
PYC                  ;                              5          31
CHAR                 char                           6          0
ID                   letra                          6          5
IGUAL                =                              6          11        
CHARACTER            'A'                            6          13
PYC                  ;                              6          16
ID                   boolean                        7          0
ID                   condicion                      7          8
IGUAL                =                              7          18
ID                   true                           7          20
PYC                  ;                              7          24
IF                   if                             10         0
PA                   (                              10         3
ID                   numero                         10         4
MAYOR                >                              10         11
INTEGER              10                             10         13
PC                   )                              10         15        
LA                   {                              10         17
ID                   System                         11         4
OTRO                 .                              11         10
ID                   out                            11         11
OTRO                 .                              11         14
ID                   println                        11         15
PA                   (                              11         22
ID                   mensaje                        11         23
PC                   )                              11         30
PYC                  ;                              11         31
LC                   }                              12         0
ELSE                 else                           12         2
LA                   {                              12         7
ID                   System                         13         4
OTRO                 .                              13         10
ID                   out                            13         11
OTRO                 .                              13         14
ID                   println                        13         15
PA                   (                              13         22
OTRO                 "                              13         23
ID                   N                              13         24
OTRO                 ú                              13         25
ID                   mero                           13         26
ID                   pequeo                         13         31
OTRO                 "                              13         37
PC                   )                              13         38
PYC                  ;                              13         39
LC                   }                              14         0

? Análisis léxico completado sin errores.
```
