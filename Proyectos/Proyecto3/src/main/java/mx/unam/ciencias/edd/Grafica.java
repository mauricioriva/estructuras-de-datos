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
    private class Vertice implements VerticeGrafica<T>,ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* El diccionario de vecinos del vértice. */
        public Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Diccionario<T, Vecino>();
            this.distancia = 0;
            this.indice = -1;
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            if (distancia < vertice.distancia) return -1;
            if (distancia > vertice.distancia) return 1;
            return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Diccionario<T, Vertice>();
        aristas = 0;
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
        this.vertices.agrega(elemento,v);
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

        Vertice vi = busca(a);
        Vertice vj = busca(b);

        Vecino va = new Vecino(vi,1);
        Vecino vb = new Vecino(vj,1);

        vi.vecinos.agrega(b,vb);
        vj.vecinos.agrega(a,va);

        this.aristas++;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        if (a.equals(b) || sonVecinos(a, b) || peso < 0)
            throw new IllegalArgumentException();
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException();

        Vertice verticea = busca(a);
        Vertice verticeb = busca(b);

        Vecino va = new Vecino(verticea,peso);
        Vecino vb = new Vecino(verticeb,peso);

        verticea.vecinos.agrega(b,vb);
        verticeb.vecinos.agrega(a,va);

        this.aristas++;
    }

    private Vertice busca(T elemento) {
    	return vertices.get(elemento);
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

        Vertice verticea = busca(a);
        Vertice verticeb = busca(b);

        verticea.vecinos.elimina(b);
        verticeb.vecinos.elimina(a);

        this.aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return vertices.contiene(elemento);
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
        Vertice ve = busca(elemento);
        this.vertices.elimina(elemento);
        for (Vecino v : ve.vecinos ) {
            v.vecino.vecinos.elimina(elemento);
            aristas--;
        }
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

        Vertice verticea = busca(a);
        try {
            Vecino vb = verticea.vecinos.get(b);
            return true;
        } catch (NoSuchElementException nse) {
            return false;
        }
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        if (!contiene(a) || !contiene(b)) throw new NoSuchElementException();
        if (!sonVecinos(a,b)) throw new IllegalArgumentException();

        Vertice v = busca(a);
        Vecino va = v.vecinos.get(b);

        return va.peso;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        if (!contiene(a) || !contiene(b)) throw new NoSuchElementException();
        if (!sonVecinos(a,b) || peso <= 0) throw new IllegalArgumentException();

        Vertice va = busca(a);
        Vertice vb = busca(b);

        Vecino eleBen_va = va.vecinos.get(b);
        Vecino eleAen_vb = vb.vecinos.get(a);

        eleAen_vb.peso = peso;
        eleBen_va.peso = peso;
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
        VerticeGrafica<T> vertice = this.vertices.get(elemento);
        return vertice;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class) {
        	throw new IllegalArgumentException("Vértice inválido");
        }
        if (vertice.getClass() == Vertice.class) {
        	Vertice v = (Vertice) vertice;
        	v.color = color;
        }
        if (vertice.getClass() == Vecino.class) {
        	Vecino v = (Vecino) vertice;
        	v.vecino.color = color;
        }
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        paraCadaVertice((vertice) -> {setColor(vertice, Color.ROJO);});
        Cola<Vertice> c = new Cola<>();
        Vertice primero = null;
        for (Vertice v : this.vertices ) {
            primero = v;
            break;
        }
        c.mete(primero);
        setColor(primero, Color.NEGRO);
        while (!c.esVacia()) {
            Vertice v = c.saca();
            for (Vecino ve : v.vecinos) {
                if (ve.vecino.color == Color.ROJO) {
                    setColor(ve, Color.NEGRO);
                    c.mete(ve.vecino);
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
            for (Vecino v : u.vecinos) {
                if (v.vecino.color == Color.ROJO) {
                    setColor(v, Color.NEGRO);
                    q.mete(v.vecino);
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
            for (Vecino v : u.vecinos) {
                if (v.vecino.color == Color.ROJO) {
                    setColor(v, Color.NEGRO);
                    q.mete(v.vecino);
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
        return vertices.getElementos() == 0;
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
            for (Vecino vi : v.vecinos){
                if (vi.vecino.color == Color.ROJO)
                    s = s + "(" + v.elemento + ", " + vi.vecino.elemento + "), ";
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
            for (Vecino u : v.vecinos) {
                if (u.vecino.color == Color.ROJO)
                    if (!grafica.sonVecinos(v.elemento,u.vecino.elemento))
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

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        Vertice s = busca(origen);
        Vertice t = busca(destino);
        if (s == null || t == null)
        	throw new NoSuchElementException();
        Lista<VerticeGrafica<T>> lista = new Lista<>();
        if (origen.equals(destino)){
        	lista.agrega(s);
        	return lista;
        }

        for (Vertice vk : vertices ) {
        	vk.distancia = Double.MAX_VALUE;
        }

        s.distancia = 0;
        Cola<Vertice> queue = new Cola<>();
        queue.mete(s);
        while (!queue.esVacia()) {
        	Vertice u = queue.saca();
        	for (Vecino v : u.vecinos ) {
        		if (v.vecino.distancia == Double.MAX_VALUE){
        			v.vecino.distancia = u.distancia + 1;
        			queue.mete(v.vecino);
        		}
        	}
        }
        if (t.distancia == Double.MAX_VALUE){
        	return lista;
        }
        Vertice u = t;
        lista.agrega(u);
	    while (!u.elemento.equals(origen))
	        for (Vecino ve : u.vecinos ) {
	        	if (ve.vecino.distancia + 1 == u.distancia){
	        		lista.agrega(ve.vecino);
	        		u = ve.vecino;
	        	}
	        }
        return lista.reversa();
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
    	Vertice s = busca(origen);
        Vertice t = busca(destino);
        if (s == null || t == null)
        	throw new NoSuchElementException();
        Lista<VerticeGrafica<T>> lista = new Lista<>();
        if (origen.equals(destino)){
        	lista.agrega(s);
        	return lista;
        }

        for (Vertice vk : vertices ) {
        	vk.distancia = Double.MAX_VALUE;
        }

        s.distancia = 0;

        MonticuloMinimo<Vertice> mm = new MonticuloMinimo<>(vertices, getElementos());

        while (!mm.esVacia()) {
        	Vertice u = mm.elimina();
        	for (Vecino v : u.vecinos) {
        		if (v.vecino.distancia > u.distancia + v.peso){
        			v.vecino.distancia = u.distancia + v.peso;
        			mm.reordena(v.vecino);
        		}
        	}
        }

        if (t.distancia == Double.MAX_VALUE){
        	return lista;
        }

        Vertice u = t;
        lista.agrega(u);
	    while (!u.elemento.equals(origen)) {
	    	for (Vecino v : u.vecinos ) {
	        	if (v.vecino.distancia == u.distancia - v.peso) {
	        		lista.agrega(v.vecino);
	        		u = v.vecino;
	        	}
	        }
	    }
	    return lista.reversa();
    }
}
