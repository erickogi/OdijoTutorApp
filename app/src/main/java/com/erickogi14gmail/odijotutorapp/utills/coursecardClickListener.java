package com.erickogi14gmail.odijotutorapp.utills;

import android.view.View;

/**
 * Created by Eric on 9/26/2017.
 */

public interface coursecardClickListener {
    void onBtnMoreClicked(int position);

    void  onCourseCardClicked(int position);
    void onMenuClicked(int position, View v);
    void onScheduleBtnClicked(int position);
}
