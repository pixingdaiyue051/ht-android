package com.tequeno.bar.fancyview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tequeno.bar.R;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {

    private final static String TAG = "MyListViewAdapter";

    private final List<MyViewDto> dataList;
    private final Context ctx;

    public MyListViewAdapter(List<MyViewDto> dataList, Context ctx) {
        this.dataList = dataList;
        this.ctx = ctx;
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_listview_item, parent, false);
            holder = new MyViewHolder();
//            holder.ck = convertView.findViewById(R.id.ck);
            holder.tv = convertView.findViewById(R.id.tv);
//            holder.btn = convertView.findViewById(R.id.btn);
//            holder.ck.setOnCheckedChangeListener((b,isChecked)-> Log.d(TAG, "holder.ck " + isChecked));
//            holder.tv.setOnClickListener(v -> Log.d(TAG, "holder.tv "));
//            holder.btn.setOnClickListener(v -> Log.d(TAG, "holder.btn "));
            convertView.setTag(holder);
        } else {
            Log.d(TAG, "recycle: convertView");
            holder = (MyViewHolder) convertView.getTag();
        }
        MyViewDto dto = dataList.get(position);
//        holder.ck.setText(dto.ckName);
        holder.tv.setText(dto.tvName);
//        holder.btn.setText(dto.btnName);
        return convertView;
    }
}