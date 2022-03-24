package de.bossascrew.pathlib;

public class Edge {

    private Node start;
    private Node end;
    private float costs;

    public Edge(Node start, Node end, float costs) {
        this.start = start;
        this.end = end;
        this.costs = costs;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public float getCosts() {
        return costs;
    }
}
