package com.titan.cssl.map;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.nostra13.universalimageloader.utils.L;
import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityMapBrowseBinding;
import com.titan.cssl.util.ToastUtil;
import com.titan.model.ProjSearch;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/3/003.
 * 地图浏览
 */

public class MapBrowseActivity extends BaseActivity {

    private ActivityMapBrowseBinding binding;
    private MapView mapView;
    public static final String ROOT_MAPS = "/maps";
    private static final String otitan_map = "/otitan.map";
    private ArcGISMap arcGISMap;
    private Basemap basemap;
    private TileCache cache;
    private ArcGISTiledLayer tiledLayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.activity_map_browse, null, false);
        setContentView(binding.getRoot());
        mapView = binding.mapview;
        mapView.setBackgroundGrid(new BackgroundGrid(0xffffff, 0xffffff, 0, 3));
        try {
            cache = new TileCache(getTitlePath());
            tiledLayer = new ArcGISTiledLayer(cache);
            basemap = new Basemap(tiledLayer);
            arcGISMap = new ArcGISMap();
            arcGISMap.setBasemap(basemap);
            mapView.setMap(arcGISMap);
        } catch (Exception e) {
            Log.e("tag", "mapError:" + e);
        }
        initView();
    }

    private void initView() {
        Toolbar toolbar = binding.mapBrowseToolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.appname));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private List<File> initData() {
        return getPahts("/otitan.map", "image");
    }

    public String getTitlePath() {
        String name = otitan_map + "/title_gy.tpk";
        return getFilePath(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_change:
                final List<File> list = initData();
                List<String> stringList = new ArrayList<>();
                for (File file:list){
                    stringList.add(file.getName());
                }
                MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                        .title("图层切换")
                        .negativeText("取消")
                        .items(stringList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                try {
                                    cache = new TileCache(list.get(position).getAbsolutePath());
                                    tiledLayer = new ArcGISTiledLayer(cache);
                                    basemap = new Basemap(tiledLayer);
                                    arcGISMap = new ArcGISMap();
                                    arcGISMap.setBasemap(basemap);
                                    mapView.setMap(arcGISMap);
                                } catch (Exception e) {
                                    Log.e("tag", "dialogError:" + e);
                                }
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .build();
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }


    public String[] getMemoryPath() {
        StorageManager sm = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        String[] paths = null;
        try {
            //paths = (String[]) sm.getClass().getMethod("getVolumePaths", new Class[0]).invoke(sm,new Object[]{});
            paths = (String[]) sm.getClass().getMethod("getVolumePaths").invoke(sm);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return paths;
    }

    public String getFilePath(String path) {
        String dataPath = "文件可用地址";
        String[] memoryPath = getMemoryPath();
        for (int i = 0; i < memoryPath.length; i++) {
            File file = new File(memoryPath[i] + ROOT_MAPS + path);
            if (file.exists() && file.isFile()) {
                dataPath = memoryPath[i] + ROOT_MAPS + path;
                break;
            }
        }
        return dataPath;
    }

    public List<File> getPahts(String path, String keyword) {
        List<File> list = new ArrayList<>();
        String[] array = getMemoryPath();
        for (int i = 0; i < array.length; i++) {
            File file = new File(array[i] + ROOT_MAPS + path);
            if (file.exists()) {
                for (int m = 0; m < file.listFiles().length; m++) {
                    if (file.listFiles()[m].isFile()
                            && file.listFiles()[m].getName().contains(keyword)) {
                        list.add(file.listFiles()[m]);
                    }
                }
            }
        }
        return list;
    }


    @Override
    public Fragment findOrCreateViewFragment() {
        return null;
    }

    @Override
    public BaseViewModel findOrCreateViewModel() {
        return null;
    }
}
