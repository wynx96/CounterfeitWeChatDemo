package com.lyb.wechat.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lyb.wechat.R;
import com.lyb.wechat.adapter.diff.DiffCallback;
import com.lyb.wechat.bean.FindBean;
import com.lyb.wechat.ui.widget.divider.GridSpanItemDecoration;
import com.lyb.wechat.ui.widget.view.ExpandableTextView;
import com.lyb.wechat.util.TimeUtil;
import com.yuyh.library.imgsel.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18348 on 2016/8/24.
 */
public class FindAdapter extends BaseRecyclerAdapter<FindAdapter.FindViewHolder, FindBean> {

    private static final int TYPE_GRID = 0;
    private static final int TYPE_SINGLE = 1;

    public FindAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        List<String> imagePaths = get(position).getImagePaths();
        return (imagePaths == null || imagePaths.isEmpty()) ? TYPE_GRID : (imagePaths.size() == 1 ? TYPE_SINGLE : TYPE_GRID);
    }

    @Override
    public FindAdapter.FindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_GRID:
                view = getLayoutInflater().inflate(R.layout.adapter_find_item_grid_layout, parent, false);
                return new GridViewHolder(view);
            case TYPE_SINGLE:
                view = getLayoutInflater().inflate(R.layout.adapter_find_item_single_layout, parent, false);
                return new SingleViewHolder(view);
            default:
                view = null;
        }
        return new GridViewHolder(view);
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
    public void onBindViewHolder(FindAdapter.FindViewHolder holder, int position) {

        final int layoutPosition = holder.getLayoutPosition();

        FindBean findBean = get(layoutPosition);
        holder.expandableTextView.setText(findBean.getContent());
        holder.username.setText(findBean.getUsername());
        holder.createTime.setText(TimeUtil.formatDate(findBean.getCreateTime()));

        imageLoader.displayImage(getContext(), findBean.getImageUrl(), holder.userHead);

        switch (holder.getItemViewType()) {
            case TYPE_GRID: {
                GridViewHolder gridViewHolder = (GridViewHolder) holder;
                ImageGridAdapter adapter = (ImageGridAdapter) gridViewHolder.imageGrid.getAdapter();
                if (layoutPosition < 0 && layoutPosition >= size()) {
                    return;
                }
                List<String> imagePaths = findBean.getImagePaths();
                if (imagePaths != null /*&& !imagePaths.isEmpty()*/) {
                    ArrayList<String> oldList = new ArrayList<>(adapter.getList());
                    adapter.getList().clear();
                    adapter.getList().addAll(imagePaths);
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback<String>(oldList, imagePaths) {
                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            return TextUtils.equals(oldList.get(oldItemPosition), newList.get(newItemPosition));
                        }
                    });
                    diffResult.dispatchUpdatesTo(gridViewHolder.imageGrid.getAdapter());
                }
                break;
            }
            case TYPE_SINGLE:
                imageLoader.displayImage(getContext(), findBean.getImagePaths().get(0), ((SingleViewHolder) holder).imageSingle);
                break;
            default:
        }


    }

    public class FindViewHolder extends RecyclerView.ViewHolder {
        public ImageView userHead;
        public TextView username;
        public TextView content;
        public TextView fullText;
        public TextView createTime;
        public ExpandableTextView expandableTextView;

        public FindViewHolder(View itemView) {
            super(itemView);
            this.userHead = (ImageView) itemView.findViewById(R.id.find_item_user_head);
            this.username = (TextView) itemView.findViewById(R.id.find_item_user_name);
            this.content = (TextView) itemView.findViewById(R.id.expandable_text);
            this.fullText = (TextView) itemView.findViewById(R.id.expand_collapse);
            this.createTime = (TextView) itemView.findViewById(R.id.find_item_create_time);
            this.expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.ExpandableTextView);
        }
    }

    public class GridViewHolder extends FindViewHolder {

        public RecyclerView imageGrid;

        public GridViewHolder(View itemView) {
            super(itemView);
            this.imageGrid = (RecyclerView) itemView.findViewById(R.id.find_item_image_single);

            this.imageGrid.setLayoutManager(new GridLayoutManager(getContext(), 3));
            this.imageGrid.addItemDecoration(new GridSpanItemDecoration(10, 10));
            this.imageGrid.setAdapter(new ImageGridAdapter(getContext()));
        }

    }

    public class SingleViewHolder extends FindViewHolder {
        public ImageView imageSingle;

        public SingleViewHolder(View itemView) {
            super(itemView);
            this.imageSingle = (ImageView) itemView.findViewById(R.id.find_item_image_single);
        }

    }
}
