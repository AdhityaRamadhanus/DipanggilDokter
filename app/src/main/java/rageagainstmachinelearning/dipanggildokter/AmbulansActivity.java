package rageagainstmachinelearning.dipanggildokter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import rageagainstmachinelearning.dipanggildokter.Adapter.AmbulansAdapter;
import rageagainstmachinelearning.dipanggildokter.Adapter.DoctorAdapter;
import rageagainstmachinelearning.dipanggildokter.Beans.Ambulans;
import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;


public class AmbulansActivity extends ActionBarActivity {
    private Toolbar toolbar;

    AmbulansAdapter ambulansAdapter;
    ArrayList<Ambulans> ambulansArrayList;
    ListView ambulansListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulans);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_apotek);
        getSupportActionBar().setTitle(null);

        //Set DoctorList Adapter
        ambulansArrayList = new ArrayList<Ambulans>();
        ambulansArrayList.add(new Ambulans("Adhitya R", "0838444520", "St Jimmy Hospital"));
        ambulansArrayList.add(new Ambulans("NGENT*O*T", "0838444520", "North Carolina"));
        ambulansArrayList.add(new Ambulans("A*N*J*I*N*G","0838444520","St Jimmy Hospital"));
        ambulansListView = (ListView) findViewById(R.id.list_ambulans);
        ambulansAdapter = new AmbulansAdapter(this, R.layout.list_item_doctor, R.id.firstLine, ambulansArrayList);
        ambulansListView.setAdapter(ambulansAdapter);
        ambulansListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // custom dialog
                final Dialog dialog = new Dialog(AmbulansActivity.this);
                dialog.setContentView(R.layout.ambulans_dialog);
                dialog.setTitle("Ambulance Detail");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.ambulans_detail);
                text.setText(ambulansArrayList.get(position).getAlamat());
                ImageView image = (ImageView) dialog.findViewById(R.id.dialog_img);
                image.setImageResource(R.drawable.ic_beacon);

                Button callButton = (Button) dialog.findViewById(R.id.button_cancel);
                // if button is clicked, close the custom dialog
                callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO CALL
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
        else if (id ==  R.id.action_apotek){
            Intent intent = new Intent(this,ApotekActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.action_doctor){
            Intent intent = new Intent(this, DoctorActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
