package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/* Clase para crear Arboles Binarios Ordenados */
public class ArbolBinarioOrdenadoSVG extends ArbolBinarioSVG implements ArbolGrafico {

	/* Constructor */
	public ArbolBinarioOrdenadoSVG(Coleccion<Integer> c) {
		super.ab = (ArbolBinarioOrdenado<Integer>) c;
		super.setRaizIzquierdoDerecho();
	}

}