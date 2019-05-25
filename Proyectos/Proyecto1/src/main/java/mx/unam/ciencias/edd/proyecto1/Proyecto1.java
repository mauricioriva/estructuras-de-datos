package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.proyecto1.Banderas;

public class Proyecto1 {
    public static void main(String[] args) {
        run(args);
    }

    public static void run(String[] args) {
        Banderas b = new Banderas(args);
        TipoEntrada tp = new TipoEntrada(b);
        ArmaFlujos af = new ArmaFlujos(tp);
        Estructura e = new Estructura(af);
        if (b.bandera_o) {
            imprimeArchivo(b, e, af);
        } else {
            imprimePantalla(b, e);
        }
    }

    public static void imprimePantalla(Banderas b, Estructura e) {
        if (b.bandera_r && !b.bandera_o) {
            e.lista = e.lista.reversa();
        }
        for (Cadena c : e.lista) {
            System.out.println(c);
        }
    }

    public static void imprimeArchivo(Banderas b, Estructura e, ArmaFlujos af) {
        if (b.bandera_o && b.bandera_r) {
            af.EscribirArchivo(e.lista.reversa(), b.nombreArchivo);
        } else if (b.bandera_o) {
            af.EscribirArchivo(e.lista, b.nombreArchivo);
        }
    }

}