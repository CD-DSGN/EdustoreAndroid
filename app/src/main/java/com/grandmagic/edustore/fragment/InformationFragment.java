package com.grandmagic.edustore.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.InfoListAdapter;
import com.grandmagic.edustore.model.NewsModel;
import com.grandmagic.edustore.model.ShoppingCartModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.NewsList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 新的首页。关于资讯
 */
public class InformationFragment extends Fragment implements BusinessResponse {
    private static final String TAG = "InformationFragment";
    private XListView mXListView;
    FrameLayout mView_Empty;
    NewsModel mNewsModel;
    int mTotalPage = 1;
    int currpage = 1;
    private AlertDialog mAlertDialog;
    private SharedPreferences shared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mrootView = inflater.inflate(R.layout.fragment_information, container, false);
        initview(mrootView);
        initdata();
        initlistener();
        return mrootView;
    }

    private void initlistener() {
        mXListView.setPullLoadEnable(true);
        mXListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh(int id) {
                currpage = 1;
                mNewsModel.getNewsData(currpage);
                mXListView.setRefreshTime();
            }

            @Override
            public void onLoadMore(int id) {
                currpage++;
                mNewsModel.getNewsData(currpage);
            }
        }, 0);
    }

    ArrayList<NewsList.DataBean.InfoBean> mList = new ArrayList<>();
    InfoListAdapter mInfoListAdapter;

    private void initview(View mMrootView) {
        mXListView = (XListView) mMrootView.findViewById(R.id.listview);
        mView_Empty = (FrameLayout) mMrootView.findViewById(R.id.view_empty);
        mView_Empty.setVisibility(View.GONE);


        mInfoListAdapter = new InfoListAdapter(getActivity(), mList);
        mXListView.setAdapter(mInfoListAdapter);
    }

    private void initdata() {
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel(getActivity());
        shoppingCartModel.addResponseListener(this);

        shared = getActivity().getSharedPreferences("userInfo", 0);
        String uid = shared.getString("uid", "");
        if (!TextUtils.isEmpty(uid)) {
            shoppingCartModel.homeCartList();
        }
        mNewsModel = new NewsModel(getActivity());
        mNewsModel.addResponseListener(this);
        mNewsModel.getNewsData(currpage);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.CART_LIST)) {
            TabsFragment.setShoppingcartNum();
        } else if (ApiInterface.GET_NEWS.equals(url)) {
            mXListView.stopRefresh();
            mXListView.stopLoadMore();
            Gson mGson = new Gson();
            try {
                NewsList mNewsList = mGson.fromJson(jo.toString(), NewsList.class);
                if (mNewsList.getCode() == 200) {
                    if (mNewsList.getData() != null) {
                        if (currpage == 1) mList.clear();
                        mTotalPage = mNewsList.getData().getTotal_page();
                        if (currpage < mTotalPage) {
                            mXListView.setPullLoadEnable(true);
                        } else {
                            mXListView.setPullLoadEnable(false);
                        }
                        ArrayList<NewsList.DataBean.InfoBean> mInfo = mNewsList.getData().getInfo();
                        if (mInfo != null && !mInfo.isEmpty()) {
                            mList.addAll(mInfo);
                            mInfoListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (JsonSyntaxException mE) {
                mE.printStackTrace();
            }
        }
    }
}
