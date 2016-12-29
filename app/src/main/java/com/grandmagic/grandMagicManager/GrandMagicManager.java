package com.grandmagic.grandMagicManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import com.grandmagic.grandMagicManager.protocal.GrandMagicManagerApiInterface;
import com.grandmagic.grandMagicManager.util.AESEncryptor;
import com.grandmagic.grandMagicManager.util.NetService;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhangmengqi on 2016/11/2.
 */
public class GrandMagicManager {
    private static SharedPreferences shared;
    private static SharedPreferences.Editor editor;
    private static String password = "insthub";

    private static int ERROR = 0;
    private static int API_CONFIG = 1;
    private static int API_PUSH_REGISTER = 2;

    private static Context mContext;

    public GrandMagicManager() {

    }

    public static void startWork(Context context, String appId, String appKey) {
        mContext = context;
        HashMap params = new HashMap();
        params.put("APPID", appId);
        params.put("APPKEY", appKey);
        sendPost(GrandMagicManagerApiInterface.CONFIG, params);
    }

    private static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject json;
            int e;
            if(msg.what == GrandMagicManager.API_CONFIG) {
                json = (JSONObject)msg.obj;
                GrandMagicManager.shared = GrandMagicManager.mContext.getSharedPreferences("config", 0);
                GrandMagicManager.editor = GrandMagicManager.shared.edit();

                try {
                    e = json.optInt("succeed");
                    if(e == 1) {
                        JSONObject push = json.optJSONObject("config");
                        if(push != null) {
                            String isSwitch = push.optString("site_url");
                            String umeng_key = push.optString("umeng_aos_key");
                            String kuaidi100 = push.optString("kuaidi100_key");
                            String ifly_key = push.optString("ifly_aos_key");
                            if(isSwitch != null) {
                                GrandMagicManager.editor.putString("sit_url", AESEncryptor.encrypt(GrandMagicManager.password, isSwitch));
                            }

                            if(umeng_key != null) {
                                GrandMagicManager.editor.putString("umeng_key", AESEncryptor.encrypt(GrandMagicManager.password, umeng_key));
                            }

                            if(kuaidi100 != null) {
                                GrandMagicManager.editor.putString("kuaidi100", AESEncryptor.encrypt(GrandMagicManager.password, kuaidi100));
                            }

                            if(ifly_key != null) {
                                GrandMagicManager.editor.putString("ifly_key", AESEncryptor.encrypt(GrandMagicManager.password, ifly_key));
                            }

                            JSONObject bae_push = push.optJSONObject("bae_push_key");
                            JSONObject alipay = push.optJSONObject("alipay_key");
                            JSONObject weibo = push.optJSONObject("weibo_key");
                            JSONObject tencent = push.optJSONObject("tencent_key");
                            JSONObject weixin_key = push.optJSONObject("weixin_key");
                            String app_id;
                            String app_key;
                            if(bae_push != null) {
                                app_id = bae_push.optString("api_key");
                                app_key = bae_push.optString("secret_key");
                                if(app_id != null) {
                                    GrandMagicManager.editor.putString("push_key", AESEncryptor.encrypt(GrandMagicManager.password, app_id));
                                }

                                if(app_key != null) {
                                    GrandMagicManager.editor.putString("push_seckey", AESEncryptor.encrypt(GrandMagicManager.password, app_key));
                                }
                            }

                            String partner_id;
                            if(alipay != null) {
                                app_id = alipay.optString("parterID");
                                app_key = alipay.optString("sellerID");
                                partner_id = alipay.optString("key");
                                String rsa_alipay_public = alipay.optString("rsa_alipay_public");
                                String rsa_private = alipay.optString("rsa_private");
                                String alipay_callback = alipay.optString("callback");
                                if(app_id != null) {
                                    GrandMagicManager.editor.putString("parterID", AESEncryptor.encrypt(GrandMagicManager.password, app_id));
                                }

                                if(app_key != null) {
                                    GrandMagicManager.editor.putString("sellerID", AESEncryptor.encrypt(GrandMagicManager.password, app_key));
                                }

                                if(partner_id != null) {
                                    GrandMagicManager.editor.putString("alipay_key", AESEncryptor.encrypt(GrandMagicManager.password, partner_id));
                                }

                                if(rsa_alipay_public != null) {
                                    GrandMagicManager.editor.putString("rsa_alipay_public", AESEncryptor.encrypt(GrandMagicManager.password, rsa_alipay_public));
                                }

                                if(rsa_private != null) {
                                    GrandMagicManager.editor.putString("rsa_private", AESEncryptor.encrypt(GrandMagicManager.password, rsa_private));
                                }

                                if(alipay_callback != null) {
                                    GrandMagicManager.editor.putString("alipay_callback", AESEncryptor.encrypt(GrandMagicManager.password, alipay_callback));
                                }
                            }

                            if(weibo != null) {
                                app_id = weibo.optString("app_key");
                                app_key = weibo.optString("app_secret");
                                partner_id = weibo.optString("callback");
                                if(app_id != null) {
                                    GrandMagicManager.editor.putString("weibo_key", AESEncryptor.encrypt(GrandMagicManager.password, app_id));
                                }

                                if(app_key != null) {
                                    GrandMagicManager.editor.putString("weibo_secret", AESEncryptor.encrypt(GrandMagicManager.password, app_key));
                                }

                                if(partner_id != null) {
                                    GrandMagicManager.editor.putString("weibo_callback", AESEncryptor.encrypt(GrandMagicManager.password, partner_id));
                                }
                            }

                            if(tencent != null) {
                                app_id = tencent.optString("app_key");
                                app_key = tencent.optString("app_secret");
                                partner_id = tencent.optString("callback");
                                if(app_id != null) {
                                    GrandMagicManager.editor.putString("tencent_key", AESEncryptor.encrypt(GrandMagicManager.password, app_id));
                                }

                                if(app_key != null) {
                                    GrandMagicManager.editor.putString("tencent_secret", AESEncryptor.encrypt(GrandMagicManager.password, app_key));
                                }

                                if(partner_id != null) {
                                    GrandMagicManager.editor.putString("tencent_callback", AESEncryptor.encrypt(GrandMagicManager.password, partner_id));
                                }
                            }

                            if(weixin_key != null) {
                                app_id = weixin_key.optString("app_id");
                                app_key = weixin_key.optString("app_key");
                                partner_id = weixin_key.optString("partner_id");
                                if(app_id != null) {
                                    GrandMagicManager.editor.putString("weixin_id", AESEncryptor.encrypt(GrandMagicManager.password, app_id));
                                }

                                if(app_key != null) {
                                    GrandMagicManager.editor.putString("weixin_key", AESEncryptor.encrypt(GrandMagicManager.password, app_key));
                                }

                                if(null != partner_id) {
                                    GrandMagicManager.editor.putString("weixin_partner_id", AESEncryptor.encrypt(GrandMagicManager.password, partner_id));
                                }
                            }

                            GrandMagicManager.editor.commit();
                        }

//                        GrandMagicManager.registerApp.onRegisterResponse(true);
                    } else {
                        GrandMagicManager.shared = GrandMagicManager.mContext.getSharedPreferences("config", 0);
                        GrandMagicManager.editor = GrandMagicManager.shared.edit();
                        GrandMagicManager.editor.clear();
                        GrandMagicManager.editor.commit();
//                        GrandMagicManager.registerApp.onRegisterResponse(false);
//                        Intent push1 = new Intent(GrandMagicManager.mContext, AppExpiredAcitivty.class);
//                        GrandMagicManager.mContext.startActivity(push1);
                    }
                } catch (Exception var21) {
//                    GrandMagicManager.registerApp.onRegisterResponse(false);
                }
            } else if(msg.what == GrandMagicManager.API_PUSH_REGISTER) {
                json = (JSONObject)msg.obj;

                try {
                    e = json.optInt("succeed");
                    if(e == 1) {
                        int push2 = json.optInt("push");
                        boolean isSwitch1;
                        if(push2 == 1) {
                            isSwitch1 = true;
                        } else {
                            isSwitch1 = false;
                        }

                        GrandMagicManager.editor.putBoolean("push_switch", isSwitch1);
                        GrandMagicManager.editor.commit();
                    }
                } catch (Exception var20) {
                    var20.printStackTrace();
                }
            } else if(msg.what == GrandMagicManager.ERROR) {
//                GrandMagicManager.registerApp.onRegisterResponse(false);
            }

        }
    };

    public static String getSitUrl(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String sit_url = null;

            try {
                sit_url = AESEncryptor.decrypt(password, shared.getString("sit_url", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(sit_url) ? sit_url : null;
        }
    }

    public static String getUmengKey(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String umeng_key = null;

            try {
                umeng_key = AESEncryptor.decrypt(password, shared.getString("umeng_key", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(umeng_key) ? umeng_key : null;
        }
    }

    public static String getKuaidiKey(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String kuaidi100 = null;

            try {
                kuaidi100 = AESEncryptor.decrypt(password, shared.getString("kuaidi100", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(kuaidi100) ? kuaidi100 : null;
        }
    }


    public static String getXunFeiCode(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String ifly_key = null;

            try {
                ifly_key = AESEncryptor.decrypt(password, shared.getString("ifly_key", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(ifly_key) ? ifly_key : null;
        }
    }


    public static String getPushKey(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String push_key = null;

            try {
                push_key = AESEncryptor.decrypt(password, shared.getString("push_key", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(push_key) ? push_key : null;
        }
    }

    public static String getPushSecKey(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String push_seckey = null;

            try {
                push_seckey = AESEncryptor.decrypt(password, shared.getString("push_seckey", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(push_seckey) ? push_seckey : null;
        }
    }

    public static String getAlipayParterId(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String parterID = null;

            try {
                parterID = AESEncryptor.decrypt(password, shared.getString("parterID", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(parterID) ? parterID : null;
        }
    }

    public static String getAlipaySellerId(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String sellerID = null;

            try {
                sellerID = AESEncryptor.decrypt(password, shared.getString("sellerID", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(sellerID) ? sellerID : null;
        }
    }

    public static String getRsaPrivate(Context context) {
        if(context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String rsa_private = null;

            try {
                rsa_private = AESEncryptor.decrypt(password, shared.getString("rsa_private", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(rsa_private)?rsa_private:null;
        }
    }

    public static String getRsaAlipayPublic(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String rsa_alipay_public = null;

            try {
                rsa_alipay_public = AESEncryptor.decrypt(password, shared.getString("rsa_alipay_public", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(rsa_alipay_public) ? rsa_alipay_public : null;
        }
    }

    public static String getAlipayCallback(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String alipay_callback = null;

            try {
                alipay_callback = AESEncryptor.decrypt(password, shared.getString("alipay_callback", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(alipay_callback) ? alipay_callback : null;
        }
    }

    public static String getSinaKey(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String sinaKey = null;

            try {
                sinaKey = AESEncryptor.decrypt(password, shared.getString("weibo_key", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(sinaKey) ? sinaKey : null;
        }
    }

    public static String getSinaSecret(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String sinaSecret = null;

            try {
                sinaSecret = AESEncryptor.decrypt(password, shared.getString("weibo_secret", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(sinaSecret) ? sinaSecret : null;
        }
    }

    public static String getSinaCallback(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String sinaCallback = null;

            try {
                sinaCallback = AESEncryptor.decrypt(password, shared.getString("weibo_callback", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(sinaCallback) ? sinaCallback : null;
        }
    }

    public static String getWeixinAppId(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String weixin_id = null;

            try {
                weixin_id = AESEncryptor.decrypt(password, shared.getString("weixin_id", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(weixin_id) ? weixin_id : null;
        }
    }

    public static String getWeixinAppKey(Context context) {
        if(context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String weixin_key = null;

            try {
                weixin_key = AESEncryptor.decrypt(password, shared.getString("weixin_key", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(weixin_key)?weixin_key:null;
        }
    }

    public static String getWeixinAppPartnerId(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String weixin_partner_id = null;

            try {
                weixin_partner_id = AESEncryptor.decrypt(password, shared.getString("weixin_partner_id", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(weixin_partner_id) ? weixin_partner_id : null;
        }
    }

    public static String getTencentKey(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String tencent_key = null;

            try {
                tencent_key = AESEncryptor.decrypt(password, shared.getString("tencent_key", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(tencent_key) ? tencent_key : null;
        }
    }

    public static String getTencentSecret(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String tencent_secret = null;

            try {
                tencent_secret = AESEncryptor.decrypt(password, shared.getString("tencent_secret", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(tencent_secret) ? tencent_secret : null;
        }
    }

    public static String getTencentCallback(Context context) {
        if (context == null) {
            return null;
        } else {
            shared = context.getSharedPreferences("config", 0);
            String tencent_callback = null;

            try {
                tencent_callback = AESEncryptor.decrypt(password, shared.getString("tencent_callback", ""));
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return !"".equals(tencent_callback) ? tencent_callback : null;
        }
    }

    private static void sendPost(final String url, final Map<String, String> map) {
        (new Thread() {
            public void run() {
                super.run();
                Object response = null;

                Message msg;
                try {
                    JSONObject e;
                    byte[] response1;
                    if (url.startsWith(GrandMagicManagerApiInterface.CONFIG)) {
                        response1 = NetService.sendPostRequest(url, map, "utf-8");
                        e = new JSONObject(new String(response1));
                        msg = new Message();
                        msg.what = GrandMagicManager.API_CONFIG;
                        msg.obj = e;
                        GrandMagicManager.handler.sendMessage(msg);
                    } else if (url.startsWith(GrandMagicManagerApiInterface.PUSH_REGISTER)) {
                        response1 = NetService.sendPostRequest(url, map, "utf-8");
                        e = new JSONObject(new String(response1));
                        msg = new Message();
                        msg.what = GrandMagicManager.API_PUSH_REGISTER;
                        msg.obj = e;
                        GrandMagicManager.handler.sendMessage(msg);
                    }
                } catch (JSONException var4) {
                    msg = new Message();
                    msg.what = GrandMagicManager.ERROR;
                    GrandMagicManager.handler.sendMessage(msg);
                } catch (Exception var5) {
                    msg = new Message();
                    msg.what = GrandMagicManager.ERROR;
                    GrandMagicManager.handler.sendMessage(msg);
                }

            }
        }).start();
    }


}
