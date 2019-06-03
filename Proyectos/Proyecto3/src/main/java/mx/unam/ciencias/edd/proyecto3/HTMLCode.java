package mx.unam.ciencias.edd.proyecto3;

/** Clase con el codigo HTML */
public class HTMLCode {

    /* Constructor vacio */
    public HTMLCode() {}

    /**
     * Método que escribe el inicio de una pagina HTML
     * @return El inicio de un archivo HTML.
     */
    public String inicio() {
        return "<!DOCTYPE html>\n<html>\n<body>";
    }

    /**
     * Método que genera un titulo en HTML.
     * @param  s El titulo
     * @return   El titulo en HTML
     */
    public String titulo(String s) {
        return "\n<h1>" + s +"</h1>";
    }

    /**
     * Método que genera texto en un archivo HTML
     * @param  s El mensaje a escribir
     * @return   El mensaje en HTML.
     */
    public String texto(String s) {
        return "\n<p>" + s + "</p>";
    }

    /**
     * Método que crea un enlace en un archivo HTML
     * @param  direccion La direccion del enlace
     * @param  nombre    El mensaje del enlace
     * @param  num       Numero de enlace.
     * @return           Un enlace en codigo HTML para redireccionar.
     */
    public String enlace(String direccion, String nombre, int num) {
        return "\n<p>" + num + ": " + "<a href=\"" + direccion + "\">" + nombre + "</a></p>";
    }

    /**
     * Método que crea una imagen de un archivo.
     * @param  archivo La direccion del archivo
     * @return         Una imagen en codigo HTML de un archivo.
     */
    public String imagen(String archivo) {
        return "\n<br><br><img width=\"1300px\" src=\"" + archivo + "\"></img><br><br>";
    }

    /**
     * Método que finaliza un archivo HTML
     * @return Codigo para terminar el archivo HTML
     */
    public String finalizar() {
        return "\n</body>\n</html>";
    }

}
