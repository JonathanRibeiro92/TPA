package Hash.tadDicionario.Documento;

import Hash.tadDicionario.TDicChain;
import Hash.tadDicionario.TDicEA;

public interface IDocumento{
    
    public TDicEA getTabFreq(String nome_arq_doc);
    public String salvaTabFreq(String nome_arq_doc);
}