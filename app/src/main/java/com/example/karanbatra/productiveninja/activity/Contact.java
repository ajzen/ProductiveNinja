package com.example.karanbatra.productiveninja.activity;

/**
 * Created by neeraj.varshney on 6/3/2016.
 */
public class Contact {
    int _id;
    String _name;
    int seconds;
    int minutes;
    int hours;
    int max_sec;
    String category;

    public Contact(){   }

    public Contact(int id, String name, int seconds, int minutes, int hours, String category,int max_sec){
        this._id = id;
        this._name = name;
        this.seconds =seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.category=category;
        this.max_sec=max_sec;
    }

    public Contact(String name, int seconds, int minutes, int hours, String category,int max_sec){
        this._name = name;
this.max_sec=max_sec;
        this.seconds = seconds;
        this.minutes=minutes;
        this.hours = hours;
        this.category = category;
    }
    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }

    public int getSeconds() {
        return seconds;
    }


    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }


    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMax_sec() {
        return max_sec;
    }

    public void setMax_sec(int max_sec) {
        this.max_sec = max_sec;
    }

    public String getCategory(){ return category;}
    public void setCategory(String category){ this.category = category;}

}