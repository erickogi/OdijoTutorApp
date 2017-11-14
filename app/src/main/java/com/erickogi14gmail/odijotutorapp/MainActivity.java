package com.erickogi14gmail.odijotutorapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.Main.FragmentRequests;
import com.erickogi14gmail.odijotutorapp.Messaging.MessagingActivity;
import com.erickogi14gmail.odijotutorapp.Profile.FragmentMyProfile;
import com.erickogi14gmail.odijotutorapp.Receipt.FragmentReceiptList;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Fragment fragment = null;
    private QBadgeView qBadgeView;
    private AppBarLayout mAppBarLayout;
    private TextView textViewUpcoming;
    private PrefManager prefManager;
    private TextView name, email;
    private ImageView img;

    public Bitmap getThumbnail(String filename) {
        Bitmap thumnail = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_person_black_24dp);
        try {
            File filepath = this.getFileStreamPath(filename);
            FileInputStream fi = new FileInputStream(filepath);
            thumnail = BitmapFactory.decodeStream(fi);

        } catch (Exception m) {
            m.printStackTrace();
        }
        return thumnail;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager = new PrefManager(MainActivity.this);

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


        if(prefManager.isProfileSet()) {
            fragment = new FragmentRequests();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentRequests").commit();
        }else {
            fragment = new FragmentMyProfile();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentProfile").commit();
        }


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


        View header = navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        HashMap<String, String> hashMap = prefManager.getUserDetails();


        name = (TextView) header.findViewById(R.id.username);
        email = (TextView) header.findViewById(R.id.email);
        name.setText(hashMap.get("name"));
        email.setText(hashMap.get("email"));

        img = (ImageView) header.findViewById(R.id.imageView);
        String image = prefManager.getImg();
        //Toast.makeText(getContext(), ""+image, Toast.LENGTH_SHORT).show();

        if (!image.equals("null")) {
            img.setImageBitmap(getThumbnail(image));
        } else {

        }

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
            prefManager.clearSession();
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
