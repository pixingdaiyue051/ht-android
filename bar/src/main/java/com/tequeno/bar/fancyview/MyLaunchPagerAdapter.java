package com.tequeno.bar.fancyview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.tequeno.bar.MainUtil;
import com.tequeno.bar.R;

import java.util.ArrayList;
import java.util.List;

public class MyLaunchPagerAdapter extends PagerAdapter {

    private final static String TAG = "MyLaunchPagerAdapter";

    private final List<View> viewList;

    public MyLaunchPagerAdapter(Context ctx) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view1 = inflater.inflate(R.layout.view_pager_1, null);
        View view2 = inflater.inflate(R.layout.view_pager_2, null);
        View view3 = inflater.inflate(R.layout.view_pager_3, null);
        View view4 = inflater.inflate(R.layout.view_pager_4, null);
        View view5 = inflater.inflate(R.layout.view_pager_5, null);
        Button btnStart = view5.findViewById(R.id.btn_l_start);
        btnStart.setOnClickListener(v -> MainUtil.toast( "thank you"));
        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        Log.d(TAG, "MyLaunchPagerAdapter");
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
        Log.d(TAG, "instantiateItem: " + position);
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
}