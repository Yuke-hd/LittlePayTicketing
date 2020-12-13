package Model.Graph;

public class Edge implements Comparable<Edge>{
    private final int source;
    private final int destination;
    private final float weight;

    //region Setter and Getter
    int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public float getWeight() {
        return weight;
    }
    //endregion

    public Edge(int source, int destination, float weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Float.compare(weight, o.weight);
    }
}
