package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.grandmagic.edustore.R;

public class CopyRightActivity extends Activity {
    TextView mTvTitleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_right);
        initViews();
    }

    private void initViews() {
        mTvTitleText = (TextView) findViewById(R.id.top_view_text);
        mTvTitleText.setText(R.string.about_us);


    }
}
