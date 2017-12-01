package com.titan.cssl.map;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.titan.BaseActivity;
import com.titan.BaseViewModel;
import com.titan.MyApplication;
import com.titan.cssl.R;
import com.titan.cssl.databinding.ActivityMapBrowseBinding;
import com.titan.util.ResourcesManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by hanyw on 2017/11/3/003.
 * 地图浏览
 */
@RuntimePermissions
public class MapBrowseActivity extends BaseActivity {

    private ActivityMapBrowseBinding binding;
    public static final String ROOT_MAPS = "/maps";
    private static final String otitan_map = "/otitan.map";
    private ArcGISMap arcGISMap;
    private Basemap basemap;
    private TileCache cache;
    private ArcGISTiledLayer tiledLayer;
    /**
     * 绘制图层
     */
    private GraphicsOverlay graphicsOverlay;
    private MarkerSymbol markerSymbol;
    private List<File> fileList;
    private ResourcesManager manager;
    /**
     * 项目所在位置标注
     */
    private Graphic graphic;
    private List<Double[]> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                    R.layout.activity_map_browse, null, false);
            setContentView(binding.getRoot());
            initView();

//            MapBrowseActivityPermissionsDispatcher.initDataWithCheck(this);
            binding.mapview.setBackgroundGrid(new BackgroundGrid(0xffffff,
                    0xffffff, 0, 3));
            //去除水印
            ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none,RP5X0H4AH7CLJ9HSX018");
            //去除版权声明
            binding.mapview.setAttributionTextVisible(false);
            //创建地图
            arcGISMap = new ArcGISMap(Basemap.createImageryWithLabelsVector());
            arcGISMap.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    locaLable();
                }
            });
            //arcGISMap.setBasemap(basemap);
            binding.mapview.setMap(arcGISMap);
            graphicsOverlay = new GraphicsOverlay();
            binding.mapview.getGraphicsOverlays().add(graphicsOverlay);

            markerSymbol = new PictureMarkerSymbol((BitmapDrawable) ContextCompat
                    .getDrawable(mContext, R.drawable.icon_location));
            createLable();
            binding.mapview.setOnTouchListener(new DefaultMapViewOnTouchListener(mContext,
                    binding.mapview) {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    try {
                        Point point;
                        point = binding.mapview
                                .screenToLocation(new android.graphics.Point((int) e.getX(), (int) e.getY()));
                        Point point1 = (Point)
                        GeometryEngine.project(point, SpatialReferences.getWgs84());
                        Graphic graphic = new Graphic(point1, markerSymbol);
                        Log.e("tag", "point:" + point1.getX() + "," + point1.getY() + ","
                                + binding.mapview.getMapScale());
//                        graphicsOverlay.getGraphics().add(graphic);
                    } catch (Exception e1) {
                        Toast.makeText(mContext, "map error:" + e1, Toast.LENGTH_SHORT).show();
                        Log.e("tag", "map error:" + e1);
                    }
                    return super.onSingleTapConfirmed(e);
                }
            });
//            }
        } catch (Exception e) {
            Log.e("tag", "mapError:" + e);
        }

        MyApplication.getInstance().addActivity(this);
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

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void initData() {
//        fileList = manager.getPahts(otitan_map, "image");
//        if (fileList == null || fileList.size() <= 0) {
//            MaterialDialogUtil.showSureDialog(mContext, "没有发现图层数据")
//                    .onPositive(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                            dialog.dismiss();
//                        }
//                    }).build().show();
//        }
    }

    /**
     * 创建标注
     */
    private void createLable() {
        Intent intent = getIntent();
        if (intent != null) {
            list = (List<Double[]>) getIntent().getExtras().get("coordinate");
            try {
                if (list != null && list.size() == 1) {
                    Point point = new Point(list.get(0)[0], list.get(0)[1], SpatialReferences.getWgs84());
                    graphicsOverlay.getGraphics().add(new Graphic(point, markerSymbol));
                    binding.mapview.setViewpointCenterAsync(point);
                    return;
                }
                if (list != null && list.size() > 1) {
                    createPolygon(list);
                    return;
                }
                Toast.makeText(mContext, "没有坐标参数", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(mContext, "坐标参数错误:" + e, Toast.LENGTH_SHORT).show();
                Log.e("tag", "坐标参数错误:" + e);
            }

        }
    }

    /**
     * 添加几何标注
     *
     * @param list1
     */
    private void createPolygon(List<Double[]> list1) {
        PointCollection collection = new PointCollection(SpatialReferences.getWgs84());
        for (Double[] d : list1) {
            collection.add(d[0], d[1]);
        }
        Polygon polygon = new Polygon(collection);
        SimpleLineSymbol outlineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID,
                Color.BLUE, 2.0f);
        SimpleFillSymbol symbol = new SimpleFillSymbol(SimpleFillSymbol.Style.DIAGONAL_CROSS,
                Color.GREEN, outlineSymbol);
        graphic = new Graphic(polygon, symbol);
        locaLable();
        graphicsOverlay.getGraphics().add(graphic);
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
                if (fileList == null || fileList.size() <= 0) {
                    Toast.makeText(mContext, "没有图层数据", Toast.LENGTH_SHORT).show();
                    break;
                }
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
                locaLable();
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * 定位标注到屏幕中央位置
     */
    private void locaLable() {
//        if (graphic!=null){
//            Envelope envelope = graphic.getGeometry().getExtent();
//            binding.mapview.setViewpointCenterAsync(envelope.getCenter());
//        }
        if (tiledLayer != null) {
            Envelope envelope = tiledLayer.getFullExtent();
            binding.mapview.setViewpointCenterAsync(envelope.getCenter());
        }
        if (list != null && list.size() > 0) {
            Point center = new Point(list.get(0)[0], list.get(0)[1], SpatialReferences.getWgs84());
            binding.mapview.setViewpointCenterAsync(center, 20376.198251415677);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapBrowseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void permissionDenied() {
        Toast.makeText(mContext, "已拒绝权限，无法读取地图文件，若想使用请开启权限", Toast.LENGTH_LONG).show();
    }
}
