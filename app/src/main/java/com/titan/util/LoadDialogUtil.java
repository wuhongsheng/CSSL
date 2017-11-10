package com.titan.util;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by hanyw on 2017/11/10/010.
 * 进度弹窗
 */

public class LoadDialogUtil {

    private static MaterialDialog dialog;
    /**
     * 加载数据进度弹窗
     * @param context
     * @param msg
     * @return
     */
    public static MaterialDialog showLoadProgress(Context context, String msg){
        dialog =  new MaterialDialog.Builder(context)
                .content(msg)
                .progress(true, 0)
                .cancelable(false)
                .build();
        return dialog;
    }

    /**
     * 关闭进度弹窗
     */
    public static void stopProgress(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
