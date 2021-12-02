package cls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectIO {

	
	static Move move[] = new Move[15];

	
    public static String filepath="C:\\Users\\nex78\\Desktop\\descriptive";

    public static void main(String args[]) {
 
        ObjectIO objIO = new ObjectIO();
 
        for (int i = 0; i <= 14; i++ ) {
            move[i] = new Move(i, 0.34, 0.33, 0.33);
        }
        
        // objIO.WriteObjectToFile(move);


        //read object from file
        Move readMove = (Move) objIO.ReadObjectFromFile(filepath);
        System.out.println(readMove);
    }
 
    public void WriteObjectToFile(Object serObj) {
 
        try {
 
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public Object ReadObjectFromFile(String filepath) {

        try {
 
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
 
            Object obj = objectIn.readObject();
 
            System.out.println("The Object has been read from the file");
            objectIn.close();
            return obj;
 
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}