package uvg.edu.gt;
import java.util.HashMap;

public class Graph {
    public static final int INF = Integer.MAX_VALUE; // Representa una distancia infinita
    int[][] adjacencyMatrix; // Cambiado a paquete privado para acceso directo desde GraphApp
    int[][] predecessorMatrix; // Matriz de predecesores

    // Constructor del grafo
    public Graph(int size) {
        adjacencyMatrix = new int[size][size];
        predecessorMatrix = new int[size][size];
        // Inicializa la matriz con "infinito" excepto en la diagonal principal
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    adjacencyMatrix[i][j] = 0;
                } else {
                    adjacencyMatrix[i][j] = INF;
                }
                predecessorMatrix[i][j] = i;
            }
        }
    }

    // Añadir un arco al grafo
    public void addEdge(int from, int to, int weight) {
        adjacencyMatrix[from][to] = weight;
        predecessorMatrix[from][to] = from;
    }

    // Eliminar un arco del grafo
    public void removeEdge(int from, int to) {
        adjacencyMatrix[from][to] = INF;
        predecessorMatrix[from][to] = -1;
    }

    // Implementación del algoritmo de Floyd-Warshall
    public void floyd() {
        int size = adjacencyMatrix.length;
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (adjacencyMatrix[i][k] != INF && adjacencyMatrix[k][j] != INF && adjacencyMatrix[i][k] + adjacencyMatrix[k][j] < adjacencyMatrix[i][j]) {
                        adjacencyMatrix[i][j] = adjacencyMatrix[i][k] + adjacencyMatrix[k][j];
                        predecessorMatrix[i][j] = predecessorMatrix[k][j];
                    }
                }
            }
        }
    }

    // Mostrar la matriz de adyacencia
    public void printMatrix() {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(adjacencyMatrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int calculateGraphCenter() {
        int size = adjacencyMatrix.length;
        int[] maxDistances = new int[size];
        int minDistance = Integer.MAX_VALUE;
        int centerNode = -1;

        System.out.println("Matriz de adyacencia:");
        printMatrix();

        for (int i = 0; i < size; i++) {
            int maxDistance = 0;
            for (int j = 0; j < size; j++) {
                if (adjacencyMatrix[i][j] > maxDistance && adjacencyMatrix[i][j] != INF) {
                    maxDistance = adjacencyMatrix[i][j];
                }
            }
            maxDistances[i] = maxDistance;
        }

        System.out.println("Distancias máximas por nodo: ");
    for (int i = 0; i < size; i++) {
        System.out.println("Nodo " + i + ": " + maxDistances[i]);

    }

        for (int i = 0; i < size; i++) {
            if (maxDistances[i] < minDistance) {
                minDistance = maxDistances[i];
                centerNode = i;
            }
        }
        System.out.println("Centro del grafo: " + centerNode);
        return centerNode;
    }

    // Método para reconstruir el camino más corto usando la matriz de predecesores
    public void printPath(int from, int to, HashMap<Integer, String> indexCityMap) {
        if (from == to) {
            System.out.print(indexCityMap.get(from));
        } else if (predecessorMatrix[from][to] == -1) {
            System.out.print("No hay camino disponible");
        } else {
            printPath(from, predecessorMatrix[from][to], indexCityMap);
            System.out.print(" -> " + indexCityMap.get(to));
        }
    }
}


