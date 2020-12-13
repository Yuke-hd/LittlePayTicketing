package Model.Graph;

import java.util.LinkedList;

public class Graph {
    int vertices;
    LinkedList<Edge>[] adjacencylist;
    LinkedList<Edge>[] backupAdjacencylist;

    Graph(int vertices) {
        this.vertices = vertices;
        adjacencylist = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencylist[i] = new LinkedList<>();
        }

        backupAdjacencylist = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            backupAdjacencylist[i] = new LinkedList<>();
        }
    }

    void addEdge(Edge edge) {
        addEdge(edge.getSource(), edge.getDestination(), edge.getWeight());
    }

    void addEdge(int source, int destination, float weight) {
        Edge edge = new Edge(source, destination, weight);
        // check if user input price data already exist
        for (Edge e : adjacencylist[source]) {
            if (e.getDestination() == destination) {
                System.out.println("dup found");
                return;
            }
        }
        // add s1 to s2 price = 2
        adjacencylist[source].addFirst(edge);
        // also add it to a backup
        backupAdjacencylist[source].addFirst(edge);
        // add s2 to s1 price = 2
        edge = new Edge(destination, source, weight);
        adjacencylist[destination].addFirst(edge);
    }

    void printGraph() {
        for (int i = 0; i < vertices; i++) {
            LinkedList<Edge> list = adjacencylist[i];
            for (Edge edge : list) {
                System.out.println("Stop" + i + " to Stop" +
                        edge.getDestination() + " price: " + edge.getWeight());
            }
        }
    }
}
