package com.grandmagic.edustore.activity;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
// 全部商品的某项

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import com.external.maxwin.view.XListView;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.view.MyViewGroup;
import com.grandmagic.grandMagicManager.GrandMagicManager;

import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.D0_CategoryAdapter;
import com.grandmagic.edustore.protocol.CATEGORY;
import com.grandmagic.edustore.protocol.FILTER;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class D1_CategoryActivity extends BaseActivity 
{

    private ImageView backButton;
    private TextView title;

    private XListView childListView;
    D0_CategoryAdapter childListAdapter;
    CATEGORY category ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_category);

        
        backButton = (ImageView)findViewById(R.id.top_view_back);
        title = (TextView) findViewById(R.id.top_view_text);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        childListView = (XListView)findViewById(R.id.child_list);
        childListView.setPullLoadEnable(false);
        childListView.setPullRefreshEnable(false);
        String categoryStr = getIntent().getStringExtra("category");
        title.setText(getIntent().getStringExtra("category_name"));
        
        try
        {
            JSONObject jsonObject = new JSONObject(categoryStr);
            category = new  CATEGORY();
            category.fromJson(jsonObject);
            childListAdapter = new D0_CategoryAdapter(this,this.category.children);
            childListView.setAdapter(childListAdapter);
            childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position - 1 < category.children.size())
                    {
                        CATEGORY item = category.children.get(position - 1);

                        try
                        {
                            Intent intent = new Intent(D1_CategoryActivity.this, B1_ProductListActivity.class);
                            FILTER filter = new FILTER();
                            filter.category_id = String.valueOf(item.id);
                            intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                            startActivity(intent);
                            overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                        catch (JSONException e)
                        {

                        }

                    }
                }
            });
        }
        catch (JSONException e)
        {

        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
