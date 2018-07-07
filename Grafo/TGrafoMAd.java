package Grafo;

import Hash.TabH.TDic;
import Hash.tadDicionario.Documento.ArquivoTxt;
import Hash.tadDicionario.TDicChain;

import java.util.Arrays;
import java.util.LinkedList;

public class TGrafoMAd extends Grafo {

	protected TDic dicVertexes = new TDicChain();
	protected TDic dicEdges = new TDicChain();

	protected LinkedList<Integer> lstVtxDelete = new LinkedList();

	protected int globalVertexID = 0;
	protected int globalEdgeID = 0;
	protected Integer matrix[][];	

	protected static final int defaultsize = 64;

	protected int firstIndexMatrix = 0;
	protected int lastIndexMatrix = -1;

	//Cria um grafo de tamanho Default com 64 nós.
	public TGrafoMAd()
	{
		matrix = new Integer[defaultsize][defaultsize];
	}

	//Cria um grafo de tamanho Custom com N nós.
	public TGrafoMAd(int length)
	{
		matrix = new Integer[length][length];
	}

	@Override
	public int numVertices() {
		return dicVertexes.size();
	}

	@Override
	public LinkedList<Vertex> vertices() {
		LinkedList<Vertex> lstVertex = new LinkedList<Vertex>();
		for (Object o:
			 dicVertexes.elements()) {
			lstVertex.add((Vertex)o);
		}

		return lstVertex;
	}

	@Override
	public int numEdges() {
		return dicEdges.size();
	}

	@Override
	public LinkedList<Edge> edges() {
		LinkedList<Edge> lstEdge = new LinkedList<Edge>();
		for (Object o:
				dicEdges.elements()) {
			lstEdge.add((Edge)o);
		}

		return lstEdge;
	}

	//Get edge - se a posição do vertex1 for encontrada eu busco o 2 senao retorno nulo o mesmo vale pro vertex 2;
	@Override
	public Edge getEdge(String vertex1, String vertex2) {
		int vertex1Pos = findVertexPosByLabel(vertex1);
		int vertex2Pos;
		if(vertex1Pos != -1)
		{
			vertex2Pos = findVertexPosByLabel(vertex2);
			if(vertex2Pos != -1){
				return (Edge) dicEdges.findElement(matrix[vertex1Pos][vertex2Pos]);
			}
		}

		return null;
	}

	@Override
	public String[] endVerticesString(String edge) {
		int tam = numVertices();

		int id = findEdgePosByLabel(edge);
		for (int i = 0; i< tam; i++){
			for(int j = 0; j < tam; j++){
				if(matrix[i][j] != null && matrix[i][j].equals(id)){
					Vertex vertex1 = (Vertex) dicVertexes.findElement(i);
					String labelVertex1 = vertex1.getLabel();
					String labelVertex2;
					if(!labelVertex1.equals(""))
					{
						Vertex vertex2 = (Vertex) dicVertexes.findElement(j);
						labelVertex2 = vertex2.getLabel();
						if(!labelVertex2.equals(""))
						{
							return new String[] {labelVertex2,labelVertex1};
						}
					}
				}
			}
		}

		return null;
	}

	public LinkedList<Vertex> endVertices(Edge edge) {
		int tam = numVertices();

		int id = edge.getId();
		for (int i = 0; i< tam; i++){
			for(int j = 0; j < tam; j++){
				if(matrix[i][j] != null && matrix[i][j].equals(id)){
					Vertex vertex1 = (Vertex) dicVertexes.findElement(i);
					Vertex vertex2;
					if(vertex1 != null)
					{
						vertex2 = (Vertex) dicVertexes.findElement(j);
						if(vertex2 != null)
						{
							return new LinkedList<>(Arrays.asList(vertex2, vertex1));
						}
					}
				}
			}
		}

		return null;
	}


	public LinkedList<Vertex> endVertices(String edge) {

		int tam = numVertices();
		int id = findEdgePosByLabel(edge);

		for (int i = 0; i< tam; i++){
			for(int j = 0; j < tam; j++){
				if(matrix[i][j] != null && matrix[i][j].equals(id)){
					Vertex vertex1 = (Vertex) dicVertexes.findElement(i);
					Vertex vertex2;
					if(vertex1 != null)
					{
						vertex2 = (Vertex) dicVertexes.findElement(j);
						if(vertex2 != null)
						{
							return new LinkedList<>(Arrays.asList(vertex2, vertex1));
						}
					}
				}
			}
		}

		return null;
	}



	@Override
	public String opossite(String vertex, String edge) {
		if(findVertexPosByLabel(vertex)==-1) return null;
		if(findEdgePosByLabel(edge)==-1)return null;

		String endPoints[] = endVerticesString(edge);

		if(endPoints != null) {
			if (vertex.equals(endPoints[0])) {
				return endPoints[1];
			} else
			if(vertex.equals(endPoints[1])) {
				return endPoints[0];
			}
		}

		return null;
	}

	public Vertex opossite(Vertex vertex, Edge edge) {
		if(vertex==null) return null;
		if(edge==null)return null;

		LinkedList<Vertex> endPoints = endVertices(edge);

		if(endPoints != null) {
			if (vertex.equals(endPoints.get(0))) {
				return endPoints.get(1);
			} else
			if(vertex.equals(endPoints.get(1))) {
				return endPoints.get(0);
			}
		}

		return null;
	}

