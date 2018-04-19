//todo
public class ElemMat {
 
    private int i;
    private int j;
    private float elem;
     
    public ElemMat(int i, int j, float pelem){
        this.i = i;
        this.j = j;
        this.elem = pelem;
    }
 
    public int getI() {
        return i;
    }
 
    public void setI(int i) {
        this.i = i;
    }
 
    public int getJ() {
        return j;
    }
 
    public void setJ(int j) {
        this.j = j;
    }
 
    public float getElem() {
        return elem;
    }
 
    public void setElem(float elem) {
        this.elem = elem;
    }
     
     
    public boolean equals(ElemMat pelemMat){
        if(pelemMat.elem == this.elem){
            return true;
        }
        else{
            return false;
        }
    }
     
     
     
}
