package com.tequeno.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class FormActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private TextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        tv1 = findViewById(R.id.tv_form1);
        tv2 = findViewById(R.id.tv_form2);

        // 复选框
        CheckBox ck1 = findViewById(R.id.ck_hobby1);
//        ck1.setChecked(false); // 设置是否选中
//        ck1.isChecked(); // 判断是否选中
//        ck1.setButtonDrawable(null); // 设置复选框背景图片 如果设置null这不显示复选框
//        ck1.setText(); // 设置复选框文本内容
        ck1.setOnCheckedChangeListener(this::setTv1Text);

        CheckBox ck2 = findViewById(R.id.ck_hobby2);
        ck2.setOnCheckedChangeListener(this::setTv1Text);

        CheckBox ck3 = findViewById(R.id.ck_hobby3);
        ck3.setOnCheckedChangeListener(this::setTv1Text);

        CheckBox ck4 = findViewById(R.id.ck_hobby4);
        ck4.setOnCheckedChangeListener(this::setTv1Text);

        // 单选框
        RadioGroup rbGroup = findViewById(R.id.rb_group);
//        rbGroup.check(); // 根据id设置选中
//        rbGroup.getCheckedRadioButtonId(); // 获得选中项的id
//        rbGroup.clearCheck(); // 清空所选
        rbGroup.setOnCheckedChangeListener(this::setRbText);

        // 开关按钮
        SwitchCompat swDefault = findViewById(R.id.sw_default);
        swDefault.setOnCheckedChangeListener(this::setTv2Text);

        CheckBox swIos = findViewById(R.id.sw_ios);
        swIos.setOnCheckedChangeListener(this::setTv2Text);

        // 输入框
        EditText unameEditor = findViewById(R.id.form_uname);
        unameEditor.setOnFocusChangeListener((view, hasFocus) -> Log.d(TAG, "unameEditor 获取焦点: " + hasFocus));
        unameEditor.addTextChangedListener(new MyTextWatcher(unameEditor, 11));

        EditText pwdEditor = findViewById(R.id.form_password);
        pwdEditor.setOnFocusChangeListener((view, hasFocus) -> Log.d(TAG, "pwdEditor 获取焦点: " + hasFocus));
        pwdEditor.addTextChangedListener(new MyTextWatcher(pwdEditor, 6));

        Button btnLogin = findViewById(R.id.btn_form_login);
        btnLogin.setOnClickListener(view -> {
            String uname = unameEditor.getText().toString();
            if (TextUtils.isEmpty(uname)) {
                Log.d(TAG, "请输入用户名");
                MainUtil.toast(this, "请输入用户名");
                return;
            }
            String pwd = pwdEditor.getText().toString();
            if (TextUtils.isEmpty(pwd)) {
                Log.d(TAG, "请输入密码");
                MainUtil.toast(this, "请输入密码");
                return;
            }
            Log.d(TAG, String.format("输入内容 %s", MainUtil.toJsonString(uname, pwd)));
            MainUtil.toast(this, String.format("输入内容 %s", MainUtil.toJsonString(uname, pwd)));
        });

        // 对话框
        Button btnCs = findViewById(R.id.btn_cs1);
        btnCs.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("测试对话框");
            builder.setMessage("哈哈哈哈哈哈哈哈哈");
            builder.setPositiveButton("确定", (dialog, which) -> Log.d(TAG, "setPositiveButton: " + which));
            builder.setNegativeButton("取消", (dialog, which) -> Log.d(TAG, "setNegativeButton: " + which));

            builder.create().show();
        });
    }

    private void setTv1Text(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "复选框: " + buttonView.getText());
        String text = String.format("%s了%s", isChecked ? "勾选" : "取消勾选", buttonView.getText());
        tv1.setText(text);
    }

    private void setRbText(RadioGroup group, int checkedId) {
        RadioButton view = findViewById(checkedId);
        CharSequence text = view.getText();
        tv1.setText(text);
        Log.d(TAG, "单选框: " + text);
    }

    private void setTv2Text(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "开关: " + buttonView.getText());
        String text = String.format("%s switch状态:%s", buttonView.getText(), isChecked ? "开" : "关");
        tv2.setText(text);
    }

    private class MyTextWatcher implements TextWatcher {
        private EditText view;
        private int maxLen;

        public MyTextWatcher(EditText view, int maxLen) {
            this.view = view;
            this.maxLen = maxLen;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d(TAG, "beforeTextChanged: " + s);
            Log.d(TAG, "beforeTextChanged: " + start);
            Log.d(TAG, "beforeTextChanged: " + count);
            Log.d(TAG, "beforeTextChanged: " + after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "onTextChanged: " + s);
            Log.d(TAG, "onTextChanged: " + start);
            Log.d(TAG, "onTextChanged: " + before);
            Log.d(TAG, "onTextChanged: " + count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(TAG, "afterTextChanged: " + s);
            if (s.toString().length() == maxLen) {
                MainUtil.hideInputMethod(FormActivity.this, view);
            }
        }
    }
}