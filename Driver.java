import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The driver for my graph program. 
 *
 * @author Nicholas O'Kelley
 * @version June 10, 2020
 */
public class Driver {

    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("Usage: <mapName> <search>");
            System.exit(0);
        }
        
        boolean running = true;
        boolean validEntry = true;
        // Start the Graph up
        Graph g = new Graph();
        g.buildGraph(args[0]);
        int user_choice = -1;
        Scanner scan = new Scanner(System.in);
        while(running){
            displayMenu();
            do{
                try{
                    user_choice = scan.nextInt();
                    validEntry = true;
                    String search = selectChoice(user_choice);
                    g.startJourney(search, scan);
                    
                } catch (InputMismatchException e){
                    validEntry = false;
                    System.out.println("ERROR: Value is not an integer!");
                }
            } while(!validEntry);

        }
        scan.close();
    }

    private static String selectChoice(int choice){
        String searchName = "";
        if(choice == 1){
            searchName = "DFS";
            //System.out.println("Ah, silly goose. Gotta build this");
        } else if(choice == 2){
            //System.out.println("Dang! 2 in a row");
            searchName = "trans";
        } else if(choice == 3){
            System.out.println("Dijkstra? A noble man");
            searchName = "Dijkstra";
        } else if(choice == 4) {
            System.out.println("Loading a new map? Sure thing... how does that go again?");
        } else{
            //System.out.println("It seems the simulation is broken...\nGoodbye");
            System.exit(0);
        }
        //System.out.println("searchName contains ====> " + searchName);
        return searchName;
    }

    private static void displayMenu(){
        System.out.println("========== Graph Searches ==========");
        System.out.println("1. Depth First Search");
        System.out.println("2. Transitive Closure");
        System.out.println("3. Dijkstra's Shortest Path");
        System.out.println("4. Load new graph");
        System.out.println("5. Quit");
        System.out.println("=============================");
    }
}
