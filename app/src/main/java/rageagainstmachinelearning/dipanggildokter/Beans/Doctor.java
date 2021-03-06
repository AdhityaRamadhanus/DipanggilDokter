package rageagainstmachinelearning.dipanggildokter.Beans;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by nao on 10/7/15.
 */
public class Doctor implements Serializable{
    private String Nama,Alamat,Kontak, image;
    Double Distance;
    public Doctor(String N,String A,String K){
        Nama=N;
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

    public String getNama() {
        return Nama;
    }

    public String getImage(){
        return this.image;
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

    public void setImage(String image){
        this.image = image;
    }
}
