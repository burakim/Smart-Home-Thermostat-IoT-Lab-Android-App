package com.csl.burakim.smartthermostat.models;

import android.graphics.drawable.Drawable;

import java.nio.file.Path;

/**
 * Created by burak on 4/7/18.
 */

public class Variable {

    private String name;
    private String dataTime;
    private Drawable picture;
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Drawable getPicture() {
        return picture;
    }

    public void setPicture(Drawable picture) {
        this.picture = picture;
    }
}
