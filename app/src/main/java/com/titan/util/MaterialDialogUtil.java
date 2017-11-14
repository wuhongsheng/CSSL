package com.titan.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by hanyw on 2017/11/10/010.
 * 进度弹窗
 */

public class MaterialDialogUtil {

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

    public static MaterialDialog.Builder showSureDialog(Context context,String msg){
        return new MaterialDialog.Builder(context)
                .positiveText("确定")
                .negativeText("取消")
                .content(msg)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
    }

    public static AlertDialog.Builder showAlertDialog(Context context,String msg){
        return new AlertDialog.Builder(context)
                .setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }
}
