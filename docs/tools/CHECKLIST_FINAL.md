# Checklist rapido para final

## Si el profe pide agregar algo nuevo

1. Identificar si es global, sentencia o expresion
2. Agregar token si hace falta
3. Crear regla en `MiLenguaje.g4`
4. Conectarla en el lugar correcto
5. Regenerar ANTLR
6. Buscar el `visit...` nuevo
7. Tocar semantico si debe validar o guardar algo
8. Tocar tabla si debe verse en simbolos
9. Tocar generador si debe producir codigo intermedio
10. Probar con un ejemplo minimo

## Si aparece un error raro

- revisar nombre del `Context`
- revisar si la regla del parser tiene el mismo nombre que el `visit...`
- revisar si el token nuevo termina con `;`
- revisar si la regla nueva fue conectada en `elemento`, `sentencia` o `expresion`

## Si no aparece en tabla

- el parser no la carga solo
- falta `tabla.declarar(...)` en el semantico

## Si no genera codigo

- falta un `visit...` en `GeneradorCodigo.java`
- o la construccion se parsea pero nadie la baja a tres direcciones
