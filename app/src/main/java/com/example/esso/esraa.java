package com.example.esso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class esraa extends AppCompatActivity {
    Button button;
    EditText editText;
    List<String> catlist ;
 public    Restaurant restourant=new Restaurant();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_esraa);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );
        TextView f=(TextView)findViewById(R.id.textView10) ;
        f.setTypeface(f.getTypeface(), Typeface.BOLD);
        f.setTypeface(f.getTypeface(),Typeface.ITALIC);
        editText = (EditText) this.findViewById(R.id.editText);
        button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              final String text2Qr = editText.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    final Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    final ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference posts = database.getReference("Restaurant");
                    final Intent intent = new Intent(esraa.this,showqr.class );
                    Query q=posts.orderByChild("restaurant_name").equalTo(text2Qr);
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {Toast.makeText(getApplicationContext(),"this restaurant name is already exist ,please entor another Name",Toast.LENGTH_LONG).show();}
                            else
                            {intent.putExtra("pic", bitmap);intent.putExtra("Name",text2Qr);startActivity(intent);
                            }}
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}});}
                catch (WriterException e)
                {
                    e.printStackTrace();
                }}
        });}}
