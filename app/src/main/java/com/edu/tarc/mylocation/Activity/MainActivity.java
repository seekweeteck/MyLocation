package com.edu.tarc.mylocation.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.tarc.mylocation.DataClass.LocationPoint;
import com.edu.tarc.mylocation.DataSource.LocationAdapter;
import com.edu.tarc.mylocation.DataSource.LocationDataSource;
import com.edu.tarc.mylocation.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private LocationDataSource dataSource;
    public static List<LocationPoint> locationList;
    public static int index = -1;
    ListView listViewLocation;
    LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewLocation = (ListView)findViewById(R.id.listViewLocation);
        listViewLocation.setOnItemClickListener(this);

    }

    private void updateLocationList() {
        try{
            dataSource = new LocationDataSource(this);
            dataSource.open();
            locationList = dataSource.getAllLocations();

            if(locationList.isEmpty()){
                Toast.makeText(getApplicationContext(),"No location record.", Toast.LENGTH_SHORT).show();
            }else{
                locationAdapter = new LocationAdapter(this, R.layout.location_item, locationList);
                listViewLocation.setAdapter(locationAdapter);
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

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
        }else if(id==R.id.action_insert){
            Intent intent = new Intent(this, InsertLocationActivity.class);
            startActivity(intent);
        }else if(id==R.id.action_save){
            Intent intent = new Intent(this, SaveRouteActivity.class);
            startActivity(intent);
        }else if(id==R.id.action_locate){
            Intent intent = new Intent(this, CurrentLocationActivity.class);
            startActivity(intent);
        }else if(id==R.id.action_delete){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Delete all location records");

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    LocationDataSource locationDataSource = new LocationDataSource(getApplicationContext());
                    locationDataSource.open();
                    locationDataSource.deleteLocation();
                    locationDataSource.close();
                    Toast.makeText(MainActivity.this,"Record(s) deleted.",Toast.LENGTH_LONG).show();
                    listViewLocation.setAdapter(null);
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else if(id == R.id.action_map){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLocationList();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(), "Item selected: " + i, Toast.LENGTH_SHORT).show();
        index = i;
    }
}
