package com.csl.burakim.smartthermostat;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.csl.burakim.smartthermostat.models.Variable;
import com.csl.burakim.smartthermostat.webclient.WebServiceClientManager;
import com.csl.burakim.smartthermostat.webclient.models.GetLatestSensorValuesResponse;
import com.csl.burakim.smartthermostat.webclient.models.Value;
import com.csl.burakim.smartthermostat.webclient.models.VariableResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;



    private String serverURL = "https://10.108.96.228:8443/";
    private int studentId = 6029718;

    private Handler retrieveDataHandler;
    private Runnable retrievingRunnable;



    private void initializeVariables(DataAdapter dataAdapter)
    {
        Variable temperatureVariable = new Variable();
        temperatureVariable.setName(getString(R.string.temperature));
        temperatureVariable.setDataTime("23/09/2018 4:23 AM");

        temperatureVariable.setPicture(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.temperature));

        Variable humidityVariable = new Variable();
        humidityVariable.setName(getString(R.string.humidity));
        humidityVariable.setPicture(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.humidity));

        Variable pressureVariable = new Variable();
        pressureVariable.setName(getString(R.string.pressure));
        pressureVariable.setPicture(ContextCompat.getDrawable(getApplicationContext(),R.mipmap.atmospheric_pressure));

        dataAdapter.addVariable(temperatureVariable);
        dataAdapter.addVariable(humidityVariable);
        dataAdapter.addVariable(pressureVariable);

    }

    public void updateSensorListView(GetLatestSensorValuesResponse response)
    {
        if(response.getPantherId() != studentId)
            throw new IllegalArgumentException("Student IDs mismatched");
        else
        {
            for (VariableResponse variableResponse : response.getValues())
                  {
                      Value value = variableResponse.getValue();
                      ArrayList<Variable> dataList = ((DataAdapter)recyclerViewAdapter).getmDataset();
if(value != null) {
    for (Variable variable : dataList) {
        if (variableResponse.getVariableName().compareTo(variable.getName()) == 0) {
            variable.setValue(value.getValue());
            variable.setDataTime(value.getTimeStamp());
        }
    }
}
            }
            (recyclerViewAdapter).notifyDataSetChanged();
        }

    }

    private void initializeWebClient()
    {
        WebServiceClientManager.initialize(serverURL,getApplicationContext());

    }

    private void initializeHandler()
    {

        retrieveDataHandler = new Handler();
       retrievingRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Response<GetLatestSensorValuesResponse> response = WebServiceClientManager.getInstance().getWebService().getSensorDataFromPantherID(studentId).execute();
                if(response.isSuccessful())
                {
                    updateSensorListView(response.body());
                    retrieveDataHandler.postDelayed(retrievingRunnable,1000);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    }




    private void startDataFetching()
    {
        retrieveDataHandler.postDelayed(retrievingRunnable,1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.sensor_value_recyclerview);

        recyclerView.setHasFixedSize(true);

        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        recyclerViewAdapter = new DataAdapter(getApplicationContext());

        initializeVariables((DataAdapter) recyclerViewAdapter);

        recyclerView.setAdapter(recyclerViewAdapter);

        initializeWebClient();
        RetrieveDataFromWebService retrieveDataFromWebService = new RetrieveDataFromWebService(this);

        MainActivity mainActivity = this;
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        new RetrieveDataFromWebService(mainActivity).execute(studentId);
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000); //it executes this every 1000ms



//        initializeHandler();
//        startDataFetching();




    }
}
