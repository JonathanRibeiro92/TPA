package tadDicionario;

import java.util.LinkedList;

class TItemDic{
	private Object key = null;
	private Object dado = null;
	
	
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
	private int[] vet_conteudo;
	private int tam_vet_conteudo= 0;
	
	tam_vet_conteudo = vet_conteudo.size();

	
	
	
	
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
