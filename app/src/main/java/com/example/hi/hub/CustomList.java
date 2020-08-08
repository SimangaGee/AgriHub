package com.example.hi.hub;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomList extends ArrayAdapter<String> {
    private String[] urls,name,product,quantity,price,phone,available,location;
    private Bitmap[] bitmaps;
    private String search;
    private Activity context;

    public CustomList(Activity context,String[] name, String search) {
        super(context, R.layout.image_list_view,name);
        this.context = context;
        this.name= name;
        this.search = search;

    }

   /* public CustomList(Activity context, String[] name,String[] available) {
        super(context, R.layout.image_list_view, name);
        this.context = context;
        this.name= name;
        this.available= available;
       // this.bitmaps= bitmaps;
    }*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);

        TextView namer =  listViewItem.findViewById(R.id.namee);
        TextView pro =  listViewItem.findViewById(R.id.proee);
        TextView status =  listViewItem.findViewById(R.id.avee);
        TextView pric =  listViewItem.findViewById(R.id.pree);

        if(search.isEmpty())
         {
             for (int i = 0; i < 10; i++) {
                 namer.setText("Name    :       Simanga Gwebu ");
                 pro.setText("Product :       Oranges    ");
                 status.setText("Status  :       Available ");
                 pric.setText("Location :  Nhlambeni  Price : E200.00 each");
             }
         }
       /*  else if (search == 3){
            if (name[position].toLowerCase().trim().equals(search)) {
                // TextView namer =  listViewItem.findViewById(R.id.name);
                namer.setText(name[position]);

            }else{
                namer.setHeight(0);
            }
        }*/

      //  image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],100,50,false));
       else {

            if( name[position].toLowerCase().trim().contains(search))
            {
                namer.setText(name[position]);
            }else{
                namer.setHeight(0);
            }
        }
        return  listViewItem;
    }
}
