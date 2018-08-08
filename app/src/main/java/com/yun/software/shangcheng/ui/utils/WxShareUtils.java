package com.yun.software.shangcheng.ui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.yun.software.shangcheng.R;

/**
 * Created by yanliang
 * on 2017/9/30 13:55
 */

public class WxShareUtils {


    private static final int THUMB_SIZE = 150;

    public static void shareText(IWXAPI api, String text,Boolean isfrendCircle) {

      // 初始化一个WXTextObject对象
      WXTextObject textObj = new WXTextObject();
      textObj.text = text;

      // 用WXTextObject对象初始化一个WXMediaMessage对象
      WXMediaMessage msg = new WXMediaMessage();
      msg.mediaObject = textObj;
      // 发送文本类型的消息时，title字段不起作用
      // msg.title = "Will be ignored";
      msg.description = text;

      // 构造一个Req
      SendMessageToWX.Req req = new SendMessageToWX.Req();
      req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
      req.message = msg;
      req.scene = isfrendCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
//      req.scene = SendMessageToWX.Req.WXSceneTimeline; // 分享到朋友圈
      // 调用api接口发送数据到微信
      api.sendReq(req);
    }

    public static void shareImage(IWXAPI api, Bitmap bmp) {

      WXImageObject imgObj = new WXImageObject(bmp);
      WXMediaMessage msg = new WXMediaMessage();
      msg.mediaObject = imgObj;
      Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
      bmp.recycle();
      msg.thumbData = ImageUtils.bmpToByteArray(thumbBmp, true);  // 设置缩略图

      SendMessageToWX.Req req = new SendMessageToWX.Req();
      req.transaction = buildTransaction("img");
      req.message = msg;
      //        req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
      req.scene = SendMessageToWX.Req.WXSceneTimeline;
      api.sendReq(req);
    }


    public static void shareWebpage(IWXAPI api, String webpageTitle, String webpageDesc, Bitmap bmpThumb, String webpageUrl) {
      WXWebpageObject webpage = new WXWebpageObject();
      webpage.webpageUrl = webpageUrl;
      WXMediaMessage msg = new WXMediaMessage(webpage);
      msg.title = webpageTitle;
      msg.description = webpageTitle;
      msg.thumbData =ImageUtils.bmpToByteArray(bmpThumb, true);

      SendMessageToWX.Req req = new SendMessageToWX.Req();
      req.transaction = buildTransaction("webpage");
      req.message = msg;
      //        req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
      req.scene = SendMessageToWX.Req.WXSceneTimeline;
      api.sendReq(req);
    }

    public static void shareWebpage(Context context, IWXAPI api, String webpageTitle, Bitmap bitmap, String webpageDesc, String webpageUrl, boolean allFriends) {
      WXWebpageObject webpage = new WXWebpageObject();
      webpage.webpageUrl = webpageUrl;
      WXMediaMessage msg = new WXMediaMessage(webpage);
      msg.title = webpageTitle;
      msg.description = webpageDesc;

      if (bitmap != null) {
        msg.setThumbImage(bitmap);
      } else {
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.head_icon);
        msg.setThumbImage(thumb);
      }

      SendMessageToWX.Req req = new SendMessageToWX.Req();
      req.transaction = buildTransaction("webpage");
      req.message = msg;
      //        req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
      if (allFriends) {
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
      } else {
        req.scene = SendMessageToWX.Req.WXSceneSession;
      }

      api.sendReq(req);
    }


    private static String buildTransaction(final String type) {
      return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
  }