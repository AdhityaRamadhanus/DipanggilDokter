package rageagainstmachinelearning.dipanggildokter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;


public class DoctorDetailActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private Context ctx;
    private TextView name;
    private TextView distance;
    private TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        Intent intent = getIntent();
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("Doctor");
        name = (TextView) findViewById(R.id.dokterNama);
        distance = (TextView) findViewById(R.id.dokterDistance);
        address = (TextView) findViewById(R.id.dokterAlamat);
        name.setText(doctor.getNama());
        distance.setText(Math.round(doctor.getDistance())+" KM");
        address.setText(doctor.getAlamat());

        toolbar = (Toolbar) findViewById(R.id.tool_bar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_logopd);
        getSupportActionBar().setTitle(null);
        ctx = this.getApplicationContext();

        ImageView doctorPhoto = (ImageView) findViewById(R.id.dokterPhoto);
        DownloadImageTask task = new DownloadImageTask(doctorPhoto);
        task.execute(doctor.getImage());

       // Toast.makeText(getApplicationContext(), doctor.getNama(), Toast.LENGTH_SHORT).show();

//        TextView textView = (TextView) findViewById(R.id.doctor_name);
//        textView.setText(doctor.getNama());



        Button btnCall = (Button) findViewById(R.id.btn_calldoctor);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+6285795884714"));
                startActivity(intent);
              //  Toast.makeText(getApplicationContext(), "calling", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctor_detail, menu);
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
        return super.onOptionsItemSelected(item);
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView mImageView;
        public DownloadImageTask(ImageView mImageView){
            this.mImageView = mImageView;
        }
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            //Do something with bitmap eg:
            mImageView.setImageBitmap(result);
        }
    }

    private Bitmap loadImageFromNetwork(String imageURL){
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeResource(getResources(), R.drawable.docfinder);
    }
}
