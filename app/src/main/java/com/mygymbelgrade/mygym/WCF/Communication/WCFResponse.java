package com.mygymbelgrade.mygym.WCF.Communication;

/**
 * Created by vukas on 6/23/2017.
 */

public interface WCFResponse {

    void getResponse(String response);
    void onError(String response);
}
