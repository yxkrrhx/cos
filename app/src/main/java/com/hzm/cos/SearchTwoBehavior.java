package com.hzm.cos;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hzm on 2018/3/4 in CQ.
 * Desc:
 */

public class SearchTwoBehavior  extends CoordinatorLayout.Behavior<View>  {

    Context ctx;
    public SearchTwoBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        child.bringToFront();
        return false;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency.getId()== R.id.nested_scroll_view) return true;
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        float  pgroess=(dependency.getTranslationY()-getSearchHeight())/(getHeaderHeight()-getSearchHeight())*getSearchDefaulrTransY();
        if (pgroess>0) {
            child.setTranslationY(pgroess);
            float scaleX = 1.0f - 0.2f * (pgroess / getSearchDefaulrTransY());
            child.setScaleX(scaleX);
        }else {
            child.setTranslationY(0);
            child.setScaleX(1.0f);
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }


    public int getHeaderHeight(){
        return ctx.getResources().getDimensionPixelOffset(R.dimen.header_height);
    }
    public int getSearchHeight(){
        return ctx.getResources().getDimensionPixelOffset(R.dimen.edit_search_height);
    }
    public int getSearchDefaulrTransY(){
        return ctx.getResources().getDimensionPixelOffset(R.dimen.edit_default_transy);
    }
}
