package rageagainstmachinelearning.dipanggildokter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;
import rageagainstmachinelearning.dipanggildokter.R;

/**
 * Created by nao on 10/4/15.
 */
public class DoctorAdapter extends ArrayAdapter<Doctor> {
    Context context;
    int layoutResourceId;
    ArrayList<Doctor> DoctorList;
    public DoctorAdapter(Context context,int Resource, int ResourceTextId,ArrayList<Doctor> data) {
        super(context, Resource,ResourceTextId, data);
        this.layoutResourceId = Resource;
        this.context = context;
        this.DoctorList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        DoctorHolder holder = null;
        Doctor doctor = DoctorList.get(position);
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new DoctorHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
            holder.FirstLine = (TextView)row.findViewById(R.id.firstLine);
            holder.SecondLine = (TextView) row.findViewById(R.id.secondLine);
            DownloadImageTask task= new DownloadImageTask(holder.imgIcon);
            task.execute(doctor.getImage());
            row.setTag(holder);
        }
        else {
            holder = (DoctorHolder)row.getTag();
        }

        holder.FirstLine.setText(doctor.getNama()+" , "+doctor.getKontak());
        holder.SecondLine.setText(doctor.getAlamat());

        return row;
    }

    static class DoctorHolder {
        ImageView imgIcon;
        TextView FirstLine;
        TextView SecondLine;
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
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.docfinder);
    }
}

