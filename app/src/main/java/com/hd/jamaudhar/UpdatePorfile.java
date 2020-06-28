package com.hd.jamaudhar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdatePorfile extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    TextView emailaddress, mobilenumber, shopname, contactperson;
    Button submit;
    DataBsaeHelper dataBsaeHelper;
    boolean insert;
    PrefrenceHandler prefrenceHandler;
    Bitmap bitmap;
    CircleImageView imageView;
    //DataBsaeHelper dataBsaeHelper;
    Cursor cursor;
    Cursor res;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailaddress = (TextView) findViewById(R.id.shopemailaddress);
        mobilenumber = (TextView) findViewById(R.id.shopmobilenumber);
        prefrenceHandler = new PrefrenceHandler(this);
        shopname = (TextView) findViewById(R.id.shopname);
        submit = (Button) findViewById(R.id.buttonsubmitshop);
        contactperson = (EditText) findViewById(R.id.shopcontactperson);
        imageView = (CircleImageView) findViewById(R.id.profileimg);
        dataBsaeHelper = new DataBsaeHelper(this);
        setImageView();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        Cursor res = dataBsaeHelper.getShop();
        /*if (res.getCount() >= 1) {
            Intent intent = new Intent(UpdatePorfile.this, Home.class);
            startActivity(intent);
            finish();
        }*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shopname.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter ShopName", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else if (mobilenumber.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter MobileNumber", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else if (emailaddress.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter EmailAddress", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else if (contactperson.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Please enter ContactPerson", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(R.color.lightgreen));
                    snackbar.show();
                } else {
                    insert = dataBsaeHelper.UpdateShop(shopname.getText().toString(), mobilenumber.getText().toString(), emailaddress.getText().toString(), contactperson.getText().toString());

                    if (insert) {
                        Snackbar snackbar = Snackbar
                                .make(view, "DATA INSERTED", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
                        Intent intent = new Intent(UpdatePorfile.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(view, "ERROR IN INSERTATION", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
                    }
                }
            }
        });
    }

    public void upload() {
        CropImage.activity().setCropShape(CropImageView.CropShape.OVAL).setFixAspectRatio(true).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    public void setImageView() {
        cursor = dataBsaeHelper.getShop();
        while (cursor.moveToNext()) {
            submit.setText("Update");
            shopname.setText(cursor.getString(1));
            mobilenumber.setText(cursor.getString(2));
            emailaddress.setText(cursor.getString(3));
            contactperson.setText(cursor.getString(4));

        }


        Log.i("glid-------------------",prefrenceHandler.getBitmap().toString());
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.user)).load(prefrenceHandler.getBitmap()).into(imageView);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                /*try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Log.d(TAG, String.valueOf(bitmap));*/
                Log.i("glid-------------------", String.valueOf(resultUri));

                prefrenceHandler.setBitmap(String.valueOf(resultUri));
                Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.user)).load(prefrenceHandler.getBitmap()).into(imageView);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
