## Tools de examen

Esta carpeta es un kit de apoyo para cambios rapidos sobre el compilador.
No reemplaza a los archivos reales de `demo/src/...`: esta pensada como
referencia para estudiar, practicar y copiar una base si el profe pide
agregar una construccion nueva en el final.

### Archivos incluidos

- `MiLenguaje.g4`: copia comentada de la gramatica con puntos clave para
  agregar keywords, sentencias, tipos, bucles y expresiones.
- `AnalizadorSemantico.java`: copia del semantico con ejemplos del flujo
  tipico para registrar simbolos y validar reglas semanticas.
- `GeneradorCodigo.java`: copia comentada de la generacion de codigo
  intermedio en tres direcciones.
- `Optimizador.java`: pipeline de optimizaciones con explicacion del orden.
- `Simbolo.java`: ejemplo de categorias de simbolos, incluyendo `IMPORT`.
- `TablaSimbolos.java`: ejemplo de como se guarda e imprime la tabla.
- `GUIA_CAMBIOS_RAPIDOS.md`: checklist corto para cambios comunes.
- `CASOS_DE_EXAMEN.md`: listado grande de cambios probables y que archivo
  tocar en cada caso.
- `PLANTILLAS_G4.md`: recetas cortas para tocar la gramatica sin perderse.
- `PLANTILLAS_CODIGO_INTERMEDIO.md`: ejemplos de como bajar construcciones
  del AST a codigo intermedio.
- `PLANTILLAS_SEMANTICO.md`: esqueletos de visitors y chequeos semanticos.
- `PLANTILLAS_TABLA_SIMBOLOS.md`: ideas para categorias, columnas y detalles.
- `CHECKLIST_FINAL.md`: orden corto para resolver rapido en examen.
- `EJEMPLOS_MINIMOS.md`: programitas chicos para probar cada agregado.
- `ERRORES_COMUNES.md`: fallas tipicas al tocar lexer, parser o visitor.
- `MAPA_ARCHIVOS.md`: guia rapida de "si pide X, toca Y".
- `CASOS_RESUELTOS.md`: mini cambios resueltos paso a paso.
- `FAQ_FINAL.md`: preguntas y respuestas cortas para defender el trabajo.
- `CASOS_REGEX.md`: tokens listos para copiar si te piden una regex.
- `optimizaciones/`: copias de las optimizaciones actuales como apoyo.

### Regla mental rapida

Si el cambio pedido es...

- Una keyword o una sintaxis nueva: tocar `MiLenguaje.g4`
- Una validacion o una carga en la tabla: tocar `AnalizadorSemantico.java`
- Una categoria nueva en tabla: tocar `Simbolo.java`
- Una columna o formato de impresion: tocar `TablaSimbolos.java`

### Ejemplos de cambios tipicos

- Agregar `import "math";`
- Permitir algo solo global: colgarlo de `elemento`
- Permitir algo dentro de bloques: colgarlo de `sentencia`
- Agregar un tipo nuevo como `long`
- Agregar un bucle nuevo como `do while`
- Registrar una construccion nueva en la tabla de simbolos
- Emitir codigo intermedio para una construccion nueva
- Ajustar el pipeline de optimizacion si aparece una instruccion nueva
- Resolver rapido si el pedido cae en lexer, parser, semantico o generador

### Consejo practico

En examen, pensa siempre en este orden:

1. Token o regla nueva en `g4`
2. Donde se conecta esa regla (`elemento`, `sentencia`, `expresion`)
3. Regenerar ANTLR
4. Visitor semantico (`visit...`) si hace falta validar o guardar algo
5. Ajustar tabla/categorias si queres que aparezca prolijo en consola
