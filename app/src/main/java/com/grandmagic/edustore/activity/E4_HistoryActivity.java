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
//待付款。待收货等

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.androidquery.util.IRequest;
import com.external.maxwin.view.XListView;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.MyDialog;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.ECMobileAppConst;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.E4_HistoryAdapter;
import com.grandmagic.edustore.model.OrderModel;
import com.grandmagic.edustore.model.ShoppingCartModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.GOODORDER;
import com.grandmagic.edustore.protocol.ORDER_INFO;
import com.grandmagic.edustore.protocol.PAYMENT;
import com.grandmagic.edustore.protocol.wxbeforepayResponse;
import com.grandmagic.grandMagicManager.GrandMagicManager;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.unionpay.UPPayAssistEx;
//import com.unionpay.uppay.PayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.grandmagic.BeeFramework.BeeFrameworkConst.TAG;

public class E4_HistoryActivity extends BaseActivity implements BusinessResponse, IXListViewListener {
public static final int REQUEST_REFUND=101;
    private String flag;
    private TextView title;
    private ImageView back;
    private XListView xlistView;
    private E4_HistoryAdapter tradeAdapter;
    private View null_paView;
    private OrderModel orderModel;
    public Handler messageHandler;
    private MyDialog mDialog;
    private String UPPay_mMode = "00";
    private ORDER_INFO order_info;
    private final static int REQUEST_ALIPAY = 7;
    private final static int REQUEST_Pay_Web = 8;
    private final static int REQUEST_UPPay = 10;
    private final static int REQUEST_SelectPay = 11;//选择支付方式


