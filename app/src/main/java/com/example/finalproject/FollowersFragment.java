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


public class FollowersFragment extends Fragment {
    public String name;
    public String email;
    public String google_id;
    public String user_id;
    private String BASE_URL = "http://10.0.2.2/getFollowers/";
    private String USER_URL;
    private static AsyncHttpClient client = new AsyncHttpClient();
    private ArrayList<User> usersArr;
    private RecyclerView recyclerView;


    public FollowersFragment() {
        super(R.layout.fragment_followers);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        //Look up recycler view
        recyclerView = view.findViewById(R.id.followers_recycler_view);
        usersArr = new ArrayList<>();

        // Receive following info through bundle
        name = this.getArguments().getString("name");
        email = this.getArguments().getString("email");
        google_id = this.getArguments().getString("google_id");
        user_id = this.getArguments().getString("user_id");

        getUsers();

    }
    void getUsers() {
        USER_URL = BASE_URL += user_id;
        client.addHeader("Accept", "application/json");
        client.get(USER_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                try {
                    JSONObject responseObj = new JSONObject(new String(responseBody));
                    JSONObject followers = responseObj.getJSONObject("followers");
                    JSONArray followersArr = followers.getJSONArray("followerObjects");
                    setRecyclerView(followersArr);

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
            JSONObject userObject = data.getJSONObject(i);
            User userObj = new User(userObject.getInt("id"),
                    userObject.getString("username"));
            usersArr.add(userObj);
        }

        // Display in recycler view
        UserAdapter adapter = new UserAdapter(usersArr);
        //attach the adapter to recycler view to populate
        recyclerView.setAdapter(adapter);
        //layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
    }
}
