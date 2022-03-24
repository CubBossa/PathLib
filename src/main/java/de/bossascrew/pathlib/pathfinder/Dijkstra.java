package de.bossascrew.pathlib.pathfinder;

import de.bossascrew.pathlib.Edge;
import de.bossascrew.pathlib.Graph;
import de.bossascrew.pathlib.Node;
import de.bossascrew.pathlib.Path;

import java.util.*;

public class Dijkstra implements PathFinder {

    @Override
    public <V> Path getShortestPath(Graph<V> graph, Node startNode, Node endNode) {

        Map<String, DNode> dnodes = new HashMap<>();
        for (Node node : graph.getNodes()) {
            dnodes.put(node.getKey(), new DNode(node));
        }
        for (Edge edge : graph.getEdges()) {
            dnodes.get(edge.getStart().getKey()).addDestination(dnodes.get(edge.getEnd().getKey()), edge.getCosts());
        }
        DNode source = dnodes.get(startNode.getKey());
        source.setDistance(0);

        Set<DNode> settledNodes = new HashSet<>();
        Set<DNode> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            DNode currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<DNode, Float> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                DNode adjacentNode = adjacencyPair.getKey();
                float edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        Path shortest = new Path();
        for (DNode dNode : dnodes.get(endNode.getKey()).getShortestPath()) {
            shortest.add(dNode.getNode());
        }
        if(shortest.size() != 0) {
            shortest.add(endNode);
        }
        return shortest;
    }

    private DNode getLowestDistanceNode(Set<DNode> unsettledNodes) {
        DNode lowestDistanceNode = null;
        float lowestDistance = Integer.MAX_VALUE;
        for (DNode node : unsettledNodes) {
            float nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(DNode evaluationNode, float edgeWeigh, DNode sourceNode) {
        float sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<DNode> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private class DNode {
        private Node node;

        private List<DNode> shortestPath = new LinkedList<>();

        private float distance = Integer.MAX_VALUE;

        Map<DNode, Float> adjacentNodes = new HashMap<>();

        public void addDestination(DNode destination, float distance) {
            adjacentNodes.put(destination, distance);
        }

        public DNode(Node node) {
            this.node = node;
        }

        public Node getNode() {
            return node;
        }

        public Map<DNode, Float> getAdjacentNodes() {
            return adjacentNodes;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public List<DNode> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(List<DNode> shortestPath) {
            this.shortestPath = shortestPath;
        }
    }
}
