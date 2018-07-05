package Grafo;


import Hash.TabH.TDic;
import Hash.tadDicionario.Documento.ArquivoTxt;
import Hash.tadDicionario.TDicChain;

import java.util.LinkedList;

public class TGrafoDDMad extends TGrafoMAd implements TGrafoDD{

public TGrafoDDMad(int length) {
    super(length);
}

public TGrafoDDMad() {

    }


    @Override
    public int degree(Vertex vertice){
        return inDegree(vertice)+ outDegree(vertice);
    }

    public int outDegree(Vertex vertice) {
        int outDegree = 0;

        int row = findVertexPosByLabel(vertice.getLabel());
        for(int i = firstIndexMatrix; i<= lastIndexMatrix; i++)
            if(!lstVtxDelete.contains(i)){
                if(matrix[row][i] != null)
                    outDegree ++;
            }

        return outDegree;
    }

    public int inDegree(Vertex vertice) {

        int inDegree = 0;

        int col = findVertexPosByLabel(vertice.getLabel());
        for(int i = firstIndexMatrix; i<= lastIndexMatrix; i++)
            if(!lstVtxDelete.contains(i)){
                if(matrix[i][col] != null)
                    inDegree ++;
            }

        return inDegree;
    }

    @Override
    public LinkedList<Vertex> vertices() {
        return null;
    }

    @Override
    public LinkedList<Edge> edges() {
        return null;
    }

    @Override
    public Edge insertEdge(Vertex vertice1, Vertex vertice2, Object value) {
        
        if(dicVertexes.findElement(vertice1.getId()) == null || dicVertexes.findElement(vertice2.getId()) == null)
        {
            return null;
        }

        Integer id = globalEdgeID++;
        String label = id.toString();

        Edge edge = new Edge(id,label,value);

        dicEdges.insertItem(id,edge);

        int row = vertice1.getId();
        int column = vertice2.getId();

        matrix[row][column] = id;
        return  edge;
    }

    @Override
    public Edge insertEdge(Vertex vertice1, Vertex vertice2, Object value, String label) {
        
        if(dicVertexes.findElement(vertice1.getId()) == null || dicVertexes.findElement(vertice2.getId()) == null)
        {
            return null;
        }

        Integer id = globalEdgeID++;

        Edge edge = new Edge(id,label,value);

        dicEdges.insertItem(id,edge);

        int row = vertice1.getId();
        int column = vertice2.getId();

        matrix[row][column] = id;

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

        return tmp;
    }

    public LinkedList<Edge> inIncidentEdges(Vertex vertice) {
        LinkedList<Edge> lst = new LinkedList<>();
        int col = findVertexPosByLabel(vertice.getLabel());
        incidentEdges(col,lst,false);

        return lst;
    }

    public LinkedList<Edge> outIncidentEdges(Vertex vertice) {
        LinkedList<Edge> lst = new LinkedList<>();
        int row = findVertexPosByLabel(vertice.getLabel());
        incidentEdges(row,lst,true);

        return lst;
    }

    @Override
    public LinkedList<Edge> incidentEdges(Vertex vertice) {
        LinkedList<Edge> dicEdges = inIncidentEdges(vertice);
        dicEdges.addAll(outIncidentEdges(vertice));

        return dicEdges;
    }

    private void incidentEdges(int pos, LinkedList<Edge> lst, boolean row )
    {
        for(int i = firstIndexMatrix; i<= lastIndexMatrix; i++) {
            if (!lstVtxDelete.contains(i)) {
                Integer label;

                if(row) {
                    if (matrix[pos][i] != null) {
                        label = matrix[pos][i];
                        lst.add((Edge)dicEdges.findElement(label));
                    }
                }else
                {
                    if (matrix[i][pos] != null) {
                        label = matrix[i][pos];
                        lst.add((Edge)dicEdges.findElement(label));
                    }
                }
            }
        }

    }

    public LinkedList<Vertex> inAdjacentVertices(Vertex vertice) {
        return adjacentVertices(vertice,false);
    }

    public  LinkedList<Vertex> outAdjacentVertices(Vertex vertice) {
        return adjacentVertices(vertice,true);
    }

    @Override
    public LinkedList<Vertex> adjacentVertices(Vertex vertice) {
        LinkedList<Vertex> dicEdges = inAdjacentVertices(vertice);
        dicEdges.addAll(outAdjacentVertices(vertice));

        return dicEdges;
    }

    private LinkedList<Vertex> adjacentVertices(Vertex vertice, boolean row)
    {
        if(dicVertexes.findElement(vertice.getId()) == null)
            return null;

        LinkedList<Vertex> lst = new LinkedList<>();

        int pos = vertice.getId();

        for(int i = firstIndexMatrix; i<=lastIndexMatrix; i++) {
            if(row) {
                if (!lstVtxDelete.contains(i) && (matrix[pos][i] != null)) {
                    String label = findVertexLabelById(i);
                    lst.add((Vertex)dicVertexes.findElement(i));
                }
            }else {
                if (!lstVtxDelete.contains(i) && (matrix[i][pos] != null)) {
                    String label = findVertexLabelById(i);
                    lst.add((Vertex)dicVertexes.findElement(i));
                }
            }
        }

        return lst;
    }

    public Vertex destination(Edge edge) {
        return findOriginOrDestiny(edge,false);
    }

    public Vertex origin(Edge edge) {
        return findOriginOrDestiny(edge,true);
    }

    private Vertex findOriginOrDestiny(Edge edge, boolean origin)
    {
        for(int i=firstIndexMatrix; i<lastIndexMatrix ;i++) {
            for (int j = 0; j < lastIndexMatrix; j++) {
                if(origin) {
                    if (!lstVtxDelete.contains(i) && (matrix[i][j] != null) && (matrix[i][j].equals(edge.getLabel()))) {
                        String label = findVertexLabelById(i);
                        return (Vertex)dicVertexes.findElement(i);
                    }
                }else{
                    if (!lstVtxDelete.contains(j) && (matrix[i][j] != null) && (matrix[i][j].equals(edge.getLabel()))) {
                        String label = findVertexLabelById(j);
                        return (Vertex)dicVertexes.findElement(j);
                    }
                }
            }
        }

        return null;

    }

     /*
     *  Exemplo de uso:
     *  TGrafoNDMAd g = TGrafoNDMAd.carrega("nomeArqTGF.txt");
     * */
    public static TGrafoDDMad carrega(String nome_arq_TGF){
        TGrafoDDMad graph = new TGrafoDDMad();

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
                for (int col = 0; col <= lastIndexMatrix; col++){
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
