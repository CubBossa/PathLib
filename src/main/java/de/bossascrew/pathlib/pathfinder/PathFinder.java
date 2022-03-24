package de.bossascrew.pathlib.pathfinder;

import de.bossascrew.pathlib.Node;
import de.bossascrew.pathlib.Graph;
import de.bossascrew.pathlib.Path;

import java.util.Map;

public interface PathFinder {

    /**
     * Returns the shortest path from the given start node to the end node. If no path could be found, an empty map will be returned
     *
     * @param graph the graph to calculate the shortest paths on
     * @param start the start node
     * @param end   the end node
     * @return the shortest path from start node to end node or an empty map if no path was found
     */
    public <V> Path getShortestPath(Graph<V> graph, Node start, Node end);
}
