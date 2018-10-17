package com.titan.cssl.projsearch;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.esri.arcgisruntime.geometry.AngularUnit;
import com.esri.arcgisruntime.geometry.AngularUnitId;
import com.esri.arcgisruntime.geometry.GeodeticCurveType;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.LinearUnit;
import com.esri.arcgisruntime.geometry.LinearUnitId;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.titan.base.BaseViewModel;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.model.Gps;
import com.titan.model.ProjSearch;
import com.titan.model.UserModel;
import com.titan.util.MyFileUtil;
import com.titan.util.PositionUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索viewmodel
 */

public class ProjSearchViewModel extends BaseViewModel implements BDLocationListener {
    private Context mContext;
    /**
     * 项目检索设置接口
     */
    private ProjSearchSet projSearchSet;

    /**
     * 时间选择接口
     */
    private DateChoose dateChoose;

    /**
     * 项目参数选择接口
     */
    private OptionSelect optionSelect;

    /**
     * 数据管理
     */
    private DataRepository mDataRepository;

    /**
     * 判断设置的时间 true：开始时间；false：结束时间
     */
    public ObservableBoolean timeSet = new ObservableBoolean();

    /**
     * 是否根据当前位置搜索，true：是；false：否
     */
    public ObservableBoolean isLocal = new ObservableBoolean();

    /**
     * 开始时间
     */
    public ObservableField<String> startTime = new ObservableField<>("请选择");

    /**
     * 结束时间
     */
    public ObservableField<String> endTime = new ObservableField<>("请选择");

    /**
     * 项目类型
     */
    public ObservableField<String> projectType = new ObservableField<>("请选择");

    /**
     * 项目审批状态
     */
    public ObservableField<String> projectStatus = new ObservableField<>("请选择");

    /**
     * 搜索关键词
     */
    public ObservableField<String> keyWord = new ObservableField<>();

    /**
     * 当前坐标点
     */
    public ObservableField<Point> locPoint = new ObservableField<>();

    /**
     * 已经请求到的数据页码
     */
    public ObservableInt pageIndex = new ObservableInt(1);

    /**
     * 是否获取到当前坐标点
     */
    public ObservableBoolean hasLocPoint = new ObservableBoolean();

    /**
     * 项目检索结果数据list
     */
    public ObservableField<List<ProjSearch>> projSearchList = new ObservableField<>();

    /**
     * 项目检索结果数据备份list
     */
    public ObservableField<List<ProjSearch>> projSearchListBack = new ObservableField<>();

    /**
     * 是否在刷新状态 true：是；false：否
     */
    public ObservableBoolean isRefresh = new ObservableBoolean(false);

    /**
     * 是否还有更多数据，true 有；false 没有
     */
    public ObservableBoolean hasMore = new ObservableBoolean(true);

    /**
     * 项目类型 -1:"全部",1：3万㎡以下；2：3-8万㎡；3：8万㎡以上
     */
    public ObservableInt projType = new ObservableInt();


    /**
     * 项目检索
     *
     * @param context
     * @param projSearchSet
     * @param mDataRepository
     */
    public ProjSearchViewModel(Context context, ProjSearchSet projSearchSet, DataRepository mDataRepository) {
        this.mContext = context;
        this.projSearchSet = projSearchSet;
        this.mDataRepository = mDataRepository;
    }

    /**
     * 时间选择
     *
     * @param dateChoose
     */
    public ProjSearchViewModel(DateChoose dateChoose) {
        this.dateChoose = dateChoose;
    }

    /**
     * 项目类型与审批状态选择
     *
     * @param optionSelect
     */
    public ProjSearchViewModel(OptionSelect optionSelect) {
        this.optionSelect = optionSelect;
    }

    /**
     * 开始时间选择
     */
    public void startTimeSet() {
        projSearchSet.startTimeSet();
    }

    /**
     * 结束时间选择
     */
    public void endTimeSet() {
        projSearchSet.endTimeSet();
    }

    /**
     * 时间选择确定
     */
    public void setTimeSure() {
        if (timeSet.get()) {
            dateChoose.startTime();
        } else {
            dateChoose.endTime();
        }
    }

    /**
     * 项目类型选择
     */
    public void projectTypeSet() {
        projSearchSet.projectTypeSet();
    }

    /**
     * 项目审批状态选择
     */
    public void approvalStatuSet() {
        projSearchSet.approvalStatuSet();
    }

