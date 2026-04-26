package edu.ttap.graphs;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * A generic, weighted, undirected graph where nodes are represented by strings.
 */
public class Graph {

    private Map<String, List<String>> adjacencyList;
    private Set<String> nodes;
    private Map<String, Map<String, Integer>> weightsList;

    /**
     * Constructs a graph from a list of graph entries.
     * 
     * @param entries the entries of the graph; each entry is one edge
     */
    public Graph(List<GraphEntry> entries) {
        adjacencyList = new HashMap<>();
        nodes = new HashSet<>();
        weightsList = new HashMap<>();

        for (GraphEntry entry : entries) {
            String src = entry.src();
            String dest = entry.dest();
            int weight = entry.weight();

            nodes.add(src);
            nodes.add(dest);

            // checks to see if the adjacencylist has our src and dest nodes, and adds if
            // non existent.
            if (!adjacencyList.containsKey(src)) {
                adjacencyList.put(src, new ArrayList<>());
            }
            if (!adjacencyList.containsKey(dest)) {
                adjacencyList.put(dest, new ArrayList<>());
            }
            adjacencyList.get(src).add(dest); // gets the src node from the adjacency list, and adds dest as a
                                              // destination node to the list.
            adjacencyList.get(dest).add(src); // a connection goes 2 ways.

            // same shit, different toilet for weights
            if (!weightsList.containsKey(src)) {
                weightsList.put(src, new HashMap<>());
            }
            if (!weightsList.containsKey(dest)) {
                weightsList.put(dest, new HashMap<>());
            }
            weightsList.get(src).put(dest, weight);
            weightsList.get(dest).put(src, weight);

        }
    }

    /**
     * @param n the name of the node to check for
     * @return true if the graph contains a node with the given name, false
     *         otherwise
     */
    public boolean contains(String n) {
        return nodes.contains(n);
    }

    public Set<String> getNodes() {
        return nodes;
    }

    /**
     * @param src the source node
     * @param dst the destination node
     * @return the weight of (src, dst) if it exists, or an empty Optional
     *         otherwise
     */
    public Optional<Integer> getWeight(String src, String dst) {
        if (!weightsList.containsKey(src)) {
            return Optional.empty(); // idk what this does.
        }
        if (!weightsList.get(src).containsKey(dst)) {
            return Optional.empty();
        }
        return Optional.of(weightsList.get(src).get(dst)); // lowkirkuinely had to look up what Optional is and how its
                                                           // used.
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a depth-first traversal
     *         beginning at the starting node.
     */
    public List<String> collectDepthFirst(String start) {
        List<String> result = new ArrayList<>();
        Set<String> whereibeen = new HashSet<>();
        Stack<String> whereineedtogo = new Stack<>(); // variable names so that ALL of us understand how this works.

        if (!this.contains(start)) {
            return result;
        }

        whereineedtogo.push(start);

        while (!whereineedtogo.isEmpty()) {
            String curr = whereineedtogo.pop();

            if (!whereibeen.contains(curr)) {
                result.add(curr);
                whereibeen.add(curr);

                for (String neighbours : adjacencyList.get(curr)) {
                    whereineedtogo.push(neighbours);
                }
            }

        }

        return result;
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a breadth-first traversal
     *         beginning at the starting node.
     */
    public List<String> collectBreadthFirst(String start) {
        List<String> result = new ArrayList<>();
        Set<String> ibeenhere = new HashSet<>();
        Queue<String> ineedtogo = new LinkedList<>(); // so weird how queue is a fucking interface but stack is defined.

        if (!this.contains(start)) {
            return result;
        }

        ineedtogo.add(start);

        while (!ineedtogo.isEmpty()) {
            String curr = ineedtogo.remove();

            if (!ibeenhere.contains(curr)) {
                result.add(curr);
                ibeenhere.add(curr);

                for (String neighbours : adjacencyList.get(curr)) {
                    ineedtogo.add(neighbours);
                }
            }
        }

        return result;
    }

    /**
     * Derives a minimum spanning tree of the graph using Prim's algorithm
     * 
     * @param start the starting node for this search
     * @return a list of edges that form a minimum spanning tree of the graph
     */
    public List<Edge> deriveMST(String start) {

        // made the lists they wanted
        List<Edge> visitedEdges = new ArrayList<>();
        Set<String> visitedVertices = new HashSet<>();
        Map<String, Edge> vertexEdgeMap = new HashMap<>();

        // if our graph doesnt contain start, eat shit.
        if (!this.contains(start)) {
            return visitedEdges;
        }

        visitedVertices.add(start); // we've visited start.

        for (String neighbor : adjacencyList.get(start)) { // add every neighboring vertex to the current edgemap.
            vertexEdgeMap.put(neighbor, new Edge(start, neighbor));
        }

        while (visitedVertices.size() < nodes.size()) { // while we havent visted evertything
            String minVertex = null;
            Edge minEdge = null;

            for (String vertex : vertexEdgeMap.keySet()) { // for every vertex in our edgemap
                if (!visitedVertices.contains(vertex)) { // if we havent visited
                    Edge currentEdge = vertexEdgeMap.get(vertex);
                    // if we have nothing, or if the edge we're looking at has a lower value then
                    // the one we were just looking at
                    if (minEdge == null || (weightsList.get(currentEdge.src()).get(currentEdge.dest())) < (weightsList
                            .get(minEdge.src()).get(minEdge.dest()))) {
                        minEdge = currentEdge; // das the one we want type shit.
                        minVertex = vertex;
                    }
                }
            }

            visitedEdges.add(minEdge);
            visitedVertices.add(minVertex);

            for (String neighbor : adjacencyList.get(minVertex)) { // add all the edges from the one we just added.
                Edge newEdge = new Edge(minVertex, neighbor);
                if (!vertexEdgeMap.containsKey(neighbor)) {
                    vertexEdgeMap.put(neighbor, newEdge);
                } else if (!visitedVertices.contains(neighbor)
                        && (weightsList.get(newEdge.src()).get(newEdge.dest())) < (weightsList
                                .get((vertexEdgeMap.get(neighbor)).src()).get((vertexEdgeMap.get(neighbor)).dest()))) {
                    vertexEdgeMap.put(neighbor, newEdge);
                }

            }

        }
        return visitedEdges;
    }

}