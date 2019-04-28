package mx.unam.ciencias.edd.proyecto2;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/* Clase que procesa la entrada del usuario */
public class Entrada {
	
	/* Cadena limpia sin almohadillas ni espacios */
	public String cadena;
	/* Un arreglo con los datos de la cadena */
	public String [] entrada;
	/* El nombre de la estructura */
	public String estructura;
	/* El arreglo con los elementos */
	public int [] elementos;

	/* Constructor */
	public Entrada() {
		cadena = "";
		run();
		entrada = cadena.split(" ");
		estructura = entrada[0];
		elementos = numeros();
	}

	/**
	 * Método principal que crea un flujo para leer los datos de entrada
	 * del usuario.
	 */
	public void run() {
		System.setErr(System.out);
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String aux;
			while((aux = br.readLine()) != null)
				cadena = cadena + analiza(aux);
		} catch (IOException ioe) {
			System.err.println("Error al leer el archivo.");
		}
	}

	/**
	 * Método que recibe una cadena y analiza si contiene almohadillas, espacios extra,
	 * tabuladores, saltos de linea, etc y los quita.
	 * @param s Cadena a analizar
	 * @return La cadena sin espacios extra, e ignorando todo lo que sigue de la almohadilla
	 */
	public String analiza(String s) {
		s = s.trim();
		int k = s.indexOf("#");
		if (k == -1)
			return s + " ";
		if (k == 0)
			return "";
		else
			return s.substring(0,k) + " ";
	}

	/**
	 * Método que convierte un arreglo de String en uno
	 * de enteros.
	 * @return Un arreglo de enteros con los datos del usuario.
	 */
	public int [] numeros() {
		int [] num = new int[entrada.length - 1];
		for (int i = 0 ; i < num.length ; i++) {
			num[i] = Integer.parseInt(entrada[i+1]);
		}
		return num;
	}

	/**
	 * @return La cadena con la estructura y sus elementos.
	 */
	public String toString() {
		return cadena;
	}

}