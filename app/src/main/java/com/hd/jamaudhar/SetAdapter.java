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

public class SetAdapter extends BaseAdapter {
    Activity context;
    ArrayList<String> display;
    ArrayList<String> unq;
    String tablename,colname;
    TextView displaytext;
    ImageView delete;
    DataBsaeHelper dataBsaeHelper;
    SQLiteDatabase db;
    public SetAdapter(Activity context,ArrayList<String> display,String tablename,String colname,ArrayList<String> unq) {
        dataBsaeHelper=new DataBsaeHelper(context);
        db = dataBsaeHelper.getWritableDatabase();
        this.context=context;
        this.display=display;
        this.unq=unq;
        this.tablename=tablename;
        this.colname=colname;

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
        delete=(ImageView)rowView.findViewById(R.id.delete);
        displaytext.setText(display.get(i)+"");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("delete from "+tablename+" where "+colname+"='"+unq.get(i)+"'");
                if(tablename.equals("CUST")){
                    db.execSQL("delete from TRANSACTION_TABLE where USERID='"+unq.get(i)+"'");
                }
                display.remove(i);
                unq.remove(i);
                notifyDataSetChanged();
            }
        });

        return rowView;

    }
}
