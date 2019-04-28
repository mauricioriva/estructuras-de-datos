package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/* Interfaz para dibujar Arboles Binarios en SVG */
public interface ArbolGrafico{

	/* La altura del SVG */
	public int height(int alturaRaiz);

	/* El ancho del SVG */
	public int width(VerticeArbolBinario<Integer> raiz);

	/* Método principal para dibujar los arboles */
	public void imprime();
}