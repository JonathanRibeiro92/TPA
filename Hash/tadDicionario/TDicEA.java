package Hash.tadDicionario;

import Hash.TabH.TDic;
import java.util.LinkedList;
import Hash.TabH.Item;
import Hash.hashFunctions.*;
//TODO
public class TDicEA extends TDic{

    public static final Item NO_SUCH_KEY = new Item(null, null);
    private static final Item DISPONIVEL = new Item(null, null);
    private Item[] conteudos;
    private HashEngine hengine = null;
    private int N;
    private int quant = 0;

    public TDicEA(int n, HashEngine pHashEngine) {

        N = Primo.primoMaiorqN(n);
        conteudos = new Item[N];
        hengine = pHashEngine;
        quant = 0;
    }

    public TDicEA(HashEngine pHashEngine) {
        N = Primo.primoMaiorqN(1<<4);
        conteudos = new Item[N];
        hengine = pHashEngine;
        quant = 0;
    }

    public TDicEA() {
        N = Primo.primoMaiorqN(1<<4);
        conteudos = new Item[N];
        hengine = new HashEngineDefault();
        quant = 0;
    }

    public TDicEA(int n){
        hengine = new HashEngineDefault();
        N = Primo.primoMaiorqN(n);
        conteudos = new Item[N];
        quant = 0;
    }

    private int encontraItem(Object k) {
        int pos = hengine.calcCodeHash(k) % this.conteudos.length;
        int j = pos;

        do {
            if (conteudos[j] == null) {
                return -1;
            }

            if (conteudos[j].equals(TDicEA.DISPONIVEL)) {
                j = (j + 1) % N;
            } else if (conteudos[j].getKey().equals(k)) {
                return j;
            } else
            {
                j = (j + 1) % N;
            }

        } while (j != pos);

        return -1;
    }

    private void redimensiona() {
        int NN = Primo.primoMaiorqN(N);
        Item[] novosConteudos = new Item[NN];

        int j = 0;

        // Varre o vetor de conteúdos transferindo o seu conteúdo para o vetor novosConteudos.
        while (j < conteudos.length) {
            if ((conteudos[j] != null) && (conteudos[j] != DISPONIVEL)) {
                Object k = conteudos[j].getKey();
                Object pelem = conteudos[j].getElement();

                int pos = conteudos[j].getCacheHCode() % NN;
                int i = pos;
                boolean fim = false;

                do {
                    // Altera um item já existente.
                    if ((novosConteudos[i] != null) && (novosConteudos[i].getKey().equals(k))) {
                        novosConteudos[i].setElem(pelem);
                        fim = true;
                    } else {
                        // Inclui um item novo.
                        if ((novosConteudos[i] == null) || (novosConteudos[i] == DISPONIVEL)) {
                            novosConteudos[i] = conteudos[j];
                            fim = true;
                        }
                    }

                    i = (i + 1) % NN;
                } while ((i != pos) && !fim);
            } 

            j = j + 1;
        }	

        conteudos = novosConteudos;
        N = NN;
    }

    
    /*public boolean insertItem(Object k, Object elem) {
        if (this.quant <= this.conteudos.length * 0.8) {
            int hash = this.hengine.calcCodeHash(k);
            int index = hash % this.conteudos.length;
            if (this.conteudos == null) {
                this.conteudos = new Item[N];
                this.conteudos[index] = new Item(elem,k, hash);
                this.quant++;
                return true;
            }
            else if(this.conteudos[index] == null){
                this.conteudos[index] = new Item(elem,k, hash);
                this.quant++;
                return true;
            }
            else {
                //se existem chaves iguais
                if (this.conteudos[index].getKey() == k) {
                    //altera o elemento
                    this.conteudos[index].setElem(elem);
                    return true;
                } else {
                    int i = 1;
                    //procura o proximo lugar vago
                    while ((i+index) < this.conteudos.length && this.conteudos[index + i] != null) {
                        i++;
                    }
                    if(i+index == this.conteudos.length){
                        i = 0;
                        boolean achou = false;
                        while (achou == false && i < index){
                            if(this.conteudos[i] != null){
                                i++;
                            }else {
                                achou = true;
                            }
                        }
                        this.conteudos[i] = new Item(elem,k, hash);
                        this.quant++;
                        return true;
                    }
                    else {
                        this.conteudos[index + i] = new Item(elem,k, hash);
                        this.quant++;
                        return true;
                    }
                    
                }
            }
        }
        else {
            this.redimensiona();
            this.insertItem(k,elem);
            return true;
        }
                    

    }*/
    
    public int[] find(Object k)
    {
        int firstNull = -1;
        try {
            int hash = this.hengine.calcCodeHash(k);
            int vectorPos = hash % this.conteudos.length;

            int searchPos = vectorPos;

            if(conteudos[vectorPos] != null && conteudos[vectorPos].getKey().equals(k))
            {
                return new int[]{vectorPos,firstNull};
            }else
            {
                do{
                    if(conteudos[searchPos]==null && firstNull == -1)
                    {
                        firstNull = searchPos;
                    }
                    else if(conteudos[searchPos] != null && conteudos[searchPos].getKey().equals(k))
                    {
                        return new int[]{searchPos,firstNull};
                    }
                    searchPos = (searchPos + 1) % this.conteudos.length;
                }while (searchPos != vectorPos);
            }

            return new int[]{-1,firstNull};
        }catch (Exception e){
            return new int[]{-1,firstNull};
        }
    }
    
    public boolean insertItem(Object k, Object elem) {
        if(this.quant <= this.conteudos.length * 0.8){
            redimensiona();
        }

        try {
            int hash = this.hengine.calcCodeHash(k);
            int vectorPos = hash % this.conteudos.length;
            int pos = vectorPos;
            int[] find = find(k);
            int posFind = find[0];
            int firstNull = find[1];
            if(posFind != -1)
            {
                conteudos[posFind].setElem(elem);
                return true;
            }
            else{
                if(firstNull != -1)
                {
                    conteudos[firstNull] = new Item(k,elem, hash);
                    this.quant++;
                    return true;
                }
                return false;
            }
        }catch (Exception e)
        {
            return false;
        }
    }

    public Object findElement(Object k) {
        int indice = encontraItem(k);

        if (indice == -1) {
            return NO_SUCH_KEY;
        } else {
            return conteudos[indice].getElement();
        }
    }



    @Override
    public Object removeElement(Object k) {
        int indice = encontraItem(k);

        if (indice == -1) {
            return NO_SUCH_KEY;
        } else {
            Item item = conteudos[indice];
            this.conteudos[indice] = null;
            this.quant--;
            return item;
        }
    }
   
    

    @Override
    public boolean isEmpty() {
        return quant == 0;
    } 

    public int size() {
        return quant;
    }

    public LinkedList<Object> keys() {
        LinkedList<Object> lstKeys = new LinkedList<>();

        int i = 0;

        while (i < conteudos.length) {
            if ((conteudos[i] != null) && (conteudos[i] != DISPONIVEL)) {
                lstKeys.add(conteudos[i].getKey());
            }

            i = i + 1;
        } 

        return lstKeys;
    } 

    public LinkedList<Object> elements() {
        LinkedList<Object> lstElements = new LinkedList<>();

        int i = 0;

        while (i < conteudos.length) {
            if ((conteudos[i] != null) && (conteudos[i] != DISPONIVEL)) {
                lstElements.add(conteudos[i].getElement());
            }

            i = i + 1;
        } 

        return lstElements;
    }


} 
