package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;

public class ArregloSVG implements EstructuraGrafica{

	public Coleccion<Integer> coleccion;

	public ArregloSVG(Coleccion<Integer> c) {
		coleccion = c;
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
		return 45;
	}

	public void imprime() {
		String res = "<?xml version='1.0' encoding='UTF-8' ?>";
		int x = 0;
		int cont = 0;
		res = "<svg width=\"" + width(coleccion.getElementos()) + "\" height=\"" + height(0) + "\">\n";
		res = res + "\t<rect x=\""+ 0 +"\" y=\"0\" width=\"" + width(coleccion.getElementos()) + "\" height=\"45\" style=\"fill:white;stroke:white;stroke-width:2;stroke-opacity:1\" />\n";
		for (int i : coleccion ) {
			res = res + "\t<rect x=\""+ x +"\" y=\"19\" width=\"50\" height=\"25\" style=\"fill:white;stroke:blue;stroke-width:2;stroke-opacity:1\" />\n";
			res = res + "\t<text fill='black' font-family='sans-serif' font-size='12' x='" + (x + 25) + "' y='37' text-anchor='middle'>" + i + "</text>\n";
			res = res + "\t<text fill='black' font-family='sans-serif' font-size='12' x='" + (x + 25) + "' y='14' text-anchor='middle'>" + cont + "</text>\n";
			x = x + 50;
			cont++;
		}
		x = 50;
		res = res + "</svg>";

		System.out.println(res);

	}

}