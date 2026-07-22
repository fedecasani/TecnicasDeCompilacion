# Plantillas de semantico

## 1. Registrar una construccion nueva en tabla

```java
@Override
public String visitImportacion(MiLenguajeParser.ImportacionContext ctx) {
    String cadena = ctx.CADENA().getText();
    String nombre = cadena.substring(1, cadena.length() - 1);

    Simbolo simbolo = simbolo(
            nombre,
            "import",
            Simbolo.Categoria.IMPORT,
            ctx,
            ambito,
            null,
            null);

    if (!tabla.declarar(simbolo)) {
        error(ctx, "El import '" + nombre + "' ya esta declarado");
    }

    return null;
}
```

## 2. Validar que una condicion sea booleana

```java
String tipoCondicion = visit(ctx.expresion());
if (!"bool".equals(tipoCondicion)) {
    error(ctx, "La condicion debe ser bool");
}
```

## 3. Validar una asignacion

```java
String tipoValor = visit(ctx.expresion());
validarAsignacion(ctx, tipoDestino, tipoValor);
```

## 4. Validar llamada a funcion

```java
Simbolo funcion = tabla.buscar(ctx.ID().getText(), ambito);
if (funcion == null || funcion.getCategoria() != Simbolo.Categoria.FUNCION) {
    error(ctx, "Funcion '" + ctx.ID().getText() + "' no declarada");
}
```

## 5. Marcar warning por variable no usada

```java
if (simbolo.getCategoria() == Simbolo.Categoria.VARIABLE
        && !"global".equals(simbolo.getAmbito())
        && !simbolo.isUsado()) {
    resultado.warning("Variable '" + simbolo.getNombre()
            + "' declarada pero nunca utilizada");
}
```

## 6. Validar `break` o `continue` dentro de bucles

Idea:

- llevar un contador o booleano `enBucle`
- cuando entras a `while` o `for`, activarlo
- cuando salis, restaurarlo
- si aparece `break` o `continue` y `enBucle == false`, error

Ejemplo:

```java
private int profundidadBucles = 0;

@Override
public String visitSentenciaWhile(MiLenguajeParser.SentenciaWhileContext ctx) {
    String tipoCondicion = visit(ctx.expresion());
    if (!"bool".equals(tipoCondicion)) {
        error(ctx, "La condicion del while debe ser bool");
    }
    profundidadBucles++;
    visit(ctx.bloque());
    profundidadBucles--;
    return null;
}
```

## 7. Exigir `main`

Idea:

- al terminar `analizar(...)`, buscar si existe una funcion `main`
- si no existe, registrar error

```java
Simbolo main = tabla.buscar("main", "global");
if (main == null || main.getCategoria() != Simbolo.Categoria.FUNCION) {
    resultado.error("No se encontro la funcion main");
}
```

## 8. Exigir que `main` retorne `int`

```java
Simbolo main = tabla.buscar("main", "global");
if (main != null && !"int".equals(main.getTipo())) {
    resultado.error("La funcion main debe retornar int");
}
```

## 9. Validar import duplicado

Ya queda cubierto si haces:

```java
if (!tabla.declarar(simboloImport)) {
    error(ctx, "El import '" + nombre + "' ya esta declarado");
}
```

## 10. Validar variable usada sin inicializar

Idea:

- agregar un campo `inicializado` a `Simbolo`
- marcarlo en declaraciones con valor y en asignaciones
- al leer una variable, si no esta inicializada, warning o error

## 11. Validar constantes que no se pueden reasignar

Idea:

- crear categoria o modificador `CONSTANTE`
- cuando se intente asignar a ese simbolo, error

## 7. Regla mental

- El parser reconoce la forma
- El semantico le da significado
- La tabla de simbolos se llena manualmente desde el semantico
