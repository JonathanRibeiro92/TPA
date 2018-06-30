package Grafo;


import Hash.TabH.TDic;
import Hash.tadDicionario.TDicChain;

import java.util.LinkedList;

public class TGrafoNDLAdj extends TGrafoND{

    int idEdgeLAdj = 0;
    private LinkedList<Edge> edges;                                         // lista de arestas adj
    private LinkedList<Vertex> vertices;                                   // lista de vertices adj

    private TDic dicEdges = new TDicChain();
    private TDic dicVertexes = new TDicChain();

    /*
    public TGrafoNDLAdj() {
        this.edges = new LinkedList<Edge>() {
        };
        this.vertices = new LinkedList<Vertex>() {
        };

    }
    */

    public TGrafoNDLAdj() {

    }

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


    LinkedList<VertexLAd> adjacentVertices(VertexLAd v) {
        LinkedList<EdgeLAd> lst_edges = v.getEdges();
        LinkedList<VertexLAd> lst_vertexes = new LinkedList<VertexLAd>();

        for (int i = 0; i < lst_edges.size() ; i++) {
            EdgeLAd e = lst_edges.get(i);
            VertexLAd u = e.meuOpposite(v);
            if(v != null)
                lst_vertexes.add(v);
        }

        return lst_vertexes;
    }


    @Override
    String[] endVertices(String labelE) {

        EdgeLAd e = (EdgeLAd)dicEdges.findElement(labelE);

        if(e==null)
            return null;
        else{
            String[] vet = new String[2];
            vet[0] = e.getOrigem().getLabel();
            vet[1] = e.getDestino().getLabel();
            return vet;
        }
    }

    @Override
    String opposite(String v, String e) {
        if(dicVertexes.findElement(v) == null)
            return null;

        if(dicEdges.findElement(e)==null)
            return null;

        String endpoints[] = endVertices(e);

        if(endpoints != null){

        }



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


    public LinkedList<EdgeLAd> incidentEdges(VertexLAd v) {
        return v.getEdgesOUT();
    }

    @Override
    Object removeVertex(Vertex v) {
        return null;
    }

    @Override
    Object removeEdge(Edge e) {
        return null;
    }

    /*
    public Vertex insertVertex(Object dado) {

        idEdgeLAdj++; //contador de arestas
        Vertex v = new Vertex(dado);             //cria o verticeLAdj
        v.setId(idEdgeLAdj);
        v.setLabel(String.valueOf(idEdgeLAdj));

        vertices.add(v);                                                        //adiciona o veertice criado na lista de vertices

        return v;
    }
     */

    public VertexLAd insertVertex(Object dado) {
        VertexLAd v = new VertexLAd(dado);
        dicVertexes.insertItem(v.getLabel(), v);
        return v;
    }

    public Object removeVertex(VertexLAd v) {

        LinkedList<EdgeLAd> arestasIncidentes = v.getEdges();
        arestasIncidentes.forEach((a) -> {
            Object obj =  removeEdge(a);
        });
        dicVertexes.removeElem(v.getLabel());

        return null;
    }

    public EdgeLAd getEdge(VertexLAd u, VertexLAd v) {
        boolean achou = false;
        EdgeLAd aresta = null;
        int i = 0;
        LinkedList<Edge> listaArestas = edges();
        while(i < listaArestas.size() && !achou){
            EdgeLAd obj = (EdgeLAd) listaArestas.get(i);
            if((obj.getOrigem().getId() == u.getId()) && (obj.getDestino().getId() == v.getId()) ||
                    (obj.getOrigem().getId() == v.getId()) && (obj.getDestino().getId() == u.getId()) ){
                achou = true;
                aresta = obj;
                break;
            }
            i++;
        }

        return aresta;
    }

    public EdgeLAd insertEdge(VertexLAd u, VertexLAd v, Object pdado) {
        VertexLAd vertice1 = (VertexLAd) dicVertexes.findElement(u.getLabel());
        VertexLAd vertice2 = (VertexLAd) dicVertexes.findElement(v.getLabel());
        EdgeLAd aresta =  getEdge(vertice1, vertice2);
        if(aresta != null){
            aresta.setDado(pdado);
        }
        else{
            aresta = new EdgeLAd(vertice1, vertice2, pdado);
            vertice1.addEdgeIN(aresta);
            vertice1.addEdgeOUT(aresta);
            vertice2.addEdgeIN(aresta);
            vertice2.addEdgeOUT(aresta);
            dicEdges.insertItem(aresta.getLabel(), aresta);
        }
        return aresta;

    }

    public Object removeEdge(EdgeLAd edge){
        Object edgeRemoved = dicEdges.removeElem(edge.getLabel());

        return edgeRemoved;
    }



}
