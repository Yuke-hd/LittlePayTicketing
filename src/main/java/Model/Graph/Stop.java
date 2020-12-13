package Model.Graph;

import java.util.LinkedList;

public class Stop {
    private int vertices = 6;
    Graph graph;

    public void init() {
        vertices = 6; // 2 * 3
        graph = new Graph(vertices);
        // original price data
        graph.addEdge(1, 2, 3.25f);
        graph.addEdge(2, 3, 5.50f);
        graph.addEdge(1, 3, 7.30f);
    }

    public void add(int source, int destination, float weight) {
        // add 1 new price information should allocate 2 new space
        vertices += 2;
        Graph tempGraph = new Graph(vertices);
        // copy price data from the old price table
        for (LinkedList<Edge> edges : graph.backupAdjacencylist) {
            for (Edge edge : edges) {
                tempGraph.addEdge(edge);
            }
        }
        // add user input price data
        tempGraph.addEdge(source, destination, weight);
        graph = tempGraph;
        graph.printGraph();
    }

    // get price table for a particular stop
    public LinkedList<Model.Graph.Edge> getPriceTableForStop(int num) {
        return graph.adjacencylist[num];
    }

    public LinkedList<Edge>[] getPriceTableForStop() {
        return graph.adjacencylist;
    }
}