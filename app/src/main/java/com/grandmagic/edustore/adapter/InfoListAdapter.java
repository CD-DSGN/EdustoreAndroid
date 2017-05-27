package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.NewsDetailActivity;
import com.grandmagic.edustore.protocol.NewsList;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

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
        mInfoHolder.catagory = (TextView) cellView.findViewById(R.id.tv_news_label);
        mInfoHolder.time = (TextView) cellView.findViewById(R.id.tv_news_update);
        return mInfoHolder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        final NewsList.DataBean.InfoBean data = (NewsList.DataBean.InfoBean) dataList.get(position);
        InfoHolder hodler = (InfoHolder) h;
        hodler.content.setText(data.getSketch());
        hodler.title.setText(data.getTitle());
        hodler.catagory.setText(data.getLabel_name());
        hodler.time.setText(data.getUpdated_at());
        int type = 0;
        try {
            type = Integer.valueOf(data.getLabel_id());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        switch (type) {
            case 1:
                hodler.catagory.setBackgroundColor(0xff82659d);
                break;
            case 2:
                hodler.catagory.setBackgroundColor(0xffd780b5);
                break;
            case 3:
                hodler.catagory.setBackgroundColor(0xffe57c5e);
                break;
            case 4:
                hodler.catagory.setBackgroundColor(0xffff8879);
                break;
            case 5:
                hodler.catagory.setBackgroundColor(0xfff5ab24);
                break;
            case 6:
                hodler.catagory.setBackgroundColor(0xfffcde02);
                break;
            case 7:
                hodler.catagory.setBackgroundColor(0xff61c7b1);
                break;
            default:
                hodler.catagory.setBackgroundColor(0xff99cbee);
                break;
        }

        ImageLoader.getInstance().displayImage(data.getBanner().getBanner_url(), hodler.conver);
        cellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mContext, NewsDetailActivity.class);
                mIntent.putExtra(NewsDetailActivity.URL,data.getUrl());
                mContext.startActivity(mIntent);
            }
        });

        return null;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_home_news, null);
    }

    public class InfoHolder extends BeeCellHolder {
        ImageView conver;
        TextView title, content,time;
        TextView catagory;
    }


    public void refreshData(ArrayList mList) {
        dataList = mList;
        notifyDataSetChanged();
    }
}
