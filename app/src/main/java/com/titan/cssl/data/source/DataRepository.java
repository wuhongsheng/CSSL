package com.titan.cssl.data.source;

import android.content.Context;

import com.titan.cssl.data.local.DataSource;
import com.titan.cssl.data.local.LocalDataSource;
import com.titan.cssl.model.ProjSearch;
import com.titan.cssl.model.ProjTime;

import java.util.Map;

/**
 * Created by hanyw on 2017/10/30
 */

public class DataRepository implements DataSource{
    private Context mContext;

    private static DataRepository INSTANCE = null;


    private final LocalDataSource mLocalDataSource;

    public static DataRepository getInstance(LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(localDataSource);
        }
        return INSTANCE;
    }

    public DataRepository(LocalDataSource localDataSource) {
        //this.mContext=context;
        this.mLocalDataSource = localDataSource;
    }

    /**
     * @return 检索设置时间参数对象
     */
    public Map<String,ProjTime> getProjectTimeMap(){
        return mLocalDataSource.getProjectTimeMap();
    }

    /**
     * @return 检索设置参数对象
     */
    public ProjSearch getProjSearch(){
        return mLocalDataSource.getProjSearch();
    }
}
