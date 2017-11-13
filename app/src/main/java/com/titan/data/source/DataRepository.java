package com.titan.data.source;

import android.content.Context;

import com.titan.cssl.remote.RemoteData;
import com.titan.cssl.remote.RemoteDataSource;
import com.titan.data.local.DataSource;
import com.titan.data.local.LocalDataSource;

/**
 * Created by hanyw on 2017/10/30
 */

public class DataRepository implements DataSource ,RemoteData{
    private Context mContext;

    private static DataRepository INSTANCE = null;


    private final LocalDataSource mLocalDataSource;
    private final RemoteDataSource dataSource;

    public static DataRepository getInstance(LocalDataSource localDataSource, RemoteDataSource dataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(localDataSource,dataSource);
        }
        return INSTANCE;
    }

    public DataRepository(LocalDataSource localDataSource, RemoteDataSource dataSource) {
        //this.mContext=context;
        this.mLocalDataSource = localDataSource;
        this.dataSource = dataSource;
    }

    public String getProjNum(){
        return mLocalDataSource.getProjNum();
    }

    public void setProjNum(String projNum){
        mLocalDataSource.setProjNum(projNum);
    }

    @Override
    public String getRole() {
        return mLocalDataSource.getRole();
    }

    @Override
    public void setRole(String role) {
        mLocalDataSource.setProjNum(role);
    }

    @Override
    public void login(String name, String password, loginCallback callback) {
        dataSource.login(name,password,callback);
    }
}
