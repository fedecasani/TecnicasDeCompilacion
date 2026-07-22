# Plantillas de tabla de simbolos

## Idea general

La tabla de simbolos guarda informacion semantica del programa:

- nombre
- tipo
- categoria
- linea
- columna
- ambito
- detalles

## Categorias utiles

```java
public enum Categoria {
    VARIABLE,
    PARAMETRO,
    FUNCION,
    IMPORT,
    CONSTANTE
}
```

## Si queres una columna `usado`

### En `Simbolo.java`

```java
private boolean usado;
public boolean isUsado() { return usado; }
public void marcarUsado() { usado = true; }
```

### En `TablaSimbolos.java`

Agregar la columna al `printf`.

## Si queres una columna `inicializado`

### En `Simbolo.java`

```java
private boolean inicializado;
public boolean isInicializado() { return inicializado; }
public void marcarInicializado() { inicializado = true; }
```

### En el semantico

- marcar inicializado en declaraciones con valor
- marcar inicializado en asignaciones
- chequear al leer una variable

## Si queres mostrar mejor los detalles

### Funcion

```java
if (categoria == Categoria.FUNCION) {
    return "[private] " + parametros;
}
```

### Array

```java
if (tamanioArray != null) {
    return "[arr:" + tamanioArray + "] [private]";
}
```

### Import

```java
if (categoria == Categoria.IMPORT) {
    return "[modulo]";
}
```

### Constante

```java
if (categoria == Categoria.CONSTANTE) {
    return "[const]";
}
```

## Agregar una categoria nueva

Pasos:

1. agregarla al enum
2. usarla desde el semantico al crear el simbolo
3. si hace falta, personalizar `detalles()`
4. revisar la impresion de la tabla
