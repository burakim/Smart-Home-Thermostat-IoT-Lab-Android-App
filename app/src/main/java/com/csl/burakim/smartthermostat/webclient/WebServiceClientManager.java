package com.csl.burakim.smartthermostat.webclient;

import android.content.Context;

import com.csl.burakim.smartthermostat.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by burak on 3/31/18.
 */

public class WebServiceClientManager {
    private String webServiceBaseURL;
    private static WebServiceClientManager self;
    private Retrofit retrofit;
    private WebService webService;
    private String webServiceURL;
    private Context context;

    public static WebServiceClientManager getInstance() throws NullPointerException
    {
        if(self == null)
        {
            throw new NullPointerException("WebServiceClientManager hasn't been initialized yet. Please use initialize function first.");
        }

        return self;
    }

    public static void initialize(String webServiceBaseURL, Context context)
    {
        if(self == null)
        {
            self = new WebServiceClientManager(webServiceBaseURL,context);

        }
    }
    private KeyStore readKeyStore() {
        KeyStore ks = null;
        try {
            ks =  KeyStore.getInstance("PKCS12");

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        char[] password = "miamiFIU".toCharArray();

        java.io.InputStream fis = null;
        try {
            fis =  context.getResources().openRawResource(R.raw.keystore);

            ks.load(fis, password);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ks;
    }


    public WebService getWebService() {
        return webService;
    }

    public WebServiceClientManager(String baseURL,Context context)
    {
        if(retrofit == null){
            this.context = context;

            KeyStore keyStore = readKeyStore(); //your method to obtain KeyStore
            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");


                TrustManagerFactory trustManagerFactory = null;

                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());


                trustManagerFactory.init(keyStore);

                KeyManagerFactory keyManagerFactory = null;

                keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());


                keyManagerFactory.init(keyStore, "miamiFIU".toCharArray());

                sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());


                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslContext.getSocketFactory());
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                OkHttpClient client = builder.build();


                retrofit = new Retrofit.Builder().baseUrl(baseURL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

        }

        webService = retrofit.create(WebService.class);
        webServiceURL = baseURL;

    }


}
