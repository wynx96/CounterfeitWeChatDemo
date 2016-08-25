package com.lyb.wechat.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyb.wechat.R;
import com.lyb.wechat.bean.FindBean;
import com.lyb.wechat.ui.widget.view.ExpandableTextView;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FindViewHolder) holder).expandableTextView.setText(getContext().getString(R.string.default_content));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 10;
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
            this.imageGrid.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(0, 0, 10, 10);
                }
            });

            this.imageGrid.setAdapter(new ImageGridAdapter(getContext()));
        }

    }
}
