package uvg.edu.gt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class GraphApp {
    private static Graph graph;
    private static final String FILE_PATH = "C:\\Users\\bjjaq\\OneDrive\\Escritorio\\HDT10\\hdt10\\src\\main\\java\\uvg\\edu\\gt\\guategrafo.txt";
    private static HashMap<String, Integer> cityIndexMap;
    private static HashMap<Integer, String> indexCityMap;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Inicializando el sistema de grafos para la distribución de suministros médicos.");

        // Leer el archivo y construir el grafo
        readGraphFromFile(FILE_PATH);

        while (!exit) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Mostrar ruta más corta entre dos ciudades");
            System.out.println("2. Indicar ciudad centro del grafo");
            System.out.println("3. Modificar grafo");
            System.out.println("4. Salir");
            System.out.print("Opción: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    findShortestPath(scanner);
                    break;
                case 2:
                    int center = graph.calculateGraphCenter();
                    System.out.println("El centro del grafo es: " + indexCityMap.get(center));
                    break;
                case 3:
                    modifyGraph(scanner);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }

        scanner.close();
    }

    private static void readGraphFromFile(String filePath) {
        File file = new File(filePath);

        try (Scanner fileScanner = new Scanner(file)) {
            cityIndexMap = new HashMap<>();
            indexCityMap = new HashMap<>();
            int index = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");
                String city1 = parts[0];
                String city2 = parts[1];

                if (!cityIndexMap.containsKey(city1)) {
                    cityIndexMap.put(city1, index);
                    indexCityMap.put(index, city1);
                    index++;
                }
                if (!cityIndexMap.containsKey(city2)) {
                    cityIndexMap.put(city2, index);
                    indexCityMap.put(index, city2);
                    index++;
                }
            }

            int size = cityIndexMap.size();
            graph = new Graph(size);

            try (Scanner fileScanner2 = new Scanner(file)) {
                while (fileScanner2.hasNextLine()) {
                    String line = fileScanner2.nextLine();
                    String[] parts = line.split(" ");
                    int from = cityIndexMap.get(parts[0]);
                    int to = cityIndexMap.get(parts[1]);
                    int weight = Integer.parseInt(parts[2]);
                    graph.addEdge(from, to, weight);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        }
    }

    private static void modifyGraph(Scanner scanner) {
        System.out.println("1. Interrupción de tráfico entre dos ciudades");
        System.out.println("2. Establecer conexión entre dos ciudades");
        System.out.print("Seleccione una opción: ");
        int option = scanner.nextInt();
        System.out.print("Ingrese el nombre de la ciudad origen: ");
        String fromCity = scanner.next();
        System.out.print("Ingrese el nombre de la ciudad destino: ");
        String toCity = scanner.next();
        if (option == 1) {
            graph.removeEdge(cityIndexMap.get(fromCity), cityIndexMap.get(toCity));
        } else if (option == 2) {
            System.out.print("Ingrese el peso del arco: ");
            int weight = scanner.nextInt();
            graph.addEdge(cityIndexMap.get(fromCity), cityIndexMap.get(toCity), weight);
        } else {
            System.out.println("Opción no válida.");
        }
        graph.floyd(); // Recalcula las rutas más cortas
        int center = graph.calculateGraphCenter();
        System.out.println("El nuevo centro del grafo es: " + indexCityMap.get(center));
    }

    private static void findShortestPath(Scanner scanner) {
        System.out.print("Ingrese el nombre de la ciudad origen: ");
        String fromCity = scanner.next();
        System.out.print("Ingrese el nombre de la ciudad destino: ");
        String toCity = scanner.next();

        int from = cityIndexMap.get(fromCity);
        int to = cityIndexMap.get(toCity);
        int[][] distances = graph.adjacencyMatrix;
        if (distances[from][to] == Graph.INF) {
            System.out.println("No hay ruta disponible entre " + fromCity + " y " + toCity);
        } else {
            System.out.println("La distancia más corta entre " + fromCity + " y " + toCity + " es " + distances[from][to]);
            System.out.print("Ruta: ");
            graph.printPath(from, to, indexCityMap);
            System.out.println();
        }
    }
}



