package com.erickogi14gmail.odijotutorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.erickogi14gmail.odijotutorapp.Login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private static int spalsh_time_out = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("odijotutorloginStatus", Context.MODE_PRIVATE);


                boolean isLoggedIn= sharedPreferences.getBoolean("loginStaus", false);
                if(isLoggedIn) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                    finish();
                }
            }

        },spalsh_time_out);
    }
}
