package com.jianguo.aoaocar.activity.driver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.it.jianguo.common.Utils.NavigationUtils;
import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.commonutils.ToastUitl;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.view.RoundImageView;
import com.jianguo.aoaocar.view.pop.NavigationPop;

import java.util.List;

import butterknife.Bind;
import overlayutil.DrivingRouteOverlay;

/**
 * Created by 22077 on 2018/1/23.
 */
public class DriverOrderDetailActivity extends BaseActivity implements View.OnClickListener, OnGetRoutePlanResultListener {
    @Bind(R.id.btn_rob)
    Button  btnRob;
    @Bind(R.id.ll_naviget)
    LinearLayout llNaviget;
    @Bind(R.id.ll_center)
    RelativeLayout llCenter;
    @Bind(R.id.mv_order_detail)
    MapView mMapView;
    @Bind(R.id.iv_contact_service)
    ImageView ivContact;
    @Bind(R.id.tv_driver_order_cost)
    TextView orderCost;
    @Bind(R.id.riv_driver_head)
    RoundImageView rivHead;
    private BaiduMap baiduMap;
    private double latitude;
    private double longitude;

    DrivingRouteResult nowResultdrive = null;
    // 搜索相关
     RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用

    int nodeIndex = -1; // 节点索引,供浏览节点时使用

    boolean hasShownDialogue = false;

    RouteLine route = null;
    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_order_detail;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("订单详情");
        SDKInitializer.initialize(this);

        initMapView();
        btnRob.setOnClickListener(this);
        ivContact.setOnClickListener(this);
        llNaviget.setOnClickListener(this);
        llCenter.setOnClickListener(this);
    }

    private void initMapView() {
        baiduMap=mMapView.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mMapView.showZoomControls(false);//隐藏缩放按钮
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);

        latitude = Double.parseDouble(PreUtils.getString(this,"Latitude","31.2461092170"));
        longitude = Double.parseDouble(PreUtils.getString(this,"Longitude","121.4897215516"));
//        LatLng point = new LatLng(latitude, longitude);
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(20)
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(0).latitude(point.latitude)
//                .longitude(point.longitude).build();
//        baiduMap.setMyLocationData(locData);
//
//        MapStatus   ms = new MapStatus.Builder().target(point).zoom(18.0f).build();
//        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
//
//        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        // 设置起终点信息，对于tranist search 来说，城市名无意义
        PlanNode stNode =PlanNode.withCityNameAndPlaceName("上海", "上海南站");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("上海", "东方明珠");

        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode).to(enNode));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_naviget:
                List<NavigationUtils.Navigtion> mNavigtions = NavigationUtils.getNavigatMapName(this);
                if (mNavigtions != null) {
                    new NavigationPop(this, mNavigtions);
                } else {
                    ToastUitl.showShort(this, "您手机还没有装导航应用");
                }
                break;
            case R.id.ll_center:
                //  Utils.toActivity(DriverOrderDetailActivity.this,PartnersActivity.class);
                break;
            case R.id.btn_rob:
              //  Utils.toActivity(DriverOrderDetailActivity.this,PartnersActivity.class);
                break;
            case R.id.iv_contact_service:
                OnTakePhone();
                break;
        }
    }
    /**
     * 联系客服
     */

    public void OnTakePhone() {
        requestPermission(Constant.RC_PHONE_PERM, new String[]{Manifest.permission.CALL_PHONE},
                getString(R.string.rationale_call), new PermissionCallBackM() {
                    @Override
                    public void onPermissionGrantedM(int requestCode, String... perms) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "021-61906273"));
                        if (ActivityCompat.checkSelfPermission(DriverOrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        DriverOrderDetailActivity.this.startActivity(intent);
                    }
                    @Override public void onPermissionDeniedM(int requestCode, String... perms) {
                         showShortToast("您没有电话权限，去设置里授权");
                        return;
                    }
                });
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(DriverOrderDetailActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
         if (result.getRouteLines().size() >= 1) {
                route = result.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);

                baiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();

            } else {
                Log.d("route result", "结果数<0");
                return;
            }

        }
    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {

                return BitmapDescriptorFactory.fromResource(R.mipmap.iocn_sta);

        }

        @Override
        public BitmapDescriptor getTerminalMarker() {

                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_end);

        }
    }
}
