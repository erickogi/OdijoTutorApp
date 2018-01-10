package com.erickogi14gmail.odijotutorapp.Views.Login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.erickogi14gmail.odijotutorapp.Configs;
import com.erickogi14gmail.odijotutorapp.Data.Network.DumbVolleyRequest;
import com.erickogi14gmail.odijotutorapp.Data.Network.RequestListener;
import com.erickogi14gmail.odijotutorapp.Firebase.SharedPrefManager;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.service.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 9/12/2017.
 */


public class SignUpFragment extends Fragment implements View.OnClickListener {
    public static Fragment fragment = null;
    LinearLayout relativeLayoutSignup;
    private View view;
    private TextInputEditText mFirstName, mLastName, mEmail, mMobile, mPassword, mConfirmPassword;
    private Button btnRegister;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private PrefManager pref;
    private RelativeLayout relativeLayoutOtp;
    private EditText edtCode;
    private Button btnSignup, btnVerify, btnResend;


    private Dialog dialoge;
    private ImageView imagback;
    private DumbVolleyRequest dumbVolleyRequest;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.sign_up_fragment,container,false);
        pref = new PrefManager(getContext());
        dumbVolleyRequest = new DumbVolleyRequest();
        mFirstName = view.findViewById(R.id.txt_firstname);
        mLastName = view.findViewById(R.id.txt_lastname);
        mEmail = view.findViewById(R.id.txt_emailAdress);
        mMobile = view.findViewById(R.id.txt_mobile);
        relativeLayoutOtp = view.findViewById(R.id.otp);
        relativeLayoutSignup = view.findViewById(R.id.signup);
        edtCode = view.findViewById(R.id.edt_code);


        // dialoge = new Dialog(getContext());
        // dialoge.setContentView(R.layout.dialog_otp);
        // dialoge.setCancelable(false);
        // dialoge.setCanceledOnTouchOutside(false);
        btnVerify = view.findViewById(R.id.btn_verify);
        btnResend = view.findViewById(R.id.btn_resend);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText(edtCode.getText().toString());
            }
        });
        btnResend.setOnClickListener(this);


        btnRegister = view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        //progressBar = (ProgressBar)view. findViewById(R.id.progressBar);
        imagback = view.findViewById(R.id.img_back);

        imagback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutOtp.setVisibility(View.GONE);
                relativeLayoutSignup.setVisibility(View.VISIBLE);
            }
        });


        mPassword = view.findViewById(R.id.txt_password);
        mConfirmPassword = view.findViewById(R.id.txt_confirm_password);







        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_register || v.getId() == R.id.btn_resend) {

            if (isFilled(mFirstName) && isFilled(mLastName) && isFilled(mEmail)
                    && isFilled(mMobile) && isFilled(mConfirmPassword) && isFilled(mPassword)) {

                if (validateFields()) {

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Registering you in....");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    String name = mFirstName.getText().toString() + "  " + mLastName.getText().toString();
                    String email = mEmail.getText().toString();
                    String mobile = mMobile.getText().toString();
                    String pass = mPassword.getText().toString();

                    relativeLayoutOtp.setVisibility(View.VISIBLE);
                    relativeLayoutSignup.setVisibility(View.GONE);
                    String token = SharedPrefManager.getInstance(getContext()).getDeviceToken();

                    requestForSMS(name
                            , email, mobile, token, pass);

                }
            } else {

            }


        }
    }

    private boolean validateFields() {
        if (!isValidEmail(mEmail.getText().toString())) {
            mEmail.setError("Invalid email");
            return false;
        } else if (!isValidPhoneNumber(mMobile.getText().toString())) {
            mMobile.setError("Invalid Number");
            return false;
        } else if (!isNotEqual(mConfirmPassword.getText().toString(), mPassword.getText().toString())) {
            mConfirmPassword.setError("Password doesn't match");
            return false;
        } else {
            return true;
        }


    }

    private boolean isNotEqual(String s, String s1) {
        return s.equals(s1);
    }

    private boolean isFilled(TextInputEditText textInputEditText) {
        if (textInputEditText.getText().toString().equals("")) {
            textInputEditText.setError("Required");
            return false;
        } else {
            return true;
        }

    }

    private void alertDialog(final String message) {
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        relativeLayoutOtp.setVisibility(View.GONE);
                        relativeLayoutSignup.setVisibility(View.VISIBLE);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();

                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(message).setPositiveButton("Okay", dialogClickListener)
                // .setNegativeButton("No", dialogClickListener)
                .show();

    }


    /**
     * Method initiates the SMS request on the server
     *  @param name   user name
     * @param email  user email address
     * @param mobile user valid mobile number
     * @param pass
     */
    private void requestForSMS(final String name, final String email, final String mobile, final String token, String pass) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("email", email);
        params.put("mobile", mobile);
        params.put("token", token);
        params.put("password", pass);
        relativeLayoutOtp.setVisibility(View.VISIBLE);
        relativeLayoutSignup.setVisibility(View.GONE);


        dumbVolleyRequest.getPostData(Configs.REGISTER_URL, params, new RequestListener() {
            @Override
            public void onError(VolleyError error) {
                alertDialog("Error registering you please try again");
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject responseObj = new JSONObject(response);
                    boolean error = responseObj.getBoolean("error");
                    if (!error) {
                        pref.setIsWaitingForSms(true);
                        //progressDialog.dismiss();

                    } else {

                        String message = responseObj.getString("message");
                        //String message = responseObj.getString("message");
                        progressDialog.dismiss();
                        alertDialog(message);

                    }

                    // hiding the progress bar


                } catch (JSONException e) {
                    e.printStackTrace();
                    alertDialog("Error registering you please try again");

                    progressDialog.dismiss();
                }
            }
        });


