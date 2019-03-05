package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador() {
            cola = new Cola<Vertice>();
            if (raiz != null){
                cola.mete(raiz);
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            if (cola.esVacia()) throw new NoSuchElementException();
            Vertice v = cola.saca();
            if (v.hayIzquierdo()) cola.mete(v.izquierdo);
            if (v.hayDerecho()) cola.mete(v.derecho);
            return v.get();
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null) throw new IllegalArgumentException();
        Vertice nuevo = nuevoVertice(elemento);        
        elementos++;
        if (esVacia()) raiz = nuevo;
        else {
            Cola<VerticeArbolBinario<T>> cola = new Cola<>();
            cola.mete(raiz());
            VerticeArbolBinario<T> v;
            while (!cola.esVacia()) {
                v = cola.saca();
                if (v.hayIzquierdo()) cola.mete(v.izquierdo());
                if (v.hayDerecho()) cola.mete(v.derecho());
                if (!v.hayIzquierdo()) {
                    Vertice a = vertice(v);
                    a.izquierdo = nuevo;
                    nuevo.padre = a;
                    return;
                }
                if (!v.hayDerecho()) {
                    Vertice b = vertice(v);
                    b.derecho = nuevo;
                    nuevo.padre = b;
                    return;
                }
            }      
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if (elemento == null) throw new IllegalArgumentException();
        if (!contiene(elemento)) return;
        elementos--;
        if (elementos == 0) {
            limpia();
            return;
        }
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        Vertice ultimo = null;
        while (!cola.esVacia()) {
            ultimo = cola.saca();
            if (ultimo.hayIzquierdo()) cola.mete(ultimo.izquierdo);
            if (ultimo.hayDerecho()) cola.mete(ultimo.derecho);
        }
        Vertice e = vertice(busca(elemento));
        e.elemento = ultimo.elemento;
        Vertice padre = ultimo.padre;
        if (padre.izquierdo == ultimo) {
            padre.izquierdo = null;
        } else {
            padre.derecho = null;
        }
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if (raiz == null) return -1;
        return (int)(Math.floor(Math.log(elementos) / Math.log(2)));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (esVacia()) return;
        Cola<VerticeArbolBinario<T>> cola = new Cola<>();
        cola.mete(raiz());
        while (!cola.esVacia()) {
            VerticeArbolBinario<T> v = cola.saca();
            accion.actua(v);
            if (v.hayIzquierdo()) cola.mete(v.izquierdo());
            if (v.hayDerecho()) cola.mete(v.derecho());
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
