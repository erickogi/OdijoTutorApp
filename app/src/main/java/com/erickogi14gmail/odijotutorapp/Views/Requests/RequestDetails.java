package com.erickogi14gmail.odijotutorapp.Views.Requests;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.erickogi14gmail.odijotutorapp.Configs;
import com.erickogi14gmail.odijotutorapp.Data.Models.RequestPojo;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.MyApplication;
import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.utills.Controller;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestDetails extends AppCompatActivity implements View.OnClickListener {
    public static Fragment fragment = null;
    PrefManager prefManager;
    private View view;
    private Button buttonPhone, buttonMail, buttonAccept, buttonDecline;
    private TextView txtName, txtSubject, txtLocation, txtDate, txtTime, txtMsg;
    private String[] zones = {"Select Zone", "Westlands", "Karen", "Rongai", "Ngara"};
    private ImageView img;
    private RequestPojo requestPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonMail = findViewById(R.id.btn_mail);
        buttonPhone = findViewById(R.id.btn_phone);
        txtName = findViewById(R.id.txt_name);

        txtLocation = findViewById(R.id.txt_location);
        txtDate = findViewById(R.id.txt_date);
        txtTime = findViewById(R.id.txt_time);
        txtMsg = findViewById(R.id.txt_message);
        txtSubject = findViewById(R.id.txt_subject);
        img = findViewById(R.id.image);

        buttonAccept = findViewById(R.id.btn_accept);
        buttonDecline = findViewById(R.id.btn_decline);


        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);


        // prefManager = new PrefManager(getContext());
        // final HashMap<String, String> hashMap = prefManager.getUserDetails();
        Intent intent = getIntent();

        requestPojo = (RequestPojo) intent.getSerializableExtra("data");
        buttonMail.setText(" " + requestPojo.getStudent_email());
        buttonPhone.setText(requestPojo.getStudent_mobile());
        txtName.setText(requestPojo.getStudent_name());
        txtMsg.setText(requestPojo.getMessage());
        txtSubject.setText(" " + requestPojo.getSubject_name());
        txtLocation.setText(" " + requestPojo.getZone());
        txtTime.setText(" " + requestPojo.getTime());
        txtDate.setText(" " + requestPojo.getDate());

        setTitle(requestPojo.getStudent_name());

        Picasso.with(RequestDetails.this)
                .load(requestPojo.getStudent_image())
                .resize(140, 140)
                .centerCrop()
                .error(R.drawable.ic_person_black_24dp)
                .into(img);


        try {
            buttonPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone_iphone_black_24dp, 0, 0, 0);
            buttonMail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email_black_24dp, 0, 0, 0);
            txtLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_my_location_black_24dp, 0, 0, 0);
            txtSubject.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_import_contacts_black_24dp, 0, 0, 0);
        } catch (Exception nm) {

        }

        Picasso.with(RequestDetails.this).load(requestPojo.getStudent_image()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Palette.generateAsync(bitmap, palette -> {
                    try {
                        int bgColor = palette.getMutedColor(RequestDetails.this.getResources().getColor(R.color.primary));

                        appBarLayout.setBackgroundColor(bgColor);
                        collapsingToolbarLayout.setContentScrimColor(bgColor);
                        collapsingToolbarLayout.setStatusBarScrimColor(bgColor);
                        //toolbar.setBackgroundColor(bgColor);
                    } catch (Exception n) {

                    }


                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });


        buttonAccept.setOnClickListener(this);
        if (requestPojo.getStatus() == 1) {

            if (requestPojo.getSession_status() == 0) {
                buttonAccept.setText("Start Session");
            } else if (requestPojo.getSession_status() == 1) {
                buttonAccept.setText("End Session");
            }
            //buttonAccept.setVisibility(View.GONE);
            buttonDecline.setText("CANCEL");
            buttonDecline.setOnClickListener(this);
        } else if (requestPojo.getStatus() > 1) {
            buttonAccept.setVisibility(View.GONE);
            buttonDecline.setText("BACK");
            buttonDecline.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_accept) {

            if (buttonAccept.getText().toString().toLowerCase().contains("start")) {

                startSession();
            } else if (buttonAccept.getText().toString().toLowerCase().contains("end")) {

                endSession();

            } else {
                update(requestPojo, "1");
            }
        } else {
            if (buttonDecline.getText().toString().equals("BACK")) {
                //  update(request);
                finish();
            } else if (buttonDecline.getText().toString().equals("CANCEL")) {

                update(requestPojo, "3");

            } else if (buttonDecline.getText().toString().equals("DECLINE")) {

                update(requestPojo, "2");
            }
        }
    }

    private void startSession() {
        PrefManager prefManager = new PrefManager(RequestDetails.this);
        HashMap<String, String> de = prefManager.getUserDetails();
        final String mobile = de.get("mobile");


        final ProgressDialog loading = ProgressDialog.show(RequestDetails.this, "Updating...", "Please wait...", false, false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.REPLY_REQUEST_URL, response -> {
            try {
                JSONObject responseObj = new JSONObject(response);

                boolean s = responseObj.getBoolean("dd");
                if (s) {
                    buttonAccept.setText("End Session");
                    Controller.mytoast("Session Started", RequestDetails.this, R.drawable.ic_info_black_24dp);
                    this.finish();
                } else {
                    Controller.mytoast("Error Starting Session", RequestDetails.this, R.drawable.ic_info_black_24dp);
                }


            } catch (JSONException e) {
                loading.dismiss();
                e.printStackTrace();
                Log.d("ERRRRR", e.toString());

            }

        }, error -> {
            error.printStackTrace();
            loading.dismiss();
            finish();
            Log.e("error", "Error: " + error.getMessage());

        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("request_id", String.valueOf(requestPojo.getId()));
                params.put("tutor", prefManager.getId());
                params.put("student", requestPojo.getFrom());

                params.put("student_mobile", requestPojo.getStudent_mobile());
                params.put("tutor_mobile", prefManager.getMobileNumber());
                params.put("subject", requestPojo.getSubject_name());

                params.put("status", "1");
                params.put("session", "1");

                Log.e("posting params", "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);


    }

    private void endSession() {
        PrefManager prefManager = new PrefManager(RequestDetails.this);
        HashMap<String, String> de = prefManager.getUserDetails();
        final String mobile = de.get("mobile");


        final ProgressDialog loading = ProgressDialog.show(RequestDetails.this, "Updating...", "Please wait...", false, false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.REPLY_REQUEST_URL, response -> {
            try {
                JSONObject responseObj = new JSONObject(response);
                boolean s = responseObj.getBoolean("dd");
                if (s) {
                    buttonAccept.setText("Start Session");
                    Controller.mytoast("Session Ended", RequestDetails.this, R.drawable.ic_info_black_24dp);
                    finish();
                } else {
                    Controller.mytoast("Error Starting Session", RequestDetails.this, R.drawable.ic_info_black_24dp);
                }


            } catch (JSONException e) {

                loading.dismiss();
                e.printStackTrace();
                Log.d("ERRRRR", e.toString());

            }

        }, error -> {
            error.printStackTrace();
            loading.dismiss();
            finish();
            Log.e("error", "Error: " + error.getMessage());

        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("request_id", String.valueOf(requestPojo.getId()));
                params.put("session_id", "" + requestPojo.getCurrent_session());
                params.put("student", requestPojo.getFrom());
                params.put("status", "0");
                params.put("end_session", "1");
                params.put("student_mobile", requestPojo.getStudent_mobile());
                params.put("tutor_mobile", prefManager.getMobileNumber());
                params.put("subject", requestPojo.getSubject_name());
                Log.e("posting params", "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);

    }

    private void update(final RequestPojo requestPojo, final String status) {

        PrefManager prefManager = new PrefManager(RequestDetails.this);
        HashMap<String, String> de = prefManager.getUserDetails();
        final String mobile = de.get("mobile");


        final ProgressDialog loading = ProgressDialog.show(RequestDetails.this, "Updating...", "Please wait...", false, false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.REPLY_REQUEST_URL, response -> {
            try {
                JSONObject responseObj = new JSONObject(response);
                boolean error = responseObj.getBoolean("error");
                if (!error) {

                    loading.dismiss();
                    if (status.equals("1")) {
                        buttonDecline.setVisibility(View.GONE);
                        buttonAccept.setText("Cancel");
                    } else if (status.equals("2")) {
                        buttonAccept.setText("Declined");
                        buttonDecline.setVisibility(View.GONE);
                    } else if (status.equals("3")) {
                        buttonAccept.setText("Accept");
                        buttonDecline.setText("Decline");
                        buttonAccept.setVisibility(View.VISIBLE);
                        buttonDecline.setVisibility(View.VISIBLE);
                    }

                    try {
                        refresh refreshs = (refresh) RequestDetails.this;
                        refreshs.onRefresh();
                        this.finish();
                    } catch (Exception nm) {

                    }

                } else {
                    loading.dismiss();
                    Controller.mytoast("Error ", RequestDetails.this, R.drawable.ic_info_black_24dp);
                    String message = responseObj.getString("message");


                }


            } catch (JSONException e) {
                loading.dismiss();
                e.printStackTrace();
                Log.d("ERRRRR", e.toString());

            }

        }, error -> {
            loading.dismiss();
            error.printStackTrace();
            finish();
            Log.e("error", "Error: " + error.getMessage());

        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                params.put("request_id", String.valueOf(requestPojo.getId()));
                params.put("to", requestPojo.getStudent_mobile());
                params.put("from", mobile);
                params.put("subject", requestPojo.getSubject_name());
                params.put("status", status);

                Log.e("posting params", "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);


    }

    public interface refresh {
        void onRefresh();

    }
}
