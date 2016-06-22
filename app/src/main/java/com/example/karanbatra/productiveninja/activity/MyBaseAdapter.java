package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;

/**
 * Created by KARAN.BATRA on 6/22/2016.
 */
public class MyBaseAdapter extends BaseAdapter{
    ArrayList<ListData> myList = new ArrayList<ListData>();
    LayoutInflater inflater;
    Context context;

    public MyBaseAdapter(Context context, ArrayList myList){
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public int getCount() {
        return myList.size();
    }

    @Override
    public ListData getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        ListData currentListData = getItem(position);

        mViewHolder.name.setText(currentListData.getName());
        mViewHolder.time.setText(currentListData.getTime());
        mViewHolder.ivIcon.setImageBitmap(currentListData.getImgBitMap());

        return convertView;
    }
    private class MyViewHolder {
        TextView name, time;
        ImageView ivIcon;

        public MyViewHolder(View item) {
            name = (TextView) item.findViewById(R.id.list_item_name_detail);
            time = (TextView) item.findViewById(R.id.list_item_time_detail);
            ivIcon = (ImageView) item.findViewById(R.id.list_item_icon);
        }
    }

}
