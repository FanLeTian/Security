package com.letian.security.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.letian.security.utils.SharedPreferencesUtil;

import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

/**
 * Created by acer on 2017/2/24.
 */

public class ToConfirmPatternActivity extends ConfirmPatternActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        String patternSha1 = SharedPreferencesUtil.getPrefString(this, "PASSWORD", "");
        if (TextUtils.equals(PatternUtils.patternToSha1String(pattern), patternSha1)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return TextUtils.equals(PatternUtils.patternToSha1String(pattern), patternSha1);
    }

    @Override
    protected boolean isStealthModeEnabled() {
        return super.isStealthModeEnabled();
    }

    @Override
    protected void onForgotPassword() {
        super.onForgotPassword();
    }
}
