import java.util.LinkedList;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Main {

	public static void main(String[] args) throws IOException {
		
		LinkedList<String> lista = new LinkedList<>();

		lista.add("9");
		lista.add("casa");
		lista.add("Proy2");
		lista.add("Fac");
		lista.add("Compu");
		lista.add("235");
		lista.add("SVG");

		File f = new File("PruebaLista-svg");
		FileWriter fr = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fr);

		bw.write("<svg width=\"" + width(lista.size()) + "\" height=\"" + 25 + "\">");
		bw.newLine();

		int x = 0;

		for (String s : lista) {
			bw.write("\t<rect x=\""+ x +"\" y=\"0\" width=\"50\" height=\"25\" style=\"fill:white;stroke:blue;stroke-width:2;stroke-opacity:1\" />");
			bw.newLine();
			bw.write("\t<text fill='black' font-family='sans-serif' font-size='12' x='" + (x + 25) + "' y='17' text-anchor='middle'>" + s + "</text>");
			bw.newLine();
			x = x + 70;
		}

		x = 50;

		for (int i = 0; i < lista.size() ; i++) {
			bw.write("\t<rect x=\"" + x + "\" y=\"0\" width=\"20\" height=\"25\" style=\"fill:white\" />");
			bw.newLine();
			bw.write("\t<polygon points=\"" + (x + 2) + ",12 " + (x + 4) + ",10 " + (x + 4) + ",14 \"/> ");
			bw.newLine();
			bw.write("\t<polygon points=\"" + (x + 18) + ",12 " + (x + 16) + ",10 " + (x + 16) + ",14 \"/>");
			bw.newLine();
			bw.write("\t<rect x=\"" + (x + 3.9) + "\" y=\"11.5\" width=\"12.5\" height=\"1\" style=\"fill:black\" />");
			bw.newLine();
			x = x + 70;
		}

		bw.write("</svg>");

		bw.close();
		fr.close();

	}

	public static int width(int tamañoLista) {
		if (tamañoLista == 0) 
			return 0;
		else if (tamañoLista == 1)
			return 50;
		else 
			return 70 + width(tamañoLista - 1);
	}

}