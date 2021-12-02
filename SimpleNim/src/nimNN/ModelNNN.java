package nimNN;
import objects.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;




public class ModelNNN extends ViewNNN{
	
	static ArrayList<Integer> arr = new ArrayList<Integer>();
	static ViewNNN v = new ViewNNN();
	static NimNN n = new NimNN();

	static ObjectIO o = new ObjectIO();
	
	static String totalCount = v.totalTextField.getText();
	static int counter = Integer.parseInt(totalCount);	
	private static NimNN nn;
	public static NeuralPattern nPat = null;
	private static double alpha;
	static HiddenNeuron[]  hiddenNeuron = new HiddenNeuron[4];
    static OutputNeuron[]  outputNeuron = new OutputNeuron[3];
    
   // public static String filepathNP;
   // public static String filepathW;
	
	public static void createObj(){
        double[] iA= new double[15];
        double [] oA = new double[3];
       //  HiddenNeuron[]  hiddenNeuron = new HiddenNeuron[4];
       //  OutputNeuron[]  outputNeuron = new OutputNeuron[3];

         for(int i = 0; i< 4; i++) hiddenNeuron[i] = new HiddenNeuron();
        for(int i = 0; i< 3; i++) outputNeuron[i] = new OutputNeuron();
        iA = NeuralPattern.NeuralPattern(15, 1, true);
        oA = NeuralPattern.NeuralPattern(15, 1, false);
        
        neuralTraining(15,2);
        neuralTraining(13,1);
        neuralTraining(12,3);
        neuralTraining(9,2);
        neuralTraining(7,2);
        neuralTraining(5,3);
        neuralTraining(2,1);
        
        saveWeightFiles();
        
      //  nn = new NimNN(iA, hiddenNeuron, outputNeuron, oA);
		        

      }
	
	
	
	
    // Game logic
    static int totalMarbles = counter;
    static int turn;
    static int winner = 0;
    
    // Players
    static Player p1;
    static Player p2;
    static Player[] players;
    
    // Import objects
    static Random rand;
    static Scanner in;
    
    // Get number of marbles a player wants to take.
    private static int getMarbles() {
        int numMarbles = 0;
        v.inaction = 1;
        
        try {
            do {
                v.Console.append(" - Select button of number to remove (1-3): ");
                   numMarbles = v.Action();
                
            } while(v.inaction == 1); // between 1-3
        } catch(Exception e) {
            System.out.println(e);
        }
        
        return numMarbles;
    }
    
private static int AITurn(int totalMarbles2) {
	double sum1 = 0;
	double sum2 = 0;
	double sum3 = 0;
	
	for (int i = 0; i < 4; i++) {
		if(1 > hiddenNeuron[i].th) {
			for (int j = 0; j < 3; j++) {
				if(j == 0 ) {
					for (int k = 0; k < 4; k++) {
						sum1 = sum1 + outputNeuron[j].den[k];
					}
				}
				if(j == 1 ) {
					for (int k = 0; k < 4; k++) {
						sum2 = sum2 + outputNeuron[j].den[k];
					}
				}
				if(j == 2 ) {
					for (int k = 0; k < 4; k++) {
						sum3 = sum3 + outputNeuron[j].den[k];
					}
				}
			}
		}
	}
	
	if(sum1 > sum2 && sum1 > sum3) {
		return 1;
	}
	
	if(sum2 > sum1 && sum2 > sum3) {
		return 2;
	}
	
	if(sum3 > sum1 && sum3 > sum2) {
		return 3;
	}
	
	else {
			arr.add(totalMarbles -1);
		
		rand = new Random();
		double randy = rand.nextDouble();
		
		
		if(randy < 0.33) {
			return 1;
		}
		else if(randy < 0.66) {
			return 2;
		}
		else {
			return 3;
		}
	}
	
	
		
	}
    
