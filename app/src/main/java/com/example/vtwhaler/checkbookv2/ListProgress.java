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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private TextView textYear;
    private ImageButton lftBtn;
    private ImageButton rghtBtn;

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);
        mListView = (ListView) findViewById(R.id.amtSavedList);
        textYear = (TextView) findViewById(R.id.textYear);
        lftBtn = (ImageButton) findViewById(R.id.prgLftBtn);
        rghtBtn = (ImageButton) findViewById(R.id.prgRghtBtn);
        mDatabaseHelper = new DatabaseHelper(this);

        updateYear(0);
        populateListView();

        lftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateYear(-1);
                populateListView();
            }
        });

        rghtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateYear(1);
                populateListView();
            }
        });




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

    private void updateYear(int updateBy) {
        year += updateBy;
        textYear.setText("" + year);
    }

    private void populateListView() {

        Cursor data = mDatabaseHelper.getProgressByYear("" + year);
        list = new ArrayList<HashMap<String,String>>();
        while(data.moveToNext()) {

            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(FIRST_COLUMN, data.getString(1).substring(0, data.getString(1).length() - 4)); //ID
            temp.put(SECOND_COLUMN, String.valueOf(formatter.format(data.getDouble(2)))); //Amount Spent
            list.add(temp);

        }
        data.close();

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
