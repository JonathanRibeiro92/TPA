package Hash.tadDicionario;
public class Primo {

    private static boolean isprimo(int n)
    {
        int qtdDiv = 0, div =1;
        int limit = (int)Math.sqrt(n);

        while(div < limit)
        {
            if((n%div)==0)
            {
                qtdDiv++;
            }

            div ++;
        }

        return qtdDiv==1;
    }

    public static int primoMaiorqN(int n)
    {
        if(isprimo(n))
        {
            return n;
        }
        return primoMaiorqN(n+1);
    }
}
