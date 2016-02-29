package com.example.lenovo.bey2olak_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private RecyclerView mRecyclerView;
    private MainRecyclerView_Adapter adapter;
    ProgressDialog loading = null;
    FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    double latitude, longitude;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitViews();
        db = new DatabaseHandler(this);
        List<POI_Data> poi_datas = db.getAllPOIS();
        adapter = new MainRecyclerView_Adapter(this,poi_datas);
        if (db.getContactsCount() == 0) {
            Snackbar.make(coordinatorLayout, "Click on the right button to get your location and the POIs", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        else
            mRecyclerView.setAdapter(adapter);

    }

    private void InitViews() {
        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Please Wait");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);
        mRecyclerView.setLayoutManager(layoutManager);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

//        if (adapter.getItemCount() == 0) {
//            Snackbar.make(coordinatorLayout, "Click on the right button to get your location and the POIs", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent = new Intent(MainActivity.this, POI_Screen.class);
                    Toast.makeText(getApplicationContext(), String.valueOf(latitude) + " " + String.valueOf(longitude), Toast.LENGTH_LONG).show();
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "open your location please", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }

        }

    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = (double) (location.getLatitude());
        longitude = (double) (location.getLongitude());
        Toast.makeText(getApplicationContext(), String.valueOf(latitude) + " " + String.valueOf(longitude), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
