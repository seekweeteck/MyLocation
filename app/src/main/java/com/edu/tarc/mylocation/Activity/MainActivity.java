package com.edu.tarc.mylocation.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.tarc.mylocation.DataClass.LocationPoint;
import com.edu.tarc.mylocation.DataSource.LocationAdapter;
import com.edu.tarc.mylocation.DataSource.LocationDataSource;
import com.edu.tarc.mylocation.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LocationDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listViewLocation;
        listViewLocation = (ListView)findViewById(R.id.listViewLocation);

        try{

            dataSource = new LocationDataSource(this);
            dataSource.open();
            final List<LocationPoint> locationList = dataSource.getAllLocations();

            if(locationList.isEmpty()){
                Toast.makeText(getApplicationContext(),"No location record.", Toast.LENGTH_SHORT).show();
            }else{
                LocationAdapter locationAdapter = new LocationAdapter(this, R.layout.location_item, locationList);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
