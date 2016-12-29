# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\chenggaoyuan\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#阿里支付混淆规则


-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

#微信支付混淆规则
-keep class com.tencent.** { *;}

#友盟混淆
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keep public class [com.grandmagic.edustore].R$*{
public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#ConvenientBanner不需要混淆

#BeeFrameWork不混淆
#-keep class com.external.** {*;}

#model类会被反射，因此不混淆
-keep class com.edustore.model.** {*;}
-keep class com.edustore.protocol.** {*;}

#科大讯飞类不要混淆
-keep class com.iflytek.speech.** {*;}
-keepattributes Signature

#百度云推送不要混淆
-dontwarn com.baidu.**
-keep class com.baidu.** { *;}

#httpclient不要混淆
-keep class com.external.** {*;}

#银联支付不要混淆
#-keep class com.unionpay.**{*;}

#http不要混淆
-dontwarn org.apache.**
-keep class org.apache.** {*;}

#parcelable不混淆
-keep class * implements Android.os.Parcelable { # 保持Parcelable不被混淆
    public static final Android.os.Parcelable$Creator *;
}

#新浪微博不混淆
-keep class com.sina.** {*;}

#image-loader不要被混淆
-dontwarn com.nostra13.universalimageloader.**
-keep class com.nostra13.universalimageloader.** { *; }

#保证natvie方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#腾讯微博不要混淆
-dontwarn com.tencent.**

#eventbus
-keep class de.greenrobot.event.** {*;}
-keepclassmembers class ** {
    public void onEvent*(**);
    void onEvent*(**);
}

