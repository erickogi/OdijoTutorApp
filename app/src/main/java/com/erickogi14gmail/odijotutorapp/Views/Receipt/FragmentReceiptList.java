package com.erickogi14gmail.odijotutorapp.Views.Receipt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.erickogi14gmail.odijotutorapp.Adapters.ReceiptListAdapter;
import com.erickogi14gmail.odijotutorapp.Configs;
import com.erickogi14gmail.odijotutorapp.Data.Models.ReceiptPojo;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.MyApplication;
import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.utills.RecyclerTouchListener;
import com.erickogi14gmail.odijotutorapp.utills.StaggeredHiddingScrollListener;
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
 * Created by Eric on 9/15/2017.
 */

public class FragmentReceiptList extends Fragment {
    private View view;
    private ArrayList<ReceiptPojo> receiptPojos;
    private ReceiptListAdapter receiptListAdapter;
    private LinearLayout linearLayoutEmpty;

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private SwipeRefreshLayout swipe_refresh_layout;


    private ArrayList<ReceiptPojo> insertItems(JSONObject responseObj) {
        receiptPojos = new ArrayList<>();


        //ArrayList<ReceiptPojo> requestPojo = new ArrayList<>();
        String gsong = null;
        try {
            gsong = responseObj.getString("requests");


            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<ReceiptPojo>>() {

            }.getType();
            receiptPojos = gson.fromJson(gsong, collectionType);
            //  requestpojos = requestPojo;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intViews();
        return receiptPojos;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_receipt_list,container,false);
        // insertItems();
        // get();
        // intViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipe_refresh_layout = view.findViewById(R.id.swipeContainer);
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        swipe_refresh_layout.setBackgroundResource(android.R.color.white);
        swipe_refresh_layout.setColorSchemeResources(android.R.color.white, android.R.color.holo_purple, android.R.color.white);

        swipe_refresh_layout.setRefreshing(true);
        get();
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get();
            }
        });
    }

    private void intViews() {

        recyclerView = view.findViewById(R.id.recycler_view);
        linearLayoutEmpty = view.findViewById(R.id.empty_layout);

        receiptListAdapter = new ReceiptListAdapter(getContext(), receiptPojos);
        receiptListAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(receiptListAdapter);
        if (receiptListAdapter.getItemCount() < 1) {
            recyclerView.setVisibility(View.GONE);
            linearLayoutEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            linearLayoutEmpty.setVisibility(View.GONE);
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), ReceiptActivity.class);
                intent.putExtra("data", receiptPojos.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerView.setOnScrollListener(new StaggeredHiddingScrollListener() {

            @Override
            public void onHide() {//hideViews();
                //  f.hideRadioBtns();

            }

            @Override
            public void onShow() {//showViews();
                // f.showRadioBtns();


            }
        });
    }

    private void get() {
        PrefManager prefManager = new PrefManager(getContext());
        HashMap<String, String> de = prefManager.getUserDetails();
        final String mobile = de.get("mobile");


        //final ProgressDialog loading = ProgressDialog.show(RequestDetails.this, "Updating...", "Please wait...", false, false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.SESSIONS, response -> {
            try {
                JSONObject responseObj = new JSONObject(response);

                insertItems(responseObj);

                if (swipe_refresh_layout.isRefreshing()) {
                    swipe_refresh_layout.setRefreshing(false);
                }


            } catch (JSONException e) {
                //loading.dismiss();
                e.printStackTrace();
                Log.d("ERRRRR", e.toString());

            }

        }, error -> {
            error.printStackTrace();
            // loading.dismiss();
            // finish();
            Log.e("error", "Error: " + error.getMessage());

        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("tutor_id", prefManager.getId());


                Log.e("posting params", "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);


    }

}
