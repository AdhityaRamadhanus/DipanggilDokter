package rageagainstmachinelearning.dipanggildokter;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.ArrayList;

import rageagainstmachinelearning.dipanggildokter.Adapter.AmbulansAdapter;
import rageagainstmachinelearning.dipanggildokter.Adapter.ApotekAdapter;
import rageagainstmachinelearning.dipanggildokter.Adapter.DoctorAdapter;
import rageagainstmachinelearning.dipanggildokter.Beans.Ambulans;
import rageagainstmachinelearning.dipanggildokter.Beans.Apotek;
import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;


public class ApotekActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private Toolbar toolbar;

    ApotekAdapter apotekAdapter;
    ArrayList<Apotek> apotekArrayList;
    ListView apotekListView;
    TextView loading;

    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    public LocationRequest mLocationRequest;
    public Boolean mRequestLocationFirst=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulans);
        loading = (TextView) findViewById(R.id.empty);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_logopd);
        getSupportActionBar().setTitle(null);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        //Set DoctorList Adapter
        apotekArrayList = new ArrayList<Apotek>();
        //ambulansArrayList.add(new Ambulans("Adhitya R", "0838444520", "St Jimmy Hospital"));
        apotekListView = (ListView) findViewById(R.id.list_ambulans);
        apotekAdapter = new ApotekAdapter(this, R.layout.list_item_doctor, R.id.firstLine, apotekArrayList);
        apotekListView.setAdapter(apotekAdapter);
        apotekListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // custom dialog
                final Dialog dialog = new Dialog(ApotekActivity.this);
                dialog.setContentView(R.layout.ambulans_dialog);
                dialog.setTitle("Apotek Detail");

                // set the custom dialog components - text, image and button

                TextView text = (TextView) dialog.findViewById(R.id.ambulans_detail);
                text.setText(apotekArrayList.get(position).getAlamat());
                ImageView image = (ImageView) dialog.findViewById(R.id.dialog_img);
                image.setImageResource(R.drawable.ic_beacon);

                Button callButton = (Button) dialog.findViewById(R.id.button_call);
                // if button is clicked, close the custom dialog
                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        String CallNumber = apotekArrayList.get(position).getKontak();
                        callIntent.setData(Uri.parse("tel:" + CallNumber));
                        startActivity(callIntent);
                    }
                });

                Button cancelButton = (Button) dialog.findViewById(R.id.button_cancel);
                // if button is clicked, close the custom dialog
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
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
        else if (id ==  R.id.action_doctor){
            Intent intent = new Intent(this,DoctorActivity.class);
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
        String URL = "http://216.126.192.36/lapakdokter/api/apotek?latitude="
                +location.getLatitude()
                +"&longitude="
                +location.getLongitude()
                +"&offset=0&limit=10";
        return URL;
    }
    public void updateLocation(Location location){
        mLastLocation = location;
        //Toast.makeText(this, "lat:" + location.getLatitude() + " , long:" + location.getLongitude(), Toast.LENGTH_LONG).show();
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
                            try {
                                AndreAPIParser APP = new AndreAPIParser();
                                ArrayList<Apotek> Hospitals = APP.ParseApotek(response);
                                apotekArrayList.clear();
                                for(int i=0;i<Hospitals.size();i++){
                                    //Toast.makeText(getApplicationContext(), Hospitals.get(i).getNama(), Toast.LENGTH_LONG).show();
                                    apotekArrayList.add(Hospitals.get(i));
                                }
                                loading.setText("");
                                apotekAdapter.notifyDataSetChanged();
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
       // Toast.makeText(this, "Connected Gan", Toast.LENGTH_SHORT).show();
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
