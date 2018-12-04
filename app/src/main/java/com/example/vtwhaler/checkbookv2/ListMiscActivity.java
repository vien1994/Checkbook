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
 * Created by VTWhaler on 8/12/2017.
 */

public class ListMiscActivity extends AppCompatActivity {

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
                Intent intent = new Intent(ListMiscActivity.this, EditTransaction.class);
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
        });
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView");

        Cursor data = mDatabaseHelper.getMisc();
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

        String total = "Total: " + mDatabaseHelper.getTotal("misc");
        textTotal.setText(total);

        ListViewAdapters adapter = new ListViewAdapters(this, list);
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
