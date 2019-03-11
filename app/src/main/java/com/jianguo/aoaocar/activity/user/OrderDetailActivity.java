package com.jianguo.aoaocar.activity.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.model.OrderRecord;
import com.jianguo.aoaocar.view.CrilcleRecordView;
import com.jianguo.aoaocar.view.pop.OrderRecordPop;
import com.jianguo.aoaocar.view.pop.RentCarFreePop;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
/**
 * Created by 22077 on 2017/12/4.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_contact_service)
    TextView contact_service;
    @Bind(R.id.ll_order_record)
    LinearLayout order_record;
    @Bind(R.id.mv_route)
    MapView mapView;
    @Bind(R.id.iv_order_back)
    ImageView back;
    @Bind(R.id.cricleview)
    CrilcleRecordView mCrilcleView;

    private BaiduMap baiduMap;
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
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }
    @Override
    public void initView() {
        SDKInitializer.initialize(this);
        contact_service.setOnClickListener(this);
        order_record.setOnClickListener(this);
        mCrilcleView.setOnClickListener(this);
        back.setOnClickListener(this);
        initMap();
    }
    private void initMap() {
        // 初始化全局 bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory
                .fromResource(R.mipmap.iocn_sta);
        BitmapDescriptor bdB = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_end);

            baiduMap = mapView.getMap();
            //普通地图
            baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            mapView.showZoomControls(false);//隐藏缩放按钮
            //发起检索请求

        LatLng llA = new LatLng(31.1527460817,121.2736534700);
        LatLng llB = new LatLng(30.2469304146,120.1915275175);

        OverlayOptions option = new MarkerOptions()
                .position(llA)
                .icon(bdA).draggable(true).animateType(MarkerOptions.MarkerAnimateType.drop);
        baiduMap.addOverlay(option);

        OverlayOptions option2 = new MarkerOptions()
                .position(llB)
                .icon(bdB)
                .draggable(true).animateType(MarkerOptions.MarkerAnimateType.drop);  //设置手势拖拽
        baiduMap.addOverlay(option2);
        LatLngBounds bounds = new LatLngBounds.Builder().include(llA)
                .include(llB).build();

        //构建折线点坐标
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(llA);
        points.add(llB);

        //绘制折线
//        OverlayOptions ooPolyline = new PolylineOptions().width(8)
//                .color( ).points(points);
//        baiduMap.addOverlay(ooPolyline);

        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(9).build()));//设置缩放级别
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(bounds.getCenter());
        baiduMap.animateMapStatus(u);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.tv_contact_service:
                takePhone();
                break;
            case R.id.cricleview:
                RentCarFreePop pop=new RentCarFreePop(this);
                Utils.showAsDropDown(pop,order_record);
                break;
            case R.id.ll_order_record:
                List<OrderRecord> mOrderRecord = new ArrayList<>();
                mOrderRecord.add(new OrderRecord(6,"无违章",null));
                mOrderRecord.add(new OrderRecord(5,"已支付","2017-12-2 17:42"));
                mOrderRecord.add(new OrderRecord(4,"还车成功","2017-12-2 12:30"));
                mOrderRecord.add(new OrderRecord(3,"取车","2017-12-1 16:30"));
                mOrderRecord.add(new OrderRecord(2,"已预约","2017-12-1 14:12"));
                mOrderRecord.add(new OrderRecord(1,"提交订单","2017-12-1 12:02"));
                OrderRecordPop opo=new OrderRecordPop(this,mOrderRecord);
                Utils.showAsDropDown(opo,order_record);
                break;
        }
    }
    /**
     * 拨打电话
     */
    private void takePhone() {
        requestPermission(Constant.RC_PHONE_PERM, new String[]{Manifest.permission.CALL_PHONE},
                getString(R.string.rationale_call), new PermissionCallBackM() {
                    @Override
                    public void onPermissionGrantedM(int requestCode, String... perms) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "021-61906273"));
                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        OrderDetailActivity.this.startActivity(intent);
                    }
                    @Override public void onPermissionDeniedM(int requestCode, String... perms) {
                           showShortToast("您没有电话权限，去设置里授权");
                        return;
                    }
                });
    }
}