    // Main game loop.
    private static void run() {
    	
    	rand = new Random();
        in = new Scanner(System.in);
        
        p1 = new Player("Player 1");
        p2 = new Player("Player 2");
        players = new Player[2];
        
        players[0] = p1;
        players[1] = p2;
        
        //Determines who goes first randomly
        turn = rand.nextInt(2);
    	v.stop = false;
        do {
            for(Player p: players) {
                
                // Handles first turn.
                switch(turn) {
                    case 0:
                        break;
                    case 1:
                        turn = 0;
                        continue;
                }
                
                v.Console.setText("\n" + p.getName() + "'s Turn");
               // v.Console.append("\nTotal Marbles Left: " + totalMarbles);
                int q = getMarbles();
                totalMarbles = totalMarbles - q;
                v.updateState(totalMarbles, p.getName());
                
                // Trigger win condition.
                if(totalMarbles <= 0) {
                   // isWinner(p);
                    break;
                }
            }
        } while(!v.stop);
    }

	// Handle game logic.
    public static void main(String args[]) {
    	
    	loadWeightFiles();
    	
        
    	for (int i = 0; i <= 14; i++ ) {
    		System.out.println(i);

    	}
    	
    	
        while(true) {
        	do {
        		v.start();
        	} while (v.start == 0);
        	if(v.LearningCheck.isSelected()) {
        		//Prescriptive
        		run_AI();
        	}
        	else {
    // Descriptive AI no learning
        		run_AI();
        	}
        v.start = 0;
        }
    }

	private static void run_AI()  {
        
		// Fill move array with relevant values
		// ***************************************************************************************************************************************
		String filepath;
		if(v.LearningCheck.isSelected()) {
			filepath = System.getProperty("user.dir");
	        filepath = filepath + "\\src\\data\\prescriptive";

		}
		else {
			filepath = System.getProperty("user.dir");
	        filepath = filepath + "\\src\\data\\descriptive";
		}
		
		try {
			FileInputStream fileIn = new FileInputStream(filepath);
	        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	        

	        objectIn.close();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

		
        p1 = new Player("Player 1");
        p2 = new Player("AI");
        players = new Player[2];
        
        players[0] = p1;
        players[1] = p2;
        
        //Determines who goes first randomly
        turn = 1;
		v.stop = false;
		// createObj(); //This method was originally only used to create a base descriptive file
		do {
                
                // Handles first turn.
			if(turn == 0) {
				
                	v.Console.setText("\n" + p1.getName() + "'s Turn");
                    // v.Console.append("\nTotal Marbles Left: " + totalMarbles);
                	
                	if (totalMarbles != 0) {
                		int q = getMarbles();
                        totalMarbles = totalMarbles - q;
                        turn = 1;
                	}
                	else {
                		winner = 0;
                	}
                    v.updateState(totalMarbles, p1.getName());
			}
			else {
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("possible error");
				}
                	v.Console.setText("\n" + p2.getName() + "'s Turn");
                	
                	if (totalMarbles != 0) {
                		int p = AITurn(totalMarbles);
                		neuralTraining(totalMarbles, p);
                		totalMarbles = totalMarbles - p;
                		turn = 0;
                	
                	}
                	else {
                		winner = 1;
                	}
                	v.updateState(totalMarbles, p2.getName());
                    
            }
        } while(!v.stop);
		saveWeightFiles();
		System.out.println("Network has been Saved!");
		
		
	}

