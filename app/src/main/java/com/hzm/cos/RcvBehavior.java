package com.hzm.cos;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by hzm on 2018/3/6 in CQ.
 * Desc:
 */

public class RcvBehavior extends CoordinatorLayout.Behavior<View>  {
    public static final String TAG = "RcvBehavior";
    CoordinatorLayout coordinatorLayout;
    View child;
    public void setListener(OnRcvListener listener) {
        mListener = listener;
    }
    OnRcvListener mListener;

    Context ctx;
    public RcvBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if(params!=null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT){
            View view=parent.getChildAt(1);
            view.setTranslationY(getStatusDivHeight());
            child.layout(0,0,parent.getWidth(),parent.getHeight());
            child.setTranslationY(getStatusDivHeight()+getHeaderHeight());
        }
        this.coordinatorLayout=parent;
        this.child=child;
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

        // 在这个方法里面只处理向上滑动
        Log.i(TAG,"onNestedPreScroll dy:"+dy);
        if(dy < 0){
            return;
        }
        View view=coordinatorLayout.getChildAt(1);
        float delta=view.getTranslationY()-dy*1/2;
        if (delta >0) {
            Log.i(TAG,"onNestedPreScroll delta:"+delta);
            view.setTranslationY(view.getTranslationY()-dy/2);
            child.setTranslationY(child.getTranslationY()-dy*1/2);
            consumed[1] = dy;
        }else if (delta<=0){ //以child的顶部滚动到getStatusDivHeight位置为判断dialog顶部是否继续滚动
            view.setTranslationY(0);
            if (mListener!=null) mListener.onTop();
            if (child.getTranslationY()>0) {
                Log.i(TAG,"onNestedPreScroll child.getTranslationY():"+child.getTranslationY());
                child.setTranslationY(child.getTranslationY() - dy);
                consumed[1] = dy;
            }else {
                Log.i(TAG,"onNestedPreScroll scrollBy:"+dy);
                child.setTranslationY(0);
                child.scrollBy(0,dy);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        // 在这个方法里只处理向下滑动
        if(dyUnconsumed >=0){
            return;
        }
        Log.i(TAG,"onNestedScroll =====dyUnconsumed:"+dyUnconsumed);
        View view=coordinatorLayout.getChildAt(1);
        if (child.getTranslationY()-dyUnconsumed<getStatusDivHeight()){ //依赖的view向下滑动完成了，只有滑动child的view了
            Log.i(TAG,"onNestedScroll child.getTranslationY():"+child.getTranslationY()+"::getStatusDivHeight():"+getStatusDivHeight());
            child.setTranslationY(child.getTranslationY() - dyUnconsumed);
        }else { //依赖的view向下滑动未完成，child和依赖的view各消耗一半的下滑
            Log.i(TAG,"onNestedScroll dyUnconsumed/2:"+dyUnconsumed/2);
            if (mListener!=null) mListener.onLeaveTop();
            float dy=view.getTranslationY()-dyUnconsumed/2+getHeaderHeight();
            view.setTranslationY(view.getTranslationY()-dyUnconsumed/2);
            child.setTranslationY(dy);//注意这儿的写法   请思考为何不用 child.getTranslationY()-dyUnconsumed/2
        }
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }


    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        handleLastMove(false);
    }

    public void handleLastMove(boolean flag){
        View view=coordinatorLayout.getChildAt(1);
        if (view.getTranslationY()>getStatusDivHeight()*2||flag){
            ObjectAnimator object1= ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), child.getMeasuredHeight());
            ObjectAnimator object2=ObjectAnimator.ofFloat(child, "translationY", child.getTranslationY(), child.getMeasuredHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(object1,object2);
            animatorSet.setDuration(500);
            animatorSet.setInterpolator(new AccelerateInterpolator());
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mListener!=null) mListener.onFinsh();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.start();
        }
    }


    public int getHeaderHeight(){ //dialog的顶部部分的高度
        return ctx.getResources().getDimensionPixelOffset(R.dimen.bar_height);
    }

    public int getStatusDivHeight(){ //空白处到状态栏的距离
        return ctx.getResources().getDimensionPixelOffset(R.dimen.status_div);
    }

}
