package com.mygymbelgrade.mygym.Lifecycle;

/**
 * Created by vukas on 7/15/2018.
 */

public class PublicProfile {

    private static PublicProfile _instance;
    public static PublicProfile getInstance()
    {
        if(_instance == null)
            _instance = new PublicProfile();

        return _instance;
    }

    public String name;
    public String email;
    public long userID;

}
