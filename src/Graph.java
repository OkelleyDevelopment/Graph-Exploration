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
    private final int maxVertices = 50;
    // The number of total vertices in the vertexList
    private int numOfVertices;
    // The adjMatrix
    private int[][] adjMatrix;

    // The name of the current graph
    private String name;
    

    /**
     * Constructor
     *
     * @param none
     */
    public Graph(){
        this.name = "";
        this.vertexList = new ArrayList<>();
        this.adjacencyList = new ArrayList<>();
        this.adjMatrix = new int[maxVertices][maxVertices];

        for(int i = 0; i < maxVertices; i++){
            for(int j = 0; j < maxVertices; j++){
                this.adjMatrix[i][j]= 0;
            }
        }
		this.stack = new Stack();
    }

    public String getName(){
        return this.name;
    }

    /**
     * A method that gets user input for the start node and end vertex that will
     * then call the appropritate method for the process specified.
     *
     * @param serachName - the process or search name to be passed
     *
     * @param input - a scanner to gather the vertices needed
     *
     * @return none
     */
    public void startJourney(String searchName, Scanner input){
        //System.out.println("Reached startJourney");
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
            System.out.println("\nTHE DFS PATH: " + depthFirstSearch(source, dest) + "\n");
        }
    }


    /**
     * This method constructs the graph by reading the input file and 
     * adding Vertex objects to the appropritate lists.
     *
     * @param inputFile - the string of the path to the input file
     *
     * @return none
     */
    public void buildGraph(String inputFile){
        try{
            Scanner sc = new Scanner(new File(inputFile));
            this.name = sc.nextLine();
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
                adjMatrix[vertexID][neighbor] = 1;
                //System.out.println("VertexID: " + vertexID);
                //System.out.println("neighbor: " + neighbor);

                for(int i = vertexList.size(); i < largest + 1; i++){
                    vertexList.add(new Vertex(i));
                }
                // This adds in the needed array lists for the 
                // adjacencyList (the largest node + 1)
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
            numOfVertices = vertexList.size();
        } catch(FileNotFoundException e){
            System.out.println("That map is not available...");
            System.exit(0);
        } 
    }




    //------------ Pathfinding and Search Algorithms ----------------//    
    
    /**
     * A method to reset the vertices after a search has been executed, allowing
     * multiple seraches to be performed on a single program loading.
     *
     * @param none
     *
     * @return none
     */
    private void resetVertices(){
        for(int i = 0; i < vertexList.size(); i++){
            vertexList.get(i).setColor("White");
        }
    }

    /**
     * THis is the depth first search method, one of many algorithms 
     * used when analyzing graphs and finding paths. 
     *
     * @param root - the root vertex
     *
     * @param dest - the destination vertex
     *
     * @return String - the string containing the path 
     */
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
        return "No path exists between " + root + " and " + dest + "\n";
    }


    public boolean cycleSearch(){
        // Flush the stack incase of inproper usage
        stack.clear();

        // A counter variable
        int count = 0;
        // Setting colors to used in the cycle search
        String visited = "Red";
        String popped = "Black";
        
        // Set the first vertex (zero)
        vertexList.get(0).setColor(visited);
        stack.push(0);

        while(!stack.isEmpty()){
            int peek = stack.peek();
            ArrayList<Integer> temp = adjacencyList.get(peek);
            
            // Check to see if the stack is empty, if so, add the first vertex
            if(temp.isEmpty()){
                int poppedVertex = stack.pop();
                vertexList.get(poppedVertex).setColor(popped);
                count++;
            } else {
                // if the count is less than the size of adjacency list
                // then iterate over the list
                if(count < temp.size()){
                    int adjacent = temp.get(count);
                    // return true for a cycle 
                    if(vertexList.get(adjacent).getColor().equals(visited)){
                        return true;
                    } else if(!(vertexList.get(adjacent).getColor().equals(popped))){
                        // This keeps processing the vertices until
                        // a cycle is reached or is out of vertices
                        vertexList.get(adjacent).setColor(visited);
                        stack.push(adjacent);
                        count = 0;
                    }
                } else {
                    // Get the next vertex to try assuming the visited
                    // color has not been found yet.
                    int poppedVertex = stack.pop();
                    vertexList.get(poppedVertex).setColor(popped);
                    count++;
                }
            }
        }
        // Reset the vertices before exiting
        resetVertices();
        // Return false in the event no cycle is found
        return false;
    }


    /**
     * This method is for calculating the transitive closure of an adjacency 
     * matrix.
     *
     * @param none
     *
     * @return int[][] - a 2D metrix that holds the results.
     */
    public int[][] transitiveClosure(){

        //int[][] A = adjMatrix;
        int[][] B = popMatrix(numOfVertices);

        // Implementing Warshall's Algorithm
        // Basing this algorithm from what i learned in this powerpoint
        // https://cs.winona.edu/lin/cs440/ch08-2.pdf
        for(int k = 0; k < numOfVertices; k++){
            for(int i = 0; i < numOfVertices; i++){
                for(int j = 0; j < numOfVertices; j++){
                    // We can reduce this to a bitwise operation since 
                    // zero and one are being compared.
                    B[i][j] = bitwiseOr(adjMatrix[i][j], bitwiseAnd(adjMatrix[i][k], adjMatrix[k][j]));
                    //System.out.println("B[" + i + "][" + j + "]");
                }
            }
            // We copy the result back to the adjMatrix so the calculations
            // can resume
            for (int i = 0; i < numOfVertices; i++){
                for (int j = 0; j < numOfVertices; j++){
                    adjMatrix[i][j] = B[i][j];
                    
                }
            }
        }
        return adjMatrix;
    }


//----------------- Helper Methods ------------------//

    public static int[][] popMatrix(int nPop){
        //create new matrix based on input param size
        int[][] zeroMatrix = new int[nPop][nPop];
        
        //populate new matrix with all zero's
        for (int t = 0; t < nPop; t++){
            for (int y = 0; y < nPop; y++){
                zeroMatrix[t][y] = 0;
            }
        }
        
        //returns new matrix filled with all zero's
        return zeroMatrix;
    }

    public void displayMatrix(int[][] matrix){
        //System.out.println("numOfVertices: " + numOfVertices);
        System.out.println("\nBegin Transitive Closure:");
        for(int i = 0; i < numOfVertices; i++){
            for(int j = 0; j < numOfVertices; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("End Transitive Closure\n");
    }

    public int bitwiseAnd(int a, int b){
        if(a == 1 && b == 1){
            return 1;
        } else {
            return 0;
        }
    }

    public int bitwiseOr(int a, int b){
        if(a == 0 && b == 0){
            return 0;
        } else {
            return 1;
        }
    }


    /**
     * This method is used to display details about the current graph.
     *
     * @param none
     *
     * @return none
     */
    public void displayGraphStats(){
        System.out.println("\n\t====== " + this.name + " ======");
        for(int i = 0; i < vertexList.size(); i++){
            System.out.println("\t" + vertexList.get(i).toString() +
                  " " + adjacencyList.get(i));
        }
        System.out.println("\n");
    }
}
