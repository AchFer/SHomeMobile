package com.example.ahemdhammouda.malvoyants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.ahemdhammouda.malvoyants.Helper.LoginActivity;

public class SplashActivity extends Activity implements Animation.AnimationListener {
    Animation animFadeIn;
    LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("idmaison", Context.MODE_PRIVATE);
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        Log.e("maisonnnnnn",sharedPreferences.getString("id",""));
        Log.e("useeeeeeeeeer",user.getString("id",""));
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
        }
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animation_fade_in);
        // set animation listener
        animFadeIn.setAnimationListener(this);
        // animation for image
        // start the animation
        linearLayout = (LinearLayout) findViewById(R.id.layout_linear);

        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.startAnimation(animFadeIn);


    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        //under Implementation
    }

    public void onAnimationEnd(Animation animation) {
        // Start Main Screen
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //under Implementation
    }


}