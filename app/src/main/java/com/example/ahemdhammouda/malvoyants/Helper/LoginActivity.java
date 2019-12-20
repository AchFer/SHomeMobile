package com.example.ahemdhammouda.malvoyants.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ahemdhammouda.malvoyants.MainActivity;
import com.example.ahemdhammouda.malvoyants.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    EditText Email, Password;
    Button  Login;
    String email, password;
    AlertDialog.Builder builder;
    private SessionManager session;
    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Log.e("ifinstallinotherdevice", getListUser( iduser( "M.H@esprit.tn")));


        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);


        builder = new AlertDialog.Builder(this);

        Login = (Button) findViewById(R.id.btnLogin);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            //   User is already logged in.Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                email = Email.getText().toString();
                password = Password.getText().toString();
                String server_url = GlobalUrl.url + "/login/" + email + "/" + password + "";
                pDialog.setMessage("Logging in ...");

                if (!email.isEmpty() && !password.isEmpty()) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(final String response) {
                                    Log.d(TAG, "Login Response: " + response);
                                    hideDialog();
                                    builder.setTitle("Server Response");
                                    builder.setMessage("Response : " + response);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String a = "User connected !!!!";
                                            if (response.contains(a)) {
                                               session.setLogin(true);
                                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                i.putExtra("email", email);
                                                startActivity(i);
                                            }

                                        }

                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            Log.e(TAG, "Login Error: " + error.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                    );

                    MySegleton.getmInstance(LoginActivity.this).addTorequestque(stringRequest);

                } else {
                    hideDialog();
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }


        });

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public String getListUser(String id) {
        Log.e("tesssssssssssssst",id);
        String server_url = GlobalUrl.url + "/idmaison" + "/" + id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                server_url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        int count = 0;
                        while (count < response.length()) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);

                                SharedPreferences sharedPreferences = getSharedPreferences("idmaison", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", jsonObject.getString("id"));
                                editor.apply();

                                count++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR...", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySegleton.getmInstance(LoginActivity.this).addTorequestque(jsonArrayRequest);
        SharedPreferences sharedPreferences = getSharedPreferences("idmaison", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id","");
    }


    public String iduser(String mail) {
        String server_url = GlobalUrl.url + "/iduser/"+ mail;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                server_url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        int count = 0;
                        while (count < response.length()) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);

                                SharedPreferences idd = getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = idd.edit();
                                e.putString("id", jsonObject.getString("id"));
                                e.apply();

                                count++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR...", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySegleton.getmInstance(LoginActivity.this).addTorequestque(jsonArrayRequest);
        SharedPreferences ff = getSharedPreferences("user", Context.MODE_PRIVATE);
        return ff.getString("id","");
    }

}
