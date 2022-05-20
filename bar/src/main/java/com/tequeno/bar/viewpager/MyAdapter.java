package com.tequeno.bar.viewpager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyAdapter extends PagerAdapter {

    private final static String TAG = "MyAdapter";

    private List<View> viewList;
    private Context ctx;

    public MyAdapter(List<View> viewList, Context ctx) {
        this.viewList = viewList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if(object == viewList.get(position)) {
            Log.d(TAG, "destroyItem: ");
        }
        container.removeView(viewList.get(position));
    }
}