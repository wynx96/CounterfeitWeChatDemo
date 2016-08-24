package com.lyb.wechat.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lyb.wechat.R;
import com.lyb.wechat.ui.activity.TitlePagerAdapter;
import com.lyb.wechat.ui.fragment.FindFragment;
import com.lyb.wechat.ui.fragment.InfoFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends TitlePagerAdapter {

    private Context context;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? InfoFragment.newInstance() : FindFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Table getPageTable(int position) {
        switch (position) {
            case 0:
                return new Table(context.getString(R.string.f_info), context.getString(R.string.info));
            case 1:
                return new Table(context.getString(R.string.f_find), context.getString(R.string.find));
        }
        return null;
    }
}
