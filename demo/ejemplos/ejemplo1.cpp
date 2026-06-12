int globalContador;
double promedioGlobal;
char letraGlobal;
bool activoGlobal;
string mensajeGlobal;

int sumar(int a, int b) {
    int resultado;
    resultado = a + b;
    globalContador = globalContador + 1;
    return resultado;
}

double dividir(double a, double b) {
    double r;
    r = a / b;
    return r;
}

void mostrarEstado(int valor, bool activo) {
    if (activo && (valor > 0)) {
        cout << valor << activo;
    } else {
        cout << valor << activo;
    }
}

int main() {
    int i;
    int total;
    int datos[5];
    double promedio;
    char inicial;
    bool encontrado;
    string titulo;

    globalContador = 0;
    promedioGlobal = 0.0;
    letraGlobal = 'Z';
    activoGlobal = true;
    mensajeGlobal = "inicio";

    total = 0;
    inicial = 'A';
    encontrado = false;
    titulo = "prueba";
    cout << inicial << titulo;

    datos[0] = 10;
    datos[1] = 20;
    datos[2] = 30;
    datos[3] = 40;
    datos[4] = 50;

    i = 0;
    while (i < 5) {
        total = total + datos[i];
        if (datos[i] == 30) {
            encontrado = true;
        }
        i = i + 1;
    }

    promedio = dividir(total, 5);
    promedioGlobal = promedio;

    for (i = 0; i < 5; i = i + 1) {
        if (i == 1) {
            continue;
        }

        if (i > 3) {
            break;
        }

        total = sumar(total, i);
    }

    if (encontrado || (total > 0)) {
        mostrarEstado(total, activoGlobal);
    } else {
        cout << total << promedio;
    }

    return total;
}
