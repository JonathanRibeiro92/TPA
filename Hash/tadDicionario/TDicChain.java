package Hash.tadDicionario;

import java.util.LinkedList;

import Hash.TabH.TDic;
import Hash.hashFunctions.HashEngine;
import Hash.hashFunctions.HashEngineDefault;
import Hash.tadDicionario.Primo;

class TItemDic{
	private Object key = null;
	private Object dado = null;
	private int cache_hash;
	
	public TItemDic(Object k, Object dado) {
		this.setKey(k);
		this.setDado(dado);
	}
	
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	
	public Object getDado() {
		return dado;
	}
	public void setDado(Object dado) {
		this.dado = dado;
	}
	
	public void set_cache_hash(int hk){
		this.cache_hash = hk;
	}
	
	public int get_cache_hash(){
		return this.cache_hash;
	}
	
}

public class TDicChain extends TDic{

	private int quant_entradas;
	private LinkedList<TItemDic> []vet_conteudo = null;
	private int tam_vet_conteudo = 0;
	private HashEngine he;
	
	public TDicChain() {
        this.he = new HashEngineDefault();
        this.tam_vet_conteudo = 1<<4;
        vet_conteudo = new LinkedList[this.tam_vet_conteudo];
        for(int i=0; i < tam_vet_conteudo; i++) {
			vet_conteudo[i] = new LinkedList<TItemDic>();
		}
}



	public TDicChain(int tam_vet, HashEngine he) {
		tam_vet_conteudo = Primo.primoMaiorqN(tam_vet);
		vet_conteudo = new LinkedList[tam_vet];
		quant_entradas = 0;
		this.he = he;
		
		for(int i=0; i < tam_vet; i++) {
			vet_conteudo[i] = new LinkedList<TItemDic>();
		}
	}
	
	private void redimensiona(){
		int novo_tam = Primo.primoMaiorqN((int)(tam_vet_conteudo *1.5));
		LinkedList<TItemDic> []novo_vet = new LinkedList[novo_tam];
		int novo_pos;
		
		//novo vetor
		for(int i=0; i < novo_tam; i++) {
			novo_vet[i] = new LinkedList<TItemDic>();
		}
		
		//verificando cada posição do vetor anterior
		for(int i=0; i < tam_vet_conteudo; i++) {
			vet_conteudo[i] = new LinkedList<TItemDic>();
			//navegando pelos itens das listas e calculando nova posição
			for(int k=0; k<vet_conteudo[i].size(); k++){
				novo_pos = vet_conteudo[i].get(k).get_cache_hash() % novo_tam;
				novo_vet[novo_pos].add(vet_conteudo[i].get(k));
			}
			
		}
		
		vet_conteudo = novo_vet;
		tam_vet_conteudo = novo_tam;
	}

	public TDicChain(HashEngine he) {
		int tam_vet = 64;
		vet_conteudo = new LinkedList[tam_vet];
		quant_entradas = 0;
		this.he = he;
		
		for(int i=0; i < tam_vet; i++) {
			vet_conteudo[i] = new LinkedList<TItemDic>();
		}
	}
	
	
	
	
	public boolean insertItem (Object k, Object dado){
		TItemDic item = new TItemDic(k,dado);
		
		int hk = he.calcCodeHash(k);
		
		int pos = he.calcCodeHash(k) % tam_vet_conteudo;
		
		//Decidindo se é uma alteração ou a inclusão de uma nova entrada
		int posAchou = pesquisaItem(k,vet_conteudo[pos]);
		
		if (posAchou== -1)
			vet_conteudo[pos].add(item);
		else
			vet_conteudo[pos].get(posAchou).setDado(dado);
		
		return true;

	}
	
	
	private int pesquisaItem(Object k, LinkedList<TItemDic> lst){
		boolean achou = false;
		int i = 0;
		
		while(!achou && (i<lst.size())){
			achou = k.equals(lst.get(i).getKey());
			i++;
		}
		
		if(achou)
			return i-1;
		else
			return -1;
	}
	
	
	public Object removeElement(Object k){
		int pos = he.calcCodeHash(k) % tam_vet_conteudo;
		
		int posItem = pesquisaItem(k,vet_conteudo[pos]);
		TItemDic temp;
		
		if(posItem != -1){
			temp = vet_conteudo[pos].get(posItem);
			vet_conteudo[pos].remove(posItem);
			return temp.getDado();
		}
		
		return null;
	}
	
	public int size(){
		return quant_entradas;
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	
	public Object getKeys(){
		return null;
	}
	
	public Object getItens(){
		return null;
	}

	@Override
	public Object findElement(Object k){
		int pos = he.calcCodeHash(k) % tam_vet_conteudo;

		LinkedList<TItemDic> lst = vet_conteudo[pos];

		boolean achou = false;
		int i = 0;

		while(!achou && (i < lst.size())){
			achou = k.equals(lst.get(i).getKey());
			i++;
		}

		if(achou)
			return vet_conteudo[pos].get(i-1).getDado();
		else
			return null;
	} // fim de findElement

	@Override
	public Object removeElem(Object chave) {
		return null;
	}

	@Override
	public LinkedList keys() {
		return null;
	}

	@Override
	public LinkedList elements() {
		return null;
	}
	
}
