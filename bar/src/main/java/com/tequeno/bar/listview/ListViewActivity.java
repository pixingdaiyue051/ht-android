package com.tequeno.bar.listview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.R;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListViewActivity extends AppCompatActivity {

    private final static String TAG = "ListViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        List<String> list = IntStream.range(0, 100)
                .boxed()
                .map(i -> "item" + i)
                .collect(Collectors.toList());

        ListView listView = findViewById(R.id.lv);
        listView.setAdapter(new MyAdapter(list, this));
    }
}