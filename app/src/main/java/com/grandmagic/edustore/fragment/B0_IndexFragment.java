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

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.external.viewpagerindicator.PageIndicator;
import com.grandmagic.BeeFramework.fragment.BaseFragment;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.MyDialog;
import com.grandmagic.BeeFramework.view.MyListView;
import com.grandmagic.edustore.ECMobileAppConst;
import com.grandmagic.edustore.EcmobileApp;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.A0_SigninActivity;
import com.grandmagic.edustore.activity.B1_ProductListActivity;
import com.grandmagic.edustore.activity.B2_ProductDetailActivity;
import com.grandmagic.edustore.activity.BannerWebActivity;
import com.grandmagic.edustore.activity.D0_AllCategoryActivity;
import com.grandmagic.edustore.activity.G0_SettingActivity;
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
import com.grandmagic.edustore.model.SimpleUserInfoModel;
import com.grandmagic.edustore.model.UserInfoModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.FILTER;
import com.grandmagic.edustore.protocol.PLAYER;
import com.grandmagic.edustore.protocol.USER;
import com.grandmagic.grandMagicManager.GrandMagicManager;
import com.iflytek.cloud.resource.Resource;
import com.insthub.ecmobile.EcmobileManager;
import com.insthub.ecmobile.EcmobileManager.RegisterApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class B0_IndexFragment extends BaseFragment implements BusinessResponse, XListView.IXListViewListener, RegisterApp, OnMessageContResponse {

    private MyListView mListView;
    private B0_IndexAdapter listAdapter;

    private ShoppingCartModel shoppingCartModel;

    private HomeModel dataModel;
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
    private ImageView call_btn;

    private FrameLayout fl_connect_teacher;
    private FrameLayout fl_point;

    private EditText input;
    private ImageView search_search;

    private ConvenientBanner mConvenientBanner;
    private ArrayList<String> images = new ArrayList<String>();
    String imageType;

    private MyDialog mDialog;

    //zhangmengqi begin
    public static  int login_is_teacher = -1; //未登录状态为-1, 0表示学生，1表示教师

    private SimpleUserInfoModel mSimpleUserInfoModel;

    private String tel_num;

    //zhangmengqi end

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = getActivity().getSharedPreferences("userInfo", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        View mainView = inflater.inflate(R.layout.b0_index, null);

        input = (EditText) mainView.findViewById(R.id.search_input);
        search_search = (ImageView) mainView.findViewById(R.id.search_search);

        shared = getActivity().getSharedPreferences("userInfo", 0);
        imageType = shared.getString("imageType", "mind"); //一开始读一次数据

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    try {
                        searchByKeyWord();
                        return true;

                    } catch (JSONException e) {

                    }
                }
                return false;
            }
        });

        search_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    searchByKeyWord();

                } catch (JSONException e) {

                }
            }
        });


        if (null == MsgModel.getInstance()) {
            msgModel = new MsgModel(getActivity());
        } else {
            msgModel = MsgModel.getInstance();
        }

        if (null == dataModel) {
            dataModel = new HomeModel(getActivity());
            dataModel.fetchHotSelling();
            dataModel.fetchCategoryGoods();
        }


        //msgModel.addResponseListener(this);
        //msgModel.getUnreadMessageCount();


        if (null == ConfigModel.getInstance()) {
            ConfigModel configModel = new ConfigModel(getActivity());
            configModel.getConfig();
        }

        dataModel.addResponseListener(this);

        b0_index_banner_and_button = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner_and_button, null);
        headUnreadTextView = (TextView) b0_index_banner_and_button.findViewById(R.id.head_unread_num);

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

        fl_connect_teacher = (FrameLayout) b0_index_banner_and_button.findViewById(R.id.fl_connect_teacher);
        fl_point = (FrameLayout) b0_index_banner_and_button.findViewById(R.id.fl_points);

        connect_teacher_btn = (ImageView) b0_index_banner_and_button.findViewById(R.id.connect_teacher);
        connect_teacher_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断是否登录
                if (login_is_teacher < 0) {
                    startLoginActivity();

                } else if (login_is_teacher == 0) {
                    Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_right_in,
                            R.anim.push_right_out);

                }
            }
        });


        check_points_btn = (ImageView) b0_index_banner_and_button.findViewById(R.id.check_integral);
        check_points_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_is_teacher == 1) {
                    Intent intent = new Intent(getActivity(), UserIntegralActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_right_in,
                            R.anim.push_right_out);
                } else if(login_is_teacher < 0){
                    startLoginActivity();
                }

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

        call_btn = (ImageView) b0_index_banner_and_button.findViewById(R.id.iv_call);
        call_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                call_client_service();
            }
        });



        mConvenientBanner = (ConvenientBanner) b0_index_banner_and_button.findViewById(R.id.index_banner);

        LayoutParams params1 = mConvenientBanner.getLayoutParams();
        params1.width = getDisplayMetricsWidth();
        params1.height = (int) (params1.width * 1.0 / 484 * 200);

        mConvenientBanner.setLayoutParams(params1);

        mListView = (MyListView) mainView.findViewById(R.id.home_listview);
        mListView.addHeaderView(b0_index_banner_and_button);

        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        mListView.setXListViewListener(this, 0);
        mListView.setRefreshTime();

        mSimpleUserInfoModel = new SimpleUserInfoModel(getActivity());
        String uid = shared.getString("uid", "");
        if (uid.equals("")) {
            login_is_teacher = -1;
        }else{
            mSimpleUserInfoModel.getSimpleUserInfo();
        }
        mSimpleUserInfoModel.addResponseListener(this);
        homeSetAdapter();

        ShoppingCartModel shoppingCartModel = new ShoppingCartModel(getActivity());
        shoppingCartModel.addResponseListener(this);
        shoppingCartModel.homeCartList();

        return mainView;
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getActivity(), A0_SigninActivity.class);
        startActivity(intent);
    }

    private void call_client_service() {
        Resources resource = (Resources) getActivity().getResources();
        String call=resource.getString(R.string.call_or_not);
        tel_num = ConfigModel.getInstance().config.service_phone;
        if (TextUtils.isEmpty(tel_num)) {
            if (shared != null) {
                tel_num = shared.getString("service_phone", "");
            }
        }
        if (!TextUtils.isEmpty(tel_num)) {
            mDialog = new MyDialog(getActivity(), call, ConfigModel.getInstance().config.service_phone);
            mDialog.show();
            mDialog.positive.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel_num));
                    startActivity(intent);
                }
            });
            mDialog.negative.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        }else{
            String service_not_available = resource.getString(R.string.service_not_avalaible);
            Toast.makeText(getActivity(), service_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private void searchByKeyWord() throws JSONException {
        Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
        FILTER filter = new FILTER();
        filter.keywords = input.getText().toString().toString();
        intent.putExtra(B1_ProductListActivity.FILTER, filter.toJson().toString());
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


        changeVisibility();
        //zhangmengqi begin
        //        bannerView.startImageCycle();
        mConvenientBanner.startTurning(5000);

        //zhangmengqi end
    }

    private void changeVisibility() {
        if (login_is_teacher == 1) {
            fl_connect_teacher.setVisibility(View.GONE);
            fl_point.setVisibility(View.VISIBLE);
        }else {
            fl_connect_teacher.setVisibility(View.VISIBLE);
            fl_point.setVisibility(View.GONE);
        }
    }

    public void homeSetAdapter() {
        if (dataModel.homeDataCache() != null) {
            if (null == listAdapter) {
                listAdapter = new B0_IndexAdapter(getActivity(), dataModel);

            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        }


    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) {

        imageType = shared.getString("imageType", "mind");
        if (url.endsWith(ApiInterface.HOME_DATA)) {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter) {
                listAdapter = new B0_IndexAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            addBannerView();
        } else if (url.endsWith(ApiInterface.HOME_CATEGORY)) {
            mListView.stopRefresh();
            mListView.setRefreshTime();

            if (null == listAdapter) {
                listAdapter = new B0_IndexAdapter(getActivity(), dataModel);
            }
            mListView.setAdapter(listAdapter);
            //            addBannerView();
        } else if (url.endsWith(ApiInterface.CART_LIST)) {
            TabsFragment.setShoppingcartNum();
        } else if (url.endsWith(ApiInterface.SIMPLE_USER_INFO)) {
            login_is_teacher = mSimpleUserInfoModel.is_teacher;
            changeVisibility();
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

    }

    public void onRefresh(int id) {

        dataModel.fetchHotSelling();
        dataModel.fetchCategoryGoods();

    }

    @Override
    public void onLoadMore(int id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addBannerView() {
        images.clear();
        PLAYER player;
        for (int i = 0; i < dataModel.playersList.size(); i++) {
            player = dataModel.playersList.get(i);
            if (imageType.equals("high")) {
                images.add(player.photo.small);
            } else if (imageType.equals("low")) {
                images.add(player.photo.thumb);
            } else {
                String netType = shared.getString("netType", "wifi");
                if (netType.equals("wifi")) {
                    images.add(player.photo.small);
                } else {
                    images.add(player.photo.thumb);
                }
            }

        }
        mConvenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, images)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new BannerOnItemClickListener());
        mConvenientBanner.notifyDataSetChanged();

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
        mConvenientBanner.stopTurning();
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

        if (msgModel.unreadCount > 0) {
            headUnreadTextView.setVisibility(View.VISIBLE);
            headUnreadTextView.setText("" + msgModel.unreadCount);
        } else {
            headUnreadTextView.setVisibility(View.GONE);
        }
    }

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = (ImageView) LayoutInflater.from(getActivity()).inflate(R.layout.b0_index_banner_cell, null);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            if (imageType.equals("high")) {
                imageLoader.displayImage(data, imageView, EcmobileApp.options);
            } else if (imageType.equals("low")) {
                imageLoader.displayImage(data, imageView, EcmobileApp.options);
            } else {
                String netType = shared.getString("netType", "wifi");
                if (netType.equals("wifi")) {
                    imageLoader.displayImage(data, imageView, EcmobileApp.options);
                } else {
                    imageLoader.displayImage(data, imageView, EcmobileApp.options);
                }
            }
        }
    }


    private class BannerOnItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(int position) {
            if (dataModel.playersList.size() > 0) {
                PLAYER player = dataModel.playersList.get(position);
                try {
                    if (null == player.action) {
                        if (null != player.url) {
                            Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                            intent.putExtra("url", player.url);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                    } else {
                        if (player.action.equals("goods")) {
                            Intent intent = new Intent(getActivity(), B2_ProductDetailActivity.class);
                            intent.putExtra("good_id", player.action_id + "");
                            getActivity().startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        } else if (player.action.equals("category")) {
                            Intent intent = new Intent(getActivity(), B1_ProductListActivity.class);
                            FILTER filter = new FILTER();
                            filter.category_id = String.valueOf(player.action_id);
                            intent.putExtra(B1_ProductListActivity.FILTER, filter.toJson().toString());
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        } else if (null != player.url) {
                            Intent intent = new Intent(getActivity(), BannerWebActivity.class);
                            intent.putExtra("url", player.url);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.push_right_in,
                                    R.anim.push_right_out);
                        }
                    }

                } catch (JSONException e) {

                }

            }
        }
    }
}
