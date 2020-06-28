package com.hd.jamaudhar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class PrefrenceHandler {

    SharedPreferences bitpref;
    SharedPreferences.Editor editbitpref;

    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "BitPref";

    public PrefrenceHandler(Context context)
    {
        try
        {
            this._context = context;
            bitpref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editbitpref = bitpref.edit();
            editbitpref.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setBitmap(String username)
    {

        editbitpref.putString("bitmap", username);
        editbitpref.commit();
    }

    public String getBitmap()
    {
        return bitpref.getString("bitmap",null);
    }

}
