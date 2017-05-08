package com.letian.security.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.letian.security.R;
import com.letian.security.utils.SharedPreferencesUtil;
import com.letian.security.utils.ToastUtil;
import com.letian.security.view.LoginDialogFragment;

/**
 * Created by acer on 2017/5/4.
 */

public class AccountActivity extends BackBaseActivity implements View.OnClickListener{

    private TextView phone;
    private View findPass;
    private Switch mSwitch;
    private View reSetGes;
    private View reSetGesturePass;
    private Button exit;

    private LoginDialogFragment loginDialogFragment;

    private String status = "0";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_layout);
        setTitle("账户管理");
        initView();
        loginDialogFragment = new LoginDialogFragment.Builder().
                setPhoneNumber(SharedPreferencesUtil.getPrefString(this, "ACCOUNT", ""))
                .setCanceledOnTouchOutside(false)
                .setAutoDismiss(false)
                .setCanCancel(false)
                .setNagtiveButton(new LoginDialogFragment.OnClickListener() {
                    @Override
                    public void onClick(LoginDialogFragment loginDialogFragment) {
                        loginDialogFragment.clearPassWord();
                        loginDialogFragment.dismiss();
                        reSetSwingButtonStatus();
                    }
                })
                .setPositiveButton(new LoginDialogFragment.OnClickListener() {
                    @Override
                    public void onClick(LoginDialogFragment loginDialogFragment) {
                        if (loginDialogFragment.getPassWord().length() > 0) {
                            if (loginDialogFragment.getPassWord().equals(SharedPreferencesUtil.getPrefString(AccountActivity.this, "Login_PASSWORD", ""))) {
                                if (status.equals("0")) {
                                    SharedPreferencesUtil.setPrefBoolean(AccountActivity.this, "SET_PATTERN", false);
                                    reSetGesturePass.setVisibility(View.GONE);
                                    mSwitch.setChecked(false);
                                } else if (status.equals("1")) {
                                    Intent intent2 = new Intent(AccountActivity.this, ToSetPatternActivity.class);
                                    startActivity(intent2);
                                }
                                loginDialogFragment.dismiss();
                            } else {
                                ToastUtil.showToast(AccountActivity.this, "密码错误");
                            }
                            loginDialogFragment.clearPassWord();
                        } else {
                            ToastUtil.showToast(AccountActivity.this, "密码不能为空");
                        }
                    }
                }).create();
    }

    private void reSetSwingButtonStatus() {
        if (SharedPreferencesUtil.getPrefBoolean(this, "SET_PATTERN", false)) {
            mSwitch.setChecked(true);
            reSetGesturePass.setVisibility(View.VISIBLE);
        } else {
            mSwitch.setChecked(false);
            reSetGesturePass.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reSetSwingButtonStatus();
    }

    private void initView() {
        exit = (Button) findViewById(R.id.exit);
        phone = (TextView) findViewById(R.id.account_name);
        findPass = findViewById(R.id.tv_find_pass);
        mSwitch = (Switch) findViewById(R.id.switvh_button);
        reSetGes = findViewById(R.id.re_set_gesture);
        reSetGesturePass = findViewById(R.id.reset_gesture_pasword);
        phone.setText(SharedPreferencesUtil.getPrefString(this, "ACCOUNT", ""));
        findPass.setOnClickListener(this);
        reSetGes.setOnClickListener(this);
        exit.setOnClickListener(this);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    status = "1";
                    loginDialogFragment.show(getSupportFragmentManager(), "dialog");
                } else {
                    status = "0";
                    loginDialogFragment.show(getSupportFragmentManager(), "dialog");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_find_pass:
                Intent intent = new Intent(this, FindPassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.re_set_gesture:
                status = "1";
                loginDialogFragment.show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.exit:
                break;
        }

    }


}
