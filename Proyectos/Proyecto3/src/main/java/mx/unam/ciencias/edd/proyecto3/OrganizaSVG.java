package mx.unam.ciencias.edd.proyecto3;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import mx.unam.ciencias.edd.Lista;

/* Clase para asignar el codigo SVG al archivo */
public class OrganizaSVG {

    /* Lista con todas las caracteristicas de cada archivo */
    Lista<Organizador> lista;

    /**
     * Constructor que asigna la lista de Organizadores
     * @param l La lista de Organizadores
     */
    public OrganizaSVG(Lista<Organizador> l) {
        this.lista = l;
    }

    /**
     * Método que escribe el código HTML en el archivo.
     * @throws IOException Excepcion por si ocurre algun error al escribir
     *                     el codigo HTML.
     */
    public void escribe() throws IOException {
        File f = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        HTMLCode h = new HTMLCode();
        for (Organizador o : lista ) {
            f = o.html;
            fw = new FileWriter(f);
            bw = new BufferedWriter(fw);
            bw.write(h.inicio());
            bw.write(h.titulo(o.nombreArchivo));
            bw.write(h.texto(o.getContenido().toString()));
            bw.write(h.imagen(o.graficaBarras.getAbsolutePath()));
            bw.write(h.imagen(o.graficaPastel.getAbsolutePath()));
            bw.write(h.imagen(o.arbolRojinegro.getAbsolutePath()));
            bw.write(h.imagen(o.arbolAVL.getAbsolutePath()));
            bw.write(h.texto("Todas las demás palabras del archivo[" + o.getContenido().getTodas().getElementos() + "]: "));
            bw.write(h.texto(o.getContenido().getTodas().toString()));
            bw.write(h.texto("El conjunto de palabras del archivo[" + o.getContenido().getConjunto().getElementos() + "]:"));
            bw.write(h.texto(o.getContenido().getConjunto().toString()));
            bw.write(h.finalizar());
            bw.close();
            fw.close();
        }
    }

}
