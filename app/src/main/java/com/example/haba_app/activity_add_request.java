package com.example.haba_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_add_request extends AppCompatActivity {
    ArrayList<User> user_info=new ArrayList<>();
    DatabaseHandler databaseHandler;
    Requests requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        requests=new Requests();
        databaseHandler=new DatabaseHandler(activity_add_request.this);
        user_info.clear();
        user_info=databaseHandler.get_user_information(Helper.GetData(activity_add_request.this,"email"));
        EditText txt_type=findViewById(R.id.txt_type);
        EditText txt_description=findViewById(R.id.txt_description);
        Button btn_add=findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = null;

                if(user_info.size()>0){

                    location=user_info.get(0).getAddress();

                }
                requests.setRequest_type(txt_type.getText().toString());
                requests.setRequest_description(txt_description.getText().toString());
                requests.setRequest_location(location);
                requests.setRequest_status("uncomplete");
                requests.setPosted_by(Helper.GetData(activity_add_request.this,"user_id"));

                requests.setNotification_status("-1");
              if(  databaseHandler.addRequest(requests)){

                  finish();
                  Toast.makeText(activity_add_request.this, "Request Added Successfully!", Toast.LENGTH_SHORT).show();

               }
            }
        });


    }
}