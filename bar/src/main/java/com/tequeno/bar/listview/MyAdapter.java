package com.tequeno.bar.listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tequeno.bar.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private final static String TAG = "MyAdapter";

    private final List<String> dataList;
    private final Context ctx;

    public MyAdapter(List<String> dataList, Context ctx) {
        this.dataList = dataList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: " + position);
        if (null == convertView) {
            Log.d(TAG, "getView: convertView");
            convertView = LayoutInflater.from(ctx).inflate(R.layout.list_view_item, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.tv);
        textView.setText(dataList.get(position));
        if(!textView.hasOnClickListeners()) {
            Log.d(TAG, "getView-setOnClickListener");
            textView.setOnClickListener(view -> Log.d(TAG, "clicked"));
        }
        return textView;
    }
}