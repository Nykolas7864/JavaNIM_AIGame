package objects;

import java.util.Scanner;

public class Player {
    
    //private Scanner in;
    private String name;
    
    // If name is in source code.
    Player(String n, Scanner scan) {
       // in = scan;
        name = n;
    }
    
    // If name is determined by the console.
    public Player(String name2) {
        setName(name2);
    }
    
    // Set name;
    public void setName(String n) {
        name = n;
    }
    
    // Set name via console.
//    public void setName() {
//        System.out.print("Enter name: ");
//        try {
//            if(in.hasNextLine()) {
//                name = in.nextLine();
//            }
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }
    
    // Get name.
    public String getName() {
        return name;
    }
}