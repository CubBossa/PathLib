package de.bossascrew.pathlib.pathfinder;

import de.bossascrew.pathlib.Graph;
import de.bossascrew.pathlib.Node;
import de.bossascrew.pathlib.Path;

import java.util.*;

public class Dijkstra implements PathFinder {

    //TODO only copy paste for now

    @Override
    public Path getShortestPath(Graph graph, Node start, Node end) {

        DNode source = null;

        source.setDistance(0);

        Set<DNode> settledNodes = new HashSet<>();
        Set<DNode> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            DNode currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< DNode, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                DNode adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return null;
    }

    private DNode getLowestDistanceNode(Set < DNode > unsettledNodes) {
        DNode lowestDistanceNode = null;
        float lowestDistance = Integer.MAX_VALUE;
        for (DNode node: unsettledNodes) {
            float nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void calculateMinimumDistance(DNode evaluationNode, Integer edgeWeigh, DNode sourceNode) {
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

        Map<DNode, Integer> adjacentNodes = new HashMap<>();

        public void addDestination(DNode destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public DNode(Node node) {
            this.node = node;
        }

        public Map<DNode, Integer> getAdjacentNodes() {
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
