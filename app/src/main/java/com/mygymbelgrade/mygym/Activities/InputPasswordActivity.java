package com.mygymbelgrade.mygym.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mygymbelgrade.mygym.Lifecycle.PublicProfile;
import com.mygymbelgrade.mygym.R;
import com.mygymbelgrade.mygym.WCF.Communication.WCFHandler;
import com.mygymbelgrade.mygym.WCF.Communication.WCFResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vukas on 7/15/2018.
 */

public class InputPasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button confirmPass = (Button)findViewById(R.id.confirmPassword);

        confirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = ((EditText)findViewById(R.id.password1)).getText().toString();
                String password2 = ((EditText)findViewById(R.id.password2)).getText().toString();

                if(password1.equals(password2))
                {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(PublicProfile.getInstance().email, password1).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            JSONObject toSend = new JSONObject();
                            try {
                                toSend.put("UserID",PublicProfile.getInstance().userID);
                                toSend.put("GoogleToken",authResult.getUser().getToken(true).toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            WCFHandler.getInstance().sendPostRequest("/json/confirmuser", new WCFResponse() {
                                @Override
                                public void getResponse(String response) {
                                    Log.d("WCFResponse",response);

                                    if(response.contains("true"))
                                    {
                                        startActivity(new Intent(InputPasswordActivity.this, MainLogicActiity.class));
                                    }
                                }

                                @Override
                                public void onError(String response) {
                                    Log.d("WCFError",response);
                                }
                            }, toSend.toString());

                            //startActivity(new Intent(InputPasswordActivity.this, MainLogicActiity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Fail",e.getMessage());
                        }
                    });
                }
            }
        });
    }
}
