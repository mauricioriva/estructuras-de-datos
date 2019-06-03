package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;

/** Clase para generar graficas en SVG */
public class GraficaSVG {

    Lista<Conjunto<Palabra>> lista;

    public GraficaSVG(Lista<Organizador> l) {
        this.lista = new Lista<Conjunto<Palabra>>();
        for (Organizador o : l) {
            lista.agrega(o.getContenido().getConjunto());
        }
    }

    public String imprime() {

        String res = "<?xml version='1.0' encoding='UTF-8' ?>\n";

        res = res + "<svg width=\"2500\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n";

        return res + "</svg>";

    }

}
