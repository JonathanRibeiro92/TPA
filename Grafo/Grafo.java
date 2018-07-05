package Grafo;

import java.util.LinkedList;

public abstract class Grafo {

    public abstract int numVertices();
    public abstract LinkedList<Vertex> vertices();
    public abstract int numEdges();
    public abstract LinkedList<Edge> edges();
    public abstract Edge getEdge(String vertex1, String vertex2);
    public abstract String[] endVertices(String edge);
    public abstract String opossite(String vertex, String edge);
    public abstract Vertex insertVertex(Object value);
    public abstract Vertex insertVertex(Object value, String label);
    public abstract Edge insertEdge(Vertex vertex1, Vertex vertex2, Object value);
    public abstract Edge insertEdge(Vertex vertex1, Vertex vertex2, Object value, String label);
    public abstract Object removeVertex(Vertex vertex);
    public abstract Object removeEdge(Edge edge);
    public abstract boolean areAdjacent(Vertex vertex1, Vertex vertex2);
    public abstract int degree(Vertex vertex);

}
