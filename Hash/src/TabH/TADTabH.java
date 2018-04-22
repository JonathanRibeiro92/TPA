package TabH;

import java.util.LinkedList;
import hashFunctions.*;

public abstract class TADTabH {
     
    private static Item NO_SUCH_KEY = new Item(null,null);
    private int tamanho;
    private final HashEngine hengine;
    protected int colisoes[];
     
    public TADTabH(){
        this.hengine = new HashEngineDefault();
        this.tamanho = 100;
        colisoes = new int [tamanho];
         
        for(int i = 0; i<tamanho ; i++){
            colisoes[i]= 0;
        }
    }
     
    public TADTabH(int tam,HashEngine hE){
        hengine=hE;
        this.tamanho = tam;
        colisoes = new int [tamanho];
         
        for(int i = 0; i<tamanho ; i++){
            colisoes[i]= 0;
        }
    }
     
    public TADTabH(HashEngine hE){
        hengine = hE;
        tamanho = 100;
         
        colisoes = new int [tamanho];
         
        for(int i = 0; i<tamanho ; i++){
            colisoes[i]= 0;
        }
    }
 
    public int getTamanho() {
        return tamanho;
    }
     
    public abstract Object findElem(Object key);
     
 
    public boolean isEmpty(){
        return getTamanho()==0;
    }
    public int size(){
        return getTamanho();
    }
    public abstract boolean insertItem(Object chave,Object elemento);
     
    public abstract Object removeElem(Object chave);
     
     
    public abstract LinkedList keys();
     
    public abstract LinkedList elements();
     
     
}
