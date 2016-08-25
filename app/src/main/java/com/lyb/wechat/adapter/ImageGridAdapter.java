package com.lyb.wechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lyb.wechat.R;
import com.lyb.wechat.ui.widget.view.SquareFrameLayout;
import com.yuyh.library.imgsel.ImageLoader;

/**
 * Created by 18348 on 2016/8/25.
 */
public class ImageGridAdapter extends BaseRecyclerAdapter<RecyclerView.ViewHolder, String> {
    private int maxCount = 9;
    private boolean isShowMode = true;
    private static final int TYPE_SHOW = 0;
    private static final int TYPE_ADD = 1;

    public ImageGridAdapter(Context context) {
        super(context);
    }

    public ImageGridAdapter(Context context, boolean isShowMode) {
        super(context);
        this.isShowMode = isShowMode;
    }

    public boolean isShowMode() {
        return isShowMode;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        notifyDataSetChanged();
    }

    public int getMaxCount() {
        return maxCount;
    }

    @Override
    public int getItemCount() {
        return Math.min(maxCount, super.getItemCount() + (isShowMode ? 0 : 1));
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = getItemCount();
        return isShowMode ? TYPE_SHOW :
                (size() >= maxCount ? TYPE_SHOW :
                        ((itemCount <= maxCount && position == (itemCount - 1)) ||
                                (size() == 0 && position == 0) ? TYPE_ADD : TYPE_SHOW));
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageViewHolder holder = new ImageViewHolder(new SquareFrameLayout(getContext()));
        switch (viewType) {
            case TYPE_SHOW:
                return holder;
            case TYPE_ADD:
                holder.imageView.setImageResource(R.mipmap.add);
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                holder.overlayView.setClickable(false);
                holder.overlayView.setVisibility(View.GONE);
                return holder;
        }
        return null;
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
        final int layoutPosition = holder.getLayoutPosition();
        if (holder.getItemViewType() == TYPE_SHOW) {
            if (layoutPosition >= 0 && layoutPosition < size()) {
                imageLoader.displayImage(getContext(), get(layoutPosition), ((ImageViewHolder) holder).imageView);
            }
        } else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onClick(v);
                    }
                }
            });
        }
    }

    @Override
    public boolean add(String object) {
        boolean add = mList.add(object);
        if (add) {
            notifyDataSetChanged();
        }
        return add;
    }

    private View.OnClickListener onClickListener;

    public void setOnAddClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View overlayView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            FrameLayout frameLayout = (FrameLayout) itemView;

            imageView = new ImageView(itemView.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            overlayView = new View(itemView.getContext());
            overlayView.setBackgroundResource(R.drawable.find_item_image_overlay);
            overlayView.setClickable(true);

            frameLayout.addView(imageView);
            frameLayout.addView(overlayView);
        }
    }
}
