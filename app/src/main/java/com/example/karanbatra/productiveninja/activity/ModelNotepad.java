package com.example.karanbatra.productiveninja.activity;

/**
 * Created by neeraj.varshney on 6/15/2016.
 */
public class ModelNotepad {
    //
    private int id;
    //
    private String name;
    private String desc;


    public ModelNotepad(int id, String name, String desc) {
        this.id=id;
        this.name = name;
        this.desc = desc;

    }

    public String getDesc() {
        return desc;
    }



    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
