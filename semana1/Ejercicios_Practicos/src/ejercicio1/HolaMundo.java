package ejercicio1;

import org.w3c.dom.ls.LSOutput;

public class HolaMundo {
    public static void main(String[] args) {

    String nombre = "Ana";
    int edad = 25;
    double altura = 1.68;
    boolean esActivo = true;

    String mensaje1 = "Me llamo " + nombre + ", tengo " + edad
            + " anios, mido " + altura + "m y estoy "
            + (esActivo? "activo" : "inactivo") + ".";

    String mensaje2 = String.format(
            "Me llamo %s, tengo %d anios, mido %.2f m y estoy %s.",
            nombre, edad, altura, esActivo? "activo" : "inactivo"
    );
        System.out.println(mensaje1);
        System.out.println(mensaje2);
        }
    }





