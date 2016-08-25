package com.lyb.wechat.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lyb.wechat.R;
import com.lyb.wechat.config.ImageSelectConfig;
import com.yuyh.library.imgsel.ImgSelActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoFragment extends Fragment {

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_we_chat, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 自由配置选项
                ImgSelActivity.startActivity(getActivity(), ImageSelectConfig.getConfig(), 0);
            }
        });
        return rootView;
    }
}