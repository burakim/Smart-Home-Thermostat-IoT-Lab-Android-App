package com.csl.burakim.smartthermostat.webclient.models;

import com.csl.burakim.smartthermostat.ApplicationConstants;

import java.util.ArrayList;

/**
 * Created by burak on 4/6/18.
 */

public class GetLatestSensorValuesResponse {

    private ApplicationConstants.RequestResult requestResult;
    private ArrayList<VariableResponse> values;
    private int pantherId;

    public ArrayList<VariableResponse> getValues() {
        return values;
    }

    public void setValues(ArrayList<VariableResponse> values) {
        this.values = values;
    }

    public int getPantherId() {
        return pantherId;
    }

    public void setPantherId(int pantherId) {
        this.pantherId = pantherId;
    }

    public ApplicationConstants.RequestResult getRequestResult() {
        return requestResult;
    }

    public void setRequestResult(ApplicationConstants.RequestResult requestResult) {
        this.requestResult = requestResult;
    }

}
