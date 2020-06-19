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

    /**
     * This is the main method for the Graph Exploration Program.
     *
     * @param args - the map file path as a string 
     */ 
    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("Usage: <mapName> ");
            System.exit(0);
        }
        // Boolean for the current state of the program
        boolean running = true;
        // Start the Graph up
        Graph g = new Graph();
        g.buildGraph(args[0]);
        // An int representing the value of the decision made 
        int user_choice = -1;
        // A scanner object to collect user input throughout the program
        // run time.
        Scanner scan = new Scanner(System.in);

        while(running){
            displayMenu();
            user_choice = getInput(scan);
            String search = selectChoice(user_choice);
            graphExploration(search, scan, g);
        }
        // Close out the scanner when finished. 
        scan.close();
    }

    /**
     * A helper method that gathers user input for decisions in the 
     * graph exploration program.
     *
     * @param scan - a scanner object to collect input
     *
     *
     * @return the user choice
     */
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


    /**
     * A static helper method that handles program direction by starting the
     * journey for graph searches/analysis or displaying the stats or 
     * loading in a new graph.
     *
     * @param search - a string with the process or search to complete
     *
     * @param scan - a scanner to accept user input for the graph loading
     *
     * @param g - a graph object to "recreate" in the graph loading process
     *
     * @return none
     */
    private static void graphExploration(String search, Scanner scan, Graph g){

        // TODO: refactor to switch?
        if(!search.equals("new_map") && !search.equals("display_stats") &&
                !search.equals("cycle")){
            g.startJourney(search, scan);
        } else if(search.equals("display_stats")){
            g.displayGraphStats();
        } else if(search.equals("cycle")){
            if(g.cycleSearch()){
                System.out.println("A cycle does exist");
            } else {
                System.out.println("A cycle does not exist");
            }
        } else { 
            // Find, print out, and return the list of available graphs
            File[] listOfFiles = displayAvailGraphs();

            // Prompt user to choose a graph
            System.out.println("\nPlease choose from above...");
            int mapNum = scan.nextInt();

            // Reset the Graph object to defaults
            // And show the status of the loading process (in case of error)
            g = new Graph();
            System.out.println("Loading " + listOfFiles[mapNum] + "... ");
            g.buildGraph("" + listOfFiles[mapNum]);
            System.out.println("Done.\n");
        }
    }

    /**
     * A static helper method to display the available graph text files 
     * in the maps/ directory
     *
     * @param none
     *
     * @return listOfFiles - a File array
     */
    private static File[] displayAvailGraphs(){
        File folder = new File("./maps");
        File[] listOfFiles = folder.listFiles();
        int fileNum = -1;

        // TODO: Clear out the screen. 
        System.out.println("\n========== Maps Available =========");
        for(File file : listOfFiles){
            if(file.isFile()){
                fileNum += 1;
                System.out.println("\t"+ fileNum + " " + file.getName());
            }
        }
        return listOfFiles;
    }

    /**
     * A static helper method that returns the process or search to be
     * completed.
     *
     * @param choice - the integer value of the choice
     * 
     * @return searchName - the search or process to be completed
     */
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
            System.out.println("Ah yes, cycles do go round.");
            searchName = "cycle";           
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

    /**
     * A static helper method that displays the menu for the graph program.
     *
     * @param none
     *
     * @return none 
     */
    private static void displayMenu(){
        System.out.println("========== Graph Options ==========");
        System.out.println(" 1. Display Stats");
        System.out.println(" 2. Load new graph");
        System.out.println("=============================");
        System.out.println(" 3. Depth First Search");
        System.out.println(" 4. Transitive Closure");
        System.out.println(" 5. Cycle Search");
        System.out.println(" 6. Quit");
        System.out.println("=============================");
    }
}
