package com.grandmagic.edustore.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grandmagic.edustore.R;

public class NewsDetailActivity extends Activity {
public static final String URL="url";
    ProgressBar mProgressBar;
    WebView mWebView;
    TextView mTitle;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initview();
        initdata();
    }

    private void initdata() {
        String url = getIntent().getStringExtra(URL);
        mWebView.loadUrl(url);
    }

    private void initview() {
        mWebView= (WebView) findViewById(R.id.webview);
        mTitle= (TextView) findViewById(R.id.top_view_text);
        mProgressBar= (ProgressBar) findViewById(R.id.progress);
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        back= (ImageView) findViewById(R.id.top_view_back);
        initlistener();
    }

    private void initlistener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress<100) {
                    mProgressBar.setProgress(newProgress);
                    mProgressBar.setVisibility(View.VISIBLE);
                }else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitle.setText(title);
            }
        });
    }

}
