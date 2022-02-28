package com.adapterclass.todomobileapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private List<AvengersData> list;
    private Context context;
    public CustomListAdapter(List<AvengersData> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_profile_item, parent, false);
        TextView tvName = convertView.findViewById(R.id.item_name);
        TextView tvName1 = convertView.findViewById(R.id.item_message);
        TextView tvName2 = convertView.findViewById(R.id.DateTime);
        AvengersData data = list.get(position);

        tvName.setText(data.getTitle());
        tvName1.setText(data.getMessage());
        tvName2.setText(data.getDateTime());

        return convertView;
    }
}
