package Grafo;


import Hash.TabH.TDic;
import Hash.tadDicionario.TDicChain;

import java.util.LinkedList;

public class TGrafoDDMad extends TGrafoDD{

    /*
    * INTERFACE PRIVADA DO GRAFO
    */
    private static final int TAM_DEFAULT = 64;


    // Dicionários com chave label e conteúdo objeto Vertex/Edge.
    private TDic dicVertexes = new TDicChain();
    private TDic dicEdges = new TDicChain();

    //Dicionário label x linha/coluna matriz: chave label do vértice,
    // conteúdo int com número da linha/coluna da matriz
    private TDic dicVertexLblId = new TDicChain();
    private TDic dicVertexIdLbl = new TDicChain();

    private TDic dicEdgeLblId = new TDicChain();
    private TDic dicEdgeIdLbl = new TDicChain();

    private TDic dicVertexDels = new TDicChain();
    private TDic dicEdgeDels = new TDicChain();

    private LinkedList<Integer> lstVtxDeletados = new LinkedList();

    private int globalID = 0 ;
    private int globalVertexID = 0;
    private int globalEdgeID = 0;
    private String matrix[][];

    private int primIndexMatrix = 0;
    private int ultimIndexMatrix = -1;

    private int geraIDVtx(){
        if(lstVtxDeletados.size() > 0){
            int id = lstVtxDeletados.get(0);
            lstVtxDeletados.remove(0);
            return id;
        }
        else
            return globalVertexID++;
    }

    private int firstLinhaColUtil(){
        int i = primIndexMatrix+1;
        while(lstVtxDeletados.contains(i) && (i<= ultimIndexMatrix))
            i=i+1;
        if(!lstVtxDeletados.contains(i))
            return i;

        return ultimIndexMatrix;
    }


    private int lastLinhaColUtil(){
        int i = ultimIndexMatrix - 1;
        while(lstVtxDeletados.contains(i) && (i>= primIndexMatrix))
            i = i-1;

        if(!lstVtxDeletados.contains(i))
            return i;

        return primIndexMatrix;
    }




    private void redimensiona(){
        int novoTam = (int)(matrix[0].length * 1.5f);
        String novaMat[][] = new String[novoTam][novoTam];


        for (int i = primIndexMatrix; i <= ultimIndexMatrix ; i++) {
            for (int j = primIndexMatrix; j <=ultimIndexMatrix ; j++) {
                novaMat[i][j] = matrix[i][j];
            }
        }

        matrix = novaMat;

    }

    public TGrafoDDMad(){
        //cria um grafo com espaço inicial igual a 64 nós.
        matrix = new String[64][64];

        for(int i = 0; i<TAM_DEFAULT; i++){
            for (int j= 0; j <TAM_DEFAULT; j++){
                matrix[i][j] = null;
            }
        }
        primIndexMatrix = -1;
        ultimIndexMatrix = -1;

    }


    public TGrafoDDMad(int n){
        matrix = new String[n][n];
        for(int i = 0; i<n; i++){
            for (int j= 0; j <n; j++){
                matrix[i][j] = null;
            }
        }
        primIndexMatrix = -1;
        ultimIndexMatrix = -1;
    }


    public int degree(Vertex v){
        return inDegree(v)+ outDegree(v);
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
        int linha = u.getId();
        int coluna = v.getId();

        return matrix[linha][coluna] != null;
    }

    @Override
    LinkedList<Vertex> adjacentVertices(Vertex v) {
        if(dicVertexes.findElement(v) == null)
            return null;

        LinkedList<Vertex> lst = new LinkedList<Vertex>();
        int linha = v.getId();

        for (int i = primIndexMatrix; i <= ultimIndexMatrix ; i++) {
            if(!lstVtxDeletados.contains(i) && matrix[linha][i] != null){
                String labelU = (String)dicVertexIdLbl.findElement(i);
                lst.add((Vertex) dicVertexes.findElement(labelU));
            }
        }



        return lst;
    }

