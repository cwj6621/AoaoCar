package com.jianguo.aoaocar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.it.jianguo.common.Utils.NavigationUtils;
import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.it.jianguo.common.commonutils.ToastUitl;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.it.jianguo.common.retrofit_rx.listener.HttpOnNextListener;
import com.it.jianguo.common.retrofit_rx.resulte.BaseResultEntity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.driver.SendCarActivity;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.model.Car;
import com.jianguo.aoaocar.model.PotCar;
import com.jianguo.aoaocar.model.SwitBdItem;
import com.jianguo.aoaocar.netHttp.api.GetCarShopSApi;
import com.jianguo.aoaocar.view.HorizontalGridView;
import com.jianguo.aoaocar.view.MapCenterPotion;
import com.jianguo.aoaocar.view.PagingView;
import com.jianguo.aoaocar.view.animation.EasyFlipView;
import com.jianguo.aoaocar.view.pop.ConventSuccessPop;
import com.jianguo.aoaocar.view.pop.HeadDialog;
import com.jianguo.aoaocar.view.pop.NavigationPop;
import com.jianguo.aoaocar.view.silderbottompannel.SlideBottomPanel;
import com.jianguo.timedialog.TimePickerDialog;
import com.jianguo.timedialog.data.Type;
import com.jianguo.timedialog.listener.OnDateSetListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import mapapi.clusterutil.DistanceUtils;
import mapapi.clusterutil.clustering.ClusterManager;
public class MainActivity extends BaseActivity implements ConventSuccessPop.OnTakePhonetener, View.OnClickListener  ,BaiduMap.OnMapLoadedCallback {
    @Bind(R.id.id_drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.ll_leftBtn)
    RelativeLayout leftBtn;
    @Bind(R.id.ll_rightBtn)
    RelativeLayout rightBtn;

