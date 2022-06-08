package com.tequeno.bar.fancyview;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.tequeno.bar.R;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ViewPagerActivity extends AppCompatActivity {

    private final static String TAG = "ViewPagerActivity";
    private List<MyViewDto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = findViewById(R.id.vp);

        list = IntStream.range(0, 5)
                .boxed()
                .map(i -> {
                    MyViewDto dto = new MyViewDto();
                    dto.tvName = "item" + i;
                    dto.rid = R.drawable.iv_83372;
                    return dto;
                }).collect(Collectors.toList());

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(list, this);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
        viewPager.setCurrentItem(2);

        // 加载标签栏
        PagerTabStrip pts = findViewById(R.id.pts);
        pts.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        pts.setTextColor(Color.RED);
    }

    private class MyPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected: " + list.get(position).tvName);
        }
    }
}