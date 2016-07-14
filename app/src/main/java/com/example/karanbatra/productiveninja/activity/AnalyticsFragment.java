package com.example.karanbatra.productiveninja.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karanbatra.productiveninja.R;

import java.util.List;


public class AnalyticsFragment extends Fragment implements Animation.AnimationListener  {
    View rootView;
    TextView social;
    TextView communication;
    TextView media;
    TextView games;
    Button btnSocial;
    Button btnMedia;
    Button btnComm;
    Button btnGames;
    ImageView bonus;
    TextView fivetext;
    Animation animFadein;
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
        games = (TextView)rootView.findViewById(R.id.games_textview_value);
        fivetext=(TextView)rootView.findViewById(R.id.fivetext);
        bonus=(ImageView)rootView.findViewById(R.id.bonus);
        bonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 DBHelper db = new DBHelper(getContext());
                List<Contact> contacts = db.getAllContacts();
                for(Contact cn : contacts) {
                    db.updateContact(new Contact(cn.getID(), cn.getName(), cn.getSeconds(), cn.getMinutes(), cn.getHours() , cn.getCategory(),cn.getMax_sec()+5));
                    bonus.startAnimation(animFadein);
                }
            }
        });
        animFadein = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.rotate);
        animFadein.setAnimationListener(this);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        int social_hours = 0;
        int social_minutes = 0;
        int social_seconds = 0;
        int media_hours=0;
        int media_minutes=0;
        int media_seconds=0;
        int comm_hours=0;
        int comm_minutes=0;
        int comm_seconds=0;
        int games_hours = 0;
        int games_minutes = 0;
        int games_seconds = 0;
        super.onResume();
        DBHelper db = new DBHelper(getContext());
        List<Contact> list = db.getCategoryContacts("Social");
        for(int i = 0;i < list.size(); i++){
            social_hours+=list.get(i).getHours();
            social_minutes+=list.get(i).getMinutes();
            social_seconds+=list.get(i).getSeconds();
            if(social_seconds > 59){
                social_minutes+=1;
                social_seconds=0;
            }
        }
        social.setText(social_hours+":"+social_minutes+":"+social_seconds);


        List<Contact> list_media = db.getCategoryContacts("Media");
        for(int i = 0;i < list_media.size(); i++){
            media_hours+=list_media.get(i).getHours();
            media_minutes+=list_media.get(i).getMinutes();
            media_seconds+=list_media.get(i).getSeconds();
            if(media_seconds > 59){
                media_minutes+=1;
                media_seconds=0;
            }
        }
        media.setText(media_hours+":"+media_minutes+":"+media_seconds);

        List<Contact> list_comm = db.getCategoryContacts("Communication");
        for(int i = 0;i < list_comm.size(); i++){
            comm_hours+=list_comm.get(i).getHours();
            comm_minutes+=list_comm.get(i).getMinutes();
            comm_seconds+=list_comm.get(i).getSeconds();
            if(comm_seconds > 59){
                comm_minutes+=1;
                comm_seconds=0;
            }
        }
        communication.setText(comm_hours+":"+comm_minutes+":"+comm_seconds);

        List<Contact> list_games = db.getCategoryContacts("Games");
        for(int i = 0;i < list_games.size(); i++){
            games_hours+=list_games.get(i).getHours();
            games_minutes+=list_games.get(i).getMinutes();
            games_seconds+=list_games.get(i).getSeconds();
            if(games_seconds > 59){
                games_minutes+=1;
                games_seconds=0;
            }
        }
        games.setText(games_hours+":"+games_minutes+":"+games_seconds);

        btnSocial = (Button)rootView.findViewById(R.id.social_button);
        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SocialActivity.class));
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

        btnGames = (Button)rootView.findViewById(R.id.games_button);
        btnGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GamesActivity.class));
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

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {


    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}