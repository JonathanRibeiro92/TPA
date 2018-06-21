package Grafo;

public class Edge{

    private int id;
    private String label;
    private Object dado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getDado() {
        return dado;
    }

    public void setDado(Object dado) {
        this.dado = dado;
    }

    public Edge(Object d){
        this.setDado(d);
    }





}