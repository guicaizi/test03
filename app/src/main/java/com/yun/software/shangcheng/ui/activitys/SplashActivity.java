package com.yun.software.shangcheng.ui.activitys;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.Permission;

import java.util.List;

import butterknife.Bind;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yanliang
 * on 2018/8/3 14:26
 */

public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final int PERMISSION_REQUEST_CODE = 110;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.tv_name)
    TextView tvName;
    private static final int RC_WRITE_READ = 123;
    @Override
    public int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    public void initPresenter() {
        String date = getIntent().getStringExtra("data");
        MyLogUtils.i("options", "Splash-----" + date);
    }

    @Override
    public void initView() {
        SetTranslanteBar();

        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(tvName, alpha, scaleX, scaleY);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivLogo, alpha, scaleX, scaleY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1, objectAnimator2);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                MyLogUtils.i("kankan","动画结束");
                WriteOrRead();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void addLisener() {

    }

    private boolean hasWritePermission() {
        return EasyPermissions.hasPermissions(this, Permission.Group.STORAGE);
    }

    @AfterPermissionGranted(RC_WRITE_READ)
    public void WriteOrRead() {
        MyLogUtils.i("kankan","走了没");
        if (hasWritePermission()) {
            MyLogUtils.i("kankan","收到了没");
            enterPage(MainActivity.class);
            finish();
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_wirte_read),
                    RC_WRITE_READ,
                    Permission.Group.STORAGE);
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        enterPage(MainActivity.class);
        finish();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        MyLogUtils.i("kankan","权限列表"+ JSON.toJSONString(perms));
        new AppSettingsDialog.Builder(this).setTitle("权限通知").setRationale("时享优品需要访问您的读写权限，请去设置。").setPositiveButton("设置").build().show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            MyLogUtils.i("kankan","权限丢失"+ JSON.toJSONString(perms));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
              if(hasWritePermission()){
                  enterPage(MainActivity.class);
                  finish();
              }else{
                  finish();
              }
        }
    }
}
