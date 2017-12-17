package com.erickogi14gmail.odijotutorapp.Views.Login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
import com.erickogi14gmail.odijotutorapp.Data.Models.Subjects;
import com.erickogi14gmail.odijotutorapp.Data.Network.DumbVolleyRequest;
import com.erickogi14gmail.odijotutorapp.Data.Network.RequestListener;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.MyApplication;
import com.erickogi14gmail.odijotutorapp.R;
import com.erickogi14gmail.odijotutorapp.Views.MainActivity;
import com.erickogi14gmail.odijotutorapp.utills.Controller;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eric on 9/12/2017.
 */

public class LoginFragment extends Fragment {
    ProgressDialog progressDialog;
    Bitmap thumnail = null;//BitmapFactory.decodeResource(getContext().getResources(),R.drawable.ic_person_black_24dp);
    private View view;
    private PrefManager pref;
    private Dialog dialoge;
    private TextInputEditText mEmail;
    private Button btnLogin;

    private DumbVolleyRequest dumbVolleyRequest;
    private Controller controller;

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.login_fragment,container,false);
        dumbVolleyRequest = new DumbVolleyRequest();
        controller = new Controller();
        mEmail = view.findViewById(R.id.edt_email);
        btnLogin = view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiate();
            }
        });
        return view;
    }

    private void initiate() {
        if (isFilled(mEmail)) {
            //if(validateFields())
            //{
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Login in ....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            requestLoginTutor(mEmail.getText().toString());
            //}
        }
    }

    private boolean validateFields() {
        if (!isValidEmail(mEmail.getText().toString())) {
            mEmail.setError("Invalid Email");
            return false;
        } else {
            return true;
        }


    }

    private boolean isFilled(TextInputEditText textInputEditText) {
        if (textInputEditText.getText().toString().equals("")) {
            textInputEditText.setError("Required");
            return false;
        } else {
            return true;
        }

    }

    private void alertDialogDelete(final String message) {
        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //popOutFragments();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();

                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(message).setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void requestLoginTutor(final String mobile) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("mobile", mobile);
        params.put("user", "TUTOR");


        dumbVolleyRequest.getPostData(Configs.LOGIN_URL, params, new RequestListener() {
            @Override
            public void onError(VolleyError error) {
                controller.toast("Error Login In Please Try Again", getContext(), R.drawable.ic_error_outline_black_24dp);
                progressDialog.dismiss();
            }

            @Override
            public void onError(String error) {
                controller.toast("Error Login In Please Try Again", getContext(), R.drawable.ic_error_outline_black_24dp);
                progressDialog.dismiss();
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
                        JSONObject profileObj = responseObj.getJSONObject("profile");

                        String name = profileObj.getString("name");

                        String email = profileObj.getString("email");

                        String mobile = profileObj.getString("mobile");

                        String api = profileObj.getString("apikey");
                        String ZONE = profileObj.getString("zone");
                        String description = profileObj.getString("description");
                        String image = profileObj.getString("image");
                        String workhrs = profileObj.getString("working_hours");
                        String id = profileObj.getString("id");


                        String a = null;


                        PrefManager pref = new PrefManager(getContext());
                        pref.createLogin(name, email, mobile, ZONE, description, workhrs, id);

                        try {
                            // JSONObject jObj = new JSONObject(response);
                            // JSONObject jsonArray = profileObj.getJSONObject("subjects");
                            JSONArray jsonArray1 = responseObj.getJSONArray("subjects");
                            Log.d("subjects", jsonArray1.toString());
                            //jsonArray=profileObj.getString("subjects");
                            String l = jsonArray1.toString();
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<Collection<Subjects>>() {

                            }.getType();
                            ArrayList<Subjects> subjectses1 = new ArrayList<>();
                            subjectses1 = gson.fromJson(l, collectionType);
                            pref.saveSubjects(subjectses1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("catch", e.getMessage());
                        }


                        //startActivity(new Intent(getContext(), MainActivity.class));
                        //getActivity().finish();
                        getThumbnail(image, name);


                        // Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                    } else {

                        progressDialog.dismiss();
                        alertDialogDelete("Error Login In .\nIf you don't have an account ,\ncreate One first");

                        String message = responseObj.getString("error");


                    }


                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("ERRRRR", e.toString());
                    alertDialogDelete("Error Login In .\nIf you don't have an account ,\ncreate One first");

                    progressDialog.dismiss();
                }
            }
        });

