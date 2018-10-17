package com.titan.cssl.localcensor;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.titan.base.BaseViewModel;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.model.ProjCensor;
import com.titan.model.ProjDetailMeasure;
import com.titan.util.MyFileUtil;
import com.titan.util.ResourcesManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/6/006.
 * 现场审查viewmodel
 */

public class ProjOpinionViewModel extends BaseViewModel {

    /**
     * 选择"属实"
     */
    public ObservableBoolean isTrue = new ObservableBoolean(true);
    /**
     * 选择"不属实"
     */
    public ObservableBoolean isFalse = new ObservableBoolean();

    /**
     * 图片地址
     */
    public ObservableField<List<String>> urlList = new ObservableField<>();

    /**
     * 水保措施详情对象
     */
    public ObservableField<ProjDetailMeasure.subBean> subBean = new ObservableField<>();

    /**
     * 整改意见对象json
     */
    public ObservableField<String> json = new ObservableField<>();

    /**
     * json转换完成标识位
     */
    private final int FINISH = 0;


    /**
     * 整改意见接口
     */
    private ProjOpinion projOpinion;
    private DataRepository mDataRepository;
    private Handler handler;

    public ProjOpinionViewModel(ProjOpinion projOpinion, DataRepository mDataRepository) {
        this.projOpinion = projOpinion;
        this.mDataRepository = mDataRepository;
    }

    /**
     * 添加照片
     */
    public void addImage() {
        projOpinion.addImage();
    }

    /**
     * 提交审查数据
     */
    public void localCensorSubmit() {
        projOpinion.localCensorSubmit();
    }

    /**
     * 删除照片
     *
     * @param position 照片数组下标
     */
    public void del(int position) {
        projOpinion.del(position);
    }

    /**
     * 提交数据
     */
    public void submit() {
        projOpinion.showProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("tag", "firstTime");
                createJson();
            }
        }).start();
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == FINISH) {
                    Log.e("tag", "SecondTime");
                    mDataRepository.InsertXCZFData(json.get(), new RemoteData.infoCallback() {
                        @Override
                        public void onFailure(String info) {
                            projOpinion.stopProgress();
                            projOpinion.showToast(info);
                            Log.e("tag", "insertDataError:" + info);
                        }

                        @Override
                        public void onSuccess(String info) {
                            projOpinion.stopProgress();
                            projOpinion.showToast(info);
                            Log.e("tag", "updata:" + info);
                        }
                    });
                }
            }
        };

    }

    /**
     * json转换
     */
    private void createJson() {
        String id = mDataRepository.getUserModel().getID();
        String[] ids = id.split("\\.");
        Gson gson = new Gson();
        ProjCensor.photo photo = new ProjCensor.photo();
        List<ProjCensor.photo> list = new ArrayList<>();
        photo.setNAME(ResourcesManager.getCSPicName(subBean.get().getCSNAME(), ids[0]));
        try {
            String a = MyFileUtil.encodeBase64File(urlList.get().get(0));
            if (!a.equals("")) {
                photo.setDATA(a);
            } else {
                Toast.makeText(mContext, "图片转换错误", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("tag", "tobase64Error:" + e);
            projOpinion.showToast("图片转换错误" + e);
        }
        list.add(photo);
        ProjCensor censor = new ProjCensor(subBean.get().getID(), list,
                isTrue.get() ? "属实" : "不属实", ids[0]);
        json.set(gson.toJson(censor));
        Message message = new Message();
        message.what = FINISH;
        handler.sendMessage(message);
    }
}
