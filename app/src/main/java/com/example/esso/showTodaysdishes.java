package com.example.esso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.EuclideanDistance;

import java.util.ArrayList;

public class showTodaysdishes extends AppCompatActivity {
    String gender;
    double g;
    String uname;
    String date;
    String time;
    String birthdate;
    double h;
    double range;
    double day;
    double m;
    double age;
    double ordid;
    String ord_id;
    int c;
    ArrayList<order> or;
    ListView lv;
    ArrayList<order> list;
    ArrayList<order> list2;
    ArrayList<order> list3;
    ArrayList<order> list4;
    ArrayList<order> list5;
    ArrayList<String> lista1;
    public ArrayAdapter<String>  adapter11;
    public custm_ListOrderAdapter adapter1=null;
    FirebaseDatabase database;
    DatabaseReference ref;
    private ArrayList<String> keysList = new ArrayList<>();
    Object orid;
    net.sf.javaml.core.Dataset dataset;
String nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_todaysdishes);

         nm=getIntent().getExtras().getString("Name");
       or = new ArrayList<order>();
        dataset = new DefaultDataset();
        c=0;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Order");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    order O=new order();
                    O.setname(data.child("name").getValue().toString());
                    O.setDate(data.child("date").getValue().toString());
                    O.settime(data.child("time").getValue().toString());
                    O.setOrd_id(data.child("ord_id").getValue().toString());
                    or.add(O);
                    c++;
                }
            //    Toast.makeText(getApplicationContext(),"Esrfff",Toast.LENGTH_LONG).show();
                getuser(or);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't ignore errors
                 }});}
    public void getuser(final ArrayList<order>ord) {
        final ArrayList<User>user=new ArrayList<>();
        final FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = database1.getReference("User");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                for (DataSnapshot d : dataSnapshot1.getChildren()) {
                    User use=new User();
                    use.setGender(d.child("gender").getValue().toString());
                    use.setusername(d.child("username").getValue().toString());
                    use.setBirth_date(d.child("birth_date").getValue().toString());
                    user.add(use);
                }
                for(order od:ord) {
                   // ord_id= d.child("ord_id").getValue().toString();
                   ord_id=od.getOrd_id();
                   // orid=ord_id;
                   // ordid=Double.parseDouble(ord_id);
                    date = od.getDate();
                    String[] sp = date.split("-");
                    day = Double.parseDouble(sp[0]);
                    m = Double.valueOf(sp[1]);
                    time = od.gettime();
                    String[] t = time.split(":");
                    h = Double.valueOf(t[0]);
                    if (h >= 10 && h < 14) {
                        range = 1;
                    } else if (h >= 14 && h < 18) {
                        range = 2;
                    } else if (h >= 18) {
                        range = 3;
                    }
                    for(User u:user){
                        if(u.getusername().equals(od.getname())){
                            birthdate = u.getBirth_date();
                            String[] da = birthdate.split("-");
                            Time today = new Time(Time.getCurrentTimezone());
                            today.setToNow();
                            age = (double) today.year - Double.valueOf(da[2]);
                            gender = u.getGender();
                            if (gender.equals("male")) {
                                g = 0;
                            } else if (gender.equals("female")) {
                                g = 1;
                            }break;}}
                    Instance instance1 = new DenseInstance(new double[]{g, range, day, m, age},ord_id);
                    dataset.add(instance1);}
                ///////////////
               lv = (ListView) findViewById(R.id.l1);
                list =new ArrayList<order>();
             final RadioButton r1=(RadioButton)findViewById(R.id.radioButton);
                final  RadioButton r2=(RadioButton)findViewById(R.id.radioButton2);
                final  RadioButton r3=(RadioButton)findViewById(R.id.radioButton3);
                final  RadioButton r4=(RadioButton)findViewById(R.id.radioButton4);
                final RadioButton r5=(RadioButton)findViewById(R.id.radioButton5);
                if(r1.isChecked())
                adapter1 = new custm_ListOrderAdapter(getApplicationContext(), R.layout.cusm_ord_layout, list);
                else if(r2.isChecked())
                adapter1 = new custm_ListOrderAdapter(getApplicationContext(), R.layout.cusm_ord_layout, list2);
                else if(r3.isChecked())
                    adapter1 = new custm_ListOrderAdapter(getApplicationContext(), R.layout.cusm_ord_layout, list3);
                else if(r4.isChecked())
                    adapter1 = new custm_ListOrderAdapter(getApplicationContext(), R.layout.cusm_ord_layout, list4);
                else if(r5.isChecked())
                    adapter1 = new custm_ListOrderAdapter(getApplicationContext(), R.layout.cusm_ord_layout, list5);

                lv.setAdapter(adapter1);

                list =new ArrayList<order>();
                list2 =new ArrayList<order>();
                list3 =new ArrayList<order>();
                list4 =new ArrayList<order>();
                list5 =new ArrayList<order>();

              //  lista1=new ArrayList<String>();


                Clusterer c2;
                c2 = new KMedoids(5,100,new EuclideanDistance());
                Dataset[] clusterss = c2.cluster(dataset);
                String order_id;
                list.clear();
                list2.clear();
                list3.clear();
                list4.clear();
                list5.clear();
                for (int i = 0; i < clusterss.length; i++) {
                    for(int j=0;j<clusterss[i].size();j++) {
                       final int E=i;
                        order_id = clusterss[i].instance(j).classValue().toString();
                        keysList.add(order_id);
                        final order obj=new order();
                        database = FirebaseDatabase.getInstance();
                        ref = database.getReference("Order");
                        Query query = ref.orderByChild("ord_id").equalTo(order_id);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot dsp : dataSnapshot.getChildren())
                                {

                                    String x=dsp.child("name").getValue().toString();
                                    String y=dsp.child("table_num").getValue().toString();
                                    /////////////////////////////////////
                                    String ord_id=dsp.child("ord_id").getValue().toString();
                                    /////////////////////////////////////

                                    if(E==0)
                                    {  list.add(new order("Cluster : "+String.valueOf(E+1)+"  " +x,y,ord_id));}
                                    else if(E==1)
                                        list2.add(new order("Cluster : "+String.valueOf(E+1)+"  " +x,y,ord_id));
                                    else if(E==2)
                                        list3.add(new order("Cluster : "+String.valueOf(E+1)+"  " +x,y,ord_id));
                                    else if(E==3)
                                        list4.add(new order("Cluster : "+String.valueOf(E+1)+"  " +x,y,ord_id));
                                    else if(E==4)
                                        list5.add(new order("Cluster : "+String.valueOf(E+1)+"  " +x,y,ord_id));

                                }
                                adapter1.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }});}}
               lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      //  String k=keysList.get(i);
                        String k="";
                        if(r1.isChecked())
                            k=list.get(i).getOrd_id();
                        else if(r2.isChecked())
                            k=list2.get(i).getOrd_id();
                        else if(r3.isChecked())
                            k=list3.get(i).getOrd_id();
                        else if(r4.isChecked())
                            k=list4.get(i).getOrd_id();
                        else if(r5.isChecked())
                            k=list5.get(i).getOrd_id();
                       Intent ces = new Intent(showTodaysdishes.this, clus_ord_det.class);
                        ces.putExtra("id",k);
                        ces.putExtra("Name", nm);
                        startActivity(ces);
                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

      //  Toast.makeText(getApplicationContext(), instance1.toString()+" "+od.getname(), Toast.LENGTH_LONG).show();

    }
}
