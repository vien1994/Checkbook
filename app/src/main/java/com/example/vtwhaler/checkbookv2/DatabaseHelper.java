package com.example.vtwhaler.checkbookv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VTWhaler on 8/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_EXP = "Expenses";
    private static final String expID = "ID";
    private static final String expCat = "Category";
    private static final String expAmt = "Amount";
    private static final String expTag = "Tag";
    private static final String expDate="Date";

    private static final String TABLE_BALANCE= "Balance";
    private static final String balID = "ID";
    private static final String balAmount = "BalAmt";

    private double initAmt = 0;

    NumberFormat formatter = new DecimalFormat("#0.00");

    public DatabaseHelper(Context context) {
        super(context, TABLE_EXP, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " +
                TABLE_EXP + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                expCat + " TEXT, " +
                expAmt + " REAL, " +
                expTag + " TEXT, " +
                expDate + " TEXT);";
        db.execSQL(createTable);
        String createBalance = "CREATE TABLE " + TABLE_BALANCE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                balAmount + " REAL);";
        db.execSQL(createBalance);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_EXP);
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_BALANCE);
        onCreate(db);
    }

    public boolean addData(String category, double amount, String tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String date = getDate();
        contentValues.put(expCat, category);
        contentValues.put(expAmt, amount);
        contentValues.put(expTag, tag);
        contentValues.put(expDate, date);

        Log.d(TAG, "addData: Adding " + amount + ", " + tag + " and " + amount + " to " + TABLE_EXP);

        long result = db.insert(TABLE_EXP, null, contentValues);
        addTransactionBal(amount);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String getDate() {
        SQLiteDatabase db = this.getWritableDatabase();
        String getDate="SELECT date('now')";
        Cursor data =db.rawQuery(getDate,null);
        data.moveToNext();
        String date = data.getString(0);
        String result=new StringBuilder(date).delete(0,5).append("-" + date.substring(0,4)).toString();
        return result;
    }


    public boolean emptyBal() { //Checks if Balance is empty (null)
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP;
        Cursor data = db.rawQuery(query, null);
        if (data.getCount()==0) {
            return true;
        }
       return false;
    }

    public boolean initBal() { //initializes Balance to 0
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(balAmount, initAmt);

        long result = db.insert(TABLE_BALANCE, null, contentValues);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public String getBalDisplay() { //Returns the current Balance as a String
        SQLiteDatabase db = this.getWritableDatabase();
        String result = String.valueOf(formatter.format(getBal()));
        return result;
    }

    // addBal works by adding money TO the balance
    public void addBal(double amtToAdd) { //Try doing it with Content Values later
        SQLiteDatabase db = this.getWritableDatabase();
        double dataVal = getBal() + amtToAdd;
        updateBal(dataVal);
    }


    public void resetBal() {  //resets the balance to 0
        SQLiteDatabase db = this.getWritableDatabase();
        updateBal(initAmt);
    }


    public void addTransactionBal(double amtToAdd) { //Updates the Balance after a transaction has been made
        double curBal = getBal();
        curBal = curBal - amtToAdd;
        updateBal(curBal);
    }

    public void updateBal(double newBal) { //Used by other functions to easily update balance
        SQLiteDatabase db = this.getWritableDatabase();
        String queryUpd = "UPDATE " + TABLE_BALANCE + " SET " + balAmount + " = '" + newBal + "'";
        db.execSQL(queryUpd);
    }


    public boolean deleteAllData() { //Deletes all Transactions. This is mostly used for debugging purposes during development.
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM(" + expAmt + ") FROM " + TABLE_EXP;
        Cursor data = db.rawQuery(query,null);
        data.moveToNext();
        addBal(data.getDouble(0)); //Adds transaction total back to Balance
        long result = db.delete(TABLE_EXP, null, null);
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public double getBal() { //Used by other functions to grab the current balance value.
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BALANCE;
        Cursor data = db.rawQuery(query, null);
        data.moveToNext();
        double result = data.getDouble(1);

        return result;
    }

    public Cursor getBills() { //Grab all the transactions to view under Bills Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='bills'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData() {//Grab all the transactions regardless of Category to view ALL data
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getEntertainment() { //Grab all the transactions to view under Entertainment Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='entertainment'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getFood() { //Grab all the transactions to view under Food Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='food'";
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public Cursor getGas() { //Grab all the transactions to view under Gas Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='gas'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getMisc() { //Grab all the transactions to view under Misc Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='misc'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public String getTotal(String info) { //Grabs the sum of a requested Category
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM(" + expAmt + ") FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='" + info + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToNext();
        String result = String.valueOf(formatter.format(data.getDouble(0)));
        return result;
    }

    public String getAllTotal() { //Grabs the sum of all transactions regardless of Category
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM(" + expAmt + ") FROM " + TABLE_EXP;
        Cursor data = db.rawQuery(query, null);
        data.moveToNext();
        String result = String.valueOf(formatter.format(data.getDouble(0)));
        return result;
    }

}
