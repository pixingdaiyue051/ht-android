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
public class Test2Fragment extends Fragment {

    private final static String TAG = "Test2Fragment";

    private View root;
    private Button btn;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Test2Fragment onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Test2Fragment onCreateView: " + root);
        if (null == root) {
            root = inflater.inflate(R.layout.fragment_test2, container, false);
        }
        if (null == tv) {
            tv = root.findViewById(R.id.fg_tv1);
        }
        if (null == btn) {
            btn = root.findViewById(R.id.fg_btn1);
        }
        if (!btn.hasOnClickListeners()) {
            btn.setOnClickListener(view -> tv.setText("健康状况我看范围覆盖开始公开数据"));
        }
        return root;
    }
}