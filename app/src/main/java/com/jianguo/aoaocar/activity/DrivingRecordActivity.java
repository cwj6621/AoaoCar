package com.jianguo.aoaocar.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.fragment.drivingrecord.RefundCarFragment;
import com.jianguo.aoaocar.fragment.drivingrecord.DrivingCarFragment;
import com.jianguo.aoaocar.fragment.drivingrecord.EvaluationFragemnt;
import com.jianguo.aoaocar.fragment.drivingrecord.PayCarFragment;
import com.jianguo.aoaocar.fragment.drivingrecord.ReservationFragment;
import com.jianguo.aoaocar.fragment.drivingrecord.TakeCarFragment;
import com.jianguo.aoaocar.fragment.drivingrecord.base.BaseDrivingFragment;
import com.jianguo.aoaocar.fragment.drivingrecord.base.LoanClickListener;
import com.jianguo.aoaocar.view.MagicProgressBar;
import com.jianguo.aoaocar.view.TextStepView;
import butterknife.Bind;
/**
 * Created by 22077 on 2018/1/4.
 */
public class DrivingRecordActivity extends BaseActivity{

    @Bind(R.id.tv_step_view)
    TextStepView mStepView;
    @Bind(R.id.ban_numberbar)
    MagicProgressBar stepviewBar;

    private Class[] fragmentClsArray = new Class[]{ReservationFragment.class,TakeCarFragment.class,
                                DrivingCarFragment.class,RefundCarFragment.class,PayCarFragment.class,
                                                            EvaluationFragemnt.class};
    private Fragment[] fragArray = new Fragment[fragmentClsArray.length];
    private int lastIndex=0;
    private int addIndex=0;
    private float percent=0.15f;
    @Bind(R.id.map_view)
    MapView mapView;
    private BaiduMap baiduMap;
    private CommonAdapter<String> mStepAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_driving_record;
    }
    @Override
    public void initView() {
        SDKInitializer.initialize(this);
        initMap();
        setStepViews();
        selectFragment(0);
    }

    private void selectFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragArray[index] == null) {
            fragArray[index] = Fragment.instantiate(this, fragmentClsArray[index].getName());
            transaction.add(R.id.fl_driving_record, fragArray[index]);
            setLoanClick(index);
        }
        transaction.hide(fragArray[lastIndex]);
        transaction.show(fragArray[index]);
        transaction.commit();
        lastIndex = index;
    }

    private void setLoanClick(int index) {
        if (fragArray[index] instanceof BaseDrivingFragment) {
            ((BaseDrivingFragment) fragArray[index]).setLoanClickListener(new LoanClickListener() {
                @Override
                public void loanClick(boolean isAdd,int index,float percent) {
                    if(index>0){
                        selectFragment(index);
                    }
                    if(isAdd){
                        addIndex++;
                        mStepView.setProstep(addIndex);
                    }
                    stepviewBar.setSmoothPercent(percent,200);
                }
            });
        }
    }
    private void  initMap() {

        baiduMap = mapView.getMap();
        //普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mapView.showZoomControls(false);//隐藏缩放按钮
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //发起检索请求
        Double  latitude = Double.parseDouble(PreUtils.getString(this,"Latitude","31.2461092170"));
        Double    longitude = Double.parseDouble(PreUtils.getString(this,"Longitude","121.4897215516"));
        LatLng point = new LatLng(latitude, longitude);

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(30)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(point.latitude)
                .longitude(point.longitude).build();
        baiduMap.setMyLocationData(locData);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(point).zoom(18.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mapView != null) {
            mapView.onDestroy();
        }
        // 退出时销毁定位
    }
    private void setStepViews() {
        mStepView.setProstep(1);
        stepviewBar.setSmoothPercent(percent,200);
       }
    }
