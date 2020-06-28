package com.hd.jamaudhar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class Home extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    Integer integer;

    ImageView close;
    Spinner spinner1;
    Spinner spinner2;

    Button credit, debit;
    View hView;

    int creditvalue = 0;
    int debitvalue = 0;

    DataBsaeHelper dataBsaeHelper;
    Cursor cursor;
    Cursor res;
    SQLiteDatabase db;

    spinnerAdapter aa;
    spinnerAdapter aa1;


    CircleImageView imageView;
    TextView shopname;
    ListView listView;

    PrefrenceHandler prefrenceHandler;

    TextView type;
    EditText amount;
    TextView hometotal, hometotalview;

    Button tranc;

    String spin1nmae, spin1number;
    String spin2name;
    String amt;
    String typestore;

    FloatingActionButton customer, item;
    FloatingActionsMenu menu;

    String sname;

    EditText name, number, address, city, companyname;
    Button add;


    byte[] bytarray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Permissions permissions = new Permissions(this);
        permissions.requestStoragePermission();
        prefrenceHandler=new PrefrenceHandler(this);
        bind();
        setToolbar();
        setImageView();
        click();

    }

    private void loaddata() {
        creditvalue = 0;
        debitvalue = 0;
        String userid;
        ArrayList name = new ArrayList<String>();
        final ArrayList number = new ArrayList<String>();
        ArrayList total = new ArrayList<String>();
        Cursor res = dataBsaeHelper.getCust();

        if (res.getCount() == 0) {
            /*name.add("Nothing found!!!");
            total.add("0");
            number.add("0");*/
        } else {
            while (res.moveToNext()) {
                userid = res.getString(2);
                number.add(userid);
                name.add(res.getString(1));
                Log.i("helooooo", userid);
                Cursor cs = db.rawQuery("select * from TRANSACTION_TABLE where USERID='" + userid + "' AND TYPE='Credit'", null);
                if (cs.getCount() == 0) {
                    creditvalue = 0;
                    Log.i("helooooo", creditvalue + "");
                } else {
                    while (cs.moveToNext()) {
                        creditvalue = creditvalue + Integer.parseInt(cs.getString(3));
                        Log.i("helooooo", creditvalue + "");
                    }
                }
                Cursor ds = db.rawQuery("select * from TRANSACTION_TABLE where USERID=" + userid + " AND TYPE='Debit'", null);
                if (ds.getCount() == 0) {
                    debitvalue = 0;
                } else {
                    while (ds.moveToNext()) {
                        debitvalue = debitvalue + Integer.parseInt(ds.getString(3));
                    }
                }
                total.add(creditvalue - debitvalue);
            }
        }
        CustomAdapter customAdapter = new CustomAdapter(Home.this, name, total, number);
        listView.setAdapter(customAdapter);
    }

    public void customeradd() {
        final Dialog dialog = new Dialog(Home.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.add_cust);
        // Set dialog title
        dialog.setTitle("Add Customer");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);


        name = (EditText) dialog.findViewById(R.id.customername);
        number = (EditText) dialog.findViewById(R.id.customermobilenumber);
        address = (EditText) dialog.findViewById(R.id.customeraddress);
        city = (EditText) dialog.findViewById(R.id.customercity);
        companyname = (EditText) dialog.findViewById(R.id.customercompanyname);
        add = (Button) dialog.findViewById(R.id.buttonaddcustomer);
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
                    boolean insert = dataBsaeHelper.insertDataCust(name.getText().toString(), number.getText().toString(), address.getText().toString(), city.getText().toString(), companyname.getText().toString());
                    if (insert) {
                        Snackbar snackbar = Snackbar
                                .make(view, "DATA INSERTED", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
                        dialog.dismiss();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(view, "ERROR IN INSERTATION", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
                    }
                }
                loaddata();
                setTotal();
            }
        });
        dialog.show();


    }

    public void addItem() {
        final Dialog dialog = new Dialog(Home.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.add_item);
        // Set dialog title
        dialog.setTitle("Add Item");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        name = (EditText) dialog.findViewById(R.id.itemname);
        add = (Button) dialog.findViewById(R.id.buttonadditem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter Name", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else {
                    boolean insert = dataBsaeHelper.insertDataItem(name.getText().toString());
                    if (insert) {
                        Snackbar snackbar = Snackbar
                                .make(view, "DATA INSERTED", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
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

    public void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menuitems:
                        Intent intent1 = new Intent(Home.this, ItemDisplay.class);
                        startActivity(intent1);

                        break;
                    case R.id.menutrancations:
                        Intent intent2 = new Intent(Home.this, TrancactionDisplay.class);
                        startActivity(intent2);

                        break;
                    case R.id.menucustomers:
                        Intent intent = new Intent(Home.this, CustomerDisplay.class);
                        startActivity(intent);

                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    public void bind() {
        navigationView = (NavigationView) findViewById(R.id.navigarionbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        hView = navigationView.getHeaderView(0);
        imageView = (CircleImageView) hView.findViewById(R.id.icon);
        hometotal = (TextView) findViewById(R.id.hometotal);
        shopname = (TextView) hView.findViewById(R.id.shopnameheader);
        customer = (FloatingActionButton) findViewById(R.id.actionbuttonaddcustomer);
        item = (FloatingActionButton) findViewById(R.id.actionbuttonadditem);
        menu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        credit = (Button) findViewById(R.id.buttoncredit);
        debit = (Button) findViewById(R.id.buttondebit);
        listView = (ListView) findViewById(R.id.listviewhome);
        hometotalview = (TextView) findViewById(R.id.hometotalview);


        dataBsaeHelper = new DataBsaeHelper(this);
        db = dataBsaeHelper.getWritableDatabase();

    }

    public void setImageView() {
        cursor = dataBsaeHelper.getShop();
        while (cursor.moveToNext()) {
            sname = cursor.getString(1);

        }


        Log.i("glid-------------------",prefrenceHandler.getBitmap().toString());
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.user)).load(prefrenceHandler.getBitmap()).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Home.this, UpdatePorfile.class);
                startActivity(intent3);
                drawerLayout.closeDrawers();
            }
        });
        shopname.setText(sname);
    }

    public void click() {
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tranctionDailog("Credit");
            }
        });
        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tranctionDailog("Debit");
            }
        });
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customeradd();
                menu.collapse();
            }
        });
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
                menu.collapse();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int lastItem = i + i1;
                if (lastItem == i2 && i > 0) {

                    menu.setVisibility(View.INVISIBLE);
                    credit.setVisibility(View.INVISIBLE);
                    debit.setVisibility(View.INVISIBLE);
                } else {
                    menu.setVisibility(View.VISIBLE);
                    credit.setVisibility(View.VISIBLE);
                    debit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void tranctionDailog(String type1) {

        final Dialog dialog = new Dialog(Home.this);
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
        res = dataBsaeHelper.getCust();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {

                name.add(res.getString(1));
                number.add(res.getString(2));


            }
        } else {
            name.add("Add Customer");
            number.add("");
        }
        aa = new spinnerAdapter(Home.this, name, number);
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

        aa1 = new spinnerAdapter(Home.this, name1, number1);
        spinner2.setAdapter(aa1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((TextView) view.findViewById(R.id.spinnername)).getText().toString().equals("Add Customer")) {
                    customeradd();
                    dialog.dismiss();
                } else {
                    spin1nmae = ((TextView) view.findViewById(R.id.spinnername)).getText().toString();
                    spin1number = ((TextView) view.findViewById(R.id.spinnernumber)).getText().toString();
                }

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
                if (((TextView) v.findViewById(R.id.spinnername)).getText().toString().equals("Add Item")) {
                    addItem();
                    dialog.dismiss();
                } else {
                    spin2name = ((TextView) v.findViewById(R.id.spinnername)).getText().toString();
                }


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
                    Toast.makeText(Home.this, "enter amount!!!!", Toast.LENGTH_SHORT).show();
                }else {
                    typestore = type.getText().toString();
                    Log.i("insertion", spin1number + typestore + amt + spin2name);
                    boolean b = dataBsaeHelper.insertDataTranction(spin1number, typestore, amt, spin2name);
                    if (b) {
                        dialog.dismiss();
                        loaddata();
                        setTotal();
                    } else {
                        Toast.makeText(Home.this, "Enter valid details!", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }

    public void setTotal() {
        creditvalue=0;
        debitvalue=0;
        Cursor cs = db.rawQuery("select * from TRANSACTION_TABLE where TYPE='Credit'", null);
        if (cs.getCount() == 0) {
            creditvalue = 0;
            Log.i("helooooo", creditvalue + "");
        } else {
            while (cs.moveToNext()) {
                creditvalue = creditvalue + Integer.parseInt(cs.getString(3));
                Log.i("helooooo", creditvalue + "");
            }
        }
        Cursor ds = db.rawQuery("select * from TRANSACTION_TABLE where TYPE='Debit'", null);
        if (ds.getCount() == 0) {
            debitvalue = 0;
        } else {
            while (ds.moveToNext()) {
                debitvalue = debitvalue + Integer.parseInt(ds.getString(3));
            }
        }
        integer = creditvalue - debitvalue;
        if (Double.parseDouble(integer + "") > 0) {
            hometotalview.setText("Credit");
            hometotal.setText("" + integer);

        } else if (Double.parseDouble(integer + "") < 0) {
            hometotalview.setText("Debit");
            hometotal.setText("" + integer);
        } else if (Double.parseDouble(integer + "") == 0) {
            hometotalview.setText("Balanced");
            hometotal.setText("" + integer);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddata();
        setTotal();
    }


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
                    intent.putExtra("total", total.get(position) + "");
                    intent.putExtra("unq", number.get(position) + "");
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

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
                                    setTotal();
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

}
