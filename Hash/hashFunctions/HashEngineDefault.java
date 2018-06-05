package hashFunctions;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
 
public class HashEngineDefault extends HashEngine {
     
     
     @Override
    public int calcCodeHash(Object k){
        long polResult=0;
         
        byte[] vetorBytes = null;
         
        if (k== null) return 0;
         
        try{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(k);
        vetorBytes = byteArrayOutputStream.toByteArray();
         
        }catch (Exception ex){
            System.out.println("Erro na conversão para vetor de bytes");
        }
 
        for(int i = 0; i < vetorBytes.length; i++) {
            polResult = polResult + (int) vetorBytes[i] * (long) Math.pow(27, i);
        }
        return Math.abs((int)polResult);
     
         
    }
 
}
