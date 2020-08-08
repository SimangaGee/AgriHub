package com.example.hi.hub;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.lang.String;

public class Manage extends AppCompatActivity {

    ListView lister;
    Intent myIntent;
   // private Transaction db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        lister =findViewById(R.id.list);
        myIntent = new Intent(this,Edit.class);

        Bundle c = getIntent().getExtras();
        String[] vegArr = c.getStringArray("selectedProduct");

        int count = c.getInt("count");

        String[] fulllist = new String[count] ;

        //means this is the view part not the add contact part.
        int k1 = 0;

        while(k1< count)
          {
              fulllist[k1] = vegArr[k1];
              k1++;

          }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fulllist);

        lister.setAdapter(adapter);
        lister.setOnItemClickListener(listClick);

    }

    public AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView parent,View v,int position,long id)
        {
            String itemvalue = String.valueOf(lister.getItemAtPosition(position));
            myIntent.putExtra("hold",itemvalue);
            startActivity(myIntent);
        }
    };
}

