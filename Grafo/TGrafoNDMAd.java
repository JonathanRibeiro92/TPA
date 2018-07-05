package Grafo;

import Hash.tadDicionario.Documento.ArquivoTxt;
import Hash.TabH.TDic;
import javafx.collections.transformation.TransformationList;
import Hash.tadDicionario.TDicChain;
import java.util.LinkedList;



public class TGrafoNDMAd extends TGrafoMAd implements TGrafoND{

    public TGrafoNDMAd(int length) {
        super(length);
    }

    public TGrafoNDMAd() {
    }

    @Override
    public int degree(Vertex v){
        int degree = 0;

        int row = v.getId();

        for(int i = firstIndexMatrix; i<= lastIndexMatrix; i++)
            if(!lstVtxDelete.contains(i) && (matrix[row][i]!= null))
                degree++;

        return degree;
    }

    @Override
    public Edge insertEdge(Vertex vertice1, Vertex vertice2, Object dado) {

        if(dicVertexes.findElement(vertice1.getId()) == null || dicVertexes.findElement(vertice2.getId()) == null)
        {
            return null;
        }

        Integer id = globalEdgeID++;
        String label = id.toString();

        Edge edge = new Edge(id,label,dado);

        dicEdges.insertItem(id,edge);

        int row = vertice1.getId();
        int column = vertice2.getId();

        matrix[row][column] = id;
        matrix[column][row] = id;

        return  edge;
    }

    @Override
    public Edge insertEdge(Vertex vertice1, Vertex vertice2, Object dado, String label) {

        if(dicVertexes.findElement(vertice1.getId()) == null || dicVertexes.findElement(vertice2.getId()) == null)
        {
            return null;
        }

        Integer id = globalEdgeID++;

        Edge edge = new Edge(id,label,dado);

        dicEdges.insertItem(id,edge);

        int row = vertice1.getId();
        int column = vertice2.getId();

        matrix[row][column] = id;
        matrix[column][row] = id;

        return  edge;
    }

    @Override
    public Object removeVertex(Vertex vertice) {
        Object tmp = vertice.getDado();

        int row = vertice.getId();
        int limitCol = dicVertexes.size()+lstVtxDelete.size();
        lstVtxDelete.add(row);

        for(int i = 0; i < limitCol; i++){
            if( !lstVtxDelete.contains(i)) {
                Integer edgeLabel = matrix[row][i];
                if(edgeLabel!=null) {
                    dicEdges.removeElement(edgeLabel);

                    matrix[row][i] = null;
                    matrix[i][row] = null;
                }
            }
        }


        dicVertexes.removeElement(vertice.getId());

        return tmp;
    }

    @Override
    public Object removeEdge(Edge edge) {
        Object tmp = edge.getDado();
        String edgeLabel = edge.getLabel();
        dicEdges.removeElement(edge.getId());

        String [] endPoints = endVertices(edge.getLabel());

        int row = findVertexPosByLabel(endPoints[0]);
        int column = findVertexPosByLabel(endPoints[1]);

        matrix[row][column] = null;
        matrix[column][row] = null;

        return tmp;
    }

    public LinkedList<Edge> incidentEdges(Vertex vertice){
        LinkedList<Edge> lst = new LinkedList<>();

        int row = findVertexPosByLabel(vertice.getLabel());
        for(int i = firstIndexMatrix; i<= lastIndexMatrix; i++) {
            if (!lstVtxDelete.contains(i)) {
                if (matrix[row][i] != null) {
                    lst.add((Edge) dicEdges.findElement(matrix[row][i]));
                }
            }
        }

        return lst;
    }

    public LinkedList<Vertex> adjacentVertices(Vertex vertice){


        if(dicVertexes.findElement(vertice.getId()) == null)
            return null;

        LinkedList<Vertex> lst = new LinkedList();
        int row = vertice.getId();

        for (int i = firstIndexMatrix; i <= lastIndexMatrix; i++) {
            if(!lstVtxDelete.contains(i) && matrix[row][i] != null){
                String label = findVertexLabelById(i);
                lst.add((Vertex) dicVertexes.findElement(i));
            }
        }

        return lst;
    }

    /*
     *  Exemplo de uso:
     *  TGrafoNDMAd g = TGrafoNDMAd.carrega("nomeArqTGF.txt");
     * */
    public static TGrafoNDMAd carrega(String nome_arq_TGF){
        TGrafoNDMAd graph = new TGrafoNDMAd();

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

        String row;

        for(int i = this.firstIndexMatrix; i<= this.lastIndexMatrix; i++){
            if(!lstVtxDelete.contains(i)){
                row = id + " " + findVertexLabelById(i);
                strGrafo+=(row);
                strGrafo+=("\n");

                dicIDgrafoID_tgf.insertItem(i,id);

                id++;
            }
        }
        strGrafo+=("#");
        strGrafo+=("\n");

        /* escrevendo as arestas */
        for(int lin =firstIndexMatrix; lin<=lastIndexMatrix; lin++){
            if(!lstVtxDelete.contains(lin)){
                for (int col = lin; col <= lastIndexMatrix; col++){
                    if(!lstVtxDelete.contains(col)){
                        if(matrix[lin][col] != null){
                            int tgf_lin = (int) dicIDgrafoID_tgf.findElement(lin);
                            int tgf_col = (int) dicIDgrafoID_tgf.findElement(col);

                            Edge edge = (Edge) dicEdges.findElement(matrix[lin][col]);
                            if(!edge.getLabel().substring(0,2).equals("@#")) {
                                row = tgf_lin + " " + tgf_col + " " + edge.getLabel();
                            }else{
                                row = tgf_lin + " " + tgf_col;
                            }

                            strGrafo+=(row);
                            strGrafo+=("\n");
                        }
                    }
                }
            }
        }


        return strGrafo;
    }
    
}
