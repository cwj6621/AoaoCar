package com.jianguo.aoaocar.view.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.it.jianguo.common.Utils.NavigationUtils;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.commonutils.ToastUitl;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.CostInstrutionActivity;
import com.jianguo.aoaocar.model.PotCar;
import com.jianguo.aoaocar.view.animation.EasyFlipView;
import com.jianguo.aoaocar.view.silderbottompannel.SlideBottomPanel;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 22077 on 2017/12/29.
 */
public class NearCardetailPop  extends PopupWindow implements View.OnClickListener {
    private final View mView;
    private final LinearLayout mViewCarList;
    private final ListView mListView;
    private final RelativeLayout sbpHead;
    private final LinearLayout navigatView;
    private final Button checkCar;
    private final RelativeLayout potCost;
    private Context mCtx;
    EasyFlipView easyFlipView;
    SlideBottomPanel msbv;
    LinearLayout mCarPopView;
    ImageView toFont;
    private CommonAdapter<PotCar> mAdapter;
    private List<PotCar> mPotCars=new ArrayList<>();
    private boolean isShowList=true;
    public NearCardetailPop(final Context ctx) {
        this.mCtx = ctx;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        mView = inflater.inflate(R.layout.widget_near_car_popup_window, null);

        easyFlipView = (EasyFlipView) mView.findViewById(R.id.easyFlipView);

        msbv = (SlideBottomPanel) mView.findViewById(R.id.sbv);
        sbpHead = (RelativeLayout) mView.findViewById(R.id.rl_sbp_head);
        navigatView = (LinearLayout) mView.findViewById(R.id.rv_navigation);
        mViewCarList = (LinearLayout) mView.findViewById(R.id.lv_car_list);
        mListView = (ListView) mView.findViewById(R.id.lv_car);
        potCost = (RelativeLayout) mView.findViewById(R.id.rl_pot_cost);


        mCarPopView = (LinearLayout) mView.findViewById(R.id.rv_car_detail);
        checkCar = (Button) mView.findViewById(R.id.bt_check_car);
        toFont = (ImageView) mView.findViewById(R.id.iv_to_font);

        sbpHead.setOnClickListener(this);
        toFont.setOnClickListener(this);
        navigatView.setOnClickListener(this);
        checkCar.setOnClickListener(this);
        potCost.setOnClickListener(this);

        easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyView, EasyFlipView.FlipState newCurrentSide) {
                msbv.displayPanel();
            }
        });
        easyFlipView.setFlipDuration(1000);
        easyFlipView.setFlipEnabled(true);

        //msbv.setExternalView(mViewCarList,230);
        msbv.startAnima(230);
        setPopState(mView, this);
    }

    public static void setPopState(View mFillingView, PopupWindow pWindow) {
        // 设置SelectPicPopupWindow的View
        pWindow.setContentView(mFillingView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        pWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pWindow.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        pWindow.setOutsideTouchable(false);
        pWindow.setAnimationStyle(R.style.AnimationPicker);
        // 实例化一个ColorDrawable颜色为半透明
        //  pWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体的背景
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }

    @Override
    public void onClick(View view) {
        msbv.displayPanel();
        switch (view.getId()) {
            case R.id.rl_sbp_head://
               if(msbv.isPanelShowing()){
                   if(isShowList){
                       msbv.startAnima(320);
                   }else{
                       msbv.startAnima(0);
                   }
                    isShowList=!isShowList;
                }
                break;
            case R.id.iv_to_font://反转
                mCarPopView.setVisibility(View.INVISIBLE);
                easyFlipView.flipTheView();
                break;
            case R.id.bt_check_car://约车
               new ConventSuccessPop(mCtx);
                break;
            case R.id.rv_navigation://导航
                List<NavigationUtils.Navigtion> mNavigtions= NavigationUtils.getNavigatMapName(mCtx);
                if(mNavigtions!=null){
                    new NavigationPop(mCtx,mNavigtions) ;
                }else{
                    ToastUitl.showShort(mCtx,"您手机还没有装导航应用");
                }
                break;
            case R.id.rl_pot_cost://导航
                Utils.toActivity(mCtx,CostInstrutionActivity.class);
                break;

        }
    }
}