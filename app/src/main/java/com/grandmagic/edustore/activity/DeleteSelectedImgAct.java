package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.grandmagic.edustore.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DeleteSelectedImgAct extends Activity implements View.OnClickListener {
ImageView imageView,delete,back;
    public static final String IMAGE_PATH="imagepath";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_selected_img);
        initview();
    }

    private void initview() {
        imageView= (ImageView) findViewById(R.id.image);
        String url = getIntent().getStringExtra(IMAGE_PATH);
        ImageLoader.getInstance().displayImage("file://"+url,imageView);

        delete= (ImageView) findViewById(R.id.top_view_share);
        back= (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.top_view_back:
        finish();break;
    case R.id.top_view_share:
        setResult(RESULT_OK);
        finish();
        break;
}
    }
}
