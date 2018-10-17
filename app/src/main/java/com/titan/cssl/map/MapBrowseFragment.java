package com.titan.cssl.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.model.LatLng;
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.data.TileCache;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polygon;
import com.esri.arcgisruntime.geometry.PolygonBuilder;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.BackgroundGrid;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragMapBrowseBinding;
import com.titan.model.LocationInfo;
import com.titan.util.MaterialDialogUtil;
import com.titan.util.ResourcesManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MapBrowseFragment extends Fragment implements MapBrowse {

    private FragMapBrowseBinding binding;
    private MapBrowseViewModel viewModel;
    private String[] reqPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA};
    public static final String ROOT_MAPS = "/maps";
    private static final String otitan_map = "/otitan.map";
    private ArcGISMap arcGISMap;
    private Basemap basemap;
    private TileCache cache;
    private ArcGISTiledLayer tiledLayer;
    private LocationDisplay mLocationDisplay;
    private MaterialDialog dialog;
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
    private Graphic newGraphic;
    private PolylineBuilder mPolylineBuilder;
    private PolygonBuilder mPolygonBuilder;
    private LocationInfo mLocationInfo;

    private static MapBrowseFragment single;

    public static MapBrowseFragment getInstance() {
        if (single == null) {
            single = new MapBrowseFragment();
        }
        return single;
    }

    public void setViewModel(MapBrowseViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragMapBrowseBinding.inflate(inflater, container, false);
        binding.setViewmodel(viewModel);
        initView();
        initMap();
        return binding.getRoot();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initMap() {
        try {
            mLocationInfo = (LocationInfo) getActivity().getIntent().getExtras().get("locationInfo");
//            MapBrowseActivityPermissionsDispatcher.initDataWithCheck(this);
            binding.mapview.setBackgroundGrid(new BackgroundGrid(0xffffff,
                    0xffffff, 0, 3));
            //去除水印
            ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud8065403504,none,RP5X0H4AH7CLJ9HSX018");
            //去除版权声明
            binding.mapview.setAttributionTextVisible(false);
            //创建地图
//            ArcGISTiledLayer tiledLayer = new ArcGISTiledLayer(getActivity().getResources().getString(R.string.img_201606));
//            arcGISMap = new ArcGISMap(new Basemap(tiledLayer));
            arcGISMap = new ArcGISMap(Basemap.createImageryWithLabelsVector());
            arcGISMap.addDoneLoadingListener(new Runnable() {
                @Override
                public void run() {
                    createLable();
//                    locationLable();
                }
            });
            //arcGISMap.setBasemap(basemap);
            binding.mapview.setMap(arcGISMap);
            graphicsOverlay = new GraphicsOverlay();
            binding.mapview.getGraphicsOverlays().add(graphicsOverlay);

            markerSymbol = new PictureMarkerSymbol((BitmapDrawable) ContextCompat
                    .getDrawable(getActivity(), R.drawable.icon_gcoding));
            binding.mapview.setOnTouchListener(new DefaultMapViewOnTouchListener(getActivity(),
                    binding.mapview) {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    try {
                        Point point;
                        point = binding.mapview
                                .screenToLocation(new android.graphics.Point((int) e.getX(), (int) e.getY()));
                        Point point1 = (Point)
                                GeometryEngine.project(point, SpatialReferences.getWgs84());
//                        Graphic graphic = new Graphic(point1, markerSymbol);
                        Log.e("tag", "point:" + point1.getX() + "," + point1.getY() + ","
                                + binding.mapview.getMapScale());
//                        graphicsOverlay.getGraphics().add(graphic);
                    } catch (Exception e1) {
                        Toast.makeText(getActivity(), "map error:" + e1, Toast.LENGTH_SHORT).show();
                        Log.e("tag", "map error:" + e1);
                    }
                    return super.onSingleTapConfirmed(e);
                }
            });

            mLocationDisplay = binding.mapview.getLocationDisplay();
            if (!mLocationDisplay.isStarted()) {
                mLocationDisplay.startAsync();
            }
            mLocationDisplay.addLocationChangedListener(viewModel);
        } catch (Exception e) {
            Log.e("tag", "mapError:" + e);
        }
    }


    @Override
    public void onPause() {
        binding.mapview.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapview.resume();
    }

    private void initView() {
        Toolbar toolbar = binding.mapBrowseToolbar;
        toolbar.setTitle(getActivity().getResources().getString(R.string.map_browse));
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    //定位当前设备位置
    @Override
    public void onLoction(Point point) {
        binding.mapview.setViewpointCenterAsync(point, 20376.198251415677);
    }

    @Override
    public void addPoint() {
        drawPolygon();
    }

    @Override
    public void undo() {
        if (viewModel.points != null && !viewModel.points.isEmpty()) {
            viewModel.points.remove(viewModel.points.size() - 1);
            refreshDraw();
        } else {
            Toast.makeText(getActivity(), "当前没有可撤销的点", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void cancel() {
        if (viewModel.points != null && !viewModel.points.isEmpty()) {
            MaterialDialogUtil.showSureDialog(getActivity(), "当前范围没有保存，确定取消吗?")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            viewModel.isCollection.set(false);
                            viewModel.points = null;
                            graphicsOverlay.getGraphics().remove(newGraphic);
                            dialog.dismiss();
                        }
                    }).build().show();
            return;
        }
        viewModel.isCollection.set(false);
        viewModel.points = null;
        graphicsOverlay.getGraphics().remove(newGraphic);
    }

    @Override
    public void endMeasure() {
        if (viewModel.points == null || viewModel.points.size() == 0) {
            showToast("采集范围不符合要求");
            return;
        }
        viewModel.collectionStatu.set(!viewModel.collectionStatu.get());
        viewModel.save();
    }

    @Override
    public void refreshGraphic() {
        if (viewModel.points != null && viewModel.points.size() == 1) {
            graphic = new Graphic(viewModel.points.get(0), markerSymbol);
        } else if (viewModel.points != null && viewModel.points.size() > 1) {
            graphic = newGraphic;
        }
        graphicsOverlay.getGraphics().clear();
        graphicsOverlay.getGraphics().add(graphic);
    }

    /**
     * 绘制项目边界图斑
     */
    public void drawPolygon() {
        try {
            Point point = binding.mapview.getVisibleArea().getExtent().getCenter();
            if (viewModel.points == null) {
                viewModel.points = new PointCollection(binding.mapview.getSpatialReference());
            }
            viewModel.points.add(point);
            switch (viewModel.points.size()) {
                case 1:
//                    if (newGraphic == null) {
                    newGraphic = new Graphic(viewModel.points.get(0), SymbolUtil.startpoint);
                    graphicsOverlay.getGraphics().add(newGraphic);
//                    }
                    break;
                case 2:
                    graphicsOverlay.getGraphics().remove(newGraphic);
                    mPolylineBuilder = new PolylineBuilder(viewModel.points);
                    newGraphic = new Graphic(mPolylineBuilder.toGeometry(), SymbolUtil.measureline);
                    graphicsOverlay.getGraphics().add(newGraphic);
                    break;
                default:
                    mPolygonBuilder = new PolygonBuilder(viewModel.points);
                    if (mPolygonBuilder.isSketchValid()) {
                        graphicsOverlay.getGraphics().remove(newGraphic);
                        newGraphic = new Graphic(mPolygonBuilder.toGeometry(), SymbolUtil.getFillSymbol());
                        graphicsOverlay.getGraphics().add(newGraphic);
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e("tag", "drawPolygon:" + e);
        }
    }

    //刷新撤销后的绘制范围图斑
    private void refreshDraw() {
        if (viewModel.points != null && !viewModel.points.isEmpty()) {
            switch (viewModel.points.size()) {
                case 1:
                    graphicsOverlay.getGraphics().remove(newGraphic);
                    newGraphic = new Graphic(viewModel.points.get(0), SymbolUtil.startpoint);
                    graphicsOverlay.getGraphics().add(newGraphic);
                    break;
                case 2:
                    mPolylineBuilder = new PolylineBuilder(viewModel.points);
                    graphicsOverlay.getGraphics().remove(newGraphic);
                    newGraphic = new Graphic(mPolylineBuilder.toGeometry(), SymbolUtil.measureline);
                    graphicsOverlay.getGraphics().add(newGraphic);
                    break;
                default:
                    mPolygonBuilder = new PolygonBuilder(viewModel.points);
                    if (mPolygonBuilder.isSketchValid()) {
                        graphicsOverlay.getGraphics().remove(newGraphic);
                        newGraphic = new Graphic(mPolygonBuilder.toGeometry(), SymbolUtil.getFillSymbol());
                        graphicsOverlay.getGraphics().add(newGraphic);
                    }
                    break;
            }
        } else {
            graphicsOverlay.getGraphics().remove(newGraphic);
        }
    }

    /**
     * 创建标注
     */
    private void createLable() {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
//            list = (List<Double[]>) getIntent().getExtras().get("coordinate");
            List<Double[]> list = mLocationInfo.getCoordinateList();
            if (list == null || list.isEmpty()) {
                locationCur();
            } else {
//                locationProj(list);
                if (judgmentRange(list)) {
                    locationProj(list);
                } else {
                    locationCur();
                }
            }
        }
    }

    /**
     * 定位到项目位置
     */
    private void locationProj(List<Double[]> list) {
        try {
            if (list.size() == 1) {
                Point point = new Point(list.get(0)[0], list.get(0)[1], SpatialReferences.getWgs84());
                graphic = new Graphic(point, markerSymbol);
                graphicsOverlay.getGraphics().add(graphic);
                binding.mapview.setViewpointCenterAsync(point, 20376.198251415677);
                return;
            }
            if (list.size() > 1) {
                createPolygon(list);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "坐标参数错误:" + e, Toast.LENGTH_SHORT).show();
            Log.e("tag", "坐标参数错误:" + e);
        }
    }

    /**
     * 定位到当前位置
     */
    private void locationCur() {
//        curPoint = (Double[]) getIntent().getExtras().get("location");
        Double[] curPoint = mLocationInfo.getLocalPoint();
        if (curPoint == null) {
            curPoint = new Double[2];
            Toast.makeText(getActivity(), "获取设备位置失败，已定位到默认位置", Toast.LENGTH_SHORT).show();
            Point point = new Point(112.9820620841, 28.1787790346, SpatialReferences.getWgs84());
            curPoint[0] = 112.9820620841;
            curPoint[1] = 28.1787790346;
            graphicsOverlay.getGraphics().add(new Graphic(point, markerSymbol));
            binding.mapview.setViewpointCenterAsync(point, 20376.198251415677);
            return;
        }
        Point point = new Point(curPoint[0], curPoint[1], SpatialReferences.getWgs84());
//        graphicsOverlay.getGraphics().add(new Graphic(point, markerSymbol));
        binding.mapview.setViewpointCenterAsync(point, 20376.198251415677);
        Toast.makeText(getActivity(), "没有坐标参数，已定位到设备当前位置", Toast.LENGTH_SHORT).show();
    }

    /**
     * 坐标范围判断
     *
     * @param list
     * @return
     */
    private boolean judgmentRange(List<Double[]> list) {
        boolean flag = false;
        for (Double[] doubles : list) {
            if (doubles != null) {
                boolean f = doubles[0] < 109 || doubles[0] > 118 || doubles[1] < 25 || doubles[1] > 30;
                if (!f) {
                    flag = true;
                }
            }
        }
        return flag;
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
                Color.RED, 2.0f);
        SimpleFillSymbol symbol = new SimpleFillSymbol(SimpleFillSymbol.Style.NULL,
                Color.BLUE, outlineSymbol);
        graphic = new Graphic(polygon, symbol);
//        locationLable();
        graphicsOverlay.getGraphics().add(graphic);
        binding.mapview.setViewpointCenterAsync(graphic.getGeometry().getExtent().getCenter(), 20376.198251415677);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_map_browse, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_change:
                showLayerChangeDilog();
                break;
            case R.id.map_location:
//                locationLable();
                createLable();
                break;
            case R.id.map_navigation:
                MapBrowseFragmentPermissionsDispatcher.navigationWithCheck(this);
                break;
            case R.id.proj_collection:
                collectionDialog();
                break;
            default:
                break;
        }
        return true;
    }

    //项目坐标采集提示弹窗
    private void collectionDialog() {
        if (graphic == null) {
            viewModel.isCollection.set(true);
        } else {
//                    showToast("当前项目范围已存在");
            new MaterialDialog.Builder(getActivity())
                    .positiveText("确定")
                    .negativeText("取消")
                    .content("当前项目范围已存在，是否确定重新采集")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            viewModel.isCollection.set(true);
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    //图层切换弹窗
    private void showLayerChangeDilog() {
        String[] strs = {"201606", "201612", "201701", "原始影像图"};
        List<String> serverList = new ArrayList<>(Arrays.asList(strs));
//                if (fileList == null || fileList.size() <= 0) {
//                    Toast.makeText(getActivity(), "没有图层数据", Toast.LENGTH_SHORT).show();
//                    break;
//                }
//                List<String> stringList = new ArrayList<>();
//                for (File file : list) {
//                    stringList.add(file.getName());
//                }
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("图层切换")
                .negativeText("取消")
                .items(serverList)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        try {
                            switch (text.toString()) {
                                case "201606":
                                    ArcGISTiledLayer arcGISTiledLayer1 = new ArcGISTiledLayer(getActivity().getResources().getString(R.string.img_201606));
//                                    arcGISMap = new ArcGISMap(new Basemap(arcGISTiledLayer1));
                                    arcGISMap.setBasemap(new Basemap(arcGISTiledLayer1));
                                    break;
                                case "201612":
                                    ArcGISTiledLayer arcGISTiledLayer2 = new ArcGISTiledLayer(getActivity().getResources().getString(R.string.img_201612));
                                    arcGISMap = new ArcGISMap(new Basemap(arcGISTiledLayer2));
                                    break;
                                case "201701":
                                    ArcGISTiledLayer arcGISTiledLayer3 = new ArcGISTiledLayer(getActivity().getResources().getString(R.string.img_201701));
                                    arcGISMap = new ArcGISMap(new Basemap(arcGISTiledLayer3));
                                    break;
                                case "原始影像图":
                                    arcGISMap = new ArcGISMap(Basemap.createImageryWithLabelsVector());
                                    break;
                            }
                            binding.mapview.setMap(arcGISMap);
                            createLable();
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
    }

    //导航
    @Override
    public void navigate() {
        MapBrowseFragmentPermissionsDispatcher.navigationWithCheck(this);
    }

    //项目定位
    @Override
    public void projLocation() {
        createLable();
    }

    //图层切换
    @Override
    public void layerChange() {
        showLayerChangeDilog();
    }

    //坐标采集
    @Override
    public void collection() {
        collectionDialog();
    }

    /**
     * 项目导航
     */
    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA})
    void navigation() {
        Double[] point = mLocationInfo.getLocalPoint();
        List<Double[]> list = mLocationInfo.getCoordinateList();
        if (!judgmentRange(list)) {
//        if (list.isEmpty()) {
            Toast.makeText(getActivity(), "当前项目坐标有误，无法导航", Toast.LENGTH_SHORT).show();
            return;
        }
        if (point == null || point.length == 0) {
            Toast.makeText(getActivity(), "无法定位当前位置，不能进行导航", Toast.LENGTH_SHORT).show();
            return;
        }
        LatLng[] latLngs = new LatLng[]{new LatLng(point[1], point[0]), new LatLng(list.get(0)[1], list.get(0)[0])};
        BaiduNavigation.getInstance(getActivity()).initNavigator(getActivity(), "开始", "结束", latLngs);
    }

    /**
     * 定位标注到屏幕中央位置
     */
    private void locationLable() {
//        if (graphic!=null){
//            Envelope envelope = graphic.getGeometry().getExtent();
//            binding.mapview.setViewpointCenterAsync(envelope.getCenter());
//        }
        Point point = new Point(112.9820620841, 28.1787790346, SpatialReferences.getWgs84());
        Double[] curPoint = mLocationInfo.getLocalPoint();
        List<Double[]> list = mLocationInfo.getCoordinateList();
        if (tiledLayer != null) {
            Envelope envelope = tiledLayer.getFullExtent();
            binding.mapview.setViewpointCenterAsync(envelope.getCenter(), 20376.198251415677);
        }
        boolean flag = true;
        if (list != null && list.size() > 0) {
            for (Double[] p : list) {
                flag = fliterPoint(p[0], p[1]);
                if (!flag) {
                    point = new Point(p[0], p[1], SpatialReferences.getWgs84());
                    break;
                }
            }
        }
        if (flag && curPoint != null && curPoint.length > 0) {
            point = new Point(curPoint[0], curPoint[1], SpatialReferences.getWgs84());
        }
        binding.mapview.setViewpointCenterAsync(point, 20376.198251415677);
    }

    private boolean fliterPoint(double lat, double lon) {
        return lat < 25 || lat > 30 || lon < 109 || lon > 118;
    }


    @Override
    public void showProgress() {
        dialog = MaterialDialogUtil.showLoadProgress(getActivity(), getActivity().getString(R.string.updata)).build();
        dialog.show();
    }

    @Override
    public void stopProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void showToast(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapBrowseFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void permissionDenied() {
        Toast.makeText(getActivity(), "已拒绝权限，无法读取地图文件，若想使用请开启权限", Toast.LENGTH_LONG).show();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA})
    void permissionDeniedNavi() {
        Toast.makeText(getActivity(), "已拒绝权限，无法进行导航，若想使用请开启权限", Toast.LENGTH_LONG).show();
    }

}
