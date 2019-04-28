package mx.unam.ciencias.edd.proyecto2;

public class Proyecto2 {
	
	/**
	 * Método principal que manda a llamar al metodo run
	 * @param args El programa supone que args es null, todo se 
	 * procesa con la entrada estandard.
	 */
	public static void main(String[] args) {
		run();
	}

	/**
	 * Método que crea un objeto para procesar la entrada, otro
	 * objeto para procesar la estructura e invocar el método
	 * imprime de esa estructura que genera el codigo SVG
	 */
	public static void ejecuta() {
		Entrada e = new Entrada();
		Estructura s = new Estructura(e);
		//System.out.println("<!--\n" + e + "\n" + s + "\n-->");
		if (s.estructuraSVG != null)
			s.estructuraSVG.imprime();
		else
			s.arbolSVG.imprime();
	}

	/**
	 * Método que manda a llamar a ejecuta, y atrapa todas las excepciones
	 * las cuales algo podria salir mal, y notifica al usuario de que fue lo
	 * que paso.
	 */
	public static void run() {
		try {
			ejecuta();
		} catch (NumberFormatException nfe){
			System.err.println("El programa solo acepta numeros enteros para representar la estructura.");
		} catch (IllegalArgumentException iae) {
			System.err.println("La estructura ingresada no es valida.");
		} catch (Exception e) {
			System.err.println("Ocurrio un error desconocido");
		}
	}

}