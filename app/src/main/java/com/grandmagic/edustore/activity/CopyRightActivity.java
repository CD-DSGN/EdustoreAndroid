package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.edustore.R;

public class CopyRightActivity extends Activity implements View.OnClickListener {
    TextView mTvTitleText;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_right);
        initViews();
    }

    private void initViews() {
        mTvTitleText = (TextView) findViewById(R.id.top_view_text);
        mTvTitleText.setText(R.string.about_us);
       imgBack = (ImageView) findViewById(R.id.top_view_back);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_view_back:
                finish();
                break;
        }
    }
}
