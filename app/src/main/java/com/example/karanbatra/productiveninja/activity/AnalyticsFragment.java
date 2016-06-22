package com.example.karanbatra.productiveninja.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.karanbatra.productiveninja.R;

import java.util.List;


public class AnalyticsFragment extends Fragment {
    View rootView;
    TextView social;
    TextView communication;
    TextView media;
    Button btnSocial;
    Button btnMedia;
    Button btnComm;
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
        social = (TextView) rootView.findViewById(R.id.social_textview_value);
        media = (TextView) rootView.findViewById(R.id.media_textview_value);
        communication = (TextView) rootView.findViewById(R.id.communication_textview_value);

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
                communication.setText(c.getHours()+":" + c.getMinutes()+":"+c.getSeconds());
            }else if(c.getName().equals("com.google.android.youtube")){
                media.setText(c.getHours()+":" +c.getMinutes()+":"+c.getSeconds());
            }else if(c.getName().equals("com.whatsapp")){
                social.setText(c.getHours()+":" +c.getMinutes()+":"+c.getSeconds());
            }
        }

        btnSocial = (Button)rootView.findViewById(R.id.social_button);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String time = social_min+" "+social_seconds;
                startActivity(new Intent(getActivity(), Category.class));
            }
        });


        btnMedia = (Button)rootView.findViewById(R.id.media_button);
        btnMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MediaActivity.class));
            }
        });

        btnComm = (Button)rootView.findViewById(R.id.communication_button);
        btnComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CommunicationActivity.class));
            }
        });
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