package com.jianguo.aoaocar.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.sortlist.CharacterParser;
import com.it.jianguo.common.sortlist.PinyinComparator;
import com.it.jianguo.common.sortlist.SideBar;
import com.it.jianguo.common.sortlist.SortAdapter;
import com.it.jianguo.common.sortlist.SortModel;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.view.ClearableEditText;
import com.jianguo.timedialog.data.CityModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Created by 22077 on 2018/1/9.
 */
public class SelectCityActivity extends BaseActivity{
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private ListView sortListView;
    private List<SortModel> mSrcDataList;
    private SortAdapter adapter;
    private ClearableEditText mClearEditText;
    private List<CityModel> mCityModels = new ArrayList<CityModel>();
    private boolean isStore = false;
    private SideBar sideBar;
    private TextView dialog;
    private String currectCity = null;
    private Button fixedCity;
    @Override
    public int getLayoutId() {
        return R.layout.activity_selsect_city;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("定位城市");
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar_fix_city);
        dialog = (TextView) findViewById(R.id.dialog);
        dialog.getBackground().setAlpha(180);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                if (mSrcDataList != null) {
                    int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        sortListView.setSelection(position);
                    }
                }
            }
        });
        sortListView = (ListView) findViewById(R.id.lv_city_name);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("city_name", ((SortModel) adapter.getItem(position)).getName());
                SelectCityActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                SelectCityActivity.this.finishActivity();
                overridePendingTransition(R.anim.activity_default, R.anim.exit_bottom);
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        mSrcDataList = fillData(analysiCity());
        // 根据a-z进行排序源数据
        Collections.sort(mSrcDataList, pinyinComparator);
        adapter = new SortAdapter(this, mSrcDataList);
        sortListView.setAdapter(adapter);
        mClearEditText = (ClearableEditText) findViewById(R.id.filter_edit_city_name);
        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    /**
     * 为ListView填充数据
     * @return
     */
    private List<SortModel> fillData(List<CityModel> mCityNameList) {
        List<SortModel> sortList = new ArrayList<SortModel>();
        for (int i = 0; i < mCityNameList.size(); i++) {
            CityModel merchant = mCityNameList.get(i);
            SortModel sortModel = new SortModel();
            sortModel.setName(merchant.name);
            sortModel.setSortNo(merchant.id);
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(merchant.name);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            sortList.add(sortModel);
        }
        return sortList;
    }

    private List<CityModel> analysiCity() {
        InputStream is = this.getResources().openRawResource(R.raw.city);
        byte[] buffer;
        try {
            buffer = new byte[is.available()];
            is.read(buffer);
            String json = new String(buffer);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("city_info");
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String cityName = item.optString("area_name");
                CityModel model = new CityModel(i,cityName);
                mCityModels.add(model);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mCityModels;
    }
    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = mSrcDataList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : mSrcDataList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).toUpperCase()
                        .startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_default, R.anim.exit_bottom);
    }
}
