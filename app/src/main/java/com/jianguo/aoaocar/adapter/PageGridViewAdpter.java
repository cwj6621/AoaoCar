package com.jianguo.aoaocar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.Car;
import com.jianguo.aoaocar.view.ImageBorderView;

import java.util.List;

/**
 * Created by 22077 on 2017/11/23.
 */

public class PageGridViewAdpter extends BaseAdapter {

    private Context context;
    private List<Car> lists;//数据源
    private int mIndex; // 页数下标，标示第几页，从0开始
    private int mPargerSize;// 每页显示的最大的数量
    private int mSelectIndex=0;
    public PageGridViewAdpter(Context context, List<Car> lists,
                              int mIndex, int mPargerSize ) {
        this.context = context;
        this.lists = lists;
        this.mIndex = mIndex;
        this.mPargerSize = mPargerSize;

    }

    /**
     * 先判断数据及的大小是否显示满本页lists.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量lists的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个
     */
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lists.size() > (mIndex + 1) * mPargerSize ?
                mPargerSize : (lists.size() - mIndex*mPargerSize);
    }

    @Override
    public Car getItem(int arg0) {
        // TODO Auto-generated method stub
        return lists.get(arg0 + mIndex * mPargerSize);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0 + mIndex * mPargerSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.row_grrd_car_item, null);
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_car_name);

            holder.iv_nul = (ImageBorderView)convertView.findViewById(R.id.iv_car_icon);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        //重新确定position因为拿到的总是数据源，数据源是分页加载到每页的GridView上的
        final int pos = position + mIndex * mPargerSize;//假设mPageSiez
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
        holder.tv_name.setText(lists.get(pos).carName );
        holder.iv_nul.setImageResource(lists.get(pos).icon);
//                if(pos==mSelectIndex){
//                    holder.iv_nul.setBorderWidth(Color.parseColor("#4CAF50"));
//                }else{
//                    holder.iv_nul.setBorderWidth(Color.TRANSPARENT);
//                }
        holder.iv_nul.setBorderWidth(Color.TRANSPARENT);
        //添加item监听
       convertView.setOnClickListener(new View.OnClickListener() {

            @Override
           public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mSelectIndex=pos;
                Toast.makeText(context, lists.get(pos).carName, Toast.LENGTH_SHORT).show();
                PageGridViewAdpter.this.notifyDataSetChanged();
            }
         });
        return convertView;
    }
    static class ViewHolder{
        private TextView tv_name;
        private ImageBorderView iv_nul;

    }
}
