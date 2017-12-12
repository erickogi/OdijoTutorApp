package com.erickogi14gmail.odijotutorapp.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.Views.Login.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    private static int spalsh_time_out = 1000;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefManager = new PrefManager(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefManager.isLoggedIn()) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();
                }//else if(prefManager.isWaitingForSms()){

                //}
                else {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    finish();

                }
            }

        },spalsh_time_out);
    }
}