    private IWXAPI mWeixinAPI = null;
    private ShoppingCartModel mShoppingCartModel;
    private Resources mResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mResource = (Resources) getBaseContext().getResources();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.e4_history);

        mWeixinAPI = WXAPIFactory.createWXAPI(this, GrandMagicManager.getWeixinAppId(this));
        // 将该app注册到微信
        mWeixinAPI.registerApp(GrandMagicManager.getWeixinAppId(this));

        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");

        title = (TextView) findViewById(R.id.top_view_text);

        back = (ImageView) findViewById(R.id.top_view_back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        null_paView = findViewById(R.id.null_pager);
        xlistView = (XListView) findViewById(R.id.trade_list);
        xlistView.setPullLoadEnable(true);
        xlistView.setRefreshTime();
        xlistView.setXListViewListener(this, 1);

        orderModel = new OrderModel(this);
        orderModel.addResponseListener(this);

        mShoppingCartModel = new ShoppingCartModel(this);
        mShoppingCartModel.addResponseListener(this);

        requestOrder();
        messageHandler = new Handler() {

            public void handleMessage(final Message msg) {

                if (msg.what == 1) {
                    GOODORDER order = (GOODORDER) msg.obj;
                    order_info = order.order_info;
                    Intent selectPayment = new Intent(E4_HistoryActivity.this, C2_PaymentActivity.class);
                    startActivityForResult(selectPayment, REQUEST_SelectPay);
                } else if (msg.what == 2) {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String exit = resource.getString(R.string.balance_cancel_or_not);
                    String exiten = resource.getString(R.string.prompt);
                    final MyDialog cancelOrders = new MyDialog(E4_HistoryActivity.this, exiten, exit);
                    cancelOrders.show();
                    cancelOrders.positive.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelOrders.dismiss();
                            GOODORDER order = (GOODORDER) msg.obj;
                            order_info = order.order_info;
                            orderModel.orderCancle(order_info.order_id);

                        }
                    });
                    cancelOrders.negative.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cancelOrders.dismiss();
                        }
                    });

                } else if (msg.what == 3) {
                    GOODORDER order = (GOODORDER) msg.obj;
                    order_info = order.order_info;
                    orderModel.affirmReceived(order_info.order_id);
                }

            }
        };
    }

    private void requestOrder() {
        String awa = mResource.getString(R.string.await_pay);
        String ship = mResource.getString(R.string.await_ship);
        String shipped = mResource.getString(R.string.shipped);
        String fin = mResource.getString(R.string.profile_history);

        if (flag.equals("await_pay")) {
            title.setText(awa);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("await_pay");
        } else if (flag.equals("await_ship")) {
            title.setText(ship);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("await_ship");

        } else if (flag.equals("shipped")) {
            title.setText(shipped);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("shipped");

        } else if (flag.equals("finished")) {
            title.setText(fin);
            /**
             * 在这里请求数据
             */
            orderModel.getOrder("finished");
        }
    }

    public void setOrder() {

        Resources resource = (Resources) getBaseContext().getResources();
        String nodata = resource.getString(R.string.no_data);
        if (orderModel.order_list.size() == 0) {
            null_paView.setVisibility(View.VISIBLE);
            xlistView.setVisibility(View.GONE);
        } else {
            null_paView.setVisibility(View.GONE);
            xlistView.setVisibility(View.VISIBLE);
        }

        if (flag.equals("await_pay")) {

            if (tradeAdapter == null) {
                tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 1);
                xlistView.setAdapter(tradeAdapter);
            } else {
                tradeAdapter.list = orderModel.order_list;
                tradeAdapter.notifyDataSetChanged();
            }
            tradeAdapter.parentHandler = messageHandler;

        } else if (flag.equals("await_ship")) {
            if (tradeAdapter == null) {
                tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 2);
                xlistView.setAdapter(tradeAdapter);
            } else {
                tradeAdapter.list = orderModel.order_list;
                tradeAdapter.notifyDataSetChanged();
            }
            tradeAdapter.parentHandler = messageHandler;


        } else if (flag.equals("shipped")) {
            if (tradeAdapter == null) {
                tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 3);
                xlistView.setAdapter(tradeAdapter);
            } else {
                tradeAdapter.list = orderModel.order_list;
                tradeAdapter.notifyDataSetChanged();
            }

            tradeAdapter.parentHandler = messageHandler;

        } else if (flag.equals("finished")) {

            if (tradeAdapter == null) {
                tradeAdapter = new E4_HistoryAdapter(this, orderModel.order_list, 4);
                xlistView.setAdapter(tradeAdapter);
            } else {
                tradeAdapter.list = orderModel.order_list;
                tradeAdapter.notifyDataSetChanged();
            }

            tradeAdapter.parentHandler = messageHandler;

        }

    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        Resources resource = (Resources) getBaseContext().getResources();
        xlistView.stopRefresh();
        xlistView.stopLoadMore();
        if (url.endsWith(ApiInterface.ORDER_LIST)) {
            xlistView.setRefreshTime();
            if (orderModel.paginated.more == 0) {
                xlistView.setPullLoadEnable(false);
            } else {
                xlistView.setPullLoadEnable(true);
            }
            setOrder();
        } else if (url.endsWith(ApiInterface.ORDER_PAY)) {
            String pay_wap = orderModel.pay_wap;
            String pay_online = orderModel.pay_online;
            String upop_tn = orderModel.upop_tn;
            if (upop_tn != null && !"".equals(upop_tn)) {
                //银联sdk支付
//                UPPayAssistEx.startPayByJAR(E4_HistoryActivity.this, PayActivity.class, null, null, upop_tn, UPPay_mMode);
            } else if (pay_wap != null && !"".equals(pay_wap)) {
                //wap支付
                Intent intent = new Intent(this, PayWebActivity.class);
                intent.putExtra(PayWebActivity.PAY_URL, pay_wap);
                startActivityForResult(intent, REQUEST_Pay_Web);
            } else if (pay_online != null && !"".equals(pay_online)) {
                //其他方式
                Intent intent = new Intent(this, OtherPayWebActivity.class);
                intent.putExtra("html", pay_online);
                startActivity(intent);
            }
        } else if (url.endsWith(ApiInterface.ORDER_CANCLE)) {
            orderModel.getOrder(flag);
        } else if (url.endsWith(ApiInterface.ORDER_AFFIRMRECEIVED)) {

            String suc = resource.getString(R.string.successful_operation);
            String check = resource.getString(R.string.check_or_not);
            mDialog = new MyDialog(this, suc, check);
            mDialog.show();
            mDialog.positive.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    Intent intent = new Intent(E4_HistoryActivity.this, E4_HistoryActivity.class);
                    intent.putExtra("flag", "finished");
                    startActivity(intent);
                    finish();
                }
            });
            mDialog.negative.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });

            orderModel.getOrder(flag);

        } else if (url.endsWith(ECMobileAppConst.WEIXIN_PAY_REQUEST_URL)) {
            wxbeforepayResponse response = new wxbeforepayResponse();
            response.fromJson(jo);

            PayReq req = new PayReq();
            req.appId = GrandMagicManager.getWeixinAppId(this);
            req.partnerId = GrandMagicManager.getWeixinAppPartnerId(this);

            req.prepayId = response.prepayid;
            req.nonceStr = response.noncestr;
            req.timeStamp = response.timestamp;
            req.packageValue = response.wx_package;//"Sign=" + packageValue;
            req.sign = response.sign;

            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信

            mWeixinAPI.sendReq(req);
        }

    }

    @Override
    public void onRefresh(int id) {
        orderModel.getOrder(flag);
    }

    @Override
    public void onLoadMore(int id) {
        orderModel.getOrderMore(flag);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPPay) {
            if (data == null) {
                return;
            }
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(E4_HistoryActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(E4_HistoryActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        orderModel.getOrder(flag);
                    }
                });
            } else if (str.equalsIgnoreCase("fail") || str.equals("cancel")) {
                ToastView toast = new ToastView(E4_HistoryActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else if (requestCode == REQUEST_ALIPAY) {
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                orderModel.getOrder(flag);
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(E4_HistoryActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(E4_HistoryActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(E4_HistoryActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        } else if (requestCode == REQUEST_Pay_Web) {
            if (data == null) {
                return;
            }
            String str = data.getExtras().getString("pay_result");
            if (str.equalsIgnoreCase("success")) {
                orderModel.getOrder(flag);
                Resources resource = getResources();
                String exit = resource.getString(R.string.pay_success);
                String exiten = resource.getString(R.string.continue_shopping_or_not);
                final MyDialog mDialog = new MyDialog(E4_HistoryActivity.this, exit, exiten);
                mDialog.show();
                mDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        Intent it = new Intent(E4_HistoryActivity.this, EcmobileMainActivity.class);
                        startActivity(it);
                        finish();

                    }
                });
                mDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();

                    }
                });
            } else if (str.equalsIgnoreCase("fail")) {
                ToastView toast = new ToastView(E4_HistoryActivity.this, getResources().getString(R.string.pay_failed));
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        } else if (requestCode == REQUEST_SelectPay) {
            if (data == null) return;
            String paymentString = data.getStringExtra("payment");
            try {
                JSONObject paymentJSONObject = new JSONObject(paymentString);
                PAYMENT payment = new PAYMENT();
                payment.fromJson(paymentJSONObject);
                order_info.pay_code = payment.pay_code;
            } catch (JSONException e) {

            }
//            更新订单信息
            orderModel.updatePaymentOfOrder(order_info, new IRequest<Boolean>() {
                @Override
                public void request(Boolean b) {
                    if (b) requestPay();
                }
            });

        }else if (REQUEST_REFUND==requestCode){
          requestOrder();
        }
    }

    /**
     * 拉起支付
     */
    private void requestPay() {
        if (GrandMagicManager.getAlipayCallback(getApplicationContext()) != null
                && GrandMagicManager.getAlipayParterId(getApplicationContext()) != null
                && GrandMagicManager.getAlipaySellerId(getApplicationContext()) != null
                && GrandMagicManager.getRsaAlipayPublic(getApplicationContext()) != null) {
            if (0 == order_info.pay_code.compareTo("alipay")) {
//                            showAlipayDialog();
                Intent intent = new Intent(E4_HistoryActivity.this, AlixPayActivity.class);
                intent.putExtra(AlixPayActivity.ORDER_INFO, order_info);
                startActivityForResult(intent, REQUEST_ALIPAY);
            } else if (0 == order_info.pay_code.compareTo("upop")) {
                orderModel.orderPay(order_info.order_id);
            } else if (0 == order_info.pay_code.compareTo("tenpay")) {
                orderModel.orderPay(order_info.order_id);
            } else if (0 == order_info.pay_code.compareTo("wxpay")) {
                if (!(mWeixinAPI.isWXAppInstalled() && mWeixinAPI.isWXAppSupportAPI())) {
                    Resources resource = (Resources) getBaseContext().getResources();
                    String install_wechat = resource.getString(R.string.install_wechat);
                    ToastView toast = new ToastView(E4_HistoryActivity.this, install_wechat);
                    String use = resource.getString(R.string.use);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                mShoppingCartModel.wxpayWXBeforePay(order_info.order_id);
            } else {
                orderModel.orderPay(order_info.order_id);
            }
        } else {
            orderModel.orderPay(order_info.order_id);
        }
    }

    private void showAlipayDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alipay_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout alipayLayout = (LinearLayout) view.findViewById(R.id.alipay);
        LinearLayout alipayWapLayout = (LinearLayout) view.findViewById(R.id.alipay_wap);

        alipayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(E4_HistoryActivity.this, AlixPayActivity.class);
                intent.putExtra(AlixPayActivity.ORDER_INFO, order_info);
                startActivityForResult(intent, REQUEST_ALIPAY);
            }
        });

        alipayWapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                orderModel.orderPay(order_info.order_id);
            }
        });
    }
}
