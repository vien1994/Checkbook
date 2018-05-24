package com.example.vtwhaler.checkbookv2;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by VTWhaler on 8/11/2017.
 */

public class BalanceActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;

    private TextView textBalance;
    private Button btnReset, btnAdd;
    private EditText editAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        mDatabaseHelper = new DatabaseHelper(this);

        textBalance = (TextView) findViewById(R.id.textActBal);
        btnReset = (Button) findViewById(R.id.button_reset);
        btnAdd = (Button) findViewById(R.id.button_add);
        editAdd = (EditText) findViewById(R.id.editTextAdd);

        if(mDatabaseHelper.emptyBal() == true) {
            mDatabaseHelper.initBal();
        }

        updateBalDisplay();

        setUpUI();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addAmt = editAdd.getText().toString();

                try {
                    double amt = Double.parseDouble(addAmt);

                    if (editAdd.length() != 0) {
                        mDatabaseHelper.addBal(amt); //addBal is broken
                        updateBalDisplay();
                        editAdd.setText("");
                    }
                    else {
                        toastMessage("You must put something in the text field!");
                    }
                } catch (NumberFormatException c) {
                    toastMessage("You must put something in the text field!"); //error catch for when amt is blank
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabaseHelper.resetBal(); //addBal is broken
                updateBalDisplay();
            }
        });

    }

    public void setUpUI() {
        View view = findViewById(R.id.balConLayout);
        View view2 = findViewById(R.id.activity_main_drawer);
        if (!(view instanceof EditText) || !(view2 instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(BalanceActivity.this);
                    return false;
                }
            });
        }
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    public void updateBalDisplay() {
        textBalance.setText(mDatabaseHelper.getBalDisplay());
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
