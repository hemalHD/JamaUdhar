package com.hd.jamaudhar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class DataBsaeHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "login.dataBsaeHelper";


    public static final String SHOP_TABLE = "SHOP";
    public static final String SHOP_ID = "ID";
    public static final String SHOP_SHOPNAME = "SHOPNAME";
    public static final String SHOP_MOBILENUMBER = "MOBILENUMBER";
    public static final String SHOP_EMAILID = "EMAILID";
    public static final String SHOP_CONTACTPERSON = "CONTACTPERSON";


    //
    public static final String CUST_TABLE = "CUST";
    public static final String CUST_ID = "ID";
    public static final String CUST_CUSTNAME = "CUSTNAME";
    public static final String CUST_CUSTMOBILENUMBER = "CUSTMOBILENUMBER";
    public static final String CUST_CUSTADDRESS = "CUSTADDRESS";
    public static final String CUST_CUSTCITY = "CUSTCITY";
    public static final String CUST_CUSTCOMPANYNAME = "CUSTCOMPANYNAME";

    //
    public static final String ITEM_TABLE = "ITEM";
    public static final String ITEM_ID = "ID";
    public static final String ITEM_NAME = "CUSTNAME";

    //
    public static final String TRANSACTION_TABLE="TRANSACTION_TABLE";
    public static final String TRANSACTION_ID="ID";
    public static final String TRANSACTION_USERID="USERID";
    public static final String TRANSACTION_TYPE="TYPE";
    public static final String TRANSACTION_AMOUNT="AMOUNT";
    public static final String TRANSACTION_ITEM="ITEMNAME";



    public DataBsaeHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + SHOP_TABLE +" ("+ SHOP_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ SHOP_SHOPNAME +" TEXT,"+ SHOP_MOBILENUMBER +" TEXT NOT NULL UNIQUE,"+ SHOP_EMAILID +" TEXT,"+ SHOP_CONTACTPERSON +" TEXT)");
        sqLiteDatabase.execSQL("create table " + CUST_TABLE +" ("+ CUST_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ CUST_CUSTNAME +" TEXT,"+ CUST_CUSTMOBILENUMBER +" TEXT NOT NULL UNIQUE,"+ CUST_CUSTADDRESS +" TEXT,"+ CUST_CUSTCITY +" TEXT,"+ CUST_CUSTCOMPANYNAME +" TEXT)");
        sqLiteDatabase.execSQL("create table " + ITEM_TABLE +" ("+ ITEM_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ITEM_NAME +" TEXT)");
        sqLiteDatabase.execSQL("create table " + TRANSACTION_TABLE +" ("+ TRANSACTION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ TRANSACTION_USERID +" TEXT,"+TRANSACTION_TYPE+" TEXT,"+TRANSACTION_AMOUNT+" TEXT,"+TRANSACTION_ITEM+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insertDataCust(String name, String number, String address, String city, String companyname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUST_CUSTNAME,name);
        contentValues.put(CUST_CUSTMOBILENUMBER,number);
        contentValues.put(CUST_CUSTADDRESS,address);
        contentValues.put(CUST_CUSTCITY,city);
        contentValues.put(CUST_CUSTCOMPANYNAME,companyname);
        long result = db.insert(CUST_TABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean UpdateCust(String name, String number, String address, String city, String companyname,String unq) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CUST_CUSTNAME,name);
        contentValues.put(CUST_CUSTMOBILENUMBER,number);
        contentValues.put(CUST_CUSTADDRESS,address);
        contentValues.put(CUST_CUSTCITY,city);
        contentValues.put(CUST_CUSTCOMPANYNAME,companyname);
        long result = db.update(CUST_TABLE,contentValues,"CUSTMOBILENUMBER="+unq ,null);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertDataShop(String shopname, String mobilenumber, String emailid, String contactperson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SHOP_SHOPNAME,shopname);
        contentValues.put(SHOP_MOBILENUMBER,mobilenumber);
        contentValues.put(SHOP_EMAILID,emailid);
        contentValues.put(SHOP_CONTACTPERSON,contactperson);
        long result = db.insert(SHOP_TABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean UpdateShop(String shopname, String mobilenumber, String emailid, String contactperson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SHOP_SHOPNAME,shopname);
        contentValues.put(SHOP_MOBILENUMBER,mobilenumber);
        contentValues.put(SHOP_EMAILID,emailid);
        contentValues.put(SHOP_CONTACTPERSON,contactperson);
        long result = db.update(SHOP_TABLE,contentValues,"ID=1" ,null);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertDataTranction(String userid, String type, String amount, String itemname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTION_USERID,userid);
        contentValues.put(TRANSACTION_TYPE,type);
        contentValues.put(TRANSACTION_AMOUNT,amount);
        contentValues.put(TRANSACTION_ITEM,itemname);

        long result = db.insert(TRANSACTION_TABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getShop() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+SHOP_TABLE,null);
        return res;
    }

    public Cursor getCust() {
            SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+CUST_TABLE,null);
        return res;
    }
    public Cursor getCustSpy(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+CUST_TABLE+" where CUSTMOBILENUMBER="+id,null);
        return res;
    }

    public boolean insertDataItem(String shopname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME,shopname);

        long result = db.insert(ITEM_TABLE,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getItem() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ITEM_TABLE,null);
        return res;
    }
    public Cursor gettranc() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TRANSACTION_TABLE,null);
        return res;
    }

}
