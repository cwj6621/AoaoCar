package com.jianguo.aoaocar.activity.driver;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.view.AddNumberView;
import com.jianguo.aoaocar.view.pop.SpinnerItemPop;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
/**
 * Created by 22077 on 2018/1/29.
 */

public class ChargingPileActivity extends BaseActivity{
    @Bind(R.id.add_number)
    AddNumberView numberView;

    @Bind(R.id.btn_buy_car)
    Button btnBuyCar;
    @Bind(R.id.iv_buy_detail)
    ImageView iv;
    @Bind(R.id.iv_buy_name)
    TextView buyName;
    @Bind(R.id.spinnerbumen)
    TextView spinnerbumen;
    @Bind(R.id.spinnerview)
    LinearLayout spinnerview;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<String>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_driver_host_buy_car;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("认购");

        numberView.setOnNunberChangeListenter(new AddNumberView.OnNunberChangeListenter() {
            @Override
            public void onNunberChange(int number) {

            }
        });
        list.add("黑色");
        list.add("红色");
        list.add("白色");
        list.add("棕色");
        list.add("银白色");
        spinnerbumen.setText("规格："+list.get(0));
        spinnerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerItemPop spinnerItemPop= new SpinnerItemPop(ChargingPileActivity.this,list,0, new SpinnerItemPop.OnDismissListListener() {
                    @Override
                    public void onnDismissList(int index,boolean option ) {
                        if (option) {
                            spinnerbumen.setText("规格：" + list.get(index));
                        }
                    }
                });
                spinnerItemPop.showAsDropDown(spinnerview);
            }
        });

        btnBuyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Utils.toActivity(ChargingPileActivity.this,SendCarActivity.class);
            }
        });
    }
}
