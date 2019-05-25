package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int c = llave.length;
        while (c % 4 != 0)
            c++;
        byte[] key = new byte[c];
        for (int i = 0; i < llave.length ; i++ )
            key[i] = llave[i];
        int r = 0;
        for (int i = 0; i < c ; i = i + 4 )
            r ^= combina(key[i],key[i+1],key[i+2],key[i+3]);
        return r;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int r = llave.length;
        int i = 0;
        int a,b;
        a = b = 0x9E3779B9;
        int c = 0xFFFFFFFF;
        int[] bj = new int[3];
        while(r >= 12) {
            a += combina(llave[i+3],llave[i+2],llave[i+1],llave[i]);
            b += combina(llave[i+7],llave[i+6],llave[i+5],llave[i+4]);
            c += combina(llave[i+11],llave[i+10],llave[i+9],llave[i+8]);
            bj = mezcla(a,b,c);
            a = bj[0]; b = bj[1]; c = bj[2];
            i = i + 12;
            r = r - 12;
        }
        c += llave.length;
        switch (r) {
            case 11:
                c += (llave[i + 10] & 0xFF) << 24;
            case 10:
                c += (llave[i + 9] & 0xFF) << 16;
            case 9:
                c += (llave[i + 8] & 0xFF) << 8;
            case 8:
                b += (llave[i + 7] & 0xFF) << 24;
            case 7:
                b += (llave[i + 6] & 0xFF) << 16;
            case 6:
                b += (llave[i + 5] & 0xFF) << 8;
            case 5:
                b += (llave[i + 4] & 0xFF);
            case 4:
                a += (llave[i + 3] & 0xFF) << 24;
            case 3:
                a += (llave[i + 2] & 0xFF) << 16;
            case 2:
                a += (llave[i + 1] & 0xFF) << 8;
            case 1:
                a += (llave[i] & 0xFF);
        }
        bj = mezcla(a,b,c);
        return bj[2];
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int hash = 5381;
        for(int i = 0; i < llave.length; i++)
            hash += (hash << 5) + (llave[i] & 0xFF);
        return hash;
    }

    private static int combina(byte a, byte b, byte c, byte d) {
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF));
    }

    private static int[] mezcla(int a, int b, int c) {
        int[] aux = new int[3];
        a-= b; a-= c; a^= (c >>> 13);
        b-= c; b-= a; b^= (a << 8);
        c-= a; c-= b; c^= (b >>> 13);
        a-= b; a-= c; a^= (c >>> 12);
        b-= c; b-= a; b^= (a << 16);
        c-= a; c-= b; c^= (b >>> 5);
        a-= b; a-= c; a^= (c >>> 3);
        b-= c; b-= a; b^= (a << 10);
        c-= a; c-= b; c^= (b >>> 15);
        aux[0] = a;
        aux[1] = b;
        aux[2] = c;
        return aux;
    }

}
