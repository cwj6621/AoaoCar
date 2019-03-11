package com.jianguo.aoaocar.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.it.jianguo.common.theme.Theme;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.view.CircleIndicator;
import com.jianguo.aoaocar.view.WelcomeView;
import com.jianguo.aoaocar.view.animation.RotateDownPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.baidu.location.b.g.R;

/**
 * Created by 22077 on 2018/1/12.
 */
public class WecomeActivity extends BaseActivity  {

    @Bind(R.id.indicator)
    CircleIndicator circleIndicator;
    @Bind(R.id.viewpager)
    ViewPager vpOnboarderPager;
    private List<View> viewList;
    @Override
    public int getLayoutId() {
        onPreCreate(Theme.No);
        setTranslucentStatus();
        return R.layout.activity_wecome;
    }

    @Override
    public void initView() {
        initData();
        vpOnboarderPager.setAdapter(pagerAdapter);
        circleIndicator.setViewPager(vpOnboarderPager);
        vpOnboarderPager.setPageTransformer(true,new RotateDownPageTransformer());
    }
    private void initData() {
        viewList = new ArrayList<View>();
        View view1=new WelcomeView(this,R.mipmap.icon_welcome1,"畅快T+1","绿色出行安全快捷",false);
        View view2=new WelcomeView(this,R.mipmap.icon_welcome2,"畅快T+1","绿色出行安全快捷",false);
        View view3=new WelcomeView(this,R.mipmap.icon_welcome3,"推广增优惠券","进入应用",true);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public int getCount() {
            return viewList.size();
        }
        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return "title";
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    };

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().finishAllActivity();
        super.onBackPressed();
    }
}
