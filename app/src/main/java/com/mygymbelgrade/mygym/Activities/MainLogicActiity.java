package com.mygymbelgrade.mygym.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mygymbelgrade.mygym.Lifecycle.PublicProfile;
import com.mygymbelgrade.mygym.R;

/**
 * Created by vukas on 7/15/2018.
 */

public class MainLogicActiity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView test = (TextView)findViewById(R.id.test);
        test.setText("Dobrodosli : "+PublicProfile.getInstance().name);
    }
}
