package com.edu.tarc.mylocation.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.tarc.mylocation.DataClass.LocationPoint;
import com.edu.tarc.mylocation.DataSource.LocationDataSource;
import com.edu.tarc.mylocation.R;

public class InsertLocationActivity extends AppCompatActivity implements LocationListener {
    protected LocationManager locationManagerGPS, locationManagerNetwork;
    TextView textViewLat, textViewLon;
    EditText editTextName, editTextDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_location);

        textViewLat = (TextView)findViewById(R.id.textViewLat);
        textViewLon = (TextView)findViewById(R.id.textViewLon);
        editTextName = (EditText)findViewById(R.id.editTextName);

        locationManagerGPS = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManagerNetwork = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManagerGPS.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, this);
        locationManagerNetwork.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, this);
    }

    public void saveLocation(View v){
        LocationPoint locationPoint = new LocationPoint();
        locationPoint.setName(editTextName.getText().toString());
        locationPoint.setLatitude(Double.parseDouble(textViewLat.getText().toString()));
        locationPoint.setLongitude(Double.parseDouble(textViewLon.getText().toString()));

        LocationDataSource locationDataSource= new LocationDataSource(this);
        locationDataSource.insertLocation(locationPoint);
        Toast.makeText(getApplicationContext(), "Location saved.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        textViewLat.setText("" + location.getLatitude());
        textViewLon.setText("" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManagerGPS.removeUpdates(this);
    }
}
