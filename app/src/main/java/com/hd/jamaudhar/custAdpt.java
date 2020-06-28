package com.hd.jamaudhar;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class custAdpt extends BaseAdapter {
    Activity context;
    ArrayList type ;
    ArrayList amt ;
    ArrayList item ;

    ArrayList unique ;

    TextView displaytype, displayamt, displayitem;
    ImageView delete;
    DataBsaeHelper dataBsaeHelper;
    SQLiteDatabase db;

    public custAdpt(Activity context, ArrayList<String> type, ArrayList<String> amt, ArrayList<String> item, ArrayList<String> unique) {
        dataBsaeHelper = new DataBsaeHelper(context);
        db = dataBsaeHelper.getWritableDatabase();
        this.context = context;
        this.type = type;
        this.amt = amt;
        this.item = item;
        this.unique = unique;

    }

    @Override
    public int getCount() {
        return amt.size();
    }

    @Override
    public Object getItem(int i) {
        return amt.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listcustomeractivity, null, true);

        displaytype = (TextView) rowView.findViewById(R.id.custtype);
        displayamt = (TextView) rowView.findViewById(R.id.custamt);
        displayitem = (TextView) rowView.findViewById(R.id.custitemcode);
        delete = (ImageView) rowView.findViewById(R.id.custdelete);
        displaytype.setText(type.get(i) + "");
        displayamt.setText(amt.get(i) + "");
        displayitem.setText(item.get(i) + "");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("delete from TRANSACTION_TABLE where ID='" + unique.get(i) + "'");

                type.remove(i);
                amt.remove(i);
                unique.remove(i);
                item.remove(i);

                notifyDataSetChanged();
            }
        });

        return rowView;

    }
}
