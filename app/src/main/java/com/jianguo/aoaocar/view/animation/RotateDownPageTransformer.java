package com.jianguo.aoaocar.view.animation;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.nineoldandroids.view.ViewHelper;
/**
 * Created by 22077 on 2018/1/12.
 * viewpager 扇形动画滑动效果
 */
public class RotateDownPageTransformer implements ViewPager.PageTransformer {
    private static final float ROT_MAX = 80.0f;
    private float mRot;
    @Override
    public void transformPage(View view, float position) {
        if (position < -1) { // [-Infinity,-1)  
            // This page is way off-screen to the left.  
            ViewHelper.setRotation(view, 0);
        } else if (position <= 1) {// a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0  { // [-1,1]  
            // Modify the default slide transition to shrink the page as well  
            if (position < 0) {
                mRot = (ROT_MAX * position);
                ViewHelper.setPivotX(view, view.getMeasuredWidth() * .5f);
                ViewHelper.setPivotY(view, view.getMeasuredHeight());
                ViewHelper.setRotation(view, mRot);
            }else {
                mRot = (ROT_MAX * position);
                ViewHelper.setPivotX(view, view.getMeasuredWidth()*0.5f);
                ViewHelper.setPivotY(view, view.getMeasuredHeight());
                ViewHelper.setRotation(view,mRot);
            }
            // Scale the page down (between MIN_SCALE and 1)  
            // Fade the page relative to its size.  
        } else { // (1,+Infinity]  
            // This page is way off-screen to the right.  
            ViewHelper.setRotation(view, 0);
        }
    }

}
