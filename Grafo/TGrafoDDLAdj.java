package Grafo;

import Hash.TabH.TDic;
import Hash.tadDicionario.Documento.ArquivoTxt;
import Hash.tadDicionario.TDicChain;
import java.util.LinkedList;

public class TGrafoDDLAdj  extends TGrafoND {

    private LinkedList<Edge> edges;                                         // lista de arestas adj
    private LinkedList<Vertex> vertices;                                   // lista de vertices adj

    private TDic dicEdges = new TDicChain();
    private TDic dicVertexes = new TDicChain();



    @Override
    int numVertices() {
        return dicVertexes.size();
    }

    @Override
    int numEdges() {
        return dicEdges.size();
    }

    @Override
    LinkedList<Edge> edges() {
        return dicEdges.keys();
    }

    @Override
    boolean areAdjacent(Vertex u, Vertex v) {
        return false;
    }

    boolean areAdjacent(VertexLAd u, VertexLAd v) {
        LinkedList<EdgeLAd> lst_edges = new LinkedList<EdgeLAd>();


        for (int i = 0; i < lst_edges.size() ; i++) {
            EdgeLAd e = lst_edges.get(i);

            if(e.isEndPoint(v))
                return true;
        }

        return false;
    }



    @Override
    LinkedList<Vertex> adjacentVertices(Vertex v) {
        return null;
    }

    @Override
    String[] endVertices(String e) {
        return new String[0];
    }

    @Override
    String opposite(String v, String e) {
        return null;
    }

    @Override
    Vertex insertVertex(Object x) {
        return null;
    }

    @Override
    Edge insertEdge(Vertex u, Vertex v, Object x) {
        return null;
    }

    @Override
    LinkedList<Edge> incidentEdges(Vertex v) {
        return null;
    }

    @Override
    Object removeVertex(Vertex v) {
        return null;
    }

    @Override
    Object removeEdge(Edge e) {
        return null;
    }
}
