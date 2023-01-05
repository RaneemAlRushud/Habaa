package com.example.haba_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import java.util.ArrayList;

public class activity_login extends AppCompatActivity {
    EditText email,password;
    private static final int PERMISSION_REQUEST_CODE = 200;
    Button btn_sign_in;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseHandler=new DatabaseHandler(activity_login.this);

        email = (EditText) findViewById(R.id.txt_email);
        password = (EditText) findViewById(R.id.txt_password);
        btn_sign_in=findViewById(R.id.btn_signin);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  if(email.getText().toString().equals("")){
                    email.setError("Please enter email");
                }

                else if(password.getText().toString().equals("")){
                    password.setError("Please enter password");
                }
                else {

                     if(databaseHandler.checkUser(email.getText().toString().trim(), password.getText().toString().trim())){


                         String type=databaseHandler.get_user_information(email.getText().toString()).get(0).getType();
                         String user_id=databaseHandler.get_user_information(email.getText().toString()).get(0).getId();
                         String name=databaseHandler.get_user_information(email.getText().toString()).get(0).getName();

                         if(type.equals("Guardian")){
                           Intent accountsIntent = new Intent(activity_login.this, activity_guardian.class);

                           Helper.PutData(activity_login.this,"email",email.getText().toString());
                           Helper.PutData(activity_login.this,"type",type);
                           Helper.PutData(activity_login.this,"user_id",user_id);
                             Helper.PutData(activity_login.this,"name",name);
                           startActivity(accountsIntent);
                       }
                       else{
                           Intent accountsIntent = new Intent(activity_login.this, activity_owner.class);

                           Helper.PutData(activity_login.this,"email",email.getText().toString());
                           Helper.PutData(activity_login.this,"type",type);
                           Helper.PutData(activity_login.this,"user_id",user_id);
                             Helper.PutData(activity_login.this,"name",name);
                           startActivity(accountsIntent);
                       }
                      }

                    //     if(type!=null){



                       //   Intent accountsIntent = new Intent(activity_login.this, MainActivity.class);

                         // Helper.PutData(activity_login.this,"email",email.getText().toString());

                         // startActivity(accountsIntent);

                  }

            }
        });

        TextView txt_signup=findViewById(R.id.cap_re);
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(activity_login.this,activity_register.class));
            }
        });




    }



}