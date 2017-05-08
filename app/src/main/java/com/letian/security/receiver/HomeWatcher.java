package com.letian.security.receiver;
  
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.letian.security.Config;

/** 
 * Home键监听封装 
 *  
 * @author way 
 *  
 */  
public class HomeWatcher {  
  
    static final String TAG = "HomeWatcher";  
    private Context mContext;  
    private IntentFilter mFilter;  
    private InnerRecevier mRecevier;

    public HomeWatcher(Context context) {  
        mContext = context;  
        mRecevier = new InnerRecevier();  
        mFilter = new IntentFilter();
        mFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);//home键
        mFilter.addAction(Intent.ACTION_SCREEN_ON);  //开屏
        mFilter.addAction(Intent.ACTION_SCREEN_OFF);//锁屏
        mFilter.addAction(Intent.ACTION_USER_PRESENT);//解锁
    }  
  

    /** 
     * 开始监听，注册广播 
     */  
    public void startWatch() {  
        if (mRecevier != null) {  
            mContext.registerReceiver(mRecevier, mFilter);  
        }  
    }  
  
    /** 
     * 停止监听，注销广播 
     */  
    public void stopWatch() {  
        if (mRecevier != null) {  
            mContext.unregisterReceiver(mRecevier);  
        }  
    }  
  
    /** 
     * 广播接收者 
     */  
    class InnerRecevier extends BroadcastReceiver {  
        final String SYSTEM_DIALOG_REASON_KEY = "reason";  
        final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";  
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";  
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";  
  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {  
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);  
                if (reason != null) {  
                        if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                            // 短按home键
                            Config.isShow = true;
                        } else if (reason
                                .equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                            // 长按home键
                            Config.isShow = true;
                        }
                }
                if(action.equals(Intent.ACTION_SCREEN_ON)){
                }else if(action.equals(Intent.ACTION_SCREEN_OFF)){
                    Config.isShow = true;
                }  else{// 解锁
                    //TODO
                    Config.isShow = true;
                }

            }  
        }  
    }  
}  