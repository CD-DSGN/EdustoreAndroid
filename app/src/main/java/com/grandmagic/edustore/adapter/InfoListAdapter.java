package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.R;
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


public class InfoListAdapter  extends BeeBaseAdapter{
    public InfoListAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        InfoHolder mInfoHolder=new InfoHolder();
        mInfoHolder.content= (TextView) cellView.findViewById(R.id.tv_news_content);
        mInfoHolder.title= (TextView) cellView.findViewById(R.id.tv_news_content);
        mInfoHolder.conver= (ImageView) cellView.findViewById(R.id.iv_news_pic);
        return mInfoHolder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        InfoHolder hodler= (InfoHolder) h;
      hodler.content.setText("测试内容");
        hodler.title.setText("测试title");
        ImageLoader.getInstance().displayImage("https://avatars1.githubusercontent.com/u/12251536?v=3&s=40",hodler.conver);
        return null;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_index_news,null);
    }

    public class InfoHolder extends BeeCellHolder{
        ImageView conver;
        TextView title,content;
    }
}
