package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.titan.cssl.R;
import com.titan.cssl.databinding.FragProjMeasureBinding;

/**
 * Created by hanyw on 2017/11/2/002.
 * 水土保持措施
 */
//@RuntimePermissions
public class ProjmeasureFragment extends Fragment {

    private Context mContext;
    private FragProjMeasureBinding binding;
    private ProjDetailViewModel viewModel;
    public static final int LOAD_ERROR = 0;

    public static ProjmeasureFragment getInstance() {
        return new ProjmeasureFragment();
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
        binding.setViewmodel(viewModel);
        return binding.getRoot();
    }

    //@NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void setData() {
        if (viewModel.projMeasureP.get() == null || viewModel.projMeasureP.get().size() <= 0) {
            viewModel.hasData.set(false);
            return;
        }
        ProjmeasureExpLVAdapter adapter = new ProjmeasureExpLVAdapter(mContext, viewModel.projMeasureP.get()
                , viewModel.projMeasureC.get(), viewModel);
        binding.projMeasureExplv.setGroupIndicator(null);
        binding.projMeasureExplv.setAdapter(adapter);
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        ProjmeasureFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
//
//    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
//    void showRationale(final PermissionRequest request) {
//        request.proceed();
//    }
//
//    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
//    void permissionDenied() {
//        Toast.makeText(mContext, "已拒绝权限，无法读取文件，若想使用请开启权限", Toast.LENGTH_LONG).show();
//    }
}
