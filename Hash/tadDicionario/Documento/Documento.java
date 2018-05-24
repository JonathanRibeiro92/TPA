package Hash.tadDicionario.Documento;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Hash.hashFunctions.HashEngineDefault;
import Hash.tadDicionario.TDicEA;

public class Documento implements IDocumento{
    
    private TDicEA tabFreq;
    char[] strseparadores = {'?','!','@','#','$','%','&','*','(',')','_','+','=','§','¹','²','³',':',';','.',',',' ','\\','/','|','°','º','ª','>','<','£','¢','[',']','{','}','\n','\t','“','?','’','1','2','3','4','5','6','7','8','9','0'};
    public Documento(String nome_arq_doc) throws IOException{
        this.tabFreq = new TDicEA(new HashEngineDefault());
        
        String texto = carregaArquivo(nome_arq_doc);

        StringBuilder strbuffer = new StringBuilder();
        String straux = texto.toLowerCase();
        straux=straux+" ";
        for (int i = 0; i < straux.length(); i++) {
            for (int j = 0; j < strseparadores.length; j++) {

                if (!((straux.charAt(i)) == strseparadores[j])) {
                    if (straux.charAt(i) == '-') {
                        if (straux.charAt(i - 1) != ' ' && straux.charAt(i + 1) != ' ') {
                            strbuffer.append(straux.charAt(i));

                        }
                    } else {
                        strbuffer.append(straux.charAt(i));
                    }
                } else {
                    if (!strbuffer.equals("")) {
                        tabFreq.insertItem(strbuffer.toString(), +1);
                    }
                    strbuffer.delete(0,1);
                    strbuffer = new StringBuilder();
                }
            }
        }


    }

	@Override
	public TDicEA getTabFreq(String nome_arq_doc) {
        return this.tabFreq;
	}

	@Override
	public String salvaTabFreq(String nome_arq_doc) {
        
        try {
            Documento documento = new Documento(nome_arq_doc);
            if(documento.tabFreq.isEmpty()){
                return "";
            }
            String strTabFreq = "";

            for (Object k : documento.tabFreq.keys()) {
                strTabFreq.concat(k.toString());
                strTabFreq.concat(";");
                strTabFreq.concat((tabFreq.findElement(k).toString()));
            }
		return strTabFreq;


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        
        return null;
	}

    public String carregaArquivo(String nomeArq) throws IOException {
        String texto;
        FileReader arq = new FileReader(nomeArq);
        BufferedReader lerArq = new BufferedReader(arq);
        texto = lerArq.toString();
        arq.close();

        return texto;
    }

  
}
