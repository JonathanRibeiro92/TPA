package hashFunctions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.io.IOException;
import java.lang.Math;

//
//N=100
//h(x) e comprimir
//
//index = h(x) mod N
//h1(x) = Somatório dos componentes (ascii dos caracteres)
//h2(x) = p(z) onde z=33
//
//ex.: 'goiaba'
//
//p(33) = ascii('g') + ascii('o')*33 + ascii('i')*33² + ...

public class HashFunctions {
	public final int TAM_VET = 100;
	public final int x=0;
	public int[] tabHashColisoes;
	
	//Somatório dos componentes ascii dos caracteres
	private int fsomaComponente(String s){
		int soma = 0;
		int i;
		for(i=0;i<s.length();i++){
			soma+= s.charAt(i);
		}
		return soma;
	}
	
	
	//Somatório dos componentes ascii dos caracteres
		private int fsomaPolinomial(String s){
			int soma = 0;
			int i;
			for(i=0;i<s.length();i++){
				soma+= s.charAt(i)*Math.pow(33, i);
			}
			return soma;
		}
		
		private int comprimir(int x){
			
			return x%TAM_VET; 
		}
		
		
		public void leArquivo(String arquivo){
			
			try{
		         BufferedReader br = new BufferedReader(new FileReader(arquivo));
		         while(br.ready()){
		            String linha = br.readLine();
		            int hash = 0;
		            hash =comprimir(fsomaPolinomial(linha));
		            tabHashColisoes[hash]++;
		            System.out.println(linha);
		         }
		         br.close();
		      }catch(IOException ioe){
		         ioe.printStackTrace();
		      }
		   }

	
}
