package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento = elemento;
            anterior = null;
            siguiente = null;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            this.anterior = this.siguiente;
            this.siguiente = this.siguiente.siguiente;
            return this.anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            this.siguiente = this.anterior;
            this.anterior = this.anterior.anterior;
            return this.siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            siguiente = null;
            anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Nodo n = new Nodo(elemento);
        longitud++;
        if (esVacia()) { // La cabeza y el rabo son el mismo nodo
            this.cabeza = n;
            this.rabo = n;
        } else { // Agrega un nodo después del rabo y actualiza el rabo a ese nodo
            n.anterior = this.rabo;
            this.rabo.siguiente = n;
            this.rabo = n;
        }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        Nodo n = new Nodo(elemento);
        longitud++;
        if (esVacia()) {
            this.cabeza = n;
            this.rabo = n;
        } else {
            n.anterior = this.rabo;
            this.rabo.siguiente = n;
            this.rabo = n;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Nodo n = new Nodo(elemento);
        longitud++;
        if (esVacia()) {
            this.cabeza = n;
            this.rabo = n;
        } else { // Agrega un elemento antes de la cabeza y actualiza la cabeza
            n.siguiente = this.cabeza;
            this.cabeza.anterior = n;
            this.cabeza = n;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        } else if (i < 1) {
            agregaInicio(elemento);
        } else if (i > this.longitud - 1) {
            agregaFinal(elemento);
        } else {
            if (0 < i && i <= longitud - 1) { // Se asegura otra vez que el indice esté
                longitud++; // dentro del rango
                Nodo s = iesimoNodo(i); // Nodo iesimo el cual va a ser el siguiente nodo del nuevo
                Nodo n = new Nodo(elemento); // Nodo a agregar a la lista
                Nodo a = s.anterior; // Nodo anterior al iesimo
                n.anterior = a;
                a.siguiente = n; // Agrega el nodo nuevo entre el s y el a
                n.siguiente = s;
                s.anterior = n;
            }

        }
    }

    /**
     * Busca el nodo dado un indice
     * 
     * @param i Indice del nodo requerido
     * @return El i-esimo nodo
     */
    public Nodo iesimoNodo(int i) {
        Nodo aux = cabeza;
        if (0 <= i && i <= longitud - 1) { // Se vuelve a asegurar que el indice se
            for (int j = 0; j < i; j++) { // encuentre dentro del rango.
                aux = aux.siguiente; // Recorre la lista hasta el iesimo nodo
            }
        }
        return aux;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if (elemento == null || !contiene(elemento))
            return;
        if (cabeza.elemento.equals(elemento)) {
            eliminaPrimero(); // Si el primer elemento es la cabeza elimina la cabeza
            return;
        }
        if (contiene(elemento)) { // Si el elemento esta contenido en la lista:
            Nodo aux = cabeza;
            for (int i = 0; i < longitud - 2; i++) { // Recorre la lista hasta el penultimo elemento
                if (aux.elemento.equals(elemento)) { // Encuentra el elemento recorrido con el
                    aux.anterior.siguiente = aux.siguiente; // pasado como parametro
                    aux.siguiente.anterior = aux.anterior; // elimina el nodo
                    longitud--;
                    return;
                }
                aux = aux.siguiente;
            }
            eliminaUltimo(); // Sino elimina el ultimo elemento
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        Nodo n = cabeza;
        if (esVacia()) {
            throw new NoSuchElementException();
        } else if (longitud == 1) { // La lista vacia
            cabeza = null;
            rabo = null;
        } else if (longitud == 2) { // La lista con un elemento
            cabeza = rabo;
            rabo.anterior = null;
        } else {
            cabeza = cabeza.siguiente; // Elimina la cabeza y la actualiza.
            cabeza.anterior = null;
        }
        this.longitud--;
        return n.elemento;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        Nodo n = rabo;
        if (esVacia()) {
            throw new NoSuchElementException();
        } else if (longitud == 1) { // La lista vacia
            cabeza = null;
            rabo = null;
        } else if (longitud == 2) { // La lista con un elemento
            rabo = cabeza;
            cabeza.siguiente = null;
        } else {
            rabo = rabo.anterior; // Elimina el rabo y lo actualiza
            rabo.siguiente = null;
        }
        this.longitud--;
        return n.elemento;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        Nodo aux = cabeza;
        for (int i = 0; i < longitud; i++) {// Recorre la lista en busca de un
            if (aux.elemento.equals(elemento)) { // nodo con el mismo elemento
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> rev = new Lista<T>();
        Nodo aux = cabeza;
        for (int i = 0; i < longitud; i++) { // Añade los nuevos elementos
            rev.agregaInicio(aux.elemento); // al principio
            aux = aux.siguiente;
        }
        return rev;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copy = new Lista<T>();
        Nodo aux = cabeza;
        for (int i = 0; i < longitud; i++) { // Recorre la lista y conforme la recorre
            copy.agrega(aux.elemento); // agrega los elementos a la nueva lista
            aux = aux.siguiente;
        }
        return copy;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        this.cabeza = null;
        this.rabo = null;
        this.longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (esVacia()) {
            throw new NoSuchElementException();
        }
        return this.cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia()) {
            throw new NoSuchElementException();
        }
        return this.rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i >= longitud || i < 0) {
            throw new ExcepcionIndiceInvalido();
        }
        Nodo aux = cabeza; // Recorre la lista hasta que el indice buscado sea
        for (int j = 0; j < longitud; j++) { // igual al nodo recorrido
            if (j == i) {
                break;
            }
            aux = aux.siguiente;
        }
        return aux.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        Nodo n = cabeza;
        for (int i = 0; i < longitud; i++) { // Recorre la lista, la cantidad de veces necesaria
            if (elemento.equals(n.elemento)) { // hasta encontrar el elemento y regresa el indice
                return i;
            }
            n = n.siguiente;
        }
        return -1; // Si no lo encuentra regresa -1
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if (longitud == 0) {
            return "[]"; // La lista vacia
        }
        String res = "[";
        Nodo aux = cabeza;
        for (int i = 0; i < longitud; i++) {
            if (i == longitud - 1) {
                res = res + aux.elemento + "]"; // Si es el ultimo elemento
            } else {
                res = res + aux.elemento + ", "; // Si es la cabeza o algun elemento
                aux = aux.siguiente; // intermedio
            }
        }
        return res;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if (lista.longitud != this.longitud) // Una lista es igual a otra si tiene la misma
            return false; // longitud
        Nodo aux = this.cabeza;
        for (int i = 0; aux != null; i++) { // Recorre nodo con nodo y compara
            if (!aux.elemento.equals(lista.get(i))) // elemento a elemento
                return false;
            aux = aux.siguiente;
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        if (this.longitud == 1 || this.longitud == 0) {
            return copia();
        }
        Lista<T> l1 = new Lista<>();
        Lista<T> l2 = new Lista<>();

        // Agregando elementos a la lista
        for (int i = 0; i < this.longitud / 2; i++) {
            l1.agrega(get(i));
        }
        for (int i = this.longitud / 2; i < this.longitud; i++) {
            l2.agrega(get(i));
        }

        l1 = l1.mergeSort(comparador);
        l2 = l2.mergeSort(comparador);
        return mezcla(l1, l2, comparador);
    }

    /**
     * Método auxiliar que mezcla 2 listas y las va ordenando en una nueva
     * 
     * @param lista1 Lista a ordenar
     * @param lista2 Lista a ordenar
     * @return La lista con los elementos ordenados de ambas listas
     */
    public Lista<T> mezcla(Lista<T> lista1, Lista<T> lista2, Comparator<T> comparador) {
        Nodo i = lista1.cabeza;
        Nodo j = lista2.cabeza;
        Lista<T> lista = new Lista<T>();
        while (i != null && j != null) {
            if (comparador.compare(i.elemento, j.elemento) <= 0) {
                lista.agrega(i.elemento);
                i = i.siguiente;
            } else {
                lista.agrega(j.elemento);
                j = j.siguiente;
            }
        }
        if (i == null)
            while (j != null) {
                lista.agrega(j.elemento);
                j = j.siguiente;
            }
        if (j == null)
            while (i != null) {
                lista.agrega(i.elemento);
                i = i.siguiente;
            }
        return lista;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        Nodo aux = cabeza;
        for (int i = 0; i < longitud; i++) {// Recorre la lista en busca de un
            if ((comparador.compare(elemento, aux.elemento)) == 0) { // nodo con el mismo elemento
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
