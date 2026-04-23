package edu.ttap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.ttap.graphs.Graph;
import edu.ttap.graphs.GraphEntry;

/**
 * The driver for our lab on lists.
 */
public class Main {
    /**
     * The main entry point for the program.
     * 
     * @param args the command-line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        List<String> files = List.of(
                "data/mathlan.A.data",
                "data/mathlan.B.data",
                "data/mathlan.C.data",
                "data/mathlan.D.data",
                "data/mathlan.E.data");

        List<Graph> graphs = new ArrayList<>();

        for (String file : files) {
            graphs.add(loadGraph(file));
        }

        findAlwaysUnreachable(graphs);
        // findFullyConnectedConfig(graphs);
    }

    public static Graph loadGraph(String filename) throws Exception {
        List<String> lines = Files.readAllLines(Path.of(filename));
        List<GraphEntry> entries = new ArrayList<>();

        for (String line : lines) {

            String[] parts = line.split(",");
            // diving the String into source computers, destination computers, and weights
            String src = parts[0];
            String dst = parts[1];
            int weight = Integer.parseInt(parts[2]);

            entries.add(new GraphEntry(src, dst, weight));
        }

        return new Graph(entries);
    }

    public static void findAlwaysUnreachable(List<Graph> graphs) {

        List<String> allUnreachable = new ArrayList<>();
        int i = 1;

        for (Graph g : graphs) {

            // pick a start node
            String start = g.getNodes().iterator().next();

            List<String> reachable = g.collectBreadthFirst(start);

            List<String> unreachable = new ArrayList<>();

            for (String node : g.getNodes()) {
                if (!reachable.contains(node)) {
                    unreachable.add(node);
                    allUnreachable.add(node);
                }
            }

            System.out.println("Graph " + i + ": " + reachable);
            i++;
        }

        System.out.println("ALL: " + allUnreachable);
    }

    
}
