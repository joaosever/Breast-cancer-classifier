package projeto;


import java.util.ArrayList;




public class MachineLearning {
	
	private Classifier classifier;	
	
	public MachineLearning(DataSet T) { //Pega num dataset
		
		ArrayList<Integer> Domain_class =T.Get_Class_Domain();  //Dominio da classe
		
		MRFT[] MRFTs = new MRFT[Domain_class.size()]; //Cria Array de MRFTs consoante o dominio da classe
		
		double[] Frequency_class = new double [Domain_class.size()]; //Cria Array de doubles consoante o dominio da classe
		
		for (int class_index = 0; class_index < Domain_class.size(); class_index++) { //Itera pelas classes
			
			DataSet Fiber = T.Fiber(Domain_class.get(class_index));  //Produz a fibra para cada classe
			
			WeightedGraph graph = new WeightedGraph(Fiber); //Efetua um grafo pesado para essa fibra
			
			int [] MST = graph.MST(); //Efetua uma maximum spanning tree para essa fibra
			
			MRFT M = new MRFT(MST,Fiber); //Pega numa MST e numa fibra e efetua uma MRFT
			
			MRFTs[class_index] = M; //Calcula as Mcs
			
			Frequency_class[class_index] = (double)Fiber.Size() / T.Size(); //Calcula as frequency classes de cada Mcs
		}		
		
		this.classifier = new Classifier(MRFTs,Frequency_class); //
	}
	
	public MachineLearning(String fileName) {
		ReadFromDisk(fileName);
	}
	
	private void ReadFromDisk(String fileName) {
		this.classifier = Classifier.ReadFromDisk(fileName);
	}
	
	
	public void WriteToDisk(String fileName) {
		this.classifier.WriteToDisk(fileName);
	}
	
	
	public Classifier Get_Classifier() {
		return this.classifier;
	}
	
	public int Classify(int[] X) {
		return this.classifier.Classify(X);
	}
	
}
	

