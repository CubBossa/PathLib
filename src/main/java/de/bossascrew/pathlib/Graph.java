package de.bossascrew.pathlib;

import de.bossascrew.pathlib.pathfinder.Dijkstra;
import de.bossascrew.pathlib.pathfinder.PathFinder;
import de.bossascrew.pathlib.util.Pair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Graph<V> {

    private final Map<String, Node> nodes;
    private final Set<Edge> edges;

    private final Function<V, Node> nodeFunction;
    private final Function<String, V> returnFunction;
    private final BiFunction<V, V, Float> distanceFunction;

    /**
     * This graph class converts another type of nodes (e.g. database compatible nodes or vectors)
     * into internal nodes. Therefore, you need to provide a list of objects of this type.
     * <p>
     * Edges represent directed connections between two nodes. Use the costs function to provide distance information.
     * Use `(a, b) -> 1` to make them equidistant.
     *
     * @param nodes          a list of objects that will be converted into nodes
     * @param edges          a map of edges that will be converted into internal edges
     * @param keyFunction    a function that retrieves an individual key for each node that is used for comparison
     * @param returnFunction a function that retrieves the input object from an individual key
     * @param costsFunction  a function that provides a distance value for two nodes.
     */
    public Graph(Set<V> nodes, Set<Pair<V, V>> edges, Function<V, String> keyFunction, Function<String, V> returnFunction, BiFunction<V, V, Float> costsFunction) {
        this.nodes = new HashMap<>();
        this.nodeFunction = v -> {
            String key = keyFunction.apply(v);
            Node contained = this.nodes.get(key);
            if (contained != null) {
                return contained;
            }
            return new Node(keyFunction.apply(v));
        };
        this.returnFunction = returnFunction;
        this.distanceFunction = costsFunction;

        Map<V, Node> nodeList = new HashMap<>();
        Set<Edge> edgeList = new HashSet<>();

        for (V pos : nodes) {
            Node node = nodeFunction.apply(pos);
            nodeList.put(pos, node);
        }
        for (Map.Entry<V, Node> start : nodeList.entrySet()) {
            List<V> ends = edges.stream().filter(vvPair -> vvPair.getLeft().equals(start.getKey())).map(Pair::getRight).collect(Collectors.toList());
            for (V end : ends) {
                if (end != null) {
                    Node endNode = nodeList.get(end);
                    Edge edge = new Edge(start.getValue(), endNode, costsFunction.apply(start.getKey(), end));
                    start.getValue().getEdges().add(edge);
                    edgeList.add(edge);
                }
            }
        }
        nodeList.forEach((v, node) -> this.nodes.put(keyFunction.apply(v), node));
        this.edges = edgeList;
    }

    public int size() {
        return nodes.size();
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public Collection<Edge> getEdges() {
        return edges;
    }

    public double distance(V a, V b) {
        return distanceFunction.apply(a, b);
    }

    public double distance(Node a, Node b) {
        return distance(returnFunction.apply(a.getKey()), returnFunction.apply(b.getKey()));
    }

    public void addNode(V node) {
        addNode(nodeFunction.apply(node), null);
    }

    public void addNode(V node, Map<V, Float> edges) {
        Map<Node, Float> edgeMap = new HashMap<>();
        edges.forEach((v, aFloat) -> edgeMap.put(nodeFunction.apply(v), aFloat));
        addNode(nodeFunction.apply(node), edgeMap);
    }

    /**
     * Adds a node and adds all provided edges to the node and to the graph.
     *
     * @param node  a single node to add
     * @param edges all edges to add
     */
    public void addNode(Node node, Map<Node, Float> edges) {
        nodes.put(node.getKey(), node);
        if(edges == null) {
            return;
        }
        for (Map.Entry<Node, Float> entry : edges.entrySet()) {
            Edge edge = new Edge(node, entry.getKey(), entry.getValue());
            node.getEdges().add(edge);
            this.edges.add(edge);
        }
    }

    /**
     * Creates an edge between the start and end node. This edge will be directed and works in one direction.
     * Call this method twice with swapped parameters to make it undirected.
     * The costs will be calculated by calling the provided function.
     *
     * @param start the start node
     * @param end   the end node
     */
    public void connectNodes(V start, V end) {
        connectNodes(nodeFunction.apply(start), nodeFunction.apply(end), distanceFunction.apply(start, end));
    }

    /**
     * Creates an edge between the start and end node. This edge will be directed and works in one direction.
     * Call this method twice with swapped parameters to make it undirected.
     *
     * @param start the start node
     * @param end   the end node
     * @param costs the costs/distance from start to end
     */
    public void connectNodes(Node start, Node end, float costs) {
        Edge edge = new Edge(start, end, costs);
        start.getEdges().add(edge);
        edges.add(edge);
    }

    /**
     * Removes the connection from the provided start node to end node
     */
    public void disconnectNodes(V start, V end) {
        disconnectNodes(nodeFunction.apply(start), nodeFunction.apply(end));
    }

    /**
     * Removes the connection from the provided start node to end node
     */
    public void disconnectNodes(Node start, Node end) {
        Edge edge = edges.stream().filter(e -> e.getStart().equals(start) && e.getEnd().equals(end)).findFirst().orElse(null);
        if (edge != null) {
            start.getEdges().remove(edge);
            edges.remove(edge);
        }
    }

    /**
     * The shortest path from start to end, based on the Dijkstra Algorithm.
     *
     * @param start the start node
     * @param end   the end node
     * @return the shortest path object with no entries if no shortest path was found
     */
    public Path getShortestPath(Node start, Node end) {
        return getShortestPath(new Dijkstra(), start, end);
    }

    /**
     * The shortest path from start to end, based on the provided algorithm.
     *
     * @param start the start node
     * @param end   the end node
     * @return the shortest path object with no entries if no shortest path was found
     */
    public Path getShortestPath(PathFinder pathFinder, Node start, Node end) {
        return pathFinder.getShortestPath(this, start, end);
    }

    /**
     * The shortest path from start to end, based on the Dijkstra Algorithm.
     *
     * @param start the start node
     * @param end   the end node
     * @return the shortest path object with no entries if no shortest path was found
     */
    public LinkedList<V> getShortestPath(V start, V end) {
        return getShortestPath(new Dijkstra(), start, end);
    }

    /**
     * The shortest path from start to end, based on the provided algorithm.
     *
     * @param start the start node
     * @param end   the end node
     * @return the shortest path object with no entries if no shortest path was found
     */
    public LinkedList<V> getShortestPath(PathFinder pathFinder, V start, V end) {
        return new LinkedList<>(getShortestPath(pathFinder, nodeFunction.apply(start), nodeFunction.apply(end)).stream().map(node -> returnFunction.apply(node.getKey())).collect(Collectors.toList()));
    }
}
