package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Iterator;
import mx.unam.ciencias.edd.Conjunto;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;

/** Clase para interpretar los archivos */
public class Interprete {

    /* La direccion del archivo */
    private String archivo;

    /* Lista con todas las palabras del archivo */
    private Lista<Palabra> todasSinValor;

    /* Diccionario con todas las palabras del archivo y su numero de veces en él */
    private Diccionario<Palabra,Integer> todas;

    /* Diccionario con las 15 palabras más frecuentes del archivo */
    private Diccionario<Palabra,Integer> frecuentes;

    /* Conjunto de palabras del archivo, en el que para toda palabra
       en el conjunto, la palabra tiene una longitud de 7 letras o más */
    private Conjunto<Palabra> conjunto;

    /**
     * Contructor que inicializa la lista de las palabras
     * en el archivo y manda a llamar al método leeArchivo().
     * @param  archivo     La direccion del archivo a interpretar.
     * @throws IOException Excepcion que ocurre cuando hay un error
     *                     al leer el archivo.
     */
    public Interprete(String archivo) throws IOException {
        this.archivo = archivo;
        this.conjunto = new Conjunto<Palabra>();
        this.todasSinValor = leeArchivo();
        this.todas = conteo();
        this.frecuentes = algoritmoFrecuencia();
    }

    /**
     * Método que crea un flujo de entrada para interpretar
     * el archivo y va creando objetos de tipo Palabra y
     * los va agregando a una lista.
     * @return Una lista con todas las palabras en el archivo.
     * @throws IOException Excepcion que ocurre cuando hay un error
     *                     al leer el archivo.
     */
    private Lista<Palabra> leeArchivo() throws IOException {
        File f = new File(archivo);

        if (!f.exists()) throw new NoSuchElementException();

        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        Lista<Palabra> lista = new Lista<>();

        String linea;
        String[] aux;

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            aux = linea.split(" ");
            for (int i = 0 ; i < aux.length ; i++ ) {
                if (aux[i].equals("") || aux[i].equals("\n") || aux[i].equals(" ") || aux[i].equals("\t")) {
                    continue;
                } else {
                    lista.agrega(new Palabra(aux[i]));
                }
                //Agrega al Conjunto
                if (aux[i].length() >= 7)
                    this.conjunto.agrega(new Palabra(aux[i]));
            }
        }

        br.close();
        fr.close();

        if (lista.getElementos() == 0 ) throw new UnsupportedOperationException();

        return lista;
    }

    /**
     * Método que cuenta el numero de veces que aparece una palabra
     * en el archivo, en tiempo O(n).
     * @return Un diccionario con las palabras del archivo y cuantas veces
     *         aparece en el archivo.
     */
    private Diccionario<Palabra,Integer> conteo() {
        Diccionario<Palabra,Integer> frecuente = new Diccionario<>();
        //Aqui el conteo de palabras se hace en O(n), porque los métodos get() y
        // agrega() de diccionario son O(1).
        int cont = 0;
        for (Palabra wrd : this.todasSinValor ) {
            try {
                cont = frecuente.get(wrd);
            } catch (NoSuchElementException nse) {
                cont = 0;
            }
            frecuente.agrega(wrd,cont + 1);
        }
        return frecuente;
    }

    /**
     * Algoritmo que cuenta el numero de veces que aparece una palabra
     * en el archivo, y si es frecuente lo agrega a una lista con todas
     * las palabras mas frecuentes del archivo.
     * @return Una lista con las palabras más frecuentes del archivo.
     */
    private Diccionario<Palabra,Integer> algoritmoFrecuencia() {
        Diccionario<Palabra,Integer> frecuente = this.todas;
        //Solo guardaremos las 15 más frecuentes
        Diccionario<Palabra,Integer> aux = new Diccionario<>();
        Iterator<Palabra> i = null;
        Palabra masFrecuente = null;
        Palabra q = null;
        while (aux.getElementos() < 15) {
            i = frecuente.iteradorLlaves();
            masFrecuente = i.next();
            while (i.hasNext()) {
                q = i.next();
                if (frecuente.get(masFrecuente) > frecuente.get(q)) {
                    continue;
                } else {
                    masFrecuente = q;
                }
            }
            masFrecuente.setNum(frecuente.get(masFrecuente));
            aux.agrega(masFrecuente,frecuente.get(masFrecuente));
            frecuente.elimina(masFrecuente);
        }
        int a = 65;
        i = aux.iteradorLlaves();
        while (i.hasNext()) {
            masFrecuente = i.next();
            masFrecuente.identificador = 65;
            a++;
        }
        return aux;
    }

    /**
     * Getter que devuelve una lista con todas las palabras
     * del archivo.
     * @return Una lista con todas las palabras en el archivo.
     */
    public Diccionario<Palabra,Integer> getTodas() {
        return this.todas;
    }

    /**
     * Getter de que devuelve una lista con todas las palabras del archivo,
     * asi como fueron ingresadas.
     * @return Una lista con todas la palabras del archivo.
     */
    public Lista<Palabra> getTodasSinValor() {
        return this.todasSinValor;
    }

    /**
     * Getter que devuelve un diccionario con las palabras más
     * frecuentes del archivo.
     * @return Una lista con las palabras más frecuentes.
     */
    public Diccionario<Palabra,Integer> getFrecuentes() {
        return this.frecuentes;
    }

    /**
     * Getter que devuelve dirección del archivo el cual se
     * interpretó.
     * @return La dirección del archivo.
     */
    public String getArchivo() {
        return this.archivo;
    }

    /**
     * Getter que devuelve el conjunto con todas las palabras
     * @return Un conjunto con todas las palabras del archivo.
     */
    public Conjunto<Palabra> getConjunto() {
        return this.conjunto;
    }

    /**
     * Método toString para imprimir el nombre del archivo,
     * el número de palabras en el archivo y una lista con
     * las palabras.
     * @return Una cadena con el archivo, el número de palabras
     *         y una lista con las palabras.
     */
    @Override
    public String toString() {
        String res = "Numero de palabras en el archivo: " + (this.todas.getElementos() + 15)
        + "<br><br>Palabras más frecuentes[15]: " + this.frecuentes.toString();
        return res;
    }


}
