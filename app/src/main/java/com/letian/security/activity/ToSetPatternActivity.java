package com.letian.security.activity;

import android.content.Intent;

import com.letian.security.utils.SharedPreferencesUtil;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

/**
 * Created by acer on 2017/2/24.
 */

public class ToSetPatternActivity extends SetPatternActivity {

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        super.onSetPattern(pattern);
        String patternSha1 = PatternUtils.patternToSha1String(pattern);
        SharedPreferencesUtil.setPrefBoolean(this, "SET_PATTERN", true);
        SharedPreferencesUtil.setPrefString(this, "PASSWORD", patternSha1);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
