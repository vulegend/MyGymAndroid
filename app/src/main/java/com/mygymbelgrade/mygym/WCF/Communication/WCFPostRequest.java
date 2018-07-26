package com.mygymbelgrade.mygym.WCF.Communication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vukas on 6/23/2017.
 */

public class WCFPostRequest extends AsyncTask<Void,Void,Void> {

    public WCFResponse response;
    public String jsonData;
    public String url;

    public WCFPostRequest(WCFResponse response,String jsonData,String url)
    {
        this.response = response;
        this.jsonData = jsonData;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL conUrl = new URL(WCFHandler.wcfLocation + url);
            HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json");
        //    connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(jsonData);
            streamWriter.flush();

            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String responseStr;
                while ((responseStr = bufferedReader.readLine()) != null) {
                    stringBuilder.append(responseStr);
                }
                bufferedReader.close();

                response.getResponse(stringBuilder.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.onError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            response.onError(e.getMessage());
        }
        return null;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
