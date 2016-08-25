package com.lyb.wechat.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lyb.wechat.R;
import com.lyb.wechat.adapter.FindAdapter;
import com.lyb.wechat.ui.widget.divider.DividerItemDecoration;

public class FindFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ImageView mImageBg;
    private NestedScrollView mScrollView;

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

    }


    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      /*  LineItemDecoration itemDecoration = new LineItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.horizontal_line_width),
                getResources().getColor(R.color.line_color));*/
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(new FindAdapter(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
    }

}
