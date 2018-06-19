package Grafo;

import Hash.tadDicionario.Documento.ArquivoTxt;
import Hash.TabH.TDic;
import javafx.collections.transformation.TransformationList;
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

    public Vertex(int id, String label, Object object){
        this.id = id;
        this.label= label;
        this.dado = object;
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

    public Edge(Object d){
        this.setDado(d);
    }





}


public class TGrafoNDMAd{

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
        primIndexMatrix = -1;
        ultimIndexMatrix = -1;

    }


    public TGrafoNDMAd(int n){
        matrix = new String[n][n];
        for(int i = 0; i<n; i++){
            for (int j= 0; j <n; j++){
                matrix[i][j] = null;
            }
        }
        primIndexMatrix = -1;
        ultimIndexMatrix = -1;
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

    public Object getEdge(String u, String v) {
        Integer linha = (Integer) dicVertexLblId.findElement(u);

        if (linha != null) {
            Integer coluna = (Integer) dicVertexLblId.findElement(v);
            if (coluna != null) {
                return matrix[linha][coluna];
            }
        }
        return null;
    }

    public String[] endVertices(String e){
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

    public Vertex insertVertex(Object object, String label){
        if(dicVertexes.size()/matrix[0].length >= 0.75f){
            redimensiona();
        }

        Integer id = geraIDVtx();



        if ((id < firstLinhaColUtil()) ||(firstLinhaColUtil() == -1)) {
            primIndexMatrix = id;
        }
        if((id > lastLinhaColUtil()))
            ultimIndexMatrix = id;
        Vertex v = new Vertex(id,label,object);

        dicVertexes.insertItem(v.getLabel(),v);
        dicVertexLblId.insertItem(v.getLabel(),v.getId());
        dicVertexIdLbl.insertItem(v.getId(),v.getLabel());



        return v;

    }


    public Edge insertEdge(Vertex u, Vertex v, Object x){
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
        //porque é um grafo não dirigido (matriz é simétrica)
        matrix[linha][coluna] = e.getLabel();
        matrix[coluna][linha] = e.getLabel();

        return e;
    }


    private Object removeVertex(Vertex v){
        Object tmp = v.getDado();
        lstVtxDeletados.add(v.getId());


        int linha = v.getId();
        int limiteCol = dicVertexes.size();

//TODO loop para encontrar o primeiro e último
        /*
        if((v.getId() == primIndexMatrix))
            for(int i = 0; i < limiteCol; i++){
                if(matrix[linha][i]!= null){
                    primIndexMatrix = i;
                }
            }
            primIndexMatrix = 0;

        if(v.getId() == ultimIndexMatrix)
            for(int i = limiteCol; i > -1; i--){
                if(matrix[linha][i]!= null){
                    ultimIndexMatrix = i;
                }
            }
        */

        for(int i = 0; i < limiteCol; i++){
            if( !lstVtxDeletados.contains(i)) {
                String lblEdge = matrix[linha][i];

                dicEdges.removeElem(lblEdge);
                int idEdge = (int) dicEdgeLblId.removeElem(lblEdge);
                dicEdgeIdLbl.removeElem(idEdge);
                //porque é um grafo não dirigido (matriz é simétrica)
                matrix[linha][i] = null;
                matrix[i][linha] = null;
            }
        }


        return tmp;
    }


    private Object removeEdge(Edge e){
        Object tmp = e.getDado();


        String lblEdge = e.getLabel();

        dicEdges.removeElem(lblEdge);
        int idEdge = (int)dicEdgeLblId.removeElem(lblEdge);
        dicEdgeIdLbl.removeElem(idEdge);

        String [] endPoints = endVertices(e.getLabel());

        //asocia a aresta aos seus endpoints (vertices)
        int linha = (int)dicVertexLblId.findElement(endPoints[0]);
        int coluna = (int)dicVertexLblId.findElement(endPoints[1]);
        //porque é um grafo não dirigido (matriz é simétrica)
        matrix[linha][coluna] = null;
        matrix[coluna][linha] = null;



        return tmp;
    }

    public LinkedList <Vertex> adjacentVertices(Vertex v){

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

    public boolean areaAdjacent(Vertex u, Vertex v){

        int linha = u.getId();
        int coluna = v.getId();

        return matrix[linha][coluna] != null;
    }

    /*
    *  Exemplo de uso:
    *  TGrafoNDMAd g = TGrafoNDMAd.carrega("nomeArqTGF.txt");
    * */
    public static TGrafoNDMAd carrega(String nome_arq_TGF){
        TGrafoNDMAd g = new TGrafoNDMAd();

        ArquivoTxt arq = ArquivoTxt.open(nome_arq_TGF, "rt");

        /* lendo os vertices */
        String linha = arq.readline();
        while (!linha.trim().equals("#")){
            String[] vet = linha.split(" ", 2);
            Vertex v = g.insertVertex(null);



            g.dicVertexes.removeElem(v.getLabel());
            g.dicVertexLblId.removeElem(v.getLabel());
            g.dicVertexIdLbl.removeElem(v.getId());



            v.setLabel(vet[1]);
            g.dicVertexes.insertItem(v.getLabel(),v);
            g.dicVertexLblId.insertItem(v.getLabel(),v.getId());
            g.dicVertexIdLbl.insertItem(v.getId(),v.getLabel());

            linha = arq.readline();
        }

        /* lendo as arestas */
        linha = arq.readline();
        while (linha.trim()!= null){
            String[] edges = linha.split(" ", 3);

            String lblU = (String)g.dicVertexIdLbl.findElement(Integer.parseInt(edges[0].trim()) - 1);
            String lblV = (String)g.dicVertexIdLbl.findElement(Integer.parseInt(edges[1].trim()) - 1);

            Vertex u = (Vertex)g.dicVertexes.findElement(lblU);
            Vertex v = (Vertex)g.dicVertexes.findElement(lblV);

            Edge e = g.insertEdge(u,v,null);

            if(e==null)
                return null;
            else{
                g.dicEdges.removeElem(e.getLabel());
                g.dicEdgeLblId.removeElem(e.getLabel());
                g.dicEdgeIdLbl.removeElem(e.getId());

                if(edges.length==3)
                    e.setLabel(edges[2]);
                else{
                    e.setLabel("@#" + e.getId());
                }

                g.dicEdges.insertItem(e.getLabel(),e);
                g.dicEdgeLblId.insertItem(e.getLabel(),e.getId());
                g.dicEdgeIdLbl.insertItem(e.getId(),e.getLabel());


            }
            linha = arq.readline();

        }

        arq.close();

        return g;

    }

    public String salva(String nome_arq_TGF){

        ArquivoTxt arq = ArquivoTxt.open(nome_arq_TGF, "wt");
        TDic dicIDgrafoID_tgf = new TDicChain();
        /* Escrevendo os vertices */

        int id = 1;

        String linha = null;

        for(int i = primIndexMatrix; i<=ultimIndexMatrix; i++){
            if(!lstVtxDeletados.contains(i)){
                linha = id + " " + (String)dicVertexIdLbl.findElement(i);
                arq.writeline(linha);

                dicIDgrafoID_tgf.insertItem(i,id);

                id++;
            }
        }
        arq.writeline("#");


        /* escrevendo as arestas */
        for(int lin = primIndexMatrix; lin<=ultimIndexMatrix; lin++){
            if(!lstVtxDeletados.contains(lin)){
                for (int col = primIndexMatrix; col <= ultimIndexMatrix; col++){
                    if(!lstVtxDeletados.contains(col)){
                        if(matrix[lin][col] != null){
                            int tgf_lin = (int) dicIDgrafoID_tgf.findElement(lin);
                            int tgf_col = (int) dicIDgrafoID_tgf.findElement(col);
                            linha = tgf_lin + " " + tgf_lin + " " + matrix[lin][col];

                            arq.writeline(linha);
                        }
                    }
                }
            }
        }


        arq.close();

        return nome_arq_TGF;



    }

    public void toStr(){

        TDic dicIDgrafoID_tgf = new TDicChain();
        /* Escrevendo os vertices */
        String strGrafo = "";
        int id = 1;

        String linha = null;

        for(int i = this.primIndexMatrix; i<= this.ultimIndexMatrix; i++){
            if(!lstVtxDeletados.contains(i)){
                linha = id + " " + (String)dicVertexIdLbl.findElement(i);
                strGrafo.concat(linha);
                strGrafo.concat("\n");

                dicIDgrafoID_tgf.insertItem(i,id);

                id++;
            }
        }
        strGrafo.concat("#");
        strGrafo.concat("\n");


        /* escrevendo as arestas */
        for(int lin = primIndexMatrix; lin<=ultimIndexMatrix; lin++){
            if(!lstVtxDeletados.contains(lin)){
                for (int col = primIndexMatrix; col <= ultimIndexMatrix; col++){
                    if(!lstVtxDeletados.contains(col)){
                        if(matrix[lin][col] != null){
                            int tgf_lin = (int) dicIDgrafoID_tgf.findElement(lin);
                            int tgf_col = (int) dicIDgrafoID_tgf.findElement(col);

                            if(!matrix[lin][col].substring(0,2).equals("@#"))
                                linha = tgf_lin + " " + tgf_col + " " + matrix[lin][col];
                            else
                                linha = tgf_lin + " " + tgf_col;
                            strGrafo.concat(linha);
                            strGrafo.concat("\n");
                        }
                    }
                }
            }
        }


        System.out.println(strGrafo);


    }


}