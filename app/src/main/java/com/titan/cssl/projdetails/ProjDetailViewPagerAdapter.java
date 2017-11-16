package com.titan.cssl.projdetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyw on 2017/11/2/002.
 * 三个主页面的适配器
 */

public class ProjDetailViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList = new ArrayList<>();//页面列表

    public ProjDetailViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = getmList(list);
    }

    private List<Fragment> getmList(List<Fragment> list){
        List<Fragment> list1 = new ArrayList<>();
        for (Fragment fragment:list){
            if (fragment!=null){
                list1.add(fragment);
            }
        }
        return list1;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    /*销毁Fragment页面*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //注释掉这一行以后就会防止Fragment被销毁，否则根据viewPager默认的缓存机制只缓存当前页面的左右相邻的两个页面，
        //当滑动到第三页时就会默认销毁第一页，注释掉之后就不会销毁了
        super.destroyItem(container, position, object);
    }
}
