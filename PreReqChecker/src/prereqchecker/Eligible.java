package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {
        // ensures correct files are passed
        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }

        // store name of files passed as a command line argument
        String adjListFile = args[0];
        String eligibleInputFile = args[1];
        String eligibleOutputFile = args[2];

        // create a DAG for courses
        DiGraph courses = new DiGraph(adjListFile);
        // read through file
        StdIn.setFile(eligibleInputFile);
        StdIn.readLine();
        // create an array list to store all the prereqs for all the courses listed as taken in file
        ArrayList<String> allPreReqs = new ArrayList<>();
        while(!StdIn.isEmpty()){
            // run depth first search on each course listed and add all indirect and direct prereqs to list
            String course = StdIn.readString();
            courses.depthFirstSearch(course);
            for(String crs: courses.getCourses()){
                // if course was taken, then add it to list
                if(courses.visited(crs))
                    allPreReqs.add(crs);
            }
        }

        // set file to write to 
        StdOut.setFile(eligibleOutputFile);
        // iterate through all courses to find any courses whose prereqs are all fulfilled
        for(String course: courses.getCourses()){
            // add a course to the file if its prereqs have been fulfilled and if it has not yet been taken
            if(allPreReqs.containsAll(courses.getPreReqs(course)) && !allPreReqs.contains(course))
                StdOut.println(course);
        }

    }
}
