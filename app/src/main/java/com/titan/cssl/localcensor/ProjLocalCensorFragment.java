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
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            foutPath = "file://"+ResourcesManager.getInstance(mContext).getDCIMPath() +"/"+ getPicName();
            if (currentapiVersion<24){
                makeRootDirectory(ResourcesManager.getInstance(mContext).getDCIMPath());
                Uri uri = Uri.fromFile(new File(foutPath));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            }else {
                ContentValues contentValues = new ContentValues(1);
//                File file = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File image = File.createTempFile("123",".jpg",path);
                Log.e("tag","photopath:"+path);
                Uri uri = FileProvider.getUriForFile(mContext,
                        "com.titan.cssl.fileprovider",image);
                foutPath=image.getAbsolutePath();
//                contentValues.put(MediaStore.EXTRA_OUTPUT,foutPath);
//                Uri uri = mContext.getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
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
            Log.e("tag","url:"+urlList+","+requestCode+resultCode);
            if (requestCode==1&&resultCode== Activity.RESULT_OK){
                scanDir(mContext,foutPath);
                urlList.add(foutPath);
                Log.e("tag","url:"+urlList);
                ProjCensorImageAdapter adapter = new ProjCensorImageAdapter(mContext,urlList);
                binding.censorGridview.setAdapter(adapter);
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

    /*扫描文件*/
    public static void scanDir(final Context context, String uri) {
        //判断sdk版本是否大于4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MediaScannerConnection.scanFile(context, new String[]{uri}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } else {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
                    + Environment.getExternalStorageDirectory())));
        }
    }

    @Override
    public void localCensorSubmit() {
        ToastUtil.setToast(mContext,"提交");
    }
}
