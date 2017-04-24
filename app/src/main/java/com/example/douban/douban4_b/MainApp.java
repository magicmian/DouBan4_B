package com.example.douban.douban4_b;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;

import com.example.douban.douban4_b.event.CanInitEvent;
import com.example.douban.douban4_b.event.CanStartApp;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by wangm on 2017/4/24.
 */

public class MainApp extends Application {
    //    private RefWatcher mRefWatcher;
    private static MainApp mInstance;

    private static Context context = null;


    public static MainApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        //下面这一条的效果是完全一样的
        //修改默认实现的配置，记住，必须在第一次EventBus.getDefault()之前配置，且只能设置一次。建议在application.onCreate()调用
        EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onCanInitEvent(CanInitEvent event) {
        init();
    };

    private void init() {

        mInstance = this;
        context = getApplicationContext();

        Logger.init("DouBan")
                .methodCount(3)
                .hideThreadInfo();
        SystemClock.sleep(2000);
        EventBus.getDefault().post(new CanStartApp());

    }


    private void initshare() {
        //        tencent = Tencent.createInstance(Constants.QQ_APP_ID, this);
//        //注册 微信
//        iWXApi = WXAPIFactory.createWXAPI(this, BuildConfig.WX_APP_ID, false);
//        iWXApi.unregisterApp();
//        iWXApi.registerApp(BuildConfig.WX_APP_ID);
//
//        // 创建微博分享接口实例
//        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, BuildConfig.Weibo_APP_ID);
//        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
//        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
//        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
//        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
//        mAuthInfo = new AuthInfo(this, BuildConfig.Weibo_APP_ID, Constants.SINA_REDIRECT_URL, Constants.SINA_SCOPE);
//        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
//        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
//        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
//        mWeiboShareAPI.registerApp();
    }

}
