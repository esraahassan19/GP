package com.example.esso;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustpmerListAdapter extends ArrayAdapter<items> {
    ArrayList<items> items;
    Context context;
    int resource;

    public CustpmerListAdapter(Context context, int resource, ArrayList<items> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_lst_layout, null, false);

        }
        items item = getItem(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewProduct);

        String foodImage = item.getImage();
        Bitmap bitmap = StringToBitMap(foodImage);

        imageView.setImageBitmap(bitmap);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtName.setText(item.getName());

        TextView txtPrice = (TextView) convertView.findViewById(R.id.txtPrice);
        txtPrice.setText(item.getPrice());

        return convertView;
    }
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
