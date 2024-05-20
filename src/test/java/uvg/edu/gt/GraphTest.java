package uvg.edu.gt;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {
    private Graph graph;

    @Before
    public void setUp() {
        graph = new Graph(4); // Suponiendo un grafo con 4 nodos
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(3, 0, 4);
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(0, 2, 5);
        assertEquals(5, graph.adjacencyMatrix[0][2]);
    }

    @Test
    public void testRemoveEdge() {
        graph.removeEdge(1, 2);
        assertEquals(Graph.INF, graph.adjacencyMatrix[1][2]);
    }

    @Test
    public void testFloyd() {
        graph.floyd();
        assertEquals(3, graph.adjacencyMatrix[0][2]);
        assertEquals(6, graph.adjacencyMatrix[0][3]);
    }

    @Test
    public void testCalculateGraphCenter() {
        graph.floyd(); // Asegurar que Floyd se ejecutó para actualizar las distancias
        int center = graph.calculateGraphCenter();
        System.out.println("Centro calculado: " + center);
        assertEquals(0, center); // Dependerá de tu estructura específica del grafo y distancias
    }
}

