package com.titan.cssl.measures;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.util.Log;

import com.titan.base.BaseViewModel;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.model.ProjDetailMeasure;
import com.titan.util.MyFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/17/017.
 * 项目措施viewmodel
 */

public class MeasureViewModel extends BaseViewModel {

    /**
     * 项目措施接口
     */
    private Measure measure;

    /**
     * 是否有整改意见 false：没有；true：有
     */
    public ObservableField<Boolean> hasInfo = new ObservableField<>(false);

    /**
     * 防治分区
     */
    public ObservableField<String> depict = new ObservableField<>();

    /**
     * 措施具体说明
     */
    public ObservableField<String> brief = new ObservableField<>();

    /**
     * 投资/万元
     */
    public ObservableField<String> invest = new ObservableField<>();

    /**
     * 整改意见
     */
    public ObservableField<String> opinion = new ObservableField<>();

    public ObservableField<String> picName = new ObservableField<>();

    public ObservableField<String> picUrl = new ObservableField<>();

    public MeasureViewModel(Measure measure, DataRepository dataRepository) {
        this.measure = measure;
        this.mDataRepository = dataRepository;
    }

    /**
     * 添加整改意见
     */
    public void addOpinion() {
        measure.addOpinion();
    }

    /**
     * 打开图片
     *
     * @param url 链接
     */
    public void openPhoto(String url,String name) {
        if (url.equals("")){
            return;
        }
        picName.set(name);
        picUrl.set(url);
        measure.openPhoto();
    }

    /**
     * 查看图片
     */
    public void getPhoto(final Context context) {
//        ImageView imageView = LayoutInflater.from(context)
//                .inflate(R.layout.item_img, null).findViewById(R.id.item_img);
//        Glide.with(context).load(url).placeholder(R.drawable.loading).error(R.drawable.error)
//                .override(1280, 768).into(imageView);
//        new MaterialDialog.Builder(context).customView(imageView, true)
//                .negativeText("取消")
//                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        dialog.dismiss();
//                    }
//                }).build().show();
        final SharedPreferences sharedPreferences = MyApplication.sharedPreferences;
        String picPath = sharedPreferences.getString(picName.get(),"");
        File file = new File(picPath);
        if (file.exists()){
            Intent intent = MyFileUtil.getImageFileIntent(picPath);
            context.startActivity(intent);
            return;
        }
        String url = picUrl.get();
        String path = context.getResources().getString(R.string.serverhost)+url.substring(2, url.length());
        Log.e("tag","path:"+path);
        measure.showProgress();
        mDataRepository.downLoadFile(path, new RemoteData.infoCallback() {
            @Override
            public void onFailure(String info) {
                measure.stopProgress();
                measure.showToast(info);
                Log.e("tag","picError:"+info);
            }

            @Override
            public void onSuccess(String info) {
                measure.stopProgress();
                if (!info.equals("")){
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(picName.get(),info);
                    edit.apply();
                    Intent intent = MyFileUtil.getImageFileIntent(info);
                    context.startActivity(intent);
                }
            }
        });
    }

    /**
     * 转换图片对象信息
     *
     * @param list
     * @return
     */
    public List<String[]> getPhotoList(List<ProjDetailMeasure.Photo> list) {
        List<String[]> list1 = new ArrayList<>();
        for (ProjDetailMeasure.Photo photo : list) {
            String[] strings = new String[2];
            strings[0] = photo.getXCZPMC();
            strings[1] = photo.getXCZPURL();
            list1.add(strings);
        }
        return list1;
    }

    /**
     * 转换文档对象信息
     *
     * @param list
     * @return
     */
    public List<String[]> getDocList(List<ProjDetailMeasure.Docment> list) {
        List<String[]> list1 = new ArrayList<>();
        for (ProjDetailMeasure.Docment photo : list) {
            String[] strings = new String[2];
            strings[0] = photo.getDOCMC();
            strings[1] = photo.getDOCURL();
            list1.add(strings);
        }
        return list1;
    }

    /**
     * 转换空值
     * @param value
     * @return
     */
    public String getString(String value){
        if (value.equals("null")||value.equals("")){
            value = "无";
        }
        return value;
    }
}
