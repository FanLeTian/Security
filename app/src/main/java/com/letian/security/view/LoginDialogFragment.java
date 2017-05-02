package com.letian.security.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.letian.security.R;


/**
 * Created by acer on 2017/3/13.
 */

public class LoginDialogFragment extends DialogFragmentImpl{

    private TextView mobilePhone;
    private EditText password;
    private Button nagtiveBtn;
    private Button postiveBtn;

    private boolean mBooTouchOutSide = false;
    private boolean mCancelable = true;
    private boolean autoDismiss = true;
    private OnClickListener onPositiveClickListenr;
    private OnClickListener onNagativeClickListenr;
    private String phoneNumber;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, getTheme());
        View view = inflater.inflate(R.layout.login_dialog, container);
        mobilePhone = (TextView) view.findViewById(R.id.moblie_phone);
        password = (EditText) view.findViewById(R.id.password_edt);
        nagtiveBtn = (Button) view.findViewById(R.id.nagative_btn);
        postiveBtn = (Button) view.findViewById(R.id.positive_btn);
        nagtiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNagativeClickListenr.onClick(LoginDialogFragment.this);
            }
        });
        postiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPositiveClickListenr.onClick(LoginDialogFragment.this);
            }
        });
        mobilePhone.setText(phoneNumber);
        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Dialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(mBooTouchOutSide);
        dialog.setCancelable(mCancelable);
        return dialog;
    }

    private void setPositiveButton(OnClickListener onClickListener) {
        this.onPositiveClickListenr = onClickListener;
    }

    private void setNagtiveButton(OnClickListener onClickListener) {
        this.onNagativeClickListenr = onClickListener;
    }

    public String getPassWord() {
        return password.getText().toString();
    }

    public void clearPassWord() {
        password.setText("");
    }

    private void setMobilePhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setmBooTouchOutSide(boolean mBooTouchOutSide) {
        this.mBooTouchOutSide = mBooTouchOutSide;
    }

    public void setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }

    public interface OnClickListener {
        void onClick(LoginDialogFragment loginDialogFragment);
    }


    public static class Builder {
        LoginDialogFragment mDialogFragment;

        public Builder() {
            mDialogFragment = new LoginDialogFragment();
        }


        public Builder setPositiveButton(OnClickListener onClickListener) {
            mDialogFragment.setPositiveButton(onClickListener == null ? new DefaultOnClickListener() : onClickListener);
            return this;
        }

        public Builder setNagtiveButton(OnClickListener onClickListener) {
            mDialogFragment.setNagtiveButton(onClickListener == null ? new DefaultOnClickListener() : onClickListener);
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            mDialogFragment.setMobilePhone(phoneNumber);
            return this;
        }


        public Builder setCanceledOnTouchOutside(boolean enable) {
            mDialogFragment.setmBooTouchOutSide(enable);
            return this;
        }

        public Builder setCanCancel(boolean enable) {
            mDialogFragment.setCancelable(enable);
            return this;
        }

        public Builder setAutoDismiss(boolean autoDismiss) {
            mDialogFragment.setAutoDismiss(autoDismiss);
            return this;
        }

        public LoginDialogFragment create() {
            return mDialogFragment;
        }

        /**
         * 缺省回调
         */
        private class DefaultOnClickListener implements OnClickListener {
            @Override
            public void onClick(LoginDialogFragment loginDialogFragment) {
                if (!"update".equals(loginDialogFragment.getTag())) {
                    loginDialogFragment.dismiss();
                }
            }
        }
    }
}
