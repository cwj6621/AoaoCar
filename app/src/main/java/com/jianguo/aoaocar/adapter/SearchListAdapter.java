package com.jianguo.aoaocar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.SearchResult;

import java.util.List;

/**
 * Created by 22077 on 2017/12/13.
 */

public class SearchListAdapter extends BaseAdapter{
    private List<SearchResult>  mSearchResults;
    private Context ctx;
    private LayoutInflater inflater;
    public SearchListAdapter(Context _context,List<SearchResult> mSearchResults) {
        this.ctx=_context;
        this.mSearchResults = mSearchResults;
        inflater=LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return mSearchResults.size();
    }
    @Override
    public Object getItem(int i) {
        return mSearchResults.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.row_list_search_result, null);
            mViewHolder = new ViewHolder();
            mViewHolder.name = (TextView) view.findViewById(R.id.tv_search_result_name);
            mViewHolder.address = (TextView) view.findViewById(R.id.tv_search_result_distrut);
            mViewHolder.distrct = (TextView) view.findViewById(R.id.tv_search_result_name);
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        SearchResult mSearchResult = mSearchResults.get(i);
        mViewHolder.name.setText(mSearchResult.getName());
        mViewHolder.address.setText(mSearchResult.getAddress());
        mViewHolder.distrct.setText(mSearchResult.getDistruct()+"米");

        return view;
    }
        static class ViewHolder{
        private TextView  name,address,distrct;
    }
}
