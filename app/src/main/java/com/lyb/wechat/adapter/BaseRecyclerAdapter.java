package com.lyb.wechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by 18348 on 2016/8/22.
 */
public abstract class BaseRecyclerAdapter<H extends RecyclerView.ViewHolder, T> extends
        RecyclerView.Adapter<H> implements List<T> {
    protected List<T> mList;
    protected Context context;
    protected LayoutInflater layoutInflater;
    private boolean autoRefresh = true;

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    public BaseRecyclerAdapter(Context context) {
        this(context, null);
    }

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.mList = list;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);

        if (this.mList == null) {
            this.mList = new ArrayList<>();
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public Context getContext() {
        return context;
    }


    public void add(int location, T object) {
        mList.add(location, object);
        if (isAutoRefresh())
            notifyItemInserted(location);
    }

    public boolean add(T object) {
        boolean add = mList.add(object);
        if (add && isAutoRefresh()) {
            notifyItemInserted(size() - 1);
        }
        return add;
    }

    public boolean addAll(int location, Collection<? extends T> collection) {
        boolean addAll = mList.addAll(location, collection);
        if (addAll && isAutoRefresh()) {
            notifyDataSetChanged();
        }
        return addAll;
    }

    public boolean addAll(Collection<? extends T> collection) {
        boolean addAll = mList.addAll(collection);
        if (addAll && isAutoRefresh()) {
            notifyDataSetChanged();
        }
        return addAll;
    }

    public void clear() {
        mList.clear();
        if (isAutoRefresh())
            notifyDataSetChanged();
    }

    public boolean contains(Object object) {
        return mList.contains(object);
    }

    public boolean containsAll(Collection<?> collection) {
        return mList.containsAll(collection);
    }

    @Override
    public boolean equals(Object object) {
        return mList.equals(object);
    }

    public T get(int location) {
        return mList.get(location);
    }

    @Override
    public int hashCode() {
        return mList.hashCode();
    }

    public int indexOf(Object object) {
        return mList.indexOf(object);
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

    public Iterator<T> iterator() {
        return mList.iterator();
    }

    public int lastIndexOf(Object object) {
        return mList.lastIndexOf(object);
    }

    public ListIterator<T> listIterator() {
        return mList.listIterator();
    }

    public ListIterator<T> listIterator(int location) {
        return mList.listIterator(location);
    }

    public T remove(int location) {
        T remove = mList.remove(location);
        if (isAutoRefresh())
            notifyItemRemoved(location);
        return remove;
    }

    public boolean remove(Object object) {
        boolean remove = mList.remove(object);
        if (remove && isAutoRefresh()) {
            notifyDataSetChanged();
        }
        return remove;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean removeAll = mList.removeAll(collection);
        if (removeAll && isAutoRefresh()) {
            notifyDataSetChanged();
        }
        return removeAll;
    }

    public boolean retainAll(Collection<?> collection) {
        return mList.retainAll(collection);
    }

    public T set(int location, T object) {
        T set = mList.set(location, object);
        if (isAutoRefresh())
            notifyItemChanged(location);
        return set;
    }

    public int size() {
        return mList.size();
    }

    public List<T> subList(int start, int end) {
        return mList.subList(start, end);
    }

    public Object[] toArray() {
        return mList.toArray();
    }

    public <T1> T1[] toArray(T1[] array) {
        return mList.toArray(array);
    }
}
