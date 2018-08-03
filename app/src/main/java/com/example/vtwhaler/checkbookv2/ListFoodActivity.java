package com.example.vtwhaler.checkbookv2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.vtwhaler.checkbookv2.Constants.FIRST_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.ID_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.SECOND_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.THIRD_COLUMN;

/**
 * Created by VTWhaler on 8/10/2017.
 */

public class ListFoodActivity extends ParentListClass {

    private ArrayList<HashMap<String, String>> list;
    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;
    NumberFormat formatter = new DecimalFormat("#0.00");
    private ListView mListView;
    private TextView textTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        textTotal = (TextView) findViewById(R.id.textTotal);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();

        mListView.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                toastMessage(mListView.getItemAtPosition(position).toString());
                Intent intent = new Intent(ListFoodActivity.this, EditTransaction.class);
                intent.putExtra("id", mListView.getItemAtPosition(position).toString());
                startActivity(intent);

            }
        });
    }

    public void populateListView() {
        Cursor data = mDatabaseHelper.getFood();
        list = new ArrayList<HashMap<String, String>>();
        while (data.moveToNext()) {

            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put(ID_COLUMN, data.getString(0)); //ID
            temp.put(FIRST_COLUMN, data.getString(4));
            temp.put(SECOND_COLUMN, data.getString(3));
            temp.put(THIRD_COLUMN, String.valueOf(formatter.format(data.getDouble(2))));
            list.add(temp);

        }
        data.close();

        String total = "Total: " + mDatabaseHelper.getTotal("food");
        textTotal.setText(total);

        ListViewAdapters adapter = new ListViewAdapters(this, list);
        mListView.setAdapter(adapter);

    }

    public void toastMessage(String msg) {
        super.toastMessage(msg);
    }

}
