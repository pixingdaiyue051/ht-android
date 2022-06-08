package com.tequeno.bar.fancyview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.bar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerActivity extends AppCompatActivity {

    private final static String TAG = "SpinnerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        TextView tv = findViewById(R.id.tv);

        Spinner dropSpinner = findViewById(R.id.sp_drop);
        List<String> array = new ArrayList<>();
        array.add("once1");
        array.add("once2");
        array.add("once3");
        array.add("once4");
        array.add("once5");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.adapter_item, array);
        dropSpinner.setAdapter(arrayAdapter);
        // 默认选中第一项
        dropSpinner.setSelection(0);
        // 监听选中项
        dropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: " + parent);
                Log.d(TAG, "onItemSelected: " + view);
                Log.d(TAG, "onItemSelected: " + position);
                Log.d(TAG, "onItemSelected: " + id);
//                tv.setText(((TextView) view).getText());
                tv.setText(array.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: " + parent);
            }
        });

        Spinner dropDialog = findViewById(R.id.sp_dialog);
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("icon", String.valueOf(R.drawable.iv_85433));
        map1.put("name", "test1");
        map1.put("desc", "你速度人体发育任何人方式v成功典范4645646");
        list.add(map1);
        Map<String, String> map2 = new HashMap<>();
        map2.put("icon", String.valueOf(R.drawable.iv_87434));
        map2.put("name", "test2");
        map2.put("desc", "56546fgfsd东窗事发华人富豪然后他任何人");
        list.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("icon", String.valueOf(R.drawable.iv_83372));
        map3.put("name", "test3");
        map3.put("desc", "67ffg把整个电饭锅依然有也有人的观点特瑞特瑞特让他");
        list.add(map3);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.adapter_simple_item,
                new String[]{"icon", "name", "desc"}, new int[]{R.id.simple_iv, R.id.simple_tv1, R.id.simple_tv2});
//        simpleAdapter.setViewBinder((view, data, textRepresentation) -> {
//            if (TextUtils.isEmpty(textRepresentation)) {
//                return false;
//            }
//            if (view instanceof ImageView) {
//                ((ImageView) view).setImageResource(Integer.parseInt(textRepresentation));
//            }
//            if (view instanceof TextView) {
//                ((TextView) view).setText(textRepresentation);
//            }
//            return true;
//        });
        dropDialog.setAdapter(simpleAdapter);
        // 设置弹出框标题  只有dialog模式才有效果
        dropDialog.setPrompt("请选择");
        dropDialog.setSelection(1);


        Spinner dropDialog1 = findViewById(R.id.sp_dialog1);
        List<MyViewDto> dataList = new ArrayList<>();
        MyViewDto dto1 = new MyViewDto();
        dto1.tvName = "test-base1";
        dto1.btnName = "表达式的工人挺好的身上的他已经特u热帖";
        dto1.rid = R.drawable.iv_85433;
        dataList.add(dto1);
        MyViewDto dto2 = new MyViewDto();
        dto2.tvName = "test-base2";
        dto2.btnName = "不能发电供热港台日韩各国的你会感觉一天";
        dto2.rid = R.drawable.iv_87434;
        dataList.add(dto2);
        MyViewDto dto3 = new MyViewDto();
        dto3.tvName = "test-base3";
        dto3.btnName = "埃索达天热一女程序的试图以然后返回人";
        dto3.rid = R.drawable.iv_83372;
        dataList.add(dto3);
        MySpinnerAdapter mySpinnerAdapter = new MySpinnerAdapter(this, dataList);
        dropDialog1.setAdapter(mySpinnerAdapter);
        dropDialog1.setPrompt("请选择");
        dropDialog1.setSelection(2);
    }
}