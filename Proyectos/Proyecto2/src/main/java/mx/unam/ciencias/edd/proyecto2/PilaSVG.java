package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.MeteSaca;

/* Clase para representar Pilas con SVG */
public class PilaSVG implements EstructuraGrafica{

	/* Estructura que implementa MeteSaca */
	public MeteSaca<Integer> coleccion;
	public int numElementos;

	/* Constructor */
	public PilaSVG(MeteSaca<Integer> c, int numElementos) {
		coleccion = c;
		this.numElementos = numElementos;
	}

	/**
	 * Método que devuelve el ancho del SVG, es constante
	 * ya que no importa el numero de elementos en la pila.
	 * @param longitud Ancho del SVG
	 * @return Ancho SVG
	 */
	public int width(int longitud) {
		return longitud;
	}

	/**
	 * Método que devuelve la altura del SVG de acuerdo al numero
	 * de elementos.
	 * @param longitud numero de elementos en la pila.
	 * @return La altura del SVG
	 */
	public int height(int longitud) {
		if (longitud == 0) 
			return 0;
		else if (longitud == 1)
			return 25;
		else 
			return 25 + height(longitud - 1);
	}

	/**
	 * Método principal que crea el SVG dado su alto y ancho
	 * El numero de rectangulos es el numero de elementos.
	 */
	public void imprime() {
		String res = "<?xml version='1.0' encoding='UTF-8' ?>";
		int y = 0;
		res = "<svg width=\"" + width(50) + "\" height=\"" + height(numElementos) + "\">\n";
		for (int i = 0; i < numElementos; i++) {
			res = res + "\t<rect x=\""+ 0 +"\" y=\"" + y + "\" width=\"50\" height=\"25\" style=\"fill:white;stroke:white;stroke-width:2;stroke-opacity:1\" />\n";
			res = res + "\t<text fill='black' font-family='sans-serif' font-size='12' x='" + 25 + "' y='" + (y + 17) + "' text-anchor='middle'>" + coleccion.saca() + "</text>\n";
			y = y + 25;
		}
		res = res + "\t<line x1=\"5\" y1=\"2\" x2=\"" + 5 + "\" y2=\""+ (height(numElementos) - 2) +"\" style=\"stroke:blue;stroke-width:2\" />\n";
		res = res + "\t<line x1=\"45\" y1=\"2\" x2=\"" + 45 + "\" y2=\""+ (height(numElementos) -2) +"\" style=\"stroke:blue;stroke-width:2\" />\n";
		res = res + "</svg>";

		System.out.println(res);

	}

}