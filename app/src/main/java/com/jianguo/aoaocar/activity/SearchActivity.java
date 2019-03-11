package com.jianguo.aoaocar.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.view.ClearableEditText;
import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/22.
 */
public class SearchActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView btnBack;
    @Bind(R.id.iv_not_data_notice)
    ImageView ivNotice;
    @Bind(R.id.tv_not_data_notice)
    TextView tvNotice;
    @Bind(R.id.laet_search)
    ClearableEditText etSearch;
    @Bind(R.id.view_not_data)
    View notDataView;
    @Bind(R.id.tv_search_list)
    TextView searchList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }
    @Override
    public void initView() {

        tvNotice.setText("未搜到相关数据");
        ivNotice.setBackgroundResource(R.mipmap.icon_not_search);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getAppManager().finishActivity();
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){

                }
            }
        });
        searchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.toActivity(SearchActivity.this,AllBranchesActivity.class);
            }
        });
    }
}
