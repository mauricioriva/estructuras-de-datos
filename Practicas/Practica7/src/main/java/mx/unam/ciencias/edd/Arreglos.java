package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, 0, arreglo.length - 1, comparador);
    }

    private static <T> void quickSort(T[] arreglo, int a, int b, Comparator<T> comparador) {
        if (b <= a)
            return;
        int i = a + 1;
        int j = b;
        while (i < j) {
            if ((comparador.compare(arreglo[i], arreglo[a]) > 0) && (comparador.compare(arreglo[j], arreglo[a])) <= 0) {
                intercambia(arreglo, i, j);
                i++;
                j--;
            } else if ((comparador.compare(arreglo[i], arreglo[a])) <= 0)
                i++;
            else
                j--;
        }
        if (comparador.compare(arreglo[i], arreglo[a]) > 0) {
            i--;
        }
        intercambia(arreglo, a, i);
        quickSort(arreglo, a, i - 1, comparador);
        quickSort(arreglo, i + 1, b, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void selectionSort(T[] arreglo, Comparator<T> comparador) {
        int m = 0;
        for (int i = 0; i < arreglo.length; i++) {
            m = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if ((comparador.compare(arreglo[j], arreglo[m])) < 0) {
                    m = j;
                }
            }
            intercambia(arreglo, i, m);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        return busquedaBinaria(0, arreglo.length - 1, arreglo, elemento, comparador);
    }

    /**
     * Método auxiliar que parte el arreglo en 2 hasta encontrar el indice del
     * elemento
     * 
     * @param init       Indice inicial del arreglo o del subarreglo
     * @param fin        Indice final del arreglo o del subarreglo
     * @param arreglo    El arreglo al que se le aplica la busqueda
     * @param elemento   Elemento a buscar en el arreglo
     * @param comparador Objeto que se usa para comparar los elementos internos del
     *                   arreglo
     * @return El indice del elemento en el arreglo o -1 si no se encuentra
     */
    private static <T> int busquedaBinaria(int init, int fin, T[] arreglo, T elemento, Comparator<T> comparador) {
        int m = init + ((fin - init) / 2);
        if (fin - init == 0)
            return -1;
        if (comparador.compare(elemento, arreglo[init]) == 0)
            return init;
        if (comparador.compare(elemento, arreglo[fin]) == 0)
            return fin;
        if (comparador.compare(elemento, arreglo[m]) == 0)
            return m;
        if (comparador.compare(elemento, arreglo[m]) > 0)
            return busquedaBinaria(m + 1, fin, arreglo, elemento, comparador);
        else
            return busquedaBinaria(init, m, arreglo, elemento, comparador);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Intercambia los elemento del primer indice con el segundo.
     * 
     * @param arreglo Arreglo de tipo generico
     * @param a       Primer indice
     * @param b       Segundo indice
     */
    private static <T> void intercambia(T[] arreglo, int a, int b) {
        T aux = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = aux;
    }
}
