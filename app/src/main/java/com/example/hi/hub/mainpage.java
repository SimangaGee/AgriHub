package com.example.hi.hub;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class mainpage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private ListView listView;
    public static final String GET_IMAGE_URL="https://agrid.000webhostapp.com/getAllImages.php";
    public GetAlImages getAlImages;
    public static final String BITMAP_ID = "BITMAP_ID";
    final Intent myIntent = new Intent(this,Edit.class);

  //  private DBHelper db;
    private TextView topic;
    private EditText search;
    private String searched;
    private Button searchbtn ;
    private ImageView image;
    public int  see;
    public static String[] vege;
    public String[] temp;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  db =new DBHelper(this);
        topic = findViewById(R.id.topic);
        search =findViewById(R.id.search);
        see =0;
       // Cursor rs = db.getall();
//        String named = rs.getString(rs.getColumnIndex("name"));
     //   String emailed = rs.getString(rs.getColumnIndex("email"));



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Scroll  view
        image = findViewById(R.id.image);
        searchbtn = findViewById(R.id.searchbtn);
        listView = (ListView) findViewById(R.id.listView);
      //  listView.setOnItemClickListener(this);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                see =11;
                getURLs();
            }
        });
        vege = getResources().getStringArray(R.array.vegetables);
      seet();
    }

    private void seet(){
        CustomList customList = new CustomList(mainpage.this,vege,"");
        listView.setAdapter(customList);
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
           // RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(mainpage.this,"Downloading","Please wait...",false,false);
                //startActivity(myIntent);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();

                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                if(see == 11) {
                    String sear = search.getText().toString().toLowerCase().trim();
                       CustomList customList = new CustomList(mainpage.this,GetAlImages.inname,sear);
                       listView.setAdapter(customList);

                }else
                    {

                        //    CustomList customList = new CustomList(mainpage.this, GetAlImages.inname, 3);
                         //   listView.setAdapter(customList);

                    }
              //  Toast.makeText(mainpage.this,GetAlImages.inname[1],Toast.LENGTH_LONG).show();// CustomList customList = new CustomList(mainpage.this,GetAlImages.inname,GetAlImages.inavailable);

            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {

                        getAlImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(mainpage.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();


                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainpage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

                if (id == R.id.nav_camera) {
                    // Handle the camera action
                    startActivity(new Intent(this, Register.class));
                } else if (id == R.id.nav_gallery) {
                    startActivity(new Intent(this, Vegetables.class));

                } else if (id == R.id.nav_slideshow) {
                    startActivity(new Intent(this,  Animal.class));
                }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
