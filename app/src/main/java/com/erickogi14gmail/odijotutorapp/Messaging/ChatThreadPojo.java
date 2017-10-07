package com.erickogi14gmail.odijotutorapp.Messaging;

import java.io.Serializable;

/**
 * Created by Eric on 9/27/2017.
 */

public class ChatThreadPojo implements Serializable {
    private int message_id;
    private int message_from;
    private String message_text;

    public ChatThreadPojo(int message_id, String message_text,int message_from) {
        this.message_id = message_id;
        this.message_text = message_text;
        this.message_from=message_from;
    }

    public ChatThreadPojo() {

    }

    public int getMessage_from() {
        return message_from;
    }

    public void setMessage_from(int message_from) {
        this.message_from = message_from;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }
}
