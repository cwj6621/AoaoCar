package com.jianguo.aoaocar.fragment.order;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseFragment;
import com.it.jianguo.common.xlistview.XListView;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.user.OrderDetailActivity;
import com.jianguo.aoaocar.adapter.OrderStateAdapter;
import com.jianguo.aoaocar.model.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.baidu.location.b.g.R;

/**
 * Created by 22077 on 2017/11/23.
 */

public class AllOrderFragment extends BaseFragment {

    private List<Order> mOrders=new ArrayList<>();
    private OrderStateAdapter mAdapter;

    @Bind(R.id.lv_listview)
    XListView mXListView;
    @Override
    protected void fetchData() {
  setNotDataViewClose(true);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_user_order;
    }
    @Override
    protected void initView() {

        initListView();
        initData();
    }

    private void initListView() {
        mXListView.setAdapter(mAdapter=new OrderStateAdapter(mActivity,mOrders));
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order mOrder=mOrders.get(position-1);
                if(!mOrder.getmOrderState().equals(Order.OrderState.CANCEL)){
                    Intent intent=new Intent(mActivity,OrderDetailActivity.class) ;
                    intent.putExtra("order",mOrder);
                    Utils.toActivity(mActivity,intent);
                }
            }
        });
    }

    private void initData() {
        Order  mOrder=new Order();
        mOrder.setCarName("奇瑞X5");
        mOrder.setCarNumber("沪AD6599");
        mOrder.setCostMoney(100.3f);
        mOrder.setmOrderState(Order.OrderState.COMPLETE);
        mOrder.setStartTime("2017-12-01");
        mOrder.setEndTime("2017-12-01");
        mOrder.setStartAddress("中南海");
        mOrder.setEndAddress("北戴河");
        mOrders.add(mOrder);

        Order  mOrder2=new Order();
        mOrder2.setCarName("奇瑞X5");
        mOrder2.setCarNumber("沪AD6599");
        mOrder2.setCostMoney(100.3f);
        mOrder2.setmOrderState(Order.OrderState.CANCEL);
        mOrder2.setStartTime("2017-12-01");
        mOrder2.setStartAddress("中南海");
        mOrders.add(mOrder2);

        mAdapter.notifyDataSetChanged();
    }
}
