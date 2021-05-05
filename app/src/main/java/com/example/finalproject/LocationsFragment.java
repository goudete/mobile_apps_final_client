package com.example.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LocationsFragment extends Fragment {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private String BASE_URL = "http://10.0.2.2/getLocationsByUser/";
    private ArrayList<Location> locations;
    private RecyclerView recyclerView;
    public String LOCATIONS_URL;
    public String google_id;
    public String email;
    public String name;
    public String user_id;

    public LocationsFragment() {
        //set data here
        super(R.layout.fragment_locations);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        //Look up recycler view
        recyclerView = view.findViewById(R.id.recyclyer_view);
        locations = new ArrayList<>();

        // Receive following info through bundle
        name = this.getArguments().getString("name");
        email = this.getArguments().getString("email");
        google_id = this.getArguments().getString("google_id");
        user_id = this.getArguments().getString("user_id");

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
        LocationAdapter adapter = new LocationAdapter(locations);
        //attach the adapter to recycler view to populate
        recyclerView.setAdapter(adapter);
        //layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));

    }
}
