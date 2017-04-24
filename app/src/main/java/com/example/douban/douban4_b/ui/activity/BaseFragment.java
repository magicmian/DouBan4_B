package com.example.douban.douban4_b.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.douban.douban4_b.util.AndroidTool;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangm on 2017/4/24.
 */

public abstract class  BaseFragment extends Fragment {
    protected BaseActivity mActivity;
    protected View mRootView;
    protected Unbinder mUnbinder;
    protected EventBus eventBus;

    protected abstract int initLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutId();
        mRootView = mActivity.getLayoutInflater().inflate(initLayoutId(), null);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initView();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();

        super.onDestroyView();
        //在销毁视图的时候把父控件remove一下，不然重新加载的时候会异常导致奔溃，提示should remove parent view
        ViewGroup mGroup=(ViewGroup) mRootView.getParent();
        if(mGroup!=null){
            mGroup.removeAllViewsInLayout();
        }

    }

    protected abstract void allowPermission();
    protected abstract void denyPermission();

    public void requestPermission(String permission){
        if(ContextCompat.checkSelfPermission(mActivity, permission)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(mActivity,new String[]{permission},1);
        }else{
            allowPermission();
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
}
