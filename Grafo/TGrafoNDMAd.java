package Grafo;

import Hash.TabH.TDic;
import Hash.tadDicionario.TDicChain;
import java.util.LinkedList;

class Vertex{
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

    public Vertex(Object d){
        this.setDado(d);
    }



}
class Edge{

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






}


public class TGrafoNDMAd{
    private static final int TAM_DEFAULT = 64;


    // Dicionários com chave label e conteúdo objeto Vertex/Edge.
    private TDic dicVertexes = new TDicChain();
    private TDic dicEdges = new TDicChain();

    //Dicionário label x linha/coluna matriz: chave label do vértice,
    // conteúdo int com número da linha/coluna da matriz
    private TDic dicVertexLblInt = new TDicChain();

    private TDic dicVertexIntLbl = new TDicChain();


    private String matrix[][];
    /*
    * INTERFACE PRIVADA DO GRAFO
    */


    /*
    * INTERFACE PÚBLICA DO GRAFO
    */



    public TGrafoNDMAd(){
        //cria um grafo com espaço inicial igual a 64 nós.
        matrix = new String[64][64];

        for(int i = 0; i<TAM_DEFAULT; i++){
            for (int j= 0; j <TAM_DEFAULT; j++){
                matrix[i][j] = null;
            }
        }

    }

    public int numVertices(){
        return dicVertexes.size();
    }


    public int numEdges(){
        return dicEdges.size();
    }

    public LinkedList vertices(){
        return dicVertexes.keys();
    }

    public LinkedList edges(){
        return dicEdges.keys();
    }

    public Object getEdge(String u, String v){
        Integer linha = (Integer)dicVertexLblInt.findElement(u);
        Integer coluna = (Integer)dicVertexLblInt.findElement(v);

        return matrix[linha][coluna];
    }

    public String[] endVertices(String e){
        int tam = numVertices();
        for (int i = 0; i< tam; i++){
            for(int j = 0; j < tam; j++){
                if(matrix[i][j].equals(e)){
                    String labelV = (String)dicVertexIntLbl.findElement(i);
                    String labelU = (String)dicVertexIntLbl.findElement(j);
                    return new String[] {labelU,labelV};
                }
            }
        }

        return null;
    }

    public String opposite(String v, String e){
        if(dicVertexes.findElement(v) == null)
            return null;
        if(dicEdges.findElement(e) == null)
            return null;

        String endpoints[] = endVertices(e);

        if(endpoints != null) {
            if (v.equals(endpoints[0])) {
                return endpoints[1];
            } else
                if(v.equals(endpoints[1]))
                    return endpoints[0];
                else
                    return null;
        }else
            return null;
    }


    public Vertex insertVertex(Object x){
        Vertex v = new Vertex(x);

        dicVertexes.insertItem(k,v);

        return null;
    }

}