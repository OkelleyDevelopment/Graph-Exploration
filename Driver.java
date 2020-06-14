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
        // Start the Graph up
        Graph g = new Graph();
        g.buildGraph(args[0]);
        int user_choice = -1;
        Scanner scan = new Scanner(System.in);
        while(running){
            displayGraphOptions();
            user_choice = getInput(scan);

            if(user_choice == 1){
                // TODO: Clear the screen
                displayStatOptions();
            } else if(user_choice == 2){
                g.displayGraphStats();
            }
            String search = selectChoice(user_choice);
            graphExploration(search, scan, g);
        }
        scan.close();
    }

    private static int getInput(Scanner scan){
        boolean validEntry = true;
        int user_choice = -1;
        do{
            try{
                user_choice = scan.nextInt();
                validEntry = true;
            } catch (InputMismatchException e){
                validEntry = false;
                System.out.println("ERROR: Value is not an integer!");
            }
        } while(!validEntry);
        return user_choice;
    }

    private static void graphExploration(String search, Scanner scan, Graph g){
        if(!search.equals("new_map") && !search.equals("display_stats")){
            g.startJourney(search, scan);
        } else if(search.equals("display_stats")){

        }else {
            File folder = new File("./maps");
            File[] listOfFiles = folder.listFiles();
            int fileNum = -1;

            for(File file : listOfFiles){

                if(file.isFile()){
                    fileNum += 1;
                    System.out.println("\t"+ fileNum + " " + file.getName());
                }
            }
            
            System.out.println("\nPlease choose from above...");
            int mapNum = scan.nextInt();
            g = new Graph();
            System.out.println("Loading the map " + 
                    listOfFiles[mapNum] + "... ");
            g.buildGraph("" + listOfFiles[mapNum]);
            System.out.println("Done.");
        }
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
            searchName = "new_map";
        } else{
            //System.out.println("It seems the simulation is broken...\nGoodbye");
            System.exit(0);
        }
        //System.out.println("searchName contains ====> " + searchName);
        return searchName;
    }

    private static void displayStatOptions(){
        System.out.println("========== Graph Searches ==========");
        System.out.println("1. Depth First Search");
        System.out.println("2. Transitive Closure");
        System.out.println("3. Dijkstra's Shortest Path");
       System.out.println("=============================");
    }

    private static void displayGraphOptions(){
        System.out.println("========== Graph Options ==========");
        System.out.println("1. Begin Searches");
        System.out.println("2. Display Stats");
        System.out.println("3. Load new graph");
        System.out.println("4. Quit");
        System.out.println("=============================");

    }
}
