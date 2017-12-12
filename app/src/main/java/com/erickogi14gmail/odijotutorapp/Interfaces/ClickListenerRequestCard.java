package com.erickogi14gmail.odijotutorapp.Interfaces;

import android.view.View;
import android.widget.Button;

/**
 * Created by Eric on 9/27/2017.
 */

public interface ClickListenerRequestCard {
    void onCardClicked(int position);

    void onBtnAcceptClicked(int position, Button buttonA, Button buttonB);

    void onBtnDeclineClicked(int position, Button buttonA, Button buttonB);

    void onClientImageClicked(int position);

    void onIconMoreClicked(int posotion, View v);
}
