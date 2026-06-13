package ejercicio4;

import java.util.Locale;

public class ManipuladorStrings {

    public static String invertir(String s) {

        StringBuilder voltear = new StringBuilder(String.valueOf(s));
        return String.valueOf(voltear.reverse());

    }

    public static boolean esPalindromo(String s) {
        s = s.toLowerCase(Locale.ROOT).replaceAll("\\s+", "");
        boolean igual= s.equals(invertir(s));
        return igual;
    }

    public static int contarVocales(String s) {
        int count = 0;
        String vocales = "aeiouAEIOU";

        for (int i = 0; i < s.length(); i++) {
            char letra = s.charAt(i);
            if (vocales.indexOf(letra) != -1) {
                count++;
            }
        }
        return count;
    }

    public static String construirPiramide(int niveles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= niveles; i++) {
            for (int j = 1; j <= niveles - i; j++) {
                sb.append(" ");
            }
            for (int j = 1; j <= (2 * i - 1); j++) {
                sb.append("*");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("\nInvertir 'Hola Mundo': "
                + invertir("Hola Mundo"));
       System.out.println("'Anita lava la tina' es palindromo: "
                + esPalindromo("Anita lava la tina"));
       System.out.println("Vocales en 'Murcielago': "
                + contarVocales("Murcielago"));
       System.out.println("Piramide de 5 niveles:");
        System.out.println(construirPiramide(5));
    }
}
