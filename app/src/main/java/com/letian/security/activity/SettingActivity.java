package com.letian.security.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.letian.security.R;

/**
 * Created by acer on 2017/4/11.
 */

public class SettingActivity extends BackBaseActivity {

    private View toWeChat;

    private TextView about;
    private TextView resolve;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        setTitle("系统设置");
        toWeChat = findViewById(R.id.to_wechat);
        about = (TextView) findViewById(R.id.tv_about);
        resolve = (TextView) findViewById(R.id.tv_help);
        toWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, PublicWebViewActivity.class);
                intent.putExtra("url", "file:///android_asset/about.html");
                intent.putExtra("title", "关于我们");
                startActivity(intent);
            }
        });
        resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, PublicWebViewActivity.class);
                intent.putExtra("url", "file:///android_asset/Announce.html");
                intent.putExtra("title", "常见问题");
                startActivity(intent);
            }
        });
    }
}
