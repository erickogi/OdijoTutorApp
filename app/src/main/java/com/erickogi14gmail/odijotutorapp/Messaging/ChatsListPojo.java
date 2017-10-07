package com.erickogi14gmail.odijotutorapp.Messaging;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Eric on 9/27/2017.
 */

public class ChatsListPojo implements Serializable {
    private String odijo_key;
    private String student_key;
    private String odijo_name;
    private String odijo_img;
    private String last_chat_date;
    private String last_chat;
    private int no_of_chats;



    private ArrayList<ChatThreadPojo> chatThreadPojos;



    public ChatsListPojo() {

    }

    public ChatsListPojo(String odijo_key, String student_key, String odijo_name, String odijo_img, String last_chat_date, String last_chat, int no_of_chats,ArrayList<ChatThreadPojo> chatThreadPojos) {
        this.odijo_key = odijo_key;
        this.student_key = student_key;
        this.odijo_name = odijo_name;
        this.odijo_img = odijo_img;
        this.last_chat_date = last_chat_date;
        this.last_chat = last_chat;
        this.no_of_chats = no_of_chats;
        this.chatThreadPojos=chatThreadPojos;
    }

    public ArrayList<ChatThreadPojo> getChatThreadPojos() {
        return chatThreadPojos;
    }

    public void setChatThreadPojos(ArrayList<ChatThreadPojo> chatThreadPojos) {
        this.chatThreadPojos = chatThreadPojos;
    }

    public String getLast_chat() {
        return last_chat;
    }

    public void setLast_chat(String last_chat) {
        this.last_chat = last_chat;
    }

    public String getOdijo_key() {
        return odijo_key;
    }

    public void setOdijo_key(String odijo_key) {
        this.odijo_key = odijo_key;
    }

    public String getStudent_key() {
        return student_key;
    }

    public void setStudent_key(String student_key) {
        this.student_key = student_key;
    }

    public String getOdijo_name() {
        return odijo_name;
    }

    public void setOdijo_name(String odijo_name) {
        this.odijo_name = odijo_name;
    }

    public String getOdijo_img() {
        return odijo_img;
    }

    public void setOdijo_img(String odijo_img) {
        this.odijo_img = odijo_img;
    }

    public String getLast_chat_date() {
        return last_chat_date;
    }

    public void setLast_chat_date(String last_chat_date) {
        this.last_chat_date = last_chat_date;
    }

    public int getNo_of_chats() {
        return no_of_chats;
    }

    public void setNo_of_chats(int no_of_chats) {
        this.no_of_chats = no_of_chats;
    }
}
