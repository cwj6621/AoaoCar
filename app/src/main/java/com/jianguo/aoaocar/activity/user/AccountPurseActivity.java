package com.jianguo.aoaocar.activity.user;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.Plate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/27.
 */
public class AccountPurseActivity extends BaseActivity{
    @Bind(R.id.lv_pay_type)
    ListView  mListView;
    private List<Plate> mPlates = new ArrayList<>();
    private CommonAdapter<Plate> mAdapter;
    private RadioButton checkRdia;
    private HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    private int checkBrandIndex = -1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_purse;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("账户钱包");

        initListView();
    }
    private void initListView() {
        mPlates.add(new Plate(R.mipmap.icon_pay_apliy, "支付宝支付", false));
        mPlates.add(new Plate(R.mipmap.icon_pay_weixin, "微信支付", false));

        mListView.setAdapter(mAdapter = new CommonAdapter<Plate>(this, mPlates, R.layout.row_item_play_type) {
            @Override
            public void convert(ViewHolder holder, Plate item) {
                holder.setText(R.id.tv_pay_type, item.name);
                holder.setBackgroundResource(R.id.iv_pay_type, item.icon);
                checkRdia = (RadioButton) holder.getView(R.id.rb_pay_type);
                boolean res = false;
                if (states.get(String.valueOf(holder.getPosition())) == null || states.get(String.valueOf(holder.getPosition())) == false) {
                    res = false;
                    states.put(String.valueOf(holder.getPosition()), false);
                } else
                    res = true;
                checkRdia.setChecked(res);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (checkBrandIndex == (position)) {
                    for (String key : states.keySet()) {
                        states.put(key, false);
                    }
                    mAdapter.notifyDataSetChanged();
                    checkBrandIndex = -1;
                } else {
                    for (String key : states.keySet()) {
                        states.put(key, false);
                    }
                    states.put(String.valueOf(position), true);
                    mAdapter.notifyDataSetChanged();
                    checkBrandIndex = position;
                }
            }
        });
    }
}
