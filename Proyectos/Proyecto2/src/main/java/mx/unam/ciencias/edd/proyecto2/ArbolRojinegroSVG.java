package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/* Clase para crear Arboles Rojinegros en SVG */
public class ArbolRojinegroSVG implements ArbolGrafico{

	/* El Arbol Rojinegro a dibujar */
	public ArbolRojinegro<Integer> ab;
	public VerticeArbolBinario<Integer> izquierdo_raiz;
	public VerticeArbolBinario<Integer> derecho_raiz;

	/* Constructor */
	public ArbolRojinegroSVG(Coleccion<Integer> c) {
		ab = (ArbolRojinegro<Integer>) c;
		setRaizIzquierdoDerecho();
	}

	/**
	 * Con este método se obtiene el ancho del SVG.
	 * @param raiz La raiz del arbol
	 * @return El ancho del SVG
	 */
	public int width(VerticeArbolBinario<Integer> raiz) {
		int cont = ab.getElementos();
		int res = (int)(50 * Math.pow(2,ab.altura()));
		return res;
	}

	/**
	 * Este método te da la altura del SVG
	 * @param alturaRaiz La altura de la raiz del arbol
	 * @return La altura del SVG
	 */
	public int height(int alturaRaiz) {
		if (alturaRaiz == 0) {
			return 50;
		} else if (alturaRaiz == 1) {
			return 150;
		} else {
			return 100 + height(alturaRaiz - 1);
		}
	}

	/**
	 * Método principal que crea el SVG, con su alto y ancho
	 */
	public void imprime() {
		int num = ab.getElementos();
		if (num == 0){
			System.out.println("El arbol vacio");
			return;
		}

		int height = height(ab.altura()) + 5;

		int width = 3 * width(ab.raiz());
		
		String res = "<?xml version='1.0' encoding='UTF-8' ?>\n";
		res = res + "<svg width=\"" + width + "\" height=\"" + (height + 5) + "\">\n";

		res = res + conexiones(width,ab.raiz(), width / 2, 30);

		System.out.println(res + "</svg>");

	}

	/**
	 * Método recursivo para obtener las coordenadas de los vértices, es recursivo.
	 * @param diff Es la distancia que hay entre 2 hijos.
	 * @param padre El vertice padre.
	 * @param xpadre La coordenada en x del padre.
	 * @param ypadre La coordenada en y del padre.
	 * @return La cadena de SVG que representa el arbol con sus conexiones y vertices.
	 */
	public String conexiones(int diff, VerticeArbolBinario<Integer> padre, int xpadre, int ypadre) {
		String res = "";
		if (padre.hayIzquierdo()) {
			res = res + conecta(xpadre,ypadre,(xpadre - (diff/4)), ypadre + 100);
			res = res + dibuja(padre,xpadre,ypadre);
			res = res + dibuja(padre.izquierdo(),(xpadre - (diff/4)), ypadre + 100);
			res = res + conexiones(diff/2, padre.izquierdo(), (xpadre - (diff/4)), ypadre + 100);

		}
		if (padre.hayDerecho()) {
			res = res + conecta(xpadre,ypadre,(xpadre + (diff/4)), ypadre + 100);
			res = res + dibuja(padre,xpadre,ypadre);
			res = res + dibuja(padre.derecho(),(xpadre + (diff/4)), ypadre + 100);
			res = res + conexiones(diff/2, padre.derecho(), (xpadre + (diff/4)), ypadre + 100);
		}
		return res;
	}

	/**
	 * Este método facilita el poder acceder a los vertices izquierdo
	 * y derecho de la raiz.
	 */
	public void setRaizIzquierdoDerecho() {
		if (ab.getElementos() > 1) {
			if (ab.raiz().hayIzquierdo())
				this.izquierdo_raiz = ab.raiz().izquierdo();
			if (ab.raiz().hayDerecho())
				this.derecho_raiz = ab.raiz().derecho();
		}
	}

	/**
	 * Método que crea una linea entre 2 coordendas (x,y) en SVG
	 * @param x1 Coordenada en x del primer vertice.
	 * @param y1 Coordenada en y del primer vertice.
	 * @param x2 Coordenada en x del segundo vertice.
	 * @param y2 Coordenada en y del segundo vertice.
	 * @return La linea en SVG que va de un vertice a otro.
	 */
	public String conecta(double x1, int y1, double x2, int y2) {
		String s = "";
		s = "\t<line x1='" + x1 + "' y1='" + y1 + "' x2='" + x2 + "' y2='" + y2 + "' stroke='black' stroke-width='3' />\n";
		return s;
	}

	/**
	 * Método que crea una representacion de los vertices (se usan circulos)
	 * @param v El vertice a dibujar que tiene propiedades como su elemento, y su color.
	 * @param x Coordenada en x del vertice.
	 * @param y Coordenada en y del vertice.
	 * @return
	 */
	public String dibuja(VerticeArbolBinario<Integer> v, double x, int y) {
		//Dibuja el vertice con las cordenadas pasadas
		String s = "";
		String c = "";
		if (ab.getColor(v) == Color.ROJO) c = "red";
		else c = "black";
		s = "\t<circle cx='" + x + "' cy='" + y + "' r='25' stroke='"+ c +"' stroke-width='3' fill='"+ c +"' />\n";
		s = s + "\t<text fill='white' font-family='sans-serif' font-size='16' x='" + x + "' y='"+ (y + 6) + "' text-anchor='middle'>" + v.get() + "</text>\n";
		return s;
	}

}
