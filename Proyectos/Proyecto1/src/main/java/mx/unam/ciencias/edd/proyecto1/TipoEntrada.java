package mx.unam.ciencias.edd.proyecto1;

public class TipoEntrada {

    boolean entradaStandard;
    boolean entradaArgs;
    Banderas b;

    public TipoEntrada(Banderas b) {
        tipoDeEntrada(b);
        this.b = b;
        // System.out.println("Entrada Standard: " + entradaStandard);
        // System.out.println("Entrada Args: " + entradaArgs);
    }

    public void tipoDeEntrada(Banderas b) {
        if (b.nombreArchivoLectura != null) {
            this.entradaArgs = true;
        } else {
            this.entradaStandard = true;
        }
    }

}