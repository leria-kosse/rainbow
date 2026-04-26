package edu.ttap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ttap.graphs.Edge;
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

        //findAlwaysUnreachable(graphs);
        Graph d = graphs.get(3);
        List<Edge> mst = d.deriveMST("Noyce");

        int totalCost = 0;
        for (Edge e : mst) {
            int w = d.getWeight(e.src(), e.dest()).get();
            totalCost += w;
        }
        System.out.println("Total MST cost: " + totalCost);

        // Print all edges to trace path to Salton
        for (Edge e : mst) {
            System.out.println(e.src() + " -> " + e.dest());
        }
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

    public static Set<String> loadNames(String filename) throws Exception {

        List<String> lines = Files.readAllLines(Path.of(filename));
        Set<String> names = new HashSet<>();

        for (String line : lines) {
            line = line.trim();

            if (!line.isEmpty()) {
                names.add(line);
            }
        }

        return names;
    }

    public static void findAlwaysUnreachable(List<Graph> graphs) throws Exception {

        Set<String> allMachines = loadNames("data/mathlanlist.txt");

        Set<String> alwaysUnreachable = new HashSet<>(allMachines);

        int i = 1;

        for (Graph g : graphs) {

            String start = g.getNodes().iterator().next();

            List<String> reachable = g.collectBreadthFirst(start);

            Set<String> unreachable = new HashSet<>(allMachines);
            unreachable.removeAll(reachable);

            alwaysUnreachable.retainAll(unreachable);

            System.out.println("Graph " + i + " unreachable: " + unreachable);

            if (reachable.containsAll(allMachines)) {
                System.out.println("Graph " + i + " is fully connected!");
            }

            i++;
        }

        System.out.println("Always unreachable: " + alwaysUnreachable);
    }

}
