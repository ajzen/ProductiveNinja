package com.example.karanbatra.productiveninja.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karanbatra.productiveninja.R;

import java.util.List;


public class AnalyticsFragment extends Fragment {
    View rootView;
    TextView social;
    TextView communication;
    TextView media;
    int social_sec=0;
    int social_min=0;
    int social_hours=0;
    int media_sec=0;
    int media_min=0;
    int media_hours=0;
    int comm_sec=0;
    int comm_min=0;
    int comm_hours=0;
    public AnalyticsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_analytics, container, false);
        DBHelper db = new DBHelper(getContext());
        social = (TextView) rootView.findViewById(R.id.social_textview_value);
        media = (TextView) rootView.findViewById(R.id.media_textview_value);
        communication = (TextView) rootView.findViewById(R.id.communication_textview_value);
        List<Contact> arr = db.getAllContacts();
        for(Contact c : arr){
            if(c.getName().equals("com.android.chrome") || c.getName().equals("com.google.android.gm")){
                comm_hours += c.getHours();
                comm_min += c.getMinutes();
                comm_sec += c.getSeconds();
                communication.setText(comm_hours+":" + comm_min+":"+comm_sec);
            }else if(c.getName().equals("com.google.android.youtube") || c.getName().equals("bitsie.playmee.musicplayer.free")){
                media_hours += c.getHours();
                media_min += c.getMinutes();
                media_sec += c.getSeconds();
                media.setText(media_hours+":" +media_min+":"+media_sec);
            }else if(c.getName().equals("com.whatsapp")){
                social_hours += c.getHours();
                social_min += c.getMinutes();
                social_sec += c.getSeconds();
                social.setText(social_hours+":" +social_min+":"+social_sec);
            }
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBHelper db = new DBHelper(getContext());
        List<Contact> arr = db.getAllContacts();
        for(Contact c : arr){
            if(c.getName().equals("com.android.chrome") || c.getName().equals("com.google.android.gm")){
                comm_hours += c.getHours();
                comm_min += c.getMinutes();
                comm_sec += c.getSeconds();
                communication.setText(comm_hours+":" + comm_min+":"+comm_sec);
            }else if(c.getName().equals("com.google.android.youtube") || c.getName().equals("bitsie.playmee.musicplayer.free")){
                media_hours += c.getHours();
                media_min += c.getMinutes();
                media_sec += c.getSeconds();
                media.setText(media_hours+":" +media_min+":"+media_sec);
            }else if(c.getName().equals("com.whatsapp")){
                social_hours += c.getHours();
                social_min += c.getMinutes();
                social_sec += c.getSeconds();
                social.setText(social_hours+":" +social_min+":"+social_sec);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}