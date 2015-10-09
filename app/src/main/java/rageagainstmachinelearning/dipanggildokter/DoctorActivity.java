package rageagainstmachinelearning.dipanggildokter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import rageagainstmachinelearning.dipanggildokter.Adapter.DoctorAdapter;
import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;


public class DoctorActivity extends ActionBarActivity {
    private Toolbar toolbar;

    DoctorAdapter doctorAdapter;
    ArrayList<Doctor> doctorArrayList;
    ListView doctorListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_apotek);
        getSupportActionBar().setTitle(null);

        //Set DoctorList Adapter
        doctorArrayList = new ArrayList<Doctor>();
        doctorArrayList.add(new Doctor("Adhitya R","0838444520","St Jimmy Hospital"));
        doctorArrayList.add(new Doctor("NGENT*O*T", "0838444520", "North Carolina"));
        doctorArrayList.add(new Doctor("A*N*J*I*N*G","0838444520","St Jimmy Hospital"));
        doctorListView = (ListView) findViewById(R.id.list_doctor);
        doctorAdapter = new DoctorAdapter(this, R.layout.list_item_doctor,R.id.firstLine,  doctorArrayList);
        doctorListView.setAdapter(doctorAdapter);
        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (getApplicationContext(),DoctorDetailActivity.class);
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
}
