package de.bossascrew.pathlib;

import java.util.List;
import java.util.Objects;

public class Node {

    private final String key;
    private List<Edge> edges;

    public Node(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public List<Edge> getEdges() {
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
