package com.erickogi14gmail.odijotutorapp.Data.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.erickogi14gmail.odijotutorapp.MyApplication;

import java.util.Map;

/**
 * Created by Eric on 12/15/2017.
 */

public class DumbVolleyRequest {
    String responseObj = null;

    public String getPostData(String url, Map<String, String> params, RequestListener r) {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, response -> {


            responseObj = response;
            Log.d("dvrR", response);
            r.onSuccess(response);


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("dvrVE", "Error: " + error.getMessage());
                r.onError(error);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Log.e("posting params", "Posting params: " + params.toString());

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);


        return responseObj;

    }

    public String getGetData(String url, RequestListener requestListener) {

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, response -> {


            responseObj = response;
            Log.d("dvrR", response);


            requestListener.onSuccess(response);


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("dvrVE", "Error: " + error.getMessage());

                requestListener.onError(error);
            }
        }) {


        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq);


        return responseObj;

    }

}
