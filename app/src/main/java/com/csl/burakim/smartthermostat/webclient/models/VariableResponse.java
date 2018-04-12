package com.csl.burakim.smartthermostat.webclient.models;

/**
 * Created by burak on 4/6/18.
 */

public class VariableResponse {
    private String variableName;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    private Value value;
}
