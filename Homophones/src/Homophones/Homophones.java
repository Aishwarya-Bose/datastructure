package Homophones;

import java.io.*;
import java.util.*;
public class Homophones {
	public static void main(String[] args){
		// TODO Auto-generated method stub		
		int k=70000; // default no of lines to be printed	
		if (args.length == 1) {
		    try {
		        k = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[1] + " must be an integer.");
		        System.exit(1);
		    }
		}
	       // The name of the file to open.
       String fileName = "F:\\Ritu\\Javaproject\\Homophones\\src\\Homophones\\cmudict.0.7a.txt"; // Put the filr name here
        // This will reference one line at a time
        String line = null;
        List<Node> list = new ArrayList<Node>(); // List of Node
        HashMap<Integer, String> hmap = new HashMap<Integer, String>();
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
            	if (!line.startsWith(";;;"))  // remove all lines begin with ;;;
            	{
            	  int ind=line.indexOf(' ');
            	  String word = line.substring(0,ind);   // word
            	 // System.out.println(word);  
            	  String tobehashed = line.substring(ind);  // phonemes for that word
            	 // System.out.println(tobehashed);
            	  int hcode=tobehashed.hashCode();
            	  // See in hashmap hcode is there or not
            	  if(hmap.get(hcode) != null){
            			  int i;
            		       for (i = 0; i < list.size(); i++) {
            		    	    if (list.get(i).gethashval()==hcode)break;
            		        }
            		       list.get(i).incrstring(word);   		  
            	  }
            	  else{
            		  Node mnode = new Node();
            		  mnode.incrstring(word);
                      mnode.sethashval(hcode);
                      list.add(mnode);
                      hmap.put(hcode, word);
            	  }
            	}
            }  
            // Always close files.
            bufferedReader.close();        
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +
                fileName + "'");               
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                 
            // Or we could just do this:
            // ex.printStackTrace();
        }
        
        // Now sorting arraylist based on count stored in node having all text with same phonemes
        Collections.sort(list,new listComp()); 
        // now Print the output
	    for (int i = 0; i < list.size(); i++) {
            if(i == k)break;     // K no of lines to be printed	
	    	System.out.printf("%d   %s\n", list.get(i).getcount(),list.get(i).getstring());
	       }
        
	}
}
	
	   class Node{
	 
			// data carried by this node..
			int count;       // no of string
			int hashval;     // hashval
			String str;      // str contains all string having same phonemes
	 
			// Node constructor
			public Node() {
				this.count = 0;
				this.str = " ";
				this.hashval = 0;
			}
	 
			// these methods should be self-explanatory
			public int getcount() {
				return this.count;
			}
			
			public int gethashval(){
				return this.hashval;
			}
			
			public void sethashval(int val){
				this.hashval = val;
			}
			
			public void incrstring(String string) {
				if(this.str==" "){
					this.str=string;
				}
				else{
					this.str = this.str + "  " + string;
				} 
				this.count++;
			}
	 
			public String getstring() {
				return this.str;
			}
	    }
	 
//   this class is required for comarison perpose for sorting	   
	   class listComp implements Comparator<Node>{
	//	   
	//	    @Override
		    public int compare(Node n1, Node n2) {
		    	int mycompare = n2.getcount() - n1.getcount();
		    		return mycompare;
				    }
	   }

