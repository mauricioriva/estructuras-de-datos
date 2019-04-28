package mx.unam.ciencias.edd.proyecto2;

/* Interfaz para estructuras graficas en SVG */
public interface EstructuraGrafica{

	/* Método que devuelve la altura del SVG */
	public int height(int longitud);

	/* Método que devuelve el ancho del SVG */
	public int width(int longitud);

	/* Método principal que crea el SVG */
	public void imprime();
}