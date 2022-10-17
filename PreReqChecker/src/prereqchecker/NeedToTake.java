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
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {
        // ensures correct files are passed
        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

        // store name of files passed as a command line argument
        String adjListFile = args[0];
        String needToTakeInputFile = args[1];
        String needToTakeOutputFile = args[2];

        // create a DAG for courses
        DiGraph courses = new DiGraph(adjListFile);
        // set file to read from
        StdIn.setFile(needToTakeInputFile);
        // store the course to take from the first line of the file as formatted
        String courseToTake = StdIn.readLine();
        StdIn.readLine();

        // create an array list to store all the courses taken
        ArrayList<String> coursesTaken = new ArrayList<>();
        // read through file
        while(!StdIn.isEmpty()){
            // store course listed on file
            String course = StdIn.readString();
            // run depth first search on each course to look through its prereqs
            courses.depthFirstSearch(course);
            // iterate through all courses and add any courses that have been taken
            for(String crs: courses.getCourses()){
                if(courses.visited(crs) && !coursesTaken.contains(crs))
                    coursesTaken.add(crs);
            }
        }

        // set file to write to
        StdOut.setFile(needToTakeOutputFile);
        // iterate through all the prereqs for the course to take
        for(String preReq: courses.getPreReqs(courseToTake)){
            // run depth first search on each prereq to find all its direct and indirect prereqs
            courses.depthFirstSearch(preReq);
            // iterate through all courses and add them to the file if they must be taken to take the needed course
            for(String crs: courses.getCourses()){
                // add to the file if it has not yet been taken
                if(courses.visited(crs) && !coursesTaken.contains(crs))
                    StdOut.println(crs);
            }
        }
        
    }
}
