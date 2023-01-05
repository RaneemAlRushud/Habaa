package com.example.haba_app.fragments;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.fragment.app.Fragment;

import com.example.haba_app.DatabaseHandler;
import com.example.haba_app.Helper;
import com.example.haba_app.R;
import com.example.haba_app.User;
import com.example.haba_app.activity_login;

import java.util.ArrayList;

public class fragment_guardian_profile extends Fragment {
    EditText txt_name,ph,email,txt_address;
    DatabaseHandler databaseHandler;
    Button btn_logout,btn_change_setting;
    User user;
    ArrayList<User> user_info=new ArrayList<>();
    public fragment_guardian_profile(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile_owner, container, false);
        databaseHandler=new DatabaseHandler(getActivity());
        user = new User();
        user_info.clear();
        user_info=databaseHandler.get_user_information(Helper.GetData(getActivity(),"email"));
        txt_name = (EditText) view.findViewById(R.id.txt_name);
        txt_address=view.findViewById(R.id.txt_current_address);
        email = (EditText) view.findViewById(R.id.txt_email);
        ph = (EditText) view.findViewById(R.id.txt_mobile);
        if(user_info.size()>0){
            txt_name.setText(user_info.get(0).getName());
            email.setText(user_info.get(0).getEmail());
            txt_address.setText(user_info.get(0).getAddress());
            ph.setText(user_info.get(0).getMobile());
        }
        else{
            Toast.makeText(getActivity(), "Opps something went wrong", Toast.LENGTH_SHORT).show();
        }
        btn_change_setting=view.findViewById(R.id.btn_change_setting);
        btn_change_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    AudioManager  audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

                    switch (audioManager.getRingerMode()) {


                        case AudioManager.RINGER_MODE_SILENT:

                            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
                            Toast.makeText(getActivity(),"Silent Mode Off :)",Toast.LENGTH_LONG).show();

                            break;
                        case AudioManager.RINGER_MODE_VIBRATE:
                            Toast.makeText(getActivity(),"Silent Mode Off :)",Toast.LENGTH_LONG).show();
                            int maxVolume_vibrate = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume_vibrate, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);

                            break;
                        case AudioManager.RINGER_MODE_NORMAL:
                            Toast.makeText(getActivity(),"Silent Mode On :(",Toast.LENGTH_LONG).show();
                            audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                            break;
                    }
                }

                catch (Exception e){

                    Toast.makeText(getActivity(),"Not allowed to change Do Not Distrub state",Toast.LENGTH_LONG).show();

                }


            }
        });
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Helper.PutData(getActivity(),"email",null);
                getActivity().finish();
                startActivity(new Intent(getActivity(), activity_login.class));
            }
        });
        return  view;
    }
}

