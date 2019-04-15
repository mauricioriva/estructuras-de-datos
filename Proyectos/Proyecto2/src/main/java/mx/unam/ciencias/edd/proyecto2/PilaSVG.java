package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.MeteSaca;

public class PilaSVG implements EstructuraGrafica{

	public MeteSaca<Integer> coleccion;
	public int numElementos;

	public PilaSVG(MeteSaca<Integer> c, int numElementos) {
		coleccion = c;
		this.numElementos = numElementos;
	}

	public int width(int longitud) {
		return longitud;
	}

	public int height(int longitud) {
		if (longitud == 0) 
			return 0;
		else if (longitud == 1)
			return 25;
		else 
			return 25 + height(longitud - 1);
	}

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