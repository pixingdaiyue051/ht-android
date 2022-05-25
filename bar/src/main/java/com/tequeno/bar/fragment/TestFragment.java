package com.tequeno.bar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tequeno.bar.R;

/**
 *
 */
public class TestFragment extends Fragment {

    private final static String TAG = "TestFragment";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment onCreateView: ");
        return inflater.inflate(R.layout.fragment_test1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "TestFragment onStart: ");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "TestFragment onResume: ");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "TestFragment onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "TestFragment onStop: ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "TestFragment onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "TestFragment onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "TestFragment onDetach: ");
        super.onDetach();
    }
}