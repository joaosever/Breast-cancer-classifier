package projeto;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DataSet implements Serializable {
	
	
		private static final long serialVersionUID = 3L;
		
		private ArrayList<int []> dataList;
		
		private ArrayList<ArrayList<Integer>> Domains;
 		private ArrayList<Integer> Domain_class;
 		
		DataSet(String csvFile)  {
			this.dataList = new ArrayList<int []>();
			String line;
			BufferedReader br;
			
				try {
					br = new BufferedReader(new FileReader(csvFile));
					while((line = br.readLine()) != null) {
						dataList.add(convert(line));
					}
						br.close();
						
				this.Compute_Domains();
						
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
					
		}
		
		private DataSet() { 
			this.dataList = new ArrayList<int []>();
		}

		@Override
		public String toString() {
			String s="[";
			if (dataList.size()>0) s+=Arrays.toString(dataList.get(0));
			for (int i=1; i<dataList.size();i++)
				s+=","+Arrays.toString(dataList.get(i));
			s+="]";
				
			return "Size=" + dataList.size() + " Dataset = " + s;
		}
		
		public static int [] convert (String line) {
		String cvsSplitBy = ",";
		String[] strings     = line.split(cvsSplitBy);
		int[] stringToIntVec = new int[strings.length];
		for (int i = 0; i < strings.length; i++)
			stringToIntVec[i] = Integer.parseInt(strings[i]);
		return stringToIntVec;
		}
		
		public void Add(int[] vector) { //Permite adicionar um vetor a Dataset
		    dataList.add(vector);
		}
		
		public int Count(ArrayList<Integer> variables, ArrayList<Integer> values) { //Pega numa lista de variaveis e uma lista de valores para estas variaveis e verifica quantas vezes essas variaveis tomam simultaneamente esses valores no dataset
			  int counter = 0; 
			  for (int[] data : dataList) { // para cada elemento da dataSet
			    boolean match = true;
			    for (int i = 0; i < variables.size(); i++) {
			      if (data[variables.get(i)] != values.get(i)) {
			        match = false; //nao ha correspondencia
			      }
			    }
			    if (match) {
			      counter++; //counter e incrementado sempre que houver match
			    }
			  }
			  return counter; //vai retornar o valor do counter
			}
		
		public DataSet Fiber(int classValue) { 
		    
			DataSet fiberData = new DataSet();  // Cria uma array list vazia para receber a informacao de um class value
		    for (int[] data : dataList) {
		        if (data[data.length - 1] == classValue) { // Verifica se o ultimo elemento e igual ao da class value
		            fiberData.Add(data);
		        }
		    }
		    
		    fiberData.Compute_Domains();
		    
		    return fiberData; //retorna a lista de informacao relativa ao class value 
		}

		public int Size() { 
			return this.dataList.size();
		}
		
		public int Value (int row_index,int column_index) {
			int[] row = this.dataList.get(row_index); //Pega na linha com index row_index
			return row[column_index]; //Retorna o valor desse linha com indice column_index
			
		}
		
		public int Features() {
			// vemos as colunas s0 da linha 0 por que todas elas tem o mesmo num. de colunas
			int[] row0= this.dataList.get(0);
			return row0.length-1; //numero de features, ou seja, exclui-se a classe
		}
		
		private ArrayList<Integer> Domain(int column) {
			ArrayList<Integer> domain= new ArrayList<Integer>();
			for (int row = 0; row < this.Size(); row++) { //Itera pelas linhas do dataset
				int value=this.Value (row,column); //Valor nessa coluna
				if (! domain.contains(value)) {
					domain.add(value);	//Adiciona 	o valor no dominio
				}
			}
			return domain;
		}
		
		public void Compute_Domains() {

			int class_column= this.Features(); //class_colum e um inteiro que da o indice da coluna das classes
			//Computa o domain de cada feature
			this.Domains= new ArrayList<ArrayList<Integer>>();
			
			for (int feature = 0; feature < this.Features(); feature++) { //itera pela features
				ArrayList<Integer> D_feature = Domain(feature); 
				Domains.add(D_feature); //no final tem os dominios das features
			}
			
			
			// Class Domain
			this.Domain_class= Domain(class_column); //tem o dominio das classes

		}
		
		
		public ArrayList<ArrayList<Integer>> Get_Feature_Domains(){
			return this.Domains;
		}
		
		public ArrayList<Integer> Get_Class_Domain(){
			return this.Domain_class;
		}
		
		
			
}