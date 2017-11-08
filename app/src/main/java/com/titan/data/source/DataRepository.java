package com.titan.data.source;

import android.content.Context;

import com.titan.data.local.DataSource;
import com.titan.data.local.LocalDataSource;
import com.titan.model.ProjSearch;

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
}
