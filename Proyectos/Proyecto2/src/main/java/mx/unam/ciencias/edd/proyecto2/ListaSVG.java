package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Coleccion;

public class ListaSVG implements EstructuraGrafica{

	public Coleccion<Integer> coleccion;

	public ListaSVG(Coleccion<Integer> c) {
		coleccion = c;
	}

	public int width(int longitud) {
		if (longitud == 0) 
			return 0;
		else if (longitud == 1)
			return 50;
		else 
			return 70 + width(longitud - 1);
	}

	public int height(int longitud) {
		return 25;
	}

	public void imprime() {
		String res = "<?xml version='1.0' encoding='UTF-8' ?>";
		int x = 0;
		res = "<svg width=\"" + width(coleccion.getElementos()) + "\" height=\"" + height(0) + "\">\n";
		for (int i : coleccion ) {
			res = res + "\t<rect x=\""+ x +"\" y=\"0\" width=\"50\" height=\"25\" style=\"fill:white;stroke:blue;stroke-width:2;stroke-opacity:1\" />\n";
			res = res + "\t<text fill='black' font-family='sans-serif' font-size='12' x='" + (x + 25) + "' y='17' text-anchor='middle'>" + i + "</text>\n";
			x = x + 70;
		}
		x = 50;

		for (int i = 0; i < coleccion.getElementos() ; i++) {
			res = res + "\t<rect x=\"" + x + "\" y=\"0\" width=\"20\" height=\"25\" style=\"fill:white\" />\n";
			res = res + "\t<polygon points=\"" + (x + 2) + ",12 " + (x + 4) + ",10 " + (x + 4) + ",14 \"/>\n";
			res = res + "\t<polygon points=\"" + (x + 18) + ",12 " + (x + 16) + ",10 " + (x + 16) + ",14 \"/>\n";
			res = res + "\t<rect x=\"" + (x + 3.9) + "\" y=\"11.5\" width=\"12.5\" height=\"1\" style=\"fill:black\" />\n";
			x = x + 70;
		}
		res = res + "</svg>";

		System.out.println(res);

	}

}