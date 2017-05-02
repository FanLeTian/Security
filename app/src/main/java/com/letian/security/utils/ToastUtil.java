package com.letian.security.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by acer on 2016/11/17.
 */

public class ToastUtil {
    private static Toast toast;
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.setGravity(Gravity.CENTER, 0, 60);
        toast.show();
    }
}
