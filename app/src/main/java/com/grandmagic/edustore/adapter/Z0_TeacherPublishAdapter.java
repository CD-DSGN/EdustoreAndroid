package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.grandmagic.BeeFramework.adapter.BeeBaseAdapter;
import com.grandmagic.edustore.R;

import java.util.ArrayList;

/**
 * Created by chenggaoyuan on 2016/11/29.
 */
public class Z0_TeacherPublishAdapter extends BeeBaseAdapter {

    public Z0_TeacherPublishAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        return null;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        return null;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.single_publish_item, null);
    }
}
