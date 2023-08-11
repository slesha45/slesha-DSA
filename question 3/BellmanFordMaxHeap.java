//Question 3.b)
import java.util.*;

class Edge {
    int source, destination, weight;
    
    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

public class BellmanFordMaxHeap {
    public static void bellmanFord(List<Edge> edges, int vertices, int source) {
        int[] distance = new int[vertices];
        Arrays.fill(distance, Integer.MIN_VALUE); // Initialize distances with a very small value
        distance[source] = 0; // Set the distance from the source to itself as 0
        
        // Relaxation process
        for (int i = 0; i < vertices - 1; i++) {
            for (Edge edge : edges) {
                int u = edge.source;
                int v = edge.destination;
                int w = edge.weight;
                
                // Check if the relaxation condition holds and update the distance
                if (distance[u] != Integer.MIN_VALUE && distance[u] + w > distance[v]) {
                    distance[v] = distance[u] + w;
                }
            }
        }
        
        boolean hasNegativeCycle = false;
        // Check for negative weight cycles
        for (Edge edge : edges) {
            int u = edge.source;
            int v = edge.destination;
            int w = edge.weight;
            
            if (distance[u] != Integer.MIN_VALUE && distance[u] + w > distance[v]) {
                hasNegativeCycle = true;
                break;
            }
        }
        
        if (hasNegativeCycle) {
            System.out.println("Graph contains a negative weight cycle.");
        } else {
            System.out.println("Shortest distances from source " + source + ":");
            for (int i = 0; i < vertices; i++) {
                System.out.println("Vertex " + i + ": " + distance[i]);
            }
        }
    }
    
    public static void main(String[] args) {
        int vertices = 5;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 6));
        edges.add(new Edge(0, 3, 7));
        edges.add(new Edge(1, 2, 5));
        edges.add(new Edge(1, 3, 8));
        edges.add(new Edge(1, 4, -4));
        edges.add(new Edge(2, 1, -2));
        edges.add(new Edge(3, 2, -3));
        edges.add(new Edge(3, 4, 9));
        edges.add(new Edge(4, 0, 2));
        edges.add(new Edge(4, 2, 7));
        
        int source = 0;
        bellmanFord(edges, vertices, source);
    }
}