package com.example.haba_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.haba_app.DatabaseHandler;
import com.example.haba_app.Helper;
import com.example.haba_app.R;
import com.example.haba_app.Requests;
import com.example.haba_app.activity_add_request;
import com.example.haba_app.viewholder_requests;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class fragment_owner_post_request extends Fragment {


    FloatingActionButton btn_add;
    viewholder_requests viewholder_requests;
    RecyclerView recyclerView;
    ArrayList<Requests> list=new ArrayList<>();
    DatabaseHandler databaseHandler;

    public fragment_owner_post_request(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_owner_post_request, container, false);
        databaseHandler=new DatabaseHandler(getActivity());
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        btn_add=view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), activity_add_request.class));
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        list.clear();
        list=databaseHandler.get_requests_by_user_id(Helper.GetData(getActivity(),"user_id"));
        viewholder_requests = new viewholder_requests(getActivity(), list);
        recyclerView.setAdapter(viewholder_requests);
    }
}

