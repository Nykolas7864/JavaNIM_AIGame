package nimGA;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*; 
import objects.*;


public class ViewGA extends JFrame

{  
	int[] chromosome = new int[15];
	int gen;
	
	public int start = 0;
	public boolean stop = true;
	public int inaction;
	public int marbles;
	//Images for our "marbles"
	Image lilypadImg;
	Image wizardFrogImg;
    protected ImageIcon lilypadIcon,wizardFrogIcon;
    
    // create JButtons for our lilypads + wizard frog
    protected JButton wizardF = new JButton("Wizard");
    protected JButton[] lily = new JButton[14];
    protected int winner = 0;

    //Player Choice buttons
    protected static JButton choiceOne = new JButton("1");
    protected static JButton choiceTwo = new JButton("2");
    protected static JButton choiceThree = new JButton("3");
    
	//Colors
    protected Color khaki = new Color(220,230,100);
    protected Color   TCUColors   = new Color(77,25,121);
    
    //Title
    protected JLabel  titleL = new JLabel("Nim: By Nick, Emery, and Ryan",JLabel.CENTER);  
    
    //Controls
    protected JButton  playB = new JButton("Play");
    protected static JButton  rulesB = new JButton("Guidelines");
    protected JButton  simulateB = new JButton("Simulate 1 Gen");
    protected JLabel  totalMarblesLabel= new JLabel("Total Marbles:",JLabel.RIGHT);  
    protected static JLabel totalTextField = new JLabel("15");
    protected JCheckBox  LearningCheck = new JCheckBox("AI Moves First",true);
    protected static JTextArea Console = new JTextArea(3, 1);
    protected JScrollPane sp = new JScrollPane(Console);
    
    //JLabels for Guidelines Button + Font
    protected static JLabel  guide1= new JLabel("   Guidelines:");  
    protected static JLabel  guide2= new JLabel("    Objective: Make the opponent grab the last object from the pile (Wizard Frog)");  
    protected static JLabel  guide3= new JLabel("   1. At the bottom of the client type in the number of objects you want to start with.");  
    protected static JLabel  guide4= new JLabel("   2. Hit the play button."); 
    protected static JLabel  guide5= new JLabel("   3. The AI moves first, then the player. Turns alternate between the two.");  
    protected static JLabel  guide6= new JLabel("   4. On every turn each player must remove 1, 2, or 3 objects from the pile.");  
    protected static JLabel  guide7= new JLabel("   5. To remove objects from the pile select the button which indicates your choice.");  
    protected static JLabel  guide8= new JLabel("   6. Whoever takes the last object AKA the wizard frog loses.");  
    
    Font bold1 = new Font("Arial", Font.BOLD,17);
    Font bold2 = new Font("Arial", Font.BOLD,14);
    
    //JPanel Layout
    protected JPanel  displayboard = new JPanel(new FlowLayout());
    protected JPanel  bottomPanel = new JPanel(new BorderLayout());
    protected JPanel  controlsPanel = new JPanel(new GridLayout(2,4));
    protected JPanel  consolePanel = new JPanel(new BorderLayout());
    protected JPanel  aiPanel = new JPanel(new GridLayout(1,5));
    protected JPanel  buttonChoice = new JPanel(new BorderLayout());
    protected JPanel  upperButtonChoice = new JPanel(new FlowLayout());


