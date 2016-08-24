package com.lyb.wechat.ui.widget.divider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 18348 on 2016/8/24.
 */
public class LineItemDecoration extends RecyclerView.ItemDecoration {
    private int lineWidth;
    private int lineColor;
    private Paint linePaint;
    private Drawable drawable;

    public LineItemDecoration(int lineWidth, int lineColor) {
        this.lineWidth = lineWidth;
        this.lineColor = lineColor;

        linePaint = new Paint();
        linePaint.setColor(lineColor);
        drawable = new ColorDrawable(lineColor);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, lineWidth);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = 0;

        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View view = parent.getChildAt(i);
            top += view.getBottom();
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            if (params != null) {
                top += params.bottomMargin;
            }
            drawable.setBounds(0, top, parent.getWidth(), top + lineWidth);
            drawable.draw(c);
        }
    }
}
