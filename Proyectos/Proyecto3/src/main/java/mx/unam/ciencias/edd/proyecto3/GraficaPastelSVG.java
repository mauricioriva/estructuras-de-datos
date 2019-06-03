package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.util.Iterator;

/** Clase para hacer graficas de pastel en SVG */
public class GraficaPastelSVG {

    /* La lista de palabras para la grafica de pastel */
    Lista<Palabra> lista;

    /* El mayor numero de veces que aparece una palabra */
    int max;

    /* El total de repeticiones */
    int total;

    /**
     * Constructor, asigna cada elemento del diccionario a la lista.
     * @param d Diccionario con las 15 palabras mas frecuentes.
     */
    public GraficaPastelSVG(Diccionario<Palabra,Integer> d) {
        Lista<Palabra> l = new Lista<>();
        Iterator<Palabra> i = d.iteradorLlaves();
        while(i.hasNext()) {
            l.agrega(i.next());
        }
        this.lista = l;
        max = masFrecuente();
        total = total();
    }

    public String imprime() {

        String res = "<?xml version='1.0' encoding='UTF-8' ?>\n";

        res = res + "<svg width=\"2500\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\">\n";

        double ini = 0;
        double aux = 0;
        int color = 1;

        for (Palabra p : lista ) {
            if (color == 7) {
                color = 1;
            }
            aux = porciento(p.getNum()) + ini;
            res = res + rebanada(ini, aux, color);
            color++;
            ini = aux;
        }

        res = res + rebanada(ini,0.0,color);

        return res + "</svg>";

    }

    public String coordenadasPath(double ini, double fin) {
        double xini = 1250 + 200 * Math.cos( (Math.PI / 180) * ini);
        double yini = 225 + 200 * Math.sin( (Math.PI / 180 ) * ini);

        double xfin = 1250 + 200 * Math.cos( (Math.PI / 180) * fin);
        double yfin = 225 + 200 * Math.sin( (Math.PI / 180) * fin);

        String res = "M" + 1250 + ", " + 225 +
        " L" + xini + "," + yini +
        " A" + 200 + "," + 200 + " 0 0, 1 " + " " + xfin + "," + yfin + " z";

        return res;
    }

    public String rebanada(double ini, double fin, int color) {
        String res = "";
        if (color == 1)
            res = "\t<path d=\"" + coordenadasPath(ini, fin) + "\" fill = \"blue\" />\n";
        else if (color == 2)
            res = "\t<path d=\"" + coordenadasPath(ini, fin) + "\" fill = \"red\" />\n";
        else if (color == 3)
            res = "\t<path d=\"" + coordenadasPath(ini, fin) + "\" fill = \"green\" />\n";
        else if (color == 4)
            res = "\t<path d=\"" + coordenadasPath(ini, fin) + "\" fill = \"orange\" />\n";
        else if (color == 5)
            res = "\t<path d=\"" + coordenadasPath(ini, fin) + "\" fill = \"yellow\" />\n";
        else
            res = "\t<path d=\"" + coordenadasPath(ini, fin) + "\" fill = \"grey\" />\n";
        return res;
    }

    /**
     * Método que obtiene el porcentaje de la rebanada de acuerdo a la palabra.
     * @param  frecuencia El numero de veces que aparece la palabra.
     * @return            El porcentaje sobre 330.
     */
    public double porciento(int frecuencia) {
        double reglad3 = (frecuencia * 330)/total;
        return reglad3;
    }

    /**
     * Calcula el maximo de veces que puede aparecer una palabra en el archivo.
     * @return El maximo de veces de una palabra.
     */
    public int masFrecuente() {
        int c = 0;
        for (Palabra p : lista ) {
            if (p.getNum() > c)
                c = p.getNum();
        }
        return c;
    }

    /**
     * Metodo que calcula la suma total de veces que aparecem las
     * más frecuentes.
     * @return Sobre el total de palabras.
     */
    public int total() {
        int c = 0;
        for (Palabra p : lista ) {
            c = c + p.getNum();
        }
        return c;
    }

}
