package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            this.iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Lista<Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null || contiene(elemento))
            throw new IllegalArgumentException();
        Vertice v = new Vertice(elemento);
        this.vertices.agrega(v);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        if (a.equals(b) || sonVecinos(a, b))
            throw new IllegalArgumentException();
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();

        VerticeGrafica<T> verticea = vertice(a);
        VerticeGrafica<T> verticeb = vertice(b);

        Vertice va = (Vertice) verticea;
        Vertice vb = (Vertice) verticeb;

        va.vecinos.agrega(vb);
        vb.vecinos.agrega(va);

        this.aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        if (!sonVecinos(a, b))
            throw new IllegalArgumentException();

        VerticeGrafica<T> verticea = vertice(a);
        VerticeGrafica<T> verticeb = vertice(b);

        Vertice va = (Vertice) verticea;
        Vertice vb = (Vertice) verticeb;

        va.vecinos.elimina(vb);
        vb.vecinos.elimina(va);

        this.aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for (Vertice v : vertices)
            if (elemento.equals(v.elemento))
                return true;
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        if (elemento == null || !contiene(elemento))
            throw new NoSuchElementException();
        Vertice v = busca(elemento);
        this.vertices.elimina(v);
        eliminaVecinos(v);
    }

    private void eliminaVecinos(Vertice v) {
        Lista<Vertice> aux = v.vecinos;
        for (Vertice vecino : aux) {
            if (vecino.vecinos.contiene(v)) {
                vecino.vecinos.elimina(v);
                aristas--;
            }
        }
    }

    private Vertice busca(T elemento) {
        for (Vertice v : vertices) {
            if (elemento.equals(v.elemento)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        if (a == null || b == null)
            throw new NoSuchElementException();
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();

        VerticeGrafica<T> verticea = vertice(a);
        VerticeGrafica<T> verticeb = vertice(b);

        Vertice va = (Vertice) verticea;
        Vertice vb = (Vertice) verticeb;

        return va.vecinos.contiene(vb);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        if (!contiene(elemento))
            throw new NoSuchElementException();
        VerticeGrafica<T> vertice = null;
        for (Vertice v : vertices) {
            if (elemento.equals(v.elemento))
                vertice = (VerticeGrafica<T>)v;
        }
        return vertice;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || vertice.getClass() != Vertice.class)
            throw new IllegalArgumentException("Vértice inválido");
        Vertice v = (Vertice)vertice;
        v.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        paraCadaVertice((vertice) -> {setColor(vertice, Color.ROJO);});
        Cola<Vertice> c = new Cola<>();
        c.mete(vertices.getPrimero());
        setColor(vertices.getPrimero(), Color.NEGRO);
        while (!c.esVacia()) {
            Vertice v = c.saca();
            for (Vertice vecino : v.vecinos) {
                if (vecino.color == Color.ROJO) {
                    setColor(vecino, Color.NEGRO);
                    c.mete(vecino);
                }
            }
        }
        for (Vertice v : vertices) {
            if (v.color != Color.NEGRO) {
                return false;
            }
        }
        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for (Vertice v : vertices)
            accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (!contiene(elemento)) throw new NoSuchElementException();
        paraCadaVertice((vertice) -> {setColor(vertice, Color.ROJO);});
        Vertice w = busca(elemento);
        Cola<Vertice> q = new Cola<>();
        setColor(w, Color.NEGRO);
        q.mete(w);
        while (!q.esVacia()) {
            Vertice u = q.saca();
            accion.actua(u);
            for (Vertice v : u.vecinos) {
                if (v.color == Color.ROJO) {
                    setColor(v, Color.NEGRO);
                    q.mete(v);
                }
            }
        }
        paraCadaVertice((vertice) -> {setColor(vertice, Color.NINGUNO);});
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        if (!contiene(elemento)) throw new NoSuchElementException();
        paraCadaVertice((vertice) -> {setColor(vertice, Color.ROJO);});
        Vertice w = busca(elemento);
        Pila<Vertice> q = new Pila<>();
        setColor(w, Color.NEGRO);
        q.mete(w);
        while (!q.esVacia()) {
            Vertice u = q.saca();
            accion.actua(u);
            for (Vertice v : u.vecinos) {
                if (v.color == Color.ROJO) {
                    setColor(v, Color.NEGRO);
                    q.mete(v);
                }
            }
        }
        paraCadaVertice((vertice) -> {setColor(vertice, Color.NINGUNO);});
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.getLongitud() == 0;
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        this.aristas = 0;
        this.vertices.limpia();
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {

        paraCadaVertice((vertice) -> {setColor(vertice, Color.ROJO);});

        String s = "{";
        for (Vertice v : vertices)
            s = s + v.elemento + ", ";
        s = s + "}, {";


        for (Vertice v : vertices){
            for (Vertice vi : v.vecinos){
                if (vi.color == Color.ROJO)
                    s = s + "(" + v.elemento + ", " + vi.elemento + "), ";
            }
            v.color = Color.NEGRO;
        }
        s = s + "}";

        paraCadaVertice((vertice) -> {setColor(vertice, Color.NINGUNO);});
        
        return s;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la gráfica es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") 
        Grafica<T> grafica = (Grafica<T>)objeto;
        if (grafica.getAristas() != getAristas())
            return false;
        if (grafica.getElementos() != getElementos())
            return false;
        for (Vertice v : vertices) {
            v.color = Color.ROJO;
            if (!grafica.contiene(v.elemento)) 
                return false;
        }
        for (Vertice v : vertices) {
            for (Vertice u : v.vecinos) {
                if (u.color == Color.ROJO)
                    if (!grafica.sonVecinos(v.elemento,u.elemento))
                        return false;
            }
            v.color = Color.NEGRO;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
