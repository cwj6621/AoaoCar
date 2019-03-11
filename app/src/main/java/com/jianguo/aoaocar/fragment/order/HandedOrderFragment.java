package com.jianguo.aoaocar.fragment.order;

import android.view.View;
import android.widget.AdapterView;

import com.it.jianguo.common.base.BaseFragment;
import com.it.jianguo.common.xlistview.XListView;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.adapter.OrderStateAdapter;
import com.jianguo.aoaocar.model.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.baidu.location.b.g.R;

/**
 * Created by 22077 on 2017/11/23.
 */

public class HandedOrderFragment extends BaseFragment {
    private List<Order> mOrders=new ArrayList<>();
    private OrderStateAdapter mAdapter;

    @Bind(R.id.lv_listview)
    XListView mXListView;
    @Override
    protected void fetchData() {
     //  initData();
        setNotDataAndClickIntent(R.mipmap.icon_not_order,"很伤心 您还没有订单哦","",null);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_order;
    }
    @Override
    protected void initView() {

        initListView();
    }

    private void initListView() {

        mXListView.setAdapter(mAdapter=new OrderStateAdapter(mActivity,mOrders));
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
    private void initData() {
        Order mOrder=new Order();
        mOrder.setCarName("奇瑞X5");
        mOrder.setCarNumber("沪AD6599");
        mOrder.setCostMoney(100.3f);
        mOrder.setmOrderState(Order.OrderState.COMPLETE);
        mOrder.setStartTime("2017-12-01");
        mOrder.setEndTime("2017-12-01");
        mOrder.setStartAddress("中南海");
        mOrder.setEndAddress("北戴河");
        mOrders.add(mOrder);
    }
}
