package com.example.hi.hub;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Register extends AppCompatActivity{

    private DBHelper db;
    private Button Registerbtn;
    private EditText Address;
    private EditText Name;
    private EditText Phone;
    private EditText Email;
    private String Namestr,Phonestr,Addressstr,Emailstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Address = findViewById(R.id.Address);
        Name = findViewById(R.id.name);
        Phone = findViewById(R.id.phone);
        Email = findViewById(R.id.email);
        Registerbtn = findViewById(R.id.registerbtn);
        db = new DBHelper(this);
        final Intent myIntent = new Intent(this,Edit.class);

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Emailstr = Email.getText().toString();
                Phonestr = Phone.getText().toString();
                Addressstr = Address.getText().toString();
                Namestr = Name.getText().toString();

                if(db.insertcontent(Namestr, Phonestr, Addressstr,Emailstr)) {
                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_LONG).show();
                    startActivity(myIntent);
                }
                else
                   {
                     Toast.makeText(getApplicationContext(), "Failed to create Account", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}
