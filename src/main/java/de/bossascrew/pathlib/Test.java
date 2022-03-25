package de.bossascrew.pathlib;

import de.bossascrew.pathlib.pathfinder.AStar;
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
        graph.getShortestPath(new AStar(), start, target).forEach(s -> System.out.print(s + " -> "));
    }
}
