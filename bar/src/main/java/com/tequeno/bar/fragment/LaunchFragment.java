package com.tequeno.bar.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.tequeno.bar.MainUtil;
import com.tequeno.bar.R;

public class LaunchFragment extends Fragment {

    private final static String TAG = "LaunchFragment";

    private final int position;
    private final int rid;
    private final int idx;

    public LaunchFragment(int position, int rid, int idx) {
        Log.d(TAG, "LaunchFragment: " + position);
        this.position = position;
        this.rid = rid;
        this.idx = idx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + position);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch, container, false);
        ImageView iv = view.findViewById(R.id.iv_l_bg);
        iv.setImageResource(rid);
        if (position == idx) {
            Button btn = view.findViewById(R.id.btn_l_start);
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(v -> MainUtil.toast("thank you"));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: " + position);
    }
}