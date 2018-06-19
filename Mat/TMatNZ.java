package Mat;

import java.io.InputStream;
import java.io.FileInputStream;


import Hash.tadDicionario.TDicEA;

public class TMatNZ implements IMat{

	private int linhas, colunas;

	private TDicEA matrixDicEA;

	public TMatNZ(){
		matrixDicEA = new TDicEA(linhas*colunas);
	}


	public TMatNZ(int linhas, int colunas){
        this.linhas = linhas;
		this.colunas = colunas;
		matrixDicEA = new TDicEA(linhas*colunas);
    }


	public float getValorPosIJ(int i,int j){
		String chave = i + "-" + j;
		return (float)matrixDicEA.findElement(chave);
		
	}

	public void addItemMatriz(int i,int j,float _valor){
		
		ElemMat elemMat = new ElemMat(i,j,_valor);
		String chave = i + "-" + j;
		matrixDicEA.insertItem(chave, elemMat);
	}

	@Override
	public int getColunas() {
		return this.colunas;
	}
	@Override
	public int getLinhas() {
		return linhas;
	}

	@Override
	public void setLinhas(int linhas) {
        this.linhas = linhas;
	}
	@Override
	public void setColunas(int colunas) {
        this.colunas = colunas;
    }
     

	public static TMatNZ carrega(String nome_arq){
		
	    int i=0;
		int j=0;
		TMatNZ matNZ = new TMatNZ();
		try {
			InputStream entrada = new FileInputStream(nome_arq);
	    int umByte = entrada.read();

	    while(umByte != -1)
	    {
	    	String conteudo = "";
	    	if((char)umByte!=' ' && (char)umByte!='\n' ){
	        	while((char)umByte!=' ' && (char)umByte!='\n' ){
	        		if((char)umByte==','){
	        			umByte = '.';
	        		}
	        		conteudo = conteudo+""+(char)umByte;
	        		
	        		umByte = entrada.read();
	        	}
	        	
				j++;
	    	}
	    	if(!conteudo.equals("")){
	    		//System.out.println(conteudo);
	    		matNZ.addItemMatriz(i, j-1,Float.parseFloat(conteudo) );
	    	}
	    	if((char)umByte=='\n'){
	    		i++;
	    		matNZ.colunas = j;
	    		j = 0;
	    	}
	    
	    	
	    	umByte = entrada.read();
	    }
	    matNZ.linhas = i;
		
		return matNZ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return matNZ;
		
	}


	@Override
	public String salva(String nome_arq) {
		String matString = "";
		
		for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < this.colunas; j++) {
                matString.concat(getValorPosIJ(i, j) + "  ");
            }
            matString.concat("");
        }
		return matString;
	}

	@Override
	public TMatNZ multiplica(TMatNZ matrix) {
		TMatNZ matrizResult = new TMatNZ(this.linhas, matrix.getColunas());
     
        for(int i=0;i<this.linhas;i++){
            for(int x=0;x<matrix.getColunas();x++){
                float acumulado = 0;
                for(int j=0;j<this.colunas;j++){
                    acumulado+= this.getValorPosIJ(i, j)*matrix.getValorPosIJ(j, x);
                }
                matrizResult.addItemMatriz(i, x, acumulado);
            }
        }
        return matrizResult;
    }

	@Override
	public TMatNZ soma(TMatNZ matrix) {

		if((matrix.getLinhas()!=this.getLinhas())||(matrix.getColunas()!=this.getColunas())){
			return null;
		}
		TMatNZ matrizResult = new TMatNZ(this.linhas, matrix.getColunas());
		for(int i = 0; i<this.linhas; i++){
			for(int j=0; j<this.colunas; j++){
				float acumulado = 0;
				acumulado+= this.getValorPosIJ(i, j) + matrix.getValorPosIJ(i, j);

				matrizResult.addItemMatriz(i, j, acumulado);
			}	
		}

		return matrizResult;
	}

	@Override
	public TMatNZ transposta() {
		TMatNZ transposta = new TMatNZ(this.getColunas(), this.getLinhas());
		for(int i = 0; i<this.linhas; i++){
			for(int j=0; j<this.colunas; j++){
				transposta.addItemMatriz(j, i, this.getValorPosIJ(i, j));
			}
		}
		return transposta;
	}

	@Override
	public TMatNZ vezesK(int k) {
		TMatNZ matrizResult = new TMatNZ(this.getLinhas(), this.getColunas());
		for(int i = 0; i<this.linhas; i++){
			for(int j=0; j<this.colunas; j++){
				float acumulado = 0;
				acumulado+= this.getValorPosIJ(i, j) * k;
				matrizResult.addItemMatriz(i, j, acumulado);
			}	
		}

		return matrizResult;
	}

	
}
