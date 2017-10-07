package com.erickogi14gmail.odijotutorapp.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.erickogi14gmail.odijotutorapp.MainActivity;
import com.erickogi14gmail.odijotutorapp.R;

public class LoginActivity extends AppCompatActivity {
    public static Fragment fragment = null;
    ProgressDialog    progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fragment = new WelcomFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentWelcome").commit();
    }

    void setUpView() {
        if (fragment != null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                    .addToBackStack(null).commit();
        }

    }

    void popOutFragments() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }

    public void newAccountBtnPressed(View view) {
        popOutFragments();
        fragment=new SignUpFragment();
        setUpView();

    }

    public void loginBtnPressed(View view) {
        final ProgressDialog    progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging you in....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();;
                SharedPreferences sharedPreferences = getSharedPreferences("odijotutorloginStatus", Context.MODE_PRIVATE);


                SharedPreferences.Editor editor = sharedPreferences.edit();


                editor.putBoolean("loginStaus", true);



                editor.commit();


                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

            }

        },1000);









    }

    public void newloginBtnPressed(View view) {
        popOutFragments();
        fragment=new LoginFragment();
        setUpView();
    }
}
