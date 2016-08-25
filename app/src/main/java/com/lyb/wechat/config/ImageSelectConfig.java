package com.lyb.wechat.config;

import android.graphics.Color;

import com.lyb.wechat.MyApplication;
import com.lyb.wechat.R;
import com.lyb.wechat.ui.widget.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

/**
 * Created by 18348 on 2016/8/25.
 */
public class ImageSelectConfig {
    private static ImgSelConfig config = new ImgSelConfig.Builder(ImageLoader.getInstance())
            // 是否多选
            .multiSelect(true)
            // “确定”按钮背景色
            .btnBgColor(Color.GRAY)
            // “确定”按钮文字颜色
            .btnTextColor(Color.BLUE)
            // 使用沉浸式状态栏
            .statusBarColor(MyApplication.getContext().getResources().getColor(R.color.colorPrimary))
            // 返回图标 ResId
            .backResId(R.mipmap.goback)
            // 标题
            .title("图片")
            // 标题文字颜色
            .titleColor(Color.WHITE)
            // TitleBar 背景色
            .titleBgColor(MyApplication.getContext().getResources().getColor(R.color.colorPrimary))
            // 裁剪大小。needCrop 为 true 的时候配置
            .cropSize(1, 1, 200, 200)
            .needCrop(true)
            // 第一个是否显示相机
            .needCamera(true)
            // 最大选择图片数量
            .maxNum(9)
            .btnBgColor(MyApplication.getContext().getResources().getColor(R.color.tab_bg_color_pressed))
            .btnTextColor(Color.WHITE)
            .build();

    public static ImgSelConfig getConfig() {
        return config;
    }
}
