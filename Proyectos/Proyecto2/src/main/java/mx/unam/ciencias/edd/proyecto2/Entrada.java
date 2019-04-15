package mx.unam.ciencias.edd.proyecto2;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Entrada {
	
	public String cadena;
	public String [] entrada;
	public String estructura;
	public int [] elementos;

	public Entrada() {
		cadena = "";
		run();
		entrada = cadena.split(" ");
		estructura = entrada[0];
		elementos = numeros();
	}

	public void run() {
		
		System.setErr(System.out);

		try {

			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String aux;
			while((aux = br.readLine()) != null)
				cadena = cadena + analiza(aux);
			cadena = cadena.substring(1,cadena.length());

		} catch (IOException ioe) {
			System.err.println("Error al leer el archivo.");
		}
	}

	public String analiza(String s) {
		s = s.trim();
		int k = s.indexOf("#");
		if (k == -1)
			return s;
		return s.substring(0,k) + " ";
	}

	public int [] numeros() {
		int [] num = new int[entrada.length - 1];
		for (int i = 0 ; i < num.length ; i++) {
			num[i] = Integer.parseInt(entrada[i+1]);
		}
		return num;
	}

	public String toString() {
		return cadena;
	}

}