package rageagainstmachinelearning.dipanggildokter;

import android.app.DownloadManager;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;

import rageagainstmachinelearning.dipanggildokter.Adapter.DoctorAdapter;
import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;


public class DoctorActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private Toolbar toolbar;
    private TextView loading;
    DoctorAdapter doctorAdapter;
    ArrayList<Doctor> doctorArrayList;
    ListView doctorListView;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    public LocationRequest mLocationRequest;
    public Boolean mRequestLocationFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_logopd);
        getSupportActionBar().setTitle(null);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        //Set DoctorList Adapter
        doctorArrayList = new ArrayList<Doctor>();
        //doctorArrayList.add(new Doctor("Adhitya R","0838444520","St Jimmy Hospital"));
        doctorListView = (ListView) findViewById(R.id.list_doctor);
        loading = (TextView) findViewById(R.id.empty);
        doctorAdapter = new DoctorAdapter(this, R.layout.list_item_doctor,R.id.firstLine, doctorArrayList);
        doctorListView.setAdapter(doctorAdapter);
        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DoctorDetailActivity.class);
                intent.putExtra("Doctor", doctorArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id ==  R.id.action_apotek){
            Intent intent = new Intent(this,ApotekActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.action_call){
            Intent intent = new Intent(this,AmbulansActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }
    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);

    }
    public String getDoctorsURL(Location location){
        String URL = "http://216.126.192.36/lapakdokter/api/doctors?latitude="
                +location.getLatitude()
                +"&longitude="
                +location.getLongitude()
                +"&offset=0&limit=10";
        return URL;
    }
    public void updateLocation(Location location){
        mLastLocation = location;
        //Toast.makeText(this,"lat:"+location.getLatitude()+" , long:"+location.getLongitude(),Toast.LENGTH_LONG).show();
        if (mRequestLocationFirst) {
            mLastLocation.setLongitude(0);
            mLastLocation.setLatitude(0);
            String HospitalURL = getDoctorsURL(mLastLocation);
            Log.d("Doctor URL", HospitalURL);
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,HospitalURL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Parsing JSON", response.toString());
                            try {
                                Log.d("Parsing JSON",response.get("type").toString());
                                AndreAPIParser APP = new AndreAPIParser();
                                ArrayList<Doctor> Hospitals = APP.ParseDoctors(response);
                                doctorArrayList.clear();
                                for(int i=0;i<Hospitals.size();i++){
                                    doctorArrayList.add(Hospitals.get(i));
                                }
                                loading.setText("");
                                doctorAdapter.notifyDataSetChanged();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            queue.add(req);
            mRequestLocationFirst=false;
        }
    }
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    public void onConnected(Bundle bundle) {
        //Toast.makeText(this, "Connected Gan", Toast.LENGTH_SHORT).show();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "COnnection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"Connection Failed",Toast.LENGTH_SHORT).show();
    }
}
