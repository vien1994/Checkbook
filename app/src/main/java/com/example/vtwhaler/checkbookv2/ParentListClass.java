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


import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.vtwhaler.checkbookv2.Constants.FIRST_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.SECOND_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.THIRD_COLUMN;

public class ParentListClass extends AppCompatActivity {
    private ArrayList<HashMap<String, String>> list;
    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;
    NumberFormat formatter = new DecimalFormat("#0.00");
    private ListView mListView;
    private TextView textTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        textTotal = (TextView) findViewById(R.id.textTotal);
        mDatabaseHelper = new DatabaseHelper(this);

    }

    private void itemClickListener() {
        mListView.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                toastMessage(mListView.getItemAtPosition(position).toString());
                Intent intent = new Intent(ParentListClass.this, EditTransaction.class);
                intent.putExtra("id", mListView.getItemAtPosition(position).toString());
                startActivity(intent);

            }
        });
    }

    void populateListView(String methodName)throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

            Cursor data = runMethod(mDatabaseHelper, methodName);
            //Cursor data = mDatabaseHelper.getFood();
            list = new ArrayList<HashMap<String, String>>();
            while (data.moveToNext()) {

                HashMap<String, String> temp = new HashMap<String, String>();
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

    private static Cursor runMethod(Object instance, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

            Method method = instance.getClass().getMethod(methodName);
            return (Cursor) method.invoke(instance);

    }

    void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}
