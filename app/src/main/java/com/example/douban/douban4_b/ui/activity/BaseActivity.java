package com.example.douban.douban4_b.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.douban.douban4_b.MainApp;
import com.example.douban.douban4_b.util.AndroidTool;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangm on 2017/4/24.
 */

public abstract class BaseActivity extends AppCompatActivity {
    ProgressDialog mProgress = null;

    protected LayoutInflater layoutInflater;

    protected MainApp app;

    protected Context mContext;
    protected Unbinder mUnbinder;

    protected abstract int initLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stateBarInit();
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(initLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showProgress(int res) {
        showProgress(getString(res));
    }

    public void showProgress(String text) {
        if(mProgress == null)
            mProgress = new ProgressDialog(this);
        mProgress.setMessage(text);
        mProgress.show();

    }

    public void hideProgress() {
        if(mProgress != null)
            mProgress.dismiss();
    }
    protected abstract void allowPermission();
    protected abstract void denyPermission();

    public void requestPermission(String permission){
        if(ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{permission},1);
        }else{
            allowPermission();
        }
    }
    //用于改变标题颜色状态栏颜色，各机型不一致，
    private void stateBarInit() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//           getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//           getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//           getWindow().setStatusBarColor(Color.TRANSPARENT);
//
//        } else
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    allowPermission();
                }else{
                    AndroidTool.showToast(this,"您拒绝了提供权限");
                    denyPermission();
                }
                break;
        }
    }

    public void setMarquee(TextView textView, int times){
        textView.setSingleLine();
        textView.setFocusable(true);
        textView.setSelected(true);
        textView.setFocusableInTouchMode(true);
        textView.setMarqueeRepeatLimit(times);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }
//    static boolean MIUISetStatusBarLightMode(Activity activity, boolean darkmode) {
//        boolean result = false;
//        Class<? extends Window> clazz = activity.getWindow().getClass();
//        try {
//            int darkModeFlag = 0;
//            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
//            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
//            darkModeFlag = field.getInt(layoutParams);
//            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
//            result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//    static boolean FlymeSetStatusBarLightMode(Activity activity, boolean darkmode) {
//        boolean result = false;
//        try {
//            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
//            Field darkFlag = WindowManager.LayoutParams.class
//                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
//            Field meizuFlags = WindowManager.LayoutParams.class
//                    .getDeclaredField("meizuFlags");
//            darkFlag.setAccessible(true);
//            meizuFlags.setAccessible(true);
//            int bit = darkFlag.getInt(null);
//            int value = meizuFlags.getInt(lp);
//            if (darkmode) {
//                value |= bit;
//            } else {
//                value &= ~bit;
//            }
//            meizuFlags.setInt(lp, value);
//            activity.getWindow().setAttributes(lp);
//            result = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//    public static void setStatusBarLightMode(Activity activity, int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //判断是否为小米或魅族手机，如果是则将状态栏文字改为黑色
//            if (MIUISetStatusBarLightMode(activity, true) || FlymeSetStatusBarLightMode(activity, true)) {
//                //设置状态栏为指定颜色
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
//                    activity.getWindow().setStatusBarColor(color);
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4
//                    //调用修改状态栏颜色的方法
//                    setStatusBarColor(activity, color);
//                }
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
//                activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                activity.getWindow().setStatusBarColor(color);
//
//                //fitsSystemWindow 为 false, 不预留系统栏位置.
//                ViewGroup mContentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
//                View mChildView = mContentView.getChildAt(0);
//                if (mChildView != null) {
//                    ViewCompat.setFitsSystemWindows(mChildView, true);
//                    ViewCompat.requestApplyInsets(mChildView);
//                }
//            }
//        }
//    }
//    static void setStatusBarColor(Activity activity, int statusColor) {
//        Window window = activity.getWindow();
//        //取消状态栏透明
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //添加Flag把状态栏设为可绘制模式
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
//        window.setStatusBarColor(statusColor);
//        //设置系统状态栏处于可见状态
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        //让view不根据系统窗口来调整自己的布局
//        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
//        View mChildView = mContentView.getChildAt(0);
//        if (mChildView != null) {
//            ViewCompat.setFitsSystemWindows(mChildView, false);
//            ViewCompat.requestApplyInsets(mChildView);
//        }
//    }
}
