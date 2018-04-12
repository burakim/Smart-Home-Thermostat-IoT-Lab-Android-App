package com.csl.burakim.smartthermostat.webclient;

import com.csl.burakim.smartthermostat.webclient.models.GetLatestSensorValuesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by burak on 4/6/18.
 */

public interface WebService {

@GET("getsensorvalues/{pantherId}/")
Call<GetLatestSensorValuesResponse> getSensorDataFromPantherID(@Path("pantherId") int pantherId);



}
