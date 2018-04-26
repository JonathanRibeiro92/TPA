public class AppMat{

    public static void main(String[] args) {
        TMatNZ matA = TMatNZ.carrega("nomearq.txt");
        TMatNZ matb = TMatNZ.carrega("nomearq2.txt");

        TMatNZ matP = matA.multiplica(matb);

        System.out.println(matP.toString());

        TMatNZ matGabarito = TMatNZ.carrega("gabaritoMulti.txt");

        System.out.println(matP.equals(matGabarito));
    }
}