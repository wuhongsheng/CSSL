package com.titan.cssl.statistics;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.titan.cssl.R;
import com.titan.cssl.databinding.FragStatisticsBinding;
import com.titan.cssl.projsearch.ProjSearchActivity;
import com.titan.util.MaterialDialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/12/7/007.
 * 数据统计页面
 */

public class StatisticsFragment extends Fragment implements Statistics {

    private Context mContext;
    private StatisticsViewModel viewModel;
    private FragStatisticsBinding binding;
    private PieChart chartClass;
    private MaterialDialog dialog;
    private BarChart chartTianbao;

    public static StatisticsFragment getInstance() {
        return new StatisticsFragment();
    }

    public void setViewModel(StatisticsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_statistics, container, false);
        initData();
        initPieChart();
//        initBarChart();
//        setData();
        viewModel.getData();
        return binding.getRoot();
    }

    private void initData() {

    }

    /**
     * 初始化饼状图
     */
    private void initPieChart() {
        chartClass = binding.statisticsPieChart;
        chartClass.setUsePercentValues(true);
        chartClass.getDescription().setEnabled(false);//描述
        chartClass.setExtraOffsets(5, 10, 5, 5);
        chartClass.setDrawEntryLabels(false);//设置隐藏饼图上文字，只显示百分比
        chartClass.setDrawHoleEnabled(false);//是否显示内部圆形，当该条件设为true时，对内部圆形的设置才能起作用
        chartClass.setTransparentCircleColor(getResources().getColor(R.color.blue));
        chartClass.setTransparentCircleAlpha(110);
        chartClass.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("tag", "e:" + e.toString() + ",h:" + h.getX());
                Intent intent = new Intent(mContext, ProjSearchActivity.class);
                intent.putExtra("type", (int) h.getX() + 1);
                mContext.startActivity(intent);
            }

            @Override
            public void onNothingSelected() {
                Log.e("tag", "nothing");
            }
        });
        chartClass.setHoleRadius(45f); //半径
//        chartClass.setHoleRadius(0);  //实心圆
        chartClass.setTransparentCircleRadius(48f);// 半透明圈
        chartClass.setDrawCenterText(true);//饼状图中间可以添加文字
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        chartClass.setNoDataText(getResources().getString(R.string.nodata));
        chartClass.setUsePercentValues(true);//设置显示成比例
//        chartClass.setCenterText("各类型占比");
        chartClass.setRotationAngle(0); // 初始旋转角度
        //enable rotation of the chart by touch
        chartClass.setRotationEnabled(true); // 可以手动旋转
        chartClass.setHighlightPerTapEnabled(true);
        chartClass.animateY(1000, Easing.EasingOption.EaseInOutQuad); //设置动画
        Legend mLegend = chartClass.getLegend();  //设置比例图
//        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);  //左下边显示
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//水平方向靠右
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);//竖直方向居中
        mLegend.setOrientation(Legend.LegendOrientation.VERTICAL);//比例图竖直排列
        mLegend.setFormSize(12f);//比例块字体大小
        mLegend.setXEntrySpace(2f);//设置距离饼图的距离，防止与饼图重合
        mLegend.setYEntrySpace(2f);
        //设置比例块换行...
        mLegend.setWordWrapEnabled(true);
        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        mLegend.setTextColor(getResources().getColor(R.color.blue));
        mLegend.setForm(Legend.LegendForm.SQUARE);//设置比例块形状，默认为方块
        //mLegend.setEnabled(false);//设置禁用比例块
    }

    public void initBarChart() {
        chartTianbao = binding.statisticsBarChart;
        chartTianbao.getDescription().setEnabled(false);
        chartTianbao.setDrawGridBackground(false);
        chartTianbao.setDrawBarShadow(false);
        chartTianbao.setDrawValueAboveBar(true);
        chartTianbao.setHighlightFullBarEnabled(false);
        chartTianbao.setScaleXEnabled(false);//X轴缩放
        chartTianbao.setScaleYEnabled(false);//Y轴缩放
        chartTianbao.setDragEnabled(false);//是否可以拖拽
        chartTianbao.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("tag", "e.x:" + Math.ceil(e.getX() / 3f) + ",h:" + h.getX());
                if (e.getY() > 0) {
                    Intent intent = new Intent(mContext, ProjSearchActivity.class);
                    intent.putExtra("type", (int) Math.ceil(e.getX() / 3f));
                    intent.putExtra("statu", viewModel.getProjStatu(e));
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        YAxis leftAxis = chartTianbao.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        chartTianbao.getAxisRight().setEnabled(false);

        XAxis xLabels = chartTianbao.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setDrawGridLines(false);
        xLabels.setGranularity(1f);
        xLabels.setLabelRotationAngle(-75);
        xLabels.setTextSize(12f);
        xLabels.setCenterAxisLabels(false);
        final List<String> name = viewModel.subName.get();
        xLabels.setLabelCount(name.size());
        xLabels.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return name.get((int) value % name.size());
            }

//            @Override
//            public int getDecimalDigits() {
//                return 0;
//            }
        });

        Legend l = chartTianbao.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(15f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        chartTianbao.animateXY(2500, 2500);

        setChartData();
    }

    /**
     * 设置柱状图数据
     */
    public void setChartData() {
        List<Float> valueList = viewModel.subScale.get();
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        ArrayList<BarEntry> yVals2 = new ArrayList<>();
        ArrayList<BarEntry> yVals3 = new ArrayList<>();
        for (int i = 0; i < valueList.size() / 3; i++) {
            yVals1.add(new BarEntry(i, valueList.get(i)));
            yVals2.add(new BarEntry(i + 3, valueList.get(i + 3)));
            yVals3.add(new BarEntry(i + 6, valueList.get(i + 6)));
        }
        BarDataSet set141 = new BarDataSet(yVals1, "3万㎡以下");
        set141.setColor(Color.rgb(104, 241, 175));
        BarDataSet set142 = new BarDataSet(yVals2, "3-8万㎡");
        set142.setColor(Color.rgb(164, 228, 251));
        BarDataSet set143 = new BarDataSet(yVals3, "8万㎡以上");
        set142.setColor(Color.rgb(164, 150, 251));

        BarData data1 = new BarData(set141, set142, set143);
        data1.setValueFormatter(new DefaultValueFormatter(0));//bar上的数值样式
        chartTianbao.setData(data1);
        data1.setValueTextSize(10f);
        set141.setVisible(true);
        set142.setVisible(true);
    }

    /**
     * 设置饼图的数据
     */
    public void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        List<String> name = viewModel.name.get();
        List<Float> scale = viewModel.scale.get();
        for (int i = 0; i < name.size(); i++) {
            entries.add(new PieEntry(scale.get(i), name.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        /*for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);*/

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(getResources().getColor(R.color.white));
        chartClass.setData(data);
        // undo all highlights
        chartClass.highlightValues(null);
        chartClass.invalidate();
    }


    @Override
    public void showToast(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        dialog = MaterialDialogUtil.showLoadProgress(mContext, mContext.getString(R.string.loading)).build();
        dialog.show();
    }

    @Override
    public void stopProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
//        binding.searchRefresh.setRefreshing(false);
    }
}
