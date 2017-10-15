package com.erickogi14gmail.odijotutorapp.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.erickogi14gmail.odijotutorapp.Configs;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.MyApplication;
import com.erickogi14gmail.odijotutorapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 9/27/2017.
 */

public class FragmentRequests extends Fragment {
    public static Fragment fragment = null;
    SwipeRefreshLayout swipe_refresh_layout;
    private View view;
    private RequestPojo requestpojo;
    private ArrayList<RequestPojo> requestpojos;
    private RequestAdapter requestAdapter;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_request,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //populate();

        // initViews();
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.setRefreshing(true);
                populate();
                //getRecyclerView_sources(getActivity());

            }
        });
        populate();

        return view;
    }

    private ArrayList<RequestPojo> populate() {
        requestpojos=new ArrayList<>();
        PrefManager prefManager = new PrefManager(getContext());
        HashMap<String, String> de = prefManager.getUserDetails();
        final String moblie = de.get("mobile");
        //  final ProgressDialog loading = ProgressDialog.show(getContext(),"Loading...","Please wait...",false,false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.GET_ALL_REQUESTS_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // Log.d("response", response.toString());
                //progressDialog.dismiss();
                // dialoge.show();
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
                        if (swipe_refresh_layout.isRefreshing()) {
                            swipe_refresh_layout.setRefreshing(false);
                        }

                        initViews();


                        //Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                    } else {
                        // loading.dismiss();
                        if (swipe_refresh_layout.isRefreshing()) {
                            swipe_refresh_layout.setRefreshing(false);
                        }
                        String message = responseObj.getString("message");
                        //relativeLayoutSignup.setVisibility(View.VISIBLE);
                        //relativeLayoutOtp.setVisibility(View.GONE);
                        Toast.makeText(getContext(),
                                "Error: " + message,
                                Toast.LENGTH_LONG).show();
                        // dialoge.dismiss();

                    }

                    // hiding the progress bar
                    // progressDialog.dismiss();


                } catch (JSONException e) {
                    // loading.dismiss();
                    if (swipe_refresh_layout.isRefreshing()) {
                        swipe_refresh_layout.setRefreshing(false);
                    }

                    e.printStackTrace();
                    Log.d("ERRRRR", e.toString());
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    // dialoge.dismiss();
                    //relativeLayoutSignup.setVisibility(View.VISIBLE);
                    // relativeLayoutOtp.setVisibility(View.GONE);
                    // progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // loading.dismiss();
                //relativeLayoutSignup.setVisibility(View.VISIBLE);
                //relativeLayoutOtp.setVisibility(View.GONE);
                if (swipe_refresh_layout.isRefreshing()) {
                    swipe_refresh_layout.setRefreshing(false);
                }
                Log.e("error", "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
                // dialoge.dismiss();
                //progressBar.setVisibility(View.GONE);
            }
        }) {

            /**
             * Passing user parameters to our server
             *
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile", moblie);


                Log.e("posting params", "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);

        return requestpojos;
    }
    private void initViews(){

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        requestAdapter =new RequestAdapter(getContext(), requestpojos, new ClickListenerRequestCard() {
            @Override
            public void onCardClicked(int position) {



            }

            @Override
            public void onBtnAcceptClicked(int position, Button A, Button B) {
                //  alertDialog(true,"You are about to accept this request\n Continue ? ",position);
                fragment = new RequestDetails();
                Bundle args = new Bundle();
                args.putSerializable("data", requestpojos.get(position));
                fragment.setArguments(args);
                popOutFragments();
                setUpView();


            }

            @Override
            public void onBtnDeclineClicked(int position, Button B, Button A) {
                // alertDialog(false,"You are about to decline this request\n Continue ? ",position);


            }

            @Override
            public void onClientImageClicked(int position) {

            }

            @Override
            public void onIconMoreClicked(int posotion, View v) {


            }
        });
        requestAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);

        try {
            final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.hide();

        } catch (Exception nm) {

        }


    }
//    private void alertDialog(final boolean s, final String message, final int pos){
//        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE:
//                        if(s) {
//                            requestpojos.get(pos).setStatus(1);
//                            requestAdapter.updateList(requestpojos);
//                        }else {
//                            requestpojos.get(pos).setStatus(2);
//                            requestAdapter.updateList(requestpojos);
//                        }
//
//
//                        break;
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        dialog.dismiss();
//
//                        break;
//                }
//            }
//        };
//
//
//
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
//            builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
//                    .setNegativeButton("No", dialogClickListener).show();
//
//    }


    void setUpView() {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                    .addToBackStack(null).commit();
        }

    }

    void popOutFragments() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }

}
