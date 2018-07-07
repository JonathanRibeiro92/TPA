package Grafo;

import java.util.LinkedList;

/*import abstractGrafo.Edge;
import abstractGrafo.Vertice;
import algoGrafos.TPA2GS;
*/
/*
import Hash.TabHEA;
import tadgrafoLAdj.TADGrafoLadjND;
import tadgrafoMAdjND.TADGrafoMadjND;*/

import Hash.tadDicionario.TDicEA;

public class AppTestaGrafoND2 {
    public static void main(String[] args) {
        TGrafoNDMAd gndMat = new TGrafoNDMAd();
        TGrafoNDLAdj gndLad = new TGrafoNDLAdj();



        // ****************************************************
        // *  FAZ A CLONAGEM DO GRAFO gndLad NO GRAFO gndMat. *
        // *                                                  *
        // *  AO FINAL, gndMat É UM CLONE DE gndLad,          *
        // ****************************************************

        // O conteúdo do grafo original, gndLad, é lido do arquivo meses.txt.
        // O conteúdo do grafo clone, gndMat, é salvo em meses2.txt.
        String cwd = System.getProperty("user.dir");
        //String grafoIN = cwd + "/../../bdgrafos/meses.txt";
        //String grafoOUT = cwd + "/../../bdgrafos/meses2.txt";

        String grafoIN = cwd + "/bdgrafos/meses.txt";
        String grafoOUT = cwd + "/bdgrafos/meses2.txt";

        //TGrafoNDLAdj grafoTeste = new TGrafoNDLAdj();
        // Lê o grafo original do disco e o exibe via GraphStream.
        gndLad.carrega(grafoIN);

        //grafoTeste = TGrafoNDLAdj.carrega(grafoIN);

        //TPA2GS.exibeGrafo(gndLad);
        // Se for o caso, substitua a linha acima pela sua classe de exibição GraphStream.

        // Cria um dicionário de vértices com o label sendo a chave do dicionário.
        // SUbstitua esta linha e as linhas de utilização do dicionário pelas suas
        // classes e chamadas equivalentes.
        TDicEA dicVclonados = new TDicEA();
        LinkedList<Vertex> lst_vs_glad = gndLad.vertices();
        LinkedList<Edge> lst_es_glad = gndLad.edges();

        // Monitora a quantidade de vértices do grafo clone, antes e depois da clonagem.
        LinkedList<Vertex> lst_vs_gmat = gndMat.vertices();
        System.out.println("ANTES DA CLONAGEM:");
        System.out.println("gndLad possui " + lst_vs_glad.size() + " vértices.");
        System.out.println("gndMat possui " + lst_vs_gmat.size() + " vértices.");

        int i = 0;

        // Variáveis de trabalho do processo de clonagem.
        Vertex v, w, bkpV;
        Object dado;
        Edge e;

        // Vértices terminais da aresta corrente sendo processada.
        LinkedList<Vertex> lst_end_vertices_gnLad;

        // Enquanto não clonar todas as arestas do grafo original, faça:
        while(i < lst_es_glad.size()){
            // Leia a i-ésima aresta do grafo oriignal e obtenha os seus endVertices.
            lst_end_vertices_gnLad = gndLad.endVertices(lst_es_glad.get(i).getLabel());


            // Faça um bkp do primeiro end vertice da aresta.
            bkpV = lst_end_vertices_gnLad.get(0);

            // Se este vértice ainda não foi clonado então cloná-lo: criar um novo vértice,
            // copiar os campos dado e label, inserí-lo no grafo clone (gndMat).
            if(dicVclonados.findElement(bkpV.getId()).equals(TDicEA.NO_SUCH_KEY)){
                dado = bkpV.getDado();
                v = gndMat.insertVertex(dado);
                v.setLabel(bkpV.getLabel());
                dicVclonados.insertItem(v.getId(), v);
            }
            else
                // Se o vértice já foi clonado, resgatá-lo do dicionário de clonados para posterior
                // clonagem da aresta (porque a clonagem da aresta precisa de 2 end vértices, linha 96).
                v = (Vertex)dicVclonados.findElement(bkpV.getId());

            // Faça um bkp do segundo end vertice da aresta.
            bkpV = lst_end_vertices_gnLad.get(1);

            // Se este vértice ainda não foi clonado então cloná-lo: criar um novo vértice,
            // copiar os campos dado e label, inserí-lo no grafo clone (gndMat).
            if(dicVclonados.findElement(bkpV.getId()).equals(TDicEA.NO_SUCH_KEY)){
                dado = bkpV.getDado();
                w = gndMat.insertVertex(dado);
                w.setLabel(bkpV.getLabel());
                dicVclonados.insertItem(w.getId(), w);
            }
            else
                // Se o vértice já foi clonado, resgatá-lo do dicionário de clonados para posterior
                // clonagem da aresta (porque a clonagem da aresta precisa de 2 end vértices, linha 96).
                w = (Vertex)dicVclonados.findElement(bkpV.getId());

            // FInalmente, faz a clonagem da aresta do grafo origem.
            Edge bkpE = lst_es_glad.get(i);
            e = gndMat.insertEdge(v, w, bkpE.getDado(),bkpE.getLabel());
            //e.setLabel(bkpE.getLabel());

            // Próxima aresta do grafo origem a ser clonada.
            i++;
        } // while..

        // Exibe os status dos grafos após a clonagem.
        lst_vs_gmat = gndMat.vertices();
        System.out.println("\nAPÓS A CLONAGEM:");
        System.out.println("gndLad possui " + lst_vs_glad.size() + " vértices.");
        System.out.println("gndMat possui " + lst_vs_gmat.size() + " vértices.");

        // Exibe o grafo clonado, gndMat.

    } // fim de main

} // fim de AppTestaGrafoND2
