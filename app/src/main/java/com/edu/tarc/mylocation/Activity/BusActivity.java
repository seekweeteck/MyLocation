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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.tarc.mylocation.DataClass.ConstantValues;
import com.edu.tarc.mylocation.DataClass.LocationPoint;
import com.edu.tarc.mylocation.DataSource.LocationDataSource;
import com.edu.tarc.mylocation.R;

public class BusActivity extends AppCompatActivity implements LocationListener{
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    protected LocationManager locationManagerGPS;
    TextView textViewCurrentLocation;
    EditText editTextUpdate;
    Switch aSwitchOnOffGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        textViewCurrentLocation = (TextView)findViewById(R.id.textViewCurrentLocation);
        editTextUpdate = (EditText)findViewById(R.id.editTextStatus);
        aSwitchOnOffGPS = (Switch)findViewById(R.id.switchGPS);

        locationManagerGPS = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    public void onoffGPS(View v){
        aSwitchOnOffGPS = (Switch)v;
        if(aSwitchOnOffGPS.isChecked()){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
                return;
            }
            locationManagerGPS.requestLocationUpdates(LocationManager.GPS_PROVIDER, ConstantValues.UPDATE_INTERVAL, ConstantValues.UPDATE_DISTANCE, this);
            Toast.makeText(getApplicationContext(), "GPS is ON", Toast.LENGTH_LONG).show();
        }else{
            locationManagerGPS.removeUpdates(this);
            Toast.makeText(getApplicationContext(), "GPS is OFF", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        locationManagerGPS.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        textViewCurrentLocation.setText("Lat:" + location.getLatitude() + "\nLon:" + location.getLongitude());
        editTextUpdate.setText(editTextUpdate.getText() + "Lat:" + location.getLatitude() + "\nLon:" + location.getLongitude() + "\n");
        saveLocation(location);
    }

    private void saveLocation(Location location) {
        LocationPoint locationPoint = new LocationPoint();
        locationPoint.setName("1");
        locationPoint.setLatitude(location.getLatitude());
        locationPoint.setLongitude(location.getLongitude());

        LocationDataSource locationDataSource= new LocationDataSource(this);
        locationDataSource.insertLocation(locationPoint);
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
}
