package com.mygymbelgrade.mygym.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;
import com.mygymbelgrade.mygym.Lifecycle.PublicProfile;
import com.mygymbelgrade.mygym.R;
import com.mygymbelgrade.mygym.Utils.Utils;
import com.mygymbelgrade.mygym.WCF.Communication.DataClasses.ConfirmPayload;
import com.mygymbelgrade.mygym.WCF.Communication.WCFHandler;
import com.mygymbelgrade.mygym.WCF.Communication.WCFResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null)
        {
            //Proceed to main activity and authenticate on backend
        }

        Button loginButton = (Button)findViewById(R.id.loginButton);
        Button confirmCodeButton = (Button)findViewById(R.id.confirmCodeButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText)findViewById(R.id.email)).getText().toString();
                String password = ((EditText)findViewById(R.id.passwordInput)).getText().toString();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(username,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Proceed to main activity and authenticate on backend
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Login",e.getMessage());
                    }
                });
            }
        });

        confirmCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmationNumber = ((EditText)findViewById(R.id.confirmationCode)).getText().toString();
                JSONObject jObject = new JSONObject();
                try {
                    jObject.put("Code",confirmationNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                WCFHandler.getInstance().sendPostRequest("/json/confirmcode", new WCFResponse() {
                    @Override
                    public void getResponse(String response) {
                        Log.d("WCFResponse", response);
                        try {
                            JSONObject jObject = Utils.getJsonString(response);
                            ConfirmPayload cPayload = new ConfirmPayload(jObject);
                            PublicProfile.getInstance().email = cPayload.email;
                            PublicProfile.getInstance().name = cPayload.name;
                            PublicProfile.getInstance().userID = cPayload.userID;

                            startActivity(new Intent(MainActivity.this, InputPasswordActivity.class));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String response) {

                    }
                }, jObject.toString());
            }
        });


    }
}
