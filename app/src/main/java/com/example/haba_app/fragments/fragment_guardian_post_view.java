package com.example.haba_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.haba_app.DatabaseHandler;
import com.example.haba_app.R;
import com.example.haba_app.Requests;
import com.example.haba_app.activity_add_request;
import com.example.haba_app.viewholder_requests;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class fragment_guardian_post_view extends Fragment {


    FloatingActionButton btn_add;
    viewholder_requests viewholder_requests;
    RecyclerView recyclerView;
    ArrayList<Requests> list=new ArrayList<>();
    DatabaseHandler databaseHandler;
    TextView txt_no_requests;

    public fragment_guardian_post_view(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_guardian_post_view, container, false);
        databaseHandler=new DatabaseHandler(getActivity());
        txt_no_requests=view.findViewById(R.id.txt_no_requets);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        list.clear();
        list=databaseHandler.get_requests("uncomplete");
        if(list.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            viewholder_requests = new viewholder_requests(getActivity(), list);
            recyclerView.setAdapter(viewholder_requests);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            txt_no_requests.setVisibility(View.VISIBLE);

        }

    }
}

