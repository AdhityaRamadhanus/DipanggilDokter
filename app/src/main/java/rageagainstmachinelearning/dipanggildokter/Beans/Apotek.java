package rageagainstmachinelearning.dipanggildokter.Beans;

/**
 * Created by nao on 10/9/15.
 */
public class Apotek {
    String Nama,Kontak,Alamat;
    Double Distance;
    public Apotek(String N,String K,String A, Double distance){
        Nama=N;
        Kontak=K;
        Alamat=A;
        Distance = distance;
    }

    public Double getDistance() {
        return Distance;
    }

    public void setDistance(Double distance) {
        Distance = distance;
    }

    public String getKontak() {
        return Kontak;
    }

    public String getNama() {
        return Nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public void setKontak(String kontak) {
        Kontak = kontak;
    }
}
