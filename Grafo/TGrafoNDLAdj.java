package Grafo;


import Hash.TabH.TDic;
import Hash.tadDicionario.Documento.ArquivoTxt;
import Hash.tadDicionario.TDicChain;

import java.util.LinkedList;

public class TGrafoNDLAdj extends TGrafoLAdj implements TGrafoND{
    /*
    protected int globalVertexID = 0;
    protected int globalEdgeID = 0;
   /* private LinkedList<Edge> edges;                                         // lista de arestas adj
    private LinkedList<Vertex> vertices;                                   // lista de vertices adj
    */
    /*
    private TDic dicEdges = new TDicChain();
    private TDic dicVertexes = new TDicChain();
    */
    /*
    public TGrafoNDLAdj() {
        this.edges = new LinkedList<Edge>() {
        };
        this.vertices = new LinkedList<Vertex>() {
        };

    }
    */

    /*
    private TDic dicEdges;
    private TDic dicVertexes;

    public TGrafoNDLAdj() {
        dicEdges = new TDicChain();
        dicVertexes = new TDicChain();

    }
    */
    
     @Override
    public LinkedList<Edge> incidentEdges(Vertex vertice) {
        VertexLAd vertexLad = (VertexLAd) this.dicVertexes.findElement(vertice.getId());
        LinkedList<Edge> incoming = (LinkedList<Edge>)(LinkedList<?>)vertexLad.getEdgesIN();
        LinkedList<Edge> outgoing = (LinkedList<Edge>)(LinkedList<?>)vertexLad.getEdgesOUT();

        incoming.addAll(outgoing);

        return incoming;
    }

    @Override
    public LinkedList<Vertex> adjacentVertices(Vertex vertice) {
        VertexLAd vertexLad = (VertexLAd) this.dicVertexes.findElement(vertice.getId());
        LinkedList<Vertex> lst = new LinkedList<>();

        for ( EdgeLAd edgeLad: vertexLad.getEdges()) {
            lst.add(edgeLad.meuOpposite(vertexLad));
        }

        return lst;
    }

    public void carrega(String nome_arq_TGF){
        //TGrafoNDLAdj grafo = new TGrafoNDLAdj();

        ArquivoTxt arq = ArquivoTxt.open(nome_arq_TGF, "rt");

        assert arq != null;
        carregaGenerico(this,arq);

        arq.close();

        //return grafo;

    }

    //@Override
    public String toString(){

    /* Escrevendo os dicVertexes */
    String strGrafo = "";
    int id = 1;

    String linha;

    for(int i = 0; i<  dicVertexes.size(); i++){
        Vertex vertice = (Vertex)dicVertexes.findElement(i);
        linha = id + " " + vertice.getLabel();
        strGrafo+=(linha);
        strGrafo+=("\n");



        id++;
    }
    strGrafo+=("#");
    strGrafo+=("\n");

    /* escrevendo as arestas */
    for (EdgeLAd edge: (LinkedList<EdgeLAd>)dicEdges.elements()) {
        if(!edge.getLabel().substring(0,2).equals("@#")) {
            linha = (edge.getOrigem().getId()+1) + " " + (edge.getDestino().getId()+1) + " " + edge.getLabel();
        }else{
            linha = (edge.getOrigem().getId()+1) + " " + (edge.getDestino().getId()+1);
        }
        strGrafo+=(linha);
        strGrafo+=("\n");

    }
    return strGrafo;
}
    
    
   
   
}
