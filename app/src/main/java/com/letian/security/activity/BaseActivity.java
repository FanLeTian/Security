package com.letian.security.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.letian.security.R;
import com.letian.security.activity.base.ActivityCollector;
import com.letian.security.utils.StatusBarCompat;


/**
 * Created by acer on 2016/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected TextView customTitle;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarCompat.compat(this);
        ActivityCollector.addActivity(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        customTitle = (TextView) findViewById(R.id.toolbar_title);
        setupToolbar();
    }

    protected void setupToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected void setTitle(String title) {
        if (customTitle != null) {
            customTitle.setText(title);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

