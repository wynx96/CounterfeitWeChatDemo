package com.lyb.wechat.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyb.wechat.R;
import com.lyb.wechat.bean.FindBean;
import com.lyb.wechat.ui.widget.divider.GridSpanItemDecoration;
import com.lyb.wechat.ui.widget.view.ExpandableTextView;
import com.lyb.wechat.util.TimeUtil;
import com.yuyh.library.imgsel.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 18348 on 2016/8/24.
 */
public class FindAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, FindBean> {

    public FindAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.adapter_find_item_layout, parent, false);
        return new FindViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    private ImageLoader imageLoader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path)
                    .placeholder(R.mipmap.default_head)
                    .error(R.mipmap.default_head)
                    .into(imageView);
        }
    };



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FindViewHolder viewHolder = (FindViewHolder) holder;
        ImageGridAdapter adapter = (ImageGridAdapter) viewHolder.imageGrid.getAdapter();
        adapter.clear();
        final int layoutPosition = holder.getLayoutPosition();
        if (layoutPosition >= 0 && layoutPosition < size()) {
            FindBean findBean = get(layoutPosition);
            viewHolder.expandableTextView.setText(findBean.getContent());
            imageLoader.displayImage(getContext(), findBean.getImageUrl(), viewHolder.userHead);
            viewHolder.username.setText(findBean.getUsername());
            viewHolder.createTime.setText(TimeUtil.formatDate(findBean.getCreateTime()));
            List<String> imagePaths = findBean.getImagePaths();
            if (imagePaths != null && !imagePaths.isEmpty()) {
                adapter.addAll(imagePaths);
            }
        }
    }

    public class FindViewHolder extends RecyclerView.ViewHolder {
        public ImageView userHead;
        public TextView username;
        public TextView content;
        public TextView fullText;
        public RecyclerView imageGrid;
        public TextView createTime;
        public ExpandableTextView expandableTextView;

        public FindViewHolder(View itemView) {
            super(itemView);
            this.userHead = (ImageView) itemView.findViewById(R.id.find_item_user_head);
            this.username = (TextView) itemView.findViewById(R.id.find_item_user_name);
            this.content = (TextView) itemView.findViewById(R.id.expandable_text);
            this.fullText = (TextView) itemView.findViewById(R.id.expand_collapse);
            this.imageGrid = (RecyclerView) itemView.findViewById(R.id.find_item_image_grid);
            this.createTime = (TextView) itemView.findViewById(R.id.find_item_create_time);

            this.expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.ExpandableTextView);

            this.imageGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
            this.imageGrid.addItemDecoration(new GridSpanItemDecoration(10, 10));

            this.imageGrid.setAdapter(new ImageGridAdapter(getContext()));
        }

    }
}
