package projeto;
import java.util.Arrays;

public class WeightedGraph {
	private int dim;
	private double ma[][];
	
	public WeightedGraph(int dim) {
		this.dim = dim;
		this.ma = new double[this.dim][this.dim];		
	}
	
	public WeightedGraph(DataSet T) {
		this(T.Features());	
		
		// instanciar uma var da classe que tem o metodo I.
		Prob P = new Prob(T);
		
		for (int node1 = 0; node1 < this.dim; node1++) {
	    	for (int node2 = 0; node2 < this.dim; node2++) {
	    		this.Add(node1,node2, P.I(node1,node2));	
			}
		}
	}
	
	@Override
	public String toString() {
		return "Graph [dim=" + dim + ", ma=" + Arrays.deepToString(ma) + "]";
	}

	
	public void Add(int node1, int node2,double weight) {
		this.ma[node1][node2]=weight;
	}
	

	public int[] MST() {
	
		// initialize, no parents
		int[] MST= new int[this.dim];
		
		for (int MST_index = 0; MST_index < MST.length; MST_index++) {
			MST[MST_index] = -1; //Inicializa-se os index todos a -1
		}		

		 double INF = -9999999;
		
		 int num_edges; // numero de edges
		
		 // Array que vai ficar com os vertices
		 boolean[] selected = new boolean[this.dim];
		
		 // selected comeca como falso
		 Arrays.fill(selected, false);
		
		 // numero de edges comeca a 0
		 num_edges = 0;
		
		
		 // escolher o vertice 0 como raiz
		 selected[0] = true;
		
		 while (num_edges < this.dim - 1) {
		   // Para todos os vertices encontra-se o vertice adjacente com menor peso se ainda nao tiver sido selecionado
		   
		
		   double max = INF;
		   int parent = -1; // row number
		   int child = -1; // col number
		 
		   //descobre a aresta com maior peso ainda disponivel
		   
		   for (int i = 0; i < this.dim; i++) {
			   if (selected[i] == true) {
				   for (int j = 0; j < this.dim; j++) {
			         //nao esta selected mas existe uma edge
			         if ((! selected[j])) {
			        	 if (max < this.ma[i][j]) {
				             max = this.ma[i][j];
				             parent = i;
				             child = j;
			        	 }
			         }
				   }
			   }
		   }
		   
		   
		   MST[child]=parent;
		   selected[child] = true;
		   num_edges++;
		 }
		 	return MST;
	}

}
