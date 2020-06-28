package com.hd.jamaudhar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private int PICK_IMAGE_REQUEST = 1;
    TextView emailaddress, mobilenumber, shopname, contactperson;
    Button submit;
    DataBsaeHelper db;
    boolean insert;
    PrefrenceHandler prefrenceHandler;
    Bitmap bitmap;
    CircleImageView imageView;

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
        db = new DataBsaeHelper(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });
        Cursor res = db.getShop();
        if (res.getCount() >= 1) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
        }
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
                    insert = db.insertDataShop(shopname.getText().toString(), mobilenumber.getText().toString(), emailaddress.getText().toString(), contactperson.getText().toString());

                    if (insert) {
                        Snackbar snackbar = Snackbar
                                .make(view, "DATA INSERTED", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(R.color.lightgreen));
                        snackbar.show();
                        Intent intent = new Intent(MainActivity.this, Home.class);
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


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                prefrenceHandler.setBitmap(String.valueOf(bitmap));
                Glide.with(this).load(bitmap).into(imageView);
               // imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
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

