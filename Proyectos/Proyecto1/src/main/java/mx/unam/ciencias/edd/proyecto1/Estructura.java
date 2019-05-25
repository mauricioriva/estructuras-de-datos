package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.IOException;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;;

public class Estructura {

    Lista<Cadena> lista;

    public Estructura(ArmaFlujos af) {
        try {
            this.lista = creaLista(af.bf);
        } catch (IOException ioe) {
            this.lista = null;
        }
    }

    public Lista<Cadena> creaLista(BufferedReader in) throws IOException {
        ArbolRojinegro<Cadena> arn = new ArbolRojinegro<>();
        String aux;
        while ((aux = in.readLine()) != null) {
            arn.agrega(new Cadena(aux));
        }
        in.close();
        Lista<Cadena> l = new Lista<Cadena>();
        for (Cadena c : arn) {
            l.agrega(c);
        }
        return l;
    }

}