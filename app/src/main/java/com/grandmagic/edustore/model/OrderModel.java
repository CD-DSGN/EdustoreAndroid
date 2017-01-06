package com.grandmagic.edustore.model;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.external.androidquery.util.IRequest;
import com.grandmagic.BeeFramework.view.MyProgressDialog;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.grandMagicManager.GrandMagicManager;

import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.external.androidquery.callback.AjaxStatus;
import com.grandmagic.BeeFramework.model.BaseModel;
import com.grandmagic.BeeFramework.model.BeeCallback;

public class OrderModel extends BaseModel {

    private int page = 1;
    public PAGINATED paginated;

    public ArrayList<GOODORDER> order_list = new ArrayList<GOODORDER>();
    public ArrayList<EXPRESS> express_list = new ArrayList<EXPRESS>();
    public String pay_wap = "";
    public String pay_online = "";
    public String upop_tn = "";
    public String shipping_name;

    public OrderModel(Context context) {
        super(context);

    }

    public void getOrder(String type) {
        page = 1;
        orderlistRequest request = new orderlistRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                OrderModel.this.callback(url, jo, status);
                try {
                    orderlistResponse response = new orderlistResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {
                            order_list.clear();
                            ArrayList<GOODORDER> data = response.data;
                            if (null != data && data.size() > 0) {
                                order_list.addAll(data);
                            }
                            paginated = response.paginated;
                        }
                        OrderModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        SESSION session = SESSION.getInstance();
        PAGINATION pagination = new PAGINATION();
        pagination.page = 1;
        pagination.count = 10;

        request.session = session;
        request.pagination = pagination;
        request.type = type;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.ORDER_LIST).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    public void getOrderMore(String type) {

        final orderlistRequest request = new orderlistRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                OrderModel.this.callback(url, jo, status);

                try {
                    orderlistResponse response = new orderlistResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {

                            ArrayList<GOODORDER> data = response.data;

                            if (null != data && data.size() > 0) {

                                for (int i = 0; i < data.size(); i++) {
                                    order_list.addAll(data);
                                }
                            }

                            paginated = response.paginated;

                        }
                    }
                    OrderModel.this.OnMessageResponse(url, jo, status);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        SESSION session = SESSION.getInstance();
        PAGINATION pagination = new PAGINATION();
        pagination.page = order_list.size() / 10 + 1;
        pagination.count = 10;

        request.session = session;
        request.pagination = pagination;
        request.type = type;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.ORDER_LIST).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

    public void orderPay(int order_id) {

        orderpayRequest request = new orderpayRequest();

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                OrderModel.this.callback(url, jo, status);
                try {
                    orderpayResponse response = new orderpayResponse();
                    response.fromJson(jo);

                    if (jo != null) {
                        pay_online = response.data.pay_online;
                        pay_wap = response.data.pay_wap;
                        upop_tn = response.data.upop_tn;
                        OrderModel.this.OnMessageResponse(url, jo, status);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

        };

        request.session = SESSION.getInstance();
        request.order_id = order_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }


        cb.url(ApiInterface.ORDER_PAY).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);

    }

    // 取消订单
    public void orderCancle(int order_id) {

        ordercancleRequest request = new ordercancleRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                OrderModel.this.callback(url, jo, status);
                try {
                    ordercancleResponse response = new ordercancleResponse();
                    response.fromJson(jo);
                    if (jo != null) {

                        if (response.status.succeed == 1) {

                            OrderModel.this.OnMessageResponse(url, jo, status);
                        }
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

        };

        request.session = SESSION.getInstance();
        request.order_id = order_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.ORDER_CANCLE).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }


    // 确认收货
    public void affirmReceived(int order_id) {
        orderaffirmReceivedRequest request = new orderaffirmReceivedRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                OrderModel.this.callback(url, jo, status);
                try {
                    orderaffirmReceivedResponse response = new orderaffirmReceivedResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {
                            OrderModel.this.OnMessageResponse(url, jo, status);
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        };

        request.session = SESSION.getInstance();
        request.order_id = order_id;

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }
        ;

        cb.url(ApiInterface.ORDER_AFFIRMRECEIVED).type(JSONObject.class).params(params);
        aq.ajax(cb);

    }

    // 查看物流
    public void orderExpress(String order_id) {

        orderexpressRequest request = new orderexpressRequest();
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {

                OrderModel.this.callback(url, jo, status);

                try {
                    orderexpressResponse response = new orderexpressResponse();
                    response.fromJson(jo);
                    if (jo != null) {
                        if (response.status.succeed == 1) {

                            ORDER_EXPRESS_DATA data = response.data;

                            shipping_name = data.shipping_name;
                            ArrayList<EXPRESS> content = data.content;
                            if (null != content && content.size() > 0) {
                                express_list.clear();
                                express_list.addAll(content);

                            }
                        }
                        OrderModel.this.OnMessageResponse(url, jo, status);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        };

        request.session = SESSION.getInstance();
        request.order_id = order_id;
        request.app_key = GrandMagicManager.getKuaidiKey(mContext);
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {
            // TODO: handle exception
        }

        cb.url(ApiInterface.ORDER_EXPRESS).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }

    public void updatePaymentOfOrder(ORDER_INFO order_info, final IRequest<Boolean> iRequest) {
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                OrderModel.this.callback(url, jo, status);
                int reStatus=0;
                try {
                    reStatus = jo.optJSONObject("data").optInt("update_success");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (reStatus!=1) new ToastView(mContext, R.string.error_8).show();
                }
                iRequest.request(reStatus == 1);
            }
        };
        Map<String, String> params = new HashMap<>();
        JSONObject requestjson = new JSONObject();
        try {
            requestjson.put("session", SESSION.getInstance().toJson());
            requestjson.put("pay_code", order_info.pay_code);
            requestjson.put("order_sn", order_info.order_sn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        params.put("json", requestjson.toString());
        cb.url(ApiInterface.UPDATE_PAYMENT_ORDER).type(JSONObject.class).params(params);
        MyProgressDialog pd = new MyProgressDialog(mContext, mContext.getResources().getString(R.string.hold_on));
        aq.progress(pd.mDialog).ajax(cb);
    }
}
