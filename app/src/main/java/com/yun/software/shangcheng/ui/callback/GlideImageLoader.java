package com.yun.software.shangcheng.ui.callback;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yanzhenjie.album.impl.AlbumImageLoader;
import com.youth.banner.loader.ImageLoader;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.entity.BannerImg;
import com.yun.software.shangcheng.ui.utils.Glid.GlidUtils;

import java.io.File;


public class GlideImageLoader extends ImageLoader implements AlbumImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
//        Glide.with(context.getApplicationContext())
//                .load(path)
//                .crossFade()
//                .into(imageView);
        if(path instanceof BannerImg){
            GlidUtils.loadImageNormal(context,((BannerImg) path).getSrc(),imageView, R.drawable.long_loading);
        }else if(path instanceof String ){
            GlidUtils.loadImageNormal(context,(String) path,imageView, R.drawable.long_loading);
        }
    }


    @Override
    public void loadImage(ImageView imageView, String imagePath, int width, int height) {
        Glide.with(imageView.getContext())
                .load(new File(imagePath))
                .into(imageView);
    }
}
