package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 * <li>Todos los vértices son NEGROS o ROJOS.</li>
 * <li>La raíz es NEGRA.</li>
 * <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 * <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 * <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 * mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>> extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            this.color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * 
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if (this.color == Color.ROJO) {
                return "R{" + elemento.toString() + "}";
            } else {
                return "N{" + elemento.toString() + "}";
            }
        }

        /**
         * Compara el vértice con otro objeto. La comparación es <em>recursiva</em>.
         * 
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente iguales, y los
         *         colores son iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro) objeto;
            return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol rojinegro
     * tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeRojinegro}.
     * 
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override
    protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * 
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de
     *                            {@link VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        return v.color;
    }

    /**
     * Regresa true si el vertice existe y es rojo, null en otro caso.
     */
    private boolean esRojo(VerticeRojinegro vertice) {
        return (vertice != null && vertice.color == Color.ROJO);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método
     * {@link ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        super.agrega(elemento);
        Vertice vertice = super.ultimoAgregado;
        VerticeArbolBinario<T> aux = (VerticeArbolBinario<T>) vertice;
        VerticeRojinegro v = (VerticeRojinegro) aux;
        v.color = Color.ROJO;
        agrega(v);
    }

    private void agrega(VerticeRojinegro hijo) {
        // Caso 1
        if (!hijo.hayPadre()) {
            hijo.color = Color.NEGRO;
            return;
        }
        // Caso 2
        VerticeRojinegro padre = (VerticeRojinegro) hijo.padre();
        if (!esRojo(padre))
            return;
        // Caso 3 El padre es rojo y el abuelo es != null
        VerticeRojinegro abuelo = (VerticeRojinegro) padre.padre();
        // Encontramos al tio
        VerticeRojinegro tio = null;
        if (abuelo.izquierdo == padre && abuelo.hayDerecho()) {
            tio = (VerticeRojinegro) abuelo.derecho();
        } else if (abuelo.derecho == padre && abuelo.hayIzquierdo()) {
            tio = (VerticeRojinegro) abuelo.izquierdo();
        }
        if (esRojo(tio)) {
            tio.color = Color.NEGRO;
            padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            agrega(abuelo);
            return;
        }
        // Caso 4 El tio existe y es NEGRO
        // El hijo está cruzado con su abuelo?
        VerticeRojinegro aux;
        if (abuelo.izquierdo == padre && padre.derecho == hijo) {
            super.giraIzquierda(padre);
            aux = padre;
            padre = hijo;
            hijo = aux;
        } else if (abuelo.derecho == padre && padre.izquierdo == hijo) {
            super.giraDerecha(padre);
            aux = padre;
            padre = hijo;
            hijo = aux;
        }

        // Caso 5
        padre.color = Color.NEGRO;
        abuelo.color = Color.ROJO;
        if (abuelo.izquierdo == padre) {
            super.giraDerecha(abuelo);
        } else if (abuelo.derecho == padre) {
            super.giraIzquierda(abuelo);
        }
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene el
     * elemento, y recolorea y gira el árbol como sea necesario para rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        // Aquí va su código.
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la izquierda " + "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la clase,
     * porque se desbalancean.
     * 
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException(
                "Los árboles rojinegros no " + "pueden girar a la derecha " + "por el usuario.");
    }
}
