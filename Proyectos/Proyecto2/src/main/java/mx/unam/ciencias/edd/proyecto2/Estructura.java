package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class Estructura {
	
	public Entrada e;
	public Coleccion<Integer> coleccion;
	public EstructuraGrafica estructuraSVG;
	public MeteSaca<Integer> pila_cola;
	public int numElementos;

	public Estructura(Entrada entrada) {
		this.e = entrada;
		actua();
	}

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

	public void actua() {
		switch (e.estructura) {
			case "ArbolBinario":
				ArbolBinarioOrdenado<Integer> ab = new ArbolBinarioOrdenado<>();
				coleccion = ab;
				break;
			case "ArbolBinarioOrdenado":
				ArbolBinarioOrdenado<Integer> abo = new ArbolBinarioOrdenado<>();
				coleccion = abo;
				break;
			case "ArbolBinarioCompleto":
				ArbolBinarioCompleto<Integer> abc = new ArbolBinarioCompleto<>();
				coleccion = abc;		
				break;
			case "ArbolRojinegro":
				ArbolRojinegro<Integer> ar = new ArbolRojinegro<>();
				coleccion = ar;
				break;
			case "ArbolAVL":
				ArbolAVL<Integer> avl = new ArbolAVL<>();
				coleccion = avl;
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
			/*case "Monticulo Minimo":
				MonticuloMinimo<Integer> mm = new MonticuloMinimo<Integer>();
				coleccion = mm;
				break;
			case "Monticulo Arreglo":
				MonticuloArreglo<Integer> ma = new MonticuloArreglo<>();
				monticulo = ma;
				break;*/
			default:
				throw new IllegalArgumentException();
		}
	}

	public String toString(){
		return coleccion.toString();
	}

}