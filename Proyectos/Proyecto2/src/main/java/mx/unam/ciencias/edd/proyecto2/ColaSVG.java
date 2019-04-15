package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.MeteSaca;

public class ColaSVG implements EstructuraGrafica{

	public MeteSaca<Integer> coleccion;
	public int numElementos;

	public ColaSVG(MeteSaca<Integer> c, int numElementos) {
		coleccion = c;
		this.numElementos = numElementos;
	}

	public int width(int longitud) {
		if (longitud == 0) 
			return 0;
		else if (longitud == 1)
			return 50;
		else 
			return 50 + width(longitud - 1);
	}

	public int height(int longitud) {
		return longitud;
	}

	public void imprime() {
		String res = "<?xml version='1.0' encoding='UTF-8' ?>";
		int x = width(numElementos) - 50;
		res = "<svg width=\"" + width(numElementos) + "\" height=\"" + height(25) + "\">\n";
		for (int i = 0; i < numElementos; i++) {
			res = res + "\t<rect x=\""+ x +"\" y=\"0\" width=\"50\" height=\"25\" style=\"fill:white;stroke:white;stroke-width:2;stroke-opacity:1\" />\n";
			res = res + "\t<text fill='black' font-family='sans-serif' font-size='12' x='" + (x + 25) + "' y='17' text-anchor='middle'>" + coleccion.saca() + "</text>\n";
			x = x - 50;
		}
		res = res + "\t<line x1=\"2\" y1=\"2\" x2=\"" + (width(numElementos) - 2) + "\" y2=\"2\" style=\"stroke:blue;stroke-width:1\" />\n";
		res = res + "\t<line x1=\"2\" y1=\"23\" x2=\"" + (width(numElementos) - 2) + "\" y2=\"23\" style=\"stroke:blue;stroke-width:1\" />\n";
		res = res + "</svg>";

		System.out.println(res);

	}

}