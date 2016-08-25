package com.lyb.wechat.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lyb.wechat.R;
import com.lyb.wechat.adapter.ImageGridAdapter;
import com.lyb.wechat.config.ImageSelectConfig;
import com.lyb.wechat.ui.widget.divider.GridSpanItemDecoration;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.common.Constant;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoFragment extends Fragment {

    private static final int REQUEST_CODE = 0;
    private EditText mInfoEditContent;
    private RecyclerView mInfoSelectImageGrid;
    private ImageGridAdapter adapter;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_we_chat, container, false);

        initView(rootView);
        return rootView;
    }

    private void startImageSelector() {
        Intent intent = new Intent(getActivity(), ImgSelActivity.class);
        Constant.config = ImageSelectConfig.getConfig();
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void initView(View rootView) {
        mInfoEditContent = (EditText) rootView.findViewById(R.id.info_edit_content);
        mInfoSelectImageGrid = (RecyclerView) rootView.findViewById(R.id.info_select_image_grid);

        mInfoSelectImageGrid.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mInfoSelectImageGrid.addItemDecoration(new GridSpanItemDecoration(10, 10));
        mInfoSelectImageGrid.setNestedScrollingEnabled(false);
        adapter = new ImageGridAdapter(getActivity(), false);
        mInfoSelectImageGrid.setAdapter(adapter);

        adapter.setOnAddClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImageSelector();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            if (pathList != null && !pathList.isEmpty()) {
                if (pathList.size() > 1) {
                    adapter.addAll(pathList);
                } else {
                    adapter.add(pathList.get(0));
                }
            }
        }
    }
}