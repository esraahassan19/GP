package com.example.esso;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class orderdetails extends AppCompatActivity {
 received_Oreders cop ;
    order ord;
 public    FirebaseDatabase database;
   public DatabaseReference ref;
    ListView lv;
    ArrayList< ord_item> list;
    String x,y,z,a,b,c,d;
    public custm_listitems_Adapter adapter2=null;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);

    final String nm= getIntent().getExtras().getString("Name");
    final String key=getIntent().getExtras().getString("key");
        lv=(ListView)findViewById(R.id.orddetailslist) ;
        list=new ArrayList<>();
        adapter2 = new custm_listitems_Adapter(getApplicationContext(), R.layout.custom_list_item,list);
        lv.setAdapter(adapter2);
       database = FirebaseDatabase.getInstance();
        ref = database.getReference("Order");
        Query query = ref.orderByChild("rest_name").equalTo(nm);
         query.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
               list.clear();
                 for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                     if (dsp.getKey().equals(key)) {
                         for (DataSnapshot dsp2 : dsp.child("ordered_items").getChildren()) {
                         list.add(dsp2.getValue(ord_item.class));
                             adapter2.notifyDataSetChanged();}
                         ord=dsp.child(key).getValue(order.class);
                            x=dsp.child("rest_name").getValue().toString();
                           y=dsp.child("date").getValue().toString();
                            z=dsp.child("name").getValue().toString();
                          a=dsp.child("cost").getValue().toString();
                             b=dsp.child("time").getValue().toString();
                             c=dsp.child("table_num").getValue().toString();
                           d=dsp.child("ord_id").getValue().toString();}}}
             @Override
             public void onCancelled(DatabaseError databaseError) {}});
          cop=new received_Oreders();
          database = FirebaseDatabase.getInstance();
         ref = database.getReference("Order");
          final TextView costtotal=(TextView) findViewById(R.id.totalcost);
         Button recived=(Button)findViewById(R.id.recievedbtn);
         recived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref = database.getReference("Order");
                Query query = ref.orderByChild("rest_name").equalTo(nm);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            if (dsp.getKey().equals(key)) {
                                for (DataSnapshot dsp2 : dsp.child("ordered_items").getChildren()) {
                                    list.add(dsp2.getValue(ord_item.class));
                                    adapter2.notifyDataSetChanged();}
                                ord=dsp.child(key).getValue(order.class);
                                x=dsp.child("rest_name").getValue().toString();
                                y=dsp.child("date").getValue().toString();
                                z=dsp.child("name").getValue().toString();
                                a=dsp.child("cost").getValue().toString();
                                b=dsp.child("time").getValue().toString();
                                c=dsp.child("table_num").getValue().toString();
                                d=dsp.child("ord_id").getValue().toString();
                                cop.setOrd_id(d);
                                cop.settime(b);
                                cop.setTable_num(c);
                                cop.setreciveditems(list);
                                cop.setDate(y);
                                cop.setCost(a);
                                cop.setRest_name(x);
                                cop.setname(z);
                                costtotal.setText(a);
                                DatabaseReference posts = database.getReference("received_Oreders");
                                posts.push().setValue(cop).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(orderdetails.this, "order Add Successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(orderdetails.this, "Error : post not add 🙁 ", Toast.LENGTH_SHORT).show();}}
                                });}}}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}});
                database = FirebaseDatabase.getInstance();
                ref = database.getReference("Order");
                ref.child(key).removeValue();}
        });
    }
}
