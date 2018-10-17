package com.example.esso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class showqr extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showqr);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );
        imageView = (ImageView) this.findViewById(R.id.imageView);
        final Bitmap bitmap = getIntent().getParcelableExtra("pic");
        final String nm=getIntent().getExtras().getString("Name");
        imageView.setImageBitmap(bitmap);

        Button b=(Button)findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(showqr.this,getcatt.class);
                i.putExtra("pic",bitmap);
                i.putExtra("Name",nm);
                startActivity(i);
            }
        });
        Button save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, nm+" Restaurant" ,"QR Code for restaurant");
                Toast.makeText(getApplicationContext(),nm+" QR saved in this Device",Toast.LENGTH_LONG).show();
            }
        });

    }



}