package com.example.esso;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class login extends AppCompatActivity {
Button scan_btn;
    public static String PREFS_NAME="Name";
    public static String PREF_USERNAME="qr_name";
    String res;
    Restaurant obj;
    public String y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );

     /*   SharedPreferences shr=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String qr=shr.getString(PREF_USERNAME,null);
        Boolean islogin=false;
        if(qr!=null )
        {
            res=qr;
            islogin =true;
        }

        if(islogin.equals(true)){
            Intent intent = new Intent(login.this, showmeue.class);
            startActivity(intent);
            finish();
            return;
        }*/
                final Activity activity = this;
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     final    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();}
            else {
                Toast.makeText(getApplicationContext(), result.getContents(),Toast.LENGTH_LONG).show();
                res=result.getContents();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("Restaurant");
                Query query = ref.orderByChild("qr_name").equalTo(res);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(),"Welcome to app",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(login.this,showmeue.class);
                            intent.putExtra("Name",res);
                            startActivity(intent);}
                        else
                        {AlertDialog.Builder dialog = new AlertDialog.Builder(login.this);
                            dialog.setCancelable(false);
                            dialog.setTitle("Dialog on Android");
                            dialog.setMessage("press No if you need to Try again but if You don't have account Please press Yes to create new account" );
                            dialog.setPositiveButton("Yes ", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                             Intent intent=new Intent(login.this,esraa.class);
                             startActivity(intent);
                             dialog.cancel();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Action for "Cancel".
                                    Intent intent=new Intent(login.this,MainActivity.class);
                                    startActivity(intent);
                                    dialog.cancel();

                                }
                            });
                            final AlertDialog alert = dialog.create();
                            alert.show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException(); // don't ignore errors
                    }
                });

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //end

}
