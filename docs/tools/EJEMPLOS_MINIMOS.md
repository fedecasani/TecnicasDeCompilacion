# Ejemplos minimos de prueba

## Declaracion

```cpp
int x;
```

## Declaracion con inicializacion

```cpp
int x = 5;
```

## Import

```cpp
import "math";
int x = 5;
```

## If

```cpp
int main() {
    int x;
    x = 1;
    if (x > 0) {
        x = 2;
    }
    return x;
}
```

## While

```cpp
int main() {
    int x;
    x = 0;
    while (x < 3) {
        x = x + 1;
    }
    return x;
}
```

## For

```cpp
int main() {
    int suma;
    suma = 0;
    for (int i = 0; i < 3; i = i + 1) {
        suma = suma + i;
    }
    return suma;
}
```

## Break

```cpp
int main() {
    int x;
    x = 0;
    while (true) {
        break;
    }
    return x;
}
```

## Continue

```cpp
int main() {
    int x;
    x = 0;
    while (x < 3) {
        x = x + 1;
        continue;
    }
    return x;
}
```

## Llamada a funcion

```cpp
int sumar(int a, int b) {
    return a + b;
}

int main() {
    int x;
    x = sumar(2, 3);
    return x;
}
```

## Cout

```cpp
int main() {
    int x;
    x = 5;
    cout << x;
    return x;
}
```
