package mx.unam.ciencias.edd.proyecto3;

import java.io.IOException;
import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.Lista;

/** Clase que ejecuta el programa */
public class Proyecto3 {

    /**
     * Método main manda a llamar al método atrapa para las excepciones.
     * @param args Los archivos y el directorio de salida.
     */
    public static void main(String[] args) {
        atrapa(args);
    }

    /**
     * Método que atrapa todas las excepciones posibles del programa, y
     * manda a llamar al método que inicializa el programa.
     * @param args Los archivos y el directorio de salida.
     */
    public static void atrapa(String[] args) {
        try {
            corre(args);
        } catch(IllegalArgumentException iae) {
            System.err.println("No se ingresaron archivos para su lectura.");
        } catch(IOException ioe) {
            System.err.println("Ocurrió un error al leer el archivo, o no tienes permiso para escribir en el directorio de salida.");
        } catch(NoSuchElementException nse) {
            System.err.println("No se encontró el archivo, o el numero de palabras del archivo es menor a 15.");
        } catch (UnsupportedOperationException uoe) {
            System.err.println("El archivo está vacio.");
        } catch (NullPointerException npe) {
            System.err.println("Después de la bandera -o se tiene que ingresar un directorio.");
        } catch (Exception e) {
            System.err.println("Ocurrió un error desconocido.");
        }
    }

    /**
     * Método que hace todo el programa (main), crea todos los archivos
     * necesarios para el programa, todos los SVG y HTML.
     * @param  args        Los archivos y el directorio de salida.
     * @throws IOException Se lanza si hay algun error al crear o escribir en
     *                     los archivos.
     */
    public static void corre(String[] args) throws IOException {
        Entrada e = new Entrada(args);
        Lista<Interprete> interpretes = new Lista<>();
        for (String archivo : e.getArchivos() ) {
            interpretes.agrega(new Interprete(archivo));
        }
        Lista<Organizador> organize = new Lista<>();
        for (Interprete i : interpretes ) {
            organize.agrega(new Organizador(e.getFolder(),i));
        }
        OrganizaHTML organizador = new OrganizaHTML(organize);
        organizador.creaDirectorio(e.getFolder());
        OrganizaSVG files = new OrganizaSVG(organize);
        files.escribe();
    }

}
