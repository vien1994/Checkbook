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
import static com.example.vtwhaler.checkbookv2.Constants.SECOND_COLUMN;

public class ListProgressAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;


    public ListProgressAdapter (Activity activity, ArrayList<HashMap<String, String>> list){
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

            convertView=inflater.inflate(R.layout.progress_list, null);

        }

        txtFirst=(TextView) convertView.findViewById(R.id.progressMonth); //Have this section outside of if statement to avoid scrolling bug
        txtSecond=(TextView) convertView.findViewById(R.id.progressAmt);

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN)); // First column is the Date
        txtSecond.setText(map.get(SECOND_COLUMN)); // Second column is the amt

        return convertView;
    }
}
