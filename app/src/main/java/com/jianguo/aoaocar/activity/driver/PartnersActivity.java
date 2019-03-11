package com.jianguo.aoaocar.activity.driver;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.commonwidget.NoScrollListview;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.user.CertifyActivity;
import com.jianguo.aoaocar.model.Partner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 22077 on 2018/1/23.
 */

public class PartnersActivity extends BaseActivity{
    @Bind(R.id.lv_panter)
    NoScrollListview  mListView;

    private List<Partner> mPartners=new ArrayList<>();
    private CommonAdapter<Partner>  mAdapter;

    @Override
    public int getLayoutId() {
            return R.layout.activity_partenrs;
    }

    @Override
    public void initView() {
       setLeftBtnDefaultOnClickListener();
       setTitle("合伙伙伴");

       initListView();
    }

    private void initListView() {

        mPartners.add(new Partner(0,"车主","车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主车主",false));
        mPartners.add(new Partner(1,"桩主","桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主桩主",false));
        mPartners.add(new Partner(2,"车手","车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手车手",false));
        mPartners.add(new Partner(3,"供应商","供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商供应商",false));

        mListView.setAdapter(mAdapter=new CommonAdapter<Partner>(this,mPartners,R.layout.row_list_panters_detail) {
            @Override
            public void convert(final ViewHolder holder, final Partner item) {
                holder.setText(R.id.tv_panter_name,item.name).setText(R.id.tv_panters_desc,item.desc);
                Button  btn=holder.getView(R.id.tv_certify);
                TextView tvDesc=holder.getView(R.id.tv_panters_desc);
                RelativeLayout viewHead=holder.getView(R.id.rv_panter_head);

                if(item.isShow){
                    tvDesc.setVisibility(View.VISIBLE);
                }else{
                    tvDesc.setVisibility(View.GONE);
                }
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (holder.getPosition()){
                            case 0:
                                Utils.toActivity(PartnersActivity.this,DriverHostCarActivity.class);
                                break;
                            case 1:
                                Utils.toActivity(PartnersActivity.this,ChargingPileActivity.class);
                                break;
                            case 2:
                                Utils.toActivity(PartnersActivity.this,CertifyActivity.class);
                                break;
                            case 3:
                                Utils.toActivity(PartnersActivity.this,SupplierCertifiyActivity.class);
                                break;
                        }

                    }
                });
                viewHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        item.isShow=!item.isShow;
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
