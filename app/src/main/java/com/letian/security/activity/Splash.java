package com.letian.security.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.letian.security.R;
import com.letian.security.utils.SharedPreferencesUtil;

/**
 * Created by acer on 2016/8/19.
 */
public class Splash extends BaseActivity {

    private static final String TAG = Splash.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.splash);
        Handler handler = new Handler();
        if (SharedPreferencesUtil.getPrefBoolean(this, "ISREGISTER", false)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (SharedPreferencesUtil.getPrefBoolean(Splash.this, "SET_PATTERN", false)) {
                        Intent intent = new Intent(Splash.this, ToConfirmPatternActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(Splash.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 1500);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Splash.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1500);
        }

    }
}
