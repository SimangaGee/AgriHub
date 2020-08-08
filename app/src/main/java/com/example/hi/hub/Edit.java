package com.example.hi.hub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Edit extends AppCompatActivity implements View.OnClickListener  {

    public static final String UPLOAD_URL = "https://agrid.000webhostapp.com/upload.php";
    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;

    private Bitmap bitmap;

    private Uri filePath;

    private EditText namer,quantity, price,available;
    private Button submit;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db = new DBHelper(this);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        available = findViewById(R.id.available);
        namer = findViewById(R.id.name);
        submit = findViewById(R.id.order);

        Intent sec =getIntent();
        String product = sec.getStringExtra("hold");

        if(product.equals("Other")) {
            namer.setVisibility(View.VISIBLE);

        }else{
            namer.setVisibility(View.INVISIBLE);
        }
       submit.setOnClickListener(this);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
              //  imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

          ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

                     @Override
                     protected void onPreExecute() {
                         super.onPreExecute();
                        loading = ProgressDialog.show(Edit.this, "Uploading...", null,true,true);
                     }

                     @Override
                     protected void onPostExecute(String s) {
                         super.onPostExecute(s);
                         loading.dismiss();
                         Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                     }

            @Override
            protected String doInBackground(Bitmap... params) {


                HashMap<String,String> data = new HashMap<>();
                String product;
              //  data.put(UPLOAD_KEY, uploadImage);
                String quant = quantity.getText().toString();
                String pric = price.getText().toString();
                String avail = available.getText().toString();
                product= namer.getText().toString();
                if(product.isEmpty()) {
                    Intent sec =getIntent();
                    product = sec.getStringExtra("hold");
                }
                Cursor rs = db.getall();
                rs.moveToFirst();
                String name = rs.getString(rs.getColumnIndex("name"));
                String phone = rs.getString(rs.getColumnIndex("phone"));
                String location = rs.getString(rs.getColumnIndex("address"));
                // String uploadImage = getStringImage(bitmap);

                data.put("name", name);
                data.put("product", product);
                data.put("quantity", quant);
                data.put("price", pric);
                data.put("phone", phone);
                data.put("available", avail);
                data.put("location", location);

                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == submit) {
            uploadImage();
            Toast.makeText(getApplicationContext(),"Uploading....",Toast.LENGTH_LONG).show();

        }

    }


}
