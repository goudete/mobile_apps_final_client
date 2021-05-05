package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NavActivity extends AppCompatActivity {

    Button profile_btn;
    Button new_btn;
    Button followees_btn;
    Button followers_btn;
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
        followees_btn = findViewById(R.id.followees_btn);
        followers_btn = findViewById(R.id.followers_btn);


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
        followees_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followees();
            }
        });
        followers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followers();
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
                .replace(R.id.fragContainer, LocationsFragment.class, bundle)
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
                .replace(R.id.fragContainer, NewLocationFragment.class, bundle)
                .commit();
    }

    public void followees() {
        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("google_id", google_id);
        bundle.putString("user_id", user_id);

        // Load Fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, FolloweesFragment.class, bundle)
                .commit();
    }
    public void followers() {
        Bundle bundle = new Bundle();

        bundle.putString("name", name);
        bundle.putString("email", email);
        bundle.putString("google_id", google_id);
        bundle.putString("user_id", user_id);

        // Load Fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragContainer, FollowersFragment.class, bundle)
                .commit();
    }

}
