package com.grandmagic.edustore.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.InfoListAdapter;
import com.grandmagic.edustore.model.ShoppingCartModel;
import com.grandmagic.edustore.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新的首页。关于资讯
 */
public class InformationFragment extends Fragment implements BusinessResponse {
private XListView mXListView;
    FrameLayout mView_Empty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mrootView = inflater.inflate(R.layout.fragment_information, container, false);
        initview(mrootView);
        initdata();
        return mrootView;
    }
InfoListAdapter mInfoListAdapter;
    private void initview(View mMrootView) {
        mXListView= (XListView) mMrootView.findViewById(R.id.listview);
        mView_Empty = (FrameLayout) mMrootView.findViewById(R.id.view_empty);
        mView_Empty.setVisibility(View.GONE);
        ArrayList<String> mStrings=new ArrayList<>();
        mStrings.add("afaf");
        mStrings.add("afaf");
        mStrings.add("afaf");
        mStrings.add("afaf");
        mInfoListAdapter=new InfoListAdapter(getActivity(),mStrings);
        mXListView.setAdapter(mInfoListAdapter);

    }

    private void initdata() {
        ShoppingCartModel shoppingCartModel = new ShoppingCartModel(getActivity());
        shoppingCartModel.addResponseListener(this);
        shoppingCartModel.homeCartList();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.CART_LIST)) {
            TabsFragment.setShoppingcartNum();
        }
    }
}
