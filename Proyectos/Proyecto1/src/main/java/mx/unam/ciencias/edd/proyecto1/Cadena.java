package mx.unam.ciencias.edd.proyecto1;

import java.text.Collator;
import java.text.Normalizer;

public class Cadena implements Comparable<Cadena> {

    /* La linea a ordenar */
    public String contenido;

    /* Constructor */
    public Cadena(String contenido) {
        this.contenido = contenido;
    }

    /* toString */
    @Override
    public String toString() {
        return contenido;
    }

    /* Sobrescribe el método compareTo */
    @Override
    public int compareTo(Cadena s2) {
        if (contenido.equals("") && !s2.contenido.equals(""))
            return -1;
        if (!contenido.equals("") && s2.contenido.equals(""))
            return 1;
        if (contenido.equals("") && s2.contenido.equals(""))
            return 0;

        String aux = this.contenido;
        String aux2 = s2.contenido;

        aux = aux.replaceAll(" ", "");
        aux2 = aux2.replaceAll(" ", "");

        aux = Normalizer.normalize(aux, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        aux2 = Normalizer.normalize(aux2, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        // Las cadenas No tiene acentos

        Collator comparador = Collator.getInstance();
        comparador.setStrength(Collator.PRIMARY);

        return comparador.compare(aux, aux2);

        // return String.CASE_INSENSITIVE_ORDER.compare(aux, aux2);

    }

}