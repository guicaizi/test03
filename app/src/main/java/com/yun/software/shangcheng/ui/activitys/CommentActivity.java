package com.yun.software.shangcheng.ui.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.ui.fragments.GoodCollectionFramgent;
import com.yun.software.shangcheng.ui.fragments.MyCoupotionsFramgent;

/**
 * Created by yanliang
 * on 2018/7/4 14:22
 */

public class CommentActivity extends BaseActivity {
    private FrameLayout mFrameLayout;
    public static final String TYPE_KEY = "type_key";
    private FragmentManager mFragmentManager;
    private GoodCollectionFramgent goodCollectionFramgent;
    private MyCoupotionsFramgent myCoupotionsFramgent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_collection;
    }

    @Override
    public void initPresenter() {
        mFrameLayout = (FrameLayout) this.findViewById(R.id.container_framelayout);
        int key = getIntent().getIntExtra(TYPE_KEY, -1);
        mFragmentManager = this.getSupportFragmentManager();
        openFragment(key);
    }
    @Override
    public void initView() {

    }

    private void openFragment(int key) {

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Bundle mBundle = null;
        switch (key) {
            /*Fragment 使用AgenWeb*/
            case 0: //项目中请使用常量代替0 ， 代码可读性更高
                ft.add(R.id.container_framelayout, goodCollectionFramgent = GoodCollectionFramgent.getInstance(null), GoodCollectionFramgent.class.getName());
                break;
            case 1:
                ft.add(R.id.container_framelayout, myCoupotionsFramgent = MyCoupotionsFramgent.getInstance(null), MyCoupotionsFramgent.class.getName());
                break;


        }
        ft.commit();

    }
    @Override
    public void addLisener() {

    }
}