//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                Configs.LOGIN_URL, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                // Log.d("response", response.toString());
//
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
//                        JSONObject profileObj = responseObj.getJSONObject("profile");
//
//                        String name = profileObj.getString("name");
//
//                        String email = profileObj.getString("email");
//
//                        String mobile = profileObj.getString("mobile");
//
//                        String api = profileObj.getString("apikey");
//                        String ZONE = profileObj.getString("zone");
//                        String description = profileObj.getString("description");
//                        String image = profileObj.getString("image");
//                        String workhrs = profileObj.getString("working_hours");
//                        String id = profileObj.getString("id");
//
//
//                        String a = null;
//
//
//                        PrefManager pref = new PrefManager(getContext());
//                        pref.createLogin(name, email, mobile, ZONE, description, workhrs, id);
//
//                        try {
//                            // JSONObject jObj = new JSONObject(response);
//                            // JSONObject jsonArray = profileObj.getJSONObject("subjects");
//                            JSONArray jsonArray1 = responseObj.getJSONArray("subjects");
//                            Log.d("subjects", jsonArray1.toString());
//                            //jsonArray=profileObj.getString("subjects");
//                            String l = jsonArray1.toString();
//                            Gson gson = new Gson();
//                            Type collectionType = new TypeToken<Collection<Subjects>>() {
//
//                            }.getType();
//                            ArrayList<Subjects> subjectses1 = new ArrayList<>();
//                            subjectses1 = gson.fromJson(l, collectionType);
//                            pref.saveSubjects(subjectses1);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.d("catch", e.getMessage());
//                        }
//
//
//                        //startActivity(new Intent(getContext(), MainActivity.class));
//                        //getActivity().finish();
//                        getThumbnail(image, name);
//
//
//                        // Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(getActivity(), "Error Login In .\n If you don't have an account ,create One first", Toast.LENGTH_LONG).show();
//                        String message = responseObj.getString("error");
//
//                        //Toast.makeText(getContext(),
//                        //       "Error: " + message,
//                        //      Toast.LENGTH_LONG).show();
//
//                    }
//
//                    // hiding the progress bar
//                    progressDialog.dismiss();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.d("ERRRRR", e.toString());
//                    Toast.makeText(getContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//
//                    progressDialog.dismiss();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("error", "Error: " + error.getMessage());
//                Toast.makeText(getContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
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
//
//                params.put("mobile", mobile);
//                params.put("user", "TUTOR");
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

    private void requestForSMS(final String mobile) {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Configs.LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // Log.d("response", response.toString());

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
                        JSONObject profileObj = responseObj.getJSONObject("profile");

                        String name = profileObj.getString("name");

                        String email = profileObj.getString("email");

                        String mobile = profileObj.getString("mobile");

                        String api = profileObj.getString("apikey");
                        String ZONE = profileObj.getString("zone");
                        String description = profileObj.getString("description");
                        String image = profileObj.getString("image");
                        String workhrs = profileObj.getString("work_hours");


                        String a = null;


                        PrefManager pref = new PrefManager(getContext());
                        // pref.createLogin(name, email, mobile, ZONE, description, workhrs);

                        try {
                            // JSONObject jObj = new JSONObject(response);
                            // JSONObject jsonArray = profileObj.getJSONObject("subjects");
                            JSONArray jsonArray1 = responseObj.getJSONArray("subjects");
                            Log.d("subjects", jsonArray1.toString());
                            //jsonArray=profileObj.getString("subjects");
                            String l = jsonArray1.toString();
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<Collection<Subjects>>() {

                            }.getType();
                            ArrayList<Subjects> subjectses1 = new ArrayList<>();
                            subjectses1 = gson.fromJson(l, collectionType);
                            pref.saveSubjects(subjectses1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("catch", e.getMessage());
                        }


                        //startActivity(new Intent(getContext(), MainActivity.class));
                        //getActivity().finish();
                        getThumbnail(image, name);


                        // Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Error Login In .\n If you don't have an account ,create One first", Toast.LENGTH_LONG).show();
                        String message = responseObj.getString("error");

                        //Toast.makeText(getContext(),
                        //       "Error: " + message,
                        //      Toast.LENGTH_LONG).show();

                    }

                    // hiding the progress bar
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("ERRRRR", e.toString());
                    Toast.makeText(getContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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

                params.put("mobile", mobile);
                params.put("user", "TUTOR");

                Log.e("posting params", "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);
    }

    //
    public Bitmap getThumbnail(String filename, final String name) {
        //   final Bitmap thumnail = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.ic_person_black_24dp);
//
        Picasso.with(getContext()).load(filename).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d("bitmap", "Loaded");
                // Toast.makeText(getContext(), "Bitmap loaded"+bitmap, Toast.LENGTH_SHORT).show();
                thumnail = bitmap;
                if (
                        storeImage(bitmap, name)) {
                    progressDialog.dismiss();

                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();

                } else {
                    progressDialog.dismiss();

                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();

                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("bitmap", "Failed");
                progressDialog.dismiss();

                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();

                // Toast.makeText(getContext(), "Bitmap failed"+errorDrawable, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                //progressDialog.dismiss();

                //startActivity(new Intent(getContext(), MainActivity.class));

                Log.d("bitmap", "prepared");
                // Toast.makeText(getContext(), "Bitmap prepare"+placeHolderDrawable, Toast.LENGTH_SHORT).show();

            }
        });
//        Bitmap thumnail = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.ic_person_black_24dp);
//        try {
//            File filepath = getContext().getFileStreamPath(filename);
//            FileInputStream fi = new FileInputStream(filepath);
//            thumnail = BitmapFactory.decodeStream(fi);
//
//        } catch (Exception m) {
//            m.printStackTrace();
//        }
        return thumnail;
    }


    private boolean storeImage(Bitmap b, String name) {
        try {
            PrefManager prefManager = new PrefManager(getContext());
            String imagePath = null;
            //String name = txtName.getText().toString();
            imagePath = name + ".png";
            FileOutputStream fos = getContext().openFileOutput(name + ".png", Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();


            prefManager.storeImg(imagePath);
            return true;
        } catch (Exception m) {
            //controller.toast("Error Storing Image",ItemDetails.this,R.drawable.ic_error_outline_black_24dp);
            Toast.makeText(getContext(), "not sac" + m.getMessage(), Toast.LENGTH_SHORT).show();
            m.printStackTrace();
            return false;
        }
    }

}
