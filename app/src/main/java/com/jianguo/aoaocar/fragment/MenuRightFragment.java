package com.jianguo.aoaocar.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseFragment;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.LoginActivity;
import com.jianguo.aoaocar.activity.SetActivity;
import com.jianguo.aoaocar.activity.driver.OrderListActivity;
import com.jianguo.aoaocar.activity.driver.PartnersActivity;
import com.jianguo.aoaocar.activity.user.MessageCenterActivity;
import com.jianguo.aoaocar.activity.user.UseGuideActivity;
import com.jianguo.aoaocar.activity.user.UserCenterActivity;
import com.jianguo.aoaocar.activity.user.UserOrderActivity;
import com.jianguo.aoaocar.activity.user.UserPurseActivity;
import com.jianguo.aoaocar.model.Plate;
import com.jianguo.aoaocar.view.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/11/22.
 */

public class MenuRightFragment extends BaseFragment {
    private CommonAdapter<Plate>  mAdapter;
    private List<Plate> mPlate=new ArrayList<>();
   @Bind(R.id.lv_user_center)
    ListView  mListView;
    @Bind(R.id.riv_head)
    RoundImageView riv_head;
    @Bind(R.id.lv_user_name)
    TextView user_name;
    @Override
    protected void fetchData() {

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_meun_left;
    }

    @Override
    protected void initView() {
        riv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toLoginActivity(mActivity,LoginActivity.class);
            }
        });
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toActivity(mActivity,UserCenterActivity.class);
            }
        });

        mPlate.add(new Plate(R.mipmap.icon_nine_order,"全部订单"));
        mPlate.add(new Plate(R.mipmap.icon_mine_purse,"我的钱包"));
     //   mPlate.add(new Plate(R.mipmap.icon_more,"我要出租"));
        mPlate.add(new Plate(R.mipmap.icon_mine_message,"消息中心"));
        mPlate.add(new Plate(R.mipmap.icon_mine_guide,"使用指南"));
        mPlate.add(new Plate(R.mipmap.icon_mine_set,"设置"));
        mPlate.add(new Plate(R.mipmap.icon_nine_order,"车手订单"));
        mPlate.add(new Plate(R.mipmap.icon_nine_order,"合作伙伴"));
        mListView.setAdapter(mAdapter=new CommonAdapter<Plate>(mActivity,mPlate,R.layout.row_list_user_center) {
            @Override
            public void convert(ViewHolder holder, Plate item) {
                holder.setText(R.id.tv_user_center_detail,item.name).
                        setBackgroundResource(R.id.lv_user_center_detail,item.icon);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posiiton, long id) {
                switch (posiiton){
                    case  0:
                        Utils.toActivity(mActivity, UserOrderActivity.class);
                        break;
                    case  1:
                        Utils.toActivity(mActivity,UserPurseActivity.class);
                        break;
//                    case  2:
//                          Utils.toActivity(mActivity,CarRentalActivity.class);
//                        break;
                    case  2:
                          Utils.toActivity(mActivity,MessageCenterActivity.class);
                        break;
                    case  3:
                        Utils.toActivity(mActivity,UseGuideActivity.class);
                        break;
                    case  4:
                        Utils.toActivity(mActivity,SetActivity.class);
                        break;
                    case  5:
                        Utils.toActivity(mActivity,OrderListActivity.class);
                        break;
                    case  6:
                        Utils.toActivity(mActivity,PartnersActivity.class);
                        break;
                }
            }
        });
    }
}
