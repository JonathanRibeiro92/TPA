package Grafo;

import java.util.LinkedList;

public abstract class TGrafoDD{

    //Métodos comuns dirigido/não dirigido
    abstract int numVertices();
    abstract int numEdges();
    abstract LinkedList<Edge> edges();
    abstract boolean areAdjacent(Vertex u, Vertex v);
    abstract LinkedList<Vertex> adjacentVertices(Vertex v);
    abstract String[] endVertices(String e);
    abstract String opposite (String v, String e);
    abstract Vertex insertVertex(Object x);
    abstract Edge insertEdge(Vertex u, Vertex v, Object x);
    abstract LinkedList<Edge> incidentEdges(Vertex v);
    abstract Object removeVertex(Vertex v);
    abstract Object removeEdge(Edge e);



    //Métodos dirigido

    abstract int inDegree(Vertex v);
    abstract int outDegree(Vertex v);

    abstract LinkedList<Edge> inIncidentEdges(Vertex v);
    abstract LinkedList<Edge> outIncidentEdges(Vertex v);

    abstract LinkedList<Vertex> inAdjacentVertices(Vertex v);
    abstract LinkedList<Vertex> outAdjacentVertices(Vertex v);

    abstract Vertex destination(Edge e);
    abstract Vertex origin(Edge e);

}