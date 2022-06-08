package com.tequeno.bar.fancyview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tequeno.bar.R;

import java.util.List;

public class MySpinnerAdapter extends BaseAdapter {

    private final static String TAG = "MySpinnerAdapter";

    private final List<MyViewDto> dataList;
    private final Context ctx;

    public MySpinnerAdapter(Context ctx, List<MyViewDto> dataList) {
        this.ctx = ctx;
        this.dataList = dataList;
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

    /**
     * 该方法会被framework重复调用以加载数据
     * convertView会重复使用 不会重复创建
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (null == convertView) {
            Log.d(TAG, "getView: convertView");
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_base_item, parent, false);
            holder = new MyViewHolder();
            holder.iv = convertView.findViewById(R.id.base_iv);
            holder.tv = convertView.findViewById(R.id.base_tv);
            holder.btn = convertView.findViewById(R.id.base_btn);
            convertView.setTag(holder);
        } else {
            Log.d(TAG, "recycle: convertView");
            holder = (MyViewHolder) convertView.getTag();
        }
        // 重复使用convertView和其他组件 组件需要复用 数据需要重新设置
        MyViewDto dto = dataList.get(position);
        holder.iv.setImageResource(dto.rid);
        holder.tv.setText(dto.tvName);
        holder.btn.setText(dto.btnName);
        return convertView;
    }
}