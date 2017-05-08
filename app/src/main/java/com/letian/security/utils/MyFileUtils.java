package com.letian.security.utils;

import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * Created by acer on 2017/5/3.
 */

public class MyFileUtils {


    public static void protectFileList(List<String> docList) {
        if (docList.isEmpty()) {
            return ;
        }
        for (String path : docList) {
            protectFile(path);
        }
    }

    public static void protectFile(String path) {
        File old = new File(path);
        File newF = new File(old.getParentFile().getAbsolutePath() + "/" + "." + old.getName() + "0");
        boolean isOk = old.renameTo(newF);
        Log.e("FanLetian", isOk + "");
    }


    public static void unProtectFile(String path) {
        File old = new File(path);
        File newF = new File(old.getParentFile().getAbsolutePath() + "/" + old.getName().substring(1, old.getName().length()));
        boolean isOk = old.renameTo(newF);
        Log.e("FanLetian", isOk + "");
    }

    public static void unProtectFile(String path, String filename) {
        File old = new File(path);
        File newF = new File(old.getParentFile().getAbsolutePath() + "/" + filename);
        boolean isOk = old.renameTo(newF);
        Log.e("FanLetian", isOk + "");
    }


    public static void unProtectFileList(List<String> docList) {
        if (docList.isEmpty()) {
            return ;
        }
        for (String path : docList) {
            unProtectFile(path);
        }
    }





}
