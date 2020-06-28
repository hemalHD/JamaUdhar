package com.hd.jamaudhar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CustomerDisplay extends AppCompatActivity {
    EditText name, number, address, city, companyname;
    Button add,update;
    DataBsaeHelper dataBsaeHelper;
    Cursor cursor;
    ImageView close;
    String unique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_display);
        dataBsaeHelper=new DataBsaeHelper(this);
        setlist();
    }
    public final void setlist(){
        ArrayList adapter = new ArrayList();
        ArrayList adapter1 = new ArrayList();
        ArrayList unique = new ArrayList();
        Cursor res=new DataBsaeHelper(this).getCust();
        if(res.getCount()==0){
            adapter.add("nothing to show");
            unique.add(null);
        }else {
            while (res.moveToNext()) {
                adapter.add( res.getString(1) );
                adapter1.add(res.getString(2));
                unique.add(res.getString(2));
            }
        }
        SetAdapter setAdapter=new SetAdapter(this,adapter,adapter1,"CUST","CUSTMOBILENUMBER",unique);
        ListView listView = (ListView) findViewById(R.id.listwiewcustomer);
        listView.setAdapter(setAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public class SetAdapter extends BaseAdapter {
        Activity context;
        ArrayList<String> display;
        ArrayList<String> display1;
        ArrayList<String> unq;
        String tablename,colname;
        TextView displaytext;
        TextView displaytext1;
        LinearLayout linearlist;
        ImageView delete;
        DataBsaeHelper dataBsaeHelper;
        SQLiteDatabase db;
        public SetAdapter(Activity context,ArrayList<String> display,ArrayList<String> display1,String tablename,String colname,ArrayList<String> unq) {
            dataBsaeHelper=new DataBsaeHelper(context);
            db = dataBsaeHelper.getWritableDatabase();
            this.context=context;
            this.display=display;
            this.unq=unq;
            this.tablename=tablename;
            this.colname=colname;
            this.display1=display1;

        }

        @Override
        public int getCount() {
            return display.size();
        }

        @Override
        public Object getItem(int i) {
            return display.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem, null, true);

            displaytext=(TextView)rowView.findViewById(R.id.label);
            displaytext1=(TextView)rowView.findViewById(R.id.label1);
            delete=(ImageView)rowView.findViewById(R.id.delete);
            linearlist=(LinearLayout)rowView.findViewById(R.id.linearlist);
            displaytext.setText(display.get(i)+"");
            displaytext1.setText(display1.get(i)+"");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    db.execSQL("delete from "+tablename+" where "+colname+"='"+unq.get(i)+"'");
                                    if(tablename.equals("CUST")){
                                        db.execSQL("delete from TRANSACTION_TABLE where USERID='"+unq.get(i)+"'");
                                    }
                                    display.remove(i);
                                    display1.remove(i);
                                    unq.remove(i);
                                    notifyDataSetChanged();
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
            linearlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customeradd(unq.get(i));

                }
            });

            return rowView;

        }
    }
    public void customeradd(String uq) {
        final Dialog dialog = new Dialog(CustomerDisplay.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.edit_cust);
        // Set dialog title
        dialog.setTitle("Add Customer");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);


        name = (EditText) dialog.findViewById(R.id.customername1);
        number = (EditText) dialog.findViewById(R.id.customermobilenumber1);
        address = (EditText) dialog.findViewById(R.id.customeraddress1);
        city = (EditText) dialog.findViewById(R.id.customercity1);
        companyname = (EditText) dialog.findViewById(R.id.customercompanyname1);
        add = (Button) dialog.findViewById(R.id.buttonaddcustomer1);
        update = (Button) dialog.findViewById(R.id.button1addcustomer1);
        close=(ImageView)dialog.findViewById(R.id.closecust1);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cursor = dataBsaeHelper.getCustSpy(uq);
        while (cursor.moveToNext()) {

            name.setText(cursor.getString(1));

            number.setText(cursor.getString(2));
            unique=cursor.getString(2);
            address.setText(cursor.getString(3));

            city.setText(cursor.getString(4));

            companyname.setText(cursor.getString(5));

            update.setVisibility(View.VISIBLE);
            update.setText("Edit");

        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name.setFocusableInTouchMode(true);
                name.setFocusable(true);

                number.setFocusableInTouchMode(true);
                number.setFocusable(true);

                address.setFocusableInTouchMode(true);
                address.setFocusable(true);

                city.setFocusableInTouchMode(true);
                city.setFocusable(true);

                companyname.setFocusableInTouchMode(true);
                companyname.setFocusable(true);
                add.setVisibility(View.VISIBLE);
                add.setText("Update");
                update.setVisibility(View.GONE);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter Name", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else if (number.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter MobileNumber", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else if (address.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter Address", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else if (city.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter City", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else if (companyname.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter Company", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else {
                    boolean insert = dataBsaeHelper.UpdateCust(name.getText().toString(), number.getText().toString(), address.getText().toString(), city.getText().toString(), companyname.getText().toString(),unique);
                    if (insert) {
                        Snackbar snackbar = Snackbar
                                .make(view, "DATA INSERTED", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
                        setlist();
                        dialog.dismiss();

                    } else {
                        Snackbar snackbar = Snackbar
                                .make(view, "ERROR IN INSERTATION", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
                    }
                }

            }
        });
        dialog.show();

    }

}
