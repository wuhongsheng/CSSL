package com.titan.cssl.projectsearch;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;

import com.titan.cssl.BaseViewModel;
import com.titan.cssl.data.source.DataRepository;
import com.titan.cssl.model.ProjectTime;

import java.util.Map;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索viewmodel
 */

public class ProjectSearchViewModel extends BaseViewModel {
    private ProjectSearch projectSearch;

    private DateChoose dateChoose;

    private OptionSelect optionSelect;

    private DataRepository mDataRepository;

    public ObservableField<Map<String,ProjectTime>> projectTimeMap = new ObservableField<>();

    /**
     * 判断设置的时间 true：开始时间；false：结束时间
     */
    public ObservableBoolean timeSet = new ObservableBoolean();

    public ObservableField<String> startTime = new ObservableField<>();

    public ObservableField<String> endTime = new ObservableField<>();

    public ObservableField<String> projectType = new ObservableField<>();

    public ObservableField<String> projectStatus = new ObservableField<>();

    public ObservableField<String> projectName = new ObservableField<>();

    public ObservableInt year = new ObservableInt();

    public ObservableInt month = new ObservableInt();

    public ObservableInt day = new ObservableInt();

    public ObservableInt hour = new ObservableInt();

    public ObservableInt minute = new ObservableInt();


    public ProjectSearchViewModel(ProjectSearch projectSearch, DataRepository mDataRepository) {
        this.projectSearch = projectSearch;
        this.mDataRepository = mDataRepository;
        initData();
    }


    public ProjectSearchViewModel(DateChoose dateChoose) {
        this.dateChoose = dateChoose;
    }


    public ProjectSearchViewModel(OptionSelect optionSelect){
        this.optionSelect = optionSelect;
    }

    private void initData(){
        projectTimeMap.set(mDataRepository.getProjectTimeMap());
    }

    /**
     * 检索设置dialog
     */
    public void searchSet() {
        projectSearch.searchSet();
    }

    public void startTimeSet(){
        projectSearch.startTimeSet();
    }

    public void endTimeSet(){
        projectSearch.endTimeSet();
    }

    public void setTimeSure(){
        if (timeSet.get()){
            dateChoose.startTime();
        }else {
            dateChoose.endTime();
        }
    }

    public void projectTypeSet(){
        projectSearch.projectTypeSet();
    }

    public void approvalStatuSet(){
        projectSearch.approvalStatuSet();
    }

    public void optionSelect(String name){
        optionSelect.select(name);
    }
}
