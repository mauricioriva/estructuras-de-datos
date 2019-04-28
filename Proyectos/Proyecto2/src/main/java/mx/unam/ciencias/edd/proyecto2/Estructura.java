package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/* Clase para organizar las estructuras */
public class Estructura {
	
	public Entrada e;
	public Coleccion<Integer> coleccion;
	public EstructuraGrafica estructuraSVG;
	public ArbolGrafico arbolSVG;
	public MeteSaca<Integer> pila_cola;
	public int numElementos;

	/* Constructor */
	public Estructura(Entrada entrada) {
		this.e = entrada;
		actua();
	}

	/**
	 * Método que ingresa los datos a la estructura.
	 */
	public void datosEstructura() {
		if (coleccion != null)
			for (int i : e.elementos)
				coleccion.agrega(i);

		else if (pila_cola != null)
			for (int i : e.elementos){
				pila_cola.mete(i);
				numElementos++;
			}

	}

	/**
	 * Método principal estructural el cual crea un objeto para representar una 
	 * estructura y generar su propio codigo SVG.
	 */
	public void actua() {
		switch (e.estructura) {
			case "ArbolBinario":
				ArbolBinarioOrdenado<Integer> ab = new ArbolBinarioOrdenado<>();
				coleccion = ab;
				datosEstructura();
				arbolSVG = new ArbolBinarioOrdenadoSVG(coleccion);
				break;
			case "ArbolBinarioOrdenado":
				ArbolBinarioOrdenado<Integer> abo = new ArbolBinarioOrdenado<>();
				coleccion = abo;
				datosEstructura();
				arbolSVG = new ArbolBinarioOrdenadoSVG(coleccion);
				break;
			case "ArbolBinarioCompleto":
				ArbolBinarioCompleto<Integer> abc = new ArbolBinarioCompleto<>();
				coleccion = abc;
				datosEstructura();
				arbolSVG = new ArbolBinarioCompletoSVG(coleccion);
				break;
			case "ArbolRojinegro":
				ArbolRojinegro<Integer> ar = new ArbolRojinegro<>();
				coleccion = ar;
				datosEstructura();
				arbolSVG = new ArbolRojinegroSVG(coleccion);
				break;
			case "ArbolAVL":
				ArbolAVL<Integer> avl = new ArbolAVL<>();
				coleccion = avl;
				datosEstructura();
				arbolSVG = new ArbolAVLSVG(coleccion);
				break;
			case "Lista":
				Lista<Integer> l = new Lista<>();
				coleccion = l;
				datosEstructura();
				estructuraSVG = new ListaSVG(coleccion);
				break;
			case "Grafica":
				Grafica<Integer> g = new Grafica<>();
				coleccion = g;
				break;
			case "Pila":
				Pila<Integer> p = new Pila<>();
				pila_cola = p;
				datosEstructura();
				estructuraSVG = new PilaSVG(pila_cola,numElementos);
				break;
			case "Cola":
				Cola<Integer> c = new Cola<>();
				pila_cola = c;
				datosEstructura();
				estructuraSVG = new ColaSVG(pila_cola,numElementos);
				break;
			case "Arreglo":
				Lista<Integer> a = new Lista<>();
				coleccion = a;
				datosEstructura();
				estructuraSVG = new ArregloSVG(coleccion);
				break;
			case "MonticuloMinimo":
				Lista<ValorIndexable<Integer>> listam = new Lista<>();
				ValorIndexable<Integer> v;
				for (int i : e.elementos ) {
					v = new ValorIndexable<Integer>(i,i*2);
					listam.agrega(v);
				}
				MonticuloMinimo<ValorIndexable<Integer>> mm = new MonticuloMinimo<>(listam);
				ArbolBinarioCompleto<Integer> abcd = new ArbolBinarioCompleto<Integer>();
				for (ValorIndexable<Integer> va : mm ) {
					abcd.agrega(va.getElemento());
				}
				coleccion = abcd;
				arbolSVG = new ArbolBinarioCompletoSVG(abcd);
				break;
			case "MonticuloArreglo":
				Lista<ValorIndexable<Integer>> lis = new Lista<>();
				ValorIndexable<Integer> vd;
				for (int i : e.elementos ) {
					vd = new ValorIndexable<Integer>(i,i*2);
					lis.agrega(vd);
				}
				MonticuloMinimo<ValorIndexable<Integer>> mmm = new MonticuloMinimo<>(lis);
				ArbolBinarioCompleto<Integer> abcde = new ArbolBinarioCompleto<Integer>();
				for (ValorIndexable<Integer> va : mmm ) {
					abcde.agrega(va.getElemento());
				}
				coleccion = abcde;
				estructuraSVG = new ArregloSVG(abcde);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Imprime la coleccion a representar.
	 */
	public String toString(){
		return coleccion.toString();
	}

}
