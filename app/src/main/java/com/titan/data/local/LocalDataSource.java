/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.titan.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.esri.arcgisruntime.geometry.Point;
import com.titan.model.ProjSearch;
import com.titan.model.UserModel;


/**
 * Concrete implementation of a data source as a db.
 */
public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;

    //private DbHelper mDbHelper;

    private Context mContext;

    private static ProjSearch projSearch;

    private UserModel userModel;

    private Point point;

    public ProjSearch getProjSearch() {
        return projSearch;
    }

    public void setProjSearch(ProjSearch search) {
        projSearch = search;
    }

    @Override
    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Point getLocalPoint() {
        return point;
    }

    @Override
    public void setLocalPoint(Point point) {
        this.point = point;
    }

    // Prevent direct instantiation.
    private LocalDataSource(@NonNull Context context) {
        this.mContext = context;
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

}