    /**
     * 项目参数选择
     */
    public void optionSelect(String value) {
        optionSelect.select(value);
    }

    /**
     * 项目详细信息
     */
    public void projDetails(ProjSearch search) {
        projSearchSet.projDetails(search.getTYPE());
        mDataRepository.setProjSearch(search);
        Log.e("tag", "num:" + search.getNUM());
    }

    /**
     * 根据当前位置搜索项目
     */
    public void locSearch() {
        projSearchSet.locSearch();
    }

    /**
     * 项目检索
     */
    public void search(final boolean isloadMore) {
//        getProjType();
        projSearchSet.showProgress();
        isRefresh.set(true);
        UserModel userModel = mDataRepository.getUserModel();
        if (userModel == null) {
            return;
        }
        Log.e("tag", "search:" + projectType.get() + "," +
                projectStatus.get() + "," + userModel.toString());
//        mDataRepository.projSearch(getString(startTime), getString(endTime), getAlias(getString(projectType)),
//                getAlias(getString(projectStatus)), getString(keyWord), userModel.getROLE(),
        mDataRepository.projSearch(getString(startTime), getString(endTime), getAlias(getString(projectType)),
                getAlias(getString(projectStatus)), getString(keyWord), userModel.getROLE(),
                userModel.getORG(), userModel.getID(), "10", pageIndex.get() + "",
                new RemoteData.Callback() {
                    @Override
                    public void onFailure(String info) {
                        projSearchSet.stopProgress();
                        projSearchSet.showToast(info);
                        isRefresh.set(false);
                        if (info != null && info.equals("未查询到数据记录！")) {
                            projSearchSet.showEnd();
                        }
                        Log.e("tag", "searchError:" + info);
                    }

                    @Override
                    public void onSuccess(List<? extends Map<String, ?>> info) {
                        pageIndex.set(pageIndex.get() + 1);
                        if (hasLocPoint.get()) {
                            projSearchSet.stopProgress();
                        }
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<ProjSearch>>() {
                        }.getType();
                        JsonReader reader = new JsonReader(new StringReader(gson.toJson(info)));
                        reader.setLenient(true);
                        if (!isloadMore) {
                            projSearchListBack.set(null);
                        }
                        List<ProjSearch> list = gson.fromJson(reader, type);
                        List<ProjSearch> oldList;
                        if (projSearchListBack.get() != null) {
                            oldList = projSearchListBack.get();
                        } else {
                            oldList = new ArrayList<>();
                        }
                        oldList.addAll(list);
                        projSearchListBack.set(oldList);
                        projSearchList.set(valueFormat(projSearchListBack.get()));

                        projSearchSet.refresh(isloadMore);
                        isRefresh.set(false);
                        if (list.size() < 10) {
                            projSearchSet.showEnd();
                        } else {
                            projSearchSet.showLoading();
                        }
                        Log.e("tag", "projList:" + projSearchList);
                    }
                });
    }

    /**
     * 项目距离排序
     */
    public void distanceSort() {
        List<ProjSearch> list = projSearchList.get();
        List<ProjSearch> tempList1 = new ArrayList<>();
        List<ProjSearch> tempList2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            try {
                float f = Float.parseFloat(list.get(i).getZB());
                tempList1.add(list.get(i));
            } catch (Exception e) {
                Log.e("tag", "" + e);
                tempList2.add(list.get(i));
            }
        }
        ProjSearch[] array = tempList1.toArray(new ProjSearch[tempList1.size()]);
        quickSort(array, 0, array.length - 1);

