package com.yun.software.shangcheng.ui.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.base.AppApplication;
import com.yun.software.shangcheng.base.BaseActivity;
import com.yun.software.shangcheng.ui.entity.WxPayNeedParams;
import com.yun.software.utils.ExceptionUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 工具类
 *
 * @author LiBing
 */
public class Tools {

    private static Context context = null;

    /**
     * 提示信息
     *
     * @param context
     * @param msg
     */
    public static void showInfo(Context context, String msg) {
        showToast(msg, Toast.LENGTH_SHORT,0, Gravity.CENTER);
    }

    /**
     * 提示信息
     *
     * @param context
     */
    public static void showInfo(Context context, int resId) {

        showToast(getResString(context, resId), Toast.LENGTH_SHORT,0, Gravity.CENTER);
    }

    /**
     * 根据资源ID获取字符串资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getResString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 根据资源ID获取颜色资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static int getResColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }
    private static String lastToast = "";
    private static long lastToastTime;
    public static void showToast(String message, int duration, int icon,
                                 int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {
                View view = LayoutInflater.from(AppApplication.getAppContext()).inflate(
                        R.layout.view_toast, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(AppApplication.getAppContext());
                toast.setView(view);
                if (gravity == Gravity.CENTER) {
                    toast.setGravity(gravity, 0, 0);
                } else {
                    toast.setGravity(gravity, 0, 35);
                }

                toast.setDuration(duration);
                toast.show();
                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            // 获取包管理工具类
            PackageManager packageManager = context.getPackageManager();
            // 获取版本信息
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            // 获取包管理工具类
            PackageManager packageManager = context.getPackageManager();
            // 获取版本信息
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取手机参数信息
     *
     * @param name HARDWARE，RADIO，versionCode，HOST，。。。，BRAND
     * @return
     */
    public static String getPhoneInfo(String name) {
        String value = "";
        if (name == null || "".equals(name)) {
            return value;
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (name.equals(field.getName())) {
                    value = field.get(null).toString();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static  boolean checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.ic_check);
        } else {
            ivCheck.setImageResource(R.drawable.ic_uncheckone);
        }
        return isSelect;
    }

    /**
     * 打印链接，用于调试接口
     */
    public static String getRequstUrl(Map<String, Object> params, String url) {
        if (params != null) {

            StringBuilder sb = new StringBuilder();
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            String requestUrl = sb.toString();
            requestUrl = url + "?"
                    + requestUrl.substring(0, requestUrl.length() - 1);

            return requestUrl;
        }
        return null;
    }

//    /**
//     * 打印链接，用于调试接口
//     */
//    public static String getRequstUrl(RequestParams params, String url) {
//        if (params != null) {
//            HttpEntity entity = params.getEntity();
//            // entity.
//            List<NameValuePair> queryStringParams = params
//                    .getQueryStringParams();
//            StringBuilder sb = new StringBuilder();
//            if(queryStringParams!=null){
//            for (NameValuePair nameValuePair : queryStringParams) {
//                sb.append(nameValuePair.getName()).append("=")
//                        .append(nameValuePair.getValue()).append("&");
//              }
//            }
//            List<NameValuePair> bodyParams = params
//                    .getBodyParams();
//            if (bodyParams != null) {
//                for (NameValuePair nameValuePair : bodyParams) {
//                    sb.append(nameValuePair.getName()).append("=")
//                            .append(nameValuePair.getValue()).append("&");
//                }
//            }
//            String requestUrl = sb.toString();
//            requestUrl = url + "?"
//                    + requestUrl.substring(0, requestUrl.length() - 1);
//
//            return requestUrl;
//        }
//        return null;
//    }

    /**
     * 判断上传的视频是否在规定时间内
     */
    @SuppressLint("NewApi")
    public static boolean isLessSpecifiedTime(String path, int seconds) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            if (path != null) {
                mmr.setDataSource(path);
                String durationStr = mmr
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int duration = Integer.parseInt(durationStr) / 1000;
                if (duration <= seconds) {
                    return true;
                }
            } else
                return false;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            mmr.release();
        }
        return false;
    }

