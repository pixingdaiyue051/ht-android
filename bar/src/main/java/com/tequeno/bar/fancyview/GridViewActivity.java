package com.tequeno.bar.fancyview;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.R;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GridViewActivity extends AppCompatActivity {

    private final static String TAG = "GridViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        List<MyViewDto> list = IntStream.range(0, 5)
                .boxed()
                .map(i -> {
                    MyViewDto dto = new MyViewDto();
                    dto.tvName = "item" + i;
                    dto.rid = R.drawable.iv_87434;
                    dto.btnName = "btn" + i;
                    return dto;
                })
                .collect(Collectors.toList());
        GridView gridView = findViewById(R.id.gv);
        gridView.setAdapter(new MyGridViewAdapter(list, this));
    }
}