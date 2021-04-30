package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private String BASE_URL = "http://10.0.2.2/getLocationsByUser/";
    private ArrayList<Location> locations;
    private RecyclerView recyclerView;
    public String LOCATIONS_URL;
    public String google_id;
    public String email;
    public String name;
    public String user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Look up recycler view
        recyclerView = findViewById(R.id.recyclyer_view);
        locations = new ArrayList<>();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        google_id = intent.getStringExtra("google_id");
        user_id = intent.getStringExtra("user_id");

        getLocations();
    }

    void getLocations() {
        LOCATIONS_URL = BASE_URL += user_id;
        client.addHeader("Accept", "application/json");
        client.get(LOCATIONS_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    JSONObject responseObj = new JSONObject(new String(responseBody));
                    JSONArray locationsArr = responseObj.getJSONArray("locations");

                    setRecyclerView(locationsArr);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));

            }
        });
    }

    void setRecyclerView(JSONArray data) throws JSONException {
        for (int i = 0; i < data.length(); i++) {

            JSONObject locationObject = data.getJSONObject(i);
            Location location = new Location(locationObject.getInt("id"),
                    locationObject.getInt("user_id"),
                    locationObject.getString("name"),
                    locationObject.getString("google_place_id"),
                    locationObject.getString("description"),
                    locationObject.getInt("rating"));

            locations.add(location);
        }

        // Display in recycler view
        //create beer adapter to pass in data
        LocationAdapter adapter = new LocationAdapter(this, locations);
        //attach the adapter to recycler view to populate
        recyclerView.setAdapter(adapter);
        //layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
