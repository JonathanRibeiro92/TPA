package romanos;

//"MMMCMXCIX"

public class Romanos{

    private static String nr;

    private static int[] vNum = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static String[] vRom = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};


    public Romanos(String nr) {
        this.nr = nr;
    }

    public boolean isromano(String str){
        return false;
    }

    public static String soma(String nrA){
        int numInt1 = toInt(nr);
        int numInt2 = toInt(nrA);

        int intResult = numInt1 + numInt2;
        String strResult = toRomano(intResult);

        return strResult;
    }

    private static int letraToNumero(char letra){
        switch (letra) {
            case 'I':  return 1;
            case 'V':  return 5;
            case 'X':  return 10;
            case 'L':  return 50;
            case 'C':  return 100;
            case 'D':  return 500;
            case 'M':  return 1000;
            default:   return -1;
        }
    }

    public static int toInt(String nr){
        if(nr.length()==0){
            throw new NumberFormatException("String vazia!");
        }

        nr = nr.toUpperCase();

        int i = 0;
        int numInt = 0;

        while(i< nr.length()){
            char letraRomano = nr.charAt(i);
            int numero = letraToNumero(letraRomano);

            //letraToNumero devolve -1 se não for caracter romano.
            if(numero<0){
                throw new NumberFormatException("Caracter não romano \"" + letraRomano + "\".");
            }

            i++;
            //final da string
            if( i == nr.length()){
                numInt += numero;
            }else{
                int proxNumero = letraToNumero(nr.charAt(i));
                // exemplo: IV => 5-1
                if(proxNumero > numero){
                    numInt += (proxNumero - numero);
                    i++;
                }else{  //letras não são combinadas
                    numInt += numero;
                }
            }


        }
        if(numInt > 3999){
            throw new NumberFormatException("Valor deve estar entre 1 e 3999.");
        }

        return numInt;
    }

    public static String toRomano(int ni){
        if(ni<1){
            throw new NumberFormatException("Não são aceitos valores negativos ou zero.");
        }
        if (ni > 3999){
            throw new NumberFormatException("Valor deve estar entre 1 e 3999.");
        }
        String romano = "";
        //Percorre vetor de Numeros
        for(int i=0; i<vNum.length;i++){
            while(ni >= vNum[i]){
                romano += vRom[i];
                ni -= vNum[i];
            }
        }

        return romano;
    }

    public static String subtrai(String nrA){

        int numInt1 = toInt(nr);
        int numInt2 = toInt(nrA);

        int intResult = numInt1 - numInt2;
        String strResult = toRomano(intResult);

        return strResult;
    }

    public static void main(String[] args) {

    }
}