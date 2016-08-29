import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
public class GraphTraversal {
	public static void main(String[] args) {

//	    String Input = "E:\\Javatest\\GraphTraversal\\src\\relativity.txt"; // Put the input file name here		
	    String Input = "relativity.txt"; // Put the input file name here		
//	    String Output = "E:\\Javatest\\GraphTraversal\\src\\output.txt"; // Put the output file name here	
	    String Output = "output.txt"; // Put the output file name here	
		if (args.length == 1) {
		    try {
		         Output = args[0];
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[1] + " must be an String.");
		        System.exit(1);
		    }
		}
		Graph G = new Graph();    // Create Graph Object
	       // This will reference one line at a time
        String line = null;
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(Input);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	int ind=line.indexOf(' ');
            	if(ind == -1){
                  	int vertices = Integer.parseInt(line);
            		G.addVertices(vertices);  
            	}
            	else{
            	String word = line.substring(0,ind); 
            	int fromvertex = Integer.parseInt(word.trim());
            	word = line.substring(ind);
            	int tovertex =  Integer.parseInt(word.trim());
        		G.addEdge(fromvertex,tovertex); 
            	}
            }
            // Always close files.
            bufferedReader.close();     
		}    
            catch(FileNotFoundException ex) {
                System.out.println(
                    "Unable to open file '" +
                    Output + "'");               
            }
            catch(IOException ex) {
                System.out.println("Error reading file '" + Output + "'");                 
                // Or we could just do this:
                // ex.printStackTrace();
            }
/*
		Graph G = new Graph();
		G.addVertices(14);
		G.addEdge(1,2);
		G.addEdge(1,0);
		G.addEdge(1,7);
		G.addEdge(0,3);
		G.addEdge(0,4);
		G.addEdge(4,7);
		G.addEdge(4,5);
		G.addEdge(4,6);
		G.addEdge(8,9);
		G.addEdge(9,12);
		G.addEdge(9,10);
		G.addEdge(10,13);
		G.addEdge(10,11);
		G.addEdge(12,13); 
*/		
		PrintWriter pw = null;
		try{
		pw = new PrintWriter(new File(Output));
	    } catch (FileNotFoundException error) {
		    error.printStackTrace();
		    }	
		int count = G.BFSTraversal();
		// System.out.printf("No of connected component %d \n", count);
		pw.printf("No of connected component = %d", count);
		pw.println(" ");
		int largest_size = G.get_largest_size();
		int vertex = G.get_strating_vertex_LCC();
		// System.out.printf("largest_size = %d \n", largest_size);
		pw.printf("Largest Size = %d", largest_size);
		pw.println(" ");	
		G.reset_visited();
		LinkedList<Integer> list;
		list = G.BFS(vertex);	
		list = G.getDiameter(list,pw);
		pw.println("Largest Shortest Distance from Vertex "+list.get(0)+" to vertex  "+list.get(list.size()-1));

		for (int i = 0; i < list.size(); i++)
            pw.printf("%d ",list.get(i));
		pw.close();
	}

}
	

class Graph {
	private Vertex vertexList[];        // Store all Vertex object
	private int adjMatrix[][];          // Adjacent matrix to store connectivity
	private int vertexCount;            // Total number of vertices in a Graph
	private Queue<Integer> theQueue;    // Queue used for Breadth First Search (BFS)
	private int largest_size;           // Largest size of connected component
	private int starting_vertex;        // Starting vertex of largest component
	
	public Graph(){
		vertexCount = 0;
		largest_size = 0;
		starting_vertex = 0;
		theQueue = new LinkedList<Integer>();
	}
	
	public Graph(int no_of_vertices){
		vertexList = new Vertex[no_of_vertices];
		adjMatrix = new int[no_of_vertices][no_of_vertices];
		vertexCount = 0;
		largest_size = 0;
		starting_vertex = 0;
		for (int i=0;i<no_of_vertices;i++)
			for (int j=0; j<no_of_vertices;j++)
				adjMatrix[i][j]= 0;
		theQueue = new LinkedList<Integer>();
	}
	
    public void addVertices(int no_of_vertices){
		vertexList = new Vertex[no_of_vertices];
		adjMatrix = new int[no_of_vertices][no_of_vertices];
		for (int i=0;i < no_of_vertices;i++)
			for (int j=0; j < no_of_vertices;j++)
				adjMatrix[i][j]= 0;	
       	for(int i=0; i < no_of_vertices; i++)
      		addVertex(); 
    }
	
	public void addVertex(char lab){
		vertexList[vertexCount]= new Vertex(lab);
		vertexCount++;
	}
	
	public void addVertex(){
		vertexList[vertexCount]= new Vertex();
		vertexCount++;
	}
	
	public void addEdge(int start, int end){
		adjMatrix[start][end] = 1;
		adjMatrix[end][start] = 1;
	}
	
	public void displayVertex(int v){
//		System.out.print(vertexList[v].label);
		System.out.printf("%d ",v);	
	}
	
