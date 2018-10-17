package com.titan.cssl.statistics;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.titan.base.BaseViewModel;
import com.titan.cssl.remote.RemoteData;
import com.titan.data.source.DataRepository;
import com.titan.util.MyFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hanyw on 2017/12/7/007.
 */

public class StatisticsViewModel extends BaseViewModel {
    private Context mContext;
    private Statistics statistics;

    public ObservableField<List<String>> name = new ObservableField<>();

    public ObservableField<List<String>> subName = new ObservableField<>();

    public ObservableField<List<Float>> scale = new ObservableField<>();

    public ObservableField<List<Float>> subScale = new ObservableField<>();


    public StatisticsViewModel(Context context,Statistics statistics, DataRepository dataRepository) {
        this.mContext = context;
        this.statistics = statistics;
        this.mDataRepository = dataRepository;
    }

    public void getData(){
        statistics.showProgress();
        mDataRepository.Statistics(new RemoteData.Callback() {
            @Override
            public void onFailure(String info) {
                statistics.stopProgress();
                statistics.showToast(info);
                Log.e("tag","statisticsError:"+info);
            }

            @Override
            public void onSuccess(List<? extends Map<String, ?>> info) {

                Map<String, ?> map = info.get(0);
                List<String> nameList = new ArrayList<>();
                List<String> subNameList = new ArrayList<>();
                List<Float> valueList = new ArrayList<>();
                List<Float> subValueList = new ArrayList<>();
                float[] values = new float[3];
                int i=0;
                for (String key:map.keySet()){
                    Map<String,?> map1 = (Map<String, ?>) map.get(key);
                    nameList.add(MyFileUtil.getProperties(mContext,key));
                    for (String k:map1.keySet()){
                        values[i] +=Float.parseFloat(map1.get(k).toString());
                        subNameList.add(MyFileUtil.getProperties(mContext,k));
                        subValueList.add(Float.parseFloat(map1.get(k).toString()));
                    }
                    valueList.add(values[i]);
                    i++;
                }
                name.set(nameList);
                subName.set(subNameList);
                float all=0;
                for (float value:valueList){
                    all+=value;
                }
                for (int j = 0; j < valueList.size(); j++) {
                    valueList.set(j,valueList.get(j)/all*100);
                }
//                for (int j = 0; j < subValueList.size(); j++) {
//                    valueList.set(j,valueList.get(j)/all*100);
//                    float value = (float) (Math.random() * 10) + 3;
//                    subValueList.set(j,value);
//                }
                statistics.stopProgress();

                scale.set(valueList);
                subScale.set(subValueList);
                statistics.setData();
                statistics.initBarChart();
                Log.e("tag","map:"+map.toString());
            }
        });
    }

    //通过点击的柱状图的点击位置判断项目审核状态
    public String getProjStatu(Entry e){
        String statu = "";
        switch ((int) (e.getX()%3)){
            case 0:
               statu = "审核通过";
               break;
            case 1:
                statu = "提交审核中";
                break;
            case 2:
                statu = "审核不通过";
                break;
        }
        return statu;
    }
}