	private static void Learn(int win) {
		// AI Won
		if (v.winner == 2) {
			double Beta = 0.16;
			int count = 0;
			while (!arr.isEmpty()) {
				
				int m = arr.get(count);
				

				System.out.println("We totally only got rewarded for winning!");
				

				
				arr.remove(count);
			}			
		}
		
		// AI Lost
		else {
			
			double betap = 0.08;
			int count = 0;
			while (!arr.isEmpty()) {
				int m = arr.get(count);
								
				System.out.println("We totally only got punished for losing!");
				
				arr.remove(count);
			}
			
			// Save STM
			// **********************************************************************************************
			
			
		}		
	}
	
	
	// public NeuralPattern loadNNPatterns(String file,int in, int hn, int ot)
	public NeuralPattern loadNNPatterns(String file) { 
		
		System.out.println("load nn Patterns");
	     //hiddenNeuron = new HiddenNeuron[hn]; for(int i = 0; i< hn; i++) hiddenNeuron[i] = new HiddenNeuron();
		 //outputNeuron=  new OutputNeuron[ot];  for(int i = 0; i< 11; i++) outputNeuron[i] = new OutputNeuron();
	  try { FileInputStream fi = new FileInputStream(new File("data/" + file+".npat"));
	 		  ObjectInputStream oi = new ObjectInputStream(fi);
	 		  // Read objects
	 		 
	 		  nPat = (NeuralPattern) oi.readObject();
	 	   
	 	     
	 		  oi.close();
	 		  fi.close();
	  
	      } catch (FileNotFoundException e) {
	 			System.out.println("File not found");
	 			 
	 	 } catch (IOException e) {
	 			System.out.println("Error initializing stream");
	 	} catch (ClassNotFoundException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
	//  c.remarks.setText("open file "+file + "patterns and ready to play ");
	  return nPat; 
	 }
    
	
	// public void saveNNPatterns(String file,Vector<NeuralPattern> wr, int in, int hn, int ot)
	public void saveNNPatterns(String file, NeuralPattern nPat) 
	  { System.out.println("save nn Patterns");
	    // hiddenNeuron = new HiddenNeuron[hn]; for(int i = 0; i< hn; i++) hiddenNeuron[i] = new HiddenNeuron();
		// outputNeuron=  new OutputNeuron[ot];  for(int i = 0; i< 11; i++) outputNeuron[i] = new OutputNeuron();
	  try { FileOutputStream fo = new FileOutputStream(new File("data/" + file+".npat"));
	 		 ObjectOutputStream oo = new ObjectOutputStream(fo);
	 		  // save objects
	 		  oo.writeObject(nPat);
	 		  oo.close();
	 		  fo.close();
	  
	      } 
	     catch (FileNotFoundException e) {
	 			System.out.println("File not found"); }
	 	 catch (IOException e) {
	 			System.out.println("Error saving stream"); }
	 	// c.remarks.setText("save file "+file + " patterns ");

	 }
	
	
	
	
	public static void saveWeightFiles() {
		//fileName = c.fileWeightTF.getText();
		
		String filepathW;
		ObjectIO objIO = new ObjectIO();
			
				
				filepathW = System.getProperty("user.dir");
		        filepathW = filepathW + "\\src\\data\\weightsNN";
		        o.filepath = filepathW;
		        System.out.println("file saved \n");
		        objIO.WriteObjectToFile2(hiddenNeuron, outputNeuron);
		        System.out.println("check for save");
	}
	public static void loadWeightFiles() {
		//fileName = c.fileWeightTF.getText();
		System.out.println("check for load");
		String filepathW;
			
				filepathW = System.getProperty("user.dir");
		        filepathW = filepathW + "\\src\\data\\weightsNN";
		        
		
		 try {
			 
	            FileInputStream fileIn = new FileInputStream(filepathW);
	            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	            
	           hiddenNeuron  = (HiddenNeuron[]) objectIn.readObject();
	     	   outputNeuron  = (OutputNeuron[]) objectIn.readObject();
	 
	            System.out.println("The Object has been read from the file");
	            objectIn.close();
	 
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
		// c.remarks.setText("open weighted NN  " );
		  
	  }
	
	public static void neuralTraining(int totalMarbles, int choice) {
		  // propagation
		  System.out.println ( " IP "+ totalMarbles + " OP " + choice );
		 double sum,error[] = new double[15], errorAxon[] = new double[15];
	     double[] iA= new double[15];
	     double [] oA = new double[15];
	     double [][] ddo = new double[15][4];
	     double [][] ddh = new double[4][15];
	     double [] errorInt = new double[4];
	     // Enumeration<NeuralPattern> x = c.nnPatterns.elements();
		 
		    
			 
			 for(int i = 0; i < 15; i++) {
				 if(i > 2) {
					 iA[i] = 0;
				 }
				 else {
					 iA[i] = 0;
					 oA[i] = 0;
				 }
			 }
			 
		      int ii = (totalMarbles - 1); // - 1 for index matching
		      int jj = choice -1; //whatever ai choice is, - 1 for index matching
		    		  
		    	  // 14 - ii looks weird but it's because our input boxes start at 15 and go down to 1 for
		    	  // visual clarity, so for that reason the index is reversed, check NimNN for reference.
		    	  
		    		  iA[14 - ii] = 1; oA[jj] = 1;
		      
		    	  
		    System.out.println ( "new IP "+ totalMarbles + " OP " + choice );  
		  sum=0;
	      for(int i=0; i< hiddenNeuron.length; i++) {
	    	  sum=0;
	    	  
	    	  for(int j=0; j< 15 ; j++) sum = sum+hiddenNeuron[i].den[j]*iA[j]; 
	    	  hiddenNeuron[i].axonValue = 1/(1+Math.exp(-(sum+hiddenNeuron[i].th)));
	    	  if (sum>hiddenNeuron[i].th) hiddenNeuron[i].axon=1;else hiddenNeuron[i].axon=0; 
	    	 // System.out.print(" HIDDEN " + hiddenNeuron[i].axonValue + " " +sum);
	      }
	      System.out.print("\n");
	      for(int i=0; i< outputNeuron.length; i++) {
	    	  sum=0;
	    	  for(int j=0; j< 4 ; j++) sum = sum+outputNeuron[i].den[j]*hiddenNeuron[j].axon; 
	    	  outputNeuron[i].axonValue = 1/(1+Math.exp(-(sum+outputNeuron[i].th)));
	    	  if (sum>outputNeuron[i].th) outputNeuron[i].axon=1; else outputNeuron[i].axon=0; 
	     
	    	 // System.out.print(" OUTPUT " + outputNeuron[i].axonValue + " " +sum);
	      }  System.out.print("\n");	  
	     
	     // Backpropagation
	     for(int i=0; i< outputNeuron.length; i++)
	     { error[i] =(double)(oA[i]- outputNeuron[i].axonValue) ;
	       errorAxon[i] = outputNeuron[i].axonValue*(1-outputNeuron[i].axonValue)*error[i];
	        System.out.println(" for output state " + i + " output " + oA[i] +  " General correction "+  errorAxon[i] + " for " + i + " " + outputNeuron[i].axonValue );
	        
	         for(int j=0; j< 4; j++)   errorInt[j]=0;
	         for(int j=0; j< 4; j++)
	        	{ddo[i][j] = errorAxon[i]*outputNeuron[i].den[j];
	        	 errorInt[j] = ddo[i][j]*hiddenNeuron[j].axonValue*(1-hiddenNeuron[j].axonValue)+ errorInt[j];
	        	 outputNeuron[i].den[j] = outputNeuron[i].den[j] + alpha*ddo[i][j];
	        	 System.out.println(  "for output dendrite j" + j + " new dendrite  "+   outputNeuron[i].den[j]  );
	             }
	          outputNeuron[i].th = outputNeuron[i].th + alpha*errorAxon[i];
	          System.out.println(  "for output " + i + " threshold " +  outputNeuron[i].th  );
	         for(int j=0; j< 4; j++)
	        	     System.out.println(" for hidden " + j + " intermediate axon correction  " +errorInt[j]  );      
	     }
	     
	                 
	     
	      for(int i=0; i< 4; i++)
	    	  
	        {
	    	  for(int j=0; j< 15; j++)
	     	{
	         ddh[i][j] = errorInt[i]*hiddenNeuron[i].den[j];
	         
	         hiddenNeuron[i].den[j] = hiddenNeuron[i].den[j] + alpha*ddh[i][j];
	         System.out.println(  "for hidden dendrite j" + j + " new dendrite  "+   hiddenNeuron[i].den[j]  );
	     	}
	        hiddenNeuron[i].th = hiddenNeuron[i].th + alpha*errorInt[i];
	        System.out.println(" for hidden " + i + " threshold " +  hiddenNeuron[i].th  );
	        }
	      
	       nn = new NimNN(iA, hiddenNeuron, outputNeuron, oA);
	     
	  }
	
}