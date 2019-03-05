package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
            this.elemento = elemento;
            padre = null;
            izquierdo = null;
            derecho = null;
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <tt>true</tt> si el vértice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
            return padre != null;
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <tt>true</tt> si el vértice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <tt>true</tt> si el vértice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
            return derecho != null;
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            if (padre == null) throw new NoSuchElementException();
            return padre;
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            if (izquierdo == null) throw new NoSuchElementException();
            return izquierdo;
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            if (derecho == null) throw new NoSuchElementException();
            return derecho;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            if (hayIzquierdo() && hayDerecho()) {
                return 1 + Math.max(izquierdo.altura(), derecho.altura());
            } else if (hayIzquierdo()) {
                return 1 + izquierdo.altura();
            } else if (hayDerecho()) {
                return 1 + derecho.altura();
            } else {
                return 0;
            }
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            if (padre == null) return 0;
            return 1 + padre.profundidad();
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            return elemento;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
            return auxEquals(this,vertice);
        }

        /**
         * Método auxiliar recursivo el cual recibe 2 vertices y revisa si sus subsecuentes
         * son iguales.
         * @param a vertice a comparar
         * @param b vertice a comparar
         */
        private boolean auxEquals(Vertice a, Vertice b) {
            if (a == null && b == null)
                return true;
            if (a != null && b != null) {
                if (!a.elemento.equals(b.elemento))
                    return false;
                if (auxEquals(a.izquierdo, b.izquierdo))
                    return auxEquals(a.derecho, b.derecho);
                return false;
            }
            return false;
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
            String s = "";
            return s + elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        coleccion.forEach((t) -> {agrega(t);});
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        if (elementos == 0) return -1;
        else return raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        if (elemento == null) throw new IllegalArgumentException();
        if (busca(elemento) == null)
            return false;
        else
            return true;
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        if (elemento == null) throw new IllegalArgumentException();
        if (esVacia()) return null;
        return buscaAux(raiz(),elemento);
    }

    /**
     * Método auxiliar que busca un elemento en todos los nodos de la izquierda 
     * y de la derecha recursivamente.
     * @param v vertice a comparar con el elemento.
     * @param elemento elemento a comparar con los vertices del árbol.
     */
    private VerticeArbolBinario<T> buscaAux(VerticeArbolBinario<T> v, T elemento) {
        if (v.get().equals(elemento))
            return v;
        if (v.hayIzquierdo())
            if ((buscaAux(v.izquierdo(), elemento)) != null) 
                return (buscaAux(v.izquierdo(), elemento));
        if (v.hayDerecho())
            if ((buscaAux(v.derecho(), elemento)) != null) 
                return (buscaAux(v.derecho(), elemento));
        return null;
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        if (esVacia()) throw new NoSuchElementException();
        return raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        raiz = null;
        elementos = 0;
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
        ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
        if (esVacia() && arbol.esVacia()) return true;
        else return raiz.equals(arbol.raiz);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if (raiz == null) return "";
        int a [] = new int[altura() + 1];
        for (int i = 0; i < altura() + 1; i++) {
            a[i] = 0;
        }
        return cadena(raiz, 0, a);
    }

    /**
     * Método auxiliar que maneja los niveles del árbol para ilustrar en la cadena,
     * el significado de los simbolos es: con una '>' es el izquierdo, con dos '>>' es el
     * derecho.
     * @param v vertice el cual se imprime su elemento.
     * @param nivel el nivel del arbol el cual imprime los elementos.
     * @param a arreglo de 0 y 1.
     */
    private String cadena(Vertice v, int nivel, int[] a){
        String s = v.toString() + "\n";
        a[nivel] = 1;
        if (v.hayIzquierdo() && v.hayDerecho()) {
            s = s + dibujaEspacios(nivel,a);
            s = s + "├─›";
            s = s + cadena(v.izquierdo, nivel + 1, a);
            s = s + dibujaEspacios(nivel, a);
            s = s + "└─»";
            a[nivel] = 0;
            s = s + cadena(v.derecho, nivel + 1, a);
        } else if (v.hayIzquierdo()) {
            s = s + dibujaEspacios(nivel, a);
            s = s + "└─›";
            a[nivel] = 0;
            s = s + cadena(v.izquierdo, nivel + 1, a);
        } else if (v.hayDerecho()) {
            s = s + dibujaEspacios(nivel, a);
            s = s + "└─»";
            a[nivel] = 0;
            s = s + cadena(v.derecho, nivel + 1, a);
        }
        return s;
    }

    /**
     * Método auxiliar que dibuja los espacios correspondientes para la representación
     * en cadena del árbol.
     * @param nivel el nivel del arbol el cual imprime los elementos.
     * @param a arreglo de 0 y 1.
     */
    private String dibujaEspacios(int nivel, int[] a){
        String s = "";
        for (int i = 0; i <= nivel - 1; i++) {
            if (a[i] == 1) {
                s = s + "│  ";
            } else {
                s = s + "   ";
            }
        }
        return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
}
