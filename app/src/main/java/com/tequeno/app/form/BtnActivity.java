package com.tequeno.app.form;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.app.R;

import java.time.LocalDateTime;

public class BtnActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "BtnActivity";
    private TextView tv;
    private TextView tvTest;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn);

        tv = findViewById(R.id.tv_po_1);
        tvTest = findViewById(R.id.tv_po_test);

        Button btn1 = findViewById(R.id.btn_po_1);
//        btn1.setOnClickListener(view -> setBtn1Text(btn1, "doClick: ", "%s点击了%s"));
        btn1.setOnClickListener(this);

        // 长按事件 长按超过500ms触发
        // return true if the callback consumed the long click, false otherwise.
        // 是否消耗点击事件 true不再向外冒泡
        btn1.setOnLongClickListener(view -> {
            setBtn1Text(btn1, "doLongClick: ", "%s长按%s");
            return true;
        });


        Button btnOk = findViewById(R.id.btn_po_ok);
        Button btnDeny = findViewById(R.id.btn_po_deny);
        btnTest = findViewById(R.id.btn_po_test);

        btnOk.setOnClickListener(view -> setBtnEnabled(true, "可以点击"));
        btnDeny.setOnClickListener(view -> setBtnEnabled(false, "不可点击"));
        btnTest.setOnClickListener(view -> setBtnText());

//        btnOk.setOnClickListener(this);
//        btnDeny.setOnClickListener(this);
//        btnTest.setOnClickListener(this);
    }

    private void setBtn1Text(Button btn1, String s, String s2) {
        CharSequence btn1Text = btn1.getText();
        Log.i(TAG, s + btn1Text);
        String msg = String.format(s2, LocalDateTime.now().toString(), btn1Text);
        tv.setText(msg);
    }

    private void setBtnEnabled(boolean enabled, String text) {
        btnTest.setEnabled(enabled);
        btnTest.setText(text);
    }

    private void setBtnText() {
        CharSequence btnText = btnTest.getText();
        String msg = String.format("%s点击了%s", LocalDateTime.now().toString(), btnText);
        tvTest.setText(msg);
    }


    /**
     * 需要在资源文件配置 onclick方法 已废弃 不允许使用这种方式
     *
     * @param view
     */
    public void doClick(View view) {
        setBtn1Text((Button) view, "deprecated doClick: ", "%s点击了%s");
    }

    /**
     * 当前activity实现View.OnClickListener 做全局的按钮事件处理
     * 获取按钮 绑定按钮事件setOnClickListener(this)
     * 因为无法避免嵌套if或者switch case 不推荐使用
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        int id = btn.getId();

//        switch (id) {
//            case R.id.btn_po_1:
//                setBtn1Text(btn, "doClick: ", "%s点击了%s");
//                break;
//            case R.id.btn_po_ok:
//                setBtnEnabled(btn, true, "true");
//                break;
//            case R.id.btn_po_deny:
//                setBtnEnabled(btn, false, "不可点击");
//                break;
//            case R.id.btn_po_test:
//                setBtnText(btn);
//                break;
//            default:
//                break;
//        }

        if (R.id.btn_po_1 == id) {
            setBtn1Text(btn, "doClick: ", "%s点击了%s");
        } else if (R.id.btn_po_ok == id) {
            setBtnEnabled( true, "可以点击");
        } else if (R.id.btn_po_deny == id) {
            setBtnEnabled( false, "不可点击");
        } else if (R.id.btn_po_test == id) {
            setBtnText();
        } else {
            Log.e(TAG, "onClick: 没有对应按钮");
        }
    }
}