package Ejercicio5;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.stream.*;

    public class ProcesamientoTexto {
        private final List<String> lines;

        public ProcesamientoTexto(List<String> lines) {
            this.lines = lines;
        }

        private Stream<String> words() {
            return lines.stream()
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .filter(w -> !w.isEmpty());
        }

        private Stream<String> cleanWords() {
            return words()
                    .map(w -> w.replaceAll("[^a-zA-Z]", ""))
                    .filter(w -> !w.isEmpty())
                    .map(String::toLowerCase);
        }

        public long wordCount() {
            return cleanWords().count();
        }

        public Set<String> uniqueWords() {
            return cleanWords().collect(toSet());
        }

        public List<Map.Entry<String, Long>> topN(int n) {
            return cleanWords()
                    .collect(groupingBy(w -> w, counting()))
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                    .thenComparing(Map.Entry.comparingByKey()))
                    .limit(n)
                    .collect(toList());
        }

        public double averageWordLength() {
            return cleanWords()
                    .mapToInt(String::length)
                    .average()
                    .orElse(0.0);
        }

        public Map<Character, List<String>> wordsByFirstLetter() {
            return cleanWords()
                    .distinct()
                    .collect(groupingBy(w -> w.charAt(0)));
        }

        public static void main(String[] args) {
            List<String> text = List.of(
                    "Java is a powerful programming language",
                    "Java streams make data processing elegant",
                    "Lambdas and streams are the heart of modern Java"
            );

            ProcesamientoTexto analyzer = new ProcesamientoTexto(text);

            System.out.println("=== Estadisticas de Texto ===");
            System.out.println("Total palabras: " + analyzer.wordCount());
            System.out.println("Palabras unicas: " + analyzer.uniqueWords().size());
            System.out.printf("Longitud promedio: %.2f%n", analyzer.averageWordLength());

            System.out.println("\n=== Top 5 Palabras ===");
            analyzer.topN(5).forEach(e ->
                    System.out.printf("  '%s': %d veces%n", e.getKey(), e.getValue()));

            System.out.println("\n=== Palabras por Letra ===");
            analyzer.wordsByFirstLetter().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.printf("  %c: %s%n", e.getKey(), e.getValue()));
        }
    }