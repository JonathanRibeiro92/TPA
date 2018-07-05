package Grafo;


import Hash.TabH.TDic;
import Hash.tadDicionario.Documento.ArquivoTxt;
import Hash.tadDicionario.TDicChain;

import java.util.LinkedList;

public class TGrafoNDLAdj extends TGrafoND{

    protected int globalVertexID = 0;
    protected int globalEdgeID = 0;
   /* private LinkedList<Edge> edges;                                         // lista de arestas adj
    private LinkedList<Vertex> vertices;                                   // lista de vertices adj
    */
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

    protected int generateVertexId(){
        return globalVertexID++;
    }

    private VertexLAd findVertices(String vertex)
    {
        LinkedList<VertexLAd> lstVertexLAD = new LinkedList<>();
        for (Object obj: dicVertexes.elements()){
            lstVertexLAD.add((VertexLAd)obj);
        }

        for (VertexLAd vertice : lstVertexLAD) {
            if(vertice.getLabel().equals(vertex))
            {
                return vertice;
            }
        }

        return null;
    }

    public TGrafoNDLAdj() {

    }

    //todo
    public void setLabel( Vertex v, String label){
        Vertex obj = (Vertex)dicVertexes.findElement(v);
        obj.setLabel(label);


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

    LinkedList<Vertex> vertices() {
        return dicVertexes.keys();
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
    LinkedList<Edge> incidentEdges(Vertex v) {
        return null;
    }


    public LinkedList<EdgeLAd> incidentEdges(VertexLAd v) {
        return v.getEdgesOUT();
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
        Integer id = generateVertexId();

        VertexLAd v = new VertexLAd(dado);
        v.setId(id);
        dicVertexes.insertItem(v.getId(), v);
        return v;
    }
    @Override
    public Object removeVertex(Vertex v) {
        VertexLAd vertexLAd = ((VertexLAd) v);

        LinkedList<EdgeLAd> arestasIncidentes = vertexLAd.getEdges();
        arestasIncidentes.forEach((a) -> {
            Object obj =  removeEdge(a);
        });
        return dicVertexes.removeElem(v.getId());

    }


    public Object getEdge(String vertexU, String vertexv) {
        VertexLAd vertex1 = findVertices(vertexU);
        VertexLAd vertex2 = findVertices(vertexv);

        if(vertex1 != null && vertex2 != null)  {

            for (EdgeLAd edgeLad : vertex1.getEdges()) {
                if (edgeLad.isEndPoint(vertex2)) {
                    return edgeLad;
                }
            }
        }

        return null;
    }

    @Override
    public Edge insertEdge(Vertex u, Vertex v, Object pdado) {


        VertexLAd vertice1 = (VertexLAd) dicVertexes.findElement(u.getId());
        VertexLAd vertice2 = (VertexLAd) dicVertexes.findElement(v.getId());



        EdgeLAd aresta = (EdgeLAd)getEdge(vertice1.getLabel(), vertice2.getLabel());
        if(aresta != null){
            aresta.setDado(pdado);
        }
        else{
            Integer id = globalEdgeID++;
            String label = id.toString();
            aresta = new EdgeLAd(vertice1, vertice2, pdado);
            aresta.setLabel(label);
            vertice1.addEdgeIN(aresta);
            vertice1.addEdgeOUT(aresta);
            vertice2.addEdgeIN(aresta);
            vertice2.addEdgeOUT(aresta);
            dicEdges.insertItem(aresta.getId(), aresta);
        }
        return aresta;

    }

    public Object removeEdge(EdgeLAd edge){
        Object edgeRemoved = dicEdges.removeElem(edge.getId());

        return edgeRemoved;
    }


    public int degree(Vertex v) {
        return ((VertexLAd)v).meuDegree();
    }

    /*
    *  Exemplo de uso:
    *  TGrafoNDMAd g = TGrafoNDMAd.carrega("nomeArqTGF.txt");
    * */
    public static TGrafoNDLAdj carrega(String nome_arq_TGF){
        TGrafoNDLAdj g = new TGrafoNDLAdj();

        ArquivoTxt arq = ArquivoTxt.open(nome_arq_TGF, "rt");

        /* lendo os vertices */
        String linha = arq.readline();
        while (!linha.trim().equals("#")){
            String[] vet = linha.split(" ", 2);
            Vertex v = g.insertVertex(null);



            g.dicVertexes.removeElem(v.getLabel());
            /*g.dicVertexLblId.removeElem(v.getLabel());
            g.dicVertexIdLbl.removeElem(v.getId());*/



            v.setLabel(vet[1]);
            g.dicVertexes.insertItem(v.getLabel(),v);
            /*g.dicVertexLblId.insertItem(v.getLabel(),v.getId());
            g.dicVertexIdLbl.insertItem(v.getId(),v.getLabel());
            */
            linha = arq.readline();
        }

        /* lendo as arestas */
        linha = arq.readline();
        while (linha.trim()!= null){
            String[] edges = linha.split(" ", 3);

            String lblU = (String)g.dicVertexes.findElement(Integer.parseInt(edges[0].trim()) - 1);
            String lblV = (String)g.dicVertexes.findElement(Integer.parseInt(edges[1].trim()) - 1);

            Vertex u = (Vertex)g.dicVertexes.findElement(lblU);
            Vertex v = (Vertex)g.dicVertexes.findElement(lblV);

            Edge e = g.insertEdge(u,v,null);

            if(e==null)
                return null;
            else{
                g.dicEdges.removeElem(e.getLabel());
                /*g.dicEdgeLblId.removeElem(e.getLabel());
                g.dicEdgeIdLbl.removeElem(e.getId());
                */
                if(edges.length==3)
                    e.setLabel(edges[2]);
                else{
                    e.setLabel("@#" + e.getId());
                }

                g.dicEdges.insertItem(e.getLabel(),e);
                /*g.dicEdgeLblId.insertItem(e.getLabel(),e.getId());
                g.dicEdgeIdLbl.insertItem(e.getId(),e.getLabel());
                */

            }
            linha = arq.readline();

        }

        arq.close();

        return g;

    }

}
