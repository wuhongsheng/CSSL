package com.titan.util;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by hanyw on 2017/11/9/009.
 * 文件帮助类
 */
public class MyFileUtil {

    /**
     * 打开图片
     *
     * @param Path 地址
     * @return intent
     */
    public static Intent getImageFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }


    /**
     * 打开pdf文件
     *
     * @param Path 地址
     * @return intent
     */
    public static Intent getPdfFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 打开word文件
     *
     * @param Path 地址
     * @return intent
     */
    public static Intent getWordFileIntent(String Path) {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }
}
