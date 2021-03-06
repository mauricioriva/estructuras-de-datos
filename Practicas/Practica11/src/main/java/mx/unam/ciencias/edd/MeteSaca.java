package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase abtracta para estructuras lineales restringidas a operaciones
 * mete/saca/mira.
 */
public abstract class MeteSaca<T> {

    /**
     * Clase interna protegida para nodos.
     */
    protected class Nodo {
        /** El elemento del nodo. */
        public T elemento;
        /** El siguiente nodo. */
        public Nodo siguiente;

        /**
         * Construye un nodo con un elemento.
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
            this.elemento = elemento;
            siguiente = null;
        }
    }

    /** La cabeza de la estructura. */
    protected Nodo cabeza;
    /** El rabo de la estructura. */
    protected Nodo rabo;

    /**
     * Agrega un elemento al extremo de la estructura.
     * @param elemento el elemento a agregar.
     */
    public abstract void mete(T elemento);

    /**
     * Elimina el elemento en un extremo de la estructura y lo regresa.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T saca() {
        Nodo a = cabeza;
        if (cabeza == null)
            throw new NoSuchElementException();
        cabeza = cabeza.siguiente;
        if (cabeza == null) {
            rabo = null;
        }
        return a.elemento;
    }

    /**
     * Nos permite ver el elemento en un extremo de la estructura, sin sacarlo
     * de la misma.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura está vacía.
     */
    public T mira() {
        if (cabeza == null) {
            throw new NoSuchElementException();
        } else {
            return cabeza.elemento;
        }
    }

    /**
     * Nos dice si la estructura está vacía.
     * @return <tt>true</tt> si la estructura no tiene elementos,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Compara la estructura con un objeto.
     * @param object el objeto con el que queremos comparar la estructura.
     * @return <code>true</code> si el objeto recibido es una instancia de la
     *         misma clase que la estructura, y sus elementos son iguales en el
     *         mismo orden; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass())
            return false;
        @SuppressWarnings("unchecked") MeteSaca<T> m = (MeteSaca<T>)object;
        Nodo auxClase = cabeza;
        Nodo auxListaM = m.cabeza;
        if (esVacia() && m.esVacia())
            return true;
        int i = 0;
        while (auxClase != null) {
            auxClase = auxClase.siguiente;
            i++;
        }
        int j = 0;
        while (auxListaM != null) {
            auxListaM = auxListaM.siguiente;
            j++;
        }
        if (i != j)
            return false;
        Nodo aux = cabeza;
        Nodo auxM = m.cabeza;
        while (aux != null) {
            if (!aux.elemento.equals(auxM.elemento))
                return false;
            aux = aux.siguiente;
            auxM = auxM.siguiente;
        }
        return true;
    }
}
