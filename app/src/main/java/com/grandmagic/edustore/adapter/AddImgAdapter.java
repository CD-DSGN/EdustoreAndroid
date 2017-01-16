package com.grandmagic.edustore.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.external.imageselector.utils.ScreenUtils;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.Z1_TeacherPublishActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by lps on 2017/1/16.
 */

public class AddImgAdapter extends BaseAdapter {
    private Context context;
    private List<String> gridList;

    public AddImgAdapter(Context context, List<String> gridList) {

        this.context = context;
        this.gridList = gridList;
    }

    @Override
    public int getCount() {
        return gridList == null ? 1 : gridList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return gridList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if (convertView == null) {
            viewholder=new Viewholder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_img, null);
            viewholder.imageView = (ImageView) convertView.findViewById(R.id.img_teacherpush);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
//        设置宽高
        viewholder.imageView.getLayoutParams().width= ScreenUtils.getScreenSize(context).x/4;
        viewholder.imageView.getLayoutParams().height= ScreenUtils.getScreenSize(context).x/4;
        if (getCount()>1&&getCount()-1!=position) {
            ImageLoader.getInstance().displayImage("file://" + gridList.get(position), viewholder.imageView);
        } else {
            viewholder.imageView.setImageResource(R.drawable.select_img);
            if (getCount()>3)viewholder.imageView.setVisibility(View.GONE);
        }

        viewholder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag= (position != getCount() - 1);
                if (flag){
                    if (imgListener!=null){
                        imgListener.deleteimg(position);
                    }
                }else {
                    if (imgListener!=null){
                        imgListener.addimg();
                    }
                }
            }
        });
        return convertView;
    }

    class Viewholder {
        ImageView imageView;

    }
public interface  ImgListener{
    void addimg();
    void deleteimg(int position);
}
    ImgListener imgListener;

    public void setImgListener(ImgListener imgListener) {
        this.imgListener = imgListener;
    }

    public void setGridList(List<String> gridList) {
        this.gridList = gridList;
        notifyDataSetChanged();
    }
}
