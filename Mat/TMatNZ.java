

public class TMatNZ extends TADMatH{

	public int linhas, colunas;
	public int valor;
	
	public TMatNZ(int linhas, int colunas) {
		super(linhas, colunas);
		// TODO Auto-generated constructor stub
	}

	//TODO
	//Deve funcionar como abaixo:
	//TMatNZ mat = TMatNZ.carrega("mat.txt")
	public static TMatNZ carrega(String nome_arq){
		TMatNZ matriz = new TMatNZ(10, 20);
		return matriz;
	}


	@Override
	public float getElem(int i, int j) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setElem(int i, int j, float pelem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TADMatH multiplica(TADMatH mat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void carregaMMF(String nomeArq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salvaMMF(String nomeArq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(TADMatH pMat) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
