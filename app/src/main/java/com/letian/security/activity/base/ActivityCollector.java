package com.letian.security.activity.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/8/17.
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 除了传来的Activity其他的全部删除
     * 可以传多个Activity
     *
     * @param clazz
     */
    public static void removeAll(Class<?>... clazz) {
        boolean isExist = false;
        for (Activity act : activities) {
            for (Class c : clazz) {
                if (act.getClass().isAssignableFrom(c)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                if (!act.isFinishing()) {
                    act.finish();
                }
            } else {
                isExist = false;
            }
        }
    }

    /**
     * 从Activity集合查询, 传入的Activity是否存在
     * 如果存在就返回该Activity,不存在就返回null
     *
     * @param activity 需要查询的Activity, 比如MainActivity.class
     * @return
     */
    public static Activity getActivity(Class<?> activity) {
        for (int i = 0; i < activities.size(); i++) {
            // 判断是否是自身或者子类
            if (activities.get(i).getClass().isAssignableFrom(activity)) {
                return activities.get(i);
            }
        }
        return null;
    }
}
