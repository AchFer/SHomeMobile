package com.example.ahemdhammouda.malvoyants;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ahemdhammouda.malvoyants.Helper.GlobalUrl;
import com.example.ahemdhammouda.malvoyants.Helper.LoginActivity;
import com.example.ahemdhammouda.malvoyants.Helper.MySegleton;
import com.example.ahemdhammouda.malvoyants.Helper.SQLiteHandlernoeud;
import com.example.ahemdhammouda.malvoyants.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = BainActivity.class.getSimpleName();
    private SQLiteHandlernoeud db;
    private SessionManager session;
    private ProgressDialog pDialog;
    AlertDialog.Builder builder;
    TextView gaz,hum;
    Handler handler = new Handler();
    Runnable timedTask = new Runnable() {

        @Override
        public void run() {
            repeat();
            handler.postDelayed(timedTask, 1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler.post(timedTask);

        db = new SQLiteHandlernoeud(getApplicationContext());
        session = new SessionManager(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getNodeValues();
        gaz = (TextView) findViewById(R.id.gaz);
        hum = (TextView) findViewById(R.id.hum);

        repeat();
    }

    public void repeat(){
        getNodeValues();
        SharedPreferences shaaa = getSharedPreferences("bainvalues", Context.MODE_PRIVATE);
        gaz.setText(shaaa.getString("gaz",""));
        hum.setText(shaaa.getString("humidity",""));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            db.deleteUsers();
            SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor u = user.edit();
            u.clear();
            u.apply();
            SharedPreferences sharedPreferences = getSharedPreferences("idmaison", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            SharedPreferences shaaa = getSharedPreferences("salonvalue", Context.MODE_PRIVATE);
            SharedPreferences.Editor ediii = shaaa.edit();
            ediii.clear();
            ediii.apply();
            SharedPreferences cui = getSharedPreferences("cuisinevalue", Context.MODE_PRIVATE);
            SharedPreferences.Editor cuii = cui.edit();
            cuii.clear();
            cuii.apply();
            SharedPreferences bain = getSharedPreferences("bainvalues", Context.MODE_PRIVATE);
            SharedPreferences.Editor b = bain.edit();
            b.clear();
            b.apply();
            session.setLogin(false);
            Intent i = new Intent(BainActivity.this, LoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            sendrec();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public List getNodeValues() {
        String server_url = GlobalUrl.url2 + "/getnode/" + getIntent().getStringExtra("bain");
        final SharedPreferences bain = getSharedPreferences("bainvalues", MODE_PRIVATE);
        final SharedPreferences.Editor bai = bain.edit();
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
                                JSONObject jsonObject2 = jsonObject.getJSONObject("capteur");

                                if (jsonObject2.getString("nomCapteur").contains("tag ref ")) {
                                    // jsonObject.getString("content");
                                    Log.e("tag ", jsonObject.getString("content"));

                                    bai.putString("tag", jsonObject.getString("content"));
                                    bai.apply();

                                }
                                if (jsonObject2.getString("nomCapteur").contains("couleur")) {
                                    // jsonObject.getString("content");
                                    Log.e("color", jsonObject.getString("content"));
                                    bai.putString("couleur", jsonObject.getString("content"));
                                    bai.apply();

                                }
                                if (jsonObject2.getString("nomCapteur").contains("lumonisité")) {
                                    // jsonObject.getString("content");
                                    Log.e("lumonisité ", jsonObject.getString("content"));
                                    bai.putString("luminosite", jsonObject.getString("content"));
                                    bai.apply();

                                }
                                if (jsonObject2.getString("nomCapteur").contains("humidity")) {
                                    // jsonObject.getString("content");
                                    Log.e("humid ", jsonObject.getString("content"));
                                    bai.putString("humidity", jsonObject.getString("content"));
                                    bai.apply();

                                }
                                if (jsonObject2.getString("nomCapteur").contains("temperature")) {
                                    // jsonObject.getString("content");
                                    Log.e("temp", jsonObject.getString("content"));
                                    bai.putString("temperature", jsonObject.getString("content"));
                                    bai.apply();

                                }
                                if (jsonObject2.getString("nomCapteur").contains("gaz")) {
                                    // jsonObject.getString("content");
                                    Log.e("gaz", jsonObject.getString("content"));
                                    bai.putString("gaz",jsonObject.getString("content"));
                                    bai.apply();

                                }
                                if (jsonObject2.getString("nomCapteur").contains("flame")) {
                                    // jsonObject.getString("content");
                                    Log.e("flame", jsonObject.getString("content"));
                                    bai.putString("flame",jsonObject.getString("content"));
                                    bai.apply();

                                }

                                count++;
                                Log.e("counttt", String.valueOf(count));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        Log.e("aaaaaaaaaaaaa", "les valeursrrrrrrrrrrrrrrrrrr :  " + "temp = " + bain.getString("temperature", "") + "gaz = " +  bain.getString("gaz", "") + "lum =" + bain.getString("luminosite", "") + "hum=" + bain.getString("humidity", "") + "tga=" + bain.getString("tag", "") + "col=" +  bain.getString("couleur", "")+ "fla=" +  bain.getString("flame", ""));



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR...", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        MySegleton.getmInstance(BainActivity.this).addTorequestque(jsonArrayRequest);

        return null;
    }

    public void sendrec ()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        builder = new AlertDialog.Builder(this);
        SharedPreferences idd = getSharedPreferences("user", Context.MODE_PRIVATE);
        String iduser = idd.getString("id","");
        String server_url = GlobalUrl.url + "/adreclamation/" + iduser + "/" + "Bain problem" + "/" + "Problem detected in bathroom" + "";
        String str = server_url.replaceAll(" ", "%20");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, str,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideDialog();
                        Log.d(TAG, "Register Response: " + response.toString());
                        //   hideDialog();
                        builder.setTitle("Server Response");
                        builder.setMessage("Response : " + response);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }

                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }
        );

        MySegleton.getmInstance(BainActivity.this).addTorequestque(stringRequest);



    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

