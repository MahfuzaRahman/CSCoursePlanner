package prereqchecker;
import java.util.*;

/**
 * Directed Acyclic Graph Class
 */
public class DiGraph {
    private HashMap<String, ArrayList<String>> courses; // stores all courses and their prereqs
    private HashMap<String, Integer> courseCodes; // stores a code for each course
    private boolean marked[]; // used for depth first search to mark courses as visited through their codes
    private final int V; // stores the number of courses

    /**
     * Uses a file name to create the adjacency list of courses and the list of code names
     * @param adjListFile name of file to read courses from
     */
    public DiGraph(String adjListFile){
        // initialize hash maps
        courses = new HashMap<>();
        courseCodes = new HashMap<>();

        // set file to read from 
        StdIn.setFile(adjListFile);

        // store number of courses
        V = StdIn.readInt(); 
        StdIn.readLine();

        // store all the courses and their codes
        for(int i = 0; i < V; i++){
            String course = StdIn.readLine();
            // add courses
            courses.put(course, new ArrayList<String>());
            // add course codes
            courseCodes.put(course, i);
        }

        // store all the prereqs for each course
        int numEdges = StdIn.readInt();
        StdIn.readLine();
        for(int i = 0; i < numEdges; i++)
            addEdge(StdIn.readString(), StdIn.readString());
        
    }

    /**
     * Add a prereq to a course
     * @param course name of course
     * @param preReq name of prereq for the course
     */
    public void addEdge(String course, String preReq){
        // add the prereq to the arraylist stored by the hashmap for the course
        courses.get(course).add(preReq);
    }

    /**
     * Search for all the direct and indirect prereqs for a course
     * @param course name of course that all prereqs must be found for 
     */
    public void depthFirstSearch(String course){
        // reset boolean array to all be false
        marked = new boolean[V];
        // call on recursive helper method
        dfs(course);
    }

    /**
     * Recursive helper method to find all the prereqs for a course
     * @param course name of course
     */
    private void dfs(String course){
        // store code from course codes hash map to mark in array as visited
        int code = courseCodes.get(course);
        marked[code] = true;

        // search through each prereq until no paths can be reached
        for(String crs: courses.get(course)){
            code = courseCodes.get(crs);
            // if a course's prereqs have not been search through, then search
            if(!marked[code])
                dfs(crs);
        }
    }

    /**
     * Check if a course is a prereq for a certain course
     * @param preReq name of course 
     * @return true if course is a prereq, false if not
     */
    public boolean visited(String preReq){
        int code = courseCodes.get(preReq);
        return marked[code];
    }

    /**
     * @return set of all courses 
     */
    public Set<String> getCourses(){
        return courses.keySet();
    }

    /**
     * @param course name of course
     * @return all the prereqs for the given course
     */
    public ArrayList<String> getPreReqs(String course){
        return courses.get(course);
    }

}
