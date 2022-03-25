package de.bossascrew.pathlib;

import de.bossascrew.pathlib.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class Test {

    public static void main(String[] args) {

        Set<String> nodes = new HashSet<>();
        nodes.add("Point A");
        nodes.add("Point B");
        nodes.add("Point C");
        nodes.add("Point D");

        Set<Pair<String, String>> edges = new HashSet<>();
        edges.add(Pair.of("Point A", "Point B"));
        edges.add(Pair.of("Point B", "Point C"));
        edges.add(Pair.of("Point B", "Point D"));
        edges.add(Pair.of("Point C", "Point D"));

        // The string also serves as key, so we can use s -> s for mapping
        // The distance function returns 1f, so all connected nodes are equidistant
        Graph<String> graph = new Graph<>(nodes, edges, s -> s, s -> s, (s, s2) -> 1f);

        String start = "Point A";
        String target = "Point D";
        System.out.println("\n\nSimple Example");
        // Returns a list of strings -> the keys converted back to our Graph Type, which is also a string
        graph.getShortestPath(start, target).forEach(s -> System.out.print(s + " -> "));

        Vector a = new Vector("a", 1, 1, 1);
        Vector b = new Vector("b", 1, 2.5f, -1);
        Vector c = new Vector("c", 3, 0, 1);
        Vector d = new Vector("d", -1, 0, -1);

        Set<Vector> nodes1 = new HashSet<>();
        nodes1.add(a);
        nodes1.add(b);
        nodes1.add(c);
        nodes1.add(d);

        Set<Pair<Vector, Vector>> edges1 = new HashSet<>();
        edges1.add(Pair.of(a, b));
        edges1.add(Pair.of(b, a));
        edges1.add(Pair.of(b, c));
        edges1.add(Pair.of(b, d));
        edges1.add(Pair.of(c, d));

        Graph<Vector> graph1 = new Graph<>(nodes1, edges1, Vector::getName,
                string -> nodes1.stream().filter(vector -> vector.getName().equals(string)).findFirst().orElse(null), Vector::distance);

        System.out.println("\n\nVector Example");
        graph1.getShortestPath(a, d).forEach(s -> System.out.print(s.getName() + " -> "));
    }
}