        tempList1.clear();
        tempList1 = new ArrayList<>(Arrays.asList(array));
        tempList1.addAll(tempList2);
        projSearchList.set(tempList1);
    }

    public List<ProjSearch> valueFormat(List<ProjSearch> list) {
        List<ProjSearch> list1 = null;
        try {
            list1 = deepCopy(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (list1 == null) {
            return null;
        }
        for (ProjSearch s : list1) {
            s.setTYPE(getAlias(s.getTYPE()));
            s.setSTATE(getState(s.getSTATE()));
            s.setZB(getDistance(cutString(s.getZB())));
        }
        return list1;
    }

    /**
     * list深度复制
     *
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    private String getAlias(String value) {
        return MyFileUtil.getProperties(mContext, value);
    }

    public void getProjType() {
        switch (projType.get()) {
            case -1:
                projectType.set("全部");
                break;
            case 1:
                projectType.set("3万㎡以下");
                break;
            case 2:
                projectType.set("3-8万㎡");
                break;
            case 3:
                projectType.set("8万㎡以上");
                break;
            default:
                break;
        }
    }

    /**
     * 快速排序
     *
     * @param array 对象数组
     * @param min   数组最小下标
     * @param max   数组最大下标
     */
    private static void quickSort(ProjSearch[] array, int min, int max) {
        if (min > max) {
            return;
        }
        int i = min;
        int j = max;

        ProjSearch projSearch = array[min];
        float pivot = Float.parseFloat(array[min].getZB());

        while (i != j) {
            while (Float.parseFloat(array[j].getZB()) >= pivot && i < j)
                j--;
            while (Float.parseFloat(array[i].getZB()) <= pivot && i < j)
                i++;
            if (i < j) {
                ProjSearch projSearch1 = array[i];
                array[i] = array[j];
                array[j] = projSearch1;
            }
        }
        array[min] = array[i];
        array[i] = projSearch;
        quickSort(array, min, i - 1);
        quickSort(array, i + 1, max);
    }

    private String getState(String value) {
        String state = "";
        switch (value) {
            case "0":
            case "草稿":
                state = "草稿";
                break;
            case "1":
            case "提交审核中":
                state = "提交审核中";
                break;
            case "2":
            case "行政审批通过,项目审核中":
                state = "行政审批通过,项目审核中";
                break;
            case "3":
            case "审核不通过":
                state = "审核不通过";
                break;
            case "4":
            case "出窗":
                state = "出窗";
                break;
            case "5":
            case "审核通过":
                state = "审核通过";
                break;
            default:
                break;
        }
        return state;
    }

    /**
     * 计算两地的距离
     *
     * @param list
     * @return
     */
    private String getDistance(List<String> list) {
        LinearUnit unit = new LinearUnit(LinearUnitId.KILOMETERS);//距离单位
        AngularUnit angularUnit = new AngularUnit(AngularUnitId.DEGREES);//角度单位
        Point locPoint = this.locPoint.get();
        if (!hasLocPoint.get()) {
            Log.e("tag", "haslocpoint:" + hasLocPoint.get() + "," + locPoint);
            return "未知";
        }
        String[] strings;
        PointCollection collection = new PointCollection(SpatialReferences.getWgs84());
        for (String str : list) {
            if (str.equals(",") || str.equals("") || str.contains("E") || str.equals("未知")) {
                return "未知";
            }
            strings = str.split(",");
            double d1 = Double.parseDouble(strings[0]);
            double d2 = Double.parseDouble(strings[1]);
            collection.add(d1, d2);
        }
        Polygon polygon = new Polygon(collection);
        Point point = polygon.getExtent().getCenter();
        Point projPoint = new Point(point.getX(), point.getY(), SpatialReferences.getWgs84());
        DecimalFormat fnum = new DecimalFormat("##0.00");
        double distance = GeometryEngine.distanceGeodetic(locPoint, projPoint, unit, angularUnit, GeodeticCurveType.GEODESIC).getDistance();
//        Log.e("tag", "distance:" + distance);
        return fnum.format(distance);
    }

    /**
     * 以分号分割字符串
     *
     * @param str 待分割字符串
     * @return 分割好的字符list
     */
    private List<String> cutString(String str) {
        List<String> list = new ArrayList<>();
        String[] strings = str.split(";");
        Collections.addAll(list, strings);
        return list;

    }

    /**
     * @param value
     * @return
     */
    private String getString(ObservableField<String> value) {
        String str = "";
        if (value.get() == null || value.get().equals("请选择")) {
            return str;
        }
        return value.get();
    }

    /**
     * 百度定位回调
     *
     * @param bdLocation 定位数据
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            Gps gps = PositionUtil.gcj_To_Gps84(longitude, latitude);
            double d1 = gps.getWgLon();
            double d2 = gps.getWgLat();
            Point point = new Point(d1, d2, SpatialReferences.getWgs84());
            locPoint.set(point);
            hasLocPoint.set(true);
            mDataRepository.setLocalPoint(point);
            mDataRepository.setAddress(bdLocation.getAddrStr());
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//            Log.e("tag", "lat:" + latitude + "," + "lon:" + longitude + "radius：" + radius + "," + coorType);
        } else {
            hasLocPoint.set(false);
//            Toast.makeText(mContext, "定位失败：" + bdLocation.getLocTypeDescription() + ",请检查权限是否授予"
//                    , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }
}
