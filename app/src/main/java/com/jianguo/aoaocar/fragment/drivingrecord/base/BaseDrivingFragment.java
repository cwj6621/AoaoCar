package com.jianguo.aoaocar.fragment.drivingrecord.base;

import com.it.jianguo.common.base.BaseFragment;

/**
 * Created by 22077 on 2018/1/5.
 */

public abstract class BaseDrivingFragment extends BaseFragment{
    /**
     * LoanClickListener
     * 状态变化进行页面切换监听
     */
    public LoanClickListener loanListener;
    public void setLoanClickListener(LoanClickListener listener) {
        loanListener = listener;
    }
    /**
     * Drivingrecord
     * 状态
     */
    public    enum    Navigtion{
        Reservation("预约"),
        TakeCar("取车"),
        DrivingCar("用车"),
        RefundCar("还车"),
        PayCar("支付"),
        Evaluation("评价"),
        Complete("完成");

        private String desc;

        Navigtion(String desc) {
            this.desc = desc;
        }
        public String getDesc() {
            return desc;
        }
    }
}
