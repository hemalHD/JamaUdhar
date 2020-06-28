package com.hd.jamaudhar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomerActivity extends AppCompatActivity {

    DataBsaeHelper dataBsaeHelper;
    Cursor cursor;
    Cursor res;
    SQLiteDatabase db;
    String total;
    String qury;
    String number;
    TextView custname,custamt,custcity,custaddress,custnumber,custcompanyname;
    Button custcredit,custdebit;
    ListView custlistview;

    long set;

    ImageView close;
    Spinner spinner1;
    Spinner spinner2;
    TextView type;
    EditText amount;
    spinnerAdapter aa;
    spinnerAdapter aa1;
    Button tranc;
    String spin1nmae, spin1number;
    String spin2name;
    String amt;
    String typestore;
    private long creditvalue;
    private long debitvalue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        bind();
        setdata();
        setlist();
        custcredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tranctionDailog("Credit");
                setdata();
            }
        });
        custdebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tranctionDailog("Debit");

            }
        });
    }
    public void bind(){
        Intent intent=getIntent();
        qury=intent.getStringExtra("cust");
        total=intent.getStringExtra("total");
        number=intent.getStringExtra("unq");
        dataBsaeHelper = new DataBsaeHelper(this);
        db = dataBsaeHelper.getWritableDatabase();
        custname=(TextView)findViewById(R.id.custname);
        custamt=(TextView)findViewById(R.id.custamt);
        custcity=(TextView)findViewById(R.id.custcity);
        custaddress=(TextView)findViewById(R.id.custaddress);
        custnumber=(TextView)findViewById(R.id.custnumber);
        custcompanyname=(TextView)findViewById(R.id.custcompanyname);
        custcredit=(Button)findViewById(R.id.custcredit);
        custdebit=(Button)findViewById(R.id.custdebit);
        custlistview=(ListView)findViewById(R.id.custlistview);
    }
    public void setdata(){
        cursor=db.rawQuery(qury,null);
        while (cursor.moveToNext()){
            custname.setText(cursor.getString(1));
            custnumber.setText(cursor.getString(2));
            number=cursor.getString(2);
            custaddress.setText(cursor.getString(3));
            custcity.setText(cursor.getString(4));
            custcompanyname.setText(cursor.getString(5));
            custamt.setText(total);
        }
    }
    private void loaddata() {
        creditvalue = 0;
        debitvalue = 0;

                Cursor cs = db.rawQuery("select * from TRANSACTION_TABLE where USERID='" + this.number + "' AND TYPE='Credit'", null);
                if (cs.getCount() == 0) {
                    creditvalue = 0;
                    Log.i("helooooo", creditvalue + "");
                } else {
                    while (cs.moveToNext()) {
                        creditvalue = creditvalue + Integer.parseInt(cs.getString(3));
                        Log.i("helooooo", creditvalue + "");
                    }
                }
                Cursor ds = db.rawQuery("select * from TRANSACTION_TABLE where USERID=" + this.number + " AND TYPE='Debit'", null);
                if (ds.getCount() == 0) {
                    debitvalue = 0;
                } else {
                    while (ds.moveToNext()) {
                        debitvalue = debitvalue + Integer.parseInt(ds.getString(3));
                    }
                }
                set=creditvalue - debitvalue;
                custamt.setText(set+"");



    }
    public void setlist(){
        ArrayList type = new ArrayList();
        ArrayList amt = new ArrayList();
        ArrayList item = new ArrayList();
        ArrayList button = new ArrayList();
        ArrayList unique = new ArrayList();

        Cursor res=db.rawQuery("select * from TRANSACTION_TABLE where USERID="+number,null);
        if(res.getCount()==0){

        }else {
            while (res.moveToNext()) {
                unique.add(res.getString(0));
                type.add(res.getString(2));
                amt.add(res.getString(3));
                item.add(res.getString(4));
            }
        }
        custAdpt custadp=new custAdpt(this,type,amt,item,unique);

        custlistview.setAdapter(custadp);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    public void tranctionDailog(String type1) {

        final Dialog dialog = new Dialog(CustomerActivity.this);
        dialog.setContentView(R.layout.tranction_dailog);
        // Set dialog title
        spinner1 = (Spinner) dialog.findViewById(R.id.trancationsuserspin);
        spinner2 = (Spinner) dialog.findViewById(R.id.trancationsitemspin);
        type = (TextView) dialog.findViewById(R.id.typetextview);
        tranc = (Button) dialog.findViewById(R.id.buttontranction);
        amount = (EditText) dialog.findViewById(R.id.amountedittext);
        type.setText(type1);
        //stramount=amount.getText().toString();
        dialog.setTitle("Add Tranction");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        close = (ImageView) dialog.findViewById(R.id.closetranctation);
        ArrayList name = new ArrayList<String>();
        ArrayList number = new ArrayList<String>();
        //Setting the ArrayAdapter data on the Spinner
        res = dataBsaeHelper.getCustSpy(this.number);
        if (res.getCount() != 0) {
            while (res.moveToNext()) {

                name.add(res.getString(1));
                number.add(res.getString(2));


            }
        } else {
            name.add("Add Customer");
            number.add("");
        }
        aa = new spinnerAdapter(CustomerActivity.this, name, number);
        spinner1.setAdapter(aa);

        ArrayList name1 = new ArrayList<String>();
        ArrayList number1 = new ArrayList<String>();
        res = dataBsaeHelper.getItem();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                name1.add(res.getString(1));
                number1.add(res.getString(0));
            }

        } else {
            name1.add("Add Item");
            number1.add("");
        }

        aa1 = new spinnerAdapter(CustomerActivity.this, name1, number1);
        spinner2.setAdapter(aa1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    spin1nmae = ((TextView) view.findViewById(R.id.spinnername)).getText().toString();
                    spin1number = ((TextView) view.findViewById(R.id.spinnernumber)).getText().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                // your code here

                // Get selected row data to show on screen

                    spin2name = ((TextView) v.findViewById(R.id.spinnername)).getText().toString();



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tranc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                amt = amount.getText().toString();
                if(amt.equals("")){
                    Toast.makeText(CustomerActivity.this, "enter amount!!!!", Toast.LENGTH_SHORT).show();
                }else {
                    typestore = type.getText().toString();
                    Log.i("insertion", spin1number + typestore + amt + spin2name);
                    boolean b = dataBsaeHelper.insertDataTranction(spin1number, typestore, amt, spin2name);
                    if (b) {
                        dialog.dismiss();
                        setlist();
                        loaddata();

                    } else {
                        Toast.makeText(CustomerActivity.this, "Enter valid details!", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }
}
