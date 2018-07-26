package com.mygymbelgrade.mygym.WCF.Communication;

import android.os.AsyncTask;

/**
 * Created by vukas on 6/23/2017.
 */

public class WCFHandler {


    //public static String wcfLocation = "http://54.93.126.190:9000";
    public static String wcfLocation = "http://192.168.1.2:9000";

    //public static String wcfLocation = "http://89.216.27.246:9100";

    private String _wcfLocation;

    private static WCFHandler _instance;

    public static WCFHandler getInstance()
    {
        if(_instance == null)
            _instance = new WCFHandler();

        return _instance;
    }

    public void sendGetRequest(String url, WCFResponse response)
    {
        WCFGetRequest getRequest = new WCFGetRequest(response,url);
        getRequest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void sendPostRequest(String url, WCFResponse response, String json)
    {
        WCFPostRequest postRequest = new WCFPostRequest(response,json,url);
        postRequest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
