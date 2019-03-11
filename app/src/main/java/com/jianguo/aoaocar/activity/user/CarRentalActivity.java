package com.jianguo.aoaocar.activity.user;

import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.base.BaseFragment;
import com.it.jianguo.common.commonwidget.ViewPagerFixed;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.adapter.AtViewPagerAdapter;
import com.jianguo.aoaocar.fragment.order.AllOrderFragment;
import com.jianguo.aoaocar.fragment.order.HandedOrderFragment;
import com.jianguo.aoaocar.fragment.order.HandlingOrderFragment;
import com.jianguo.aoaocar.fragment.rental.CarRentNextFragment;
import com.jianguo.aoaocar.fragment.rental.CarRentOneFragment;

import java.util.ArrayList;

import butterknife.Bind;

import static com.jianguo.aoaocar.R.id.tabLayout;

/**
 * Created by 22077 on 2017/12/11.
 */

public class CarRentalActivity extends BaseActivity   {
    @Bind(R.id.vp_car_rental)
    ViewPagerFixed vp;
    private ArrayList<Fragment> mFragmentList;
    private AtViewPagerAdapter mViewPagerAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.acticity_car_rental;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("我的出租");

        setupViewPager();
        //Android7.0拍照
        if (Build.VERSION.SDK_INT >=24) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }

    private void setupViewPager() {
        mFragmentList = new ArrayList<Fragment>();
        BaseFragment mCarRentOne  = new CarRentOneFragment(new CarRentOneFragment.OnCarRentalSubmit() {
            @Override
            public void CarRentalSubmit() {
                vp.setCurrentItem(1);
            }
        });
        BaseFragment mCarRentNext  = new CarRentNextFragment();

        mFragmentList.add(mCarRentOne );
        mFragmentList.add(mCarRentNext );

        // 第二步：为ViewPager设置适配器
        mViewPagerAdapter = new AtViewPagerAdapter(getSupportFragmentManager(), mFragmentList,null);
        vp.setAdapter(mViewPagerAdapter);
        vp.setCurrentItem(0);
        vp.setNoScroll(true);
    }
}