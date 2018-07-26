package com.mygymbelgrade.mygym.WCF.Communication.DataClasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vukas on 7/15/2018.
 */

public class ConfirmPayload {

    public String email;
    public String name;
    public long userID;

    public ConfirmPayload(JSONObject jsonObject)
    {
        try {
            email = jsonObject.getString("Email");
            name = jsonObject.getString("Name");
            userID = jsonObject.getLong("UserID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
