package com.titan.cssl.localcensor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragLocalCensorBinding;
import com.titan.model.ProjCensor;
import com.titan.model.ProjDetailMeasure;
import com.titan.util.MaterialDialogUtil;
import com.titan.util.MyFileUtil;
import com.titan.util.ResourcesManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IMultiImageCheckedListener;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by hanyw on 2017/11/6/006.
 * 现场审查
 */
@RuntimePermissions
public class ProjOpinionFragment extends Fragment implements ProjOpinion {

    private ProjOpinionViewModel viewModel;

    private FragLocalCensorBinding binding;
    private Context mContext;
    private static ProjDetailMeasure.subBean mSubBean;
    /**
     * 照片地址
     */
    private List<String> urlList = new ArrayList<>();
    /**
     * 相册选择照片
     */
    private List<MediaBean> mediaBeanList;
    /**
     * 照片地址
     */
    private String foutPath;
    /**
     * 照片列表适配器
     */
    private ProjOpinionImageAdapter adapter;

    public static ProjOpinionFragment getInstance(ProjDetailMeasure.subBean subBean) {
        mSubBean = subBean;
        return new ProjOpinionFragment();
    }

    /**
     * 设置viewmodel
     *
     * @param viewModel
     */
    public void setViewModel(ProjOpinionViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_local_censor, container, false);
        binding.setViewmodel(viewModel);
        initGalleryListener();
        return binding.getRoot();
    }

    /**
     * 初始化图片选择监听
     */
    private void initGalleryListener() {
        RxGalleryListener.getInstance().setMultiImageCheckedListener(new IMultiImageCheckedListener() {
            @Override
            public void selectedImg(Object t, boolean isChecked) {

            }

            @Override
            public void selectedImgMax(Object t, boolean isChecked, int maxSize) {
                Toast.makeText(mContext, "你最多只能选择" + maxSize + "张图片",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 添加图片
     */
    @Override
    public void addImage() {
        if (urlList.size() >= 3) {
            Toast.makeText(mContext, "你最多只能选择3张图片",Toast.LENGTH_SHORT).show();
            return;
        }
        final String[] array = new String[]{"相机", "相册"};
        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .items(array)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                ProjOpinionFragmentPermissionsDispatcher
                                        .takePhotoWithCheck(ProjOpinionFragment.this);
                                dialog.dismiss();
                                break;
                            case 1:
                                ProjOpinionFragmentPermissionsDispatcher
                                        .selectPhotoWithCheck(ProjOpinionFragment.this);
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
    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void takePhoto() {
        try {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (currentapiVersion < 24) {
                foutPath = ResourcesManager.getInstance(mContext).getDCIMPath() + "/"
                        + ResourcesManager.getPicName();
                MyFileUtil.makeRootDirectory(ResourcesManager.getInstance(mContext).getDCIMPath());
                Uri uri = Uri.fromFile(new File(foutPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            } else {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File image = File.createTempFile("img", ".jpg", path);
                Uri uri = FileProvider.getUriForFile(mContext,
                        mContext.getPackageName() + ".fileprovider", image);
                foutPath = image.getAbsolutePath();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            }
        } catch (Exception e) {
            Log.e("tag", "photoError:" + e);
        }
    }


    /**
     * 相册选择照片
     */
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void selectPhoto() {
        RxGalleryFinal.with(mContext)
                .image()
                .maxSize(3 - urlList.size())
                .selected(mediaBeanList)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        mediaBeanList = imageMultipleResultEvent.getResult();
                        for (MediaBean bean : mediaBeanList) {
                            urlList.add(bean.getOriginalPath());
                        }
                        refresh();
                    }
                })
                .openGallery();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                urlList.add(foutPath);
                refresh();
            }
        } catch (Exception e) {
            Log.e("tag", "result:" + e);
        }
    }

    /**
     * 更新显示图片
     */
    private void refresh() {
        if (adapter == null) {
            adapter = new ProjOpinionImageAdapter(mContext, urlList, viewModel);
            binding.censorGridview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        if (urlList != null && urlList.size() > 0) {
            binding.projPrompt.setVisibility(View.GONE);
        }
    }

    /**
     * 提交数据
     */
    @Override
    public void localCensorSubmit() {
        if (urlList.size()<=0){
            Toast.makeText(mContext,"图片不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.urlList.set(urlList);
        viewModel.subBean.set(mSubBean);
        viewModel.upData();
    }

    /**
     * 删除照片
     *
     * @param position 照片数组下标
     */
    @Override
    public void del(int position) {
        urlList.remove(position);
        viewModel.urlList.set(urlList);
        adapter.notifyDataSetChanged();
        if (urlList == null || urlList.size() <= 0) {
            binding.projPrompt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProgress() {
        MaterialDialogUtil.showLoadProgress(mContext,getString(R.string.loading),null).show();
    }

    @Override
    public void stopProgress() {
        MaterialDialogUtil.stopProgress();
    }

    @Override
    public void showToast(String info) {
        Toast.makeText(mContext,info,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProjOpinionFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationale(final PermissionRequest request){
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void permissionDenied(){
        Toast.makeText(mContext, "已拒绝权限，若想使用请开启权限",Toast.LENGTH_LONG).show();
    }
}
