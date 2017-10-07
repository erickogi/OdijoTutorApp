package com.erickogi14gmail.odijotutorapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.Main.FragmentRequests;
import com.erickogi14gmail.odijotutorapp.Messaging.MessagingActivity;
import com.erickogi14gmail.odijotutorapp.Profile.FragmentMyProfile;
import com.erickogi14gmail.odijotutorapp.Receipt.FragmentReceiptList;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Fragment fragment = null;
    private QBadgeView qBadgeView;
    private AppBarLayout mAppBarLayout;
    private TextView textViewUpcoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_school_icon100dp);
        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.hide();
        textViewUpcoming=(TextView)findViewById(R.id.txt_upcoming_courses);
        qBadgeView=new QBadgeView(getApplicationContext());
        textViewUpcoming.setVisibility(View.VISIBLE);

        qBadgeView.bindTarget(textViewUpcoming).setBadgeNumber(4).setBadgeGravity( Gravity.CENTER| Gravity.END).setBadgeBackgroundColor(this.getResources().getColor(R.color.colorPrimaryP));



        setSupportActionBar(toolbar);

        this.getSupportActionBar().show();

        mAppBarLayout=(AppBarLayout)findViewById(R.id.mAppBarLayout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAppBarLayout.setElevation(0);
        }

        fragment = new FragmentRequests();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentRequests").commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.nav_profile){
            fragment=new FragmentMyProfile();
            // popOutFragments();
            setUpView();
        }
else if(id==R.id.nav_messages){

        startActivity(new Intent(MainActivity.this, MessagingActivity.class));

}else if (id == R.id.nav_payments) {
    fragment = new FragmentReceiptList();
    popOutFragments();
    setUpView();
}
else if (id == R.id.nav_home) {
    // fragment = new FragmentCourses();
    popOutFragments();
    // setUpView();
}else if (id == R.id.nav_logout) {
    SharedPreferences sharedPreferences = getSharedPreferences("odijotutorloginStatus", Context.MODE_PRIVATE);


    SharedPreferences.Editor editor = sharedPreferences.edit();


    editor.putBoolean("loginStaus", false);


    editor.commit();
    startActivity(new Intent(MainActivity.this, SplashScreen.class));
    finish();

}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void goBack(View view) {
        popOutFragments();
        // fragment = new FragmentCourses();
        // FragmentManager fragmentManager = getSupportFragmentManager();
        // fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentMain").commit();

    }
}
