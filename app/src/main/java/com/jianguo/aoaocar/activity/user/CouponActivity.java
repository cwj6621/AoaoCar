package com.jianguo.aoaocar.activity.user;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.commonutils.ToastUitl;
import com.it.jianguo.common.xlistview.XListView;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.Coupon;
import com.jianguo.aoaocar.view.coupons.CouponView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/27.
 */
public class CouponActivity extends BaseActivity{
    @Bind(R.id.btn_exchange)
    Button  btn_exchange;
    @Bind(R.id.et_exchange_code)
    EditText et_code;
    @Bind(R.id.lv_coupon)
    XListView mXListView;
    private List<Coupon> mCoupons=new ArrayList<>();
    private CommonAdapter<Coupon> mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("优惠券");

        initListView();
        btn_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String   code =et_code.getText().toString();
                if(code.isEmpty()){
                    ToastUitl.showShort(CouponActivity.this,"请输入兑换码");
                    return;
                 }
                mCoupons.add(0,new Coupon(1,"25","满90元","21017-01-30" ));
                mAdapter.notifyDataSetChanged();
            }
        });
     }

    private void initListView() {
        mCoupons.add(new Coupon(1,"10","满50元","21017-12-01" ));
        mCoupons.add(new Coupon(2,"20","满80元","21017-12-13"  ));
        mCoupons.add(new Coupon(3,"15","满70元","21017-12-15" ));
        mCoupons.add(new Coupon(4,"30","满100元","21017-12-30" ));
        mXListView.setAdapter(mAdapter=new CommonAdapter<Coupon>(this,mCoupons,R.layout.row_list_citem_oupon) {
            @Override
            public void convert(ViewHolder holder, Coupon item) {

                CouponView mCouponView=holder.getView(R.id.couponiew);
                holder.setText(R.id.tv_coupons_value,"￥"+item.value).
                        setText(R.id.tv_coupons_rule,"使用规则："+item.rule).
                        setText(R.id.tv_coupons_overtime,"截至日期："+item.overtime);
                mCouponView.setSemicircleLeft(true);
                mCouponView.setSemicircleTop(true);
                mCouponView.setSemicircleBottom(true);
                mCouponView.setSemicircleRight(true);
                mCouponView.setSemicircleRadius(Utils.dp2px(CouponActivity.this,3));
                mCouponView.setSemicircleGap(Utils.dp2px(CouponActivity.this,1));
            }
        } );

    }
}
