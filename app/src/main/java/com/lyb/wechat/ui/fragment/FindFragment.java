package com.lyb.wechat.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lyb.wechat.R;
import com.lyb.wechat.adapter.FindAdapter;
import com.lyb.wechat.adapter.diff.DiffCallback;
import com.lyb.wechat.bean.FindBean;
import com.lyb.wechat.ui.widget.divider.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class FindFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView mRecyclerView;
    private ImageView mImageBg;
    private NestedScrollView mScrollView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FindAdapter adapter;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mImageBg = (ImageView) view.findViewById(R.id.image_bg);
        mScrollView = (NestedScrollView) view.findViewById(R.id.scrollView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      /*  LineItemDecoration itemDecoration = new LineItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.horizontal_line_width),
                getResources().getColor(R.color.line_color));*/
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new FindAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);

        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();

    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<FindBean> beanList = getFindBeans();
                final DiffUtil.DiffResult diffResult = calculateDiff(beanList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.getList().clear();
                        adapter.getList().addAll(beanList);
                        diffResult.dispatchUpdatesTo(adapter);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private DiffUtil.DiffResult calculateDiff(final List<FindBean> newList) {
        final List<FindBean> oldList = new ArrayList<>(adapter.getList());
        return DiffUtil.calculateDiff(new DiffCallback<FindBean>(oldList, newList) {

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).getCreateTime() == newList.get(newItemPosition).getCreateTime();
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return newList.get(newItemPosition);
            }
        });
    }

    private List<FindBean> getFindBeans() {
        final List<FindBean> beanList = new ArrayList<>();
        Set<String> strings = PreferenceManager.getDefaultSharedPreferences(getActivity()).getStringSet(FindBean.class.getName(), null);
        if (strings != null) {
            Gson gson = new Gson();
            for (String json : strings) {
                try {
                    FindBean findBean = gson.fromJson(json, FindBean.class);
                    beanList.add(findBean);
                } catch (Exception e) {
                }
            }
            Collections.sort(beanList, new Comparator<FindBean>() {
                @Override
                public int compare(FindBean lhs, FindBean rhs) {
                    return Long.valueOf(rhs.getCreateTime()).compareTo(lhs.getCreateTime());
                }
            });
        }
        return beanList;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, FindBean.class.getName())) {
            onRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }
}
