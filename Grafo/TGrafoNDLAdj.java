package Grafo;


import java.util.LinkedList;

public class TGrafoNDLAdj {

    int idEdgeLAdj = 0;
    private LinkedList<Edge> edges;                                         // lista de arestas adj
    private LinkedList<Vertex> vertices;                                   // lista de vertices adj


    public TGrafoNDLAdj() {
        this.edges = new LinkedList<Edge>() {
        };
        this.vertices = new LinkedList<Vertex>() {
        };

    }

    public Vertex insertVertex(Object dado) {
        idEdgeLAdj++; //contador de arestas
        Vertex v = new Vertex(dado);             //cria o verticeLAdj
        v.setId(idEdgeLAdj);
        v.setLabel(String.valueOf(idEdgeLAdj));

        vertices.add(v);                                                        //adiciona o veertice criado na lista de vertices

        return v;
    }




}
