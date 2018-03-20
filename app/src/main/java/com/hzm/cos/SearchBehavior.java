package com.hzm.cos;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hzm on 2018/3/4 in CQ.
 * Desc:
 */

public class SearchBehavior  extends CoordinatorLayout.Behavior<View> {

    Context ctx;
    public SearchBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
    }

    public int getSearchHeight(){
        return ctx.getResources().getDimensionPixelOffset(R.dimen.edit_search_height);
    }

    public int getHeaderHeight(){
        return ctx.getResources().getDimensionPixelOffset(R.dimen.header_height);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (child instanceof NestedScrollView && params.height== CoordinatorLayout.LayoutParams.MATCH_PARENT){
            child.layout(0,0,parent.getWidth(),parent.getHeight());
            child.setTranslationY(getHeaderHeight());
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {

         if (nestedScrollAxes== ViewCompat.SCROLL_AXIS_VERTICAL){
             return true;
         }
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        //只做上滑动的处理
        Log.i("hzm","onNestedPreScroll dy:"+dy);
        if (dy<0) return;
        View view=coordinatorLayout.getChildAt(0);//就是那个imageview
        float delta=child.getTranslationY()-dy*3/4;
        if (delta > getSearchHeight()) { //此处是以child是否滚动到搜索框的底部为顶部的判断标准
            view.setTranslationY(view.getTranslationY()-dy/4);
            child.setTranslationY(delta);
            consumed[1] = dy;
        }else if (delta<=getSearchHeight()){ //滚动到搜索框的底部了即滑到顶部了
            view.setTranslationY(-(getHeaderHeight()-getSearchHeight()));
            if (delta>=0) {
                child.setTranslationY(child.getTranslationY()-dy); //为了让NestedScrollView显示完全，需要加上这个哟
            } else {
                child.setTranslationY(0);
                child.scrollBy(0,dy);
            }
            consumed[1] = dy;
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.e("hzm","onNestedScroll dyUnconsumed:"+dyUnconsumed+"dyConsumed:"+dyConsumed);

        if (dyUnconsumed >0) return;
         //只做下滑的处理
        View view=coordinatorLayout.getChildAt(0);
         if (view.getTranslationY()-dyUnconsumed>=0){ //依赖的view向下滑动完成了，只有滑动child的view了
            view.setTranslationY(0);
            if (child.getTranslationY()-dyUnconsumed>=getHeaderHeight()){//判断child是否滑动到底
                child.setTranslationY(getHeaderHeight());
            }else {
                child.setTranslationY(child.getTranslationY() - dyUnconsumed);
            }
        }else { //依赖的view向下滑动未完成，child和依赖的view各消耗一半的下滑
                view.setTranslationY(view.getTranslationY()-dyUnconsumed/2);
                child.setTranslationY(child.getTranslationY()-dyUnconsumed/2);
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,dyUnconsumed);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }



}
