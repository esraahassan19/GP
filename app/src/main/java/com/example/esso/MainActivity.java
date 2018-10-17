package com.example.esso;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView timeUpdate;
    Calendar calendar;
    public static String PREFS_NAME="Name";
    private DatabaseReference mDatabase;
    public items post = new items();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6"))
        );
        TextSwitcher switcher = (TextSwitcher)findViewById(R.id.txtSwitcher);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(MainActivity.this);
                //do other stuff the suit your needs
                return t;
            }
        });

        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);

        switcher.setInAnimation(in);
        switcher.setOutAnimation(out);
       switcher.setText("Welcom in Restaurants Club ");
        switcher.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.move_it));
        Button reg=(Button)findViewById(R.id.button4);
        Button log=(Button)findViewById(R.id.button2);
       log.setTypeface(log.getTypeface(), Typeface.BOLD);
        log.setTypeface(log.getTypeface(),Typeface.ITALIC);

        reg.setTypeface(reg.getTypeface(), Typeface.BOLD);
        reg.setTypeface(reg.getTypeface(), Typeface.ITALIC);
    TextView f=(TextView)findViewById(R.id.logtxt) ;
        f.setTypeface(f.getTypeface(), Typeface.BOLD);
        f.setTypeface(f.getTypeface(),Typeface.ITALIC);

        TextView ff=(TextView)findViewById(R.id.signtxt) ;
        ff.setTypeface(ff.getTypeface(), Typeface.BOLD);
        ff.setTypeface(ff.getTypeface(),Typeface.ITALIC);
        //global declaration

         reg.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent ii=new Intent(MainActivity.this,esraa.class);
         startActivity(ii);}


 });

       log.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this,login.class);
               startActivity(intent);
           }
       });



    }
}
