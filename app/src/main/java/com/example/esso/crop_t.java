package com.example.esso;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class crop_t extends AppCompatActivity {
    private DatabaseReference mDatabase;
    public items post = new items();
    public String OCRresult = "";
    ByteArrayOutputStream stream;
    Bitmap bitmapp;
    ImageView imageView;
    Toolbar toolbar;
    File file;
    String x;
    public char tmp = ' ';
    ImageView mm;
    Button but;
    Uri uri;
    int voic_code=1;
    Intent CamIntent, GalIntent, CropIntent;
    final int RequestPermissionCode = 1;
    EditText d;
    EditText p;
    EditText dd;
    Bitmap image; //our image
    private TessBaseAPI mTess; //Tess API reference
    String datapath = ""; //path to folder containing language data file
    boolean f = false;
    EditText cc;
    String bitmapToString;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_t);
      /*  getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#e6e6e6")));*/
        final String nm=getIntent().getExtras().getString("Name");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("items");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                id= ((String.valueOf(dataSnapshot.getChildrenCount())));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {//Handle possible errors.
            }});
        d = (EditText) findViewById(R.id.edname);
        p = (EditText) findViewById(R.id.pricee);
        dd = (EditText) findViewById(R.id.descr);
        cc = (EditText) findViewById(R.id.catname);
        imageView = (ImageView) findViewById(R.id.imageView);
        int permissionCheck = ContextCompat.checkSelfPermission(crop_t.this, android.Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
            RequestRuntimePermission();
       // ImageButton n_v=(ImageButton) findViewById(R.id.v1);
        ImageButton name = (ImageButton) findViewById(R.id.name);
        ImageButton price = (ImageButton) findViewById(R.id.Price);
        ImageButton desc = (ImageButton) findViewById(R.id.DES);
        ImageButton category = (ImageButton) findViewById(R.id.cat);
        ImageButton galary=(ImageButton)findViewById(R.id.imageButton3);
        final ImageButton img = (ImageButton) findViewById(R.id.img_bt);
        Button add = (Button) findViewById(R.id.con_btn);
     /*  n_v.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

            Intent ii=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
               startActivityForResult(ii,voic_code);
               tmp='v';
           }
       });*/
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = 'n';
                CameraOpen();

            }
        });
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = 'p';
                CameraOpen();

            }
        });
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = 'd';
                CameraOpen();

            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = 'c';
                CameraOpen();

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = 'i';
                CameraOpen();
            }
        });
        galary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = 'g';
                GalleryOpen();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = (EditText) findViewById(R.id.edname);
                p = (EditText) findViewById(R.id.pricee);
                dd = (EditText) findViewById(R.id.descr);
                cc = (EditText) findViewById(R.id.catname);
                mm = (ImageView) findViewById(R.id.imageView2);
                //--------------------------------------insert
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference posts = database.getReference("items");
                post.setName(d.getText().toString());
                post.setPrice(p.getText().toString());
                post.setdes(dd.getText().toString());
                post.setImage(bitmapToString);
                post.setRestaurant_name(nm);
                post.setcat(cc.getText().toString());
                int iditem=Integer.parseInt(id)+1;
                post.setItem_id(String.valueOf(iditem));
                posts.push().setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(crop_t.this, "item Add Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(crop_t.this, "Error : post not add ?? ", Toast.LENGTH_SHORT).show();
                        }}
                });
                d.setText("");
                p.setText("");
                dd.setText("");
                cc.setText("");
            }
        });
        Button show = (Button) findViewById(R.id.button4);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(crop_t.this,showmeue.class);
                i.putExtra("Name",nm);
                startActivity(i);}
        });}
    private void RequestRuntimePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(crop_t.this, android.Manifest.permission.CAMERA))
            Toast.makeText(this,"CAMERA permission allows us to access CAMERA app",Toast.LENGTH_SHORT).show();
        else
        {
            ActivityCompat.requestPermissions(crop_t.this,new String[]{Manifest.permission.CAMERA},RequestPermissionCode);
        }
    }
    private void GalleryOpen() {
        GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent,"Select Image from Gallery"),2);
    }
    private void CameraOpen() {
        CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(),
                "file"+String.valueOf(System.currentTimeMillis())+".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        CamIntent.putExtra("return-data",true);
        startActivityForResult(CamIntent,0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == RESULT_OK)
            CropImage();
        else if(requestCode == 2)
        {if(data != null)
        {uri = data.getData();
            CropImage();}}
        else if (requestCode == 1)
        {if(data != null) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = bundle.getParcelable("data");
            imageView.setImageBitmap(bitmap);
            bitmapToString = BitMapToString(bitmap);
            imageView.setDrawingCacheEnabled(true);
            bitmapp = imageView.getDrawingCache();
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmapp,"Es", "jjj");
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            mm=(ImageView) findViewById(R.id.imageView2);
            datapath = getFilesDir()+ "/tesseract/";
            checkFile(new File(datapath+"tessdata/"));
            String lang = "eng";
            mTess = new TessBaseAPI();
            mTess.init(datapath, lang);
            if(tmp=='i'||tmp =='g')
                mm.setImageBitmap(bitmap);
            else
               // processImage();
            {     OCRresult = null;
                mTess.setImage(bitmap);
                OCRresult = mTess.getUTF8Text();
                d=(EditText)findViewById(R.id.edname);
                p=(EditText)findViewById(R.id.pricee);
                dd=(EditText)findViewById(R.id.descr);
                cc=(EditText)findViewById(R.id.catname);
                switch (tmp)
                {  /* case 'v':
               d.setText(x);*/
                    case 'n' :
                        d.setText(OCRresult);
                        break;
                    case 'p' :
                        p.setText(OCRresult);
                        break;
                    case 'd' :
                        dd.setText(OCRresult);
                        break;
                    case 'c' :
                        cc.setText(OCRresult);
                        break;
                    default:
                        break;
                }}
        }}
    /*   else if(voic_code==requestCode &&resultCode==RESULT_OK)
        {
            ArrayList<String> arr=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            x=arr.get(0);

        }*/
    }
    private void CropImage() {
        try{
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri,"image/*");
            CropIntent.putExtra("crop","true");
            CropIntent.putExtra("outputX",180);
            CropIntent.putExtra("outputY",180);
            CropIntent.putExtra("aspectX",8);
            CropIntent.putExtra("aspectY",3);
            CropIntent.putExtra("scaleUpIfNeeded",true);
            CropIntent.putExtra("scaleDownIfNeeded",true);
            CropIntent.putExtra("return-data",true);

            startActivityForResult(CropIntent,1);
        }
        catch (ActivityNotFoundException ex)
        {}}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {case RequestPermissionCode:
        {if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Permission Canceled",Toast.LENGTH_SHORT).show();
        }break;}}

   /* public void processImage(){
        OCRresult = null;
        mTess.setImage(bitmap);
        OCRresult = mTess.getUTF8Text();
        d=(EditText)findViewById(R.id.edname);
        p=(EditText)findViewById(R.id.pricee);
        dd=(EditText)findViewById(R.id.descr);
        cc=(EditText)findViewById(R.id.catname);
        switch (tmp)
        {
            case 'n' :
                d.setText(OCRresult);
                break;
            case 'p' :
                p.setText(OCRresult);
                break;
            case 'd' :
                dd.setText(OCRresult);
                break;
            case 'c' :
                cc.setText(OCRresult);
                break;
            default:
                break;
        }

    }*/

    private void checkFile(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();}}}
    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();
            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
