package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.NewsList;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lps on 2017/5/3.
 *
 * @version 1
 * @see
 * @since 2017/5/3 17:22
 * 首页的资讯adapter
 */


public class InfoListAdapter extends BeeBaseAdapter {
    public InfoListAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        InfoHolder mInfoHolder = new InfoHolder();
        mInfoHolder.content = (TextView) cellView.findViewById(R.id.tv_news_content);
        mInfoHolder.title = (TextView) cellView.findViewById(R.id.tv_news_title);
        mInfoHolder.conver = (ImageView) cellView.findViewById(R.id.iv_news_pic);
        return mInfoHolder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        NewsList.DataBean.InfoBean data = (NewsList.DataBean.InfoBean) dataList.get(position);
        InfoHolder hodler = (InfoHolder) h;
        hodler.content.setText(data.getSketch());
        hodler.title.setText(data.getTitle());
        ImageLoader.getInstance().displayImage(data.getBanner().getBanner_url(), hodler.conver);
        return null;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_home_news, null);
    }

    public class InfoHolder extends BeeCellHolder {
        ImageView conver;
        TextView title, content;
    }


    public void refreshData(ArrayList mList) {
        dataList = mList;
        notifyDataSetChanged();
    }
}
