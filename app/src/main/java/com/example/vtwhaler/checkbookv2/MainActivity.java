package com.example.vtwhaler.checkbookv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    DatabaseHelper mDatabaseHelper;
    Jokes joke = new Jokes();
    private Button btnAdd, btnViewData, btnDeleteAll, btnFood;
    private EditText editTextAmt, editTextTag;
    private AutoCompleteTextView editTextCat;
    private TextView textBalance, textJoke;

    private String jokeString;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View view = findViewById(R.id.drawer_layout);
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }
            });
        }

        editTextAmt = (EditText) findViewById(R.id.editTextAmt);
        editTextCat = (AutoCompleteTextView) findViewById(R.id.editTextCat);
        editTextTag = (EditText) findViewById(R.id.editTextTag);
        btnAdd = (Button) findViewById(R.id.button_submit);
        btnViewData = (Button) findViewById(R.id.buttonView);
        //btnDeleteAll = (Button) findViewById(R.id.button_deleteAll);
        textBalance = (TextView) findViewById(R.id.textBalance);
        textJoke = (TextView) findViewById(R.id.textViewJoke);
        mDatabaseHelper = new DatabaseHelper(this);

        if (mDatabaseHelper.emptyBal()) {
            mDatabaseHelper.initBal();
        }

        updateTextBal();

        String[] categories = getResources().getStringArray(R.array.categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        editTextCat.setAdapter(adapter);

        checkProgress();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = editTextCat.getText().toString();
                String amtTemp = editTextAmt.getText().toString();
                try {
                    double amt = Double.parseDouble(amtTemp);

                    String tag = editTextTag.getText().toString();
                    if (editTextAmt.length() != 0 && editTextCat.length() != 0 && editTextTag.length() != 0) {
                        AddData(category, amt, tag);
                        editTextAmt.setText("");
                        editTextCat.setText("");
                        editTextTag.setText("");
                    }
                    else {
                        toastMessage("You must put something in the text field!");
                    }
                } catch (NumberFormatException c) {
                    toastMessage("You must put something in the text field!"); //error catch for when amt is blank
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });


        /* This is old for resetting data and an old no longer existing button
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
*/


        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
        });

    }

// FINISH HERE
    public void checkProgress() {

        //Calendar.MONTH starts at 0. So 11 is December, but we're treating it like November anyways since we only want to check the previous month.
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) ;
        int year = now.get(Calendar.YEAR);

        if(month == 0) {
            month = 12;
        }


        boolean isChecked = mDatabaseHelper.progressCheck(month, year);

        if(isChecked == false) {
            mDatabaseHelper.insertProgress(month, year, mDatabaseHelper.getBal());
            mDatabaseHelper.resetBal();
            toastMessage("Previous Budget Saved AND your balance was reset (;");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //Settings button was here before

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            textBalance.setText(mDatabaseHelper.getBalDisplay());
        } else if (id == R.id.nav_balance) {
            Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_food) {
            Intent intent = new Intent(MainActivity.this, ListFoodActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_entertainment) {
            Intent intent = new Intent(MainActivity.this, ListEntertainmentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gas) {
            Intent intent = new Intent(MainActivity.this, ListGasActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bills) {
            Intent intent = new Intent(MainActivity.this, ListBillsActivity.class);
            startActivity(intent);
        } else if (id== R.id.nav_misc) {
            Intent intent = new Intent(MainActivity.this, ListMiscActivity.class);
            startActivity(intent);
        } else if (id==R.id.nav_progress) {
            Intent intent = new Intent(MainActivity.this, ListProgress.class);
            startActivity(intent);
        } else if(id==R.id.nav_reset) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            deleteData();
                            //finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            finish();
                            break;
                    }


                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to reset everything?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void deleteData() {
        mDatabaseHelper.deleteAllData();
        updateTextBal();

        toastMessage("Checkbook has been reset");
    }

    public void AddData(String category, double amount, String tag) {
        boolean insertData = mDatabaseHelper.addData(category, amount, tag);
        updateTextBal();
        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        }
        else {
            toastMessage("Something went wrong");
        }
    }

    public void updateTextBal() {
        textBalance.setText(mDatabaseHelper.getBalDisplay());
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTextBal();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    public void handleShakeEvent(int count) {
        jokeString = joke.getJoke();
        textJoke.setText(jokeString);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