    //Opens the view, this function is called by Main
	public ViewGA()
	{   
		Arrays.fill(chromosome,new Integer(0));
	    System.out.println(Arrays.toString(chromosome));
	    gen = 0;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout()); 
	    drawDisplay();
        setBounds(450,450,800,600);
        setLocationRelativeTo(null);
	    setVisible(true);	
	}
	
	//Creates the display for main JFrame app
	public void drawDisplay()
	{   
		//Overall layout is borderlayout
		setLayout(new BorderLayout()); 
	
		//In the north is Title
	    add(titleL,BorderLayout.NORTH);
	    titleL.setFont(new Font("Serif", Font.BOLD, 18));
	    titleL.setForeground(TCUColors);
	    
	    //In the center is the board with the images
	    add(displayboard,BorderLayout.CENTER);
	    displayboard.setBackground(TCUColors);
	    controlsPanel.setBackground(khaki);
	    aiPanel.setBackground(khaki);
	    
	    //In the south is the options/buttons
        add(bottomPanel,BorderLayout.SOUTH);
        bottomPanel.add(buttonChoice,BorderLayout.NORTH);
	    bottomPanel.add(controlsPanel,BorderLayout.CENTER); 
	    bottomPanel.add(consolePanel,BorderLayout.SOUTH);
	    consolePanel.add(aiPanel,BorderLayout.NORTH);
	    consolePanel.add(Console,BorderLayout.SOUTH);
	    //consolePanel.add(sp,BorderLayout.SOUTH);
	    
	    //To the north of the southern buttons are the button choices of 1,2,3
	    buttonChoice.add(upperButtonChoice, BorderLayout.NORTH);
	    upperButtonChoice.setBackground(TCUColors);

	    //Functions to add Labels, Buttons, Textfields, etc to each JPanel
	    addControls();
	    addBoard();
	    addChoiceButtons();
	    rulesPane();
	}
	
	public void updateState(int padsleft, String playername) {
		int removePads = 15 - padsleft;
		if (padsleft <= 0) {
			lily[12].setVisible(false);
			lily[13].setVisible(false);
			wizardF.setVisible(false);
			if(playername.equals("Player 1")) {
				winner = 2;
			}
			if(playername.equals("AI")) {
				winner = 1;
			}
			win(playername);
			// System.out.println("Game Ended, Network Updated!");
			stop = true;
		}
		else {
			for (int j = 1; j <= removePads; j++) {
				try {
					lily[j-1].setVisible(false);
					stop = false;
				}
				catch(ArrayIndexOutOfBoundsException a) {
					wizardF.setVisible(false);
					win(playername);
					stop = true;
				}
			}
		}
		totalTextField.setText("" + padsleft);
		return;
	}

	private void win(String playername) {
		Console.setText("Player: " + playername + " Loses and is bad at math");	
		return;
	}

	//Adds buttons for choices
	public void addChoiceButtons(){
		//Set sizes of button
	    choiceOne.setPreferredSize(new Dimension(120, 50));
	    choiceTwo.setPreferredSize(new Dimension(120, 50));
	    choiceThree.setPreferredSize(new Dimension(120, 50));
	    //add the buttons to upperButton row
		upperButtonChoice.add(choiceOne);
		upperButtonChoice.add(choiceTwo);
		upperButtonChoice.add(choiceThree);
	}
	
	//Add buttons/Textfields to control
	public void addControls(){
		controlsPanel.add(playB);
		controlsPanel.add(rulesB);
		controlsPanel.add(simulateB);
		controlsPanel.add(totalMarblesLabel);
		controlsPanel.add(totalTextField);
		controlsPanel.add(LearningCheck);
	}
	
	//Main Center Board
	public void addBoard(){
		lilypadImg  = Toolkit.getDefaultToolkit().getImage("images/lilypad.png");
		lilypadIcon = new ImageIcon(lilypadImg);
		wizardFrogImg  = Toolkit.getDefaultToolkit().getImage("images/wizardFrog.png");
		wizardFrogIcon = new ImageIcon(wizardFrogImg);
		
		//Creation of 14 lilypads
		for (int i = 0; i < 14; i++) {
			lily[i] = new JButton();
			lily[i].setIcon(lilypadIcon);
			displayboard.add(lily[i]);
		}
		
		wizardF.setIcon(wizardFrogIcon);
		displayboard.add(wizardF);
	
	}
	
	//Create the Jframe panel for the rules
	JFrame rules;  
	void rulesPane(){  
	    rules=new JFrame();   
	    rules.setSize(600, 300);  
	    rules.setLayout(null);  
	    //dispose on close means that it will just close the one window not the whole app 
	    rules.setDefaultCloseOperation(rules.DISPOSE_ON_CLOSE);
	    rules.setVisible(false); 
	    //Makes a Gridlayout of 8 rows and 1 column. If you want to add more jlables will need to change gridlayout too!
	    rules.setLayout(new GridLayout(8,1));
	    guide1.setFont(bold1);
	    guide2.setFont(bold2);
	    rules.add(guide1);
	    rules.add(guide2);
	    rules.add(guide3);
	    rules.add(guide4);
	    rules.add(guide5);
	    rules.add(guide6);
	    rules.add(guide7);
	    rules.add(guide8);
	    
	    //This actionlistener determines if guidelines button is pushed
	    rulesB.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e)
	        {
	        	rules.setVisible(true); 
	        	
	        }
	    });

	}  
	
	public int Action() {
		
		choiceOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c1)
			{
				
				marbles = 1;
				inaction = 0;
				return; 
        	
			}
		});
		
		choiceTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c2)
			{
				marbles = 2;
				inaction = 0;
				return; 
        	
			}
		});
		
		choiceThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c3)
			{
				marbles = 3;
				inaction = 0;
				return; 
        	
			}
		});
		
		return marbles;
		
	}
	
	public void start(int currentGen) {
		playB.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		start = 1;
	    		return;
	    	}
	    } );
		simulateB.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		start = 2;
	    		if(currentGen == gen) {
	    			simulate();
	    			return;
	    		}
	    		return;
	    	}
	    } );
	}
	
	private void simulate() {
		gen++;
		Random rand = new Random();
		
		// creates and fills competing chromosomes
		// then also calculates each chromosome's point total
		float avpoints = 0;
		int[][] chromes = new int[10][16];
		for(int c = 0; c < 10; c++) {
			int points = 0;
			for(int g = 0; g < 15; g++) {
				int gene = chromosome[g];
				if (gene == 0 || !(((g + 1 - gene) % 4) == 1)) {
					gene = rand.nextInt(3) + 1;
				}
				if (((g + 1 - gene) % 4) == 1) {
					points++;
				}
				chromes[c][g] = gene;
			}
			chromes[c][15] = points;
			avpoints = avpoints + points;
		}
		
		// finds highest point total among chromosomes
		int max = 0;
		int maxLoc = 0;
		for(int w = 0; w < 10; w++) {
			if(chromes[w][15] > max) {
				max = chromes[w][15];
				maxLoc = w;
			}
		}
		
		// copies points over to global chromosome
		// outputs relevant info
		System.out.println("Generation :" + gen);
		System.out.println("Chromosome number " + maxLoc + " was the most successful chromosome of this generation and had " + max + " points");
		System.out.println("Here are it's genes:");
		for(int s = 1; s < 16; s++) {
			int gene2 = chromes[maxLoc][s-1];
			chromosome[s-1] = gene2;
			System.out.println("State: " + s + ",  Gene value: " + gene2);
		}
		
		// calculates average performance of generation
		avpoints = avpoints / 10;
		System.out.println("The average performance of this generation is: " + avpoints);
		return;
	}
	
	 public static void main(String[ ] args) 
	    {  
		 new ViewGA();
	            
	   }
   
} 