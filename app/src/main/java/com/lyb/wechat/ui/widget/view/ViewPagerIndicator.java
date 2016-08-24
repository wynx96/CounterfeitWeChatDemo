package com.lyb.wechat.ui.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyb.wechat.R;
import com.lyb.wechat.ui.activity.TitlePagerAdapter;
import com.lyb.wechat.util.ArgbUtils;
import com.lyb.wechat.util.ResUtils;


/**
 * Created by 18348 on 2016/8/18.
 */
public class ViewPagerIndicator extends LinearLayout {

    private static final String TAG = "ViewPagerIndicator";

    private int checkedColor;
    private int uncheckedColor;
    private int checkedWidth;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private ArgbUtils argbUtils;

    private LayoutInflater layoutInflater;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.ViewPagerIndicator);

        checkedColor = typedArray.getColor(R.styleable.ViewPagerIndicator_checked_color, getResources().getColor(R.color.tab_bg_color_pressed));
        uncheckedColor = typedArray.getColor(R.styleable.ViewPagerIndicator_unchecked_color, getResources().getColor(R.color.tab_bg_color_normal));
        checkedWidth = typedArray.getDimensionPixelSize(R.styleable.ViewPagerIndicator_checked_width, getResources().getDimensionPixelSize(R.dimen.checked_width));

        typedArray.recycle();

        init();

    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        argbUtils = ArgbUtils.getInstance(checkedColor, uncheckedColor);
        layoutInflater = LayoutInflater.from(getContext());
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setupViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.pagerAdapter = this.viewPager.getAdapter();
        viewPager.addOnPageChangeListener(onPageChangeListener);
        addTabList();
    }

    private int getDotWidth(float positionOffset) {
        return (int) Math.max(ResUtils.dp2Pxf(getContext(), 6), checkedWidth * (1 - positionOffset));
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            View view = getChildAt(position);
            setViewParams(view, argbUtils.evaluate(positionOffset));
            int childCount = getChildCount();
            if (position + 1 > 0 && position + 1 < childCount) {
                view = getChildAt(position + 1);
                setViewParams(view, argbUtils.evaluate(1f - positionOffset));
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private static final void setViewParams(View view, int color) {
        ViewHolder holder = ViewHolder.getViewHolder(view);
        holder.icon.setTextColor(color);
        holder.name.setTextColor(color);
    }


    private void addTabList() {
        int count = pagerAdapter.getCount();

        boolean isTablePageAdapter = pagerAdapter instanceof TitlePagerAdapter;

        for (int i = 0; i < count; i++) {

            View view = layoutInflater.inflate(R.layout.view_pager_tab_layout, null);
            ViewHolder holder = ViewHolder.getViewHolder(view);

            if (!isTablePageAdapter) {
                holder.icon.setVisibility(View.GONE);
                holder.name.setText(pagerAdapter.getPageTitle(i));
            } else {
                TitlePagerAdapter.Table table = ((TitlePagerAdapter) pagerAdapter).getPageTable(i);
                holder.icon.setText(table.getIcon());
                holder.name.setText(table.getName());
            }

            LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            this.addView(view, params);

            final int position = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(position);
                }
            });
        }

        if (count > 0) {
            onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
        }
    }

    public void unset() {
        if (this.viewPager != null) {
            this.viewPager.removeOnPageChangeListener(onPageChangeListener);
        }
    }

    private static class ViewHolder {
        public View rootView;
        public TextView icon;
        public TextView name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.icon = (TextView) rootView.findViewById(R.id.tab_icon);
            this.name = (TextView) rootView.findViewById(R.id.tab_name);
            rootView.setTag(this);
        }

        public static ViewHolder getViewHolder(View view) {
            Object tag = view.getTag();
            if (tag != null && tag instanceof ViewHolder) {
                return ((ViewHolder) tag);
            }
            return new ViewHolder(view);
        }
    }

}
