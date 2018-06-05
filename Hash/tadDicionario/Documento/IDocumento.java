package tadDicionario.Documento;

import tadDicionario.TDicChain;
import tadDicionario.TDicEA;

public interface IDocumento{
    
    public TDicEA getTabFreq(String nome_arq_doc);
    public String salvaTabFreq(String nome_arq_doc);
}