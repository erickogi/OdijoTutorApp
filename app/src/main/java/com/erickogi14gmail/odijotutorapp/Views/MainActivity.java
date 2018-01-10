package com.erickogi14gmail.odijotutorapp.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.erickogi14gmail.odijotutorapp.Configs;
import com.erickogi14gmail.odijotutorapp.Data.Models.RequestPojo;
import com.erickogi14gmail.odijotutorapp.Data.Network.DumbVolleyRequest;
import com.erickogi14gmail.odijotutorapp.Data.Network.RequestListener;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.Interfaces.DrawerItemListener;
import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.Views.Requests.FragmentActive;
import com.erickogi14gmail.odijotutorapp.Views.Requests.FragmentHistory;
import com.erickogi14gmail.odijotutorapp.Views.Requests.FragmentNew;
import com.erickogi14gmail.odijotutorapp.Views.Requests.RequestDetails;
import com.erickogi14gmail.odijotutorapp.utills.TutorDrawerUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity implements FragmentActive.DataChangedListener, RequestDetails.refresh {
    public static Fragment fragment = null;
    FragmentActive fragmentActive;
    FragmentNew fragmentNew;
    FragmentHistory fragmentHistory;
    Fragment fragmentNewRequest;
    Fragment fragmentActiveRequest;
    Fragment fragmentHistoryRequest;
    ViewPagerAdapter adapter;
    ArrayList<RequestPojo> newRequest;
    ArrayList<RequestPojo> activeRequest;
    ArrayList<RequestPojo> historyRequest;

    private QBadgeView qBadgeView;
    private AppBarLayout mAppBarLayout;
    private TextView textViewUpcoming;
    private PrefManager prefManager;
    private TextView name, email;
    private ImageView img;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private SwipeRefreshLayout swipe_refresh_layout;
    private ArrayList<RequestPojo> requestpojos;
    private DumbVolleyRequest dumbVolleyRequest;


    private void setupWindowAnimationss() {
        Slide slide = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slide = new Slide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            slide.setDuration(1000);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(slide);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dumbVolleyRequest = new DumbVolleyRequest();
        setupWindowAnimationss();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //setTitle("Odijo");


        prefManager = new PrefManager(MainActivity.this);


        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.ic_school_icon100dp);
        // toolbar.setLogoDescription("Odijo");
        // textViewUpcoming= findViewById(R.id.txt_upcoming_courses);
        qBadgeView=new QBadgeView(getApplicationContext());
        //      textViewUpcoming.setVisibility(View.GONE);
        //qBadgeView.bindTarget(textViewUpcoming).setBadgeNumber(4).setBadgeGravity( Gravity.CENTER| Gravity.END).setBadgeBackgroundColor(this.getResources().getColor(R.color.colorPrimaryP));
        viewPager = findViewById(R.id.viewpager);


        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        swipe_refresh_layout = findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populate();
            }
        });




        setSupportActionBar(toolbar);

        this.getSupportActionBar().show();

        mAppBarLayout = findViewById(R.id.mAppBarLayout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mAppBarLayout.setElevation(0);
        }



        HashMap<String, String> hashMap = prefManager.getUserDetails();
        String image = prefManager.getImg();
        TutorDrawerUtil.getDrawer(MainActivity.this, toolbar, 1, hashMap, image, new DrawerItemListener() {
            Intent intent = new Intent(MainActivity.this, BaseActivity.class);

            @Override
            public void homeClicked() {


            }

            @Override
            public void messageClicked() {

                //  startActivity(new Intent(MainActivity.this,MessagingActivity.class));
            }

            @Override
            public void favoritesClicked() {


            }

            @Override
            public void paymentsClicked() {


                intent.putExtra("id", 4);
                startActivity(intent);

            }

            @Override
            public void profileClicked() {
                intent.putExtra("id", 3);
                startActivity(intent);
            }

            @Override
            public void settingsClicked() {

            }

            @Override
            public void logOutClicked() {
                prefManager.clearSession();
                startActivity(new Intent(MainActivity.this, SplashScreen.class));
                finish();
            }

            @Override
            public void helpClicked() {
                intent.putExtra("id", 2);
                startActivity(intent);
            }

            @Override
            public void aboutClicked() {
                intent.putExtra("id", 1);
                startActivity(intent);
            }

            @Override
            public void upcomingClicked() {

            }
        });
        // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.hide();
        populate();
    }

    private void filter() {
        newRequest = new ArrayList<>();
        activeRequest = new ArrayList<>();
        historyRequest = new ArrayList<>();


        for (RequestPojo requestPojo : requestpojos) {
            if (requestPojo.getStatus() == 0) {
                newRequest.add(requestPojo);
            } else if (requestPojo.getStatus() == 1) {
                activeRequest.add(requestPojo);
            } else {
                historyRequest.add(requestPojo);
            }
        }
        update();


    }

    private void update() {
        fragmentActive = (FragmentActive) getSupportFragmentManager().findFragmentByTag(getFragmentTag(1));
        fragmentNew = (FragmentNew) getSupportFragmentManager().findFragmentByTag(getFragmentTag(0));
        fragmentHistory = (FragmentHistory) getSupportFragmentManager().findFragmentByTag(getFragmentTag(2));


        try {
            fragmentNew.updateList(newRequest);
            fragmentActive.updateList(activeRequest);
            fragmentHistory.updateList(historyRequest);

        } catch (Exception nm) {

        }
        //fragmentActiveRequest.
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        fragmentNewRequest = new FragmentNew();
        fragmentActiveRequest = new FragmentActive();
        fragmentHistoryRequest = new FragmentHistory();
        Bundle activebundle = new Bundle();
        activebundle.putSerializable("data", activeRequest);
        Bundle newBundle = new Bundle();
        newBundle.putSerializable("data", newRequest);
        Bundle historyBundle = new Bundle();
        historyBundle.putSerializable("data", historyRequest);

        fragmentActiveRequest.setArguments(activebundle);
        fragmentHistoryRequest.setArguments(historyBundle);
        fragmentNewRequest.setArguments(newBundle);

        adapter.addFragment(fragmentNewRequest, "lo");
        adapter.addFragment(fragmentActiveRequest, "li");
        adapter.addFragment(fragmentHistoryRequest, "li");


        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setText("New");
        tabLayout.getTabAt(1).setText("Active");
        tabLayout.getTabAt(2).setText("History");


    }

    @Override
    public void onSelected(ArrayList<RequestPojo> requestpojos) {


    }

    @Override
    public void onRefresh() {
        populate();
    }

    private String getFragmentTag(int pos) {
        return "android:switcher:" + R.id.viewpager + ":" + pos;
    }

    private ArrayList<RequestPojo> populate() {
        requestpojos = new ArrayList<>();
        PrefManager prefManager = new PrefManager(MainActivity.this);
        HashMap<String, String> de = prefManager.getUserDetails();
        final String moblie = de.get("mobile");
        //  final ProgressDialog loading = ProgressDialog.show(getContext(),"Loading...","Please wait...",false,false);
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", moblie);


        dumbVolleyRequest.getPostData(Configs.GET_ALL_REQUESTS_URL, params, new RequestListener() {
            @Override
            public void onError(VolleyError error) {
                swipe_refresh_layout.setRefreshing(false);
                filter();
                Log.e("error", "Error: " + error.getMessage());
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject responseObj = new JSONObject(response);

                    // Parsing json object response
                    // response will be a json object
                    boolean error = responseObj.getBoolean("error");
                    //  String message = responseObj.getString("message");

                    // checking for error, if not error SMS is initiated
                    // device should receive it shortly
                    if (!error) {

                        // loading.dismiss();
                        ArrayList<RequestPojo> requestPojo = new ArrayList<>();
                        String gsong = responseObj.getString("requests");


                        Gson gson = new Gson();
                        Type collectionType = new TypeToken<Collection<RequestPojo>>() {

                        }.getType();
                        requestPojo = gson.fromJson(gsong, collectionType);
                        requestpojos = requestPojo;

                        Log.d("dataloades", "" + requestPojo.toString());
                        swipe_refresh_layout.setRefreshing(false);
                        filter();


                    } else {
                        // loading.dismiss();
                        swipe_refresh_layout.setRefreshing(false);
                        filter();
                        // String message = responseObj.getString("message");


                    }


                } catch (JSONException e) {
                    swipe_refresh_layout.setRefreshing(false);

                    filter();
                    e.printStackTrace();
                    Log.d("ERRRRR", e.toString());

                }
            }
        });
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                Configs.GET_ALL_REQUESTS_URL, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    JSONObject responseObj = new JSONObject(response);
//
//                    // Parsing json object response
//                    // response will be a json object
//                    boolean error = responseObj.getBoolean("error");
//                    //  String message = responseObj.getString("message");
//
//                    // checking for error, if not error SMS is initiated
//                    // device should receive it shortly
//                    if (!error) {
//
//                        // loading.dismiss();
//                        ArrayList<RequestPojo> requestPojo = new ArrayList<>();
//                        String gsong = responseObj.getString("requests");
//
//
//                        Gson gson = new Gson();
//                        Type collectionType = new TypeToken<Collection<RequestPojo>>() {
//
//                        }.getType();
//                        requestPojo = gson.fromJson(gsong, collectionType);
//                        requestpojos = requestPojo;
//
//                        Log.d("dataloades", "" + requestPojo.toString());
//                        swipe_refresh_layout.setRefreshing(false);
//                        filter();
//
//
//                    } else {
//                        // loading.dismiss();
//                        swipe_refresh_layout.setRefreshing(false);
//                        String message = responseObj.getString("message");
//
//
//                    }
//
//
//                } catch (JSONException e) {
//                    swipe_refresh_layout.setRefreshing(false);
//
//                    e.printStackTrace();
//                    Log.d("ERRRRR", e.toString());
//
//                }
//
//            }
//        }, error -> {
//            // loading.dismiss();
//            //relativeLayoutSignup.setVisibility(View.VISIBLE);
//            //relativeLayoutOtp.setVisibility(View.GONE);
//            swipe_refresh_layout.setRefreshing(false);
//            Log.e("error", "Error: " + error.getMessage());
//
//        }) {
//
//            /**
//             * Passing user parameters to our server
//             *
//             * @return
//             */
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("mobile", moblie);
//
//
//                Log.e("posting params", "Posting params: " + params.toString());
//
//                return params;
//            }
//
//        };
//
//        // Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(strReq);

        return requestpojos;
    }

    @Override
    protected void onResume() {
        super.onResume();
        populate();
    }


    public interface onDataChanged {
        void onData(ArrayList<RequestPojo> requestPojos);

    }

    public interface update {
        void update();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }




}
