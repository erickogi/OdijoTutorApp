package com.erickogi14gmail.odijotutorapp.Data.Models;

import java.io.Serializable;

/**
 * Created by Eric on 10/18/2017.
 */

public class Levels implements Serializable {
    private int id;
    private String level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
