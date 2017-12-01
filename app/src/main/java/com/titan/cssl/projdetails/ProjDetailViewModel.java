package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titan.BaseViewModel;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.model.ProjDetailMeasure;
import com.titan.model.ProjSearch;
import com.titan.util.MyFileUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/11/2/002.
 * 项目详情model
 */

public class ProjDetailViewModel extends BaseViewModel {

    private Context mContext;
    /**
     * 详细信息接口
     */
    private ProjDetail projDetail;

    private DataRepository mDataRepository;

    /**
     * fragment列表
     */
    public ObservableField<List<String>> fragList = new ObservableField<>();

    /**
     * 项目信息列表
     */
    public ObservableField<List<String[]>> projDetailInfo = new ObservableField<>();

    /**
     * 项目信息父级名称
     */
    public ObservableField<String> projInfoParent = new ObservableField<>();

    /**
     * 项目信息子级
     */
    public ObservableField<List<Map<String, ?>>> projInfoChild = new ObservableField<>();

    /**
     * 是否在刷新状态
     */
    public ObservableBoolean isRefresh = new ObservableBoolean(false);

    /**
     * 当前页面序号
     */
    public ObservableInt currentFrag = new ObservableInt();

    /**
     * 项目措施
     */
    public ObservableField<ProjDetailMeasure> projDetailMeasure = new ObservableField<>();

    /**
     * 项目措施父级名称
     */
    public ObservableField<List<String>> projMeasureP = new ObservableField<>();

    /**
     * 项目措施子级
     */
    public ObservableField<List<List<ProjDetailMeasure.subBean>>> projMeasureC = new ObservableField<>();
    /**
     * 项目类型
     */
    public ObservableBoolean hasBaseinfo = new ObservableBoolean(true);

    /**
     * 坐标列表
     */
    public ObservableField<List<Double[]>> coordinateList = new ObservableField<>();

    /**
     * 是否有数据 true 有；false 无
     */
    public ObservableBoolean hasData = new ObservableBoolean(true);

    public ProjDetailViewModel(Context context, DataRepository mDataRepository, ProjDetail projDetail) {
        this.mContext = context;
        this.mDataRepository = mDataRepository;
        this.projDetail = projDetail;
    }

    /**
     * 页面选择
     *
     * @param pager 页码
     */
    public void pagerSelect(int pager) {
        projDetail.pagerSelect(pager);
    }

    /**
     * 查看项目措施详细信息
     */
    public void measureInfo(ProjDetailMeasure.subBean subBean) {
        projDetail.measureInfo(subBean);
    }

    public void getData(int type, boolean flag) {
        if (fragList.get() != null && fragList.get().contains(type + "")) {
            if (!isRefresh.get() && flag) {
                netRequest(type,false);
                isRefresh.set(true);
            }
        } else {
            netRequest(type,false);
        }
    }

    /**
     * 展示项目概要信息中子级内容
     *
     * @param list
     */
    public void showSubInfo(List<String[]> list) {
        List<String> stringList = new ArrayList<>();
        for (String[] strings : list) {
            stringList.add(strings[0] + ":  " + strings[1]);
        }
        projDetail.showSubInfo(stringList);
    }

