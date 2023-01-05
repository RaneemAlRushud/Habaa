package com.example.haba_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_request_decision extends AppCompatActivity {

    
    TextView txt_type,txt_description,txt_status;
    Button btn_approve;
    DatabaseHandler databaseHandler;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_decision);
        databaseHandler=new DatabaseHandler(activity_request_decision.this);
        txt_type=(TextView) findViewById(R.id.txt_request_type);
        txt_description=(TextView) findViewById(R.id.txt_request_description);
        txt_status=(TextView) findViewById(R.id.txt_status);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("id");
            txt_type.setText(extras.getString("type"));
            txt_description.setText(extras.getString("description"));
            txt_status.setText(extras.getString("status"));
        }

        btn_approve=findViewById(R.id.btn_approve);
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity_request_decision.this)
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage("Are you sure you want to accept this invitation")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if(databaseHandler.update_Request(id,"complete",Helper.GetData(activity_request_decision.this,"name"),"0")){

                                     NotificationUtils.createNotification(activity_request_decision.this,"Your adoption request has sent to the pet owner");

                                }

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                

            }
        });

    }
}