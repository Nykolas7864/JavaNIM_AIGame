package objects;

import java.io.Serializable;

public class NeuralPattern implements Serializable{
public static int marblesLeft;
public static int aiChoice;
public static String[] inputNeuron;
public static double[] inputAxon;
public static String[] outputNeuron;
public static double[] outputAxon;


public int nStates;
public static double[] NeuralPattern(int ml, int ac, boolean test)
{ marblesLeft = ml; aiChoice = ac;
   inputNeuron = new String[15];
     outputNeuron = new String[3];
     inputAxon  = new double[15];
     outputAxon  = new double[3];
     for (int i=0; i < 15; i++) {
    	 inputNeuron[i] = Integer.toString(i);
    	 inputAxon[i] = 0;
     }
     for (int i=0; i < 3; i++) {
    	 outputNeuron[i] = Integer.toString(i+1);
    	 outputAxon[i] = 0;
     }
     inpat(ml);
     outpat(ac);
    
     if(test == true)
    	 return(inputAxon);
     else
    	 return(outputAxon);
   }
public static void inpat(int r)
     {  switch(r)
	    { case 15 : inputAxon[0]=1; break;
	    case 14 : inputAxon[1]=1; break;
	    case 13 : inputAxon[2]=1; break;
	    case 12 : inputAxon[3]=1; break;
	    case 11 : inputAxon[4]=1; break;
	    case 10 : inputAxon[5]=1; break;
	    case 9 : inputAxon[6]=1; break;
	    case 8 : inputAxon[7]=1; break;
	    case 7 : inputAxon[8]=1; break;
	    case 6 : inputAxon[9]=1; break;
	    case 5 : inputAxon[10]=1; break;
	    case 4 : inputAxon[11]=1; break;
	    case 3 : inputAxon[12]=1; break;
	    case 2 : inputAxon[13]=1; break;
	    case 1 : inputAxon[14]=1; break;
	    
	    
	    }
	    
      }
public static void outpat(int r)
{  switch(r)
   { case 1 : outputAxon[0]=1; break;
   case 2 : outputAxon[1]=1; break;
   case 3 : outputAxon[2]=1; break;
   
   }
   
 }

}


