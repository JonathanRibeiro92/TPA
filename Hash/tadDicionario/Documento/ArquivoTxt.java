package tadDicionario.Documento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArquivoTxt {
    private BufferedWriter bw = null;
    private BufferedReader br = null;
    private FileWriter fw = null;
    private FileReader fr = null;

    public static ArquivoTxt open(String nome_arq, String modo){
        if(modo.toLowerCase().equals("wt") || modo.toLowerCase().equals("tw") || modo.toLowerCase().equals("w")){
            try {
                ArquivoTxt arqtxt = new ArquivoTxt();
                arqtxt.fw = new FileWriter(nome_arq);
                arqtxt.bw = new BufferedWriter(arqtxt.fw);
                return arqtxt;
            } catch (IOException e) {
                e.printStackTrace();
            } // catch
        }
        else
        if(modo.toLowerCase().equals("rt") || modo.toLowerCase().equals("tr") || modo.toLowerCase().equals("r")){
            try {
                ArquivoTxt arqtxt = new ArquivoTxt();
                arqtxt.fr = new FileReader(nome_arq);
                arqtxt.br = new BufferedReader(arqtxt.fr);
                return arqtxt;
            } catch (IOException e) {
                e.printStackTrace();
            } // catch
        }

        return null;
    }

    public String readline(){
        try {
            String linha = br.readLine();
            return linha;
        } catch (IOException e) {
            e.printStackTrace();
        } // catch
        return null;
    } // readline

    public void writeline(String content){
        try {
            bw.write(content + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        } // catch
    } // writeline

    public void write(String content){
        try {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } // catch
    } // write

    public void close() {
        try {
            if (bw != null) {
                bw.close();
            }
            else {
                br.close();
            }

            if (fw != null) {
                fw.close();
            }
            else {
                fr.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } // catch
    } // close
} // class ArquivoTxt
