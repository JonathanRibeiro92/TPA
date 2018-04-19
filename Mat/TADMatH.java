import java.util.ArrayList;
 
import TabH.*;
 
public abstract class TADMatH {
 
    private int linhas;
    private int colunas;
    private TADTabH matriz;
     
     
    public TADMatH(int linhas, int colunas){
        this.linhas = linhas;
        this.colunas = colunas;
    }
     
     
    public int getLinhas() {
        return linhas;
    }
    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }
    public int getColunas() {
        return colunas;
    }
    public void setColunas(int colunas) {
        this.colunas = colunas;
    }
     
    public abstract float getElem(int i, int j);
     
    public abstract void setElem(int i, int j, float pelem);
     
    public abstract TADMatH multiplica(TADMatH mat);
     
    public abstract void carregaMMF(String nomeArq);
     
    public abstract void salvaMMF(String nomeArq);
     
    public abstract boolean equals(TADMatH pMat);
     
     
     
     
}
