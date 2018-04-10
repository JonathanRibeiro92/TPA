package tadDicionario;

import java.util.LinkedList;
import tadDicionario.HashEngine;


class TItemDic{
	private Object key = null;
	private Object dado = null;
	
	
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
		
	
	
}

public class TDicChain {

	private int quant_entradas;
	private LinkedList<TItemDic> []vet_conteudo = null;
	private int tam_vet_conteudo = 0;
	private HashEngine he;
	
	

	public TDicChain(int tam_vet, HashEngine he) {
		tam_vet_conteudo = primoMaiorqN(tam_vet);
		vet_conteudo = new LinkedList[tam_vet];
		quant_entradas = 0;
		this.he = he;
		
		for(int i=0; i < tam_vet; i++) {
			vet_conteudo[i] = new LinkedList<TItemDic>();
		}
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
	
	
	private boolean isprimo(int n) {
		if (n < 2) {
	        return false;
		}else {
	        for (int i = 2; i < n ; i++) {
	            if (i% n == 0){
	               return false;
	            }
	        }
	        return true;
		}
		
	}
		
	private int primoMaiorqN(int n) {
		
		
		return 0;
	}
	
	
	
	public boolean insertItem (Object k, Object dado){
		TItemDic item = new TItemDic(k,dado);
		int pos = he.hash(k) % tam_vet_conteudo;
		
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
		int pos = he.hash(k) % tam_vet_conteudo;
		
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
	
}
