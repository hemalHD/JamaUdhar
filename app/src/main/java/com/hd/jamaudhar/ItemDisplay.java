package com.hd.jamaudhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        ArrayList adapter = new ArrayList();
        ArrayList unique = new ArrayList();


        Cursor res=new DataBsaeHelper(this).getItem();
        if(res.getCount()==0){
            adapter.add("nothing to show");
            unique.add(null);
        }else {
            while (res.moveToNext()) {
                adapter.add("ID is " + res.getString(0) + "\nName is " + res.getString(1));
                unique.add(res.getString(0));
            }
        }
        SetAdapter setAdapter=new SetAdapter(this,adapter,"ITEM","ID",unique);
        ListView listView = (ListView) findViewById(R.id.listwiewitem);
        listView.setAdapter(setAdapter);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
