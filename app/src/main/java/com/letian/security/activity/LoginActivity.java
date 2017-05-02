package com.letian.security.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.letian.security.R;
import com.letian.security.utils.SharedPreferencesUtil;
import com.letian.security.utils.ToastUtil;

/**
 * Created by acer on 2017/4/10.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText passWord;
    private TextView phone;
    private View delete;
    private ImageView watch;
    private Button login;
    private boolean isHidden = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setTitle("欢迎回来");
        initView();
    }

    private void initView() {
        passWord = (EditText) findViewById(R.id.password);
        phone = (TextView) findViewById(R.id.phone);
        delete = findViewById(R.id.delete);
        watch = (ImageView) findViewById(R.id.watch_password);
        login = (Button) findViewById(R.id.login);
        delete.setOnClickListener(this);
        watch.setOnClickListener(this);
        login.setOnClickListener(this);
        phone.setText(SharedPreferencesUtil.getPrefString(this, "ACCOUNT", ""));
        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    delete.setVisibility(View.VISIBLE);
                } else {
                    delete.setVisibility(View.GONE);
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
            case R.id.delete:
                passWord.setText("");
                passWord.requestFocus();
                break;
            case R.id.watch_password:
                if (isHidden) {
                    //设置EditText文本为可见的
                    watch.setImageResource(R.drawable.kj);
                    passWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    watch.setImageResource(R.drawable.bkj);
                }
                isHidden = !isHidden;
                passWord.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = passWord.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.login:
                if (TextUtils.isEmpty(passWord.getText().toString())) {
                    ToastUtil.showToast(this, "密码不能为空");
                    return;
                }
                if (passWord.getText().toString().equals(SharedPreferencesUtil.getPrefString(this, "PASSWORD", ""))) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showToast(this, "密码错误");
                }
                break;
        }
    }
}
