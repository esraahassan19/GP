package com.example.esso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by تن on 07/06/2018.
 */

public class custm_ListOrderAdapter extends ArrayAdapter<order>{
    Context context;
    int resource;
    ArrayList<order> Orders;
    public custm_ListOrderAdapter(Context context, int resource ,ArrayList<order> Orders){
        super(context, resource, Orders);
        this.context=context;
        this.resource=resource;
        this.Orders = Orders;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cusm_ord_layout, null, false);

        }
        order reord = getItem(position);

        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        txtName.setText(reord.getname());

        TextView txtPrice = (TextView) convertView.findViewById(R.id.textnumtable);
        txtPrice.setText("his Table Number is "+reord.getTable_num());


        return convertView;
    }}
