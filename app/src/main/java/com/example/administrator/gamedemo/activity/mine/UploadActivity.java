package com.example.administrator.gamedemo.activity.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.gamedemo.R;
import com.example.administrator.gamedemo.adapter.BaseFragmentAdapterTableLayout;
import com.example.administrator.gamedemo.core.Constants;
import com.example.administrator.gamedemo.fragment.ShareFragment;
import com.example.administrator.gamedemo.fragment.upload.UploadFragmentING;
import com.example.administrator.gamedemo.fragment.upload.UploadFragmentNO;
import com.example.administrator.gamedemo.fragment.upload.UploadFragmentOK;
import com.example.administrator.gamedemo.utils.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @auther lixu
 * Created by lixu on 2017/1/2 0002.
 * 我的上传
 */
public class UploadActivity extends BaseFragmentActivity{

    @BindView(R.id.activity_work_main_vp)
    ViewPager vp;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    private FragmentManager myFM;
    private List<Fragment> list_fragmet = new ArrayList<>();
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_upload);
    }

    @Override
    public void initViews() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myFM = getSupportFragmentManager();
        list_fragmet.add(UploadFragmentOK.getInstance());
        list_fragmet.add(UploadFragmentING.getInstance());
        list_fragmet.add(UploadFragmentNO.getInstance());
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(Constants.strWorkText.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(Constants.strWorkText.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(Constants.strWorkText.get(2)));
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new BaseFragmentAdapterTableLayout(Constants.strWorkText,list_fragmet,myFM));

        tabLayout.setupWithViewPager(vp);
    }

    @Override
    public void initData() {

    }
}
