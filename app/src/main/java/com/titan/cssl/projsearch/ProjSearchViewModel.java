package com.titan.cssl.projsearch;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.titan.cssl.BaseViewModel;
import com.titan.cssl.data.source.DataRepository;
import com.titan.cssl.model.ProjSearch;
import com.titan.cssl.model.ProjTime;

import java.util.Map;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索viewmodel
 */

public class ProjSearchViewModel extends BaseViewModel {
    private ProjSearchSet projSearchSet;

    private DateChoose dateChoose;

    private OptionSelect optionSelect;

    private DataRepository mDataRepository;

    public ObservableField<Map<String,ProjTime>> projectTimeMap = new ObservableField<>();

    /**
     * 检索设置
     */
    public ObservableField<ProjSearch> projSearch = new ObservableField<>();

    /**
     * 判断设置的时间 true：开始时间；false：结束时间
     */
    public ObservableBoolean timeSet = new ObservableBoolean();

    public ObservableField<String> startTime = new ObservableField<>();

    public ObservableField<String> endTime = new ObservableField<>();

    public ObservableField<String> projectType = new ObservableField<>();

    public ObservableField<String> projectStatus = new ObservableField<>();

    public ObservableInt year = new ObservableInt();

    public ObservableInt month = new ObservableInt();

    public ObservableInt day = new ObservableInt();

    public ObservableInt hour = new ObservableInt();

    public ObservableInt minute = new ObservableInt();


    public ProjSearchViewModel(ProjSearchSet projSearchSet, DataRepository mDataRepository) {
        this.projSearchSet = projSearchSet;
        this.mDataRepository = mDataRepository;
        initData();
    }


    public ProjSearchViewModel(DateChoose dateChoose) {
        this.dateChoose = dateChoose;
    }


    public ProjSearchViewModel(OptionSelect optionSelect){
        this.optionSelect = optionSelect;
    }

    private void initData(){
        projectTimeMap.set(mDataRepository.getProjectTimeMap());
        projSearch.set(mDataRepository.getProjSearch());
    }

    /**
     * 检索设置dialog
     */
    public void searchSet() {
        projSearchSet.searchSet();
    }

    public void startTimeSet(){
        projSearchSet.startTimeSet();
    }

    public void endTimeSet(){
        projSearchSet.endTimeSet();
    }

    public void setTimeSure(){
        if (timeSet.get()){
            dateChoose.startTime();
        }else {
            dateChoose.endTime();
        }
    }

    public void projectTypeSet(){
        projSearchSet.projectTypeSet();
    }

    public void approvalStatuSet(){
        projSearchSet.approvalStatuSet();
    }

    public void optionSelect(String value){
        optionSelect.select(value);
    }

    public void locaSearch(){
        projSearchSet.locaSearch();
    }

    public void projDetails(){
        projSearchSet.projDetails();
    }

}
