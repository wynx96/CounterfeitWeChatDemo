package com.lyb.wechat.util;

import java.text.SimpleDateFormat;

/**
 * Created by 18348 on 2016/8/25.
 */
public class TimeUtil {
    public static final long ONE_MS = 1;
    public static final long ONE_S = ONE_MS * 1000;
    public static final long ONE_M = ONE_S * 60;
    public static final long ONE_H = ONE_M * 60;
    public static final long ONE_D = ONE_H * 24;
    public static final long ONE_W = ONE_D * 7;
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd hh-mm");

    public static String formatDate(long createTime) {
        String formatTime = null;
        long offsetTime = System.currentTimeMillis() - createTime;
        if (offsetTime < ONE_M) {
            formatTime = "刚刚";
        } else if (offsetTime < ONE_H) {
            formatTime = offsetTime / ONE_M + "分钟前";
        } else if (offsetTime < ONE_D) {
            formatTime = offsetTime / ONE_H + "小时前";
        } else if (offsetTime < ONE_D * 30) {
            formatTime = offsetTime / ONE_D + "天前";
        } else {
            formatTime = simpleDateFormat.format(createTime);
        }
        return formatTime;
    }
}
