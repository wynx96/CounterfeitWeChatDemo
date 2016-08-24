package com.lyb.wechat.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by 18348 on 2016/8/18.
 */
public class ResUtils {

    public static final int dp2Px(Context context, float value) {
        return (int) dp2Pxf(context, value);
    }

    public static final float dp2Pxf(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }
}
