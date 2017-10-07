package com.erickogi14gmail.odijotutorapp.Main;

import java.io.Serializable;

/**
 * Created by Eric on 9/27/2017.
 */

public class RequestPojo implements Serializable {
    private int request_id;
    private int client_id;
    private String client_name;
    private String client_zone;
    private String client_rating;
    private String client_image;
    private String request_date;
    private String request_startDate;
    private String request_endDate;

    private int status;

    public RequestPojo() {
    }

    public RequestPojo(int request_id, int client_id, String client_name, String client_zone, String client_rating, String client_image, String request_date, String request_startDate, String request_endDate) {
        this.request_id = request_id;
        this.client_id = client_id;
        this.client_name = client_name;
        this.client_zone = client_zone;
        this.client_rating = client_rating;
        this.client_image = client_image;
        this.request_date = request_date;
        this.request_startDate = request_startDate;
        this.request_endDate = request_endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_zone() {
        return client_zone;
    }

    public void setClient_zone(String client_zone) {
        this.client_zone = client_zone;
    }

    public String getClient_rating() {
        return client_rating;
    }

    public void setClient_rating(String client_rating) {
        this.client_rating = client_rating;
    }

    public String getClient_image() {
        return client_image;
    }

    public void setClient_image(String client_image) {
        this.client_image = client_image;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getRequest_startDate() {
        return request_startDate;
    }

    public void setRequest_startDate(String request_startDate) {
        this.request_startDate = request_startDate;
    }

    public String getRequest_endDate() {
        return request_endDate;
    }

    public void setRequest_endDate(String request_endDate) {
        this.request_endDate = request_endDate;
    }
}
