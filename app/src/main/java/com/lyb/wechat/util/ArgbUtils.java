package com.lyb.wechat.util;

import android.animation.ArgbEvaluator;

/**
 * Created by 18348 on 2016/8/18.
 */
public class ArgbUtils {

    private static final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private int startColor;
    private int endColor;

    public ArgbUtils(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public static ArgbEvaluator getArgbEvaluator() {
        return argbEvaluator;
    }

    public static ArgbUtils getInstance(int startColor, int endColor) {
        return new ArgbUtils(startColor, endColor);
    }

    public int evaluate(float fraction) {
        return ((Integer) getArgbEvaluator().evaluate(fraction, startColor, endColor));
    }


}
