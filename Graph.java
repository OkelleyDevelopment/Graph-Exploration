import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class models a graph based on a given input file and allows
 * the user to traverse the graph in various different ways.
 *
 * @author Nicholas O'Kelley
 *
 * @version June 9, 2020
 */

public class Graph {

    // The list of vertices in the graph
    private ArrayList<Vertex> vertexList;
    // Adj List where the list at each index corresponds to the vertices
    // adjacent to vertex ID 
    private ArrayList<ArrayList<Integer>> adjacencyList;
    // A stack for DFS
    private Stack stack;
    // A starting number to initalize our adjMatrix
    private final int maxVertices = 25;
    // The number of total vertices in the vertexList
    private int numOfVertices;

    // The adjacency matrix
    private int[][] adjMatrix;
    
    

    public Graph(){
        this.vertexList = new ArrayList<>();
        this.adjacencyList = new ArrayList<>();
        this.adjMatrix = new int[maxVertices][maxVertices];
        for(int row = 0; row < 20; row++){
            for(int col = 0; col < 20; col++){
                adjMatrix[col][row] = 0;
            }
        }
		stack = new Stack();
    }

    public void startJourney(String searchName, Scanner input){
        System.out.println("Reached startJourney");
        int source = -1;
        int dest = -1;
        //Scanner input = null;
        try{

            //readInput = new Scanner()
			System.out.println("Please enter the source vertex #: ");
            input = new Scanner(System.in);
			source = input.nextInt();
			if (source < 0){
				System.out.println("Please try again with a "
						+ "valid source vertex #.");
				System.exit(0);
			}

            System.out.println("Input the destination vertex #: ");
            dest = input.nextInt();

            //input.close();
        }catch(InputMismatchException e){
            System.out.println("Check startJourney");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        if(searchName.equals("DFS")){
            System.out.println(depthFirstSearch(source, dest));
        } else if(searchName.equals("trans")){
            System.out.println(transitiveClosure());
        }
    }

    public void buildGraph(String inputFile){
        try{
            Scanner sc = new Scanner(new File(inputFile));

            while(sc.hasNextLine()){
                String mapData = sc.nextLine(); 
                Scanner map = new Scanner(mapData);

                int vertexID = 0;
                int neighbor = 0;
                try{
                    vertexID = map.nextInt();
                    neighbor = map.nextInt();
                    map.close();
                } catch(NumberFormatException e){
                    System.out.println("ERROR: The map appears to" +
                            " have a smudge!\nPlease try another map.");
                    System.exit(0);
                }

                int largest = Math.max(vertexID, neighbor);

                for(int i = vertexList.size(); i < largest + 1; i++){
                    vertexList.add(new Vertex(i));
                }

                while(adjacencyList.size() < largest + 1){
                    adjacencyList.add(new ArrayList<>());
                }

                if(!(adjacencyList.get(vertexID).contains(neighbor))){
                    int size = adjacencyList.get(vertexID).size();
                    // if the size is zero, then just add the neighbor
                    if(size == 0){
                        adjacencyList.get(vertexID).add(neighbor);
                    } else{
                        // Sort out the adjacencyList 
                        // and then add in the new neighbor
                        while(size != 0){
                            if(neighbor < adjacencyList.get(vertexID).get(size - 1)){
                                size--;
                            } else {
                                break;
                            }
                        }
                        adjacencyList.get(vertexID).add(size, neighbor);
                    }
                }   
            }
            sc.close();
        } catch(FileNotFoundException e){
            System.out.println("That map is not available...");
            System.exit(0);
        } 
    }




    //------------ Pathfinding and Search Algorithms ----------------//
    
    private void resetVertices(){
        for(int i = 0; i < vertexList.size(); i++){
            vertexList.get(i).setColor("White");
        }
    }

    public String depthFirstSearch(int root, int dest){
        vertexList.get(root).setColor("Blue");
        stack.push(root);
        int i = 0;

        while(!stack.isEmpty()){
            int current = stack.peek();
            ArrayList<Integer> temp = adjacencyList.get(current);
            if(temp.isEmpty()){
                stack.pop();
                i++;
            } else{
                if(i < temp.size()){
                    int adjVertex = temp.get(i);
                    if(adjVertex == dest){
                        String path = ""+dest;
                        while(!stack.isEmpty()){
                            path = stack.pop()+ " -> " + path;
                        }
                        return path;
                    } else{
                        if(!vertexList.get(adjVertex).getColor().equals("Blue")){
                            vertexList.get(adjVertex).setColor("Blue");
                            stack.push(adjVertex);
                            i = 0;
                        } else {
                            i++;
                        }
                    }
                } else {
                    stack.pop();
                    i = 0;
                }
            }
        }
        resetVertices();
        return "No path exists between " + root + " and " + dest;
    }


    public int[][] transitiveClosure(){
        boolean[][] reached = new boolean[numOfVertices][numOfVertices];
        int[][] closure = new int[numOfVertices][numOfVertices];

        for(int i = 0; i < numOfVertices; i++){
            for(int j = 0; j < numOfVertices; j++){
                if(adjMatrix[i][j] == 0){
                    reached[i][j] = false;
                } else{
                    reached[i][j] = true;
                }
            }
        }

        for(int i = 0; i < numOfVertices; i++){
            for(int j = 0; j < numOfVertices; j++){
                if(reached[i][j] == false){
                    closure[i][j] = 0;
                } else {
                    closure[i][j] = 1;
                }
            }
        }
        return closure;
    }

    public void displayGraph(){
        System.out.println("\nVertex List: " + vertexList.toString() +
                "\nAdjacency List: " + adjacencyList.toString());
    }
}
