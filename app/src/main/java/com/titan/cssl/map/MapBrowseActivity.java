package com.titan.cssl.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.nostra13.universalimageloader.utils.L;
import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityMapBrowseBinding;
import com.titan.cssl.util.ToastUtil;
import com.titan.model.ProjSearch;
import com.titan.util.ResourcesManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by hanyw on 2017/11/3/003.
 * 地图浏览
 */
public class MapBrowseActivity extends BaseActivity {

    private ActivityMapBrowseBinding binding;
    public static final String ROOT_MAPS = "/maps";
    private static final String otitan_map = "/otitan.map";
    private ArcGISMap arcGISMap;
    private Basemap basemap;
    private TileCache cache;
    private ArcGISTiledLayer tiledLayer;
    private GraphicsOverlay graphicsOverlay;
    private MarkerSymbol markerSymbol;
    private List<File> fileList;
    private ResourcesManager manager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.activity_map_browse, null, false);
            setContentView(binding.getRoot());
            manager = ResourcesManager.getInstance(mContext);
            initView();
            initData();
            binding.mapview.setBackgroundGrid(new BackgroundGrid(0xffffff, 0xffffff, 0, 3));

            double[] doubles = getIntent().getDoubleArrayExtra("coordinate");
            com.esri.arcgisruntime.geometry.Point point = new com.esri.arcgisruntime.geometry.Point(doubles[0],doubles[1],SpatialReferences.getWgs84());

            cache = new TileCache(fileList.get(0).getAbsolutePath());
            tiledLayer = new ArcGISTiledLayer(cache);
            basemap = new Basemap(tiledLayer);
            arcGISMap = new ArcGISMap();
            arcGISMap.setBasemap(basemap);
            binding.mapview.setMap(arcGISMap);
            graphicsOverlay = new GraphicsOverlay();
            binding.mapview.getGraphicsOverlays().add(graphicsOverlay);
            markerSymbol = new PictureMarkerSymbol((BitmapDrawable) ContextCompat
                    .getDrawable(mContext, R.drawable.icon_location));
            graphicsOverlay.getGraphics().add(new Graphic(point,markerSymbol));

            binding.mapview.setViewpointCenterAsync(point);
            binding.mapview.setOnTouchListener(new DefaultMapViewOnTouchListener(mContext,binding.mapview){
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    com.esri.arcgisruntime.geometry.Point point;
                    point = binding.mapview
                            .screenToLocation(new Point((int) e.getX(), (int) e.getY()));
                    com.esri.arcgisruntime.geometry.Point point1 = (com.esri.arcgisruntime.geometry.Point) GeometryEngine.project(point,SpatialReferences.getWgs84());
                    Graphic graphic = new Graphic(point1, markerSymbol);
                    Log.e("tag","point:"+point1.getX()+","+point1.getY());
                    graphicsOverlay.getGraphics().add(graphic);
                    return super.onSingleTapConfirmed(e);
                }
            });
        } catch (Exception e) {
            Log.e("tag", "mapError:" + e);
        }
    }

    @Override
    protected void onPause() {
        binding.mapview.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapview.resume();
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

    private void initData() {
        fileList = manager.getPahts("/otitan.map", "image");
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
                final List<File> list = fileList;
                List<String> stringList = new ArrayList<>();
                for (File file : list) {
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
                                    binding.mapview.setMap(arcGISMap);
                                    Log.e("tag", "spat:" + binding.mapview.getSpatialReference());
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
            case R.id.map_location:
                addLable();
                break;
            default:
                break;
        }
        return true;
    }


    private void addLable() {
        Intent intent = getIntent();
        if (intent != null) {
            double[] doubles = intent.getDoubleArrayExtra("coordinate");
            if (doubles != null) {
                com.esri.arcgisruntime.geometry.Point point = new com.esri.arcgisruntime.geometry
                        .Point(doubles[0], doubles[1],SpatialReferences.getWgs84());

                Log.e("tag","Scale:"+binding.mapview.getMapScale());
                binding.mapview.setViewpointCenterAsync(point);
                return;
            }
            ToastUtil.setToast(mContext, "坐标参数出错");
        }

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
