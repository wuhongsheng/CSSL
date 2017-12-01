package com.titan.cssl.measures;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.titan.BaseViewModel;
import com.titan.cssl.R;
import com.titan.model.ProjCensor;
import com.titan.model.ProjDetailMeasure;

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

    public ObservableBoolean hasPic = new ObservableBoolean(false);

    public ObservableBoolean hasDoc = new ObservableBoolean(false);

    /**
     * 描述
     */
    public ObservableField<String> depict = new ObservableField<>();

    /**
     * 简要描述
     */
    public ObservableField<String> brief = new ObservableField<>();

    /**
     * 投资
     */
    public ObservableField<String> invest = new ObservableField<>();

    /**
     * 整改意见
     */
    public ObservableField<String> opinion = new ObservableField<>();

    public MeasureViewModel(Measure measure) {
        this.measure = measure;
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
    public void openPhoto(String url) {
        if (url.equals("")){
            return;
        }
        measure.openPhoto(url);
    }

    /**
     * 查看图片
     *
     * @param url 图片地址
     */
    public void getPhoto(Context context,String url) {
        ImageView imageView = LayoutInflater.from(context)
                .inflate(R.layout.item_img, null).findViewById(R.id.item_img);
        Glide.with(context).load(url).placeholder(R.drawable.loading).error(R.drawable.error)
                .override(1280, 768).into(imageView);
        new MaterialDialog.Builder(context).customView(imageView, true)
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build().show();
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

    public String getString(String value){
        if (value.equals("null")||value.equals("")){
            value = "无";
        }
        return value;
    }
}
