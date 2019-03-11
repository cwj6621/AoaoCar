package com.jianguo.aoaocar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22077 on 2017/12/4.
 */

public class OrderStateAdapter extends BaseAdapter{
    private List<Order> mOrders=new ArrayList<>();
    private Context  mtx;
    private int ORDER_LAYOUT_FIRST =1;
    private int ORDER_LAYOUT_SENCOND=2;
    private LayoutInflater  inflater;

    public OrderStateAdapter( Context  _mtx, List<Order> _mOrders){
        this.mtx=_mtx;
        this.mOrders=_mOrders;
        inflater= (LayoutInflater) mtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mOrders.size();
    }
    @Override
    public Object getItem(int position) {
        return mOrders.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderOrderOne mHolderOrderOne=null;
        HolderOrderTwo mHolderOrderTwo=null;

         int type=getItemViewType(position);
         if(type==ORDER_LAYOUT_FIRST)  {
             if (convertView == null) {
                 convertView =  inflater.inflate(R.layout.row_list_order_detail, null);
                 mHolderOrderOne=new HolderOrderOne();
                 mHolderOrderOne.vCarName=(TextView) convertView.findViewById(R.id.tv_order_car_name);
                 mHolderOrderOne.vCarNumber=(TextView) convertView.findViewById(R.id.tv_order_car_number);
                 mHolderOrderOne.vCostMoney=(TextView) convertView.findViewById(R.id.tv_order_cost_money);
                 mHolderOrderOne.vOrderState=(TextView) convertView.findViewById(R.id.tv_order_state);
                 mHolderOrderOne.vStartTime=(TextView) convertView.findViewById(R.id.tv_order_start_time);
                 mHolderOrderOne.vStartAddress=(TextView) convertView.findViewById(R.id.tv_order_start_address);
                 mHolderOrderOne.vEndTime=(TextView) convertView.findViewById(R.id.tv_order_end_time);
                 mHolderOrderOne.vEndAddress=(TextView) convertView.findViewById(R.id.tv_order_end_address);

                 convertView.setTag(mHolderOrderOne);
             } else {
                 mHolderOrderOne = (HolderOrderOne) convertView.getTag();
             }
             mHolderOrderOne.updataView(position);
             return convertView;
         }else{
             if (convertView == null) {
                 convertView =   inflater.inflate(R.layout.row_list_order_detail_orther, null);
                 mHolderOrderTwo=new HolderOrderTwo();
                 mHolderOrderTwo.vCarName=(TextView) convertView.findViewById(R.id.tv_order_car_name);
                 mHolderOrderTwo.vCarNumber=(TextView) convertView.findViewById(R.id.tv_order_car_number);
                 mHolderOrderTwo.vCostMoney=(TextView) convertView.findViewById(R.id.tv_order_cost_money);
                 mHolderOrderTwo.vOrderState=(TextView) convertView.findViewById(R.id.tv_order_state);
                 mHolderOrderTwo.vStartTime=(TextView) convertView.findViewById(R.id.tv_order_start_time);
                 mHolderOrderTwo.vStartAddress=(TextView) convertView.findViewById(R.id.tv_order_start_address);
                 convertView.setTag(mHolderOrderTwo);
               } else {
                 mHolderOrderTwo = (HolderOrderTwo) convertView.getTag();
               }
             mHolderOrderTwo.updataView(position);
             return convertView;
         }

    }

    @Override
    public int getViewTypeCount() {
        return  2;
    }

    @Override
    public int getItemViewType(int position) {
        Order.OrderState  state= mOrders.get(position).getmOrderState();
         if(state.equals(Order.OrderState.CANCEL)){
             return  ORDER_LAYOUT_SENCOND;
         }else{
             return  ORDER_LAYOUT_FIRST;
         }
    }

    private class HolderOrderOne {
        private TextView vCarName,vCarNumber,vCostMoney,vOrderState,vStartTime,vEndTime,vStartAddress,vEndAddress;
        public void updataView(final int position) {
             Order  mOrder=mOrders.get(position);
            vCarName.setText(mOrder.getCarName());
            vCarNumber.setText(mOrder.getCarNumber());
            vCostMoney.setText("￥"+mOrder.getCostMoney());
            vOrderState.setText(mOrder.getmOrderState().getDesc() );
            vStartTime.setText(mOrder.getStartTime());
            vEndTime.setText(mOrder.getEndTime());
            vStartAddress.setText(mOrder.getStartAddress());
            vEndAddress.setText(mOrder.getEndAddress());
        }
    }
    private class HolderOrderTwo {
        private TextView vCarName,vCarNumber,vCostMoney,vOrderState,vStartTime,vStartAddress;
        public void updataView(final int position) {
            Order  mOrder=mOrders.get(position);
            vCarName.setText(mOrder.getCarName());
            vCarNumber.setText(mOrder.getCarNumber());
            vCostMoney.setText("￥"+mOrder.getCostMoney());
            vOrderState.setText(mOrder.getmOrderState().getDesc() );
            vStartTime.setText(mOrder.getStartTime());
            vStartAddress.setText(mOrder.getStartAddress());
        }
    }
}
