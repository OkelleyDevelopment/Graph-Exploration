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
            g.displayGraphStats();
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
            searchName = "display_stats";
            //System.out.println("Ah, silly goose. Gotta build this");
        } else if(choice == 2){
            //System.out.println("Loading a new map? Sure thing... how does that go again?");
            searchName = "new_map";
        }else if(choice == 3){
            searchName = "DFS";
        } else if(choice == 4){
            searchName = "trans";
        } else if(choice == 5) {
            System.out.println("Dijkstra? A noble man");
            searchName = "Dijkstra";           
        } else if(choice == 6) {
            //System.out.println("It seems the simulation is broken...\nGoodbye");
            System.exit(0);
        } else {
            System.out.println("Is my list a joke to you? Get out!\n");
            System.exit(0);
        }
        //System.out.println("searchName contains ====> " + searchName);
        return searchName;
    }

    private static void displayGraphOptions(){
        System.out.println("========== Graph Options ==========");
        System.out.println(" 1. Display Stats");
        System.out.println(" 2. Load new graph");
        System.out.println("=============================");
        System.out.println(" 3. Depth First Search");
        System.out.println(" 4. Transitive Closure");
        System.out.println(" 5. Dijkstra's Shortest Path");
        System.out.println(" 6. Quit");
        System.out.println("=============================");

    }
}
