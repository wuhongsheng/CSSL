package com.titan.cssl.localcensor;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragLocalCensorBinding;
import com.titan.cssl.util.ToastUtil;
import com.titan.util.ResourcesManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hanyw on 2017/11/6/006.
 * 现场审查
 */

public class ProjLocalCensorFragment extends Fragment implements ProjLocalCensor {

    private ProjLocalCensorViewModel viewModel;

    private static ProjLocalCensorFragment fragment;
    private FragLocalCensorBinding binding;
    private Context mContext;
    /**
     * 照片地址数组
     */
    private List<String> urlList = new ArrayList<>();
    /**
     * 照片地址
     */
    private String foutPath;
    /**
     * 照片列表适配器
     */
    private ProjCensorImageAdapter adapter;

    public static ProjLocalCensorFragment getInstance() {
        if (fragment == null) {
            fragment = new ProjLocalCensorFragment();
        }
        return fragment;
    }

    /**
     * 设置viewmodel
     * @param viewModel
     */
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

    /**
     * 添加图片
     */
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

    /**
     * 拍照
     */
    private void takePhoto(){
        try {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (currentapiVersion<24){
                foutPath = ResourcesManager.getInstance(mContext).getDCIMPath() +"/"+ getPicName();
                makeRootDirectory(ResourcesManager.getInstance(mContext).getDCIMPath());
                Uri uri = Uri.fromFile(new File(foutPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            }else {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File image = File.createTempFile("img",".jpg",path);
                Uri uri = FileProvider.getUriForFile(mContext,
                        mContext.getPackageName()+".fileprovider",image);
                foutPath=image.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent,1);
            }
        } catch (Exception e) {
            Log.e("tag","photoError:"+e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode==1&&resultCode== Activity.RESULT_OK){
                urlList.add(foutPath);
                adapter = new ProjCensorImageAdapter(mContext,urlList,viewModel);
                binding.censorGridview.setAdapter(adapter);
                if (urlList!=null&&urlList.size()>0){
                    binding.projPrompt.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            Log.e("tag","result:"+e);
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

    /**
     * 提交数据
     */
    @Override
    public void localCensorSubmit() {
        ToastUtil.setToast(mContext,"提交");
    }

    /**
     * 删除照片
     * @param position 照片数组下标
     */
    @Override
    public void del(int position) {
        urlList.remove(position);
        adapter.notifyDataSetChanged();
        if (urlList==null||urlList.size()<=0) {
            binding.projPrompt.setVisibility(View.VISIBLE);
        }
    }
}
