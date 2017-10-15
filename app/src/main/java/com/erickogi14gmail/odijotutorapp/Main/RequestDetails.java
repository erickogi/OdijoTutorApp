package com.erickogi14gmail.odijotutorapp.Main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.erickogi14gmail.odijotutorapp.Configs;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.MyApplication;
import com.erickogi14gmail.odijotutorapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 10/15/2017.
 */

public class RequestDetails extends Fragment {
    public static Fragment fragment = null;
    PrefManager prefManager;
    private View view;
    private Button buttonPhone, buttonMail, buttonAccept, buttonDecline;
    private TextView txtName, txtSubject, txtLocation, txtDate, txtTime, txtMsg;
    private String[] zones = {"Select Zone", "Westlands", "Karen", "Rongai", "Ngara"};
    private ImageView img;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_request_details, container, false);
        buttonMail = (Button) view.findViewById(R.id.btn_mail);
        buttonPhone = (Button) view.findViewById(R.id.btn_phone);
        txtName = (TextView) view.findViewById(R.id.txt_name);

        txtLocation = (TextView) view.findViewById(R.id.txt_location);
        txtDate = (TextView) view.findViewById(R.id.txt_date);
        txtTime = (TextView) view.findViewById(R.id.txt_time);
        txtMsg = (TextView) view.findViewById(R.id.txt_message);
        txtSubject = (TextView) view.findViewById(R.id.txt_subject);
        img = (ImageView) view.findViewById(R.id.imageView);

        buttonAccept = (Button) view.findViewById(R.id.btn_accept);
        buttonDecline = (Button) view.findViewById(R.id.btn_decline);


        // prefManager = new PrefManager(getContext());
        // final HashMap<String, String> hashMap = prefManager.getUserDetails();
        final RequestPojo requestPojo = (RequestPojo) getArguments().getSerializable("data");
        buttonMail.setText(" " + requestPojo.getStudent_email());
        buttonPhone.setText(requestPojo.getStudent_mobile());
        txtName.setText(requestPojo.getStudent_name());
        txtMsg.setText(requestPojo.getMessage());
        txtSubject.setText(" " + requestPojo.getSubject_name());
        txtLocation.setText(" " + requestPojo.getZone());
        txtTime.setText(" " + requestPojo.getTime());
        txtDate.setText(" " + requestPojo.getDate());
        Picasso.with(getContext()).load(requestPojo.getStudent_image()).resize(140, 140).centerCrop().error(R.drawable.ic_person_black_24dp).into(img);

        if (requestPojo.getStatus() == 1) {
            buttonAccept.setVisibility(View.GONE);
            buttonDecline.setText("Cancel Class");
        }


        try {
            buttonPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone_iphone_black_24dp, 0, 0, 0);
            buttonMail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email_black_24dp, 0, 0, 0);
            txtLocation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_my_location_black_24dp, 0, 0, 0);
            txtSubject.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_import_contacts_black_24dp, 0, 0, 0);
            txtDate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_today_black_24dp, 0, 0, 0);
            txtTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_access_time_black_24dp, 0, 0, 0);

        } catch (Exception nm) {

        }
        buttonDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(requestPojo, "2");
            }
        });
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonAccept.getText().toString().equals("Cancel")) {
                    update(requestPojo, "3");
                } else {
                    update(requestPojo, "1");
                }

            }
        });
        return view;
    }

    private void update(final RequestPojo requestPojo, final String status) {
//        $id = $_POST['request_id'];
//        $to = $_POST['to'];
//        $from = $_POST['from'];
//        $subject = $_POST['subject'];
//        $status=$_POST['status'];
        PrefManager prefManager = new PrefManager(getContext());
        HashMap<String, String> de = prefManager.getUserDetails();
        final String mobile = de.get("mobile");


        final ProgressDialog loading = ProgressDialog.show(getContext(), "Updating...", "Please wait...", false, false);

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.REPLY_REQUEST_URL, new Response.Listener<String>() {

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

                        //prefManager.updateUser(name,email,imagePath,zone,description,work_hours);
                        // popOutFragments();

                        // boolean flag saying device is waiting for sms

                        //pref.setIsWaitingForSms(true);
                        // progressDialog.dismiss();
                        // moving the screen to next pager item i.e otp screen
                        //dialoge.show();


                        // Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                    } else {
                        loading.dismiss();
                        String message = responseObj.getString("message");
                        //relativeLayoutSignup.setVisibility(View.VISIBLE);
                        //relativeLayoutOtp.setVisibility(View.GONE);
                        //  Toast.makeText(getContext(),
                        //         "Error: " + message,
                        //         Toast.LENGTH_LONG).show();
                        // dialoge.dismiss();

                    }

                    // hiding the progress bar
                    // progressDialog.dismiss();


                } catch (JSONException e) {
                    loading.dismiss();
                    e.printStackTrace();
                    Log.d("ERRRRR", e.toString());
                    // Toast.makeText(getContext(),
                    //        "Error: " + e.getMessage(),
                    //        Toast.LENGTH_LONG).show();
                    // dialoge.dismiss();
                    //relativeLayoutSignup.setVisibility(View.VISIBLE);
                    // relativeLayoutOtp.setVisibility(View.GONE);
                    // progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                //relativeLayoutSignup.setVisibility(View.VISIBLE);
                //relativeLayoutOtp.setVisibility(View.GONE);
                Log.e("error", "Error: " + error.getMessage());
                //Toast.makeText(getContext(),
                //        error.getMessage(), Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
                // dialoge.dismiss();
                //progressBar.setVisibility(View.GONE);
            }
        }) {

            /**
             * Passing user parameters to our server
             * @return
             */
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                // $id = $_POST['request_id'];
//        $to = $_POST['to'];
//        $from = $_POST['from'];
//        $subject = $_POST['subject'];
//        $status=$_POST['status'];
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
}