	public void reset_visited(){
			for(int j=0; j < vertexCount; j++)   
	     		vertexList[j].visited = false;	
	}
	
	// This is BFS using Queue. It will return no of vertices in a single connected component
	public int bfs(int vertex_source){
		int count = 1;
		reset_visited();
		vertexList[vertex_source].visited = true;
//		displayVertex(vertex_source);
		theQueue.clear();                           // reset the queue		
		theQueue.add(vertex_source);
		while (!theQueue.isEmpty()){
			int v1 = theQueue.remove();
			for (int v2 = 0; v2 < vertexCount; v2++)
		            if ((adjMatrix[v1][v2]== 1) && (vertexList[v2].visited==false))
		            {
		                vertexList[v2].visited = true;
		                count++;
		           // 	displayVertex(v2);
		                theQueue.add(v2);
		            }
		}
		return count;
	}
	
	// This is BFS using Queue. It will return vertices in connected component
	public LinkedList<Integer> BFS(int vertex_source){
		LinkedList<Integer> list = new LinkedList<Integer>();
		vertexList[vertex_source].visited = true;
		list.add(vertex_source);
		theQueue.clear();                           // reset the queue
		theQueue.add(vertex_source);
		while (!theQueue.isEmpty()){
			int v1 = theQueue.remove();
			for (int v2 = 0; v2 < vertexCount; v2++)
		            if ((adjMatrix[v1][v2]== 1) && (vertexList[v2].visited==false))
		            {
		                vertexList[v2].visited = true;
		                list.add(v2);
		                theQueue.add(v2);
		            }
		}
		return list;
	}

// This BFS traversal of graph. It will return Numbers of connected component in graph	
	public int BFSTraversal(){

		int count=0;
		reset_visited();
		for(int i=0; i < vertexCount; i++) 	
     		if (vertexList[i].visited == false){
     			int size = bfs(i);
     			if(size > largest_size)
     			{
     				largest_size = size;
     				starting_vertex = i;
     			}
     			count++;
     		}
		return count;
	}
	
	public int get_largest_size(){
		return largest_size;
	}
	
	public int get_strating_vertex_LCC(){
		return starting_vertex;
	}
	
	// This will find out largest shortest distance of connected graph
	// list: all vertices connected graph
	//printw: File I/O handler where it will write data
	public LinkedList <Integer> getDiameter(LinkedList<Integer> list, PrintWriter printw){
		Map<Integer,Integer> backlinks = new HashMap<Integer,Integer>();
		// this will store all possible path starting from farthest vertex in 
		// connected component given in list
		LinkedList<Integer> dir_list = new LinkedList<Integer>(); // This will store temporary shortest path
		LinkedList<Integer> final_dir_list = new LinkedList<Integer>(); // This will store final shortest path
		int max_path_length=0;
		for (int i = 0; i < list.size(); i++){
			reset_visited();
			int far_vertex = list.get(list.size()-1);     // Hope far_vertex is last entry in list 
			int current = list.get(i);
			vertexList[current].visited = true;
			theQueue.clear();     // reset the queue
			theQueue.add(current);
			backlinks.clear();     // reset the backlinks
			dir_list.clear();      // reset dir_list
			while (!theQueue.isEmpty()){
				current = theQueue.remove();	
				for (int j = 0; j < list.size(); j++){      // see other vertices from current
					int v2 = list.get(j);
		            if ((adjMatrix[current][v2]== 1) && (vertexList[v2].visited==false))
		            {
		                vertexList[v2].visited = true;
		                if (!backlinks.containsKey(v2)) {		              	
		                    backlinks.put(v2, current);
		                    theQueue.add(v2);
		                }
		            }	
				}
			}
			 //Reconstruct path in reverse order and store in dir_list 
			int path_length =0;
			int sp=i+1;
		 	// System.out.println("Shortest Path " + sp);
		 	printw.println("Shortest Path " + sp);		 	
		    for (Integer k = far_vertex; k != null; k = backlinks.get(k)) {
		    	path_length++;
		        dir_list.add(k);
		    } 
			printw.println("Shortest Distance from Vertex "+dir_list.get(0)+" to vertex  "+dir_list.get(dir_list.size()-1)+ " Path Length " + path_length);
			// System.out.println("Shortest Distance from Vertex "+dir_list.get(0)+" to vertex  "+dir_list.get(dir_list.size()-1)+ " Path Length " + path_length);
		    if(path_length > max_path_length){
		    	max_path_length = path_length;
		    	final_dir_list.clear();
		    	for(int k=0; k < dir_list.size();k++){
		    		final_dir_list.add(dir_list.get(k));
		    	}
		    }
		}   
		return final_dir_list;
	}
 
// Vertex is a class where it will store name of vertex (not needed here) and flag for visited or not	
  class Vertex {
	  private char label;
	  private boolean visited;
	  
	  public Vertex(){
		  label = ' ';
		  visited = false;
	  }
	  
	  public Vertex (char lab){
		  label = lab;
		  visited = false;
	  }
  }
}
