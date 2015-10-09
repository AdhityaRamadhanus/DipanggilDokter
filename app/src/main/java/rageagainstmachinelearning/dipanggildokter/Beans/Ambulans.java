package rageagainstmachinelearning.dipanggildokter.Beans;

/**
 * Created by nao on 10/7/15.
 */
public class Ambulans {
    private String Alamat,Kontak;
    public Ambulans(String N,String A,String K){
        Alamat=A;
        Kontak=K;
    }

    public String getAlamat() {
        return Alamat;
    }

    public String getKontak() {
        return Kontak;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public void setKontak(String kontak) {
        Kontak = kontak;
    }

}
