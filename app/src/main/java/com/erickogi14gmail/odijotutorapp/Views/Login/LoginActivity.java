package com.erickogi14gmail.odijotutorapp.Views.Login;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.Views.MainActivity;

public class LoginActivity extends AppCompatActivity {
    public static Fragment fragment = null;
    ProgressDialog    progressDialog;
    SignUpFragment signUpFragment;
    private PrefManager pref;
    private BroadcastReceiver codeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("closeoperation", "truecode received");
            if (intent.getAction().equals("com.odijo.codereceived")) {
                try {
                    // Intent hhtpIntent = new Intent(LoginActivity.this, HttpService.class);
                    // hhtpIntent.putExtra("otp", intent.getExtras().getString("code"));

                    //startService(hhtpIntent);

                    signUpFragment = (SignUpFragment) getSupportFragmentManager().findFragmentByTag("fragmentsignup");
                    signUpFragment.setText(intent.getExtras().getString("code"));


                    //signUpFragment.setText("23432");
                } catch (NullPointerException nm) {
                    nm.printStackTrace();
                }

            }

        }
    };
    private BroadcastReceiver closeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("closeoperation", "close received");

            if (intent.getAction().equals("com.odijo.close")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    //    public void otpSending(Intent grapprIntent) {
//
//        startService(grapprIntent);
//
//    }
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = new PrefManager(this);

        registerReceiver(codeReceiver, new IntentFilter("com.odijo.codereceived"));
        registerReceiver(closeReceiver, new IntentFilter("com.odijo.close"));


        fragment = new WelcomFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentWelcome").commit();
        //}
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
        fragment = new SignUpFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentsignup").commit();

        signUpFragment = (SignUpFragment) getSupportFragmentManager().findFragmentByTag("fragmentsignup");


    }

    public void loginBtnPressed(View view) {
        final ProgressDialog    progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging you in....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }

    public void newloginBtnPressed(View view) {
        popOutFragments();
        fragment = new LoginFragment();
        setUpView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(closeReceiver);

        unregisterReceiver(codeReceiver);

    }
}
