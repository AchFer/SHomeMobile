package com.example.ahemdhammouda.malvoyants;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ahemdhammouda.malvoyants.Helper.GlobalUrl;
import com.example.ahemdhammouda.malvoyants.Helper.LoginActivity;
import com.example.ahemdhammouda.malvoyants.Helper.MySegleton;
import com.example.ahemdhammouda.malvoyants.Helper.SQLiteHandlernoeud;
import com.example.ahemdhammouda.malvoyants.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    public SQLiteHandlernoeud db;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iduser();

        db = new SQLiteHandlernoeud(getApplicationContext());
        linearLayout1 = findViewById(R.id.cuisine);
        linearLayout2 = findViewById(R.id.salon);
        linearLayout3 = findViewById(R.id.saledebain);
        session = new SessionManager(getApplicationContext());

        Log.e("salonextra", db.getsalid());
        Log.e("cuisineextra", db.getcuisid());
        Log.e("bainextra", db.getbainid());

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, CuisineActivity.class);
                i.putExtra("cui", db.getcuisid());
                startActivity(i);
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SalonnActivity.class);
                i.putExtra("sal", db.getsalid());
                startActivity(i);
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, BainActivity.class);
                i.putExtra("bain", db.getbainid());
                startActivity(i);
            }
        });

    }


    public String idmaison(String a) {
        SharedPreferences idd = getSharedPreferences("user", Context.MODE_PRIVATE);

      //  String server_url = GlobalUrl.url + "/idmaison" + "/" + idd.getString("id", "");
        String server_url = GlobalUrl.url + "/idmaison" + "/" + a;
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
                                Log.e("nnnnnnnnnnn","fff");
                                SharedPreferences sharedPreferences = getSharedPreferences("idmaison", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", jsonObject.getString("id"));
                                editor.apply();
                                Log.e("naaanzananana",sharedPreferences.getString("id",""));
                                Log.e("bgbgbgbgb","fff");
                                getidnoeud(sharedPreferences.getString("id",""));
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
        MySegleton.getmInstance(MainActivity.this).addTorequestque(jsonArrayRequest);
        SharedPreferences sharedPreferences = getSharedPreferences("idmaison", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }


    public String iduser() {
        String server_url = GlobalUrl.url + "/iduser/" + getIntent().getStringExtra("email");
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
                                e.commit();
                                Log.e("zaaaaaaaaaaaaaab",jsonObject.getString("id"));
                                Log.e("zaaaaaaav",idd.getString("id",""));
                                idmaison(jsonObject.getString("id"));
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
        MySegleton.getmInstance(MainActivity.this).addTorequestque(jsonArrayRequest);
        SharedPreferences ff = getSharedPreferences("user", Context.MODE_PRIVATE);
        Log.e("zeeeeee","fff");
        Log.e("btttttttttttttt",ff.getString("id", ""));
        Log.e("ziiiiii","ccc");
        return ff.getString("id", "");
    }


    public int getidnoeud(String b ) {
        SharedPreferences sharedPreferences = getSharedPreferences("idmaison", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        Log.e("sharedpreferenceid", id);
        String server_url = GlobalUrl.url + "/idnoeud" + "/" + b;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                server_url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        String cuisine = "", salon = "", bain = "";
                        int count = 0;
                        while (count < response.length()) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);

                                if (jsonObject.getString("NomNoeud").contains("cuisine")) {
                                    cuisine = jsonObject.getString("id");
                                }
                                if (jsonObject.getString("NomNoeud").contains("salon")) {
                                    salon = jsonObject.getString("id");

                                }
                                if (jsonObject.getString("NomNoeud").contains("bain")) {
                                    bain = jsonObject.getString("id");

                                }
                                count++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        //  Log.e("cuiiiii", cuisine);
                        //  Log.e("salll", salon);
                        //   Log.e("bainnnnn", bain);
                        db.addUser(cuisine, salon, bain);

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR...", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySegleton.getmInstance(MainActivity.this).addTorequestque(jsonArrayRequest);
        return 1;
    }

}
