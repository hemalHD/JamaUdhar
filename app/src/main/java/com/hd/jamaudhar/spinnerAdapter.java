package com.hd.jamaudhar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class spinnerAdapter  extends BaseAdapter{
    Activity context;
    ArrayList<String> name;
    ArrayList<String> total;
    TextView nameview,amountview;
    public spinnerAdapter(Activity context,ArrayList<String> name,ArrayList<String> total) {

        this.context=context;
        this.name=name;
        this.total=total;

    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int i) {
        return name.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater=context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.spinner_layout, null, true);

            nameview=(TextView)rowView.findViewById(R.id.spinnername);
            amountview=(TextView)rowView.findViewById(R.id.spinnernumber);
            nameview.setText(name.get(i)+"");
            amountview.setText(total.get(i)+"");

            return rowView;

    }


}
