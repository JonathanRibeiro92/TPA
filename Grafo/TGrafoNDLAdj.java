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
    
     @Override
    public LinkedList<Edge> incidentEdges(Vertex vertice) {
        VertexLAd vertexLad = this.dicVertexes.findElement(vertice.getId());
        LinkedList<Edge> incoming = (LinkedList<Edge>)(LinkedList<?>)vertexLad.getEdgesIn();
        LinkedList<Edge> outgoing = (LinkedList<Edge>)(LinkedList<?>)vertexLad.getEdgesOut();

        incoming.addAll(outgoing);

        return incoming;
    }

    @Override
    public LinkedList<Vertex> adjacenteVertices(Vertex vertice) {
        VertexLAd vertexLad = this.dicVertexes.findElement(vertice.getId());
        LinkedList<Vertex> lst = new LinkedList<>();

        for ( EdgeLAd edgeLad: vertexLad.getEdges()) {
            lst.add(edgeLad.meuOpposite(vertexLad));
        }

        return lst;
    }

    public static TGrafoNDLAdj carrega(String nome_arq_TGF){
        TGrafoNDLAdj graph = new TGrafoNDLAdj();

        ArquivoTxt arq = ArquivoTxt.open(nome_arq_TGF, "rt");

        assert arq != null;
        carregaGenerico(graph,arq);

        arq.close();

        return graph;

    }

    public String toString(){

        TDic dicIDgrafoID_tgf = new TDicChain();
        /* Escrevendo os dicVertexes */
        String strGrafo = "";
        int id = 1;

        String linha;

        for(int i = 0; i<  dicVertexes.size(); i++){
            linha = id + " " + dicVertexes.findElement(i).getLabel();
            strGrafo+=(linha);
            strGrafo+=("\n");

            dicIDgrafoID_tgf.insertItem(i,id);

            id++;
        }
        strGrafo+=("#");
        strGrafo+=("\n");

        /* escrevendo as arestas */
        for (EdgeLAd edge: edges.elements()) {
            if(!edge.getLabel().substring(0,2).equals("@#")) {
                linha = (edge.getOrigin().getId()+1) + " " + (edge.getDestination().getId()+1) + " " + edge.getLabel();
            }else{
                linha = (edge.getOrigin().getId()+1) + " " + (edge.getDestination().getId()+1);
            }
            strGrafo+=(linha);
            strGrafo+=("\n");

        }
        return strGrafo;
    }
    
    
   
   
}
