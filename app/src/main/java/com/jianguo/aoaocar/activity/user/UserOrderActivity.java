package com.jianguo.aoaocar.activity.user;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.base.BaseFragment;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.adapter.AtViewPagerAdapter;
import com.jianguo.aoaocar.fragment.order.AllOrderFragment;
import com.jianguo.aoaocar.fragment.order.HandedOrderFragment;
import com.jianguo.aoaocar.fragment.order.HandlingOrderFragment;

import java.util.ArrayList;

import butterknife.Bind;

import static com.baidu.location.b.g.R;

/**
 * Created by 22077 on 2017/11/23.
 */

public class UserOrderActivity extends BaseActivity{

    @Bind(R.id.vp)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    String[] mTitles = new String[]{
            "全部", "未完成","已完成"
    };
    private ArrayList<Fragment> mFragmentList;
    private AtViewPagerAdapter mViewPagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_order;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("我的订单");

        setupViewPager();
    }

    private void setupViewPager() {
        mFragmentList = new ArrayList<Fragment>();
        BaseFragment mAllOrder  = new AllOrderFragment();
        BaseFragment mHandlingOrder  = new HandlingOrderFragment();
        BaseFragment mHandedOrder = new HandedOrderFragment();

        mFragmentList.add(mAllOrder );
        mFragmentList.add(mHandlingOrder );
        mFragmentList.add( mHandedOrder);
        // 第二步：为ViewPager设置适配器
        mViewPagerAdapter = new AtViewPagerAdapter(getSupportFragmentManager(), mFragmentList,mTitles);
        mViewPager.setAdapter(mViewPagerAdapter);
        //  第三步：将ViewPager与TableLayout 绑定在一
        mViewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(mViewPager);
    }
}
