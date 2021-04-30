package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    public GoogleSignInClient mGoogleSignInClient;
    private String BASE_URL = "http://10.0.2.2/auth";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        // Register button's on click listener
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);

    }

    public void updateUI( GoogleSignInAccount accountInput, String user_id) {
        if (accountInput != null) {
            // user already signed in
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("name", accountInput.getDisplayName());
            intent.putExtra("email", accountInput.getEmail());
            intent.putExtra("google_id", accountInput.getId());
            intent.putExtra("user_id", user_id);
            startActivity(intent);
        } else {
            // Should stay in page (maybe refresh?)
            Log.d("error:", "in updatdateUI null for account");
        }
    }
    
    private void signIn() {
        // 1. change to home Activity
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Send info to backend
            String name = account.getDisplayName();
            String email = account.getEmail();
            String google_id = account.getId();

            // Send POST request to Backend
            RequestParams params = new RequestParams();
            params.put("name", name);
            params.put("email", email);
            params.put("google_id", google_id);

            Log.d("PRE POST", "HERE");
            client.post(BASE_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("POST REQUEST", "SUCCESS");
                    // POST request successful
                    try {
                        JSONObject responseObj = new JSONObject( new String(responseBody));
                        JSONObject responseBodyObject = responseObj.getJSONObject("body");
                        int user_id = responseBodyObject.getInt("id");

                        updateUI(account, Integer.toString(user_id));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    Log.e("api error", new String(responseBody));
                    updateUI(null, null);
                }
            });

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("signInResult:failed", "error in handleSignInResult");
            updateUI(null, null);
        }
    }
}
