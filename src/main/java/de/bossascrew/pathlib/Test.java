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

        Graph<String> graph = new Graph<>(nodes, edges, Node::new, s -> s, (s, s2) -> 1f);

        String start = "Point A";
        String target = "Point D";
        System.out.println("\n\nSimple Example");
        for (Node node : graph.getShortestPath(start, target)) {
            System.out.print(node.getKey() + " -> ");
        }
    }
}
