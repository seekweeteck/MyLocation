package com.edu.tarc.mylocation.DataSource;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.edu.tarc.mylocation.DataClass.LocationPoint;
import com.edu.tarc.mylocation.R;

import java.util.List;

/**
 * Created by TARC on 3/16/2016.
 */
public class LocationAdapter extends ArrayAdapter<LocationPoint> {
    private final Activity context;
    private final List<LocationPoint> locationPointList;
    private final int layout_id;

    public LocationAdapter(Activity context, int resource, List<LocationPoint> objects) {
        super(context, resource, objects);
        this.layout_id = resource;
        this.context = context;
        this.locationPointList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.location_item, null, true);

        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
        TextView textViewLatLon = (TextView) rowView.findViewById(R.id.textViewLatLon);

        textViewName.setText(locationPointList.get(position).getName());
        textViewLatLon.setText("Lat:" +locationPointList.get(position).getLatitude() +
                " Lon:" + locationPointList.get(position).getLongitude());
        return rowView;
    }
}
