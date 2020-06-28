package com.hd.jamaudhar;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.checkSelfPermission;


public class CustomAdapter extends ArrayAdapter {
    Activity context;
    ArrayList name;
    ArrayList total;
    ArrayList number;
    Button call;
    Button messsage;
    Button delete;
    LinearLayout show;
    TextView nameview, amountview, type;
    DataBsaeHelper dataBsaeHelper;
    SQLiteDatabase db;

    public CustomAdapter(Activity context, ArrayList name, ArrayList total, ArrayList number) {
        super(context, R.layout.home_listitem, name);
        dataBsaeHelper = new DataBsaeHelper(context);
        db = dataBsaeHelper.getWritableDatabase();
        this.context = context;
        this.name = name;
        this.number = number;
        this.total = total;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.home_listitem, null, true);
        show = (LinearLayout) rowView.findViewById(R.id.linermain);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CustomerActivity.class);
                intent.putExtra("cust", "select * from CUST where CUSTMOBILENUMBER=" + number.get(position) + "");
                intent.putExtra("total",total.get(position)+"");
                intent.putExtra("unq",number.get(position)+"");
                context.startActivity(intent);
//                context.finish();
            }
        });
        call = (Button) rowView.findViewById(R.id.listcall);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phno = String.valueOf(number.get(position));
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phno));
                if (checkSelfPermission(context,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                }else {
                    context.startActivity(i);
                }




            }
        });
        messsage = (Button) rowView.findViewById(R.id.listmessage);
        messsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String smsNumber = (String) number.get(position);
                String smsText = name.get(position) + " Test msg";

                Uri uri = Uri.parse("smsto:" + smsNumber);
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", smsText);
                context.startActivity(intent);
            }
        });
        delete = (Button) rowView.findViewById(R.id.listdelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                db.execSQL("delete from CUST where CUSTMOBILENUMBER='"+number.get(position)+"'");
                                db.execSQL("delete from TRANSACTION_TABLE where USERID='"+number.get(position)+"'");
                                number.remove(position);
                                total.remove(position);
                                name.remove(position);
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
        nameview = (TextView) rowView.findViewById(R.id.listname);
        type = (TextView) rowView.findViewById(R.id.listtype);
        amountview = (TextView) rowView.findViewById(R.id.listamount);
        nameview.setText("" + name.get(position));
        if (Double.parseDouble(total.get(position) + "") > 0) {
            type.setText("Credit");
            amountview.setText("" + total.get(position));
            type.setTextColor(rowView.getResources().getColor(R.color.lightgreen));
            amountview.setTextColor(rowView.getResources().getColor(R.color.lightgreen));
        } else if (Double.parseDouble(total.get(position) + "") < 0) {
            type.setText("Debit");
            amountview.setText("" + total.get(position));
            type.setTextColor(rowView.getResources().getColor(R.color.red));
            amountview.setTextColor(rowView.getResources().getColor(R.color.red));
        } else if (Double.parseDouble(total.get(position) + "") == 0) {
            type.setText("NO tran.");
            amountview.setText("" + total.get(position));
            type.setTextColor(rowView.getResources().getColor(R.color.black));
            amountview.setTextColor(rowView.getResources().getColor(R.color.black));
        }

        if (name.get(position).equals("")) {
            call.setVisibility(View.GONE);
            messsage.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
        return rowView;
    }


}
