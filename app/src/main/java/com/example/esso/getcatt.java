package com.example.esso;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class getcatt extends AppCompatActivity {
    public Restaurant restourant = new Restaurant();
    private ArrayList<String> catList=new ArrayList<>();
    private ArrayList<items> arrayList = new ArrayList<>();
    private ArrayList<String> keysList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference ref;
    Restaurant obj;
   public String key1;
    Integer  index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getcatt);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );
        final Bitmap bitmap = getIntent().getParcelableExtra("pic");
        final String nm = getIntent().getExtras().getString("Name");
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final ListView ll = (ListView) findViewById(R.id.cat_list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        ll.setAdapter(adapter);
        registerForContextMenu(ll);
        final EditText CAT_TEXT = (EditText) findViewById(R.id.editText2);
        catList = new ArrayList<String>();
        Button b = (Button) findViewById(R.id.addcat);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cc = CAT_TEXT.getText().toString();
                catList.add(cc);
                adapter.add(cc);
                CAT_TEXT.getText().clear();

            }
        });
      final  EditText tables=(EditText)findViewById(R.id.num_tabes);

        Button bb = (Button) findViewById(R.id.button5);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference posts = database.getReference("Restaurant");

                restourant.setcat_nam(catList);
                restourant.setQr_name(nm);
                restourant.setQr_image(stream.toString());
                restourant.setCheckWaiting("False");
                restourant.setNumber_minuts("0");
                restourant.SetNumber_of_of_tables(tables.getText().toString());
                posts.push().setValue(restourant).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getcatt.this, "restaurant Added Successfully", Toast.LENGTH_SHORT).show();}
                        else {Toast.makeText(getcatt.this, "Error : post not add 🙁 ", Toast.LENGTH_SHORT).show();}}});
                Intent iii = new Intent(getcatt.this, crop_t.class);
                iii.putExtra("Name", nm);

                startActivity(iii);
            }
        });


    }}