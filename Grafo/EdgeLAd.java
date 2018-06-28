package Grafo;


import Hash.TabH.TDic;
import Hash.tadDicionario.TDicChain;

public class EdgeLAd extends Edge{

    private VertexLAd origem = null;
    private VertexLAd destino = null;

    public VertexLAd getDestino() {
        return destino;
    }

    public VertexLAd getOrigem() {
        return origem;
    }

    public EdgeLAd(VertexLAd origem, VertexLAd destino, Object dado) {
        super(dado);
        this.origem = origem;
        this.destino = destino;
    }

    public boolean isEndPoint(VertexLAd v){
        return origem.equals(v)|| destino.equals(v);
    }


    public VertexLAd meuOpposite(VertexLAd v){
        if(v.equals(origem))
            return destino;
        else
            if(v.equals(destino))
                return origem;
            else
                return null;
    }

}
