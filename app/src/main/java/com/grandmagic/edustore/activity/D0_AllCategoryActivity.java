package com.grandmagic.edustore.activity;

/**
 * Created by chenggaoyuan on 2016/11/21.
 */

import android.content.res.Resources;
import android.widget.*;
import com.external.maxwin.view.XListView;
import com.grandmagic.BeeFramework.activity.BaseActivity;

import com.grandmagic.edustore.adapter.D0_CategoryAdapter;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.CATEGORY;
import com.grandmagic.edustore.protocol.FILTER;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.model.SearchModel;

public class D0_AllCategoryActivity extends BaseActivity implements BusinessResponse {

    private SearchModel searchModel;
    private XListView parentListView;
    private ImageButton backBtn;
    private TextView topText;
    D0_CategoryAdapter parentListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.d0_categoryactivity);

        parentListView = (XListView) findViewById(R.id.parent_list);
        backBtn = (ImageButton) findViewById(R.id.back_button);
        topText = (TextView) findViewById(R.id.category_text);
        Resources resource = this.getResources();
        String titleText=resource.getString(R.string.category_title);
        topText.setText(titleText);
        if (null == searchModel)
        {
            searchModel = new SearchModel(this);
            searchModel.searchCategory();
        }

        searchModel.addResponseListener(this);

        parentListAdapter = new D0_CategoryAdapter(this,searchModel.categoryArrayList);
        parentListView.setAdapter(parentListAdapter);
        parentListView.setPullLoadEnable(false);
        parentListView.setPullRefreshEnable(false);
        parentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position -1 < searchModel.categoryArrayList.size())
                {
                    CATEGORY category = searchModel.categoryArrayList.get(position -1);
                    try
                    {
                        if (category.children.size() > 0)
                        {
                            Intent it = new Intent(D0_AllCategoryActivity.this,D1_CategoryActivity.class);
                            it.putExtra("category",category.toJson().toString());
                            it.putExtra("category_name", searchModel.categoryArrayList.get(position-1).name);
                            D0_AllCategoryActivity.this.startActivity(it);
                            D0_AllCategoryActivity.this.overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                        else
                        {
                            Intent intent = new Intent(D0_AllCategoryActivity.this, B1_ProductListActivity.class);
                            FILTER filter = new FILTER();
                            filter.category_id = String.valueOf(category.id);
                            intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                            startActivity(intent);
                            D0_AllCategoryActivity.this.overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {
        if (url.endsWith(ApiInterface.SEARCHKEYWORDS))
        {
            ToastView toast = new ToastView(this, "D0_AllCategoryActivity SearchKeywords");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else if (url.endsWith(ApiInterface.CATEGORY))
        {
            parentListAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
