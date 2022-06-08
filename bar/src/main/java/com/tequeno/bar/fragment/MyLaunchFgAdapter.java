package com.tequeno.bar.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tequeno.bar.R;

import java.util.Arrays;
import java.util.List;

public class MyLaunchFgAdapter extends FragmentPagerAdapter {

    private final static String TAG = "MyLaunchFgAdapter";
    private final List<Integer> ridList;

    public MyLaunchFgAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ridList = Arrays.asList(R.drawable.vp_l_1, R.drawable.vp_l_2, R.drawable.vp_l_3
                , R.drawable.vp_l_4, R.drawable.vp_l_5);
        Log.d(TAG, "MyLaunchFgAdapter");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new LaunchFragment(position, ridList.get(position), ridList.size() - 1);
    }

    @Override
    public int getCount() {
        return ridList.size();
    }

}