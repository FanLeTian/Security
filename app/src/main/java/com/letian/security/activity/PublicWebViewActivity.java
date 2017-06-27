package com.letian.security.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.letian.security.R;

/**
 * Created by acer on 2017/6/19.
 */

public class PublicWebViewActivity extends BackBaseActivity {

    private WebView mWebView;

    private String url;

    private String title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);
        mWebView = (WebView) findViewById(R.id.webview);

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        mWebView.loadUrl(url);
        setTitle(title);
    }
}
