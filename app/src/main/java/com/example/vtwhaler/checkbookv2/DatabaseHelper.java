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

    private static final String TABLE_PROGRESS = "Progress";
    private static final String monthID = "monthID";
    private static final String amtSaved = "amtSaved";

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

        String createProgress = "CREATE TABLE " + TABLE_PROGRESS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + monthID + " TEXT, " + amtSaved + " REAL);";
        db.execSQL(createProgress);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int j) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BALANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        onCreate(db);
    }

//PROGRESS TABLE HERE

    //This is for testing purposes
    public void initProgress() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(monthID, "January 2018");
        contentValues.put(amtSaved, -12);

        db.insert(TABLE_PROGRESS, null, contentValues);
    }

    public Cursor getProgressByYear(String year) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PROGRESS+ " WHERE " + monthID + " LIKE '%" + year + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //ProgressCheck returns true if the app has updated already
    public boolean progressCheck(int month, int year) {

        SQLiteDatabase db = this.getWritableDatabase();

        String monthString = convertMonth(month);

        String query = "SELECT * FROM " + TABLE_PROGRESS+ " WHERE " + monthID + " LIKE '%" + monthString + "%" + year +"'";
        Cursor data = db.rawQuery(query, null);
        if(data.getCount() == 0) {
            return false;
        }

        return true;
    }

    public String convertMonth(int month) {
        String monthString = "";
        switch (month) {
            case 1:  monthString = "January";
                break;
            case 2:  monthString = "February";
                break;
            case 3:  monthString = "March";
                break;
            case 4:  monthString = "April";
                break;
            case 5:  monthString = "May";
                break;
            case 6:  monthString = "June";
                break;
            case 7:  monthString = "July";
                break;
            case 8:  monthString = "August";
                break;
            case 9:  monthString = "September";
                break;
            case 10: monthString = "October";
                break;
            case 11: monthString = "November";
                break;
            case 12: monthString = "December";
                break;
        }

        return monthString;
    }

    public void insertProgress(int month, int year, double balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        String monthString = convertMonth(month);

        val.put(monthID, monthString + " " + year);
        val.put(amtSaved, balance);

        db.insert(TABLE_PROGRESS, null, val);

    }


    //PROGRESS TABLE ENDS HERE



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

    public void updateTransaction(int id, String date, String tag, double newAmount, String category) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryRefund = "SELECT " + expAmt + " FROM " + TABLE_EXP + " WHERE ID = " + id;
        Cursor data = db.rawQuery(queryRefund, null);
        data.moveToNext();
        double oldAmt = data.getDouble(0);
        addBal(oldAmt);

        String queryUpd = "UPDATE " + TABLE_EXP
                + " SET " + expDate + " = '" + date + "'" + ", "
                + expTag + " = '" + tag + "'" + ", "
                + expAmt + " = '" + newAmount + "' , "
                + expCat + " = '" + category + "' WHERE ID = " + id ;
        db.execSQL(queryUpd);
        addTransactionBal(newAmount);


    }


    public void deleteAllData() { //Deletes all Transactions. This is mostly used for debugging purposes during development.
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM(" + expAmt + ") FROM " + TABLE_EXP;
        Cursor data = db.rawQuery(query,null);
        data.moveToNext();
        addBal(data.getDouble(0)); //Adds transaction total back to Balance
        db.execSQL("delete from "+ TABLE_EXP);
        db.execSQL("delete from " + TABLE_PROGRESS);
        //db.execSQL("delete from " + TABLE_BALANCE);

    }

    public boolean deleteTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_EXP + " WHERE ID = " + id;
        return db.delete(TABLE_EXP, "ID =" + id, null) > 0;
    }

    public double getBal() { //Used by other functions to grab the current balance value.
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_BALANCE;
        Cursor data = db.rawQuery(query, null);
        data.moveToNext();
        double result = data.getDouble(1);

        return result;
    }

    //Date is stored as text. Work with it from there.
    public Cursor getDataByMonth(String month, String year) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getBills(String month, String year) { //Grab all the transactions to view under Bills Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='bills' AND " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData() {//Grab all the transactions regardless of Category to view ALL data
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getEntertainment(String month, String year) { //Grab all the transactions to view under Entertainment Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='entertainment' AND " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getFood(String month, String year) { //Grab all the transactions to view under Food Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='food' AND " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        return data;

    }

    public Cursor getGas(String month, String year) { //Grab all the transactions to view under Gas Category
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='transportation' AND " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getMisc(String month, String year) { //Grab all the transactions to view under Misc Category. CHANGE IT SO ITS EVERYTHING ELSE TOO
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='misc' AND " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public String getTotal(String info, String month, String year) { //Grabs the sum of a requested Category
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM(" + expAmt + ") FROM " + TABLE_EXP + " WHERE lower(" + expCat + ")='" + info + "' AND " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToNext();
        String result = String.valueOf(formatter.format(data.getDouble(0)));
        return result;
    }

    public String getAllTotal(String month, String year) { //Grabs the sum of all transactions regardless of Category
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM(" + expAmt + ") FROM " + TABLE_EXP  + " WHERE " + expDate + " LIKE '%" + month + "-%%-" + year + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToNext();
        String result = String.valueOf(formatter.format(data.getDouble(0)));
        return result;
    }

    //getters here
    public String getExpDate (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + expDate + " FROM " + TABLE_EXP  + " WHERE ID = " + id;
        Cursor data =db.rawQuery(query,null);
        data.moveToNext();
        String date = data.getString(0);

        return date;
    }

    public String getExpCat (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + expCat  + " FROM " + TABLE_EXP  + " WHERE ID = " + id;
        Cursor data =db.rawQuery(query,null);
        data.moveToNext();
        String category = data.getString(0);

        return category;
    }

    public String getExpAmt (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + expAmt  + " FROM " + TABLE_EXP  + " WHERE ID = " + id;
        Cursor data =db.rawQuery(query,null);
        data.moveToNext();
        String amt = data.getString(0);

        return amt;
    }

    public String getExpTag (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + expTag  + " FROM " + TABLE_EXP  + " WHERE ID = " + id;
        Cursor data =db.rawQuery(query,null);
        data.moveToNext();
        String tag = data.getString(0);

        return tag;
    }

}
