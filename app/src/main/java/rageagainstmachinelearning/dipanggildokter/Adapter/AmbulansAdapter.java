package rageagainstmachinelearning.dipanggildokter.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import rageagainstmachinelearning.dipanggildokter.Beans.Ambulans;
import rageagainstmachinelearning.dipanggildokter.Beans.Doctor;
import rageagainstmachinelearning.dipanggildokter.R;

public class AmbulansAdapter extends ArrayAdapter<Ambulans> {
    Context context;
    int layoutResourceId;
    ArrayList<Ambulans> AmbulansList;
    public AmbulansAdapter(Context context,int Resource, int ResourceTextId,ArrayList<Ambulans> data) {
        super(context, Resource,ResourceTextId, data);
        this.layoutResourceId = Resource;
        this.context = context;
        this.AmbulansList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AmbulansHolder holder = null;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new AmbulansHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
            holder.FirstLine = (TextView)row.findViewById(R.id.firstLine);
            holder.SecondLine = (TextView) row.findViewById(R.id.secondLine);
            row.setTag(holder);
        }
        else {
            holder = (AmbulansHolder)row.getTag();
        }

        Ambulans ambulans = AmbulansList.get(position);
        holder.FirstLine.setText(ambulans.getAlamat());
        holder.SecondLine.setText(ambulans.getKontak());
        return row;
    }

    static class AmbulansHolder {
        ImageView imgIcon;
        TextView FirstLine;
        TextView SecondLine;
    }
}

