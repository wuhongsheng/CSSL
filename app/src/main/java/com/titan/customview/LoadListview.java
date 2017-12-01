package com.titan.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.titan.cssl.R;

/**
 * Created by hanyw on 2017/11/20/020.
 * 自定义listview，实现上拉加载
 */

public class LoadListview extends ListView implements AbsListView.OnScrollListener {
    private View mFootView;
    private int mTotalItemCount;//item总数
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mIsLoading = false;//是否正在加载
    private boolean isadd = false;

    public LoadListview(Context context) {
        super(context);
        init(context);
    }

    public LoadListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mFootView = LayoutInflater.from(context).inflate(R.layout.item_foot_view, null);
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

        int lastPosition = absListView.getLastVisiblePosition();
        if (!mIsLoading && i == OnScrollListener.SCROLL_STATE_IDLE && lastPosition == mTotalItemCount - 1) {
            mIsLoading = true;
//            addFooterView(mFootView);
            if (mLoadMoreListener != null) {
                mLoadMoreListener.onloadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        mTotalItemCount = i2;
    }

    public void setLoadCompleted(boolean state) {
        if (state) {
            mIsLoading = false;
            removeFooterView(mFootView);
        } else {
            if (!isadd){
                isadd = true;
                mIsLoading = true;
                addFooterView(mFootView);
            }
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onloadMore();
    }

}
