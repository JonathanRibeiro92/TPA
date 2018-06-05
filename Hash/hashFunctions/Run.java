package hashFunctions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Run {

	public static void main(String[] args) throws FileNotFoundException, IOException {
        
        HashFunctions hashFunc = new HashFunctions();
        
        hashFunc.leArquivo("src\\hashFunctions\\teste.txt");
        hashFunc.gravaArquivo(hashFunc.tabHashColisoes);
        System.out.println("Arquivo salvo com sucesso.");

	}


}
