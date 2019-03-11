package com.jianguo.aoaocar.activity.driver;

import android.view.View;
import android.widget.AdapterView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.xlistview.XListView;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.DriverOrder;
import com.jianguo.aoaocar.model.User;
import com.jianguo.aoaocar.view.SelectConditionView;
import com.jianguo.aoaocar.view.pop.SelectConditionPop;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
/**
 * Created by 22077 on 2018/2/2.
 */
public class OrderListActivity extends BaseActivity{

    private CommonAdapter<DriverOrder>  mAdapter;
    private List<DriverOrder> mDriverOrders=new ArrayList();

    @Bind(R.id.xlv_driver_list)
    XListView mListView;

    @Bind(R.id.rb_creditor_condition1)
    SelectConditionView  conditionView1;
    @Bind(R.id.rb_creditor_condition2)
    SelectConditionView conditionView2;
    private List<String> mConditions1=new ArrayList<>();
    private List<String> mConditions2=new ArrayList<>();
    private int conditionIndex1=0;
    private int conditionIndex2=0;
    private SelectConditionPop selectConditionPop1=null;
    private SelectConditionPop selectConditionPop2=null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_order_list;
    }
    @Override
    public void initView() {
       setLeftBtnDefaultOnClickListener();
       setTitle("订单");

        initConditionsData();
        setconditionView();
        initListView();
    }
    private void initListView() {
        User mUser=new User(1,"老刘六",null,"男");
        mDriverOrders.add(new DriverOrder(1,null,"0011",10.0f,"2018-01-15","北京东路",mUser));

        mListView.setAdapter(mAdapter=new CommonAdapter<DriverOrder>(this,mDriverOrders,R.layout.row_list_driver_order_list) {
            @Override
            public void convert(ViewHolder holder, DriverOrder item) {
                holder.setText(R.id.tv_driver_name,item.mUser.userName);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Utils.toActivity(OrderListActivity.this,DriverOrderDetailActivity.class);
            }
        });
    }


    private void setconditionView() {
        conditionView1.isSelectCondtion(false);
        conditionView1.setCondtionContext(mConditions1.get(0));
        conditionView2.setCondtionContext(mConditions2.get(0));
        conditionView1.setOnClickListener(new SelectConditionView.OnSelectClickListener() {
            @Override
            public void onOnClick(View v) {
                conditionView1.isSelectCondtion(true);
                selectConditionPop1= new SelectConditionPop(OrderListActivity.this,mConditions1,conditionIndex1, new SelectConditionPop.OnDismissListListener() {
                    @Override
                    public void onnDismissList(int index, boolean option) {
                        conditionView1.isSelectCondtion(false);
                        if(option) {
                            conditionIndex1 = index;
                            conditionView1.setCondtionContext(mConditions1.get(index));
                        }
                    }
                });
                selectConditionPop1.showAsDropDown(conditionView1);
            }
        });

        conditionView2.setOnClickListener(new SelectConditionView.OnSelectClickListener() {
            @Override
            public void onOnClick(View v) {
                conditionView2.isSelectCondtion(true);
                selectConditionPop2= new SelectConditionPop(OrderListActivity.this,mConditions2,conditionIndex2, new SelectConditionPop.OnDismissListListener() {
                    @Override
                    public void onnDismissList(int index, boolean option) {
                        conditionView2.isSelectCondtion(false);
                        if(option) {
                            conditionIndex2 = index;
                            conditionView2.setCondtionContext(mConditions2.get(index));
                        }
                    }
                });
                selectConditionPop2.showAsDropDown(conditionView2);
            }
        });
    }
    private void initConditionsData() {
        mConditions1.add("所有");
        mConditions1.add("送车 ");
        mConditions1.add("取车");

        mConditions2.add("最近");
        mConditions2.add("倒序");
    }
}
