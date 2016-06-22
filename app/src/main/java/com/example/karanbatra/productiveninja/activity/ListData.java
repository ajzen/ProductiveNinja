package com.example.karanbatra.productiveninja.activity;

import android.graphics.Bitmap;

/**
 * Created by KARAN.BATRA on 6/22/2016.
 */
public class ListData {
    String name;
    String time;
    Bitmap imgBitMap;

    public String getName(){ return this.name; }
    public String getTime(){ return this.time; }
    public Bitmap getImgBitMap(){ return this.imgBitMap; }

    public void setName(String name){ this.name = name; }
    public void setTime(String time){ this.time = time; }
    public void setImgBitMap(Bitmap imgBitMap){ this.imgBitMap = imgBitMap; }
}
