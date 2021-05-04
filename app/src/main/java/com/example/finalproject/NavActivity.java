package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

public class NavActivity extends AppCompatActivity {

    Button profile_btn;
    Button new_btn;
    Button search_btn;
    public String google_id;
    public String email;
    public String name;
    public String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        // Grabbing buttons
        profile_btn = findViewById(R.id.profile_btn);
        new_btn = findViewById(R.id.new_btn);
        search_btn = findViewById(R.id.search_btn);

        // Grabbing sign in info
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        google_id = intent.getStringExtra("google_id");
        user_id = intent.getStringExtra("user_id");


        //on click listeners to load respective Fragments
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHome();
            }
        });
        new_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNew();
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        getHome();

    }
    public void getHome() {
        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("google_id", google_id);
        bundle.putString("user_id", user_id);

        // Load Fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, LocationFragment.class, bundle)
                .commit();
    }

    public void createNew() {
        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("google_id", google_id);
        bundle.putString("user_id", user_id);

        // Load Fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, CreateNewFragment.class, bundle)
                .commit();
    }

    public void search() {
        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("google_id", google_id);
        bundle.putString("user_id", user_id);

        // Load Fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, LocationFragment.class, bundle)
                .commit();
    }

}
