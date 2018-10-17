package com.titan.cssl.projdetails;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;

import com.esri.arcgisruntime.geometry.Point;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titan.base.BaseViewModel;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.model.ProjDetailItemModel;
import com.titan.model.ProjDetailMeasure;
import com.titan.model.ProjSearch;
import com.titan.util.MyFileUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
     * 行政审批记录
     */
    public ObservableField<List<List<String[]>>> projApproval = new ObservableField<>();

    /**
     * 日常监督报告
     */
    public ObservableField<List<List<String[]>>> projSupervise = new ObservableField<>();

    /**
     * 项目信息父级名称
     */
    public ObservableField<String> projInfoParent = new ObservableField<>();

    /**
     * 项目信息子级
     */
    public ObservableField<List<Map<String, ?>>> projInfoChild = new ObservableField<>();

    //项目基本信息、概况集合
    public List<ProjDetailItemModel> detailItemModelList;

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
     * 项目措施父级名称场平期
     */
    public List<String> measurePCpq = new ArrayList<>();
    /**
     * 项目措施父级名称建设期
     */
    public List<String> measurePJsq = new ArrayList<>();

    /**
     * 项目措施子级场平期
     */
    public List<List<ProjDetailMeasure.subBean>> measureCCpq = new ArrayList<>();
    /**
     * 项目措施子级建设期
     */
    public List<List<ProjDetailMeasure.subBean>> measureCJsq = new ArrayList<>();
    /**
     * 项目类型
     */
    public ObservableBoolean hasBaseinfo = new ObservableBoolean(true);

    /**
     * 坐标列表
     */
    public ObservableField<List<Double[]>> coordinateList = new ObservableField<>();

    /**
     * 设备当前位置
     */
    public ObservableField<Double[]> localPoint = new ObservableField<>();

    /**
     * 当前设备位置详细地址
     */
    public ObservableField<String> address = new ObservableField<>();

    /**
     * 水保措施是否有数据 true 有；false 无
     */
    public ObservableBoolean hasData = new ObservableBoolean(true);

    /**
     * 是否有行政审批记录 true 有；false 无
     */
    public ObservableBoolean hasApproval = new ObservableBoolean(true);
    /**
     * 是否有监督记录 true 有；false 无
     */
    public ObservableBoolean hasSupervise = new ObservableBoolean(true);

    /**
     * 行政审批记录列表是否显示 true 显示；false 隐藏
     */
    public ObservableBoolean showApproval = new ObservableBoolean(false);
    /**
     * 日常监督记录列表是否显示 true 显示；false 隐藏
     */
    public ObservableBoolean showSupervise = new ObservableBoolean(false);

    public ObservableBoolean is = new ObservableBoolean(false);

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
                netRequest(type, false);
                isRefresh.set(true);
            }
        } else {
            netRequest(type, false);
        }
    }

    /**
     * 显示项目记录
     *
     * @param type 记录类型 3 行政审批记录；4 日常监督记录
     */
    public void showRecord(int type) {
        if (type == 3 && showApproval.get()) {
            showApproval.set(false);
            Log.e("tag", "hideApproval");
        } else if (type == 3 && !hasApproval.get()) {
//            hasApproval.set(true);
        } else if (type == 4 && showSupervise.get()) {
            showSupervise.set(false);
            Log.e("tag", "hideSupervise");
        } else if (type == 4 && !hasSupervise.get()) {
//            hasSupervise.set(true);
        } else if (type == 3 && projApproval.get() != null) {
            showApproval.set(true);
            Log.e("tag", "showApproval");
        } else if (type == 4 && projSupervise.get() != null) {
            showSupervise.set(true);
            Log.e("tag", "showSupervise");
        } else {
            netRequest(type, false);

        }
    }

    /**
     * 展示项目概要信息中子级内容
     *
     * @param list
     */
    public void showSubInfo(List<String[]> list) {
        projDetail.showSubInfo(list);
    }

    /**
     * 网络请求
     *
     * @param type 请求类型 0：基本信息；1：概要信息；2：措施信息；3、行政审批记录；4、日常监督记录
     */
    public void netRequest(final int type, final boolean isMap) {
        ProjSearch search = mDataRepository.getProjSearch();
        projDetail.showProgress();
        Log.e("tag", "info:" + search.getID() + "," + type + "," + getAlias(search.getTYPE()));

//        if (type==3||type==4){
//         mDataRepository.ProjectInfo(search.getID(), type, getAlias(search.getTYPE()), new RemoteData.infoCallback() {
//             @Override
//             public void onFailure(String info) {
//                 projDetail.stopProgress();
//                 projDetail.showToast(info);
//                 isRefresh.set(false);
//                 Log.e("tag", "projRecordError:" + info);
//             }
//
//             @Override
//             public void onSuccess(String info) {
//                 Gson gson = new Gson();
//                 Type type = new TypeToken<Approval>(){}.getType();
//                 Approval mapList = gson.fromJson(info,type);
//
//             }
//         });
//        }
        mDataRepository.ProjectInfo(search.getID(), type, getAlias(search.getTYPE()), new RemoteData.Callback() {
            @Override
            public void onFailure(String info) {
                projDetail.stopProgress();
                projDetail.showToast(info);
                isRefresh.set(false);
                if (info.equals("没有审批流程")) {
                    showApproval.set(false);
//                    hasApproval.set(false);
                }
                if (info.equals("没有日常监督检查记录")) {
                    showSupervise.set(false);
//                    hasSupervise.set(false);
                }
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
                    for (int i = 0; i < pList.size(); i++) {
                        if (pList.get(i).contains("1")) {
                            measurePCpq.add(pList.get(i));
                            measureCCpq.add(cList.get(i));
                        }
                        if (pList.get(i).contains("2")) {
                            measurePJsq.add(pList.get(i));
                            measureCJsq.add(cList.get(i));
                        }
                    }
                    projDetailMeasure.set(measure);
                    projDetail.refresh(type);
                    Log.e("tag", "detaInfo:" + map2);
                    projDetail.stopProgress();
                    return;
                }
                if (type == 3 || type == 4) {
                    Gson gson = new Gson();
                    Type type1 = new TypeToken<List<Map<String, ?>>>() {
                    }.getType();
                    List<Map<String, ?>> mapList = gson.fromJson(gson.toJson(info), type1);
                    if (type == 4) {
                        Map<String, ?> tempMap = new HashMap<>();
                        for (Map<String, ?> map : mapList) {
                            if (map.containsKey("Reason")) {
                                map.remove("Reason");
                                break;
                            }
                        }
                    }
                    List<List<String[]>> list = getLists(mapList);
                    if (type == 3) {
                        projApproval.set(list);
                    } else {
                        projSupervise.set(list);
                    }
                    projDetail.refresh(type);
                    return;
                }
                Map<String, ?> map = info.get(0);
                Log.e("tag", "detaMap:" + map);
                List<String[]> list1 = new ArrayList<>();
//                List<List<String[]>> child = new ArrayList<>();
                List<ProjDetailItemModel> tempList1 = new ArrayList<>();
                List<ProjDetailItemModel> tempList2 = new ArrayList<>();
                for (String key : map.keySet()) {
                    if (key.equals("ID")) {
                        continue;
                    }
                    String[] array = new String[2];
                    //存在二级对象构建二级菜单
                    if (key.equals("FNDSSJH") || key.equals("XMZC")) {
                        projInfoParent.set(getAlias(key));
                        List<Map<String, ?>> mapList = (List<Map<String, ?>>) map.get(key);
                        ProjDetailItemModel model = new ProjDetailItemModel(getAlias(key), 1, setValue(map, key));
                        tempList2.add(model);
                        for (Map<String, ?> map1 : mapList) {
                            ProjDetailItemModel model1 = new ProjDetailItemModel();
                            if (key.equals("FNDSSJH")) {
                                model1.setType(3);
                            } else {
                                model1.setType(2);
                            }
                            ProjDetailItemModel.SubContent subContent = new ProjDetailItemModel.SubContent();
                            for (String k : map1.keySet()) {
                                switch (k) {
                                    case "ND":
                                        subContent.setYear(getAlias(k));
                                        subContent.setYearValue(setValue(map1, k));
                                        break;
                                    case "TZ":
                                        subContent.setInvestment(getAlias(k));
                                        subContent.setInvestmentValue(setValue(map1, k));
                                        break;
                                    case "BZ":
                                        subContent.setInstructions(getAlias(k));
                                        subContent.setInstructionsValue(setValue(map1, k));
                                        break;
                                    case "JSQY":
                                        subContent.setBuildArea(getAlias(k));
                                        subContent.setBuildAreaValue(setValue(map1, k));
                                        break;
                                    case "CDMJ":
                                        subContent.setLengArea(getAlias(k));
                                        subContent.setLengAreaValue(setValue(map1, k));
                                        break;
                                    case "WFL":
                                        subContent.setWfl(getAlias(k));
                                        subContent.setWflValue(setValue(map1, k));
                                        break;
                                    case "TFL":
                                        subContent.setTfl(getAlias(k));
                                        subContent.setTflValue(setValue(map1, k));
                                        break;
                                }
                            }
                            model1.setSubContent(subContent);
                            tempList2.add(model1);
                        }

                        projInfoChild.set(mapList);
                        continue;
                    }

                    //获取坐标
                    if (key.equals("ZB")) {
                        String zbStr = (String) map.get(key);
                        String[] zbs = zbStr.split(";");
                        List<Double[]> list = new ArrayList<>();
                        for (String s : zbs) {
                            Double[] doubles = new Double[2];
                            String[] zb = s.split(",");
                            boolean flag = zb[0].equals("") || zb[0].trim().equals("") ||
                                    zb[1].equals("") || zb[1].trim().equals("");
                            if (flag) {
                                continue;
                            }
                            doubles[0] = Double.parseDouble(zb[0]);
                            doubles[1] = Double.parseDouble(zb[1]);
                            list.add(doubles);
                        }
                        coordinateList.set(list);
                        if (isMap) {
                            Point point = mDataRepository.getLocalPoint();
                            Double[] latlon = new Double[2];
                            if (point != null) {
                                latlon[0] = point.getX();
                                latlon[1] = point.getY();
                            }
                            localPoint.set(latlon);
                            address.set(mDataRepository.getAddress());
                            projDetail.showMap();
                            return;
                        }
                    }
                    array[0] = getAlias(key);
                    array[1] = setValue(map, key);
                    list1.add(array);

                    tempList1.add(new ProjDetailItemModel(getAlias(key), 0, setValue(map, key)));
                }

                tempList1.addAll(tempList2);
                detailItemModelList = tempList1;

                projDetailInfo.set(list1);
                projDetail.refresh(type);
            }
        });
    }

    @NonNull
    private List<List<String[]>> getLists(List<? extends Map<String, ?>> maps) {
        List<List<String[]>> list = new ArrayList<>();
        for (Map<String, ?> map : maps) {
            List<String[]> list1 = new ArrayList<>();
            for (String k : map.keySet()) {
                String[] array = new String[2];
                array[0] = getAlias(k);
                array[1] = setValue(map, k);
                list1.add(array);
            }
            list.add(list1);
        }
        return list;
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
     */
    private String setValue(Map<String, ?> map, String key) {
        String result;
        if (map.get(key) == null || map.get(key).toString().trim().equals("")) {
            result = "无";
        } else {
            if (key.equals("TYPE")) {
                result = getAlias(map.get(key).toString());
            } else {
                result = map.get(key).toString();
            }
        }
        return result;
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
