package mx.unam.ciencias.edd.proyecto2;

public class Proyecto2 {
	
	public static void main(String[] args) {
		run();
	}

	public static void ejecuta() {
		Entrada e = new Entrada();
		Estructura s = new Estructura(e);
		s.estructuraSVG.imprime();
	}

	public static void run() {
		try {
			ejecuta();
		} catch (NumberFormatException nfe){
			System.err.println("El programa solo acepta numeros enteros para representar la estructura.");
		} catch (IllegalArgumentException iae) {
			System.err.println("La estructura ingresada no es valida.");
		}
	}

}