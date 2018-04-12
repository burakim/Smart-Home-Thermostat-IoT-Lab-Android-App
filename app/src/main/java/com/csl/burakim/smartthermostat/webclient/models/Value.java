package com.csl.burakim.smartthermostat.webclient.models;

/**
 * Created by burak on 4/6/18.
 */

public class Value {
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    private double value;
    private String timeStamp;
}
