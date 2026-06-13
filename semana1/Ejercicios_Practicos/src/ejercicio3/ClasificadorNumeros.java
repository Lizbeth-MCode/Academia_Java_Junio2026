package ejercicio3;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class ClasificadorNumeros {

    public static void clasificar(int[] numeros) {

        int positivos = 0, negativos = 0, ceros = 0;
        int pares = 0, impares = 0;

        //Numero mayor
        int mayor = Arrays.stream(numeros).max().orElse(0);
        //Numero menor
        int menor = Arrays.stream(numeros).min().orElse(0);
        // Promedio
        double suma= Arrays.stream(numeros).sum();
        double promedio = (double) suma/numeros.length;


        // positivo / negativo / cero
        for (int num : numeros) {
            if (num > 0) {
                positivos++;
            } else if (num == 0) {
                ceros++;
            } else if (num < 0) {
                negativos++;
            }
        }

        // par / impar
        for (int num : numeros) {
            if (num % 2 == 0) {
                pares++;
            } else {
                impares++;
            }
        }

        System.out.println("=== Resultados ==="
                +"\nPositivos: " + positivos
                +"\nNegativos: " + negativos
                +"\nCeros: " + ceros
                +"\nPares: " + pares
                +"\nImpares: " + impares
                +"\nMayor: " + mayor
                +"\nMenor: " + menor
                +"\nPromedio: " + promedio);
    }

    public static void main(String[] args) {
        int[] datos = {15, -3, 0, 8, -12, 7, 0, 22, -5, 10};
        clasificar(datos);
    }
}