	@Override
	public Vertex insertVertex(Object value) {
		if(dicVertexes.size()/matrix[0].length >= 0.75f)
			resize();

		Integer id = generateVertexId();
		String label = id.toString();

		if ((id < firstLinhaColUtil()) ||(firstLinhaColUtil() == -1)) {
			firstIndexMatrix = id;
		}
		if((id > lastLinhaColUtil())) {
			lastIndexMatrix = id;
		}

		Vertex vertice = new Vertex(id,label, value);
		dicVertexes.insertItem(id, vertice);
		return vertice;
	}

	@Override
	public Vertex insertVertex(Object value, String label)
	{
		if(dicVertexes.size()/matrix[0].length >= 0.75f)
			resize();

		Integer id = generateVertexId();

		if ((id < firstLinhaColUtil()) ||(firstLinhaColUtil() == -1)) {
			firstIndexMatrix = id;
		}
		if((id > lastLinhaColUtil())) {
			lastIndexMatrix = id;
		}

		Vertex vertice = new Vertex(id,label, value);

		dicVertexes.insertItem(id, vertice);
		return vertice;
	}

	@Override
	public Edge insertEdge(Vertex vertice1, Vertex vertice2, Object value) {
		return null;
	}


	@Override
	public Edge insertEdge(Vertex vertice1, Vertex vertice2, Object value, String label) {
		return null;
	}

	@Override
	public Object removeVertex(Vertex vertice) {
		return null;
	}

	@Override
	public Object removeEdge(Edge edge) {
		return null;
	}

	@Override
	public boolean areAdjacent(Vertex vertice1, Vertex vertice2)
	{
		int row = vertice1.getId();
		int column = vertice2.getId();

		return matrix[row][column] != null;
	}

	@Override
	public int degree(Vertex vertice) {
		return 0;
	}

	/*******Funções utilitárias********/

	protected int findVertexPosByLabel(String label)
	{
		for ( Vertex vertice: ((LinkedList<Vertex>) dicVertexes.elements())) {
			if(vertice.getLabel().equals(label))
			{
				return vertice.getId();
			}
		}
		return -1;
	}

	protected String findVertexLabelById(int id)
	{

		for ( Vertex vertice: ((LinkedList<Vertex>) dicVertexes.elements())) {
			if(vertice.getId()==id)
			{
				return vertice.getLabel();
			}
		}
		return "";
	}

	protected int findEdgePosByLabel(String label)
	{
		for (Edge edge: ((LinkedList<Edge>) dicEdges.elements())) {
			if(edge.getLabel().equals(label))
			{
				return edge.getId();
			}
		}
		return -1;
	}

	protected int generateVertexId(){
		if(lstVtxDelete.size() > 0){
			int id = lstVtxDelete.get(0);
			lstVtxDelete.remove(0);
			return id;
		}
		else
			return globalVertexID++;
	}

	protected int firstLinhaColUtil(){
		int i = firstIndexMatrix +1;
		while(lstVtxDelete.contains(i) && (i<= lastIndexMatrix))
			i=i+1;
		if(!lstVtxDelete.contains(i))
			return i;

		return lastIndexMatrix;
	}

	protected int lastLinhaColUtil(){
		int i = lastIndexMatrix - 1;
		while(lstVtxDelete.contains(i) && (i>= firstIndexMatrix))
			i = i-1;

		if(!lstVtxDelete.contains(i))
			return i;

		return firstIndexMatrix;
	}

	protected void resize(){
		int newSize = (int)(matrix[0].length * 1.5f);
		Integer newMat[][] = new Integer[newSize][newSize];

		for (int i = firstIndexMatrix; i <= lastIndexMatrix; i++) {
			for (int j = firstIndexMatrix; j <= lastIndexMatrix; j++) {
				newMat[i][j] = matrix[i][j];
			}
		}

		matrix = newMat;

	}

	protected static void carregaGenerico(TGrafoMAd grafo, ArquivoTxt arq){
		/* lendo os dicVertexes */
		String row = arq.readline();
		while (!row.trim().equals("#")){
			String[] vector = row.split(" ", 2);
			if(vector.length>1) {
				grafo.insertVertex(null, vector[1].trim());
			}

			row = arq.readline();
		}

		/* lendo as arestas */
		row = arq.readline();
		while (row!= null){
			String[] dicEdges = row.split(" ", 3);

			int idVertex1 = Integer.parseInt(dicEdges[0].trim()) - 1;
			int idVertex2 = Integer.parseInt(dicEdges[1].trim()) - 1;

			Vertex vertice1 = (Vertex) grafo.dicVertexes.findElement(idVertex1);
			Vertex vertice2 = (Vertex) grafo.dicVertexes.findElement(idVertex2);

			String label="";

			if(dicEdges.length == 3) {
				label = (dicEdges[2].trim());
			}
			if(label.equals("")) {
				label = ("@#" + (grafo.globalEdgeID+1));
			}

			grafo.insertEdge(vertice1, vertice2, null, label);

			row = arq.readline();
		}

	}

	public String salva(String nome_arq_TGF){

		ArquivoTxt arq = ArquivoTxt.open(nome_arq_TGF, "wt");

		arq.write(this.toString());

		arq.close();

		return nome_arq_TGF;
	}

}
