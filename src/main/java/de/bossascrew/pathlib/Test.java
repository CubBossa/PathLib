package de.bossascrew.pathlib;

import jdk.internal.vm.compiler.collections.Pair;

import java.util.*;

public class Test {

    public static void main(String[] args) {

        Set<String> nodes = new HashSet<>();
        nodes.add("Point A");
        nodes.add("Point B");
        nodes.add("Point C");
        nodes.add("Point D");
        Set<Pair<String, String>> edges = new HashSet<>();
        edges.add(Pair.create("Point A", "Point B"));
        edges.add(Pair.create("Point B", "Point C"));
        edges.add(Pair.create("Point B", "Point D"));

        Graph<String> graph = new Graph<>(nodes, edges, Node::new, s -> s, (s, s2) -> 1f);

        //graph.getShortestPath("Point A", "Point B");
    }

}
