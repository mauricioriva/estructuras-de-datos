package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            this.llave = llave;
            this.valor = valor;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
            for (int i = 0 ; i < entradas.length ; i++ ) {
                if (entradas[i] != null) {
                    this.indice = i;
                    this.iterador = entradas[i].iterator();
                    return;
                }
            }
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            return this.iterador != null;
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            if (iterador == null) throw new NoSuchElementException();
            Entrada e = iterador.next();
            if (!iterador.hasNext()) {
                int aux = indice + 1;
                for (int i = indice + 1; i < entradas.length ; i++ ) {
                    if (entradas[i] != null)
                        break;
                    aux++;
                }
                if (aux < entradas.length)
                    iterador = entradas[aux].iterator();
                else
                    iterador = null;
                indice = aux;
            }
            return e;
        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            return super.siguiente().llave;
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            return super.siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        this.dispersor = dispersor;
        if (capacidad < MINIMA_CAPACIDAD) {
            capacidad = MINIMA_CAPACIDAD;
        }
        boolean aux = true;
        for (int i = 0; aux ; i++ ) {
            if (2 * capacidad <= Math.pow(2,i)) {
                capacidad = (int)Math.pow(2,i);
                aux = false;
            }
        }
        entradas = nuevoArreglo(capacidad);
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        if (llave == null || valor == null) throw new IllegalArgumentException();
        int i = dispersion(llave);
        if (entradas[i] == null) {
            entradas[i] = new Lista<Entrada>();
            entradas[i].agrega(new Entrada(llave,valor));
            elementos++;
        } else {
            for (Entrada e : entradas[i] ) {
                if (e.llave.equals(llave)) {
                    e.valor = valor;
                    return;
                }
            }
            Entrada k = new Entrada(llave,valor);
            entradas[i].agrega(k);
            elementos++;
        }
        if (carga() >= MAXIMA_CARGA) {
            Lista<Entrada>[] bob = entradas;
            Lista<Entrada>[] aux = nuevoArreglo(entradas.length * 2);
            entradas = aux;
            elementos = 0;
            for (int k = 0 ; k < bob.length ; k++ ) {
                if (bob[k] != null)
                    for (Entrada e : bob[k] ) {
                        agrega(e.llave,e.valor);
                    }
            }
        }
    }

    /**
     * Método que dispersa la llave y le aplica la máscara para regresar un indice
     * dentro del rango del tamaño del arreglo.
     * @param  llave La llave a dispersar
     * @return       La dispersion de la llave con la máscara aplicada.
     */
    private int dispersion(K llave) {
        return this.dispersor.dispersa(llave) & (this.entradas.length -1);
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        if (llave == null) throw new IllegalArgumentException();
        int i = dispersion(llave);
        if (entradas[i] == null) throw new NoSuchElementException();
        Entrada e = busca(entradas[i], llave);
        if (e == null) throw new NoSuchElementException();
        return e.valor;
    }

    /**
     * Método que busca una entrada especifica dentro del arreglo
     * @param  lista La lista con las entradas
     * @param  llave La llave a la que queremos acceder
     * @return       La entrada que contiene la llave recibida
     */
    private Entrada busca(Lista<Entrada> lista, K llave) {
        for (Entrada e : lista ) {
            if (e.llave.equals(llave))
                return e;
        }
        return null;
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <tt>true</tt> si la llave está en el diccionario,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(K llave) {
        if (llave == null)
            return false;
        int i = dispersion(llave);
        if (entradas[i] == null)
            return false;
        return busca(entradas[i], llave) != null;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        if (llave == null) throw new IllegalArgumentException();
        int i = dispersion(llave);
        if (entradas[i] == null) throw new NoSuchElementException();
        Entrada e = busca(entradas[i],llave);
        if (e == null) throw new NoSuchElementException();
        entradas[i].elimina(e);
        if (entradas[i].esVacia())
            entradas[i] = null; //Se anula la entrada del arreglo si la lista es vacia
        elementos--;
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        if (esVacia())
            return 0; //Caso para que no regrese -1
        int contador = 0;
        for (int i = 0 ; i < entradas.length ; i++ ) {
            if (entradas[i] != null)
                contador = contador + entradas[i].getElementos();
        }
        return contador - 1;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        int contador = 0;
        for (int i = 0 ; i < entradas.length ; i++ ) {
            if (entradas[i] != null)
                if (entradas[i].getElementos() > contador)
                    contador = entradas[i].getElementos();
        }
        return contador - 1;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        double e = (double)elementos;
        double l = (double)entradas.length;
        return e / l;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        Lista<Entrada>[] nuevo = nuevoArreglo(MINIMA_CAPACIDAD); //Optimizacion para no tener un arreglo muy grande
        elementos = 0;
        entradas = nuevo;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        if (esVacia())
            return "{}";
        String s = "{ ";
        Iterador i = new Iterador();
        while(i.hasNext()) {
            Entrada e = i.siguiente();
            s = s + "'" + e.llave + "': '" + e.valor + "', ";
        }
        return s + "}";
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked")
        Diccionario<K, V> d = (Diccionario<K, V>)o;
        if (getElementos() != d.getElementos())
            return false;
        for (int i = 0 ; i < entradas.length ; i++ ) {
            if (entradas[i] != null)
                for (Entrada e : entradas[i] ) {
                    if (!d.contiene(e.llave))
                        return false;
                }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
