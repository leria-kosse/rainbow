package edu.ttap.graphs;

import java.util.List;
import java.util.Optional;

/**
 * A generic, weighted, undirected graph where nodes are represented by strings.
 */
public class Graph {

    /**
     * Constructs a graph from a list of graph entries.
     * @param entries the entries of the graph; each entry is one edge
     */
    public Graph(List<GraphEntry> entries) {
        // TODO: implement me!
    }

    /**
     * @param n the name of the node to check for
     * @return true if the graph contains a node with the given name, false
     * otherwise
     */
    public boolean contains(String n) {
        // TODO: implement me!
        return false;
    }

    /**
     * @param src the source node
     * @param dst the destination node
     * @return the weight of (src, dst) if it exists, or an empty Optional
     * otherwise
     */
    public Optional<Integer> getWeight(String src, String dst) {
        // TODO: implement me!
        return Optional.empty();
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a depth-first traversal
     * beginning at the starting node.
     */
    public List<String> collectDepthFirst(String start) {
        // TODO: implement me!
        return null;
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a breadth-first traversal
     * beginning at the starting node.
     */
    public List<String> collectBreadthFirst(String start) {
        // TODO: implement me!
        return null;
    }

    /**
     * Derives a minimum spanning tree of the graph using Prim's algorithm
     * @param start the starting node for this search
     * @return a list of edges that form a minimum spanning tree of the graph
     */
    public List<Edge> deriveMST(String start) {
        // TODO: implement me!
        return null;
    }
}