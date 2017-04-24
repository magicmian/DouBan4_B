package com.example.douban.douban4_b.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.douban.douban4_b.R;
import com.example.douban.douban4_b.ui.customeView.FragmentTabHost;
import com.example.douban.douban4_b.ui.fragment.BookMovieFragment;
import com.example.douban.douban4_b.ui.fragment.GroupFragment;
import com.example.douban.douban4_b.ui.fragment.HomeFragment;
import com.example.douban.douban4_b.ui.fragment.MyFragment;
import com.example.douban.douban4_b.ui.fragment.RadioFragment;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(android.R.id.tabcontent)
    FrameLayout tabcontent;
    @BindView(R.id.real_content)
    FrameLayout realContent;
    @BindView(android.R.id.tabs)
    TabWidget tabs;
    @BindView(R.id.main_layout)
    FragmentTabHost tabHost;
    private FragmentManager manager;
    private HomeFragment homeFragment;
    private BookMovieFragment bookMovieFragment;
    private RadioFragment RadioFragment;
    private GroupFragment GroupFragment;
    private MyFragment myFragment;
    String[] tabTag, tabTitle;
    TextView txt_count;
    protected Class[] classTab = {HomeFragment.class, BookMovieFragment.class, RadioFragment.class, GroupFragment.class,MyFragment.class};
    protected int[] ids = {R.drawable.tab_menu_home_icon, R.drawable.tab_menu_group_icon, R.drawable.tab_menu_radio_icon, R.drawable.tab_menu_group_icon,R.drawable.tab_menu_my_icon};
    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initTab();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void initData() {

    }
    protected void initTab() {
        tabTag = getResources().getStringArray(R.array.tabTag);
        tabTitle = getResources().getStringArray(R.array.tabTitle);
        tabHost.setup(this, getSupportFragmentManager(), R.id.real_content);
        for (int i = 0; i < tabTag.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTag[i]);
            tabSpec.setIndicator(buildIndicator(i));
            tabHost.addTab(tabSpec, classTab[i], null);
        }
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener((tabId) -> {
        });
    }
    protected View buildIndicator(int position) {
        View view = layoutInflater.inflate(R.layout.tab_indicator, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);
        TextView textView = (TextView) view.findViewById(R.id.text_indicator);
        if (position == 2) {
            txt_count = (TextView) view.findViewById(R.id.txt_count);
        }
        imageView.setImageResource(ids[position]);
        textView.setText(tabTitle[position]);
        return view;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void allowPermission() {

    }

    @Override
    protected void denyPermission() {

    }
}
