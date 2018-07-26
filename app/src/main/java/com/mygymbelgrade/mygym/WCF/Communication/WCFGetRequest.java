package com.mygymbelgrade.mygym.WCF.Communication;

import android.os.AsyncTask;

import com.mygymbelgrade.mygym.Utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vukas on 6/23/2017.
 */

public class WCFGetRequest extends AsyncTask<Void,Void,Void> {

    public WCFResponse response;
    public String url;

    public WCFGetRequest(WCFResponse response,String url)
    {
        this.response = response;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String finalUrl = WCFHandler.wcfLocation+url;
        URL conUrl = null;
        try {
            conUrl = new URL(finalUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection)conUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("USER_AGENT","Mozilla/5.0");
            connection.setConnectTimeout(15000);
            InputStream in = connection.getInputStream();
            String responseString = Utils.getStringFromInputStream(in);
            response.getResponse(responseString);
            connection.disconnect();
        } catch (IOException e) {
            response.onError(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
