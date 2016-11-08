package com.external.alipay;

import android.content.Context;

import com.grandmagic.grandMagicManager.GrandMagicManager;


public class PartnerConfig {
//
//    // 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
//    public static final String PARTNER = "2088011180065329";
//    // 商户收款的支付宝账号
//   public static final String SELLER = "2088011180065329";
//    // 商户（RSA）私钥
//    public static final String RSA_PRIVATE = "MIICXQIBAAKBgQDOQegmOwoDwVckJ74a1fXpIv1Uq+kYjv6mu18j/VboSAvyxZwtKYJIkCYeyE1mJMoyv5r5K/G8imZZTa8czVMdC/W/q3ProuR0BDJuf3aKpF/HonvP0jbF8JVsGnhJDZWvxiRb8ewWUxdsbMHRLzD9DElB/8lV19XiI+lxFCcLjQIDAQABAoGBAMsMHMMOGfTKb8PbEA4RFi410Nh7CFSx6MUw7h9a9iRjUMfle7MGCwjuR2jEXVWx4BAHikPD3A103f8KN9qN00OeD6166nqFkLkK+8EJly6PTrh2EQhwyNQX69EzWtZzfcYzKKEgbhSWtvjfRcY7YNgGmkUxlvKyZo/jcBL1MLkBAkEA+dv/j7rk4s2Q8n3yZTkYtcp6CBsUWguTjhVwFIuDvJ7DIuKK8kuh0hWFeSI6XVVxB0N9o4V7fhleJb9JvnXUzQJBANNTlcagWBC3lBaePo3noC4vL1sxvOcOT4a/WyZnAhJ484OyXLKiycWecnzL9OtDmcxsLTO5VrZoIM1W19LwEcECQH504u5Kp+dGJTMlnVWvxRrU5FhP5EgCjeaeYQB07/K544c3Yx1oO/lI3SnGsgVk5fTIKsFlIHBcxHKUUu1rU40CQHhQXjV3lKvv2KAH+BVSih/BPXTuHF5wB1bwGzUB8GwT8JBKO/x4MegRDZ1/gTFwi1+XNyT7oCK5pfNCPh4aEAECQQCULF2GybwoBKZ4jWdCfQ+RiRTT0qLUkZrdaWePVgsPrOoY6+C5/yrY+KN1lJLZbyFNbDfMDmKmEjehAaZyKu9N";
//    // 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
//    public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEN/F3Ll7MQLuxe7ma6+6jOsz+L69P9TvcuMBrRnhBnCkTbObT7I6ElnKY5OsTHKpQ7PnFasNiKPI2xbaa3hOECjbWE3JjW+1Qt0K5tZvK0OYv4FRAYBF8doKNJgwW1UhS/3lRbDY3DuogNaICCJnk1hOd1bHowOZTE0c4jHLmAwIDAQAB";
//    // 支付宝回调地址
//    public static final String ALIPAY_CALLBACK = "http://shop.ecmobile.me/ecmobile/respond.php";
    // 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致 
    public static final String ALIPAY_PLUGIN_NAME = "alipay_msp.apk";

    public String PARTNER;
    public String SELLER;
    public String RSA_PRIVATE;
    public String RSA_ALIPAY_PUBLIC;
    public String ALIPAY_CALLBACK;
    public Context mContext;
    
    public PartnerConfig(Context context) {
    	PARTNER = GrandMagicManager.getAlipayParterId(context);
    	SELLER = GrandMagicManager.getAlipaySellerId(context);
    	RSA_PRIVATE = GrandMagicManager.getRsaPrivate(context);
    	RSA_ALIPAY_PUBLIC = GrandMagicManager.getRsaAlipayPublic(context);
    	ALIPAY_CALLBACK = GrandMagicManager.getAlipayCallback(context);


    }


}

