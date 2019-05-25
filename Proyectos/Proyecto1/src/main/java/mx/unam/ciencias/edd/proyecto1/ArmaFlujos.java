package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.unam.ciencias.edd.Lista;

public class ArmaFlujos {

    BufferedReader bf;

    public ArmaFlujos(TipoEntrada tp) {
        if (tp.entradaStandard) {
            flujoStandard();
        } else {
            flujoArgs(tp.b);
        }
    }

    public void flujoStandard() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader(isr);
        this.bf = bf;
    }

    public void flujoArgs(Banderas b) {
        String archivo = b.nombreArchivoLectura;
        try {
            File f = new File(archivo);
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            this.bf = bf;
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public void EscribirArchivo(Lista<Cadena> lista, String nombre) {
        File archivo = new File(nombre);
        try {
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);

            for (Cadena c : lista) {
                bw.write(c.contenido);
                bw.newLine();
            }

            bw.close();
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}