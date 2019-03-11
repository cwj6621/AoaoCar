package com.jianguo.aoaocar.activity.user;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.base.BaseFragment;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.adapter.AtViewPagerAdapter;
import com.jianguo.aoaocar.fragment.message.AllMessageFragment;
import com.jianguo.aoaocar.fragment.message.NetDotMessageFragment;
import com.jianguo.aoaocar.fragment.message.OrderMessageFragment;
import com.jianguo.aoaocar.fragment.message.SystemMessageFragment;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/11/29.
 */

public class MessageCenterActivity extends BaseActivity {
    @Bind(R.id.vp)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    String[] mTitles = new String[]{
            "全部","网点", "订单","系统"
    };

    private ArrayList<Fragment> mFragmentList;
    private AtViewPagerAdapter mViewPagerAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("我的订单");
        setupViewPager();
    }

    private void setupViewPager() {
        mFragmentList = new ArrayList<Fragment>();
        BaseFragment mAllMessage  = new AllMessageFragment();
        BaseFragment mNetDotMessage  = new NetDotMessageFragment();
        BaseFragment mOrderMessage = new OrderMessageFragment();
        BaseFragment mSystemMessage = new SystemMessageFragment();
        mFragmentList.add(mAllMessage );
        mFragmentList.add(mNetDotMessage );
        mFragmentList.add( mOrderMessage);
        mFragmentList.add( mSystemMessage);
        // 第二步：为ViewPager设置适配器
        mViewPagerAdapter = new AtViewPagerAdapter(getSupportFragmentManager(), mFragmentList,mTitles);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        //  第三步：将ViewPager与TableLayout 绑定在一
        tabLayout.setupWithViewPager(mViewPager);
    }
}