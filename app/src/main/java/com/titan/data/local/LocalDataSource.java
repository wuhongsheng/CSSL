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

import com.titan.model.ProjSearch;

import java.util.HashMap;
import java.util.Map;


/**
 * Concrete implementation of a data source as a db.
 */
public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;

    //private DbHelper mDbHelper;

    private Context mContext;

    private static String projNum;

    public static String getProjNum(){
        return projNum;
    }

    public void setProjNum(String num){
        projNum = num;
    }

    // Prevent direct instantiation.
    private LocalDataSource(@NonNull Context context) {
        this.mContext=context;
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

}
