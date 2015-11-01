package rageagainstmachinelearning.dipanggildokter;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rageagainstmachinelearning.dipanggildokter.Beans.Ambulans;
import rageagainstmachinelearning.dipanggildokter.Beans.Apotek;
import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;

public class AndreAPIParser {
    public ArrayList<Doctor> ParseDoctors(JSONObject response){
        JSONArray JHospitals = null;
        ArrayList<Doctor> HospitalsLoc = new ArrayList<Doctor>();
        try{
            JHospitals = response.getJSONArray("data");
            Log.d("Parsing JSON", "Dcotors Size : "+JHospitals.length());
            for(int i=0;i<JHospitals.length();i++){
                JSONObject rekt = (JSONObject) JHospitals.get(i);
                JSONArray DocName = (JSONArray) rekt.get("profile");
                JSONArray DocAddress = (JSONArray) rekt.get("address");
                JSONArray DocContact = (JSONArray) rekt.get("contact");
                JSONArray DocAvatar = (JSONArray) rekt.get("avatar");


                String DocNameString = DocName.getJSONObject(0).get("name").toString();
                String Distance = rekt.get("distance").toString();
                String DocAddressString = DocAddress.getJSONObject(0).get("address").toString();
                String DocContactString = DocContact.getJSONObject(0).get("phone").toString();
                String DocImageString = DocAvatar.getJSONObject(0).get("href").toString();

                Log.d("Parsing JSON","Profile Name : "+DocNameString);
                Log.d("Parsing JSON","Doctor Address : "+DocAddressString);
                Log.d("Parsing JSON","Doctor Distance : "+Distance);
                Log.d("Parsing JSON","Doctor Contact : "+DocContactString);
                HospitalsLoc.add(new Doctor(DocNameString, DocAddressString, DocContactString));
                HospitalsLoc.get(i).setDistance(Double.parseDouble(Distance));
                HospitalsLoc.get(i).setImage(DocImageString);
            }
        } catch (JSONException e) {
            e.printStackTrace();}

        return HospitalsLoc;
    }

    public ArrayList<Ambulans> ParseAmbulans(JSONObject response){
        JSONArray JHospitals = null;
        ArrayList<Ambulans> HospitalsLoc = new ArrayList<Ambulans>();
        try{
            JHospitals = response.getJSONArray("data");
            Log.d("Parsing JSON", "Dcotors Size : "+JHospitals.length());
            for(int i=0;i<JHospitals.length();i++){
                JSONObject rekt = (JSONObject) JHospitals.get(i);

                String AmbNameString = rekt.get("name").toString();
                String Distance = rekt.get("distance").toString();
                String AmbPhoneString = rekt.get("ambulan_phone").toString();

                Log.d("Parsing JSON","Ambulan Name : "+AmbNameString);
                Log.d("Parsing JSON","Ambulan Phone : "+AmbPhoneString);
                Log.d("Parsing JSON","Ambulan Distance : "+Distance);

                HospitalsLoc.add(new Ambulans(AmbNameString, AmbPhoneString, Distance));
                HospitalsLoc.get(i).setDistance(Double.parseDouble(Distance));
            }
        } catch (JSONException e) {
            e.printStackTrace();}

        return HospitalsLoc;
    }
    public ArrayList<Apotek> ParseApotek(JSONObject response){
        JSONArray JHospitals = null;
        ArrayList<Apotek> HospitalsLoc = new ArrayList<Apotek>();
        try{
            JHospitals = response.getJSONArray("data");
            Log.d("Parsing JSON", "Dcotors Size : "+JHospitals.length());
            for(int i=0;i<JHospitals.length();i++){
                JSONObject rekt = (JSONObject) JHospitals.get(i);
                //JSONObject AptName = (JSONObject) rekt.get("profile");
                JSONArray AptAddress = (JSONArray) rekt.get("address");
                JSONArray AptContact = (JSONArray) rekt.get("contact");

                //String AptNameString = AptName.toString();
                String distance = rekt.get("distance").toString();
                String name = rekt.get("name").toString();
                String address = AptAddress.getJSONObject(0).get("address").toString();
                String phone = AptContact.getJSONObject(0).get("phone").toString();

                HospitalsLoc.add(new Apotek(name, phone, address, Double.parseDouble(distance)));
                //HospitalsLoc.add(new Apotek(AptNameString, AptContactString, AptAddressString));
                //HospitalsLoc.get(i).setDistance(Double.parseDouble(Distance));
            }
        } catch (JSONException e) {
            e.printStackTrace();}

        return HospitalsLoc;
    }
}
