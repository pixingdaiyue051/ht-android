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
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView param1: " + param1);
        Bundle args = getArguments();
        if (null != args) {
            Log.d(TAG, "onCreateView param2: " + args.getString("param2", "null1111"));
        }
        if (null != callback) {
            String call = callback.call();
            String call1 = callback.call1();
            String call2 = callback.call2();
            String call3 = callback.call3();
            Log.d(TAG, "onCreateView callback: " + call + call1 + call2 + call3);
        }
        return inflater.inflate(R.layout.fragment_test1, container, false);
    }
}