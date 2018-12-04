package com.example.vtwhaler.checkbookv2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.vtwhaler.checkbookv2.Constants.FIRST_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.FOURTH_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.ID_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.SECOND_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.THIRD_COLUMN;

public class ListProgress extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> list;
    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;
    NumberFormat formatter = new DecimalFormat("#0.00");
    private ListView mListView;
    private TextView textTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        mListView = (ListView) findViewById(R.id.amtSavedList);
        //textTotal = (TextView) findViewById(R.id.textTotal);
        mDatabaseHelper = new DatabaseHelper(this);

        mDatabaseHelper.initProgress();

        populateListView();
/*
        mListView.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent intent = new Intent(ListBillsActivity.this, EditTransaction.class);
                int num = 0;
                String item = mListView.getItemAtPosition(position).toString();
                String itemParts[] = item.split(",");
                for(int i = 0; i < itemParts.length; i++ ) {
                    if (itemParts[i].contains("ID=") ) {
                        num = i;

                        break;
                    }
                }
                if (itemParts[num].contains("{")) {
                    itemParts[num] = itemParts[num].substring(2);
                }

                if(itemParts[num].contains("}")) {
                    itemParts[num] = itemParts[num].substring(1, itemParts[num].length() -1 );
                }

                String itemID = itemParts[num].substring(4);
                intent.putExtra("id", itemID);
                startActivity(intent);

            }
        }); */
    }

    private void populateListView() {

        Cursor data = mDatabaseHelper.getProgress();
        list = new ArrayList<HashMap<String,String>>();
        while(data.moveToNext()) {

            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(FIRST_COLUMN, data.getString(1)); //ID
            temp.put(SECOND_COLUMN, String.valueOf(formatter.format(data.getDouble(2)))); //Amount Spent
            list.add(temp);

        }
        data.close();

        //String total = "Total: " + mDatabaseHelper.getTotal("bills");
        //textTotal.setText(total);

        ListProgressAdapter adapter = new ListProgressAdapter(this, list);
        mListView.setAdapter(adapter);

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }


}
