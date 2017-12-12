package com.erickogi14gmail.odijotutorapp.Views.Requests;

import android.content.Context;
import android.util.Log;

import com.erickogi14gmail.odijotutorapp.Data.Models.RequestPojo;

import java.util.ArrayList;

/**
 * Created by Eric on 11/27/2017.
 */

public class Presenter {
    private ArrayList<RequestPojo> requestPojos = new ArrayList<>();
    private DataChangedListener dataChangedListener;

    public Presenter() {

    }

    public Presenter(DataChangedListener dataChangedListener) {

        this.dataChangedListener = dataChangedListener;
    }

    public void refresh(ArrayList<RequestPojo> requestPojos, DataChangedListener listener) {
        this.requestPojos = requestPojos;
        this.dataChangedListener = listener;
        ArrayList<RequestPojo> newRequest = new ArrayList<>();
        ArrayList<RequestPojo> activeRequest = new ArrayList<>();
        ArrayList<RequestPojo> historyRequest = new ArrayList<>();
        for (RequestPojo requestPojo : requestPojos) {
            if (requestPojo.getStatus() == 0) {
                Log.d("requesrtt", requestPojo.getStudent_name());
                newRequest.add(requestPojo);
            } else if (requestPojo.getStatus() == 1) {
                activeRequest.add(requestPojo);
            } else {
                historyRequest.add(requestPojo);
            }
        }
        dataChangedListener.onDataChanged(newRequest, activeRequest, historyRequest);

    }

    public ArrayList<RequestPojo> Presenter(Context context, int type, DataChangedListener dataChangedListener) {


        return requestPojos;

    }
}
