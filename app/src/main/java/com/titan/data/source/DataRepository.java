package com.titan.data.source;

import android.content.Context;

import com.titan.cssl.remote.RemoteData;
import com.titan.cssl.remote.RemoteDataSource;
import com.titan.data.local.DataSource;
import com.titan.data.local.LocalDataSource;
import com.titan.model.ProjSearch;
import com.titan.model.UserModel;

/**
 * Created by hanyw on 2017/10/30
 */

public class DataRepository implements DataSource, RemoteData {
    private Context mContext;

    private static DataRepository INSTANCE = null;


    private final LocalDataSource mLocalDataSource;
    private final RemoteDataSource dataSource;

    public static DataRepository getInstance(LocalDataSource localDataSource, RemoteDataSource dataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(localDataSource, dataSource);
        }
        return INSTANCE;
    }

    public DataRepository(LocalDataSource localDataSource, RemoteDataSource dataSource) {
        //this.mContext=context;
        this.mLocalDataSource = localDataSource;
        this.dataSource = dataSource;
    }

    public ProjSearch getProjSearch() {
        return mLocalDataSource.getProjSearch();
    }

    public void setProjSearch(ProjSearch search) {
        mLocalDataSource.setProjSearch(search);
    }

    @Override
    public UserModel getUserModel() {
        return mLocalDataSource.getUserModel();
    }

    @Override
    public void setUserModel(UserModel userModel) {
        mLocalDataSource.setUserModel(userModel);
    }

    @Override
    public void login(String name, String password, Callback callback) {
        dataSource.login(name, password, callback);
    }

    @Override
    public void projSearch(String starttime, String endtime, String projecttype, String state,
                           String keyword, String role, String org, String id, String pageSize,
                           String pageIndex, Callback callback) {
        dataSource.projSearch(starttime, endtime, projecttype, state, keyword, role,
                org, id, pageSize, pageIndex, callback);
    }

    @Override
    public void ProjectInfo(String ID, int type, String projecttype, Callback callback) {
        dataSource.ProjectInfo(ID, type, projecttype, callback);
    }

    @Override
    public void InsertXCZFData(String json, infoCallback callback) {
        dataSource.InsertXCZFData(json, callback);
    }
}
