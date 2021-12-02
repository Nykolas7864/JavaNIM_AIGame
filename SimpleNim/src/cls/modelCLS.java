package cls;
import objects.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class modelCLS extends ViewCLS{
	
	static Move move[] = new Move[15];
	static ArrayList<Integer> arr = new ArrayList<Integer>();
	static ViewCLS v = new ViewCLS();
	static ObjectIO o = new ObjectIO();
	
	static String totalCount = v.totalTextField.getText();
	static int counter = Integer.parseInt(totalCount);	
	
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
        
    	for (int i = 0; i <= 14; i++ ) {
    		System.out.println(i);
    		move[i] = new Move(i, 0.34, 0.33, 0.33);
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
	        
	        Move[] obj = (Move[]) objectIn.readObject();
	        move = obj;
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
                		totalMarbles = totalMarbles - p;
                		turn = 0;
                	
                	}
                	else {
                		winner = 1;
                	}
                	v.updateState(totalMarbles, p2.getName());
                    
            }
        } while(!v.stop);
		
		System.out.println("Let's Learn!");
		Learn(winner);
		
		String filepath2;
		ObjectIO objIO = new ObjectIO();
			if(v.LearningCheck.isSelected()) {
				
				String movStrn;
				int ii = 0;
				filepath2 = System.getProperty("user.dir");
		        filepath2 = filepath2 + "\\src\\data\\prescriptiveNN";
		        o.filepath = filepath2;
		        System.out.println("file saved \n");
		        objIO.WriteObjectToFile(move);
		        
		       // System.out.println("Printing whole stm in human readable text! \n");
		        
		        try {
					PrintWriter out = new PrintWriter("STM_Learned.txt");
					for( ii = 0; ii < 15; ii++) {
			        	movStrn = move[ii].toString();
			        	// System.out.println(movStrn);
			        	out.println(movStrn);
			        	out.println("\n");
			        }
					
					out.close();
					
				}
		        catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}

	private static void Learn(int win) {
		// AI Won
		if (v.winner == 2) {
			double Beta = 0.16;
			int count = 0;
			while (!arr.isEmpty()) {
				
				int m = arr.get(count);
				int choice = move[m].getChosenMove();
				
				if(choice == 1) {
					move[m].setProbA(  move[m].getProbA() + ( Beta * (1 - move[m].getProbA() ) )              ); //reward
					move[m].setProbB(   move[m].getProbB() - ( ( Beta * (1 - move[m].getProbB() ) ) / 2 )               ); //adjust
					move[m].setProbC(   move[m].getProbC() - ( ( Beta * (1 - move[m].getProbC() ) ) / 2 )               ); //adjust
				}
				else if (choice == 2) {
					move[m].setProbA(   move[m].getProbA() - ( ( Beta * (1 - move[m].getProbA() ) ) / 2 )               ); //adjust
					move[m].setProbB(  move[m].getProbB() + ( Beta * (1 - move[m].getProbB() ) )              ); //reward
					move[m].setProbC(   move[m].getProbC() - ( ( Beta * (1 - move[m].getProbC() ) ) / 2 )               ); //adjust
				}
				else {
					move[m].setProbA(   move[m].getProbA() - ( ( Beta * (1 - move[m].getProbA() ) ) / 2 )               ); //adjust
					move[m].setProbB(   move[m].getProbB() - ( ( Beta * (1 - move[m].getProbB() ) ) / 2 )               ); //adjust
					move[m].setProbC(  move[m].getProbC() + ( Beta * (1 - move[m].getProbC() ) )              ); //reward
				}
				
				printObjTxt(move[m]);
				System.out.println("We totally only got rewarded for winning!");
				
				/*
				double sum = move[m].getProbA() + move[m].getProbB() + move[m].getProbC();
				
				while (sum <= 0.9969) {
					move[m].setProbA(move[m].getProbA() + 0.001);
					move[m].setProbB(move[m].getProbB() + 0.001);
					move[m].setProbC(move[m].getProbC() + 0.001);
				}
				while (sum >= 1.0031) {
					move[m].setProbA(move[m].getProbA() - 0.001);
					move[m].setProbB(move[m].getProbB() - 0.001);
					move[m].setProbC(move[m].getProbC() - 0.001);
				}
				*/
				
				arr.remove(count);
			}			
		}
		
		// AI Lost
		else {
			
			double betap = 0.08;
			int count = 0;
			while (!arr.isEmpty()) {
				int m = arr.get(count);
				int choice = move[m].getChosenMove();
				
				if(choice == 1) {
					move[m].setProbA(    move[m].getProbA() - (move[m].getProbA() * betap)               ); //punish
					move[m].setProbB(    move[m].getProbB() + ( ( move[m].getProbB() * betap ) / 2)               ); //adjust
					move[m].setProbC(    move[m].getProbC() + ( ( move[m].getProbC() * betap ) / 2)               ); //adjust
				}
				else if (choice == 2) {
					move[m].setProbA(    move[m].getProbA() + ( ( move[m].getProbA() * betap ) / 2)               ); //adjust
					move[m].setProbB(    move[m].getProbB() - (move[m].getProbB() * betap)               ); //punish
					move[m].setProbC(    move[m].getProbC() + ( ( move[m].getProbC() * betap ) / 2)               ); //adjust
				}
				else {
					move[m].setProbA(    move[m].getProbA() + ( ( move[m].getProbA() * betap ) / 2)               ); //adjust
					move[m].setProbB(    move[m].getProbB() + ( ( move[m].getProbB() * betap ) / 2)               ); //adjust
					move[m].setProbC(    move[m].getProbC() - (move[m].getProbC() * betap)               ); //punish
				}
				
				printObjTxt(move[m]);
				
				System.out.println("We totally only got punished for losing!");
				
				/*
				double sum = move[m].getProbA() + move[m].getProbB() + move[m].getProbC();
				
				while (sum <= 0.9969) {
					move[m].setProbA(move[m].getProbA() + 0.001);
					move[m].setProbB(move[m].getProbB() + 0.001);
					move[m].setProbC(move[m].getProbC() + 0.001);
				}
				while (sum >= 1.0031) {
					move[m].setProbA(move[m].getProbA() - 0.001);
					move[m].setProbB(move[m].getProbB() - 0.001);
					move[m].setProbC(move[m].getProbC() - 0.001);
				}
				*/
				arr.remove(count);
			}
			
			// Save STM
			// **********************************************************************************************
			
			
		}		
	}
	
	public static void printObjTxt(Move thisMove) {
		String stringMove = thisMove.toString();
		System.out.println("This Move was updated! \n");
		System.out.println(stringMove);
		System.out.println("\n");
	}

	private static int AITurn(int totalMarbles2) {
		
		arr.add(totalMarbles -1);
		
		double A = move[totalMarbles2 - 1].getProbA();
		double B = A + move[totalMarbles2 - 1].getProbB();
		double C = A + B + move[totalMarbles2 - 1].getProbC();
		
		rand = new Random();
		double randy = rand.nextDouble();
		
		if(randy < B) {
			move[totalMarbles2 - 1].setChosenMove(1);
			return 1;
		}
		else if(randy < C) {
			move[totalMarbles2 - 1].setChosenMove(2);
			return 2;
		}
		else {
			move[totalMarbles2 - 1].setChosenMove(3);
			return 3;
		}
		
		
		
	}
    
    
}