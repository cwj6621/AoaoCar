package com.jianguo.aoaocar.fragment.message;

import com.it.jianguo.common.base.BaseFragment;
import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2017/11/30.
 */

public class OrderMessageFragment extends BaseFragment {
    @Override
    protected void fetchData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_lsit;
    }

    @Override
    protected void initView() {
        setNotDataAndClickIntent(R.mipmap.icon_no_date,mActivity.getResources().getString(R.string.not_notice),null,null);
    }
}

