package com.tequeno.bar.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tequeno.bar.R;

/**
 *
 */
public class Test1Fragment extends Fragment {

    private final static String TAG = "Test1Fragment";

    private View root;
    private Button btn;
    private TextView tv;

    private String param1;

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    private FragmentMsg callback;

    public void setCallback(FragmentMsg callback) {
        this.callback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Test1Fragment onCreate: ");
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Test1Fragment onCreate param1: " + param1);
        Bundle args = getArguments();
        if (null != args) {
            Log.d(TAG, "Test1Fragment onCreate param2: " + args.getString("param2"));
        }
        if (null!= callback) {
            String call = callback.call();
            String call1 = callback.call1();
            String call2 = callback.call2();
            String call3 = callback.call3();
            Log.d(TAG, "Test1Fragment onCreate callback: " + call + call1 + call2 + call3);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Test1Fragment onCreateView: " + root);
        if (null == root) {
            root = inflater.inflate(R.layout.fragment_test1, container, false);
        }
        if (null == tv) {
            tv = root.findViewById(R.id.fg_tv1);
        }
        if (null == btn) {
            btn = root.findViewById(R.id.fg_btn1);
        }
        if (!btn.hasOnClickListeners()) {
            btn.setOnClickListener(view -> tv.setText("初步形成多个让他热狗"));
        }
        return root;
    }
}