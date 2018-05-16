package tadPolinomio;

import TabH.TDic;
import tadDicionario.*;

public class TADPoli{
  
  private TDic dicpoli = null;
  
  
  public TADPoli(String strpoli){
    
    
    TDic dicpoli = new TDicChain();
    
    // Inclui sinal no primeiro monômio, caso o sinal não exista.
    if((strpoli.charAt(0) != '+') && (strpoli.charAt(0) != '-'))
      strpoli = "+" + strpoli;
    
    String strmono = "";
    char sinal = strpoli.charAt(0);
    
    
    
    for(int i =1; i<strpoli.length(); i++){
      if((strpoli.charAt(i) != '+') || (strpoli.charAt(i) != '-')){
        strmono = strmono + strpoli.charAt(i);
        
      }
      else{
        
        String []v = strmono.split("X");
        
        Float coef;
        Integer grau;
        
        if(v.length == 2){
          if((v[0]!= "") && (v[1]!="")){
            coef = Float.parseFloat(v[0]);
            grau = Integer.parseInt(v[1]);
          }
          else {
           if((v[0]!= "") && (v[1] =="")){
            coef = Float.parseFloat(v[0]);
            grau = Integer.parseInt("1");
           }
            else{
              coef = Float.parseFloat("1.0f");
              grau = Integer.parseInt(v[1]);
            }
          }
        }
        else{
         coef = Float.parseFloat(v[0]);
         grau = Integer.parseInt("0");
          
        }
        if(sinal =='-')
          coef = coef * -1;
        
        dicpoli.insertItem(grau,coef);
        sinal = strpoli.charAt(i);  
        
      }
      
      
      
      
    }//for
    
    
  }
}
