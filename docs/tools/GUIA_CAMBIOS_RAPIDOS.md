# Guia de cambios rapidos

## 1. Agregar una keyword global como `import`

### En `MiLenguaje.g4`

1. Crear el token:

```antlr
IMPORT : 'import';
```

2. Crear la regla del parser:

```antlr
importacion
    : IMPORT CADENA PYC
    ;
```

3. Conectarla en `elemento` si queres usarla a nivel global:

```antlr
elemento
    : funcion
    | declaracion
    | importacion
    ;
```

4. Si tambien queres permitirla dentro de bloques, agregarla en `sentencia`.

### En el semantico

Implementar:

```java
@Override
public String visitImportacion(MiLenguajeParser.ImportacionContext ctx) {
    String cadena = ctx.CADENA().getText();
    String nombreModulo = cadena.substring(1, cadena.length() - 1);

    Simbolo simboloImport = simbolo(
            nombreModulo,
            "import",
            Simbolo.Categoria.IMPORT,
            ctx,
            ambito,
            null,
            null);

    if (!tabla.declarar(simboloImport)) {
        error(ctx, "El import '" + nombreModulo + "' ya esta declarado");
    }

    return null;
}
```

## 2. Agregar un tipo nuevo como `long`

### En `MiLenguaje.g4`

1. Agregar token:

```antlr
LONG : 'long';
```

2. Agregarlo en la regla `tipo`:

```antlr
tipo
    : INT
    | FLOAT
    | DOUBLE
    | CHAR
    | STRING_TYPE
    | BOOL
    | VOID
    | LONG
    ;
```

### En el semantico

- Actualizar `esNumerico(...)` si corresponde
- Actualizar compatibilidades si queres permitir conversiones

## 3. Agregar un bucle nuevo como `do while`

### En `MiLenguaje.g4`

1. Agregar token:

```antlr
DO : 'do';
```

2. Crear la regla:

```antlr
sentenciaDoWhile
    : DO bloque WHILE PA expresion PC PYC
    ;
```

3. Colgarla de `sentencia`:

```antlr
sentencia
    : declaracion
    | asignacion
    | llamada PYC
    | sentenciaCout
    | sentenciaIf
    | sentenciaWhile
    | sentenciaFor
    | sentenciaDoWhile
    | sentenciaReturn
    | BREAK PYC
    | CONTINUE PYC
    | bloque
    ;
```

### En el semantico

- Validar que la condicion sea `bool`
- Si el lenguaje maneja contexto de bucles, registrar que `break` y `continue`
  siguen siendo validos dentro de esa nueva estructura

## 4. Agregar algo que deba aparecer en la tabla

Tenes que tocar el semantico. El parser solo reconoce la sintaxis.
La tabla no se llena sola.

Pasos:

1. Crear la regla en `g4`
2. Regenerar ANTLR
3. Implementar `visit...(...)`
4. Crear el simbolo
5. Llamar a `tabla.declarar(...)`

## 5. Cuando tocar cada archivo

- `MiLenguaje.g4`: forma del lenguaje
- `AnalizadorSemantico.java`: significado y validacion
- `Simbolo.java`: categoria o detalle del simbolo
- `TablaSimbolos.java`: impresion o nuevas columnas

## 6. Recordatorio de nombres

El nombre del metodo visitor sale del nombre de la regla del parser:

- regla `importacion` -> `visitImportacion`
- regla `sentenciaDoWhile` -> `visitSentenciaDoWhile`
- regla `sentenciaSwitch` -> `visitSentenciaSwitch`

No sale del token.
