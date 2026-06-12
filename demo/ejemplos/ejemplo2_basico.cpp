int main() {
    int numero;
    int otro;
    double promedio;
    char letra;

    numero = 8;
    otro = 2;
    promedio = numero + otro;
    letra = 'A';

    if ((numero > otro) && (promedio > 0)) {
        if (letra == 'A') {
            numero = numero + otro;
        } else {
            numero = numero - otro;
        }
    } else {
        numero = numero - 1;
    }

    return numero;
}
