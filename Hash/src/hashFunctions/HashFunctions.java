package hashFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.math.BigInteger;

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
	public static int TAM_VET = 100;
	public int x=0;
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
	/*	
	private int djb_hash(String key)
	{
	    int h = 0;
	    int i;

	    for (i = 0; i < key.length() ; i++)
	    {
		h = 33 * h + key.charAt(i);
	    }

	    return h;
	}

	private double modified_djb_hash(String key)
	{

	    double h = 0;
	    int i;

	    for (i = 0; i < key.length(); i++)
	    {
		h = 33 * Math.pow(h,key.charAt(i));
	    }

	    return h;
	}
*/
	
	private static BigInteger djb_hash(String key){
	   BigInteger h = new BigInteger("0");	

	    for (int i = 0; i < key.length() ; i++)
	    {
		BigInteger temp = h.multiply(new BigInteger("33"));
            	h = temp.add(new BigInteger("" + key.charAt(i)));
	    }

	    return h;
	}

	private BigInteger modified_djb_hash(String key){

	    BigInteger h = new BigInteger("0");	

	    for (int i = 0; i < key.length(); i++)
	    {
		//h = 33 * Math.pow(h,key.charAt(i));
		BigInteger temp = h.multiply(new BigInteger("33"));
		h = temp.xor(new BigInteger("" + key.charAt(i)));
	    }

	    return h;
	}
	
	/*private long fnv_hash(String key)
	{

		long h = 2166136261;
	    int i;

	    for (i = 0; i < key.length(); i++)
	    {
		h = (h * 16777619) ^ key.charAt(i);
	    }

	    return h;
	}*/
	private BigInteger fnv_hash(String key){

		BigInteger h = new BigInteger("2166136261");
		BigInteger fnvValue = new BigInteger("16777619");

	    for (int i = 0; i < key.length(); i++)
	    {
		h = (h.multiply(fnvValue)).xor(new BigInteger("" + key.charAt(i)));
	    }

	    return h;
	}


	private long elf_hash(String key)
	{

	    long h = 0;
		long g;
	    int i;

	    for (i = 0; i < key.length(); i++)
	    {
		h = (h << 4) + key.charAt(i);
		g = h & 0xf0000000L;

		if (g != 0)
		{
		    h ^= g >> 24;
		}

		h &= ~g;
	    }

	    return h;
	}
		
	private BigInteger jsw_hash(String key)
	{

	    BigInteger h = new BigInteger("16777551");

	    for (int i = 0; i < key.length(); i++)
	    {
		//h = (long)((h << 1 | h >> 31) ^ (tab(key.charAt(i))));
		    h = (h.shiftLeft(1).or(h.shiftRight(31))).or(new BigInteger("" + key.charAt(i)));
	    }

	    return h;
	}

	private int comprimir(int x){

		return x%TAM_VET; 
	}
	
	private int comprimir(BigInteger x){
		return x.mod(new BigInteger(""+TAM_VET)).intValue();
	}
	
	private int comprimir(long x){

		return (int)x % TAM_VET; 
	}
		
		
	public void leArquivo(String arquivo){

		tabHashColisoes = new int[TAM_VET];

		try{
		 BufferedReader br = new BufferedReader(new FileReader(arquivo));
		 while(br.ready()){
		    String linha = br.readLine();
		    int hash = 0;
		    hash =comprimir(fsomaPolinomial(linha));
		    this.tabHashColisoes[hash]++;
		    //System.out.println(linha);
		 }
		 br.close();
	      }catch(IOException ioe){
		 ioe.printStackTrace();
	      }
	   }
		
		
	public void gravaArquivo(int[] obj) throws FileNotFoundException,IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("src\\hashFunctions\\resultado.csv"));
	for(int i = 0; i < obj.length ; i++){
		writer.write(obj[i] + ";");
	    writer.write("\n");
	}
	writer.close();
    }

		
}	


			        
	        
	
