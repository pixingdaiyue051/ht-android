package com.tequeno.app.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.tequeno.app.MainUtil;
import com.tequeno.app.R;

/**
 * 继承 AppCompatActivity 可以自动解决一些兼容问题
 */
public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "LoginActivity";
    private RadioGroup radioGroup;
    private View otpView;
    private View passwordView;
    private EditText formPhone;
    private EditText formOtp;
    private EditText formPassword;
    private CheckBox passwordCheck;

    /**
     * 组件加载时首先被调用的方法
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        radioGroup = findViewById(R.id.rg_choose);
        otpView = findViewById(R.id.ll_otp);
        passwordView = findViewById(R.id.ll_password);

        formPhone = findViewById(R.id.form_phone);
        formOtp = findViewById(R.id.form_otp);
        formPassword = findViewById(R.id.form_password); // TODO 查询是否有之前保存的密码
        passwordCheck = findViewById(R.id.form_remember_password);
        Button otpButton = findViewById(R.id.form_fetch_otp);
        Button passwordButton = findViewById(R.id.form_forget_password);
        Button btnLogin = findViewById(R.id.form_login);

        radioGroup.setOnCheckedChangeListener(this::chooseLoginType);
        otpButton.setOnClickListener(this::requireOtp);
        passwordButton.setOnClickListener(this::requireForgetPassword);
        btnLogin.setOnClickListener(this::login);
    }

    /**
     * 判断登录方式
     *
     * @return
     */
    private boolean isOtpLogin() {
        return R.id.rb_otp == radioGroup.getCheckedRadioButtonId();
    }

    /**
     * 校验手机号是否符合规范
     * 应采用正则匹配
     *
     * @return
     */
    private boolean isCorrectPhone() {
        String text = formPhone.getText().toString();
        boolean checked = !TextUtils.isEmpty(text);
        if (!checked) {
            MainUtil.toast("请输入手机号");
        }
        return checked;
    }

    /**
     * 校验验证码是否符合规范
     * 应采用正则匹配
     *
     * @return
     */
    private boolean isCorrectOtp() {
        String text = formOtp.getText().toString();
        boolean checked = !TextUtils.isEmpty(text) && text.length() == 6;
        if (!checked) {
            MainUtil.toast("请输入6位验证码");
        }
        return checked;
    }

    /**
     * 校验密码是否符合规范
     * 应采用正则匹配
     *
     * @return
     */
    private boolean isCorrectPassword() {
        String text = formPassword.getText().toString();
        boolean checked = !TextUtils.isEmpty(text);
        if (!checked) {
            MainUtil.toast("请输入密码");
        }
        return checked;
    }

    /**
     * 判断是否需要记住密码
     *
     * @return
     */
    private boolean needRememberPassword() {
        return passwordCheck.isChecked();
    }

    /**
     * 不同的登陆方式 切换不同的显示控件
     *
     * @param group
     * @param checkId
     */
    private void chooseLoginType(RadioGroup group, int checkId) {
        if (this.isOtpLogin()) {
            otpView.setVisibility(View.VISIBLE);
            passwordView.setVisibility(View.GONE);
            passwordCheck.setVisibility(View.GONE);
        } else {
            otpView.setVisibility(View.GONE);
            passwordView.setVisibility(View.VISIBLE);
            passwordCheck.setVisibility(View.VISIBLE);
        }
    }

    /**
     * TODO
     * 请求后台 获取验证码
     *
     * @param view
     */
    private void requireOtp(View view) {
        if (!this.isCorrectPhone()) {
            return;
        }
    }

    /**
     * TODO
     * 忘记密码
     *
     * @param view
     */
    private void requireForgetPassword(View view) {
    }

    /**
     * 登录逻辑
     *
     * @param view
     */
    private void login(View view) {
        if (!isCorrectPhone()) {
            return;
        }
        String phone = formPhone.getText().toString();
        LoginDto loginDto = new LoginDto();
        if (this.isOtpLogin()) {
            if (!this.isCorrectOtp()) {
                return;
            }
            loginDto.phone = phone;
            loginDto.otp = formOtp.getText().toString();
        } else {
            if (!this.isCorrectPassword()) {
                return;
            }
            loginDto.username = phone;
            loginDto.password = formPassword.getText().toString();
        }
        // TODO 请求后台 验证登录信息 获取登录成功token 判断是否需要保存密码

//        String url = "http://qinshitong.work:8081/middle/user/login";
//        String url1 = "http://qinshitong.work:8081/middle/user/list";
//        HttpClientWrapper util = HttpClientWrapper.getInstance();
//        util.postAsync(url, MainUtil.toJsonString(loginDto), msg -> {
//            Type type = new TypeToken<ResponseWrapper<LoginResDto>>() {
//            }.getType();
//            ResponseWrapper<LoginResDto> wrapper = new Gson().fromJson(msg, type);
//            util.header("sign", wrapper.data.sign);
//            util.post(url1, "{}", msg1 -> {
//
//            });
//        });

//        Intent intent = new Intent(this, LoginOkActivity.class);
//        // 登录之后 清空栈再跳转 之后就不会再跳转会登录页面了
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }
}