//    /**
//     * ScrollView里面嵌套GridView
//     *
//     * @param context
//     * @param couponGridView
//     * @param extraInch      多余的尺寸
//     */
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public static void setGridViewHeight(Context context, GridView couponGridView, int extraInch) {
//        if (couponGridView == null)
//            return;
//        ListAdapter adapter = couponGridView.getAdapter();
//        if (adapter == null) {
//            // pre-condition
//            return;
//        }
//        int totalHeight = 0;
//        int count = (adapter.getCount() + 1) / 2;
//        View listItem = adapter.getView(0, null, couponGridView);
//        listItem.measure(0, 0);
//        totalHeight = listItem.getMeasuredHeight() * count;
//
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) couponGridView
//                .getLayoutParams();
//        params.height = totalHeight + couponGridView.getVerticalSpacing()
//                * count + DensityUtil.dip2px(context, extraInch);
//
//        couponGridView.setLayoutParams(params);
//    }

    /**
     * 判断当前是否是wifi
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 根据视频链接获取视频缩略图
     *
     * @param url 视频链接
     * @return 含有视频缩略图的Bitmap
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    public static Bitmap getThumnail(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;
    }
    // 含有全部的权限
    public static  boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean checkList(List<T> list) {
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;

        }
    }

    public static void setListViewHeight(ListView listView) {

            if(listView == null) return;
            ListAdapter listAdapter = listView.getAdapter();
             if (listAdapter == null) {
                // pre-condition
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);

    }
    public static void toWakeWXay(WxPayNeedParams wxparams,BaseActivity mContext) {
      toWakeWXay(wxparams,mContext,true);
    }

    public static void toWakeWXay(WxPayNeedParams wxparams,BaseActivity mContext,boolean isprogress) {
            try {

                if (!AppApplication.api.isWXAppInstalled()) {
                    Tools.showInfo(mContext, "您还未安装微信，请安装完微信后再试！");
                    return;
                }
                if (!AppApplication.api.isWXAppSupportAPI()) {
                    Tools.showInfo(mContext, "当前微信版本过低，请更换至最新版本。");
                    return;
                }
                if(isprogress){
                    mContext.startProgressDialog("正在支付中....");
                }
                PayReq request = new PayReq();

                request.appId = wxparams.getAppId();
                //                商户号
                request.partnerId = wxparams.getPartnerId();
                //微信返回的支付交易会话ID
                request.prepayId = wxparams.getPrepayId();
                request.packageValue = "Sign=WXPay";
                //                随机字符串
                request.nonceStr = wxparams.getNonceStr();
                request.timeStamp = wxparams.getTimeStamp();
                //                签名
                request.sign = wxparams.getPaySign();
                MyLogUtils.i("payparams", "调取支付参数\r\n" + "request.appId " + request.appId + "quest.partnerId" + request.partnerId);
                MyLogUtils.i("payparams", "调取支付参数\r\n" + "request.prepayId" + request.prepayId + "request.packageValue " + request.packageValue);
                MyLogUtils.i("payparams", "调取支付参数\r\n" + "request.nonceStr" + request.nonceStr + "request.timeStamp " + request.timeStamp);
                MyLogUtils.i("payparams", "调取支付参数\r\n" + "request.sign " + request.sign);
                AppApplication.api.sendReq(request);
            } catch (Exception e) {
                ExceptionUtil.handle(e);
            }
    }
    //    /**
//     * 生成二维码Bitmap
//     *
//     * @param content   内容
//     * @param widthPix  图片宽度
//     * @param heightPix 图片高度
//     * @return 生成二维码及保存文件是否成功
//     */
//    public static Bitmap createQRImage(String content, int widthPix, int heightPix) {
//        if (content == null || "".equals(content)) {
//            return null;
//        }
//
//        //配置参数
//        Map<EncodeHintType, Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        //容错级别
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        //设置空白边距的宽度
////            hints.put(EncodeHintType.MARGIN, 2); //default is 4
//
//        // 图像数据转换，使用了矩阵转换
//        BitMatrix bitMatrix = null;
//        try {
//            bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        int[] pixels = new int[widthPix * heightPix];
//        // 下面这里按照二维码的算法，逐个生成二维码的图片，
//        // 两个for循环是图片横列扫描的结果
//        for (int y = 0; y < heightPix; y++) {
//            for (int x = 0; x < widthPix; x++) {
//                if (bitMatrix.get(x, y)) {
//                    pixels[y * widthPix + x] = 0xff000000;
//                } else {
//                    pixels[y * widthPix + x] = 0xffffffff;
//                }
//            }
//        }
//
//        // 生成二维码图片的格式，使用ARGB_8888
//        Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
//        return bitmap;
//
//    }
}
