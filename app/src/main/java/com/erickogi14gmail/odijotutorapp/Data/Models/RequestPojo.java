package com.erickogi14gmail.odijotutorapp.Data.Models;

import java.io.Serializable;

/**
 * Created by Eric on 9/27/2017.
 */

public class RequestPojo implements Serializable {


    private int id;
    private String to;
    private String from;
    private String message;
    private String date;
    private String time;
    private String subnitted;
    private int status;
    private String zone;

    private int session_status;
    private int current_session;

    // private String subject_name;
    private String student_name;
    private String student_email;
    private String student_mobile;
    private String student_image;
    private String student_zone;
    private String subject_name;


    public RequestPojo() {
    }

    public int getSession_status() {
        return session_status;
    }

    public void setSession_status(int session_status) {
        this.session_status = session_status;
    }

    public int getCurrent_session() {
        return current_session;
    }

    public void setCurrent_session(int current_session) {
        this.current_session = current_session;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubnitted() {
        return subnitted;
    }

    public void setSubnitted(String subnitted) {
        this.subnitted = subnitted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }

    public String getStudent_mobile() {
        return student_mobile;
    }

    public void setStudent_mobile(String student_mobile) {
        this.student_mobile = student_mobile;
    }

    public String getStudent_image() {
        return student_image;
    }

    public void setStudent_image(String student_image) {
        this.student_image = student_image;
    }

    public String getStudent_zone() {
        return student_zone;
    }

    public void setStudent_zone(String student_zone) {
        this.student_zone = student_zone;
    }
}
