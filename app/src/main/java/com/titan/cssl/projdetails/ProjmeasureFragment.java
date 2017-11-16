package com.titan.cssl.projdetails;

import android.Manifest;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.titan.cssl.R;
import com.titan.cssl.databinding.FragProjMeasureBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by hanyw on 2017/11/2/002.
 * 水土保持措施
 */
@RuntimePermissions
public class ProjmeasureFragment extends Fragment {

    private Context mContext;
    private String[] measureArray = new String[]{"工程措施", "植物措施", "临时措施", "管理措施", "其它"};
    private FragProjMeasureBinding binding;
    private ProjDetailViewModel viewModel;
    private static ProjmeasureFragment Sington;
    private List<String> list = new ArrayList<>();
    public static final int LOAD_ERROR = 0;

    private Handler mHandler;

    public static ProjmeasureFragment getInstance() {
        if (Sington == null) {
            Sington = new ProjmeasureFragment();
        }
        return Sington;
    }

    public void setViewModel(ProjDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_proj_measure,
                container, false);
        ProjmeasureFragmentPermissionsDispatcher.initDataWithCheck(this);
        return binding.getRoot();
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void initData(){
        mHandler = new Handler(mContext.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case LOAD_ERROR:
                        Toast.makeText(mContext,"图片加载错误",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
        list = Arrays.asList(measureArray);
        ProjmeasureExpLVAdapter adapter = new ProjmeasureExpLVAdapter(mContext, list, viewModel,mHandler);
        binding.projMeasureExplv.setGroupIndicator(null);
        binding.projMeasureExplv.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProjmeasureFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationale(final PermissionRequest request){
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void permissionDenied(){
        Toast.makeText(mContext, "已拒绝权限，无法读取文件，若想使用请开启权限",Toast.LENGTH_LONG).show();
    }

}
