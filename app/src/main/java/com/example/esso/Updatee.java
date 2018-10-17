package com.example.esso;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Updatee extends AppCompatActivity {
    EditText d;
    EditText p;
    EditText dd;
    EditText cc;
    ImageView mm;
    String nm;
    FirebaseDatabase database;
    DatabaseReference ref;
    private ArrayList<items> arrayList = new ArrayList<>();
    private ArrayList<String> keysList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatee);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );
        nm = getIntent().getExtras().getString("Name");
      final  String position = getIntent().getExtras().getString("position");
        String name = getIntent().getExtras().getString("itemsname");
        String cat  = getIntent().getExtras().getString("itemscat");
        String des  = getIntent().getExtras().getString("itemsdes");
      final   String image = getIntent().getExtras().getString("itemsimage");
        String price = getIntent().getExtras().getString("itemsprice");
        d = (EditText) findViewById(R.id.edname);
        p = (EditText) findViewById(R.id.pricee);
        dd = (EditText) findViewById(R.id.descr);
        cc = (EditText) findViewById(R.id.catname);
        mm = (ImageView) findViewById(R.id.imageView2);
        d.setText(name);
        cc.setText(cat);
        dd.setText(des);
        Bitmap res=stringToBitmap(image);
        mm.setImageBitmap(res);
        p.setText(price);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("items");
        Button up_btn=(Button)findViewById(R.id.button6);
        up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final items ii=new items();
                ii.setName(d.getText().toString());
                ii.setPrice(p.getText().toString());
                ii.setcat(cc.getText().toString());
                ii.setdes(dd.getText().toString());
                ii.setImage(image);
               ii.setRestaurant_name(nm);
                AlertDialog.Builder dialog = new AlertDialog.Builder(Updatee.this);
                dialog.setCancelable(false);
                dialog.setTitle("Confirm operation ");
                dialog.setMessage("Are you sure you want to update this ?" );
                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Action for "Update".
                        ref.child(position).setValue(ii);
                        Intent i=new Intent(Updatee.this,showmeue.class);
                        i.putExtra("Name",nm);
                        startActivity(i);

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                                dialog.cancel();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();


            }
        });

    }
        public Bitmap stringToBitmap(String img){
         byte [] encodeByte=Base64.decode(img,Base64.DEFAULT);
         Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
         return bitmap;
    }
}
