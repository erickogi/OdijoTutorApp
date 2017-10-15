package com.erickogi14gmail.odijotutorapp.Profile;

import java.io.Serializable;

/**
 * Created by Eric on 10/11/2017.
 */

public class Subjects implements Serializable {
    private int subject_id;
    private String subject_name;

    private String subject_rate;

    private String level;
    private String qualifications;

    public Subjects(int subject_id, String subject_name) {

        this.subject_id = subject_id;
        this.subject_name = subject_name;

    }

    public Subjects() {

    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getSubject_rate() {
        return subject_rate;
    }

    public void setSubject_rate(String subject_rate) {
        this.subject_rate = subject_rate;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }
}
