package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/* Clase para el indice y sus referencias a otros archivos */
public class OrganizaHTML {

    /* Lista con todas las caracteristicas de cada archivo */
    Lista<Organizador> lista;

    /**
     * Constructor que incializa la lista
     * @param l La lista con la propiedades de los archivos
     */
    public OrganizaHTML(Lista<Organizador> l) {
        lista = l;
    }

    /**
     * Crea el indice html, con las referencias a otros archivos.
     * @param  folder      Ubicacion para guardar el indice.
     * @throws IOException Excepcion si hay error al crear el archivo.
     */
    public void creaDirectorio(String folder) throws IOException {
        File f = new File(folder,"index.html");
        f.createNewFile();
        File g = asignaSVG(folder);
        indice(f,g);
    }

    /**
     * Agrega todo el contenido al archivo index.html
     * @param  indice      El archivo index.html
     * @throws IOException Excepcion si ocurre algun error con
     *                     los flujos al escribir en el archivo.
     */
    private void indice(File indice, File grafica) throws IOException {
        FileWriter fw = new FileWriter(indice);
        BufferedWriter bw = new BufferedWriter(fw);
        HTMLCode h = new HTMLCode();
        bw.write(h.inicio());
        bw.write(h.titulo("Reporte de Archivos"));
        int c = 1;
        for (Organizador o : lista ) {
            bw.write(h.enlace(o.html.getAbsolutePath(),o.nombreArchivo,c));
            c++;
        }
        bw.write(h.imagen(grafica.getAbsolutePath()));
        bw.write(h.finalizar());
        bw.close();
        fw.close();
    }

    /**
     * Método que crea el SVG de los archivos.
     * @param  folder      La direccion de salida.
     * @return             Un File con todo el contenido del SVG.
     * @throws IOException Por si ocurre algun error al escribir en el archivo
     *                     o al crearlo.
     */
    private File asignaSVG(String folder) throws IOException {
        File g = new File(folder,"Grafica-Archivos.svg");
        g.createNewFile();
        GraficaSVG grafica = new GraficaSVG(lista);
        //Crear el objeto grafica
        FileWriter fw = new FileWriter(g);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(grafica.imprime()); //Aqui falta agregar la gráfica
        bw.close();
        fw.close();
        return g;
    }

}
