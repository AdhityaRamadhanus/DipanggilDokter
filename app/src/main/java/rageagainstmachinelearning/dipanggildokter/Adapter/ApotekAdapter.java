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

import rageagainstmachinelearning.dipanggildokter.Beans.Apotek;
import rageagainstmachinelearning.dipanggildokter.R;

/**
 * Created by nao on 10/9/15.
 */
public class ApotekAdapter extends ArrayAdapter<Apotek> {
    Context context;
    int layoutResourceId;
    ArrayList<Apotek> ApotekList;
    public ApotekAdapter(Context context,int Resource, int ResourceTextId,ArrayList<Apotek> data) {
        super(context, Resource,ResourceTextId, data);
        this.layoutResourceId = Resource;
        this.context = context;
        this.ApotekList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ApotekHolder holder = null;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ApotekHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.icon);
            holder.FirstLine = (TextView)row.findViewById(R.id.firstLine);
            holder.SecondLine = (TextView) row.findViewById(R.id.secondLine);
            row.setTag(holder);
        }
        else {
            holder = (ApotekHolder)row.getTag();
        }

        Apotek apotek = ApotekList.get(position);
        holder.FirstLine.setText(apotek.getNama()+" , "+apotek.getKontak());
        holder.SecondLine.setText(apotek.getAlamat());
        return row;
    }

    static class ApotekHolder {
        ImageView imgIcon;
        TextView FirstLine;
        TextView SecondLine;
    }
}