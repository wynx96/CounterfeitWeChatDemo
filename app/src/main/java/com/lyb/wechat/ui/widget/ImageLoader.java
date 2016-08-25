package com.lyb.wechat.ui.widget;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by 18348 on 2016/8/25.
 */
public class ImageLoader implements com.yuyh.library.imgsel.ImageLoader {
    private static final ImageLoader imageLoader = new ImageLoader();

    public static ImageLoader getInstance() {
        return imageLoader;
    }

    private ImageLoader(){}

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
