# Errores comunes

## 1. Agregar una regla y olvidarte de conectarla

Creaste `importacion`, pero si no la agregas a `elemento` o `sentencia`,
el parser nunca la va a usar.

## 2. Tocar el `g4` y no regenerar ANTLR

Consecuencia:

- el `Context` nuevo no aparece
- el `visit...` nuevo no existe

## 3. Poner mal el nombre del visitor

La regla:

```antlr
importacion
```

genera:

```java
visitImportacion(...)
```

No sale del token `IMPORT`.

## 4. Usar el `Context` equivocado

Error tipico:

```java
public String visitImportacion(MiLenguajeParser.FuncionContext ctx)
```

Deberia ser:

```java
public String visitImportacion(MiLenguajeParser.ImportacionContext ctx)
```

## 5. Pensar que el parser llena la tabla solo

No.

- el parser reconoce la sintaxis
- el semantico decide que guardar en la tabla

## 6. Crear el simbolo pero no declararlo

Esto no alcanza:

```java
Simbolo s = simbolo(...);
```

Falta:

```java
tabla.declarar(s);
```

## 7. Agregar una construccion y olvidar el generador

Puede parsear y validar bien, pero no emitir nada en codigo intermedio.

## 8. Agregar un token abajo de `ID`

En lexer, el orden importa. Si una keyword queda mal ubicada,
puede ser comida por `ID`.

## 9. No pensar donde va la regla

- `elemento` = global
- `sentencia` = bloque
- `expresion` = dentro de expresiones

## 10. No probar con un caso minimo

Siempre conviene un ejemplo chico, no uno enorme, para aislar errores.

## 11. Meter una categoria rara en tabla

Si usas `RETURN` para un import, la tabla imprime cualquier cosa.
Conviene crear `IMPORT`.

## 12. Creer que todo cambio pide tocar todo

No siempre.

- a veces alcanza con `g4`
- a veces `g4 + semantico`
- a veces `g4 + semantico + generador`
