package mx.unam.ciencias.edd.proyecto1;

public class Banderas {

    String[] args;
    boolean bandera_r;
    boolean bandera_o;
    String nombreArchivo;
    String nombreArchivoLectura;

    public Banderas(String[] s) {
        this.args = s;
        analizaEntrada(s);
        // System.out.println("Bandera -r " + bandera_r);
        // System.out.println("Bandera -o " + bandera_o);
        // System.out.println("Nombre Archivo destino: " + nombreArchivo);
        // System.out.println("Nombre Archivo lectura: " + nombreArchivoLectura);
    }

    private void analizaEntrada(String[] s) {
        if (s.length == 0) {
            return;
        }
        if (s.length == 1) {
            caso1(s);
        }
        for (int i = 0; i < s.length; i++) {
            if ("-o".equals(s[i])) {
                this.bandera_o = true;
                try {
                    this.nombreArchivo = s[i + 1];
                } catch (ArrayIndexOutOfBoundsException a) {
                    this.nombreArchivo = "Archivo.txt";
                }
            }
            try {
                if ("-r".equals(s[i]) && !s[i - 1].equals("-o"))
                    this.bandera_r = true;
            } catch (ArrayIndexOutOfBoundsException a) {
                this.bandera_r = true;
            }
            try {
                if (!s[i - 1].equals("-o") && !s[i].equals("-o") && !s[i].equals("-r")) {
                    this.nombreArchivoLectura = s[i];
                }
            } catch (ArrayIndexOutOfBoundsException a) {
                if (!s[i].equals("-o") && !s[i].equals("-r")) {
                    this.nombreArchivoLectura = s[i];
                }
            }

        }
    }

    public void caso1(String[] s) {
        String aux = s[0];
        if (aux.equals("-r")) {
            this.bandera_r = true;
            return;
        } else if (aux.equals("-o")) {
            this.bandera_o = true;
            this.nombreArchivo = "Archivo.txt";
            return;
        } else {
            this.nombreArchivoLectura = aux;
        }
    }

}