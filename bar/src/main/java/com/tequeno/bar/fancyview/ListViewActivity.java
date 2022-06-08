package com.tequeno.bar.fancyview;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.tequeno.bar.MainUtil;
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

        List<MyViewDto> list = IntStream.range(0, 100)
                .boxed()
                .map(i -> {
                    MyViewDto dto = new MyViewDto();
                    dto.tvName = "item" + i;
                    dto.ckName = "checked" + i;
                    dto.btnName = "btn" + i;
                    return dto;
                })
                .collect(Collectors.toList());

        ListView listView = findViewById(R.id.lv);
        listView.setAdapter(new MyListViewAdapter(list, this));
        listView.setOnItemClickListener((parent, view, position, id) -> Log.d(TAG, "setOnItemClick: " + list.get(position)));
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setMessage("是否----------????")
                    .setNegativeButton("否", (dialog, which) -> Log.d(TAG, "AlertDialog: 否 " + dialog + which))
                    .setPositiveButton("是", (dialog, which) -> Log.d(TAG, "AlertDialog: 是 " + dialog + which))
                    .create().show();
            return true;
        });

        // 根据是否选中 动态改变是否需要分割线
        CheckBox ckd = findViewById(R.id.ck_d);
        ckd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                listView.setDivider(AppCompatResources.getDrawable(this, R.color.black));
                listView.setDividerHeight(MainUtil.dip2Px(this, 1));
            } else {
                listView.setDivider(null);
                listView.setDividerHeight(0);
            }
        });
//        // 根据是否选中 动态改变是否需要背景色
//        CheckBox cks = findViewById(R.id.ck_s);
//        cks.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            Log.d(TAG, "cks OnCheckedChange: " + isChecked);
//            if(isChecked) {
//                listView.setSelector(R.drawable.ic_lv_selecor);
//            } else {
//                listView.setSelector(AppCompatResources.getDrawable(this, R.color.bg_trans));
//            }
//        });
    }
}