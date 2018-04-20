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

import com.esri.arcgisruntime.geometry.Point;
import com.titan.model.ProjSearch;
import com.titan.model.UserModel;

/**
 * Main entry point for accessing tasks data.
 */
public interface DataSource {

    ProjSearch getProjSearch();

    void setProjSearch(ProjSearch num);

    UserModel getUserModel();

    void setUserModel(UserModel userModel);

    Point getLocalPoint();

    void setLocalPoint(Point point);

    String getAddress();

    void setAddress(String address);
}
