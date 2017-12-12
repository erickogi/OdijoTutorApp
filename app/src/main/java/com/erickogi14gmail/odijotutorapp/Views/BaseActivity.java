package com.erickogi14gmail.odijotutorapp.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;

import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.Views.MyProfile.FragmentMyProfile;
import com.erickogi14gmail.odijotutorapp.Views.Receipt.FragmentReceiptList;
import com.erickogi14gmail.odijotutorapp.Views.Support.FragmentAbout;
import com.erickogi14gmail.odijotutorapp.Views.Support.FragmentSupport;

public class BaseActivity extends AppCompatActivity {
    public static Fragment fragment = null;

    private void setupWindowAnimations() {
        Fade fade = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fade = new Fade();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fade.setDuration(1000);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
        }

        Slide slide = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slide.setDuration(1000);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setReturnTransition(slide);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setupWindowAnimations();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setLogo(R.drawable.ic_school_icon100dp);

        Intent intent = getIntent();


        int id = intent.getIntExtra("id", 0);


        switch (id) {
            case 1:
                fragment = new FragmentAbout();
                this.setTitle("About");

                popOutFragments();
                setUpView();
                break;

            case 2:
                fragment = new FragmentSupport();
                this.setTitle("Support");

                popOutFragments();
                setUpView();

                break;

            case 3:


                fragment = new FragmentMyProfile();
                this.setTitle("Support");

                // popOutFragments();
                //setUpView();
                popOutFragments();
                setUpView();

                break;

            case 4:

                fragment = new FragmentReceiptList();
                this.setTitle("Receipts");
                popOutFragments();
                setUpView();
                break;

            case 5:


                break;

            case 6:

                break;

            case 7:

                break;

        }
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

    @Override
    public void onBackPressed() {
        finish();
    }


}
