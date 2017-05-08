package com.letian.security.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.letian.security.R;
import com.letian.security.utils.SharedPreferencesUtil;
import com.letian.security.utils.ToastUtil;

/**
 * Created by acer on 2017/5/4.
 */

public class FindPassWordActivity extends BackBaseActivity {

    private EditText oldEdit;
    private EditText newEdit;
    private Button enter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpass_layout);
        setTitle("修改密码");
        initView();
    }

    private void initView() {
        oldEdit = (EditText) findViewById(R.id.et_odl_password);
        newEdit = (EditText) findViewById(R.id.et_new_password);
        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = oldEdit.getText().toString();
                String newL = newEdit.getText().toString();
                if (TextUtils.isEmpty(old)) {
                    ToastUtil.showToast(FindPassWordActivity.this, "原密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(newL)) {
                    ToastUtil.showToast(FindPassWordActivity.this, "新密码不能为空");
                    return;
                }
                if (old.equals(SharedPreferencesUtil.getPrefString(FindPassWordActivity.this, "Login_PASSWORD", ""))) {
                    SharedPreferencesUtil.setPrefString(FindPassWordActivity.this, "Login_PASSWORD", newL);
                    ToastUtil.showToast(FindPassWordActivity.this, "修改成功");
                } else {
                    ToastUtil.showToast(FindPassWordActivity.this, "原密码错误");
                    oldEdit.setText("");
                    newEdit.setText("");
                }
            }
        });

    }
}
