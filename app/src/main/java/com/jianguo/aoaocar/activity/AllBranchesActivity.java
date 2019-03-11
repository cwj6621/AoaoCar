package com.jianguo.aoaocar.activity;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.adapter.HeadSectionedAdapter;
import com.jianguo.aoaocar.adapter.LeftListAdapter;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.model.AllBbranches;
import com.jianguo.aoaocar.model.Branches;
import com.jianguo.aoaocar.view.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/23.
 */
public class AllBranchesActivity extends BaseActivity{

    @Bind(R.id.left_listview)
    ListView leftListview;
    @Bind(R.id.pinnedListView)
    PinnedHeaderListView pinnedListView;
    @Bind(R.id.ll_leftBtn)
    RelativeLayout leftBtn;
    @Bind(R.id.title_text)
    TextView title_text;
    @Bind(R.id.rl_rightBtn)
    RelativeLayout rightBtn;
    @Bind(R.id.tv_title)
    TextView cityTitle;
    private boolean isScroll = true;
    private LeftListAdapter adapter;
    private List<Branches> mBranches=new ArrayList<>();
    private List<AllBbranches> mAllBbranches=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_branches;
    }
    public void initDatas() {
        mBranches.add(new Branches(1,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(2,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(3,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(4,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(5,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(6,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(7,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(8,"北京西路","上海北京东路科技京城1232号"));
        mBranches.add(new Branches(9,"北京西路","上海北京东路科技京城1232号"));

        mAllBbranches.add(new AllBbranches(1,"黄浦区(7)",mBranches,true));
        mAllBbranches.add(new AllBbranches(1,"徐汇区(7)",mBranches,false));
        mAllBbranches.add(new AllBbranches(1,"长宁区(7)",mBranches,false));
        mAllBbranches.add(new AllBbranches(1,"静安区(7)",mBranches,false));
        mAllBbranches.add(new AllBbranches(1,"嘉定区(7)",mBranches,false));
        mAllBbranches.add(new AllBbranches(1,"普陀区(7)",mBranches,false));
        mAllBbranches.add(new AllBbranches(1,"浦东新区(7)",mBranches,false));
        mAllBbranches.add(new AllBbranches(1,"宝山区(7)",mBranches,false));
    }
    @Override
    public void initView() {
        setLeftBtnOnClickListener();
        title_text.setText("全部网点");
        initDatas();

        final HeadSectionedAdapter sectionedAdapter = new HeadSectionedAdapter(this, mAllBbranches, new HeadSectionedAdapter.OnItemListener() {
            @Override
            public void onItemName(String protify) {
                Intent intent=new Intent();
                intent.putExtra("name", protify);
                AllBranchesActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                AllBranchesActivity.this.finishActivity();
            }
        });

        adapter = new LeftListAdapter(this, mAllBbranches);
        leftListview.setAdapter(adapter);

        leftListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                isScroll = false;
                for (int i = 0; i < mAllBbranches.size(); i++) {
                    if (i == position) {
                        mAllBbranches.get(i).isSelect=true;
                    } else {
                        mAllBbranches.get(i).isSelect=false;
                    }
                }
                adapter.notifyDataSetChanged();
                int rightSection = 0;
                for (int i = 0; i < position; i++) {
                    rightSection += sectionedAdapter.getCountForSection(i) + 1;
                }
                pinnedListView.setSelection(rightSection);
            }
        });
        pinnedListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (pinnedListView.getLastVisiblePosition() == (pinnedListView.getCount() - 1)) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }
                        // 判断滚动到顶部
                        if (pinnedListView.getFirstVisiblePosition() == 0) {
                            leftListview.setSelection(0);
                        }
                        break;
                }
            }
            int y = 0;
            int x = 0;
            int z = 0;
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScroll) {
                    for (int i = 0; i < mAllBbranches.size(); i++) {
                        if (i == sectionedAdapter.getSectionForPosition(pinnedListView.getFirstVisiblePosition())) {
                            mAllBbranches.get(i).isSelect=true;
                            x = i;
                        } else {
                            mAllBbranches.get(i).isSelect=false;
                        }
                    }
                    if (x != y) {
                        adapter.notifyDataSetChanged();
                        y = x;
                        if (y == leftListview.getLastVisiblePosition()) {
//                            z = z + 3;
                            leftListview.setSelection(z);
                        }
                        if (x == leftListview.getFirstVisiblePosition()) {
//                            z = z - 1;
                            leftListview.setSelection(z);
                        }
                        if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
                            leftListview.setSelection(ListView.FOCUS_DOWN);
                        }
                    }
                } else {
                    isScroll = true;
                }
            }
        });
    }

    private void setLeftBtnOnClickListener() {
         rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllBranchesActivity.this, SelectCityActivity.class);
                startActivityForResult(intent, Constant.ACTION_CITY_FROM);
                overridePendingTransition( R.anim.enter_bottom,  R.anim.activity_default);
            }
        });
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AppManager.getAppManager().finishActivity();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Constant.ACTION_CITY_FROM == requestCode) {
            if (resultCode ==  RESULT_OK) {
                cityTitle.setText(data.getStringExtra("city_name"));
                PreUtils.putString(AllBranchesActivity.this, "city", data.getStringExtra("city_name"));
            }
        }
    }
}

