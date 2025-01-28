package projeto;

import java.io.Serializable;
import java.util.ArrayList;

public class MRFT implements Serializable{ 
	
	private static final long serialVersionUID = 2L;
	
	private DataSet Fiber;
	
	private int[] tree;
	
	private double delta= 0.2;
	
    private ArrayList<ArrayList<Integer>> Domains;
    
    private int classValue;	
	
	private ArrayList<double [][]> phi_tree;
	
	private boolean is_special;
	
	
	private int special_i;
	private int special_j;

	
	public MRFT(int[] tree, DataSet Fiber){
		this.tree=tree;
		this.Fiber=Fiber;
		
		this.Domains = this.Fiber.Get_Feature_Domains();
		
		
		this.classValue= this.Fiber.Get_Class_Domain().get(0); //o class domain seria [classValue], dai pegar no valor da posicao 0
		
		
		this.phi_tree= new ArrayList<double [][]>();
		
		this.is_special = true;
			

		for (int tree_index = 0; tree_index < tree.length; tree_index++) { //Iterar ao longo da arvore que e do tipo int[]
			
			// (this.tree[tree_index], tree_index) e o mesmo que (node pai, node filho)
			int i = this.tree[tree_index]; //node pai
			int j = tree_index; //node filho
			
			double [][] phi_i_j; 
			
			if (i != -1) { //  todos excepto a raiz da MST
			
				ArrayList<Integer> D_i = Domains.get(i);
				ArrayList<Integer> D_j = Domains.get(j);
				
				
				phi_i_j = new double [D_i.size()][D_j.size()]; //Tamanho da matriz depende dos dominios de i e de j
				
				for (int D_i_index = 0; D_i_index < phi_i_j.length; D_i_index++) { //Iterar para as linhas do dominio i
					for (int D_j_index = 0; D_j_index < phi_i_j[0].length; D_j_index++) { //Iterar para as colunas do dominio j
						phi_i_j[D_i_index][D_j_index] = Phi(i,j,D_i.get(D_i_index),D_j.get(D_j_index)); //Inserir o valor de phi em cada entrada
					}
				}
			}
			else { //Excecao para a raiz da MST
				
				phi_i_j = new double[0][0];
			}
			
			this.phi_tree.add(phi_i_j);
			
		
		}
		
	}
	
	private double Phi(int column_i,int column_j,int xi,int xj) {
		ArrayList<Integer> variables= new ArrayList<Integer>(); //Array com os numeros que representam as colunas i e j
	    variables.add(column_i);
	    variables.add(column_j);
	    
	    ArrayList<Integer> values= new ArrayList<Integer>(); //Valores para colunas i  e j
	    values.add(xi);
	    values.add(xj);
	    
		double numerator = (this.Fiber.Count(variables, values)+delta); //Expressao para o numerador 
				
		if (this.is_special == true) { //caso da aresta especial
			this.is_special = false;
			this.special_i = column_i;
			this.special_j = column_j;
			return numerator / Phi_special_denominator(column_i, column_j);
		}
		else {
			return numerator / Phi_others_denominator( column_i, column_j, xi);
		}
	}
	
	private double Phi_special_denominator(int column_i,int column_j) {
		
	    ArrayList<Integer> D_i = this.Domains.get(column_i);
	    ArrayList<Integer> D_j = this.Domains.get(column_j);
	    
	    return  (this.Fiber.Size()+delta*D_i.size()*D_j.size());
	} 
	
	
	private double Phi_others_denominator(int column_i,int column_j,int xi) {
	    
	    //Denominador
	    ArrayList<Integer> variable_denominator= new ArrayList<Integer>();
	    variable_denominator.add(column_i);

	    ArrayList<Integer> value_denominator= new ArrayList<Integer>();
	    value_denominator.add(xi);
	    
	    
	    ArrayList<Integer> D_j = this.Domains.get(column_j);
	    
		return 	(this.Fiber.Count(variable_denominator,value_denominator) + delta*D_j.size());
	}
	
	public double Probab(int[] X) {
		
		double probab = 1;
		// T.Features() = X.length
		for (int tree_index = 0; tree_index < this.tree.length; tree_index++) {
			// (this.tree[tree_index], tree_index) ,ou seja, aresta (i, j)
			int i = this.tree[tree_index];
			int j = tree_index;
			
			// verificar se j nao e raiz (isto e pai = -1)
			if (i != -1) {
			
				ArrayList<Integer> D_i= Domains.get(i);
				ArrayList<Integer> D_j= Domains.get(j);
				
				// indice de x_i no dominio D_i 
				int x_i_index=-1;
				for (int D_i_index = 0; D_i_index < D_i.size(); D_i_index++) {
					if(D_i.get(D_i_index)==X[i]) {
						x_i_index = D_i_index;
					}
					
				}
				/* indice de x_j no dominio D_j */
				int x_j_index=-1;
				for (int D_j_index = 0; D_j_index < D_j.size(); D_j_index++) {
					if(D_j.get(D_j_index)==X[j]) {
						x_j_index = D_j_index;
					}	
				}
				
				// Ambos os valores existem nos dominios, repectivos, desta fibra
				if ((x_i_index != -1) && (x_j_index != -1)) {
					
					double[][] phi_i_j = this.phi_tree.get(tree_index);
					probab *= phi_i_j[x_i_index][x_j_index];
				}
				// caso em que pelo menos um dos valores observados nao existe no dominio
				// repectivo desta fibra
				else {
					double numerator = 0 + this.delta;
					
					double denominator;
					// aresta especial
					if (i == this.special_i && j == special_j) {
						denominator = Phi_special_denominator(i,j);
					}
					// outras arestas
					else {
						denominator = this.delta * D_j.size();
						// Caso em que j=-1, mas i!= -1
						if(i !=-1) {
							denominator += Phi_others_denominator(i,j,X[i]);
							
						}
					}
					probab *= (numerator/denominator);
				}
			}
		}
		return probab;
	}
	
	public int Get_Class_Value() {
		return this.classValue;
	}
	
}
