package com.example.esso;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class sh_order extends AppCompatActivity {
    public custm_ListOrderAdapter adapter1=null;
    FirebaseDatabase database;
    DatabaseReference ref;
    ListView lv;
    ArrayList<order> list;
    String nm;
    private ArrayList<order> arrayList = new ArrayList<>();
    private ArrayList<String> keysList = new ArrayList<>();
     order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_order);

        nm=getIntent().getExtras().getString("Name");
        Integer index;
        lv = (ListView) findViewById(R.id.orderlist);
        list =new ArrayList<order>();
        adapter1 = new custm_ListOrderAdapter(this, R.layout.cusm_ord_layout, list);
        lv.setAdapter(adapter1);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Order");

        Query query = ref.orderByChild("rest_name").equalTo(nm);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    String x=dsp.child("name").getValue().toString();
                    String y=dsp.child("table_num").getValue().toString();
                    String ord_id=dsp.child("ord_id").getValue().toString();
                    list.add(new order(x,y,ord_id));
                    keysList.add(dsp.getKey());
                }
                adapter1.notifyDataSetChanged();}
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String k=keysList.get(i);
        Intent ccc = new Intent(sh_order.this, orderdetails.class);
        ccc.putExtra("key",k);
        ccc.putExtra("Name", nm);
        startActivity(ccc);
    }
});

    }
}
