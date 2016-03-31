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
import android.widget.TextView;
import android.widget.Toast;

import com.edu.tarc.mylocation.DataClass.LocationPoint;
import com.edu.tarc.mylocation.LocationEngine.LocationEngine;
import com.edu.tarc.mylocation.R;

public class CurrentLocationActivity extends AppCompatActivity implements LocationListener{
    protected LocationManager locationManager2;
    LocationEngine locationEngine;
    TextView textViewCurrent, textViewNear;
    LocationPoint locationPoint, locationCurrent;
    int size=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        size = MainActivity.index;
        if(size>=0){
            locationPoint = MainActivity.locationList.get(size);
        }else{
            locationPoint = new LocationPoint();
            locationPoint.setId("0");
            locationPoint.setLatitude(0);
            locationPoint.setLongitude(0);
            locationPoint.setName("Empty");
        }
        locationCurrent = new LocationPoint();
        locationEngine = new LocationEngine();

        textViewCurrent = (TextView) findViewById(R.id.textViewCurrentLocation);
        textViewNear = (TextView)findViewById(R.id.textViewNear);

        locationManager2 =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        try{
            locationManager2.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    60000, 1, this);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        double distance;

        locationCurrent.setLatitude(location.getLatitude());
        locationCurrent.setLongitude(location.getLongitude());

        distance = locationEngine.getDistance( locationCurrent.getLatitude(),  locationCurrent.getLongitude(),
                locationPoint.getLatitude(),locationPoint.getLongitude());
        textViewCurrent.setText("Selected Location :"+locationPoint.getName());

        int i = findNearest(locationCurrent);

        if(i>= 0){
            textViewNear.setText("Nearest point is:" + MainActivity.locationList.get(i).getName());
        }else{
            textViewNear.setText("Distance from the selected location :" + distance + " Kilometers");
        }

    }

    private int findNearest(LocationPoint current){
        int i=-1;
        double difference=0.0;
        for (int index=0; index < MainActivity.locationList.size()-1; index ++) {
            LocationPoint locationSearch = MainActivity.locationList.get(index);
            difference = locationEngine.getDistance(current.getLatitude(), current.getLongitude(), locationSearch.getLatitude(), locationSearch.getLongitude());
            if(difference <= 0.02){
                i = index;
                break;
            }
        }
        return i;
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
        locationManager2.removeUpdates(this);
    }
}
