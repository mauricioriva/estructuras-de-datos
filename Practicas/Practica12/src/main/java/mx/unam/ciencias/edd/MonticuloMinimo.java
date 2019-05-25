package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>> implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            if (indice >= elementos)
                throw new NoSuchElementException();
            return arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>> implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento = elemento;
            indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100); /* 100 es arbitrario. */
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        arbol = nuevoArreglo(n);
        for (T t : iterable)
            agrega(t);
        for (int j = (n / 2) - 1; j > 0; j--)
            acomodaAbajo(j);
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        if (elementos == arbol.length) {
            T [] nuevo = nuevoArreglo(arbol.length * 2);
            for (int i = 0; i < arbol.length; i++)
                nuevo[i] = arbol[i];
            arbol = nuevo;
        }
        arbol[elementos] = elemento;
        arbol[elementos].setIndice(elementos);
        elementos++;
        reordena(arbol[elementos - 1]);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        if (esVacia())
            throw new IllegalStateException();
        T auxiliar = arbol[0];
        intercambia(0, elementos - 1);
        elementos--;
        arbol[elementos].setIndice(-1);
        arbol[elementos] = null;
        acomodaAbajo(0);
        return auxiliar;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        if (elemento.getIndice() < 0 || elemento.getIndice() >= elementos)
            return;
        int aux = elemento.getIndice();
        intercambia(aux, elementos - 1);
        elementos--;
        arbol[elementos].setIndice(-1);
        arbol[elementos] = null;
        acomodaArriba(aux);
        acomodaAbajo(aux);
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        if (elemento.getIndice() < 0 || elemento.getIndice() >= elementos)
            return false;
        if (arbol[elemento.getIndice()].equals(elemento))
            return true;
        return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        elementos = 0;
        for (int i = 0; i < arbol.length; i++)
            arbol[i] = null;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        int i = elemento.getIndice();
        acomodaArriba(i);
        acomodaAbajo(i);
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        if (i < 0 || i >= elementos)
            throw new NoSuchElementException();
        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        String s = "";
        for (int i = 0; i < arbol.length; i++)
            s += arbol[i] + ", ";
        return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo = (MonticuloMinimo<T>)objeto;
        if (elementos != monticulo.elementos)
            return false;
        for (int i = 0; i < elementos; i++)
            if (!arbol[i].equals(monticulo.get(i)))
                return false;
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>> Lista<T> heapSort(Coleccion<T> coleccion) {
        Lista<Adaptador<T>> lista = new Lista<Adaptador<T>>();
        Lista<T> l = new Lista<>();
        for (T t : coleccion )
            lista.agrega(new Adaptador<T>(t));
        MonticuloMinimo<Adaptador<T>> m = new MonticuloMinimo<Adaptador<T>>(lista);
        while (!m.esVacia())
            l.agrega(m.elimina().elemento);
        return l;
    }

    private void intercambia (int i, int j) {
        int iIndice = arbol[i].getIndice();
        int jIndice = arbol[j].getIndice();
        T auxiliar = arbol[i];
        arbol[i] = arbol[j];
        arbol[i].setIndice(iIndice);
        arbol[j] = auxiliar;
        arbol[j].setIndice(jIndice);
    }

    private void acomodaAbajo (int i) {
        if (i >= elementos)
            return;
        int j = i;
        int hIzquierdo = (2 * i) + 1;
        int hDerecho = (2 * i) + 2;
        if (hIzquierdo < elementos && arbol[hIzquierdo] != null)
            if (arbol[j].compareTo(arbol[hIzquierdo]) > 0)
                j = hIzquierdo;
        if (hDerecho < elementos && arbol[hDerecho] != null)
            if (arbol[j].compareTo(arbol[hDerecho]) > 0)
                j = hDerecho;
        if (j == i)
            return;
        intercambia(i, j);
        acomodaAbajo(j);
    }

    private void acomodaArriba (int i) {
        if (i <= 0)
            return;
        int p = (i - 1) / 2;
        if (arbol[p] != null && arbol[i] != null)
            if (arbol[p].compareTo(arbol[i]) > 0) {
                intercambia(p, i);
                acomodaArriba(p);
            }
    }
}
