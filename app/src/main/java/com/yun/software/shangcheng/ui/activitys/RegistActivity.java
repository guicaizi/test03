package com.yun.software.shangcheng.ui.activitys;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.durban.Controller;
import com.yanzhenjie.durban.Durban;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.api.ApiConstants;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.nohttp.HttpListener;
import com.yun.software.shangcheng.ui.ViewWidget.XLoadingDialog;
import com.yun.software.shangcheng.ui.utils.FileUtils;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;
import com.yun.software.shangcheng.ui.utils.MyLogUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;
import com.yun.software.utils.ExceptionUtil;
import com.yun.software.utils.SMSCodeUtil;
import com.yun.software.widget.SwitchMultiButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by yanliang
 * on 2018/7/3 09:31
 */

public class RegistActivity extends BaseActivity {
    private static final int REQUEST_REGIST=1;
    private static final int REQUEST_UPLOAD_IMG = 2;
    private static final int REQUEST_SMS_INFOR = 3;
    private String tempfile;
    /**
     * 相册选择回调。
     */
    private static final int RESULT_BACK_ALBUM = 0x04;
    private static final int RESULT_BACK_CRUP = 0x05;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.edt_name)
    EditText edtName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.sw_sex)
    SwitchMultiButton swSex;
    @Bind(R.id.edt_phone)
    EditText edtPhone;
    @Bind(R.id.edt_code)
    EditText edtCode;
    @Bind(R.id.btn_code)
    Button btnCode;
    @Bind(R.id.edt_pwd)
    EditText edtPwd;
    @Bind(R.id.edt_pwd_two)
    EditText edtPwdTwo;
    @Bind(R.id.btn_ok)
    Button btnOk;
    @Bind(R.id.iv_usericon)
    ImageView userIcon;
    private String[] tabTexts4 = {"男", "女"};
    private XLoadingDialog xLoading;
    Map<String, Object> handlesms;
    Map<String,Object> handleRegist;
    private String filePath;
    private String uploadImgUrl;
    boolean  limiOptions=false;
    private String mobile;
    @Override
    public int getLayoutId() {
        return R.layout.activity_regeist_two;
    }

    @Override
    public void initPresenter() {
        tvTitle.setText("快速注册");
        swSex.setText(tabTexts4);
    }

    @Override
    public void initView() {

    }
    public void loadDate(int what){
        try {
            switch (what){
                case REQUEST_UPLOAD_IMG:
                    requestUpload(REQUEST_UPLOAD_IMG, ApiConstants.GOOD_IMAGE_UPLOAD,filePath, myhttpListener,mOnUploadListener);
                    break;
                case REQUEST_SMS_INFOR:
                    Map map3 = new HashMap();
                    map3.put("phone",mobile);
                    request(REQUEST_SMS_INFOR, ApiConstants.REGIST_CODE, JSON.toJSONString(map3), myhttpListener, false, false);
                    break;
            }

        }catch (Exception e){
            ExceptionUtil.handle(e);
        }
    }
    /**
     * 文件上传监听。
     */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            MyLogUtils.i("upload","开始上传");
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            MyLogUtils.i("upload","取消上传");
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
            MyLogUtils.i("upload","上传进度"+progress);
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            MyLogUtils.i("upload","上传完成");
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            MyLogUtils.i("upload","上传错误"+exception.getMessage());
        }
    };
    private HttpListener myhttpListener = new HttpListener() {

        @Override
        public void onSucceed(int what, String jsonstr) {
            limiOptions=false;
            try {
                switch (what) {
                    case REQUEST_UPLOAD_IMG:
                        MyLogUtils.i("upload","上传jsonstr"+jsonstr);
                        uploadImgUrl=StringUtils.getJsonKeyStr(jsonstr,"sUrl");
                        break;
                    case REQUEST_SMS_INFOR:
                        break;
                    case REQUEST_REGIST:
                        showLongToast("注册成功！");

                        finish();
                        break;

                }
            }catch (Exception e){
                ExceptionUtil.handle(e);
            }
        }

        @Override
        public void onFailed(int what, String jsonstr) {
             limiOptions=false;
        }
    };

    private void registLogin() {
        final String name = StringUtils.getText(edtName);
        mobile= StringUtils.getText(edtPhone);
        final String code = StringUtils.getText(edtCode);
        final String pwd = StringUtils.getText(edtPwd);
        final String pwdTwo = StringUtils.getText(edtPwdTwo);
        final String sex = StringUtils.getText(tvSex);
        if(StringUtils.isEmpty(uploadImgUrl)){
            showShortToast("请选择一张图片为头像！");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            showShortToast("请输入昵称");
            return;
        }
        if (TextUtils.isEmpty(sex)) {
            showShortToast("请选择性别");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            showShortToast("请输入手机号");
            return;
        } else {
            if (!StringUtils.isMobile(mobile)) {
                showShortToast("手机号不正确");
                return;
            }
        }
        if (TextUtils.isEmpty(code)) {
            showShortToast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            showShortToast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(pwdTwo)) {
            showShortToast("请再次输入密码");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 16||pwdTwo.length() < 6 || pwd.length() > 16) {
            showShortToast("请输入密码(6~16位字母或数字)");
            return;
        }
        if(!pwd.equals(pwdTwo)){
            showShortToast("两次密码输入不一致，请重新输入");
            return;
        }
        if(limiOptions){
            Tools.showInfo(mContext,"正在注册，请稍等...");
        }
        limiOptions=true;
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("phone",mobile);
        maps.put("verifyCode",code);
        maps.put("password",pwd);
        maps.put("confirm",pwdTwo);
        maps.put("icon",uploadImgUrl);
        maps.put("nickName",name);
        request(REQUEST_REGIST, ApiConstants.GOOD_REGIST,JSON.toJSONString(maps), myhttpListener, false, false);
    }
    @Override
    public void addLisener() {
        swSex.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                tvSex.setText(tabTexts4[position]);
            }
        });
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobile = StringUtils.getText(edtPhone);
                // 判断手机号不能为空
                if (StringUtils.isEmpty(mobile)) {
                    Tools.showInfo(mContext, R.string.mobile_null);
                    return;
                }
                // 判断手机号是否合法
                if (!StringUtils.isMobile(mobile)) {
                    Tools.showInfo(mContext, R.string.mobile_length_limit);
                    return;
                }
                SMSCodeUtil.startBtnTimer(btnCode);
                loadDate(REQUEST_SMS_INFOR);
            }
        });
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceImage();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registLogin();

            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    /**
     *选择图片
     */
    public  void choiceImage(){
        Album.album(this)
                .columnCount(3)
                .selectCount(1)
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .toolBarColor(ContextCompat.getColor(this, R.color.color_red6))
                .navigationBarColor(ContextCompat.getColor(this, R.color.color_red6))
                .requestCode(RESULT_BACK_ALBUM)
                .start();

    }
    /**
     *裁剪图片
     */
    public void cutImg(String tempFilePath){
        try {
            Durban.with(RegistActivity.this)
                    .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                    .toolBarColor(ContextCompat.getColor(this, R.color.color_red6))
                    .navigationBarColor(ContextCompat.getColor(this, R.color.color_red6))
                    // Image path list/array.
                    .inputImagePaths(tempFilePath)
                    // Image output directory.
                    .outputDirectory(FileUtils.getImageCropCachePath(this))
                    // Image size limit.
                    .maxWidthHeight(500, 500)
                    // Aspect ratio.
                    .aspectRatio(1, 1)
                    // Output format: JPEG, PNG.
                    .compressFormat(Durban.COMPRESS_JPEG)
                    // Compress quality, see Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                    .compressQuality(90)
                    // Gesture: ROTATE, SCALE, ALL, NONE.
                    .gesture(Durban.GESTURE_ALL)
                    .controller(Controller.newBuilder()
                            .enable(true)
                            .rotation(true)
                            .rotationTitle(true)
                            .scale(true)
                            .scaleTitle(true)
                            .build())
                    .requestCode(RESULT_BACK_CRUP)
                    .start();

        } catch (Exception e) {
            ExceptionUtil.handle(e);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case RESULT_BACK_ALBUM: {
                final String tempFilePath = Album.parseResult(data).get(0);
                tempfile=Album.parseResult(data).get(0);
                cutImg(tempFilePath);
                break;
            }
            case RESULT_BACK_CRUP: {
                this.filePath = Durban.parseResult(data).get(0);
                //                LocalImageLoader.getInstance().loadImage(mIvIcon, filePath);
                GlidUtils.loadCircleImageView(mContext,filePath,userIcon);
                MyLogUtils.i("kankan","裁剪后路径"+filePath);
                loadDate(REQUEST_UPLOAD_IMG);


                break;
            }
        }
    }
}
