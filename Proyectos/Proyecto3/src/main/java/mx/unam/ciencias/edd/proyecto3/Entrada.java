package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import java.io.File;

/** Clase para interpretar la entrada ARGS */
public class Entrada {

    /* Lista con la direccion de los archivos */
    private Lista<String> archivos;

    /* Direccion de salida */
    private String folder;

    /**
     * Constructor que inicializa la lista de archivos
     * y manda a llamar al método define.
     * @param args Arreglo con la direccion de los archivos
     *             de entrada y la direccion de salida.
     */
    public Entrada(String[] args) {
        this.archivos = new Lista<String>();
        define(args);
        comprueba();
    }

    /**
     * Método que diferencía los archivos de entrada de la
     * direccion de salida denotada por la bandera -o.
     * @param args Arreglo con la direccion de los archivos
     *             de entrada y la direccion de salida.
     */
    private void define(String[] args) {
        System.setErr(System.out);
        if (args.length == 0) throw new IllegalArgumentException();
        for (int i = 0; i < args.length ; i++ ) {
            if (args[i].equals("-o")) {
                if (i + 1 < args.length)
                    this.folder = args[i + 1];
            } else {
                if (i != 0) {
                    if (!args[i-1].equals("-o")) {
                        this.archivos.agrega(args[i]);
                    }
                } else {
                    if (!args[i].equals("-o")) {
                        this.archivos.agrega(args[i]);
                    }
                }
            }
        }
        if (this.archivos.getElementos() == 0) throw new IllegalArgumentException();
    }

    /**
     * Método que comprueba el estado de la entrada, que los archivos sean realmente
     * archivos y que la salida sea un directorio.
     */
    private void comprueba() {
        File f = new File(folder);
        if (!f.exists())
            f.mkdirs();
        else if (!f.isDirectory())
            throw new NullPointerException();
        File[] k = f.listFiles();
        if (k.length != 0)
            for (File e : k ) {
                e.delete();
            }
        for (String archivo : archivos ) {
            if (!new File(archivo).isFile()) throw new NumberFormatException();
        }
    }

    /**
     * Getter para la obtener la direccion de salida.
     * @return La direccion de salida de los archivos.
     */
    public String getFolder() {
        return this.folder;
    }

    /**
     * Getter que devuelve la lista con los archivos de entrada.
     * @return Lista con los archivos de entrada.
     */
    public Lista<String> getArchivos() {
        return this.archivos;
    }

    /**
     * Método toString para corroborar la lectura de la entrada.
     * @return Los archivos de entrada y la direccion de salida.
     */
    @Override
    public String toString() {
        String res = "";
        if (folder != null)
            res = "\nEl directorio es: " + folder + "\n";

        int c = 1;
        for (String archivo : this.archivos ) {
            res = res + "Archivo " + c + ": " + archivo + "\n";
            c++;
        }
        return res;
    }

}
