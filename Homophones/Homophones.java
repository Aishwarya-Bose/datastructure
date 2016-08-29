import java.io.*;
import java.util.*;
public class Homophones {
	public static void main(String[] args){
		// TODO Auto-generated method stub		
		int k=700000; // default no of lines to be printed	
		if (args.length == 1) {
		    try {
		        k = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
		        System.err.println("Argument" + args[1] + " must be an integer.");
		        System.exit(1);
		    }
		}
		Homophones hp=new Homophones();
	       // The name of the file to open.
        String fileName = "E:\\Javatest\\Homophones\\src\\cmudict.0.7a.txt"; // Put the filr name here
        // This will reference one line at a time
        String line = null;
        List<Node> list = new ArrayList<Node>(); // List of Nodes
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
            	    Node mnode = hp. new Node();
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
        long timeNow1 = System.currentTimeMillis(); // This is for time calculation
        //Collections.sort(list, hp.new listComp()); // Takes 15 millisecs
        //list = doInsertionSort(list); // Takes 126797 millisecs
        //list = doSelectionSort(list); // Takes 137891 millisecs
        list = doQuickSort(list);       // Takes 47 millisecs
        long timeNow2 = System.currentTimeMillis();
        long timetaken = timeNow2 - timeNow1;
        // now Print the output
	  for (int i = 0; i < list.size(); i++) {
          if(i == k)break;     // K no of lines to be printed	
	    System.out.printf("%d   %s\n", list.get(i).getcount(),list.get(i).getstring());
	  }
	  System.out.printf("Time taken to sort: %d in milisec", timetaken);
	}    // end of main
	
	// write a function to do insertion sort. No need for creating class
	// because I have to call this to sort arraylist.
	public static List<Node> doInsertionSort(List<Node> input){         
	// following same program I have modified for ListArray (derived from 
	// List that is base class). This program works for Int array and it is 
	// for increasing order. But we require decreasing order.	    
      // public static int[] doInsertionSort(int[] input){	         
	//      int temp;
	//      for (int i = 1; i < input.length; i++) {
	//          for(int j = i ; j > 0 ; j--){
	//              if(input[j] < input[j-1]){
	//                  temp = input[j];
	//                  input[j] = input[j-1];
	//                  input[j-1] = temp;
	//              }
	//          }
	//      }
	//      return input;
	//  }

	    Node temp;
	    for (int i = 1; i < input.size(); i++) {
	       for(int j = i ; j > 0 ; j--){
	            if(input.get(j).getcount() > input.get(j-1).getcount()){   // increasing order
	                temp = input.get(j);
	                input.set(j, input.get(j-1));
	                input.set(j-1, temp);
	            }
	        }
	    }
	    return input;
	}   // end of doInsertionSort
	
	// write a function to do selection sort. No need for creating class
	// because I have to call this to sort arraylist.
      public static List<Node> doSelectionSort(List<Node> arr){
      // foliowing same program I have modified for ListArray (derived from 
      // List that is base class). This program works for Int array and it is 
      // for increasing order. But we require decreasing order.
    	
 //       public static int[] doSelectionSort(int[] arr){           
 //           for (int i = 0; i < arr.length - 1; i++)
 //           {
 //               int index = i;
 //               for (int j = i + 1; j < arr.length; j++)
 //                   if (arr[j] < arr[index])
 //                       index = j;
 //         
 //               int smallerNumber = arr[index]; 
 //               arr[index] = arr[i];
 //               arr[i] = smallerNumber;
 //           }
 //           return arr;
 //       }     
        for (int i = 0; i < arr.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.size(); j++)
            // we want decreasing (max to min) order
                if (arr.get(j).getcount() > arr.get(index).getcount())
                    index = j;   
            Node Node_having_LargeNumber = arr.get(index); // Node having large count
            arr.set(index, arr.get(i));
            arr.set(i,Node_having_LargeNumber);
        }
        return arr;
    }    // end of doSelectionSort
 
	// write a function to do quick sort. No need for creating class
	// because I have to call this to sort arraylist.
    private static List<Node> array; // static declaration one instance only
    public static List<Node> doQuickSort(List<Node> inputArr){
 // foliowing same program I have modified for ListArray (derived from 
 // List that is base class). Following  program works for Int array and it is 
 // for increasing order. But we require decreasing order.    
 //   public void sort(int[] inputArr) {
 //       
 //       if (inputArr == null || inputArr.length == 0) {
 //           return;
 //       }
 //       this.array = inputArr;
 //       length = inputArr.length;
 //       quickSort(0, length - 1);
 //   }
 //
 //   private void quickSort(int lowerIndex, int higherIndex) {
 //        
 //       int i = lowerIndex;
 //       int j = higherIndex;
 //       // calculate pivot number, I am taking pivot as middle index number
 //       int pivot = array[lowerIndex+(higherIndex-lowerIndex)/2];
 //       // Divide into two arrays
 //       while (i <= j) {
 //           /**
 //            * In each iteration, we will identify a number from left side which
 //            * is greater then the pivot value, and also we will identify a number
 //            * from right side which is less then the pivot value. Once the search
 //            * is done, then we exchange both numbers.
 //            */
 //           while (array[i] < pivot) {
 //               i++;
 //           }
 //           while (array[j] > pivot) {
 //               j--;
 //           }
 //           if (i <= j) {
 //               exchangeNumbers(i, j);
 //               //move index to next position on both sides
 //               i++;
 //               j--;
 //           }
 //       }
 //       // call quickSort() method recursively
 //       if (lowerIndex < j)
 //           quickSort(lowerIndex, j);
 //       if (i < higherIndex)
 //           quickSort(i, higherIndex);
 //   }
 
 //   private void exchangeNumbers(int i, int j) {
 //       int temp = array[i];
 //       array[i] = array[j];
 //       array[j] = temp;
 //   }
        
    	int length = inputArr.size();
        if (length == 0) {
            return null;
        }
        array = inputArr;
        quickSort(0, length - 1);
        return array;
    }  // end of doQuickSort
     
    private static void quickSort(int lowerIndex, int higherIndex) {
             
        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        int pivot = array.get(lowerIndex+(higherIndex-lowerIndex)/2).getcount();
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is less then the pivot value, and also we will identify a number
             * from right side which is greater then the pivot value. Once the search
             * is done, then we exchange both numbers (here it is node having numbers).
             */
            while (array.get(i).getcount() > pivot) {
                i++;
            }
            while (array.get(j).getcount() < pivot) {
                j--;
            }
            if (i <= j) {
              //now  exchangeNode
                Node temp = array.get(i); 
                array.set(i, array.get(j));
                array.set(j,temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }  // end of quickSort
           	
	    class Node{
			// data carried by this node..
			int count;       // no of strings
			int hashval;     // hashval
			String str;      // str contains all strings having same phonemes
	 
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
	    }  // end of class Node
	 
//   this class is required for comparison purpose for sorting	   
	   class listComp implements Comparator<Node>{
	//	   
	//	    @Override
		    public int compare(Node n1, Node n2) {
		    	int mycompare = n2.getcount() - n1.getcount();
		    		return mycompare;
				    }
	   } // end of listComp
}  // end of HomoPhones class
