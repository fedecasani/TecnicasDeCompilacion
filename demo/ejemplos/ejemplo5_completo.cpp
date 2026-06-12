double dividir(double a, double b) {
    double resultado;
    resultado = a / b;
    return resultado;
}

int acumular(int limite) {
    int i;
    int suma;

    i = 0;
    suma = 0;
    while (i < limite) {
        suma = suma + i;
        i = i + 1;
    }
    return suma;
}

void ajustar(int valor) {
    int copia;

    copia = valor;
    if ((copia > 5) && (copia < 20)) {
        copia = copia + 1;
    } else {
        copia = copia - 1;
    }
}

int main() {
    int total;
    int indice;
    double promedio;
    char nivel;

    total = acumular(5);
    promedio = dividir(total, 2);
    nivel = 'C';

    for (indice = 0; indice < 4; indice = indice + 1) {
        total = total + indice;
    }

    ajustar(total);

    if ((promedio > 0) || (nivel == 'Z')) {
        total = total + 2;
    } else {
        total = total - 2;
    }

    return total;
}
