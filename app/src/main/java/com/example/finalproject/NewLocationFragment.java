package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cz.msebera.android.httpclient.Header;

public class NewLocationFragment extends Fragment {
    public String name;
    public String email;
    public String google_id;
    public String user_id;
    private EditText locationName;
    private EditText locationDescription;
    private EditText locationRating;
    private Button submitButton;
    private String URL = "http://10.0.2.2/createLocation";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public NewLocationFragment() {
        //set data here
        super(R.layout.fragment_create_new);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        // Receive following info through bundle
        name = this.getArguments().getString("name");
        email = this.getArguments().getString("email");
        google_id = this.getArguments().getString("google_id");
        user_id = this.getArguments().getString("user_id");

        //Grab ui elements
        locationName = getView().findViewById(R.id.location_name);
        locationDescription = getView().findViewById(R.id.location_description);
        locationRating = getView().findViewById(R.id.location_rating);
        submitButton = getView().findViewById(R.id.submit_button);

        //Listen for button click
        submitButton.setOnClickListener(v -> {
            validateInput(locationName, locationDescription, locationRating);
        });

    }
    public void validateInput(EditText name, EditText description, EditText rating) {
        // Grab input
        String nameString = name.getText().toString();
        String descriptionString = description.getText().toString();
        String ratingString = rating.getText().toString();

        // validate
        if (nameString.isEmpty() || descriptionString.isEmpty() || ratingString.isEmpty()) {
            Toast.makeText(getActivity(),"Please check your input and try again",Toast.LENGTH_SHORT).show();
            return;
        }
        postData(nameString, "helloworld", descriptionString, ratingString);

    }
    public void postData(String name, String google_place_id, String description, String rating ) {
        // PARAMS
        RequestParams params = new RequestParams();
        int id = Integer.valueOf(user_id);
        int ratingInt = Integer.valueOf(rating);

        params.put("user_id", id);
        params.put("name", name);
        params.put("google_place_id", google_place_id);
        params.put("description", description);
        params.put("rating", ratingInt);

        // POST REQUEST
        Log.d("PRE POST", "CREATELOCATION");
        client.post(URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("Status Code", String.valueOf(statusCode));
                Log.d("responseBody", new String(responseBody));
                Toast.makeText(getActivity(),"SUCCESS",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
