package com.titan.cssl.reserveplan;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityReservePlanBinding;
import com.titan.util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by hanyw on 2017/11/9/009.
 * 预案查看
 */
@RuntimePermissions
public class ProjReservePlanActivity extends BaseActivity implements ProjReservePlan {

    private ActivityReservePlanBinding binding;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.activity_reserve_plan, null, false);
        setContentView(binding.getRoot());
        initData();
        initView();

    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("测试" + i);
        }
    }

    private void initView() {
        Toolbar toolbar = binding.reserveToolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle("长沙水土保持");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        ListView listView = binding.reserveList;
        ProjReservePlanViewModel model = new ProjReservePlanViewModel(this);
        ProjReservePlanAdapter adapter = new ProjReservePlanAdapter(mContext, list, model);
        listView.setAdapter(adapter);
    }

    @Override
    public Fragment findOrCreateViewFragment() {
        return null;
    }

    @Override
    public BaseViewModel findOrCreateViewModel() {
        return null;
    }

    @Override
    public void load() {
        ProjReservePlanActivityPermissionsDispatcher.readWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void read(){
        String path = "/storage/emulated/0/新建 Word 2007.docx";
        Intent intent = MyFileUtil.getWordFileIntent(path);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProjReservePlanActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request){
        request.proceed();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void permissionDenied(){
        Toast.makeText(mContext, "已拒绝权限，无法读取文件，若想使用请开启权限",Toast.LENGTH_LONG).show();
    }
}
