package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan on 22/6/16.
 */
public class CategoryBaseAdapter extends BaseAdapter {
    ArrayList<CategoryListData> myList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    List<String> categories = new ArrayList<>();
    private Integer[] spinnerPosition;
    ArrayAdapter<String> dataAdapter;

    public CategoryBaseAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        categories.add("Social");
        categories.add("Games");
        categories.add("Media");
        categories.add("Communication");
        categories.add("Other");
        spinnerPosition = new Integer[myList.size()];
        for(int i = 0;i < spinnerPosition.length; i++){
            spinnerPosition[i] = 0;
        }
        dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public CategoryListData getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_list_item, parent, false);
            mViewHolder = new MyViewHolder();
            mViewHolder.name = (TextView) convertView.findViewById(R.id.category_list_name);
            mViewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.category_list_image);
//            mViewHolder.spinner = (Spinner) convertView.findViewById(R.id.category_list_spinner);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }
        CategoryListData currentListData = getItem(position);
        mViewHolder.name.setText(currentListData.getName());
        mViewHolder.ivIcon.setImageBitmap(currentListData.getImgBitMap());
//        mViewHolder.spinner.setAdapter(dataAdapter);
//        mViewHolder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
//                spinnerPosition[position] = p;
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        if (spinnerPosition[position] != null) {
//            mViewHolder.spinner.setSelection(spinnerPosition[position]);
//        } else {
//            mViewHolder.spinner.setSelection(0);
//        }
        return convertView;
    }

    private static class MyViewHolder {
        TextView name;
        ImageView ivIcon;
//        Spinner spinner;
    }

    public Integer[] spinnerSelection() {
        return spinnerPosition;
    }

}
