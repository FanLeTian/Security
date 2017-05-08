package com.letian.security.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.letian.security.R;

/**
 * Created by acer on 2017/4/11.
 */

public class SettingActivity extends BackBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        setTitle("系统设置");
    }
}
