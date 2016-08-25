package com.lyb.wechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lyb.wechat.R;

/**
 * Created by 18348 on 2016/8/25.
 */
public class ImageGridAdapter extends BaseRecyclerAdapter<ImageGridAdapter.ImageViewHolder, String> {
    private int maxCount = 9;

    public ImageGridAdapter(Context context) {
        super(context);
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
//        return Math.min(maxCount, super.getItemCount());
        return 8;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(new FrameLayout(getContext()) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
                int measureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
                super.onMeasure(measureSpec, measureSpec);
            }
        });
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Glide.with(getContext()).load(R.mipmap.default_head)
                .placeholder(R.mipmap.default_head)
                .error(R.mipmap.default_head)
                .into(holder.imageView);
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View overlayView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            FrameLayout frameLayout = (FrameLayout) itemView;

            imageView = new ImageView(itemView.getContext());

            overlayView = new View(itemView.getContext());
            overlayView.setBackgroundResource(R.drawable.find_item_image_overlay);
            overlayView.setClickable(true);

            frameLayout.addView(imageView);
            frameLayout.addView(overlayView);
        }
    }
}
