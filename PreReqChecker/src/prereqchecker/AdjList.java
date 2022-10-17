package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    public static void main(String[] args) {

        // ensures that correct files are passed
        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }

        // store name of files passed as a command line argument
        String inputFile = args[0];
        String outputFile = args[1];
        StdOut.setFile(outputFile);
        // create a DAG for courses
        DiGraph courses = new DiGraph(inputFile);
        // iterate through all the courses
        for(String course : courses.getCourses()){
            // add each course to the file
            StdOut.print(course);
            // add all the prereqs for each course to the file
            ArrayList<String> preReqs = courses.getPreReqs(course);
            for(String preReq: preReqs)
                StdOut.print(" " + preReq);
            StdOut.println();
        }
    }
}
