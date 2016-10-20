package com.external.alipay;

import com.insthub.ecmobile.EcmobileManager;

import android.content.Context;

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
//    	PARTNER = EcmobileManager.getAlipayParterId(context);
//    	SELLER = EcmobileManager.getAlipaySellerId(context);
//    	RSA_PRIVATE = EcmobileManager.getRsaPrivate(context);
//    	RSA_ALIPAY_PUBLIC = EcmobileManager.getRsaAlipayPublic(context);
//    	ALIPAY_CALLBACK = EcmobileManager.getAlipayCallback(context);

        //TODO: 2016/10/11 zhangmengqi
        PARTNER = "2088911655683743";
        SELLER = "2088911655683743";
        RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANBMvQSwwr8KfGv5" +
                "JTBBFxengfb4R7sDY1C9aIZbtlE0NqpuRvpbrEYcHhGhO7pOQ9rfI64Jb+gdAJwC" +
                "uzd69BpcmvbCp6O78X7SwgXtV9+IC97QwLxtkDhIrHeTyzXiSudpbiRf46KS1U3g" +
                "FierBpBWNmp3EUTz3MW8LM3gfxc1AgMBAAECgYARq/D9TOG4w3L61hBJn7wNzbBA" +
                "+59aRldOqkML4wv8p6lbnC95Xf2nlQsYA83FaI5pKzUjtrk/v/YlRjYL5up+isk4" +
                "kvQhSo7x6ipsRk195v9uxJy6bmIUYp531YkFL5LIQkaihu5jqstHK6EV8VF+iNyu" +
                "hT1XFJ1TYQwW5mnvAQJBAPZM+jLHJIHwnVnDUu9ab7FORrW8l4mIJk9owJmNLw+Z" +
                "PQFC922K/0DmzMcJMdsMG/+qgoqQdUA/99kQUgtA4Z0CQQDYgKncRrOXjWH5WQP+" +
                "pTVu/Wu3xTN7Nrj8csAAdv6xLRGt1jwpMAAWrTd6pnzia+ldB1R/S5IZEqzPnQnm" +
                "JgR5AkBCyHiG0Cx79ywTLL0OHW1vnBPcLzi/l+UbXwHqILgD+L7r2qaQU0IG7Q3V" +
                "Yg7coBnvZuJig+zm8PFZL+2vE3aZAkBZjGY1kRzJS5ZBj1sCoYzHWpSKT0uq5AiB" +
                "imj2CEHyQKT2VQ1PL+Zper3ewiwXbvD4JIcDm9tS+ZF20gp9Ii5pAkB2TW96cGyr" +
                "PuwQdUzfMY8fP1HjqmPUVc2jmfjicgNWcNOok8/85dQCSQdepwDraszxcjfb86y8" +
                "s7rnvyfUVXNd";
        RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
        ALIPAY_CALLBACK = "http://60.205.92.85/ecmobile/payment/alipay/sdk/notify_url.php";
    }


}

