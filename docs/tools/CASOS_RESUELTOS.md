# Casos resueltos

## Caso 1. Agregar `import "math";`

### Que tocar

- `MiLenguaje.g4`
- `AnalizadorSemantico.java`
- opcional: `Simbolo.java`

### Idea

1. crear token `IMPORT`
2. crear regla `importacion`
3. conectarla en `elemento`
4. implementar `visitImportacion(...)`
5. declarar el simbolo en tabla

## Caso 2. Agregar `do while`

### Que tocar

- `MiLenguaje.g4`
- `AnalizadorSemantico.java`
- `GeneradorCodigo.java`

### Idea

1. token `DO`
2. regla `sentenciaDoWhile`
3. colgarla en `sentencia`
4. validar condicion `bool`
5. generar labels para cuerpo y reevaluacion

## Caso 3. Agregar `read(x);`

### Que tocar

- `MiLenguaje.g4`
- `AnalizadorSemantico.java`
- `GeneradorCodigo.java`

### Idea

1. token `READ`
2. regla `sentenciaRead : READ PA acceso PC PYC ;`
3. colgarla en `sentencia`
4. validar que el acceso exista y sea asignable
5. emitir `READ x`

## Caso 4. Agregar `++`

### Que tocar

- `MiLenguaje.g4`
- `AnalizadorSemantico.java`
- `GeneradorCodigo.java`

### Idea

1. token `INCREMENTO`
2. decidir si es sentencia, expresion o ambas
3. validar que se aplique a numericos
4. generar algo tipo:

```text
t1 = x + 1
x = t1
```

## Caso 5. Agregar `const`

### Que tocar

- `MiLenguaje.g4`
- `AnalizadorSemantico.java`
- `Simbolo.java`

### Idea

1. token `CONST`
2. extender la declaracion
3. marcar el simbolo como constante
4. prohibir reasignacion posterior

## Caso 6. Agregar una columna `inicializado`

### Que tocar

- `Simbolo.java`
- `TablaSimbolos.java`
- `AnalizadorSemantico.java`

### Idea

1. agregar flag en `Simbolo`
2. imprimir columna
3. marcar en declaraciones y asignaciones
4. usarla en warnings o errores
