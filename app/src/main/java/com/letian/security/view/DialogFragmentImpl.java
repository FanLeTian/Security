package com.letian.security.view;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by 黄振伟 on 2017/3/30.
 */

public class DialogFragmentImpl extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
        }
    }
}

