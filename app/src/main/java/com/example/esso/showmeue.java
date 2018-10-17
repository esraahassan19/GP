package com.example.esso;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class showmeue extends AppCompatActivity {
    ListView lv;
    CustpmerListAdapter adapter = null;
     ArrayList<items> list;
    public static String PREFS_NAME="Name";
    String s;
    items obj;
    String nm;
    Bitmap bitmap;
    FirebaseDatabase database;
    DatabaseReference ref;
    private ArrayList<items> arrayList = new ArrayList<>();
    private ArrayList<String> keysList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmeue);
     /*   getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );*/
        nm=getIntent().getExtras().getString("Name");
        Integer index;
        lv = (ListView) findViewById(R.id.listView);
        list =new ArrayList<items>();

       database = FirebaseDatabase.getInstance();
       ref = database.getReference("items");

        Query query = ref.orderByChild("restaurant_name").equalTo(nm);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    list.add(dsp.getValue(items.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
            }
        });
        adapter = new CustpmerListAdapter(this, R.layout.custom_lst_layout, list);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        Query query2 = ref.orderByChild("restaurant_name").equalTo(nm);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    items string = dataSnapshot.getValue(items.class);
                    obj = dataSnapshot.getValue(items.class);
                   arrayList.add(string);
                    keysList.add(dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                items string = dataSnapshot.getValue(items.class);

                arrayList.remove(string);
                keysList.remove(dataSnapshot.getKey());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.contextmenu ,menu);
        menu.setHeaderTitle("Select the Operation");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
     final    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {case R.id.item1:
                Integer index=info.position;
              //  Toast.makeText(this,obj.getName(),Toast.LENGTH_LONG).show();
                String key1 = keysList.get(index);
               items x= arrayList.get(index) ;
                Intent i=new Intent(showmeue.this,Updatee.class);
                i.putExtra("position",key1);
                i.putExtra("itemsname", x.getName());
                i.putExtra("itemscat",  x.getcat());
                i.putExtra("itemsdes",  x.getdes());
                i.putExtra("itemsimage",x.getImage());
                i.putExtra("itemsprice",x.getPrice());
                i.putExtra("Name",nm);
                startActivity(i);
                return true;
              case R.id.item2:
                  AlertDialog.Builder dialog = new AlertDialog.Builder(showmeue.this);
                  dialog.setCancelable(false);
                  dialog.setTitle("Select Operation ");
                  dialog.setMessage("Are you sure you want to delete this ?" );
                  dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int id) {
                          //Action for "Delete".
                         Integer index=info.position;
                          list.remove(index.intValue());
                          adapter.notifyDataSetChanged();
                          String key = keysList.get(index);
                          ref.child(key).removeValue();

                      }
                  })
                          .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wait:
                Intent c = new Intent(showmeue.this, towaitinglist.class);
                c.putExtra("Name", nm);
                startActivity(c);
                return true;
            case R.id.add:
                Intent cc = new Intent(showmeue.this, crop_t.class);
                cc.putExtra("Name", nm);
                startActivity(cc);
                return true;
            case R.id.showOrd:
                Intent ccc = new Intent(showmeue.this, sh_order.class);
                ccc.putExtra("Name", nm);
                startActivity(ccc);
                return true;
            case R.id.allcategory:
                Intent xx=new Intent(showmeue.this, editcatlist.class);
                xx.putExtra("Name",nm);
                startActivity(xx);
            case R.id.todaysdishes:
                Intent es=new Intent(showmeue.this, showTodaysdishes.class);
                es.putExtra("Name",nm);
                startActivity(es);
            case R.id.logout:
                AlertDialog.Builder builder=new AlertDialog.Builder(showmeue.this); //Home is name of the activity
                builder.setMessage("Do you want to exit?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        Intent i=new Intent(showmeue.this, MainActivity.class);
                       i.putExtra("finish", true);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                         startActivity(i);
                        SharedPreferences sh=getSharedPreferences(showmeue.PREFS_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor edit=sh.edit();
                        edit.clear();
                        edit.commit();
                        finish();

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
                break;
        }
        return false;
    }

}
