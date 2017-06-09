package com.grandmagic.edustore;
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

import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.grandmagic.BeeFramework.BeeFrameworkApp;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.tencent.bugly.crashreport.CrashReport;

public class EcmobileApp extends BeeFrameworkApp
{
    public static DisplayImageOptions options;		// DisplayImageOptions是用于设置图片显示的类
    public static DisplayImageOptions options_head;		// DisplayImageOptions是用于设置图片显示的类
    public static DisplayImageOptions options_head_circle;
    public static DisplayImageOptions options_avartar;

    @Override
    public void onCreate() {
        super.onCreate();
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.default_image)			// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.default_image)	// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.default_image)		// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                        //.displayer(new RoundedBitmapDisplayer(20))	// 设置成圆角图片
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        options_head = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.profile_no_avarta_icon)			// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.profile_no_avarta_icon)	// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.profile_no_avarta_icon)		// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                        //.displayer(new RoundedBitmapDisplayer(30))	// 设置成圆角图片
                .build();


        options_head_circle = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.profile_no_avarta_icon)			// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.profile_no_avarta_icon)	// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.profile_no_avarta_icon)		// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(360))	// 设置成圆角图片
                .build();

        options_avartar = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.profile_no_avarta_icon)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.profile_no_avarta_icon)    // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.profile_no_avarta_icon)        // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(false)                        // 设置下载的图片是否缓存在SD卡中
                .build();

        ImageLoaderConfiguration mConfiguration = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(mConfiguration);
        initbugly();
    }

    private void initbugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        String version = "unkown";
        try {
            version = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            strategy.setAppVersion(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        CrashReport.initCrashReport(this,strategy);
    }
}