    /**
     * 网络请求
     *
     * @param type 请求类型 0：基本信息；1：概要信息；2：措施信息
     */
    public void netRequest(final int type, final boolean isMap) {
        ProjSearch search = mDataRepository.getProjSearch();
        projDetail.showProgress();
        Log.e("tag", "info:" + search.getID() + "," + type + "," + getAlias(search.getTYPE()));
        mDataRepository.ProjectInfo(search.getID(), type, getAlias(search.getTYPE()), new RemoteData.Callback() {
            @Override
            public void onFailure(String info) {
                projDetail.stopProgress();
                projDetail.showToast(info);
                isRefresh.set(false);
                Log.e("tag", "projInfoError:" + info);
            }

            @Override
            public void onSuccess(List<? extends Map<String, ?>> info) {
                isRefresh.set(false);
                projDetail.stopProgress();
                if (type == 2) {
                    Gson gson = new Gson();
                    Map<String, ?> map2 = info.get(0);
                    ProjDetailMeasure measure = gson.fromJson(gson.toJson(map2), ProjDetailMeasure.class);
                    List<List<ProjDetailMeasure.subBean>> cList = new ArrayList<>();
                    List<String> pList = new ArrayList<>();
                    for (String k : map2.keySet()) {
                        setListData(map2.get(k), cList, pList, k);
                    }
                    projMeasureC.set(cList);
                    projMeasureP.set(pList);
                    projDetailMeasure.set(measure);
                    projDetail.refresh(type);
                    Log.e("tag", "detaInfo:" + map2);
                    projDetail.stopProgress();
                    return;
                }
                Map<String, ?> map = info.get(0);
                Log.e("tag", "detaMap:" + map);
                List<String[]> list1 = new ArrayList<>();
                List<List<String[]>> child = new ArrayList<>();
                for (String key : map.keySet()) {
                    if (key.equals("ID")) {
                        continue;
                    }
                    String[] array = new String[2];
                    if (key.equals("FNDSSJH") || key.equals("XMZC")) {
                        projInfoParent.set(getAlias(key));
                        List<Map<String, ?>> mapList = (List<Map<String, ?>>) map.get(key);

                        for (Map<String, ?> map1 : mapList) {
                            List<String[]> cList = new ArrayList<>();
                            for (String k : map1.keySet()) {
                                String[] array1 = new String[2];
                                array1[0] = getAlias(k);
                                setValue(map1, k, array1);
                                cList.add(array1);
                            }
                            child.add(cList);
                        }

                        projInfoChild.set(mapList);
                        continue;
                    }

                    if (key.equals("ZB")) {
                        String zbStr = (String) map.get(key);
                        String[] zbs = zbStr.split(";");
                        List<Double[]> list = new ArrayList<>();
                        for (String s:zbs ){
                            Double[] doubles = new Double[2];
                            String[] zb = s.split(",");
                            boolean flag = zb[0].equals("")||zb[0].trim().equals("")||
                                    zb[1].equals("")||zb[1].trim().equals("");
                            if (flag){
                                continue;
                            }
                            doubles[0] = Double.parseDouble(zb[0]);
                            doubles[1] = Double.parseDouble(zb[1]);
                            list.add(doubles);
                        }
                        coordinateList.set(list);
                        if (isMap){
                            projDetail.showMap();
                            return;
                        }
                    }
                    array[0] = getAlias(key);
                    setValue(map, key, array);
                    list1.add(array);
                }

                projDetailInfo.set(list1);
                projDetail.refresh(type);
            }
        });
    }

    /**
     * 将json字段转换为中文显示,解析json
     *
     * @param object json
     * @param cList  json内容列表
     * @param pList  json字段名称
     * @param key    map的key值
     */
    private void setListData(Object object, List<List<ProjDetailMeasure.subBean>> cList
            , List<String> pList, String key) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProjDetailMeasure.subBean>>() {
        }.getType();
        List<ProjDetailMeasure.subBean> list1 = gson.fromJson(gson.toJson(object), type);
        if (list1 != null && list1.size() > 0) {
            cList.add(list1);
            pList.add(getAlias(key));
        }
    }

    /**
     * 转换null值
     *
     * @param map
     * @param key
     * @param array
     */
    private void setValue(Map<String, ?> map, String key, String[] array) {
        if (map.get(key) == null || map.get(key).toString().trim().equals("")) {
            array[1] = "无";
        } else {
            if (key.equals("TYPE")) {
                array[1] = getAlias(map.get(key).toString());
                return;
            }
            array[1] = map.get(key).toString();
        }
    }

    /**
     * 获取json英文字段名称对应的中文名
     *
     * @param value json英文字段名
     * @return 对应的中文名
     */
    private String getAlias(String value) {
        return MyFileUtil.getProperties(mContext, value);
    }
}
