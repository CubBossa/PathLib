package de.bossascrew.pathlib;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {

    private final String key;
    private Set<Edge> edges;

    public Node(String key) {
        this.key = key;
        edges = new HashSet<>();
    }

    public String getKey() {
        return key;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(key, node.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
