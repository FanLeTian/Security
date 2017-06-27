package com.letian.security.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.letian.security.R;
import com.letian.security.utils.SharedPreferencesUtil;
import com.letian.security.utils.ToastUtil;

/**
 * Created by acer on 2017/4/10.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private EditText phoneNumber;
    private EditText password;
    private EditText rePassword;

    private Button register;
    private View delete1;
    private View delete2;
    private View delete3;

    private boolean isHidden = true;

    private ImageView watch;
    private ImageView watch2;

    private String phone;
    private String pass1;
    private String pass2;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rigister_layout);
        setTitle("注册");
        initView();
    }

    private void initView() {
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.set_password);
        rePassword = (EditText) findViewById(R.id.re_set_password);
        register = (Button) findViewById(R.id.register);
        delete1 = findViewById(R.id.delete_1);
        delete2 = findViewById(R.id.delete_2);
        delete3 = findViewById(R.id.delete_3);
        watch = (ImageView) findViewById(R.id.watch_password);
        watch2 = (ImageView) findViewById(R.id.watch_password_2);
        register.setOnClickListener(this);
        delete1.setOnClickListener(this);
        delete2.setOnClickListener(this);
        delete3.setOnClickListener(this);
        watch.setOnClickListener(this);
        watch2.setOnClickListener(this);

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    delete1.setVisibility(View.VISIBLE);
                } else {
                    delete1.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    delete2.setVisibility(View.VISIBLE);
                } else {
                    delete2.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    delete3.setVisibility(View.VISIBLE);
                } else {
                    delete3.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_1:
                phoneNumber.setText("");
                if (password.isFocused() || rePassword.isFocused()) {
                    password.clearFocus();
                    rePassword.clearFocus();
                }
                phoneNumber.requestFocus();
                break;
            case R.id.delete_2:
                password.setText("");
                if (phoneNumber.isFocused() || rePassword.isFocused()) {
                    phoneNumber.clearFocus();
                    rePassword.clearFocus();
                }
                password.requestFocus();
                break;
            case R.id.delete_3:
                rePassword.setText("");
                if (password.isFocused() || phoneNumber.isFocused()) {
                    password.clearFocus();
                    phoneNumber.clearFocus();
                }
                rePassword.requestFocus();
                break;
            case R.id.watch_password:
                if (isHidden) {
                    //设置EditText文本为可见的
                    watch.setImageResource(R.drawable.kj);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    watch.setImageResource(R.drawable.bkj);
                }
                isHidden = !isHidden;
                password.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = password.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.watch_password_2:
                if (isHidden) {
                    //设置EditText文本为可见的
                    watch2.setImageResource(R.drawable.kj);
                    rePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    rePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    watch2.setImageResource(R.drawable.bkj);
                }
                isHidden = !isHidden;
                rePassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence1 = rePassword.getText();
                if (charSequence1 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence1;
                    Selection.setSelection(spanText, charSequence1.length());
                }
                break;
            case R.id.register:
                phone = phoneNumber.getText().toString();
                pass1 = password.getText().toString();
                pass2 = rePassword.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showToast(this, "请填写手机号");
                    return;
                }
                if (!isMobileNO(phone)) {
                    ToastUtil.showToast(this, "手机号码格式错误");
                    phoneNumber.setText("");
                    return;
                }
                if (TextUtils.isEmpty(pass1)) {
                    ToastUtil.showToast(this, "密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pass2)) {
                    ToastUtil.showToast(this, "请再次输入密码");
                    return;
                }
                if (!pass1.equals(pass2)) {
                    ToastUtil.showToast(this, "两次输入的密码不一样");
                    password.setText("");
                    rePassword.setText("");
                    rePassword.clearFocus();
                    password.requestFocus();
                    return;
                }
                SharedPreferencesUtil.setPrefBoolean(this, "ISREGISTER", true);
                SharedPreferencesUtil.setPrefString(this, "ACCOUNT", phone);
                SharedPreferencesUtil.setPrefString(this, "Login_PASSWORD", pass1);
                ShowDialog();
                break;
        }
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else return mobiles.matches(telRegex);
    }



    private void ShowDialog() {
        //创建退出对话框
        AlertDialog.Builder isExit = new AlertDialog.Builder(this);
        //设置对话框标题
        isExit.setTitle("注册成功");
        //设置对话框消息
        isExit.setMessage("恭喜您，您已经注册成功，去设置一个手势密码吧，方便以后更快的登录！");
        // 添加选择按钮并注册监听
        isExit.setPositiveButton("去设置", listener);
        isExit.setNegativeButton("取消", listener);
        //对话框显示
        isExit.show();
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    Intent intent = new Intent(RegisterActivity.this, ToSetPatternActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };


}
