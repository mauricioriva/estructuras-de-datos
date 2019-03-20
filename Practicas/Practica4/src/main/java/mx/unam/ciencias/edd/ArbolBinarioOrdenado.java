package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.
 * </p>
 *
 * <p>
 * Un árbol instancia de esta clase siempre cumple que:
 * </p>
 * <ul>
 * <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 * descendientes por la izquierda.</li>
 * <li>Cualquier elemento en el árbol es menor o igual que todos sus
 * descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Construye un iterador con el vértice recibido. */
        public Iterador() {
            pila = new Pila<Vertice>();
            if (raiz != null) {
                pila.mete(raiz);
                Vertice aux = raiz;
                while (aux.hayIzquierdo()) {
                    pila.mete(aux.izquierdo);
                    aux = aux.izquierdo;
                }
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() {
            Vertice aux = pila.saca();
            T t = aux.get();
            aux = aux.derecho;
            while (aux != null) {
                pila.mete(aux);
                aux = aux.izquierdo;
            }
            return t;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede garantizar
     * que existe <em>inmediatamente</em> después de haber agregado un elemento al
     * árbol. Si cualquier operación distinta a agregar sobre el árbol se ejecuta
     * después de haber agregado un elemento, el estado de esta variable es
     * indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() {
        super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario
     *                  ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Vertice nuevo = nuevoVertice(elemento);
        elementos++;
        if (raiz == null) {
            raiz = nuevo;
            ultimoAgregado = nuevo;
            return;
        } else {
            auxAgrega(raiz, nuevo);
            ultimoAgregado = nuevo;
        }
    }

    /**
     * Método auxiliar para recorrer el árbol de acuerdo a si el elemento es mayor,
     * menor o igual y hasta encontrar una hoja colocarse allí, al final del árbol.
     * 
     * @param actual Vertice con el cual se va a comparar, si es mayor, menor o
     *               igual.
     * @param nuevo  Vertice el cual se va a agregar al árbol.
     */
    private void auxAgrega(Vertice actual, Vertice nuevo) {
        if ((nuevo.get().compareTo(actual.get())) <= 0) {
            if (!actual.hayIzquierdo()) {
                actual.izquierdo = nuevo;
                nuevo.padre = actual;
                return;
            } else
                auxAgrega(actual.izquierdo, nuevo);
        } else {
            if (!actual.hayDerecho()) {
                actual.derecho = nuevo;
                nuevo.padre = actual;
                return;
            } else
                auxAgrega(actual.derecho, nuevo);
        }
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        if (busca(elemento) == null)
            return;
        VerticeArbolBinario<T> e = busca(elemento);
        Vertice aEliminar = vertice(e);
        elementos--;
        if (elementos == 0)
            limpia();
        if (aEliminar.hayIzquierdo() && aEliminar.hayDerecho()) {
            aEliminar = intercambiaEliminable(aEliminar);
        }
        eliminaVertice(aEliminar);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más un
     * hijo.
     * 
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se intercambió.
     *         El vértice regresado tiene a lo más un hijo distinto de
     *         <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice v = maximoEnSubArbol(vertice.izquierdo);
        T t = v.get();
        v.elemento = vertice.elemento;
        vertice.elemento = t;
        return v;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de <code>null</code>
     * subiendo ese hijo (si existe).
     * 
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo distinto de
     *                <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice hijo = null;
        Vertice padre = null;
        if (vertice.hayIzquierdo())
            hijo = vertice.izquierdo;
        if (vertice.hayDerecho())
            hijo = vertice.derecho;
        if (vertice.hayPadre())
            padre = vertice.padre;

        if (padre != null) {
            if (padre.izquierdo == vertice) { // Aqui estaba la falla :v
                padre.izquierdo = hijo;
            } else {
                padre.derecho = hijo;
            }
        } else if (padre == null)
            raiz = hijo;

        if (hijo != null)
            hijo.padre = padre;

    }

    /**
     * Te regresa el elemento máximo de un subarbol
     * 
     * @param v vertice el cual es la raiz de un subarbol
     */
    private Vertice maximoEnSubArbol(Vertice v) {
        if (!v.hayDerecho())
            return v;
        return maximoEnSubArbol(v.derecho);
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <tt>null</tt>.
     * 
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    @Override
    public VerticeArbolBinario<T> busca(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        if (esVacia())
            return null;
        return buscaAux(raiz(), elemento);
    }

    /**
     * Método auxiliar que busca recursivamente el elemento, debido a que el árbol
     * está ordenado si el elemento es mayor busca por la derecha, si es menor o
     * igual busca por la izquierda, si llega a alguna hoja implica que no encontró
     * el elemento y regresa <tt>null</tt>.
     * 
     * @param el elemento a buscar.
     * @return vertice encontrado que contiene el elemento, <tt>null</tt> en otro
     *         caso.
     */
    private VerticeArbolBinario<T> buscaAux(VerticeArbolBinario<T> v, T elemento) {
        if (elemento.compareTo(v.get()) == 0)
            return v;
        else if (elemento.compareTo(v.get()) <= 0)
            if (v.hayIzquierdo())
                return buscaAux(v.izquierdo(), elemento);
            else
                return null;
        else if (elemento.compareTo(v.get()) > 0)
            if (v.hayDerecho())
                return buscaAux(v.derecho(), elemento);
            else
                return null;
        return null;
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al árbol. Este
     * método sólo se puede garantizar que funcione <em>inmediatamente</em> después
     * de haber invocado al método {@link agrega}. Si cualquier operación distinta a
     * agregar sobre el árbol se ejecuta después de haber agregado un elemento, el
     * comportamiento de este método es indefinido.
     * 
     * @return el vértice que contiene el último elemento agregado al árbol, si el
     *         método es invocado inmediatamente después de agregar un elemento al
     *         árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no tiene
     * hijo izquierdo, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        if (!vertice.hayIzquierdo() || vertice == null)
            return;
        Vertice q = vertice(vertice);
        Vertice p = q.izquierdo;
        Vertice s = p.derecho;
        p.derecho = q;
        p.padre = q.padre;
        q.izquierdo = s;
        if (q.hayIzquierdo())
            s.padre = q; // Verificamos si 's' existe
        if (!p.hayPadre()) { // Preguntamos si 'q' es la raiz
            raiz = p;
            q.padre = p;
            return;
        }
        if (q == q.padre.derecho)
            q.padre.derecho = p; // Ajustamos si el vertice es izq o derecho
        else
            q.padre.izquierdo = p; // del padre
        q.padre = p;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * 
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        if (!vertice.hayDerecho())
            return;
        Vertice p = vertice(vertice);
        Vertice q = p.derecho;
        Vertice s = q.izquierdo;
        q.izquierdo = p;
        q.padre = p.padre;
        p.derecho = s;
        if (p.hayDerecho())
            s.padre = p; // Verificamos si 's' existe
        if (!q.hayPadre()) { // Preguntamos si 'p' es la raiz
            raiz = q;
            p.padre = q;
            return;
        }
        if (p == p.padre.izquierdo)
            p.padre.izquierdo = q; // Ajustamos si el vertice es izquierdo o derecho
        else
            p.padre.derecho = q; // del padre
        p.padre = q;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        auxDfsPreOrder(raiz(), accion);
    }

    /**
     * Recursivamente aplica una accion siguiendo el recorrido de DFS
     * <em>pre-order</em>
     * 
     * @param accion Recibe una lambda la cual puede interactuar con los vertices
     *               del árbol.
     * @param v      Vertice al cual se le aplica la acción.
     */
    private void auxDfsPreOrder(VerticeArbolBinario<T> v, AccionVerticeArbolBinario<T> accion) {
        accion.actua(v);
        if (v.hayIzquierdo())
            auxDfsPreOrder(v.izquierdo(), accion);
        if (v.hayDerecho())
            auxDfsPreOrder(v.derecho(), accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la acción
     * recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
            return;
        auxDfsInOrder(raiz(), accion);
    }

    /**
     * Recursivamente aplica una accion siguiendo el recorrido de DFS
     * <em>in-order</em>
     * 
     * @param accion Recibe una lambda la cual puede interactuar con los vertices
     *               del árbol.
     * @param v      Vertice al cual se le aplica la acción.
     */
    private void auxDfsInOrder(VerticeArbolBinario<T> v, AccionVerticeArbolBinario<T> accion) {
        if (v.hayIzquierdo())
            auxDfsInOrder(v.izquierdo(), accion);
        accion.actua(v);
        if (v.hayDerecho())
            auxDfsInOrder(v.derecho(), accion);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * 
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        auxDfsPostOrder(raiz(), accion);
    }

    /**
     * Recursivamente aplica una accion siguiendo el recorrido de DFS
     * <em>post-order</em>
     * 
     * @param accion Recibe una lambda la cual puede interactuar con los vertices
     *               del árbol.
     * @param v      Vertice al cual se le aplica la acción.
     */
    private void auxDfsPostOrder(VerticeArbolBinario<T> v, AccionVerticeArbolBinario<T> accion) {
        if (v.hayIzquierdo())
            auxDfsPostOrder(v.izquierdo(), accion);
        if (v.hayDerecho())
            auxDfsPostOrder(v.derecho(), accion);
        accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
