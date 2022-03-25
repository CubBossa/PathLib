package de.bossascrew.pathlib.pathfinder;

import de.bossascrew.pathlib.Edge;
import de.bossascrew.pathlib.Graph;
import de.bossascrew.pathlib.Node;
import de.bossascrew.pathlib.Path;

import java.util.*;
import java.util.stream.Collectors;

public class AStar implements PathFinder {

    @Override
    public <V> Path getShortestPath(Graph<V> graph, Node start, Node end) {


        Map<String, ANode> nodes = graph.getNodes().stream().collect(Collectors.toMap(Node::getKey, node -> {
            ANode n = new ANode(node);
            if (node.equals(start)) {
                n.distance = 0;
                n.priority = graph.distance(node, end);
            }
            return n;
        }));
        for (Edge edge : graph.getEdges()) {
            nodes.get(edge.getStart().getKey()).edges.put(nodes.get(edge.getEnd().getKey()), edge.getCosts());
        }
        ANode startNode = nodes.get(start.getKey());
        ANode endNode = nodes.get(end.getKey());

        List<ANode> path = new ArrayList<>();
        path.add(startNode);

        while (true) {
            // ... find the node with the currently lowest priority...
            double lowestPriority = Integer.MAX_VALUE;
            ANode lowestPriorityNode = null;

            for (ANode node : nodes.values()) {
                //... by going through all nodes that haven't been visited yet
                if (!node.visited && node.priority < lowestPriority) {
                    lowestPriority = node.priority;
                    lowestPriorityNode = node;
                }
            }

            // Goal node found or could not be found -> return
            if (lowestPriorityNode == null || lowestPriorityNode == endNode) {
                break;
            }
            //...then, for all neighboring nodes that haven't been visited yet....
            for (Map.Entry<ANode, Float> entry : lowestPriorityNode.edges.entrySet()) {
                ANode neighbor = entry.getKey();
                if (neighbor.visited) {
                    continue;
                }
                if(lowestPriorityNode.distance + entry.getValue() < neighbor.distance) {
                    neighbor.distance = lowestPriorityNode.distance + entry.getValue();
                    neighbor.priority = neighbor.distance + neighbor.heuristic;
                    path.add(neighbor);
                }
            }
            // Lastly, note that we are finished with this node.
            lowestPriorityNode.visited = true;
        }
        Path shortest = new Path();
        shortest.addAll(path.stream().map(aNode -> aNode.node).collect(Collectors.toList()));
        if(!shortest.isEmpty()) {
            shortest.add(end);
        }
        return shortest;
    }

    private class ANode {

        protected final Node node;

        protected Map<ANode, Float> edges = new HashMap<>();

        protected double distance = Integer.MAX_VALUE;
        protected boolean visited = false;
        protected double heuristic = Integer.MAX_VALUE;
        protected double priority = Integer.MAX_VALUE;

        public ANode(Node node) {
            this.node = node;
        }
    }
}
