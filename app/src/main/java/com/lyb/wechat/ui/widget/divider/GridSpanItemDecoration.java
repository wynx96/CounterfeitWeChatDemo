package com.lyb.wechat.ui.widget.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 18348 on 2016/8/25.
 */
public class GridSpanItemDecoration extends RecyclerView.ItemDecoration {
    private int hSpan, vSpan;

    public GridSpanItemDecoration(int hSpan, int vSpan) {
        this.hSpan = hSpan;
        this.vSpan = vSpan;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, hSpan, vSpan);
    }
}
