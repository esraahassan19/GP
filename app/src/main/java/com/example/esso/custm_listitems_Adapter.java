package com.example.esso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class custm_listitems_Adapter extends ArrayAdapter<ord_item> {
    Context context;
    int     resource;
    ArrayList<ord_item> ord_item;
    public custm_listitems_Adapter(Context context, int resource ,ArrayList<ord_item> ord_item){
        super(context,resource,ord_item);
        this.ord_item=ord_item;
        this.context=context;
        this.resource=resource;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_list_item, null, false);

        }
        ord_item o = getItem(position);

        TextView n = (TextView) convertView.findViewById(R.id.itemname);
        n.setText(o.getName());

        TextView pp = (TextView) convertView.findViewById(R.id.p);
        pp.setText("Price : "+o.getprice() +"L.E");

        TextView quqnt = (TextView) convertView.findViewById(R.id.q);
        quqnt.setText("Quantity : "+o.getQuantity());

        return convertView;
    }
}
