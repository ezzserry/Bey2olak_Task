package com.example.lenovo.bey2olak_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class POI_Screen extends AppCompatActivity {
    double latitude, longitude;
    String url;
    private ArrayList<POI_Data> poi_dataList;
    DatabaseHandler db;
    POI_RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            latitude = bundle.getDouble("latitude");
            longitude = bundle.getDouble("longitude");
        }
        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Please Wait");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_POIs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SendRequest();
        db = new DatabaseHandler(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String data = "";


        if (adapter.getlist() != null) {
            List<POI_Data> stList = ((POI_RecyclerViewAdapter) adapter)
                    .getlist();
            for (int i = 0; i < stList.size(); i++) {
                POI_Data poi_data = stList.get(i);
                if (poi_data.isSelected() == true) {

                    data = data + "\n" + poi_data.getName().toString();
                    db.AddPOI(new POI_Data(poi_data.getId(), poi_data.getName()));
                }

            }

            Toast.makeText(POI_Screen.this,
                    "Selected POIs: \n" + data, Toast.LENGTH_LONG)
                    .show();
        } else
            Toast.makeText(POI_Screen.this,
                    "No selected POIs: \n" + data, Toast.LENGTH_LONG)
                    .show();
    }

    private void SendRequest() {
        loading.show();
        url = "https://api.foursquare.com/v2/venues/search?ll=%s,%s&oauth_token=QPOJKCFGBOP3QDVC4KAYRXBORLNWB1CDMCS11OCKFJ1ANPOU&v=20160229";
        url = String.format(url, latitude, longitude);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject meta = response.getJSONObject("meta");
                            String code = meta.getString("code");
                            if (code.equals("200")) {
                                poi_dataList = new ArrayList<>();
                                Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();
                                JSONArray venus = response.getJSONObject("response").getJSONArray("venues");
                                for (int i = 0; i < venus.length(); i++) {
                                    JSONObject object = (JSONObject) venus.get(i);
                                    POI_Data poi_data = new POI_Data();
                                    poi_data.setName(object.getString("name"));
                                    try {
                                        JSONObject location = object.getJSONObject("location");
                                        JSONArray address = location.getJSONArray("formattedAddress");
                                        poi_data.setAddress(address.getString(0) + " " + address.getString(1));

                                    } catch (Exception e) {
                                        poi_data.setAddress("No Address found");
                                    }
                                    JSONObject categ = object.getJSONArray("categories").getJSONObject(0);
                                    JSONObject icon = categ.getJSONObject("icon");
                                    poi_data.setImg(icon.getString("prefix") + "64" + icon.getString("suffix"));
                                    poi_dataList.add(poi_data);

                                }
                                // adapter
                                adapter = new POI_RecyclerViewAdapter(POI_Screen.this, poi_dataList);
                                recyclerView.setAdapter(adapter);
                                loading.dismiss();
                            } else {
                                loading.dismiss();
                                String message = response.getString("meta");
                                Toast.makeText(getApplicationContext(), code + " " + message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            loading.dismiss();
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSONException Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Response Error", Toast.LENGTH_LONG).show();
                    }
                }
        ) {

        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(request);
    }

}
