package rageagainstmachinelearning.dipanggildokter.Beans;

/**
 * Created by nao on 10/7/15.
 */
public class Ambulans {
    private String Alamat,Kontak, Nama;
    Double Distance;
    public Ambulans(String N,String A,String K){
        Nama = N;
        Alamat=A;
        Kontak=K;
    }

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(Double distance) {
        Distance = distance;
    }

    public String getAlamat() {
        return Alamat;
    }

    public String getKontak() {
        return Kontak;
    }

    public String getNama(){
        return Nama;
    }
    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public void setKontak(String kontak) {
        Kontak = kontak;
    }

    public void setNama(String nama){
        Nama = nama;
    }

}
