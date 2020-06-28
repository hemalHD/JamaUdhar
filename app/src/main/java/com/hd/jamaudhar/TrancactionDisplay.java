package com.hd.jamaudhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TrancactionDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trancaction_display);
        ArrayList adapter = new ArrayList();
        ArrayList unique = new ArrayList();
        Cursor res=new DataBsaeHelper(this).gettranc();
        if(res.getCount()==0){
            adapter.add("nothing to show");
            unique.add(null);
        }else {
            while (res.moveToNext()) {
                adapter.add("ID is " + res.getString(0) + "\nPhone number is " + res.getString(1) + "\nType is " + res.getString(2) + "\nAmount is " + res.getString(3) + "\nItem is " + res.getString(4) );
                unique.add(res.getString(1));
            }
        }
        SetAdapter setAdapter=new SetAdapter(this,adapter,"TRANSACTION_TABLE","USERID",unique);
        ListView listView = (ListView) findViewById(R.id.trancationlistview);
        listView.setAdapter(setAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
