package com.example.esso;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class editcatlist extends AppCompatActivity {
    private ArrayList<String> catList=new ArrayList<>();
    private ArrayList<items> arrayList = new ArrayList<>();
    private ArrayList<String> keysList = new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference ref;
    Restaurant obj;
    public String key1;
    Integer  index;
    public String nm;
    ListView ll;
     ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcatlist);

         nm = getIntent().getExtras().getString("Name");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Restaurant");
        Query query2 = ref.orderByChild("qr_name").equalTo(nm);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Restaurant string = dataSnapshot.getValue(Restaurant.class);
                obj = dataSnapshot.getValue(Restaurant.class);
                catList=(ArrayList<String>) obj.getcat_nam();
                keysList.add(dataSnapshot.getKey());
                 ll = (ListView) findViewById(R.id.edlist);
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,catList);
                ll.setAdapter(adapter);
                registerForContextMenu(ll);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


               //  arrayList.remove(string);
                //keysList.remove(dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final EditText CAT_TEXT = (EditText) findViewById(R.id.editText3);
        Button b = (Button) findViewById(R.id.addcattbtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cc = CAT_TEXT.getText().toString();
                catList.add(cc);
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, catList);
                ll.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                CAT_TEXT.getText().clear();
                database = FirebaseDatabase.getInstance();
                ref = database.getReference("Restaurant");
                ref.child(keysList.get(0)).child("cat_nam").setValue(catList);}
        });}
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.catmenu, menu);
        menu.setHeaderTitle("Select the Operation");
        super.onCreateContextMenu(menu, v, menuInfo);}
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delcat:
                AlertDialog.Builder dialog = new AlertDialog.Builder(editcatlist.this);
                dialog.setCancelable(false);
                dialog.setTitle("Dialog on Android");
                dialog.setMessage("Are you sure you want to delete this ?");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Action for "Delete".
                        index = info.position;
                        key1 = keysList.get(index);
                        final String removedcat=catList.get(index.intValue());
                        catList.remove(index.intValue());
                        final ListView ll = (ListView) findViewById(R.id.edlist);
                        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, catList);
                        ll.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        database = FirebaseDatabase.getInstance();
                        ref = database.getReference("Restaurant");
                        ref.child(keysList.get(0)).child("cat_nam").setValue(catList);

                        ref = database.getReference("items");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot data:dataSnapshot.getChildren())
                                { String n=data.child("restaurant_name").getValue().toString();
                                    String c=data.child("cat").getValue().toString();
                                    if(n.equals(nm)&&c.equals(removedcat))
                                    {
                                       ref.child(data.getKey()).removeValue();}}}
                            @Override
                            public void onCancelled(DatabaseError databaseError) {}});
                    }
                }).setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                        dialog.cancel();
                    }
                });

                final AlertDialog alert = dialog.create();
                alert.show();

                return true;
        }
        return false;
    }
}