package com.lyb.wechat.ui.widget.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 18348 on 2016/8/24.
 */
public class IconFont extends TextView {
    private static final String ICON_FONT_NAME = "wechat.ttf";

    public IconFont(Context context) {
        this(context, null);
    }

    public IconFont(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), ICON_FONT_NAME));
    }
}
