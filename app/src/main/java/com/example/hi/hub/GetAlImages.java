package com.example.hi.hub;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Belal on 9/19/2015.
 */
public class GetAlImages {

    public static String[] imageURLs,inname,inproduct,inquantity,inprice,inphone,inlocation,inavailable;
    //public static Bitmap[] bitmaps;

    public static final String JSON_ARRAY="result";


    public static final String IMAGE_URL = "url";
    static final String IMAGE_AVAILABLE="available";
    public static final String IMAGE_NAME="name";
    public static final String IMAGE_PRODUCT="product";
    public static final String IMAGE_QUANTITY="quantity";
    public static final String IMAGE_PRICE="price";
    public static final String IMAGE_PHONE="phone";
    public static final String IMAGE_LOCATION="location";
    private String json;
    public static int total;
    private JSONArray urls,name,product,available,quantity,price,phone,location;

    public GetAlImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonO = new JSONObject(json);

            name = jsonObject.getJSONArray(JSON_ARRAY);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAllImages() throws JSONException {
       //bitmaps = new Bitmap[urls.length()];

        inname = new String[name.length()];
        inavailable = new String[name.length()];
        inproduct = new String[name.length()];
        inquantity= new String[name.length()];
        inprice = new String[name.length()];
        inphone = new String[name.length()];
        inlocation = new String[name.length()];

        for(int i=0;i<name.length();i++){
           // imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
          inname[i] = name.getJSONObject(i).getString(IMAGE_NAME);

          //  bitmaps[i]=getImage(jsonObject);
        }
    }

    public void getAllImaged(String tip) throws JSONException {
        //  bitmaps = new Bitmap[urls.length()];

        inname = new String[name.length()];
        inavailable = new String[name.length()];
        inproduct = new String[name.length()];
        inquantity= new String[name.length()];
        inprice = new String[name.length()];
        inphone = new String[name.length()];
        inlocation = new String[name.length()];

        for(int i=0;i<name.length();i++){
            // imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            if( tip.equals(name.getJSONObject(i).getString(IMAGE_NAME))) {
                inname[i] = name.getJSONObject(i).getString(IMAGE_NAME);
                total = name.length();
            }

            //  bitmaps[i]=getImage(jsonObject);
        }
    }


}