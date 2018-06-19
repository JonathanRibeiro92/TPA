package Mat;
public interface IMat {

    int getColunas();
	int getLinhas();
	void setLinhas(int linhas);
	void setColunas(int colunas);
    
    //IMat carrega(String nome_arq);
    String salva(String nome_arq);
    TMatNZ multiplica(TMatNZ matrix);
    TMatNZ soma(TMatNZ matrix);
    TMatNZ transposta();
    TMatNZ vezesK(int value);

}