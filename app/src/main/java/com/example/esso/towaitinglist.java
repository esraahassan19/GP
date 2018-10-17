package com.example.esso;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class towaitinglist extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    String res_checked;
    String time;
    Restaurant obj;
    CheckBox c1;
    public String userToken;
    String num;
    int number_tables;
    int avg=1;
    String ss;
    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_towaitinglist);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );
        final String nm = getIntent().getExtras().getString("Name");
        c1 = (CheckBox) findViewById(R.id.chech_value);
        Button bt = (Button) findViewById(R.id.chbtn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c1.isChecked()) {
                    res_checked = "True";
                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference("Restaurant");
                    final Query query = ref.orderByChild("qr_name").equalTo(nm);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                dsp.getRef().child("checkWaiting").setValue(res_checked);
                               // ss=dsp.child("Number_tables").getValue().toString();
                                number_tables=15;

                                Random rand = new Random();
                                int sum=0;
                                for(int i=0;i<number_tables;i++)
                                {    sum +=rand.nextInt(20)+ 12;   }
                                avg=sum/number_tables;
                                Toast.makeText(getApplicationContext(), String.valueOf(avg),Toast.LENGTH_LONG).show();
                            }

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException(); // don't ignore errors
                        }
                    });

                } else {
                    res_checked = "False";
                    database = FirebaseDatabase.getInstance();
                    ref = database.getReference("Restaurant");
                    final Query query = ref.orderByChild("qr_name").equalTo(nm);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String key = dataSnapshot.getRef().getKey();
                            obj = dataSnapshot.getValue(Restaurant.class);
                            obj.setCheckWaiting(res_checked);
                            ref.child(key).setValue(obj);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException(); // don't ignore errors
                        }
                    });

                }

            }
        });


        list = new ArrayList<String>();
        ListView lv = (ListView) findViewById(R.id.wl);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("waitinglist");
        Query query1 = ref.orderByChild("restaurantname").equalTo(nm);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    list.add(dsp.child("name").getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
 Button senbtn=(Button)findViewById(R.id.send_msg);
        senbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                ref = database.getReference("user");

                Query query = ref.orderByChild("username").equalTo(list.get(0));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dsp : dataSnapshot.getChildren())
                        {
                             num=dsp.child("numberphone").getValue().toString();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException(); // don't ignore errors
                    }
                });
              String number=num.trim();

                String message="Dear,You are welcome in the  resturant"+String.valueOf(avg).trim();
                if(number==null || number.equals("")||message.equals("")||message==null){
                    Toast.makeText(getApplicationContext(),"No right number",Toast.LENGTH_LONG).show();
                }
                else {

                    if (TextUtils.isDigitsOnly(number)){

                        SmsManager s=SmsManager.getDefault();
                        s.sendTextMessage(number,null,message,null,null);
                        Toast.makeText(getApplicationContext(),"Succesfully"+number,Toast.LENGTH_LONG).show();
                    }

                }

           /*     String serverKey ="AAAA_nL_HTM:APA91bGSKfxk_9xB0OktFuZXkH9Xl-Laj7WKbmi6yyUVU47z4vtsRbNG78dZ4vLEXtIQvUKZIvqm5wrFoej9IJ3wjgw1Z8yLrHqun9qknCvwZezGUkPrU6dfRivbC13DjUcbECAbkFLX";
                FCMPushNotificationManager manager = FCMPushNotificationManager.getInstance(serverKey);
                FCMPullNotificationManager manager2 = FCMPullNotificationManager.getInstance(getApplicationContext());
                database = FirebaseDatabase.getInstance();
                ref = database.getReference("user");

                        Query query = ref.orderByChild("username").equalTo(list.get(0));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dsp : dataSnapshot.getChildren())
                        {
                            userToken=dsp.child("tokenID").getValue().toString();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException(); // don't ignore errors
                    }
                });

               manager.notifyByToken("Title", "Dear, can you come soon ",userToken);
                NotificationBuilder builder = new NotificationBuilder(getApplicationContext());
                manager2.autoLaunchNotification(builder);

                builder .setContentIntent(new Intent(getApplicationContext(), towaitinglist.class));
                builder.setNotificationIcon(id);
                builder.setDefaultSound();
                manager.setPushFCMCallback(new IPushFCMCallback() {
                    @Override
                    public void onPushNotificationSuccess() {
                        // on pushinh notification

                    }

                    @Override
                    public void onError(String errorMessage) {
                        // on can't push notification
                    }
                });*/
            }  });
    }
    /*@Override
    public void onPushNotificationSuccess() {

    }

    @Override
    public void onError(String s) {

    }*/
}