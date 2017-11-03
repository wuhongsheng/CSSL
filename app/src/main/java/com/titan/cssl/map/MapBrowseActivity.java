package com.titan.cssl.map;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.activity_map_browse, null, false);
        setContentView(binding.getRoot());
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

    private List<String> initData(){
        return getPahts(getBaseLayerPath(),"image");
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
                final List<String> list = initData();
                MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                        .title("图层切换")
                        .negativeText("取消")
                        .items(initData())
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                ToastUtil.setToast(mContext,list.get(position));
                                MapView mapView = binding.mapview;
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
            paths = (String[])sm.getClass().getMethod("getVolumePaths").invoke(sm);
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

    public String getBaseLayerPath() {
        String dataPath = "文件夹可用地址";
        String[] memoryPath = getMemoryPath();
        for (String aMemoryPath : memoryPath) {
            File file = new File(aMemoryPath + "/maps" + "/otitan.map");
            if (file.exists()) {
                dataPath = aMemoryPath + "/maps" + "/otitan.map";
                break;
            }
        }
        return dataPath;
    }

    public List<String> getPahts(String path, String keyword) {
        List<String> list = new ArrayList<>();
        String[] array = getMemoryPath();
        for (int i = 0; i < array.length; i++) {
            File file = new File(array[i] + "/maps" + path);
            if (file.exists()) {
                for (int m = 0; m < file.listFiles().length; m++) {
                    if (file.listFiles()[m].isFile()
                            && file.listFiles()[m].getName().contains(keyword)) {
                        list.add(file.listFiles()[m].getName());
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
