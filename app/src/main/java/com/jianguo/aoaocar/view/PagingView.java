package com.jianguo.aoaocar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.adapter.MyViewPagerAdapter;
import com.jianguo.aoaocar.adapter.PageGridViewAdpter;
import com.jianguo.aoaocar.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22077 on 2018/1/16.
 * viewPager分页
 */

public class PagingView  extends LinearLayout{
    private  ViewGroup mGroup;
    private  ViewPager mAdvPager;
    private   Context mContext;

    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 9; //每页显示的最大的数量
    private int mSelectIndex =0; //选中位置
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    private PageGridViewAdpter myGridViewAdpter;//GridView作为一个View对象添加到ViewPager集合中


    public PagingView(Context context) {
        super(context);
        mContext = context;
    }

    public PagingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.widget_cycle_view, this);
        mAdvPager = (ViewPager) findViewById(R.id.viewpager);

        // 滚动图片右下指示器视
        mGroup = (ViewGroup) findViewById(R.id.points);

    }

    /**
     * 装填图片数据
     */
    public void setImageResources(List<Car> mCarList ) {
        // 清除
        mGroup.removeAllViews();
        // 图片广告数量

        // TODO Auto-generated method stub
        //总的页数向上取整
        totalPage = (int) Math.ceil(mCarList.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for(int i = 0; i < totalPage; i++){
            //每个页面都是inflate出一个新实例
            final GridView gridView = (GridView)View.inflate(mContext, R.layout.gridview_car_item, null);
            gridView.setAdapter(myGridViewAdpter=new PageGridViewAdpter(mContext, mCarList, i, mPageSize));
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        mAdvPager.setAdapter(new MyViewPagerAdapter(viewPagerList));
        //添加小圆点
        ivPoints = new ImageView[totalPage];
        for(int i = 0; i < totalPage; i++){
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(mContext);
            if(i==0){
                ivPoints[i].setImageResource(R.drawable.banner_show_check_bg);
            }else {
                ivPoints[i].setImageResource(R.drawable.banner_show_defauit_bg);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            mGroup.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        mAdvPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
                                               @Override
                                               public void onPageSelected(int position) {
                                                   for(int i=0 ; i < totalPage; i++){
                                                       if(i == position){
                                                           ivPoints[i].setImageResource(R.drawable.banner_show_check_bg);
                                                       }else {
                                                           ivPoints[i].setImageResource(R.drawable.banner_show_defauit_bg);
                                                       }
                                                   }
                                               }
                                           }
                  );
               }

}
