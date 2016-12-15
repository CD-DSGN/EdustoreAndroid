package com.grandmagic.edustore.fragment;
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
//

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.viewpagerindicator.PageIndicator;
import com.grandmagic.BeeFramework.fragment.BaseFragment;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.MyListView;
import com.grandmagic.edustore.ECMobileAppConst;
import com.grandmagic.edustore.EcmobileApp;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.B1_ProductListActivity;
import com.grandmagic.edustore.activity.B2_ProductDetailActivity;
import com.grandmagic.edustore.activity.BannerWebActivity;
import com.grandmagic.edustore.activity.D0_AllCategoryActivity;
import com.grandmagic.edustore.activity.G3_MessageActivity;
import com.grandmagic.edustore.activity.SubscriptionActivity;
import com.grandmagic.edustore.activity.UserIntegralActivity;
import com.grandmagic.edustore.adapter.B0_IndexAdapter;
import com.grandmagic.edustore.adapter.Bee_PageAdapter;
import com.grandmagic.edustore.component.CircleFrameLayout;
import com.grandmagic.edustore.model.ConfigModel;
import com.grandmagic.edustore.model.HomeModel;
import com.grandmagic.edustore.model.LoginModel;
import com.grandmagic.edustore.model.MsgModel;
import com.grandmagic.edustore.model.MsgModel.OnMessageContResponse;

import com.grandmagic.edustore.model.ShoppingCartModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.FILTER;
import com.grandmagic.edustore.protocol.PLAYER;
import com.grandmagic.grandMagicManager.GrandMagicManager;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.EcmobileManager.RegisterApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class B0_IndexFragment extends BaseFragment implements BusinessResponse,XListView.IXListViewListener, RegisterApp, OnMessageContResponse
{
    private ViewPager bannerViewPager;
    private PageIndicator mIndicator;
    private MyListView mListView;
    private B0_IndexAdapter listAdapter;

    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bannerPageAdapter;
    CircleFrameLayout bannerView;

    private View mTouchTarget;
    private ShoppingCartModel shoppingCartModel;

	private HomeModel dataModel ;
    private MsgModel msgModel;

	private ImageView back;
	private TextView title;
    //private LinearLayout title_right_button;
    private TextView headUnreadTextView;
	
    private SharedPreferences shared;
	private SharedPreferences.Editor editor;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private LinearLayout b0_index_banner_and_button;
    private ImageView all_category_btn;
    private ImageView check_points_btn;
    private ImageView connect_teacher_btn;
    private ImageView notification_btn;

    private EditText input;
    private ImageView search_search;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        View mainView = inflater.inflate(R.layout.b0_index,null);

        input = (EditText) mainView.findViewById(R.id.search_input);
        search_search = (ImageView) mainView.findViewById(R.id.search_search);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    try
                    {
                        searchByKeyWord();
                        return true;

                    }
                    catch (JSONException e)
                    {

                    }
                }
                return false;
            }
        });

        search_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    searchByKeyWord();

                }
                catch (JSONException e)
                {

                }
            }
        });


        if (null == MsgModel.getInstance())
        {
            msgModel = new MsgModel(getActivity());
        }
        else
        {
            msgModel = MsgModel.getInstance();
        }

        if (null == dataModel)
        {
            dataModel = new HomeModel(getActivity());
            dataModel.fetchHotSelling();
            dataModel.fetchCategoryGoods();
        }


        //msgModel.addResponseListener(this);
        //msgModel.getUnreadMessageCount();
        
        
        if (null == ConfigModel.getInstance())
        {
            ConfigModel configModel = new ConfigModel(getActivity());
            configModel.getConfig();
        }

        dataModel.addResponseListener(this);

        //bannerView = (CircleFrameLayout)LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner, null);
        b0_index_banner_and_button = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner_and_button, null);
        headUnreadTextView = (TextView)b0_index_banner_and_button.findViewById(R.id.head_unread_num);

        all_category_btn = (ImageView) b0_index_banner_and_button.findViewById(R.id.all_category);
        all_category_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), D0_AllCategoryActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);

            }
        });

        connect_teacher_btn = (ImageView) b0_index_banner_and_button.findViewById(R.id.connect_teacher);
        connect_teacher_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
            }
        });

        check_points_btn = (ImageView) b0_index_banner_and_button.findViewById(R.id.check_integral);
        check_points_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserIntegralActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);

            }
        });
        notification_btn = (ImageView) b0_index_banner_and_button.findViewById(R.id.notification_btn);
        notification_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                msgModel.unreadCount = 0;
                headUnreadTextView.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), G3_MessageActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in,
                        R.anim.push_right_out);
            }
        });


        bannerView = (CircleFrameLayout)b0_index_banner_and_button.findViewById(R.id.index_banner);
        bannerViewPager = (ViewPager) bannerView.findViewById(R.id.banner_viewpager);
        
        LayoutParams params1 = bannerViewPager.getLayoutParams();
		params1.width = getDisplayMetricsWidth();
		params1.height = (int) (params1.width*1.0/484*200);
		
		bannerViewPager.setLayoutParams(params1);

        bannerListView = new ArrayList<View>();

        
        bannerPageAdapter = new Bee_PageAdapter(bannerListView);

        bannerViewPager.setAdapter(bannerPageAdapter);
//        bannerViewPager.setCurrentItem(0);

        mIndicator = (PageIndicator)bannerView.findViewById(R.id.indicator);
        mIndicator.setViewPager(bannerViewPager);

        bannerView.setPageIndicator(mIndicator);
        bannerView.setViewPager(bannerViewPager);

        mListView = (MyListView)mainView.findViewById(R.id.home_listview);
        mListView.addHeaderView(b0_index_banner_and_button);
        mListView.bannerView = bannerView;

        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this,0);
        mListView.setRefreshTime();

        homeSetAdapter();

		ShoppingCartModel shoppingCartModel = new ShoppingCartModel(getActivity());
		shoppingCartModel.addResponseListener(this);
		shoppingCartModel.homeCartList();

