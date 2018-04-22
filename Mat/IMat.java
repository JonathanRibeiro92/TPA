
public interface IMat {

    int getColunas();
	int getLinhas();
	void setLinhas(int linhas);
	void setColunas(int colunas);
    
    //IMat carrega(String nome_arq);
    String salva(String nome_arq);
    IMat multiplica(TMatNZ matrix);
    IMat soma(TMatNZ matrix);
    IMat transposta();
    IMat vezesK(int value);

}