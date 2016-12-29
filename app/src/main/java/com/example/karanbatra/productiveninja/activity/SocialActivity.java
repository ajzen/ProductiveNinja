package com.example.karanbatra.productiveninja.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.karanbatra.productiveninja.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//public class SocialActivity extends AppCompatActivity {
//    ArrayList<ListData> myList = new ArrayList<>();
//    Context context = SocialActivity.this;
//    HashSet<String> present = new HashSet<>();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_social);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        try{
//            DBHelper db = new DBHelper(this);
//            List<Contact> list = db.getCategoryContacts("Social");
//            for(int i = 0;i < list.size(); i++) {
//                if (!present.contains(list.get(i).getName())) {
//                    ApplicationInfo app = getPackageManager().getApplicationInfo(list.get(i).getName(), 0);
//                    Drawable icon = getPackageManager().getApplicationIcon(app);
//                    Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
//                    String name = getPackageManager().getApplicationLabel(app).toString();
//                    ListData ld = new ListData();
//                    ld.setName(name);
//                    int min = list.get(i).getMinutes();
//                    int seconds = list.get(i).getSeconds();
//                    if (min < 10) {
//                        if (seconds < 10) {
//                            ld.setTime("0" + min + " : " + "0" + seconds);
//                        } else {
//                            ld.setTime("0" + min + " : " + seconds);
//                        }
//                    } else {
//                        if (seconds < 10) {
//                            ld.setTime(min + " : " + "0" + seconds);
//                        } else {
//                            ld.setTime(min + " : " + seconds);
//                        }
//                    }
//                    ld.setImgBitMap(bitmap);
//                    myList.add(ld);
//                    present.add(list.get(i).getName());
//                }
//            }
//        }
//        catch(PackageManager.NameNotFoundException e){
//
//        }
//        ListView listView = (ListView)findViewById(R.id.listView);
//        listView.setAdapter(new MyBaseAdapter(context, myList));
//    }
//    @Override
//    public void onBackPressed() {
//        //  super.onBackPressed();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//
//
//    }
//
//}
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class SocialActivity extends AppCompatActivity {
    private PieChart mChart;
    protected String[] mParties = new String[] {
            "Quora", "Facebook", "TrueCaller", "Instagram", "WhatsApp", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//        mChart.setOnChartValueSelectedListener(this);

        setData(4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);


    }
    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry((float) 30, mParties[0]));
        entries.add(new PieEntry((float) 20, mParties[1]));
        entries.add(new PieEntry((float) 30, mParties[2]));
        entries.add(new PieEntry((float) 20, mParties[3]));
//        for (int i = 1; i < count ; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
//        }

        PieDataSet dataSet = new PieDataSet(entries, "Social Usage");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Developed by\nKaran Deep Batra");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 16, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 16, s.length(), 0);
        return s;
    }
}

