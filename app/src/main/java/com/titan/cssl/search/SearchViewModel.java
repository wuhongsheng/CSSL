package com.titan.cssl.search;

import com.titan.cssl.BaseViewModel;

/**
 * Created by hanyw on 2017/10/31/031.
 * 项目检索viewmodel
 */

public class SearchViewModel extends BaseViewModel {
    private Search search;

    public SearchViewModel(Search search){
        this.search = search;
    }


    public void searchSet(){
        search.searchSet();
    }

    public void timeChoose(){
        search.timeChoose();
    }

    public void typeChoose(){

    }

    public void statuChoose(){

    }
}
