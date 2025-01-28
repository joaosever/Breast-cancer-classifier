package projeto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Classifier implements Serializable { 
	
	private static final long serialVersionUID = 1L;
	
	
	private MRFT[] MRFTs;
	private double[] Frequency_class;
	
	public Classifier(MRFT[] MRFTs, double[] Frequency_class) {
		this.MRFTs=MRFTs;
		this.Frequency_class = Frequency_class;	
	}
	
	public int Classify(int[] X) {
		int classValue = -1;
		
		double INF=-999999999;
	    double max = INF;
	    
	    //ArrayList<Double> Probs = new ArrayList<Double>();
	    // Calcular a probaba maxima de todos os MRFs
		for (int class_index= 0; class_index < MRFTs.length; class_index ++) { //Itera pelos MRFTs
			MRFT M = this.MRFTs[class_index];
			
			
			double probab = this.Frequency_class[class_index] * M.Probab(X);
			
			if(max < probab) {
				max = probab;
			    classValue = M.Get_Class_Value(); //fica com a classvalue da probab maxima
		   }
		}
		
		return classValue;
	}	
	
	
	public static Classifier ReadFromDisk(String fileName) { //Le o classificador a partir do disco
		FileInputStream fileIn;
		Classifier classifier = null;
		try {
			fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
   		 
            classifier =  (Classifier) objectIn.readObject();
 
            System.out.println("The classifier has been read from the file");
            System.out.println( classifier);
            objectIn.close();
			
            System.out.println( classifier);
            
            
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return classifier;
	}
	
	
	public void WriteToDisk(String fileName) { //Escreve o classificador disco
		
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
            System.out.println("The classifier was succesfully written to a file");
            
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}

