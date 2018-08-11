package com.example.vtwhaler.checkbookv2;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.vtwhaler.checkbookv2.Constants.FIRST_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.FOURTH_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.ID_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.SECOND_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.THIRD_COLUMN;

/**
 * Created by VTWhaler on 8/11/2017.
 */

public class ListGasActivity extends AppCompatActivity {

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
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        Cursor data = mDatabaseHelper.getGas();
        list = new ArrayList<HashMap<String,String>>();
        while(data.moveToNext()) {

            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(ID_COLUMN, data.getString(0)); //ID
            temp.put(FIRST_COLUMN, data.getString(4)); //Date
            temp.put(SECOND_COLUMN, data.getString(1)); //Category (Such as food)
            temp.put(THIRD_COLUMN, data.getString(3)); //Details of Transaction
            temp.put(FOURTH_COLUMN, String.valueOf(formatter.format(data.getDouble(2)))); //Amount Spent
            list.add(temp);

        }
        data.close();

        String total = "Total: " + mDatabaseHelper.getTotal("gas"); //change to transportation later
        textTotal.setText(total);

        ListViewAdapters adapter = new ListViewAdapters(this, list);
        mListView.setAdapter(adapter);

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
