package com.lyb.wechat.ui.activity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by 18348 on 2016/8/24.
 */
public abstract class TitlePagerAdapter extends FragmentPagerAdapter {
    public TitlePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getPageTable(position).getName();
    }

    public abstract Table getPageTable(int position);

    public static class Table implements Parcelable {
        private String icon;
        private String name;

        public Table(String icon, String name) {
            this.icon = icon;
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Table{" +
                    "icon='" + icon + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.icon);
            dest.writeString(this.name);
        }

        protected Table(Parcel in) {
            this.icon = in.readString();
            this.name = in.readString();
        }

        public static final Parcelable.Creator<Table> CREATOR = new Parcelable.Creator<Table>() {
            @Override
            public Table createFromParcel(Parcel source) {
                return new Table(source);
            }

            @Override
            public Table[] newArray(int size) {
                return new Table[size];
            }
        };
    }
}
