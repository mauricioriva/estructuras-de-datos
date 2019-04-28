package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/* Clase para crear Arboles Binarios Completos */
public class ArbolBinarioCompletoSVG extends ArbolBinarioSVG implements ArbolGrafico {

	/* Constructor */
	public ArbolBinarioCompletoSVG(Coleccion<Integer> c) {
		super.ab = (ArbolBinarioCompleto<Integer>) c;
		super.setRaizIzquierdoDerecho();
	}

}