    @Override
    String[] endVertices(String e) {
        int tam = numVertices();
        for (int i = 0; i< tam; i++) {
            for (int j = 0; j < tam; j++) {
                if (matrix[i][j].equals(e)) {
                    String labelV = (String) dicVertexIdLbl.findElement(i);
                    if (!labelV.equals("")) {
                        String labelU = (String) dicVertexIdLbl.findElement(j);
                        if (!labelU.equals("")) {
                            return new String[]{labelU, labelV};
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    String opposite(String v, String e) {
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

    @Override
    Vertex insertVertex(Object x) {
        if(dicVertexes.size()/matrix[0].length >= 0.75f)
            redimensiona();

        Vertex v = new Vertex(x);

        v.setId(geraIDVtx());
        v.setLabel(String.valueOf(globalID));


        if ((v.getId() < firstLinhaColUtil()) ||(firstLinhaColUtil() == -1)) {
            primIndexMatrix = v.getId();
        }
        if((v.getId() > lastLinhaColUtil()))
            ultimIndexMatrix = v.getId();


        dicVertexes.insertItem(v.getLabel(),v);
        dicVertexLblId.insertItem(v.getLabel(),v.getId());
        dicVertexIdLbl.insertItem(v.getId(),v.getLabel());



        return v;
    }

    @Override
    Edge insertEdge(Vertex u, Vertex v, Object x) {
        if((dicVertexLblId.findElement(u.getLabel()).equals(null))
                || (dicVertexLblId.findElement(v.getLabel()).equals(null)))
            return null;

        //cria o objeto edge (aresta)
        Edge e = new Edge(x);

        e.setId(globalEdgeID++);
        e.setLabel(String.valueOf(globalEdgeID));


        dicEdges.insertItem(e.getLabel(),e);
        dicEdgeLblId.insertItem(e.getLabel(),e.getId());
        dicEdgeIdLbl.insertItem(e.getId(),e.getLabel());

        //associa a aresta aos seus endpoints (vertices).
        int linha = u.getId();
        int coluna = v.getId();
        //porque é um grafo  dirigido (matriz não é simétrica)
        matrix[linha][coluna] = e.getLabel();

        return e;
    }

    @Override
    LinkedList<Edge> incidentEdges(Vertex v) {
        return null;
    }

    @Override
    Object removeVertex(Vertex v) {
        Object tmp = v.getDado();
        int linha = v.getId();

        for (int i = linha; i <= ultimIndexMatrix; i++) {
            if(!lstVtxDeletados.contains(i)){
                String lblEdge = matrix[linha][i];

                dicEdges.removeElem(lblEdge);
                int idEdge = (int)dicEdgeLblId.removeElem(lblEdge);
                dicEdgeIdLbl.removeElem(idEdge);

                matrix[linha][i] = null;
            }
        }

        if(v.getId() == primIndexMatrix)
            primIndexMatrix = firstLinhaColUtil();

        if(v.getId() ==ultimIndexMatrix)
            ultimIndexMatrix = lastLinhaColUtil();

        lstVtxDeletados.add(v.getId());

        return tmp;

    }

    @Override
    Object removeEdge(Edge e) {
        Object tmp = e.getDado();


        String lblEdge = e.getLabel();

        dicEdges.removeElem(lblEdge);
        int idEdge = (int)dicEdgeLblId.removeElem(lblEdge);
        dicEdgeIdLbl.removeElem(idEdge);

        String [] endPoints = endVertices(e.getLabel());

        //asocia a aresta aos seus endpoints (vertices)
        int linha = (int)dicVertexLblId.findElement(endPoints[0]);
        int coluna = (int)dicVertexLblId.findElement(endPoints[1]);
        //porque é um grafo não dirigido (matriz não é simétrica)
        matrix[linha][coluna] = null;




        return tmp;
    }

    @Override
    int inDegree(Vertex v) {
        int inGrau = 0;

        int coluna = (int)dicVertexLblId.findElement(v.getLabel());
        for(int i = primIndexMatrix; i<= ultimIndexMatrix; i++)
            if(!lstVtxDeletados.contains(i)){
                if(matrix[i][coluna] != null)
                    inGrau ++;
            }

        return inGrau;
    }

    @Override
    int outDegree(Vertex v) {
        int outGrau = 0;

        int linha = (int)dicVertexLblId.findElement(v.getLabel());
        for(int i = primIndexMatrix; i<= ultimIndexMatrix; i++)
            if(!lstVtxDeletados.contains(i)){
                if(matrix[linha][i] != null)
                    outGrau ++;
            }

        return outGrau;
    }

    @Override
    LinkedList<Edge> inIncidentEdges(Vertex v) {
        LinkedList<Edge> lst = new LinkedList<Edge>();
        int coluna = (int)dicVertexLblId.findElement(v.getLabel());
        for(int i = primIndexMatrix; i<= ultimIndexMatrix; i++)
            if(!lstVtxDeletados.contains(i)){
                if(matrix[i][coluna] != null)
                    lst.add((Edge)dicEdges.findElement(matrix[i][coluna]));
            }

        return lst;
    }

    @Override
    LinkedList<Edge> outIncidentEdges(Vertex v) {
        LinkedList<Edge> lst = new LinkedList<Edge>();
        int linha = (int)dicVertexLblId.findElement(v.getLabel());
        for(int i = primIndexMatrix; i<= ultimIndexMatrix; i++)
            if(!lstVtxDeletados.contains(i)){
                if(matrix[linha][i] != null)
                    lst.add((Edge)dicEdges.findElement(matrix[linha][i]));
            }

        return lst;
    }

    @Override
    LinkedList<Vertex> inAdjacentVertices(Vertex v) {
        if(dicVertexes.findElement(v.getLabel()) == null)
            return null;

        LinkedList<Vertex> lst = new LinkedList<Vertex>();
        int coluna = v.getId();

        for(int i = primIndexMatrix; i<=ultimIndexMatrix; i++)
            if(!lstVtxDeletados.contains(i) && (matrix[i][coluna] != null)){
                String labelU = (String)dicVertexIdLbl.findElement(i);
                lst.add((Vertex)dicVertexes.findElement(labelU));
            }


        return lst;
    }

    @Override
    LinkedList<Vertex> outAdjacentVertices(Vertex v) {
        if(dicVertexes.findElement(v.getLabel()) == null)
            return null;

        LinkedList<Vertex> lst = new LinkedList<Vertex>();
        int linha = v.getId();

        for(int i = primIndexMatrix; i<=ultimIndexMatrix; i++)
            if(!lstVtxDeletados.contains(i) && (matrix[linha][i] != null)){
                String labelU = (String)dicVertexIdLbl.findElement(i);
                lst.add((Vertex)dicVertexes.findElement(labelU));
            }


        return lst;
    }

    @Override
    Vertex destination(Edge e) {

        for(int i=primIndexMatrix; i<ultimIndexMatrix ;i++)
            for(int j = primIndexMatrix; j<ultimIndexMatrix; j++)
                if(!lstVtxDeletados.contains(i) &&(matrix[i][j].equals(e))){
                    String labelU = (String)dicVertexIdLbl.findElement(j);
                    return (Vertex)dicVertexes.findElement(labelU);
                }
        return null;
    }

    @Override
    Vertex origin(Edge e) {
        for(int i=primIndexMatrix; i<ultimIndexMatrix ;i++)
            for(int j = primIndexMatrix; j<ultimIndexMatrix; j++)
                if(!lstVtxDeletados.contains(i) &&(matrix[i][j].equals(e))){
                    String labelU = (String)dicVertexIdLbl.findElement(i);
                    return (Vertex)dicVertexes.findElement(labelU);
                }
        return null;
    }
}