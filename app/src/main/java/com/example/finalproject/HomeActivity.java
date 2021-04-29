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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private String BASE_URL = "http://10.0.2.2/getLocationsByUser/";
    public String LOCATIONS_URL;
    public String google_id;
    public String email;
    public String name;
    public String user_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        google_id = intent.getStringExtra("google_id");
        user_id = intent.getStringExtra("user_id");

        Log.d("USER ID:", user_id);
        getLocations();
    }

    void getLocations() {
        LOCATIONS_URL = BASE_URL += user_id;
        client.addHeader("Accept", "application/json");
        client.get(LOCATIONS_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                // Get info
                try {
                    JSONArray responseArr = new JSONArray(String.valueOf(responseBody));

                    // Display in recycler view
                    Log.d("LOCATION REQUEST", "SUCCESS");
                    System.out.print(responseBody);
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
}
