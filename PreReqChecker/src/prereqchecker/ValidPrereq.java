package prereqchecker;

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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {
        // ensures correct files are passed
        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }

        // store name of files passed as a command line argument
        String inputFile = args[0];
        String preReqFile = args[1];
        String outputFile = args[2];

        // create a DAG for courses
        DiGraph courses = new DiGraph(inputFile);
        
        // read through file
        StdIn.setFile(preReqFile);
        // run depth first search on the first listed course on the file to find all of its indirect and direct prereqs        
        String course = StdIn.readString();
        courses.depthFirstSearch(course);
        // set file to write to 
        StdOut.setFile(outputFile);
        // check if the second listed course on the input file was visted after running dfs to see if it is a valid prereq
        // print YES to file if it is, NO if it is not
        String preReq = StdIn.readString();
        if(courses.visited(preReq))
            StdOut.print("YES");
        else {   
            // otherwise, check if any of the prereqs of the second course conflict with the course
            // if not, it is a valid prereq, but if so, it is not a valid prereq
            courses.depthFirstSearch(preReq);
            if(!courses.visited(course))
                StdOut.print("YES");
            else
                StdOut.print("NO");
        }
    }
}
