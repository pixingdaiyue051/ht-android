package com.tequeno.bar.fancyview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tequeno.bar.R;

import java.util.List;

public class MyGridViewAdapter extends BaseAdapter {

    private final static String TAG = "MyGridViewAdapter";

    private final List<MyViewDto> dataList;
    private final Context context;

    public MyGridViewAdapter(List<MyViewDto> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (null == convertView) {
            Log.d(TAG, "getView: convertView");
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_gridview_item, parent, false);
            holder = new MyViewHolder();
            holder.iv = convertView.findViewById(R.id.iv);
            holder.tv = convertView.findViewById(R.id.tv);
            holder.btn = convertView.findViewById(R.id.btn);
            convertView.setTag(holder);
        } else {
            Log.d(TAG, "recycle: convertView");
            holder = (MyViewHolder) convertView.getTag();
        }
        MyViewDto dto = dataList.get(position);
        holder.iv.setImageResource(dto.rid);
        holder.tv.setText(dto.tvName);
        holder.btn.setText(dto.btnName);
        return convertView;
    }
}