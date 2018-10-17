package com.titan.cssl.map;

import android.content.Context;
import android.graphics.Color;

import com.esri.arcgisruntime.symbology.MarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleFillSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.TextSymbol;

/**
 * Created by whs on 2017/6/30
 */

public class SymbolUtil {
    private Context mContext;

    /**
     * 态势标绘样式
     */
    public static MarkerSymbol firepoint;
    /**
     * 测量起点样式
     */
    public static MarkerSymbol startpoint = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 5);
    /**
     * 测量线样式
     */
    public static SimpleLineSymbol measureline = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 3);

    //节点样式
    public static MarkerSymbol vertexSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.GREEN, 5);

    public SymbolUtil(Context mContext) {
        this.mContext = mContext;
    }

    //轨迹线样式
    public static SimpleLineSymbol getLineSymbol() {
        SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.parseColor("#1266E6"), 8);
        lineSymbol.setAntiAlias(true);
        return lineSymbol;
    }

    //项目范围样式
    public static SimpleFillSymbol getFillSymbol() {
        return new SimpleFillSymbol(SimpleFillSymbol.Style.NULL, Color.BLUE, measureline);
    }

    //新增面
    public static SimpleFillSymbol getNewFillSymbol() {
        return new SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, Color.RED, null);
    }

}
