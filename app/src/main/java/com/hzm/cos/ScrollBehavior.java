package com.hzm.cos;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hzm on 2018/3/2 in CQ.
 * Desc:
 */

public class ScrollBehavior  extends CoordinatorLayout.Behavior<View>  {
    public static final String TAG = "ScrollBehavior";
    Context ctx;
    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if(params!=null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT){
            child.layout(0,0,parent.getWidth(),parent.getHeight());
            child.setTranslationY(getHeaderHeight());
            return true;
        }
        return false;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        // 在这个方法里面只处理向上滑动
        Log.i(TAG,"onNestedPreScroll dy:"+dy);
        if(dy < 0){
            return;
        }

        float transY =  child.getTranslationY() - dy;
        Log.i(TAG,"onNestedPreScroll transY:"+transY+"++++child.getTranslationY():"+child.getTranslationY()+"---->dy:"+dy);
        if(transY > 0){
            child.setTranslationY(transY);
            consumed[1]= dy;
        }else if (transY<=0){
            child.setTranslationY(0);
            consumed[1]= dy;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        // 在这个方法里只处理向下滑动
        Log.i(TAG,"onNestedScroll dyUnconsumed:"+dyUnconsumed);
        if(dyUnconsumed >0){
            return;
        }

        float transY = child.getTranslationY() - dyUnconsumed;
        Log.i(TAG,"------>onNestedScroll transY:"+transY+"****** child.getTranslationY():"+child.getTranslationY()+"--->dyUnconsumed"+dxUnconsumed);
        if(transY > 0 && transY < getHeaderHeight()){
            child.setTranslationY(transY);
        }
        if (transY>0  && transY >=getHeaderHeight()){
            child.setTranslationY(getHeaderHeight());
        }
    }


    public int getHeaderHeight(){
        return ctx.getResources().getDimensionPixelOffset(R.dimen.header_height);
    }

}