//        bannerViewPager.startImageCycle();
        return mainView;
    }

    private void searchByKeyWord() throws JSONException {
        Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
        FILTER filter = new FILTER();
        filter.keywords = input.getText().toString().toString();
        intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }


    public boolean isActive = false;
    @Override
    public void onResume() {
        super.onResume();
       
        if (!isActive) {
            isActive = true;
//            EcmobileManager.registerApp(this);
//    		EcmobileManager.startWork(getActivity(), ECMobileAppConst.AppId, ECMobileAppConst.AppKey);

//            GrandMagicManager.registerApp(this);
            GrandMagicManager.startWork(getActivity(), ECMobileAppConst.AppId, ECMobileAppConst.AppKey);
        }
        
        msgModel.getMessageCont();
        msgModel.messageContCallBack(this);
        
        LoginModel loginModel = new LoginModel(getActivity());
		
		ConfigModel configModel = new ConfigModel(getActivity());
        configModel.getConfig();

        //zhangmengqi begin
        bannerView.startImageCycle();
        //zhangmengqi end
    }

    public void homeSetAdapter() {
    	if(dataModel.homeDataCache() != null) {
          if (null == listAdapter)
          {
              listAdapter = new B0_IndexAdapter(getActivity(), dataModel);

          }
          mListView.setAdapter(listAdapter);
          addBannerView();
    	}
    	
    	
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
    {
        if (url.endsWith(ApiInterface.HOME_DATA))
        {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter)
            {
                listAdapter = new B0_IndexAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        }
        else if (url.endsWith(ApiInterface.HOME_CATEGORY))
        {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter)
            {
                listAdapter = new B0_IndexAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        } 
        else if (url.endsWith(ApiInterface.CART_LIST))
        {
        	TabsFragment.setShoppingcartNum();
		}
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {    
    	super.onDestroy();
    	dataModel.removeResponseListener(this);
        bannerView.pushImageCycle();
    }

    public void onRefresh(int id)
    {

        dataModel.fetchHotSelling();
        dataModel.fetchCategoryGoods();

    }

    @Override
    public void onLoadMore(int id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addBannerView()
    {
        bannerListView.clear();
        for (int i = 0; i < dataModel.playersList.size(); i++)
        {
            PLAYER player = dataModel.playersList.get(i);
            ImageView  viewOne =  (ImageView)LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner_cell,null);

            shared = getActivity().getSharedPreferences("userInfo", 0); 
    		editor = shared.edit();
    		String imageType = shared.getString("imageType", "mind");
    		
    		if(imageType.equals("high")) {
                imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
    		} else if(imageType.equals("low")) {
                imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
    		} else {
    			String netType = shared.getString("netType", "wifi");
    			if(netType.equals("wifi")) {
                    imageLoader.displayImage(player.photo.thumb,viewOne, EcmobileApp.options);
    			} else {
                    imageLoader.displayImage(player.photo.small,viewOne, EcmobileApp.options);
    			}
    		}
            
            try
            {
                viewOne.setTag(player.toJson().toString());
            }
            catch (JSONException e)
            {

            }

            bannerListView.add(viewOne);

            viewOne.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    String playerJSONString = (String) v.getTag();

                    try {
                        JSONObject jsonObject = new JSONObject(playerJSONString);
                        PLAYER player1 = new PLAYER();
                         player1.fromJson(jsonObject);
                        if (null == player1.action)
                        {
                            if (null != player1.url) {
                                Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }
                        else
                        {
                            if (player1.action.equals("goods"))
                            {
                                Intent intent = new Intent(getActivity(), B2_ProductDetailActivity.class);
                                intent.putExtra("good_id", player1.action_id+"");
                                getActivity().startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (player1.action.equals("category"))
                            {
                                Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                                FILTER filter = new FILTER();
                                filter.category_id = String.valueOf(player1.action_id);
                                intent.putExtra(B1_ProductListActivity.FILTER,filter.toJson().toString());
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                            else if (null != player1.url)
                            {
                                Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                                intent.putExtra("url", player1.url);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.push_right_in,
                                        R.anim.push_right_out);
                            }
                        }

                    } catch (JSONException e) {

                    }

                }
            });

        }

        mIndicator.notifyDataSetChanged();
        mIndicator.setCurrentItem(0);
        bannerPageAdapter.mListViews = bannerListView;
        bannerViewPager.setAdapter(bannerPageAdapter);
        bannerView.startImageCycle();

    }
    
	//获取屏幕宽度
	public int getDisplayMetricsWidth() {
		int i = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		int j = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		return Math.min(i, j);
	}


	@Override
	public void onRegisterResponse(boolean success) {
		 
	}


    @Override
    public void onPause() {
        super.onPause();
        //zhangmengqi begin
        bannerView.pushImageCycle();
        //zhangmengqi end

    }
    
    @Override
    public void onStop() {
    	 
    	super.onStop();
    	if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;
        }
    }
    
    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getActivity().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getActivity().getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
        	return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    
	@Override
	public void onMessageContResponse(JSONObject response) {
		 
		if (msgModel.unreadCount > 0)
        {
            headUnreadTextView.setVisibility(View.VISIBLE);
            headUnreadTextView.setText(""+msgModel.unreadCount);
        }
         else
        {
            headUnreadTextView.setVisibility(View.GONE);
        }
	}

}
