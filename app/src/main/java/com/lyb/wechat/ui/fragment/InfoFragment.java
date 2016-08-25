package com.lyb.wechat.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lyb.wechat.R;
import com.lyb.wechat.adapter.ImageGridAdapter;
import com.lyb.wechat.bean.FindBean;
import com.lyb.wechat.config.ImageSelectConfig;
import com.lyb.wechat.database.HeadUrl;
import com.lyb.wechat.ui.widget.divider.GridSpanItemDecoration;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.common.Constant;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoFragment extends Fragment {

    private static final int REQUEST_CODE = 0;
    private EditText mInfoEditContent;
    private RecyclerView mInfoSelectImageGrid;
    private ImageGridAdapter adapter;
    private EditText mInfoEditUsername;
    private TextView mInfoSubmit;
    private List<String> mImageList;

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
        mInfoEditUsername = (EditText) rootView.findViewById(R.id.info_edit_username);
        mInfoEditContent = (EditText) rootView.findViewById(R.id.info_edit_content);
        mInfoSelectImageGrid = (RecyclerView) rootView.findViewById(R.id.info_select_image_grid);
        mInfoSubmit = (TextView) rootView.findViewById(R.id.info_submit);

        mInfoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

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

    private void submit() {
        // validate
        String username = mInfoEditUsername.getText().toString();
        String content = mInfoEditContent.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "姓名或者内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mImageList = null;
        if (!adapter.isEmpty()) {
            int count = Math.min(adapter.getMaxCount(), adapter.size());
            mImageList = adapter.subList(0, count);
        }

        FindBean findBean = new FindBean();
        findBean.setUsername(username);
        findBean.setContent(content);
        findBean.setImagePaths(mImageList);
        findBean.setCreateTime(System.currentTimeMillis());
        HeadUrl.getHeadUrlList().get(new Random().nextInt(HeadUrl.getHeadUrlList().size()));
        findBean.setImageUrl(HeadUrl.getHeadUrlList().get(new Random().nextInt(HeadUrl.getHeadUrlList().size())));

        Observable.just(findBean).map(new Func1<FindBean, Boolean>() {
            @Override
            public Boolean call(FindBean findBean) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Set<String> set = preferences.getStringSet(FindBean.class.getName(), new HashSet<String>());
                Gson gson = new Gson();
                String json = gson.toJson(findBean);
                set.add(json);
                return preferences.edit().putStringSet(FindBean.class.getName(), set).commit();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(InfoFragment.this.getActivity(), "添加成功，请到发现页刷新", Toast.LENGTH_SHORT).show();
                    mInfoEditUsername.setText(null);
                    mInfoEditContent.setText(null);
                    adapter.clear();
                }
            }
        });
        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> set = preferences.getStringSet(FindBean.class.getName(), new HashSet<String>());
        Gson gson = new Gson();
        String json = gson.toJson(findBean);
        set.add(json);
        if (preferences.edit().putStringSet(FindBean.class.getName(), set).commit()) {
            Toast.makeText(InfoFragment.this.getActivity(), "添加成功，请到发现页刷新", Toast.LENGTH_SHORT).show();
            mInfoEditUsername.setText(null);
            mInfoEditContent.setText(null);
            adapter.clear();
        }*/
    }

}