package com.erickogi14gmail.odijotutorapp.Messaging;

import android.view.View;

/**
 * Created by Eric on 9/27/2017.
 */

public interface ChatListItemClickListener {
    void onProfileImage(int position);
    void onChatClicked(int position);
    void onChatLongClicked(int position, View view);
}