//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                Configs.REGISTER_URL, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                // Log.d("response", response.toString());
//                progressDialog.dismiss();
//                // dialoge.show();
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
//                        // boolean flag saying device is waiting for sms
//
//                        pref.setIsWaitingForSms(true);
//                        // progressDialog.dismiss();
//                        // moving the screen to next pager item i.e otp screen
//                        //dialoge.show();
//
//
//                        //Toast.makeText(getActivity(), "sent", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        String message = responseObj.getString("message");
//                        //relativeLayoutSignup.setVisibility(View.VISIBLE);
//                        //relativeLayoutOtp.setVisibility(View.GONE);
//                        //    Toast.makeText(getContext(),
//                        //          "Error: " + message,
//                        //          Toast.LENGTH_LONG).show();
//                        // dialoge.dismiss();
//
//                    }
//
//                    // hiding the progress bar
//                    progressDialog.dismiss();
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.d("ERRRRR", e.toString());
//                    Toast.makeText(getContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                    // dialoge.dismiss();
//                    //relativeLayoutSignup.setVisibility(View.VISIBLE);
//                    // relativeLayoutOtp.setVisibility(View.GONE);
//                    progressDialog.dismiss();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //relativeLayoutSignup.setVisibility(View.VISIBLE);
//                //relativeLayoutOtp.setVisibility(View.GONE);
//                Log.e("error", "Error: " + error.getMessage());
//                Toast.makeText(getContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                // dialoge.dismiss();
//                //progressBar.setVisibility(View.GONE);
//            }
//        }) {
//
//            /**
//             * Passing user parameters to our server
//             * @return
//             */
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name", name);
//                params.put("email", email);
//                params.put("mobile", mobile);
//                params.put("token", token);
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
    }

    public void setText(String code) {
        Log.d("Closeoperation", "true333");
        try {
            try {
                edtCode.setText(code);
                btnVerify.setEnabled(true);
                if (dialoge.isShowing()) {
                    btnVerify.setEnabled(true);
                    edtCode.setText(code);

                }
            } catch (Exception nm) {
                nm.printStackTrace();
            }

            Intent hhtpIntent = new Intent(getContext(), HttpService.class);
            hhtpIntent.putExtra("otp", code);

            getActivity().startService(hhtpIntent);
        } catch (Exception nm) {
            nm.printStackTrace();
        }


    }


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