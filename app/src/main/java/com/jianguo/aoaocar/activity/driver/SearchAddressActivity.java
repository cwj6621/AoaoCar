package com.jianguo.aoaocar.activity.driver;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.it.jianguo.common.xlistview.XListView;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.view.ClearableEditText;
import butterknife.Bind;
/**
 * Created by 22077 on 2018/1/29.
 */
public class SearchAddressActivity extends BaseActivity{
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.laet_search)
    ClearableEditText searchView;
    @Bind(R.id.view_not_data)
    LinearLayout notData;
    @Bind(R.id.lv_search)
    XListView mXListView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_address;
    }
    @Override
    public void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
}
