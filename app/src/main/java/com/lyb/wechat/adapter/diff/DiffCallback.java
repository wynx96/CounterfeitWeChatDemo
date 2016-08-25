package com.lyb.wechat.adapter.diff;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by 18348 on 2016/8/25.
 */
public abstract class DiffCallback<T> extends DiffUtil.Callback {
    protected List<T> oldList;
    protected List<T> newList;

    public DiffCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return equals(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    private static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }
}
