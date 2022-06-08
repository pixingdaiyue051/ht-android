package com.tequeno.bar.fancyview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.tequeno.bar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyViewPagerAdapter extends PagerAdapter {

    private final static String TAG = "MyViewPagerAdapter";

    private final List<MyViewDto> dataList;
    private final Context ctx;
    private List<View> viewList;

    public MyViewPagerAdapter(List<MyViewDto> dataList, Context ctx) {
        this.dataList = dataList;
        this.ctx = ctx;
        if (null == dataList) {
            LayoutInflater inflater = LayoutInflater.from(ctx);
            View view1 = inflater.inflate(R.layout.view_pager_1, null);
            View view2 = inflater.inflate(R.layout.view_pager_2, null);
            View view3 = inflater.inflate(R.layout.view_pager_3, null);
            viewList = new ArrayList<>();
            viewList.add(view1);
            viewList.add(view2);
            viewList.add(view3);
        } else {
            viewList = dataList.stream().map(data -> {
                ImageView iv = new ImageView(ctx);
                iv.setImageResource(data.rid);
                return iv;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    /**
     * 判断view是否和object关联
     *
     * @param view
     * @param object instantiateItem方法返回值
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * @param container
     * @param position
     * @return 返回一个和view关联的对象 可以是view本身
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    /**
     * @param container
     * @param position
     * @param object    instantiateItem方法返回值
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem: " + position);
        container.removeView(viewList.get(position));
    }

    /**
     * @param position
     * @return 返回翻页标签蓝标题
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return dataList.get(position).tvName;
    }
}