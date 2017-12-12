package com.erickogi14gmail.odijotutorapp.Views.Requests;


import com.erickogi14gmail.odijotutorapp.Data.Models.RequestPojo;

import java.util.ArrayList;

/**
 * Created by Eric on 11/27/2017.
 */

public interface DataChangedListener {
    void onDataChanged(ArrayList<RequestPojo> newRequest, ArrayList<RequestPojo> activeRequest, ArrayList<RequestPojo> requestpojos);
}
