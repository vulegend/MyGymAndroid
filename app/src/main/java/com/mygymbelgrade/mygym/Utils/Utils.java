package com.mygymbelgrade.mygym.Utils;

import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vukas on 7/15/2018.
 */

public class Utils {

    public static String getStringFromInputStream(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        StringBuilder responseOutput = new StringBuilder();
        while((line = br.readLine()) != null)
        {
            responseOutput.append(line);
        }
        br.close();
        return responseOutput.toString();
    }

    public static JSONObject getJsonString(String response) throws JSONException {
        JsonParser parser = new JsonParser();
        String getVal = parser.parse(response).getAsString();
        return new JSONObject(getVal);
    }
}
