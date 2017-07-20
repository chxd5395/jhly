package com.jhly.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by r on 2017/7/20.
 */

public class InfoPageAdapter extends PagerAdapter {
    private List<View> list;

    public InfoPageAdapter(List<View> list) {
        this.list = list;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        if (list != null){
//            return viewList.size();
            //第一处修改，设置轮播最大值，等于无限循环
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化position位置的界面
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //第二处修改，当前要显示的数据索引为集合长度
        int newPosition = position % list.size();
        container.addView(list.get(newPosition));
        return list.get(newPosition);

//        container.addView(viewList.get(position));
//        return viewList.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        //第三处修改，移除的索引为集合的长度
        int newPosition = position % list.size();
        container.removeView(list.get(newPosition));

//        container.removeView(viewList.get(position));
    }
}

