package ejercicio2;

import java.util.Arrays;

public class CalculadoraBasica {

        public static int sumar(int a, int b) {
            return a+b;
        }

        public static double sumar(double a, double b) {
            return a+b;
        }

        public static int sumar(int a, int b, int c) {
            return a+b+c;
        }


    public static int sumar(int[] numeros) {
        int total = Arrays.stream(numeros).sum();
        return total;
    }

        public static void main(String[] args) {
            System.out.println("2 + 3 = " + sumar(2, 3));
            System.out.println("2.5 + 3.7 = " + sumar(2.5, 3.7));
            System.out.println("1 + 2 + 3 = " + sumar(1, 2, 3));

            int[] nums = {10, 20, 30, 40};
            System.out.println("Array suma = " + sumar(nums));
        }
    }
