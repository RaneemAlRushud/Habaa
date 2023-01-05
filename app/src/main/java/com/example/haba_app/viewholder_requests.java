package com.example.haba_app;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class viewholder_requests extends RecyclerView.Adapter<viewholder_requests.subjects>{

    Context context;
    ArrayList<Requests> _requests;
    DatabaseHandler databaseHandler;


    public viewholder_requests(Context c , ArrayList<Requests> list)
    {

        context = c;
        _requests = list;
        databaseHandler=new DatabaseHandler(c);

    }
    @NonNull
    @Override
    public subjects onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new subjects(LayoutInflater.from(context).inflate(R.layout.list_layout_requests,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final subjects holder, @SuppressLint("RecyclerView") final int position) {
        holder.txt_type.setText(_requests.get(position).getRequest_type());
        holder.txt_description.setText(_requests.get(position).getRequest_description());
        if(_requests.get(position).getRequest_status().equals("uncomplete")){

            holder.txt_status.setText("InProgress");

        }
        else{
            holder.txt_status.setText("Completed");
            holder.txt_status.setTextColor(Color.GREEN);
        }

        if(Helper.GetData(context,"type").equals("Pet Owner")){
            if(_requests.get(position).getNotification_status().equals("0")){

                databaseHandler.update_notification_status(_requests.get(position).getId(),"1");
                NotificationUtils.createNotification(context,_requests.get(position).getAccepted_by()+" accepted your request for pet adoption");
            }

        }
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(Helper.GetData(context,"type").equals("Guardian")){

                   if(_requests.get(position).getRequest_status().equals("uncomplete")){
                       Intent intent = new Intent(context, activity_request_decision.class);
                       intent.putExtra("id", _requests.get(position).getId());
                       intent.putExtra("type", _requests.get(position).getRequest_type());
                       intent.putExtra("description", _requests.get(position).getRequest_description());
                       intent.putExtra("status", _requests.get(position).getRequest_status());
                       context.startActivity(intent);
                   }

               }
               else{

               }
           }
       });



    }

    @Override
    public int getItemCount() {
        return _requests.size();
    }

    class subjects extends RecyclerView.ViewHolder{


        TextView txt_type,txt_description,txt_status;
        CardView cardView;

        public subjects(View itemView){
            super(itemView);
            txt_type=(TextView) itemView.findViewById(R.id.txt_request_type);
            txt_description=(TextView) itemView.findViewById(R.id.txt_request_description);
            txt_status=(TextView) itemView.findViewById(R.id.txt_status);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
        }


    }


}

