package com.erickogi14gmail.odijotutorapp.utills;

import android.view.View;

/**
 * Created by Eric on 9/12/2017.
 */

public interface clickListener {
    void onPositionClicked(int position);
    void onLongClicked(int postion);

    void onPositionClickedbtnProfile(int position);
    void onBtnMoreClicked(int position);
    void  onCourseCardClicked(int position, View v);



}
