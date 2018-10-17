package com.titan.cssl.reserveplan;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.titan.base.BaseActivity;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityReservePlanBinding;
import com.titan.cssl.statistics.StatisticsFragment;
import com.titan.cssl.statistics.StatisticsViewModel;
import com.titan.util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by hanyw on 2017/11/9/009.
 * 预案查看
 */
@RuntimePermissions
public class ProjReservePlanActivity extends BaseActivity implements ProjReservePlan {

    private ActivityReservePlanBinding binding;
    private List<String> list;
    private static final int START = 0;

    private static final int STOP = 1;

    private Handler handler;
    private Dialog dialog;
    private Observable<ResponseBody> observable;
    private Subscriber<ResponseBody> subscriber;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.activity_reserve_plan, null, false);
        setContentView(binding.getRoot());
        initData();
        initView();

        MyApplication.getInstance().addActivity(this);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("预案文档" + i);
        }
        handler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case START:
                        dialog.show();
                        break;
                    case STOP:
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (!path.equals("")){
                            Intent intent = MyFileUtil.getImageFileIntent(path);
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initView() {
        Toolbar toolbar = binding.reserveToolbar;
        toolbar.setTitle(mContext.getResources().getString(R.string.proj_plan));
        setSupportActionBar(toolbar);
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
    public StatisticsFragment findOrCreateViewFragment() {
        return null;
    }

    @Override
    public StatisticsViewModel findOrCreateViewModel() {
        return null;
    }

    @Override
    public void load() {
        ProjReservePlanActivityPermissionsDispatcher.readWithCheck(this);
    }

    /**
     * 下载预案文档
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void read() {

//        String url = "http://218.77.44.9:8020/UpLoadFiles/%E6%B9%96%E5%8D%97%E9%87%91%E9%B9%B0%E5%9F%8E%E7%BD%AE%E4%B8%9A%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8/2017-07/334f5f57-76a0-4ce5-9c72-b5f5ca206572_1827_IMG_5410.jpg";
//        observable = RetrofitHelper.getInstance(mContext).getServer()
//                .downFile(url);
//        dialog = MaterialDialogUtil.showLoadProgress(mContext, "loading1...",
//                new DialogInterface.OnCancelListener() {
//                    @Override
//                    public void onCancel(DialogInterface dialogInterface) {
//                        Log.e("tag", "dialog end");
//                        if (!subscriber.isUnsubscribed()){
//                            //中断网络请求
//                            subscriber.unsubscribe();
//                        }
//                    }
//                }).build();
//        subscriber = new Subscriber<ResponseBody>() {
//            @Override
//            public void onCompleted() {
//                Log.e("tag", "end");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("tag", "failed1" + e);
//            }
//
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                Message message = new Message();
//                message.what = START;
//                handler.sendMessage(message);
//                path = DownLoadManager.writeResponseBodyToDisk(mContext, responseBody);
//                if (!path.equals("")) {
//                    Message message1 = new Message();
//                    message1.what = STOP;
//                    handler.sendMessage(message1);
//                    Log.e("tag", "success");
//                } else {
//                    Message message2 = new Message();
//                    message2.what = STOP;
//                    handler.sendMessage(message2);
//                    Log.e("tag", "failed");
//                }
//            }
//        };
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .subscribe(subscriber);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProjReservePlanActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void permissionDenied() {
        Toast.makeText(mContext, "已拒绝权限，无法读取文件，若想使用请开启权限", Toast.LENGTH_LONG).show();
    }
}
