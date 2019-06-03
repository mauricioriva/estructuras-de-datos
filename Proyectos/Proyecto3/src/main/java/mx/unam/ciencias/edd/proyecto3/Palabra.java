package mx.unam.ciencias.edd.proyecto3;

import java.text.Normalizer;
import java.text.Collator;

/** Clase para depurar palabras y puedan ser comparables */
public class Palabra implements Comparable<Palabra> {

    /* La palabra original */
    private String original;

    /* La palabra depurada */
    private String palabra;

    /* Para identificar la palabra */
    public char identificador;

    /* Num de veces que aparece, solo se usa para las estadisticas */
    private int num;

    /**
     * Constructor que inicializa la palabra original
     * e inicializa la palabra depurada con el metodo depura().
     * @param word Palabra a depurar.
     */
    public Palabra(String word) {
        this.original = word.replaceAll(",","");
        this.palabra = depura(this.original);
        this.num = 1;
    }

    /**
     * Método que depura una palabra dejandola en minúsculas
     * y sin acentos.
     * @param  s La palabra a depurar.
     * @return   La palabra depurada.
     */
    private String depura(String s) {
        String k = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        String q = k.toLowerCase();
        return q;
    }

    /**
     * Getter para obtener la palabra depurada.
     * @return La palabra depurada.
     */
    public String getPalabra() {
        return palabra;
    }

    /**
     * Getter para obtener la palabra original.
     * @return La palabra original.
     */
    public String getOriginal() {
        return original;
    }

    /**
     * Método equals para comparar palabras depuradas.
     * @param  s Objeto Palabra para comparar con el de la clase.
     * @return   True si las palabras depuradas son iguales, False
     *           si son diferentes.
     */
    public boolean equals(Object o) {
        Palabra k = new Palabra(o.toString());
        return this.palabra.equals(k.getPalabra());
    }

    /**
     * Método hashCode para hacer la función de dispersion
     * sobre las palabras depuradas, en vez del objeto Palabra.
     * @return El hashCode de la palabra depurada.
     */
    public int hashCode() {
        return getPalabra().hashCode();
    }

    /**
     * Sobrescribiendo el método compareTo para hacer las
     * palabras comparables entre si.
     * @return La comparación de 2 cadenas.
     */
    public int compareTo(Palabra k) {
        if (num > k.getNum()) return 1;
        if (num < k.getNum()) return -1;
        return 0;
    }

    /**
     * Getter para obtener el numero de veces que aparece la palabra
     * @return El numero de veces de la palabra.
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Setter para cambiar el valor que aparece la palabra.
     * @param n El nuevo numero de veces que aparece la palabra.
     */
    public void setNum(int n) {
        this.num = n;
    }

    /**
     * Método toString para ver la diferencia entre la palabra
     * depurada y la original.
     * @return Una representacion de la palabra original y
     *         la depurada.
     */
    @Override
    public String toString() {
        return this.original;
    }

}
