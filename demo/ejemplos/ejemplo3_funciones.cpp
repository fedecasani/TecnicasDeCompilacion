int limiteGlobal;

int cuadrado(int x) {
    int resultado;
    resultado = x * x;
    return resultado;
}

double mitad(double valor) {
    double resultado;
    resultado = valor / 2;
    return resultado;
}

void actualizarLimite(int valor) {
    if (valor > limiteGlobal) {
        limiteGlobal = valor;
    } else {
        limiteGlobal = limiteGlobal + 1;
    }
}

int main() {
    int base;
    int valor;
    double escala;
    char marca;

    limiteGlobal = 4;
    base = 3;
    valor = cuadrado(base);
    escala = mitad(valor);
    marca = 'B';
    actualizarLimite(valor);

    if ((escala > 0) && (marca == 'B')) {
        valor = valor + limiteGlobal;
    } else {
        valor = valor - 1;
    }

    return valor;
}
