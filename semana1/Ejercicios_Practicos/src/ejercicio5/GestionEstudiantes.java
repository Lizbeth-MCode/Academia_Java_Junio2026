package ejercicio5;

import java.util.ArrayList;
import java.util.Arrays;

public class GestionEstudiantes {
    private String nombre;
    private String matricula;
    private ArrayList<Double> calificaciones;


    public GestionEstudiantes(String nombre, String matricula) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.calificaciones = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public String getMatricula() { return matricula; }

    public void agregarCalificacion(double cal) {
        if (cal >=0 && cal<=100) {
            calificaciones.add(cal);
        } else {
            System.out.println("Calificacion invalida: " + cal);
        }
    }

    // TODO: calcular promedio
    public double calcularPromedio() {
        if (calificaciones.isEmpty()) return 0.0;
        double suma = 0;
        for (double cal:calificaciones){
            suma += cal;
        }
        return suma / calificaciones.size();
    }

    @Override
    public String toString() {
        return String.format(
                "Estudiante{nombre='%s', matricula='%s', promedio=%.2f}",
                nombre, matricula, calcularPromedio());
    }

    public static void main(String[] args) {
        GestionEstudiantes e1 = new GestionEstudiantes("Ana Garcia", "A001");
        GestionEstudiantes e2 = new GestionEstudiantes("Carlos Lopez", "A002");
        GestionEstudiantes e3 = new GestionEstudiantes("Maria Torres", "A003");

        e1.agregarCalificacion(95);
        e1.agregarCalificacion(88);
        e1.agregarCalificacion(92);

        e2.agregarCalificacion(78);
        e2.agregarCalificacion(85);
        e2.agregarCalificacion(90);

        e3.agregarCalificacion(100);
        e3.agregarCalificacion(96);
        e3.agregarCalificacion(98);

        ArrayList<GestionEstudiantes> lista = new ArrayList<>();
        lista.add(e1); lista.add(e2); lista.add(e3);

        System.out.println("=== Lista de Estudiantes ===");
        GestionEstudiantes mejor = lista.get(0);
        for (GestionEstudiantes e : lista) {
            System.out.println(e);
            if (e.calcularPromedio() > mejor.calcularPromedio()) {
                mejor = e;
            }
        }
        System.out.println("\nMejor promedio: " + mejor.getNombre()
                + " (" + String.format("%.2f", mejor.calcularPromedio())
                + ")");
    }
}
