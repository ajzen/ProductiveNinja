package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;

/**
 * Created by karan on 22/6/16.
 */
public class CategoryBaseAdapter extends BaseAdapter{
    ArrayList<CategoryListData> myList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    ArrayAdapter<String> adapter;
    public CategoryBaseAdapter(Context context, ArrayList myList, ArrayAdapter<String> adapter){
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.adapter = adapter;
    }

    public int getCount() {
        return myList.size();
    }

    @Override
    public CategoryListData getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        CategoryListData currentListData = getItem(position);
        mViewHolder.name.setText(currentListData.getName());
        mViewHolder.ivIcon.setImageBitmap(currentListData.getImgBitMap());
        mViewHolder.spinner.setAdapter(adapter);
        return convertView;
    }
    private class MyViewHolder {
        TextView name;
        ImageView ivIcon;
        Spinner spinner;
        public MyViewHolder(View item) {
            name = (TextView) item.findViewById(R.id.category_list_name);
            ivIcon = (ImageView) item.findViewById(R.id.category_list_image);
            spinner = (Spinner) item.findViewById(R.id.category_list_spinner);
        }
    }

}
