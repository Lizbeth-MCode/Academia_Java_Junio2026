package Ejercicio1;

import java.util.*;
import java.util.stream.Collectors;

public class GestorContactos implements Comparable<GestorContactos> {
    private String name;
    private String email;
    private String phone;

    public GestorContactos(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public int compareTo(GestorContactos other) {
        // TODO: orden natural por name (alfabetico)
        return this.name.compareToIgnoreCase(other.name);
}

    @Override
    public boolean equals(Object o) {
        // TODO: igualdad basada en email
        if (this == o) return true;
        if (!(o instanceof GestorContactos c)) return false;
        return this.email.equalsIgnoreCase(c.email);
    }

    @Override
    public int hashCode() {
        // TODO: hash basado en email
        return email.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Contact{name='%s', email='%s', phone='%s'}",
                name, email, phone);
    }
}

    class ContactManager {
        private final Set<GestorContactos> contacts = new TreeSet<>();

        public boolean addContact(GestorContactos contact) {
            // TODO: agregar al set, retornar true si se agrego
            return contacts.add(contact);
        }

        public Optional<GestorContactos> findByEmail(String email) {
            // TODO: buscar contacto por email usando stream + filter
            return contacts.stream()
                    .filter(c -> c.getEmail().equalsIgnoreCase(email))
                    .findFirst();
        }

        public List<GestorContactos> findByNamePrefix(String prefix) {
            // TODO: filtrar contactos cuyo nombre empiece con prefix (case-insensitive)
            return contacts.stream()
                    .filter(c -> c.getName().toLowerCase().startsWith(prefix.toLowerCase()))
                    .collect(Collectors.toList());
        }

        public List<GestorContactos> getAllSortedBy(Comparator<GestorContactos> comp) {
            // TODO: retornar todos los contactos ordenados por el comparator dado
            return contacts.stream()
                    .sorted(comp)
                    .collect(Collectors.toList());
        }

        public int size() { return contacts.size(); }

        public static void main(String[] args) {
            ContactManager mgr = new ContactManager();

            System.out.println("=== Agregando Contactos ===");
            System.out.println("Ana: " + mgr.addContact(
                    new GestorContactos("Ana Garcia", "ana@mail.com", "555-1111")));
            System.out.println("Luis: " + mgr.addContact(
                    new GestorContactos("Luis Lopez", "luis@mail.com", "555-2222")));
            System.out.println("Maria: " + mgr.addContact(
                    new GestorContactos("Maria Torres", "maria@mail.com", "555-3333")));
            System.out.println("Ana duplicada: " + mgr.addContact(
                    new GestorContactos("Ana Garcia", "ana@mail.com", "555-9999")));
            System.out.println("Carlos: " + mgr.addContact(
                    new GestorContactos("Carlos Ruiz", "carlos@mail.com", "555-4444")));
            System.out.println("Total contactos: " + mgr.size());

            System.out.println("\n=== Orden Natural (por nombre) ===");
            mgr.getAllSortedBy(Comparator.naturalOrder())
                    .forEach(System.out::println);

            System.out.println("\n=== Ordenados por Email ===");
            mgr.getAllSortedBy(Comparator.comparing(GestorContactos::getEmail))
                    .forEach(System.out::println);

            System.out.println("\n=== Buscar por Email ===");
            mgr.findByEmail("maria@mail.com")
                    .ifPresentOrElse(
                            c -> System.out.println("Encontrado: " + c),
                            () -> System.out.println("No encontrado"));
            mgr.findByEmail("noexiste@mail.com")
                    .ifPresentOrElse(
                            c -> System.out.println("Encontrado: " + c),
                            () -> System.out.println("No encontrado"));

            System.out.println("\n=== Buscar por Prefijo 'Ma' ===");
            mgr.findByNamePrefix("Ma").forEach(System.out::println);
        }
    }
