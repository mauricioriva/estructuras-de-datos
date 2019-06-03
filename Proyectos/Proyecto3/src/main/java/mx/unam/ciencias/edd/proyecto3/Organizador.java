package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/** Clase envolvente para los interpretes */
public class Organizador {

    /* El nombre del archivo sin extension */
    String nombreArchivo;

    /* File con la ubicacion del archivo html */
    File html;

    /* File para el archivo svg */
    File arbolRojinegro;

    /* File para el arbol avl */
    File arbolAVL;

    /* File para la gráfica de pastel */
    File graficaPastel;

    /* File para la grafica de barras */
    File graficaBarras;

    /* El interprete del archivo */
    private Interprete contenido;

    /**
     * Constructor, crea todos los archivos html y svg por cada archivo
     * ingresado.
     * @param  folder      El directorio donde se guardan todos los archivos.
     * @param  i           Un interprete con el contenido del archivo, usando
     *                     estructuras de datos.
     * @throws IOException Excepcion por si hay algun error al crear los archivos.
     */
    public Organizador(String folder, Interprete i) throws IOException {
        this.contenido = i;
        this.nombreArchivo = sinExtension();
        asigna(folder);
        creaArchivos();
        dibujaArboles();
        dibujaBarras();
        dibujaPastel();
    }

    /**
     * Método que obtiene el nombre del archivo, sin su extension.
     * @return El nombre del archivo.
     */
    private String sinExtension() {
        String s = this.contenido.getArchivo();
        File f = new File(s);
        s = f.getName();
        int aux = s.lastIndexOf(".");
        if (aux != -1) {
            return s.substring(0,aux);
        }
        return s;
    }

    /**
     * Método que asigna los archivos en la ubicacion especificada,
     * con su respectivo nombre y extension correspondiente.
     * @param folder La direccion de la carpeta para guardar los archivos.
     */
    private void asigna(String folder) {
        this.html = new File(folder, this.nombreArchivo + ".html");
        this.arbolRojinegro = new File(folder, this.nombreArchivo + "-ArbolRojinegro.svg");
        this.arbolAVL = new File(folder, this.nombreArchivo + "-ArbolAVL.svg");
        this.graficaPastel = new File(folder, this.nombreArchivo + "-GraficaPastel.svg");
        this.graficaBarras = new File(folder, this.nombreArchivo + "-GraficaBarras.svg");
    }

    /**
     * Método que propiamente crea los archivos en sus rutas especificadas
     * anteriormente y sus nombres.
     * @throws IOException Excepcion por si ocurre algún error al crear los archivos.
     */
    private void creaArchivos() throws IOException {
        this.html.createNewFile();
        this.arbolRojinegro.createNewFile();
        this.arbolAVL.createNewFile();
        this.graficaPastel.createNewFile();
        this.graficaBarras.createNewFile();
    }

    /**
     * Getter que devuelve el contenido del archivo.
     * @return El contenido del archivo.
     */
    public Interprete getContenido() {
        return this.contenido;
    }

    /**
     * Método que genera el codigo SVG en su respectivo archivo, con
     * su respectiva estructura.
     * @throws IOException Excepcion por si ocurre algún error al escribir el SVG.
     */
    private void dibujaArboles() throws IOException {
        ArbolRojinegroSVG ab = new ArbolRojinegroSVG(contenido.getFrecuentes());
        FileWriter fw = new FileWriter(this.arbolRojinegro);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(ab.imprime());
        bw.close();
        fw.close();

        ArbolAVLSVG ar = new ArbolAVLSVG(contenido.getFrecuentes());
        fw = new FileWriter(this.arbolAVL);
        bw = new BufferedWriter(fw);
        bw.write(ar.imprime());
        bw.close();
        fw.close();
    }

    /**
     * Método que genera el codigo SVG en su respectivo archivo, con
     * su respectiva estructura.
     * @throws IOException Excepcion por si ocurre algún error al escribir el SVG.
     */
    private void dibujaBarras() throws IOException {
        GraficaBarrasSVG g = new GraficaBarrasSVG(contenido.getFrecuentes());
        FileWriter fw = new FileWriter(this.graficaBarras);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(g.imprime());
        bw.close();
        fw.close();
    }

    private void dibujaPastel() throws IOException {
        GraficaPastelSVG g = new GraficaPastelSVG(contenido.getFrecuentes());
        FileWriter fw = new FileWriter(this.graficaPastel);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(g.imprime());
        bw.close();
        fw.close();
    }
}