    @Bind(R.id.rl_title)
    RelativeLayout rl_title;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.gv_car_name)
    HorizontalGridView gvCars;
    private CommonAdapter<Car> mCarAdapter;
    private List<Car> mCars = new ArrayList<>();
    private int carIndex = 5;

    @Bind(R.id.rl_show_car)
    RelativeLayout showCar;

    @Bind(R.id.pag_view)
    PagingView mPagingView;

    @Bind(R.id.view_car_show)
    View view_car_show;
    @Bind(R.id.view_car_type)
    View view_car_type;

    @Bind(R.id.iv_close)
    ImageView  car_type_close;

    @Bind(R.id.sliding)
    SlidingDrawer  sd;
    @Bind(R.id.lv_arrow)
    ImageView  lv_arrow;
    @Bind(R.id.allApps)
    LinearLayout  sweep_code;
    @Bind(R.id.rl_open_car)
    RelativeLayout  open_car;

    @Bind(R.id.map_view)
    MapView mapView;
    @Bind(R.id.bt_mapCenter)
    MapCenterPotion mapCenter;
    private BaiduMap baiduMap;
    private Marker marker;
    /**
     * 附近网点信息
     */
    @Bind(R.id.view_near_pot)
    LinearLayout mViewNearPot;
    @Bind(R.id.lv_car_list)
    LinearLayout mViewCarList;
    @Bind(R.id.lv_car)
    ListView mListView;
    @Bind(R.id.rl_sbp_head)
    RelativeLayout sbpHead;
    @Bind(R.id.rv_navigation)
    LinearLayout navigatView;
    @Bind(R.id.bt_check_car)
    Button checkCar;
    @Bind(R.id.rl_pot_cost)
    RelativeLayout potCost;

    @Bind(R.id.easyFlipView)
    EasyFlipView easyFlipView;
    @Bind(R.id.sbv)
    SlideBottomPanel msbv;
    @Bind(R.id.rv_car_detail)
    LinearLayout mCarPopView;
    @Bind(R.id.iv_to_font)
    ImageView toFont;

    @Bind(R.id.rl_pot_notice)
    RelativeLayout  pot_notice;

    private CommonAdapter<PotCar> mAdapter;
    private List<PotCar> mPotCars=new ArrayList<>();
    private boolean isShowList=true;
    //百度地图点聚合
    private ClusterManager<SwitBdItem> mClusterManager;
    private TimePickerDialog mDialogAll;
    private MapStatus ms;
    private LatLng point;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void initView() {

         SDKInitializer.initialize(this);

         initMap();
        //左滑动菜单栏
         initHeadView();
        //下滑动扫码栏
         initSlidingDrawer();
        //左滑动菜单栏
         initDrawerLayout();
        //表头车型选择
         setGrivCarView();

         initData();

         setOnClick();
    }
    private void initHeadView() {
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toActivity(MainActivity.this, SearchActivity.class);

            }
        });
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT, true);
            }
        });
        rl_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HeadDialog(MainActivity.this, true, new HeadDialog.OnClickItemListener() {
                    @Override
                    public void OnClickItem(int indxe) {
                        switch (indxe) {
                            case 0:
                                break;
                            case 1:
                                Utils.toLoginActivity(MainActivity.this,TravelShortRentActivity.class);
                                break;
                            case 2:
                                Utils.toLoginActivity(MainActivity.this,SendCarActivity.class);
                                break;
                        }
                    }
                });
            }
        });
        ConventSuccessPop.setOnTakePhonetener(this);
    }

    private void initSlidingDrawer() {
      sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
          @Override
          public void onDrawerClosed() {
           //   setAnimator();
              lv_arrow.setBackgroundResource(R.mipmap.icon_two_up_arrow);
           }
      });
        sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
           public void onDrawerOpened() {
               // setAnimator();
                lv_arrow.setBackgroundResource(R.mipmap.icon_two_down_arrow);
           }
      });
        open_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission(Constant.RC_CAMERA_PERM, new String[] { Manifest.permission.CAMERA },
                        getString(R.string.rationale_camera), new PermissionCallBackM() {
                            @Override public void onPermissionGrantedM(int requestCode, String... perms) {
                                Utils.toLoginActivity(MainActivity.this,ZxingStartActivity.class);
                            }
                            @Override public void onPermissionDeniedM(int requestCode, String... perms) {
                                  showShortToast("您没有相机权限，去设置里授权");
                                return;
                            }
                        });
                 }
        });
        sweep_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sd.close();
            }
        });
    }

    private void setOnClick() {
        showCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_car_show.setVisibility(View.GONE);
                view_car_type.setVisibility(View.VISIBLE);
                sd.close();
            }
        });
        car_type_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_car_type.setVisibility(View.GONE);
                view_car_show.setVisibility(View.VISIBLE);

            }
        });
        pot_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTimeDialog();
                mDialogAll.show(getSupportFragmentManager(), "month_day_hour_min");
            }
        });
    }

    private void setGrivCarView() {
        mCars.add(new Car(1, "奇瑞X1"));
        mCars.add(new Car(2, "奇瑞X3"));
        mCars.add(new Car(3, "奇瑞X5"));
        mCars.add(new Car(4, "奇瑞V5"));
        mCars.add(new Car(5, "奇瑞E3"));
        mCars.add(new Car(6, "奇瑞E5"));
        mCars.add(new Car(7, "奇瑞X7"));

        gvCars.setAdapter(mCarAdapter = new CommonAdapter<Car>(this, mCars, R.layout.row_grid_car_detail) {
            @Override
            public void convert(ViewHolder holder, Car item) {
                TextView tv = holder.getView(R.id.tv_car_name);
                if (carIndex == holder.getPosition()) {
                    tv.setTextColor(mContext.getResources().getColor(R.color.black));
                } else {
                    tv.setTextColor(mContext.getResources().getColor(R.color.colorSecondText));
                }
                tv.setText(item.carName);
            }
        });

        gvCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                carIndex = position;
                gvCars.setSelection(carIndex);
                mCarAdapter.notifyDataSetChanged();
            }
        });

    }
    private void initDrawerLayout() {

        drawerLayout.setScrimColor(ContextCompat.getColor(this, R.color.draw_gray));
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });
    }

      private void initData() {
             List<Car>   mCarList=new ArrayList<>();
             mCarList.add(new Car(1, "奇瑞X1", R.mipmap.icon_car_detail));
             mCarList.add(new Car(2, "奇瑞X3", R.mipmap.icon_car_detail));
             mCarList.add(new Car(3, "奇瑞X5", R.mipmap.icon_car_detail));
             mCarList.add(new Car(4, "奇瑞QQ", R.mipmap.icon_car_detail));
             mCarList.add(new Car(5, "奇瑞QQ2", R.mipmap.icon_car_detail));
             mCarList.add(new Car(6, "奇瑞V5", R.mipmap.icon_car_detail));
             mCarList.add(new Car(7, "奇瑞E3", R.mipmap.icon_car_detail));
             mCarList.add(new Car(8, "奇瑞E5", R.mipmap.icon_car_detail));
             mCarList.add(new Car(9, "奇瑞X7", R.mipmap.icon_car_detail));
             mCarList.add(new Car(2, "奇瑞X3", R.mipmap.icon_car_detail));
             mCarList.add(new Car(3, "奇瑞X5", R.mipmap.icon_car_detail));
             mCarList.add(new Car(6, "奇瑞V5", R.mipmap.icon_car_detail));
             mCarList.add(new Car(7, "奇瑞E3", R.mipmap.icon_car_detail));
             mCarList.add(new Car(8, "奇瑞E5", R.mipmap.icon_car_detail));
            mPagingView.setImageResources(mCarList);

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
         point = new LatLng(latitude, longitude);

        MyLocationData  locData = new MyLocationData.Builder()
                .accuracy(30)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(point.latitude)
                .longitude(point.longitude).build();
        baiduMap.setMyLocationData(locData);

        ms = new MapStatus.Builder().target(point).zoom(10.0f).build();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

        baiduMap.setOnMapLoadedCallback(this);
        /**
         * 附近网点信息学
         */
        mapCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // NearCardetailPop opo=new NearCardetailPop(MainActivity.this );
              //  Utils.showAsDropDown(opo,mapCenter);
               // Utils.toActivity(MainActivity.this,AllBranchesActivity.class);
                NearCardetail();
                Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_in_bottom);
                sd.setVisibility(View.GONE);
                mViewNearPot.startAnimation(animation);
                mViewNearPot.setVisibility(View.VISIBLE);
            }
        });

        mClusterManager = new ClusterManager<SwitBdItem>(this, baiduMap);
        // 添加Marker点
    //    addMarkers();
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        baiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        baiduMap.setOnMarkerClickListener(mClusterManager);


        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<SwitBdItem>() {
            @Override
            public boolean onClusterItemClick(SwitBdItem item) {
                NearCardetail();
                Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_in_bottom);
                sd.setVisibility(View.GONE);
                mViewNearPot.startAnimation(animation);
                mViewNearPot.setVisibility(View.VISIBLE);
                return false;
            }
        });
        /**
         *
         * 地图改变监听
         */
        mClusterManager.seOnClusterMapStatusChangeListener(new ClusterManager.OnClusterMapStatusChangeListener() {
            @Override
            public void onMapStatusChange(MapStatus mapStatus, boolean isProgress) {
                mapCenter.setProgressBar(isProgress);
            }
        });
        /**
         * 获取附近
         */
        getCarShops(point);
    }
    @Override
    public void onMapLoaded() {
       mapCenter.setProgressBar(false);
       ms = new MapStatus.Builder().zoom(18.0f).build();
       baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }
    /**
     * 向地图添加Marker点
     */
    public void addMarkers() {
        // 添加Marker点

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
    private long exitTime;
    @Override
    public void onBackPressed() {
        if(mViewNearPot.getVisibility()==View.VISIBLE){ //网点信息页面
             closeButtomPannel();
        } else if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().finishAllActivity();
            super.onBackPressed();
        }
    }
    /**
     * 联系客服
     */
    @Override
    public void OnTakePhone() {
        requestPermission(Constant.RC_PHONE_PERM, new String[]{Manifest.permission.CALL_PHONE},
                getString(R.string.rationale_call), new PermissionCallBackM() {
                    @Override
                    public void onPermissionGrantedM(int requestCode, String... perms) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "021-61906273"));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        MainActivity.this.startActivity(intent);
                    }
                    @Override public void onPermissionDeniedM(int requestCode, String... perms) {
                        showShortToast("您没有电话权限，去设置里授权");
                        return;
                    }
                });
    }

    @Override
    public void OnCloseDimss() {
        closeButtomPannel();
    }

  private void closeButtomPannel(){
      Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.slide_out_bottom);
       mViewNearPot.startAnimation(animation);
       if(msbv.isPanelShowing()){
           msbv.hidePanel();
      }
       mViewNearPot.setVisibility(View.GONE);
        sd.setVisibility(View.VISIBLE);
       if( easyFlipView.isBackSide()){
           easyFlipView.flipTheView();
      }
}
    //附近网点信息
    private void NearCardetail() {
        sbpHead.setOnClickListener(this);
        toFont.setOnClickListener(this);
        navigatView.setOnClickListener(this);
        checkCar.setOnClickListener(this);
        potCost.setOnClickListener(this);

        easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyView, EasyFlipView.FlipState newCurrentSide) {
              //  msbv.displayPanel();
            }
        });
        easyFlipView.setFlipDuration(1000);
        easyFlipView.setFlipEnabled(true);

        msbv.setExternalView(mViewCarList,230);
        initCarDatas();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_sbp_head://
                msbv.displayPanel();
                if (msbv.isPanelShowing()) {
                    if (isShowList) {
                        msbv.startAnima(320);
                    } else {
                        msbv.startAnima(0);
                    }
                    isShowList = !isShowList;
                }
                break;
            case R.id.iv_to_font://反转
                msbv.displayPanel();
                mCarPopView.setVisibility(View.INVISIBLE);
                easyFlipView.flipTheView();
                break;
            case R.id.bt_check_car://约车
                new ConventSuccessPop(this);
                break;
            case R.id.rv_navigation://导航
                List<NavigationUtils.Navigtion> mNavigtions = NavigationUtils.getNavigatMapName(this);
                if (mNavigtions != null) {
                    new NavigationPop(this, mNavigtions);
                } else {
                    ToastUitl.showShort(this, "您手机还没有装导航应用");
                }
                break;
            case R.id.rl_pot_cost://导航
                Utils.toActivity(this, CostInstrutionActivity.class);
                break;
        }
    }
        private void initCarDatas() {
            mPotCars.add(new PotCar(0,"一汽大众",R.mipmap.icon_car_detail,"沪A2324BA",80,50));
            mPotCars.add(new PotCar(0,"一汽大众",R.mipmap.icon_car_detail,"沪A2324BA",0,50));
            mPotCars.add(new PotCar(0,"一汽大众",R.mipmap.icon_car_detail,"沪A2324BA",100,50));
            mPotCars.add(new PotCar(0,"一汽大众",R.mipmap.icon_car_detail,"沪A2324BA",50,50));
            mPotCars.add(new PotCar(0,"一汽大众",R.mipmap.icon_car_detail,"沪A2324BA",20,50));
            mListView.setAdapter(mAdapter=new CommonAdapter<PotCar>(this,mPotCars,R.layout.row_list_pot_car_detail) {
                @Override
                public void convert(ViewHolder holder, PotCar item) {
                    ProgressBar  pBar=holder.getView(R.id.progressBar);
                    pBar.setProgress(item.electricity);
                    TextView  car_electricity=holder.getView(R.id.tv_pot_car_electricity);
                    if(item.electricity==0){
                        car_electricity.setBackgroundResource(R.drawable.bg_electricity_gray_border);
                        car_electricity.setTextColor(Color.GRAY);
                        car_electricity.setText("未充电");
                    }else if(item.electricity==100){
                        car_electricity.setBackgroundResource(R.color.colorGreenPrimary);
                        car_electricity.setTextColor(Color.WHITE);
                        car_electricity.setText("已充满");
                    }else{
                        car_electricity.setBackgroundResource(R.drawable.bg_electricity_green_border);
                        car_electricity.setTextColor(Color.parseColor("#4CAF50"));
                        car_electricity.setText("充电中");
                    }
                    holder.setText(R.id.tv_pot_car_name,item.name).
                            setText(R.id.tv_pot_car_driving,item.driving+"km").
                            setText(R.id.tv_pot_car_license_number,item.licenseNumber);
                }
            });
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(msbv.isPanelShowing()){
                        if(isShowList) {
                            mCarPopView.setVisibility(View.VISIBLE);
                            easyFlipView.flipTheView();
                        }else{
                            msbv.startAnima(0);
                            isShowList=!isShowList;
                        }
                    }else{
                        msbv.displayPanel();
                    }
                }
            });
        }

    private void initTimeDialog() {
         long oneDay = 1*1000 * 60 * 60 * 24L;
          mDialogAll = new TimePickerDialog.Builder( )
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

                    }
                })
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + oneDay)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.colorOrangePrimary))
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colorOrangePrimary))
                .setWheelItemTextSize(13)
                .build();
    }


    //获取  附近网点d

    private  void  getCarShops(final LatLng point){
        manager.doHttpDeal(new GetCarShopSApi(this,point), new HttpOnNextListener(this) {
            @Override
            protected void succeed(BaseResultEntity baseResultEntity, String method) throws JSONException {
                if(baseResultEntity.getDataArray()!=null){
                    List<SwitBdItem> items = new ArrayList<SwitBdItem>();
                    for (int i=0;i<baseResultEntity.getDataArray().length();i++){
                        JSONObject option=baseResultEntity.getDataArray().getJSONObject(i);
                        int id=option.getInt("id");
                        int   sumscar= option.getInt("sumscar");
                        String  shopname= option.getString("shopname");
                        LatLng ll = new LatLng(option.getDouble("logn_y"), option.getDouble("logn_x"));
                        items.add(new SwitBdItem(MainActivity.this,id,shopname,ll,sumscar));
                        double distance = DistanceUtils.getDistance(ll, point);

                    }
                    mClusterManager.addItems(items);
                }
            }
        });
    }
}


