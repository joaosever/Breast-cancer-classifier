package projeto;

import java.util.ArrayList;


public class Prob {
	
	private DataSet T;
	private ArrayList<ArrayList<Integer>> Domains;

	public Prob (DataSet T) {
		this.T=T;
		this.Domains = T.Get_Feature_Domains();
	}
	
	public double Probab (int i, int xi) {    
		ArrayList<Integer> variables= new ArrayList<Integer>(); //Criar um array com o número da coluna i
	    variables.add(i);
	    
	    ArrayList<Integer> values= new ArrayList<Integer>(); //Criar um array com o valor xi da coluna i
	    values.add(xi);
		
		
		return (double) (this.T).Count(variables,values) / (this.T).Size(); 
	}
	
	public double Probab(int i, int j, int xi, int xj) {
		ArrayList<Integer> variables= new ArrayList<Integer>(); //Criar um array com os numeros que representam as colunas i e j 
	    variables.add(i);
	    variables.add(j);
	    
	    ArrayList<Integer> values= new ArrayList<Integer>(); //Criar um array com os valores xi e xj dessas colunas
	    values.add(xi);
	    values.add(xj);
	    
	    return (double) (this.T).Count(variables, values) / (this.T).Size();
	}
	
	
	public double I(int column_i, int column_j) {
		ArrayList<Integer> D_i= this.Domains.get(column_i); //Array com o dominio da coluna i
		ArrayList<Integer> D_j= this.Domains.get(column_j); //Array com o dominio da coluna j
		double value=0;
		
		for (int index_i = 0; index_i < D_i.size() ; index_i++) { //Iterar no dominio de i
			for (int index_j = 0; index_j < D_j.size(); index_j++) { //Iterar no dominio de j
				int xi = D_i.get(index_i); //Pegar num valor do dominio de i 
				int xj= D_j.get(index_j); //Pegar num valor do dominio de j
	            //		logaritmo de base e 
				double logarg=Probab(column_i, column_j,xi,xj) / (Probab(column_i,xi)*Probab(column_j,xj)); //Calcular o peso mutuo
				if (logarg != 0) { //Excecao do 0*log(0)
					value = value + Probab(column_i,column_j,xi,xj) * Math.log(logarg);
				}
			}
			
		}
		return value;	
		
	}
}
