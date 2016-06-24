package com.example.karanbatra.productiveninja.activity;

import android.graphics.Bitmap;

/**
 * Created by karan on 22/6/16.
 */
public class CategoryListData {
    String name;
    Bitmap imgBitMap;


    public String getName(){ return this.name; }
    public Bitmap getImgBitMap(){ return this.imgBitMap; }

    public void setName(String name){ this.name = name; }
    public void setImgBitMap(Bitmap imgBitMap){ this.imgBitMap = imgBitMap; }
}
