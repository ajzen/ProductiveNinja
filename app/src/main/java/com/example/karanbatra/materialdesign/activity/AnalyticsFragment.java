package com.example.karanbatra.materialdesign.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karanbatra.materialdesign.R;

import java.util.List;


public class AnalyticsFragment extends Fragment {
    View rootView;
    TextView social;
    TextView communication;
    TextView media;
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
            if(c.getName().equals("com.android.chrome")){
                communication.setText(c.getHours()+":" + c.getMinutes()+":"+c.getSeconds());
            }else if(c.getName().equals("com.google.android.youtube")){
                media.setText(c.getHours()+":" + c.getMinutes()+":"+c.getSeconds());
            }else if(c.getName().equals("com.whatsapp")){
                social.setText(c.getHours()+":" + c.getMinutes()+":"+c.getSeconds());
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
            //retreive app's name and if it is facebook or quora make changes to the database.
            if(c.getName().equals("com.android.chrome")){
                communication.setText(c.getHours()+":" + c.getMinutes()+":"+c.getSeconds());
            }else if(c.getName().equals("com.google.android.youtube")){
                media.setText(c.getHours()+":" + c.getMinutes()+":"+c.getSeconds());
            }else if(c.getName().equals("com.whatsapp")){
                social.setText(c.getHours()+":" + c.getMinutes()+":"+c.getSeconds());
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