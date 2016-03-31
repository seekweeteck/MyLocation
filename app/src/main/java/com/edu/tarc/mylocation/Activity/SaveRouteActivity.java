package com.edu.tarc.mylocation.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.edu.tarc.mylocation.DataClass.LocationPoint;
import com.edu.tarc.mylocation.DataClass.Route;
import com.edu.tarc.mylocation.DataSource.LocationDataSource;
import com.edu.tarc.mylocation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_route);
    }

    public void saveRecord(View v) {
        EditText editTextID, editTextName;

        editTextID = (EditText) findViewById(R.id.editTextLocationID);
        editTextName = (EditText) findViewById(R.id.editTextLocationName);

        Route route = new Route();

        route.setID(editTextID.getText().toString());
        route.setName(editTextName.getText().toString());

        try {
            String url = getApplicationContext().getString(R.string.insert_route);
            insertRoute(this, url, route);


            LocationDataSource dataSource;
            dataSource = new LocationDataSource(this);
            dataSource.open();
            final List<LocationPoint> locationList = dataSource.getAllLocations();

            if(locationList.isEmpty()){
                Toast.makeText(getApplicationContext(),"No location record.", Toast.LENGTH_SHORT).show();
            }else{
                String url2 = getApplicationContext().getString(R.string.insert_route_detail);
                for (LocationPoint locationPoint:locationList
                     ) {
                    locationPoint.setId(route.getID());
                    insertRouteDetails(this, url2, locationPoint);
                }
            }
            dataSource.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void insertRoute(Context context, String url, final Route route){
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(getApplicationContext(), "Record saved. " + response, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", String.valueOf(route.getID()));
                    params.put("name", route.getName());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertRouteDetails(Context context, String url, final LocationPoint point){
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(getApplicationContext(), "Record saved. " + response, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", point.getId());
                    params.put("name", point.getName());
                    params.put("coordinate",  point.getLatitude() + "," + point.getLongitude());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
