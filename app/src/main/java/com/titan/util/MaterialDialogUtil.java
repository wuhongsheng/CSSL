package com.titan.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

/**
 * Created by hanyw on 2017/11/10/010.
 * 进度弹窗
 */

public class MaterialDialogUtil {

    /**
     * 加载数据进度弹窗
     *
     * @param context
     * @param msg
     * @return
     */
    public static MaterialDialog.Builder showLoadProgress(Context context, String msg) {
        return new MaterialDialog.Builder(context)
                .content(msg)
                .progress(true, 0)
                .cancelable(true)
                .canceledOnTouchOutside(false);
    }

    /**
     * 包含取消动作的loadProgress
     * @param context
     * @param msg
     * @param listener
     * @return
     */
    public static MaterialDialog.Builder showLoadProgress(Context context, String msg,
                                                          DialogInterface.OnCancelListener listener) {
        return new MaterialDialog.Builder(context)
                .content(msg)
                .progress(true, 0)
                .cancelable(true)
                .cancelListener(listener)
                .canceledOnTouchOutside(false);
    }

    public static MaterialDialog.Builder showSureDialog(Context context, String msg) {
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

    public static AlertDialog.Builder showAlertDialog(Context context, String msg) {
        return new AlertDialog.Builder(context)
                .setMessage(msg)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }

    public static MaterialDialog showCustomViewDialog(Context context, String title, View view) {
        return new MaterialDialog.Builder(context)
                .negativeText("取消")
                .title(title)
                .customView(view, true)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();
    }

    public static MaterialDialog showItemDialog(Context context, String title, List<String> list) {
        return new MaterialDialog.Builder(context)
                .negativeText("取消")
                .title(title)
                .items(list)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();
    }
}
