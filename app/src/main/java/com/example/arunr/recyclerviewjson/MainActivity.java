package com.example.arunr.recyclerviewjson;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://n-pvt.hungama.com/v2/content/movieapp/queue_data.json?device=1080x1920&section_id=1&genre=Gossip&bucket_id=5360&offset=0&user_type=1&version=2.0.10.7&app-id=e3MH8F20cr&limit=10&cp=33682232";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<DataList> dataLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataLists = new ArrayList<>();

        loadUrlData();
    }

    HashMap<String, JSONArray> imageList = new HashMap<String, JSONArray>();

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Getting JSON array node

                    JSONObject jsonObject1 = jsonObject.getJSONObject("node");
                    // Data node is a JSON array
                    JSONArray array = jsonObject1.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jo = array.getJSONObject(i);
                        imageList.put(jo.getString("id"), jo.getJSONArray("images"));
                        DataList data = new DataList(jo.getString("id"),jo.getString("name"),
                                jo.getString("genre"), "", imageList);
                        dataLists.add(data);
                    }
                    adapter = new DataAdapter(dataLists, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
