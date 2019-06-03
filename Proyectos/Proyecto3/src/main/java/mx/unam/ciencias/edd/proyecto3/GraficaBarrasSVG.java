package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;
import java.util.Iterator;

/** Clase para hacer graficas de barra en SVG */
public class GraficaBarrasSVG {

    /* La lista de palabras para la grafica de barras */
    Lista<Palabra> lista;

    /* El mayor numero de veces que aparece una palabra */
    int max;

    /**
     * Constructor, asigna cada elemento del diccionario a la lista.
     * @param d Diccionario con las 15 palabras mas frecuentes.
     */
    public GraficaBarrasSVG(Diccionario<Palabra,Integer> d) {
        Lista<Palabra> l = new Lista<>();
        Iterator<Palabra> i = d.iteradorLlaves();
        while(i.hasNext()) {
            l.agrega(i.next());
        }
        this.lista = l;
        max = masFrecuente();
    }

    /**
     * Método que imprime el SVG de una gráfica de barras, asigan el
     * ancho, alto y las barras proporcional al numero de veces de cada palabra
     * @return Codigo SVG para generar grafica de barras.
     */
    public String imprime() {
        int height = 500;
        int width = 2500;
        String res = "<?xml version='1.0' encoding='UTF-8' ?>\n";
		res = res + "<svg width=\"" + width + "\" height=\"" + height + "\" xmlns=\"http://www.w3.org/2000/svg\">\n";
        int x = 7;
        boolean color = false;
        for (Palabra p : this.lista ) {
            if (!color)
                res = res + "<rect x=\"" + x + "\" y=\"" + ((450 - alturaBarra(p.getNum()))+5) + "\" width=\"130\" height=\"" + alturaBarra(p.getNum()) + "\" style=\"fill:blue\"/>\n";
            else
                res = res + "<rect x=\"" + x + "\" y=\"" + ((450 - alturaBarra(p.getNum()))+5) + "\" width=\"130\" height=\"" + alturaBarra(p.getNum()) + "\" style=\"fill:green\"/>\n";
            res = res + "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (x + 65) + "' y='"+ 480 + "' text-anchor='middle'>" + p + "</text>\n";
            res = res + "\t<text fill='black' font-family='sans-serif' font-size='16' x='" + (x + 65) + "' y='"+ 440 + "' text-anchor='middle'>" + p.getNum() + "</text>\n";
            x = x + 160;
            color = !color;
        }

        return res + "</svg>";
    }

    /**
     * Método que calcula la altura de la barra de acuerdo a la frecuencia
     * de la palabra.
     * @param  frecuencia Cuantas veces aparece la palabra en el archivo.
     * @return            El porcentaje de la altura de la barra.
     */
    public int alturaBarra(int frecuencia) {
        int porcentaje = (int)(frecuencia * 450)/this.max;
        return porcentaje;
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
}
