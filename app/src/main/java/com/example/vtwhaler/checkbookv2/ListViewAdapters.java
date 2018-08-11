package com.example.vtwhaler.checkbookv2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.vtwhaler.checkbookv2.Constants.FIRST_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.FOURTH_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.SECOND_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.THIRD_COLUMN;

/**
 * Created by VTWhaler on 8/10/2017.
 */

public class ListViewAdapters extends BaseAdapter {


    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;

    public ListViewAdapters (Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.column_row, null);

        }

        txtFirst=(TextView) convertView.findViewById(R.id.date); //Have this section outside of if statement to avoid scrolling bug
        txtSecond=(TextView) convertView.findViewById(R.id.tag);
        txtThird=(TextView) convertView.findViewById(R.id.amount);

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN)); // First column is the Date
        txtSecond.setText(map.get(THIRD_COLUMN)); // Second column is the tag/details
        txtThird.setText(map.get(FOURTH_COLUMN)); // Fourth Column is amt and Third is the category.

        return convertView;
    }
}
