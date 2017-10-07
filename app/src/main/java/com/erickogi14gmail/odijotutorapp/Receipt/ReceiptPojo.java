package com.erickogi14gmail.odijotutorapp.Receipt;

import java.io.Serializable;

/**
 * Created by Eric on 9/15/2017.
 */

public class ReceiptPojo implements Serializable {
    private int receipt_id;
    private String receipt_odijo_name;
    private String receipt_course_name;
    private String receipt_dateTime;
    private String receipt_cost;
    private String receipt_duration;

    public ReceiptPojo(int receipt_id, String receipt_odijo_name, String receipt_course_name, String receipt_dateTime, String receipt_cost, String receipt_duration) {

        this.receipt_id = receipt_id;
        this.receipt_odijo_name = receipt_odijo_name;
        this.receipt_course_name = receipt_course_name;
        this.receipt_dateTime = receipt_dateTime;
        this.receipt_cost = receipt_cost;
        this.receipt_duration = receipt_duration;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getReceipt_odijo_name() {
        return receipt_odijo_name;
    }

    public void setReceipt_odijo_name(String receipt_odijo_name) {
        this.receipt_odijo_name = receipt_odijo_name;
    }

    public String getReceipt_course_name() {
        return receipt_course_name;
    }

    public void setReceipt_course_name(String receipt_course_name) {
        this.receipt_course_name = receipt_course_name;
    }

    public String getReceipt_dateTime() {
        return receipt_dateTime;
    }

    public void setReceipt_dateTime(String receipt_dateTime) {
        this.receipt_dateTime = receipt_dateTime;
    }

    public String getReceipt_cost() {
        return receipt_cost;
    }

    public void setReceipt_cost(String receipt_cost) {
        this.receipt_cost = receipt_cost;
    }

    public String getReceipt_duration() {
        return receipt_duration;
    }

    public void setReceipt_duration(String receipt_duration) {
        this.receipt_duration = receipt_duration;
    }
}
