package com.hzm.cos;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hzm on 2018/3/3 in CQ.
 * Desc:
 */

public class AlphaBehavior extends CoordinatorLayout.Behavior<View> {
    Context ctx;
    public AlphaBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        if (dependency instanceof NestedScrollView){
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float alpha=dependency.getTranslationY()/getHeaderHeight();
        child.setAlpha(alpha);
        Log.i("AlphaBehavior","alpha: "+alpha);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    public int getHeaderHeight(){
        return ctx.getResources().getDimensionPixelOffset(R.dimen.header_height);
    }
}
