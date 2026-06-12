int avanzar(int inicio, int pasos) {
    int i;
    int valor;

    i = 0;
    valor = inicio;
    while (i < pasos) {
        valor = valor + 1;
        i = i + 1;
    }
    return valor;
}

int main() {
    int i;
    int total;
    char estado;

    total = 0;
    estado = 'N';

    for (i = 0; i < 6; i = i + 1) {
        if (i == 2) {
            continue;
        }

        if (i > 4) {
            break;
        }

        total = total + i;
    }

    total = avanzar(total, 2);

    if ((total > 0) && (estado == 'N')) {
        total = total + 1;
    } else {
        total = total - 1;
    }

    return total;
}
