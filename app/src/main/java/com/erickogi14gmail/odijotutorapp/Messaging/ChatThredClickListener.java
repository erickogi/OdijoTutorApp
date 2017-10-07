package com.erickogi14gmail.odijotutorapp.Messaging;

import android.view.View;

/**
 * Created by Eric on 9/27/2017.
 */

public interface ChatThredClickListener {
    void onChatItemClicked(int position, View view);
    void onChatItemLongClicked(int position, View view);
}
