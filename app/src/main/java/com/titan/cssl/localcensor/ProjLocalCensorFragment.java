package com.titan.cssl.localcensor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragLocalCensorBinding;
import com.titan.cssl.util.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hanyw on 2017/11/6/006.
 */

public class ProjLocalCensorFragment extends Fragment implements ProjLocalCensor {

    private ProjLocalCensorViewModel viewModel;

    private static ProjLocalCensorFragment fragment;
    private Context mContext;
    private List<String> urlList = new ArrayList<>();
    private String foutPath;
    private FragLocalCensorBinding binding;

    public static ProjLocalCensorFragment getInstance() {
        if (fragment == null) {
            fragment = new ProjLocalCensorFragment();
        }
        return fragment;
    }

    public void setViewModel(ProjLocalCensorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_local_censor, container, false);
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void addImage() {
        final String[] array = new String[]{"相机", "相册"};
        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .items(array)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                ToastUtil.setToast(mContext, array[position]);
                                takePhoto();
                                dialog.dismiss();
                                break;
                            case 1:
                                ToastUtil.setToast(mContext, array[position]);
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .build();
        dialog.show();
    }

    private void takePhoto(){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            foutPath = "/storage/emulated/0/DCIM/" + getPicName();
            makeRootDirectory("/storage/emulated/0/DCIM/");
            Uri uri = Uri.fromFile(new File(foutPath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            Log.e("tag","photoError:"+e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode== Activity.RESULT_OK){
            urlList.add(foutPath);
            ProjCensorImageAdapter adapter = new ProjCensorImageAdapter(mContext,urlList);
            Log.e("tag","url:"+urlList);
            binding.censorGridview.setAdapter(adapter);
        }
    }

    private String getPicName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'img'_yyyyMMdd_HHmmss", Locale.CHINA);
        return sdf.format(date) + ".jpg";
    }

    private void makeRootDirectory(String filePath) {
        File file;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    @Override
    public void localCensorSubmit() {
        ToastUtil.setToast(mContext,"提交");
    }
}
