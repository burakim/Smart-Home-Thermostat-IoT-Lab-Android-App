package com.csl.burakim.smartthermostat;

/**
 * Created by burak on 4/6/18.
 */

public class ApplicationConstants {

    private static ApplicationConstants self;

    public static void init()
    {
        if(self == null)
            self = new ApplicationConstants();
    }

    public enum RequestResult{
        SUCCESS,
        FAIL
    }

}

