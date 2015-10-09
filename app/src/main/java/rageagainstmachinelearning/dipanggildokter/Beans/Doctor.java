package rageagainstmachinelearning.dipanggildokter.Beans;

import java.io.Serializable;

/**
 * Created by nao on 10/7/15.
 */
public class Doctor implements Serializable{
    private String Nama,Alamat,Kontak;
    public Doctor(String N,String A,String K){
        Nama=N;
        Alamat=A;
        Kontak=K;
    }

    public String getAlamat() {
        return Alamat;
    }

    public String getKontak() {
        return Kontak;
    }

    public String getNama() {
        return Nama;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public void setKontak(String kontak) {
        Kontak = kontak;
    }

    public void setNama(String nama) {
        Nama = nama;
    }
}
