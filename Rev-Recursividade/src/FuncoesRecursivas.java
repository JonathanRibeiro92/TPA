import java.util.Arrays;




public class FuncoesRecursivas {
    //1
    public static int somaLista(int[] lista){
        if (lista.length == 1){
            return lista[0];
        }
        else if (lista.length == 0){
            return 0;
        }
        else{
            return lista[0] + somaLista(Arrays.copyOfRange(lista,1,lista.length));
        }
    }
    
    //2
    public static float produto(float a, float b){
       
    	
        if(a<0)
        	return -produto(-a,b);
        if(b<0)
        	return -produto(a,-b);
        
        if((a == 0) || (b == 0 ))
        	return 0;
      
    	if(a==1){
            return b;
        }
        else if(b==1){
            return a;
        }
        else{
            return b + produto(a-1,b);
        }
    }
    
    //3
    public static int divisao(int x, int y){
        if(x<y){
            return 0;
        }
        else if(y==1){
            return x;
        }
        else{
            return 1 + divisao(x-y,y);
        }
    }
    
    //4
   public static double raiz(double n, double t, double chute){
        if(Math.abs((chute*chute) - n) > t){
            return raiz(n,t,(chute+(n/chute))/2);
        }
        else {
        	return chute;
        }
    }
    
    //5
    public static boolean existenaLista(int[] lista, int e){
        if(lista[0] == e){
            return true;
        }
        else if (lista.length == 0){
            return false;
        }
        else{
            return existenaLista(Arrays.copyOfRange(lista,1,lista.length), e);
        }
    }
    
    //6
    public static String inverteString(String palavra){
		
    	if((palavra == null) || (palavra.length() ==1)){
			return palavra;
		}
		return inverteString(palavra.substring(1)) + palavra.charAt(0);
   	
    }
	
    
    //7
    public static int maior (int[] lista){ 
       if (lista.length == 1)
          return lista[0];
       else {
          int x;
          x = maior(Arrays.copyOfRange(lista,0,lista.length-1));  
          if (x > lista[lista.length-1]){
            return x;
          }
          else{
            return lista[lista.length-1];
          }  
       }
    }
    
    //8
    public static int menor (int[] lista){ 
       if (lista.length == 1)
          return lista[0];
       else {
          int x;
          x = menor(Arrays.copyOfRange(lista,0,lista.length-1));  
          if (x < lista[lista.length-1]){
            return x;
          }
          else{
            return lista[lista.length-1];
          }  
       }
    }
    
    //9
    public static boolean ehPalindromo(String palavra, int i, int j){
    	boolean verdade = true;
    	if (i>=j){
			return true;
		}else{
			boolean iguais = palavra.charAt(i) == palavra.charAt(j);
			return (iguais && ((j - i == 1)? verdade :ehPalindromo(palavra, i + 1, j - 1)));
		}
    }	

	
	//10
	public static String decToBin(int x){
		if(x==0)
			return "0";
		
		return decToBin(x/2) + Integer.toString(x % 2);
	}
	
	
	//11
    
    
    
    public static void main(String args[]) {
        int lista[] = {3,7,10};
        //lista = [3,7,10];
        //lista.add(3);
        //lista.add(7);
        //lista.add(10);
        
        int result = somaLista(lista);
        System.out.println("Soma da lista = " + result);
        float result2 = produto(3,4);
        System.out.println("Produto = " + result2);
        int result3 = divisao(3,2);
        System.out.println("Divisao = " + result3);
        int result4 = maior(lista);
        System.out.println("maior da lista = " + result4);
        int result5 = menor(lista);
        System.out.println("menor da lista = " + result5);
        
        double result6 = raiz(90,0.0001,5);
        System.out.println("raiz = " + result6);
		
		String palavra = "ana";
		System.out.println(ehPalindromo(palavra,0,palavra.length() - 1));
		
		
		String palavra2 = "onibus";
		System.out.println(inverteString(palavra));
    }
}