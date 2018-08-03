package com.example.vtwhaler.checkbookv2;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EditTransaction extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd;
    private TextView editId, editTag, editAmt;
    private EditText editDate;


    NumberFormat formatter = new DecimalFormat("#0.00");
    private ListView mListView;
    private TextView textTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_transaction);
        mDatabaseHelper = new DatabaseHelper(this);


        String extra[] = getIntent().getExtras().get("id").toString().split(",");
        String tag = extra[0].substring(8, extra[0].length());
        String amount = extra[1].substring(7, extra[1].length());
        String date = extra[3].substring(7, extra[3].length()-1);
        int id = Integer.parseInt( extra[2].substring(4,extra[2].length()));

        editDate = (EditText) findViewById(R.id.editDate);
        editId = (EditText) findViewById(R.id.editId);
        editTag = (EditText) findViewById(R.id.editTag);
        editAmt = (EditText) findViewById(R.id.editAmt);

        editId.setText(id);
        editDate.setText(date);
        editTag.setText(tag);
        editAmt.setText(amount);


    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}