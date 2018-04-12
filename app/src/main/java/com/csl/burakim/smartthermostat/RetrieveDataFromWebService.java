package com.csl.burakim.smartthermostat;

import android.os.AsyncTask;

import com.csl.burakim.smartthermostat.webclient.WebService;
import com.csl.burakim.smartthermostat.webclient.WebServiceClientManager;
import com.csl.burakim.smartthermostat.webclient.models.GetLatestSensorValuesResponse;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by burak on 4/10/18.
 */

public class RetrieveDataFromWebService   extends AsyncTask<Integer,Void,Void> {
    private Response<GetLatestSensorValuesResponse> response = null;
    private MainActivity mainActivity;

    public RetrieveDataFromWebService(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected Void doInBackground(Integer... studentID)  {
        if(studentID.length == 1)
        {
            Integer studentId = studentID[0];

            try {
                response = WebServiceClientManager.getInstance().getWebService().getSensorDataFromPantherID(studentId).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else{
            try {
                throw new Exception("Passed Webservice argument should be only 1.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(response != null && response.isSuccessful())
        {
            mainActivity.updateSensorListView(response.body());

        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
