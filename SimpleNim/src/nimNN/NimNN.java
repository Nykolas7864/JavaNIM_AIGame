package nimNN;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.text.DecimalFormat;

import javax.swing.*;

import objects.*;
public class NimNN extends JFrame implements Serializable
	{   double[] iA; 
	    HiddenNeuron[] hn;
	    OutputNeuron[] on;
	    double[] oA;
	   

	   JTextField[] inputAxon= new JTextField[15];  //input axon
	   JTextField[] hiddenAxon= new JTextField[4]; ;
	   
	   JTextField[] outputAxon = new JTextField[3]; 
	   JTextField[] outputAxonT = new JTextField[3]; 
	   JRadioButton[] inputNeuron = new JRadioButton[15];  
	   JRadioButton[] hiddenNeuron= new JRadioButton[4];;
	   JRadioButton[] outputNeuron= new JRadioButton[3];;
	   public Color     TCUColors   = new Color(77,25,121);
	 
	   boolean verbose = true;
	   public DecimalFormat decimal = new DecimalFormat("###,###.####");
	   public JPanel nn = new JPanel(new GridLayout(16,1));
      
		
		public static void main(String args[])
		  {
		    // Construct the frame
		    new NimNN();
		  }
		public NimNN(double[] iAfm,HiddenNeuron[] hnfm, OutputNeuron[] onfm, double[] oAfm) 
        {   setTitle("Neural Network for NIM and the Wizard Frog");
            setFont(new Font("Helvetica",Font.BOLD,18));
            iA = iAfm;
            hn = hnfm;
            on = onfm;
            oA = oAfm;
            displayNet();
            populateNet();
            setBounds(650,150,400,600);
            setVisible(true); 
            validate();
            System.out.println( "here");

         }
     public NimNN()

        {   setTitle("Neural Network for NIM and the Wizard Frog");
            setFont(new Font("Helvetica",Font.BOLD,18));

            //displayNet();

            //setBounds(500,150,500,700);
            //setVisible(true); 
            validate();
            System.out.println( "here");

         }
       
      public void displayNet() 
      {
    	  for ( int i = 0; i< 15; i++)
      	{inputAxon[i] = new JTextField();
      	
           if(i==0) inputNeuron[i] = new JRadioButton("15");
           if(i==1) inputNeuron[i] = new JRadioButton("14");
           if(i==2) inputNeuron[i] = new JRadioButton("13");
           if(i==3) inputNeuron[i] = new JRadioButton("12");
           if(i==4) inputNeuron[i] = new JRadioButton("11");
           if(i==5) inputNeuron[i] = new JRadioButton("10");
           if(i==6) inputNeuron[i] = new JRadioButton("9");
           if(i==7) inputNeuron[i] = new JRadioButton("8");
           if(i==8) inputNeuron[i] = new JRadioButton("7");
           if(i==9) inputNeuron[i] = new JRadioButton("6");
           if(i==10) inputNeuron[i] = new JRadioButton("5");
           if(i==11) inputNeuron[i] = new JRadioButton("4");
           if(i==12) inputNeuron[i] = new JRadioButton("3");
           if(i==13) inputNeuron[i] = new JRadioButton("2");
           if(i==14) inputNeuron[i] = new JRadioButton("1");
           
           inputNeuron[i].setForeground(Color.WHITE);
           
           
            }
      
      for ( int i = 0; i< 3; i++)
    	{outputAxon[i] = new JTextField();
        outputAxonT[i] = new JTextField();

         
         if(i==0) outputNeuron[i] = new JRadioButton("1");
         if(i==1) outputNeuron[i] = new JRadioButton("2");
         if(i==2) outputNeuron[i] = new JRadioButton("3"); 
         outputNeuron[i].setForeground(Color.WHITE);
         }
      
      for ( int i = 0; i< 4; i++)
  	{hiddenAxon[i] = new JTextField("");

       
       if(i==0) hiddenNeuron[i] = new JRadioButton("hgrp 1");
       if(i==1) hiddenNeuron[i] = new JRadioButton("hgrp 2");
       if(i==2) hiddenNeuron[i] = new JRadioButton("hgrp 3");
       if(i==3) hiddenNeuron[i] = new JRadioButton("hgrp 4");  
       hiddenNeuron[i].setForeground(Color.WHITE);
       }
       nn.setForeground(Color.WHITE);
       nn.setBackground(TCUColors);
       add(nn); 
       JLabel li = new JLabel("Input");JLabel lh = new JLabel("Hidden");JLabel lo = new JLabel("Output");
       
       JLabel vA = new JLabel("True");
       
       li.setForeground(Color.WHITE);lh.setForeground(Color.WHITE);lo.setForeground(Color.WHITE);vA.setForeground(Color.WHITE);
       nn.add(li); nn.add(new JLabel("")); nn.add(lh);
       nn.add(new JLabel("")); nn.add(lo); nn.add(new JLabel(""));nn.add(vA);
       for ( int i = 0; i< 15; i++)
      	     
       {      
      	     if(i> 3 ) 
      		 {nn.add(inputAxon[i]);nn.add(inputNeuron[i]); 
      		  nn.add(new JLabel("")); nn.add(new JLabel(""));
      		  nn.add(new JLabel("")); nn.add(new JLabel("")); nn.add(new JLabel(""));}
      	     
      	     else if(i== 3) {
      	    	 
      	    	nn.add(inputAxon[i]); nn.add(inputNeuron[i]); 
  		        nn.add(hiddenAxon[i]); nn.add(hiddenNeuron[i]);
  		        nn.add(new JLabel("")); nn.add(new JLabel("")); nn.add(new JLabel(""));
      	    	 
      	     }
      		 else { nn.add(inputAxon[i]); nn.add(inputNeuron[i]); 
      		        nn.add(hiddenAxon[i]); nn.add(hiddenNeuron[i]);
      		        nn.add(outputAxon[i]);nn.add(outputNeuron[i]); nn.add(outputAxonT[i]);
      		 }
       }
       
      }
      
      
      public void populateNet() 
      { for ( int i = 0; i< 15; i++)
      	{inputAxon[i].setText(""+iA[i]); 

           }
      
      for ( int i = 0; i< 3; i++)
    	{ 
    	 outputAxonT[i].setText(""+oA[i]); 
         outputAxon[i].setText(decimal.format(on[i].axonValue));
         }
      
      for ( int i = 0; i< 4; i++) 
      	  {
    	  if(hn[i].axon==1) hiddenNeuron[i].setSelected(true);
      	   hiddenAxon[i].setText(decimal.format(hn[i].axonValue));
      	  }
      for ( int i = 0; i< 15; i++)
  	 {
        if(iA[i]==1) inputNeuron[i].setSelected(true);
        
	     }
      
      for ( int i = 0; i< 3; i++)
   	 {
         if(oA[i]==1) outputNeuron[i].setSelected(true);
        
 	     }
      
	   }
	}
