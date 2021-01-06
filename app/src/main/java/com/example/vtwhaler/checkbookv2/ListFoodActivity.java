package com.example.vtwhaler.checkbookv2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.example.vtwhaler.checkbookv2.Constants.FIRST_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.FOURTH_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.ID_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.SECOND_COLUMN;
import static com.example.vtwhaler.checkbookv2.Constants.THIRD_COLUMN;

/**
 * Created by VTWhaler on 8/10/2017.
 */

public class ListFoodActivity extends ParentListClass {

    private ArrayList<HashMap<String, String>> list;

    DatabaseHelper mDatabaseHelper;
    NumberFormat formatter = new DecimalFormat("#0.00");
    private ListView mListView;
    private TextView textTotal;
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private TextView textMonth;

    Date today = new Date();
    SimpleDateFormat monthFt = new SimpleDateFormat("MM");
    SimpleDateFormat yearFt = new SimpleDateFormat("yyyy");
    String curMonth = monthFt.format(today);
    String curYear = yearFt.format(today);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        mListView = (ListView) findViewById(R.id.listView);
        textTotal = (TextView) findViewById(R.id.textTotal);
        textMonth = (TextView) findViewById(R.id.textMonth);
        leftBtn = (ImageButton) findViewById(R.id.leftBtn);
        rightBtn = (ImageButton) findViewById(R.id.rightBtn);
        mDatabaseHelper = new DatabaseHelper(this);

        setTextMonth();
        populateListView();

        mListView.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Intent intent = new Intent(ListFoodActivity.this, EditTransaction.class);
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

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mnth = Integer.parseInt(curMonth);
                if (mnth - 1 == 0) {
                    mnth = 12;
                    curYear = String.valueOf(Integer.parseInt(curYear) - 1);
                }
                else {
                    mnth = mnth -1;

                }
                if (mnth < 10) {
                    curMonth = "0" + String.valueOf(mnth);
                }
                else {
                    curMonth = String.valueOf(mnth);
                }
                setTextMonth();
                populateListView();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mnth = Integer.parseInt(curMonth);
                if (mnth + 1 == 13) {
                    mnth = 1;
                    curYear = String.valueOf(Integer.parseInt(curYear) + 1);
                }
                else {
                    mnth = mnth + 1;
                }
                if (mnth < 10) {
                    curMonth = "0" + String.valueOf(mnth);
                }
                else {
                    curMonth = String.valueOf(mnth);
                }
                setTextMonth();
                populateListView();
            }
        });

    }

    private void setTextMonth() {
        switch (curMonth) {
            case "01": textMonth.setText("January " + curYear); break;
            case "02": textMonth.setText("February " + curYear); break;
            case "03": textMonth.setText("March " + curYear); break;
            case "04": textMonth.setText("April " + curYear); break;
            case "05": textMonth.setText("May " + curYear); break;
            case "06": textMonth.setText("June " + curYear); break;
            case "07": textMonth.setText("July " + curYear); break;
            case "08": textMonth.setText("August " + curYear); break;
            case "09": textMonth.setText("September " + curYear); break;
            case "10": textMonth.setText("October " + curYear); break;
            case "11": textMonth.setText("November " + curYear); break;
            case "12": textMonth.setText("December "  + curYear); break;
        }
    }

    public void populateListView() {
        Cursor data = mDatabaseHelper.getFood(curMonth, curYear);
        list = new ArrayList<HashMap<String, String>>();
        while (data.moveToNext()) {

            HashMap<String,String> temp=new HashMap<String, String>();
            temp.put(ID_COLUMN, data.getString(0)); //ID
            temp.put(FIRST_COLUMN, data.getString(4)); //Date
            temp.put(SECOND_COLUMN, data.getString(1)); //Category (Such as food)
            temp.put(THIRD_COLUMN, data.getString(3)); //Details of Transaction
            temp.put(FOURTH_COLUMN, String.valueOf(formatter.format(data.getDouble(2)))); //Amount Spent
            list.add(temp);

        }
        data.close();

        String total = "Total: " + mDatabaseHelper.getTotal("food", curMonth, curYear);
        textTotal.setText(total);

        ListViewAdapters adapter = new ListViewAdapters(this, list);
        mListView.setAdapter(adapter);

    }

    public void toastMessage(String msg) {
        super.toastMessage(msg);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }

}
