package com.example.vtwhaler.checkbookv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EditTransaction extends AppCompatActivity  {

    DatabaseHelper mDatabaseHelper;
    private Button btnUpdate, btnDelete;
    private EditText editDate, editTag, editAmt, editCat;

    int id = 0;

    NumberFormat formatter = new DecimalFormat("#0.00");
    private ListView mListView;
    private TextView textTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_transaction);

        //This part adds the top left back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabaseHelper = new DatabaseHelper(this);

        String extra[] = getIntent().getExtras().get("id").toString().split(",");
        //toastMessage(getIntent().getExtras().get("id").toString());

        final int id = Integer.parseInt(getIntent().getExtras().get("id").toString());
        String category = mDatabaseHelper.getExpCat(id);
        String tag = mDatabaseHelper.getExpTag(id);
        String date = mDatabaseHelper.getExpDate(id);
        String originalAmountString = mDatabaseHelper.getExpAmt(id);

        editDate = (EditText) findViewById(R.id.editDate);
        editTag = (EditText) findViewById(R.id.editTag);
        editAmt = (EditText) findViewById(R.id.editAmt);
        editCat = (EditText) findViewById(R.id.editCat);
        btnUpdate = (Button) findViewById(R.id.button_update);;
        btnDelete = (Button) findViewById(R.id.button_delete);

        editDate.setText(date);
        editTag.setText(tag);
        editAmt.setText(originalAmountString);
        editCat.setText(category);

        View view = findViewById(R.id.edit_layout); //To hide keyboard you need to create an ID for the view in the xml file
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(EditTransaction.this);
                    return false;
                }
            });
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDate = editDate.getText().toString();
                String newTag = editTag.getText().toString();
                String newAmt = editAmt.getText().toString();
                String newCat = editCat.getText().toString();

                try {
                    double amt = Double.parseDouble(newAmt);

                    if (editDate.length() != 0 && editTag.length() != 0 && editAmt.length() != 0) {
                        mDatabaseHelper.updateTransaction(id, newDate, newTag, amt, newCat); //IT ADDS THE ORIGINAL AMOUNT EVERYTIME THEY PRESS THE BUTTON WHEN IT SHOULD ONLY DO IT ONCE!
                        toastMessage("Transaction and Balance Updated!");

                    }
                    else {
                        toastMessage("You must put something in the text field!");
                    }
                } catch (NumberFormatException c) {
                    toastMessage("You must put something in the text field!"); //error catch for when amt is blank
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                mDatabaseHelper.addBal(Double.parseDouble(mDatabaseHelper.getExpAmt(id)));
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                finish();
                                break;
                        }

                        boolean result = mDatabaseHelper.deleteTransaction(id);
                        if(result == true) {
                            toastMessage("Transaction Deleted!");
                        }
                        else {
                            toastMessage("Woops! Something went wrong!");
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you want to add this back into your balance?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    //This will add the return to the previous activity once the up/back button is selected on the top left corner
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
