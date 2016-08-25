package com.lyb.wechat.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 18348 on 2016/8/24.
 */
public class FindBean implements Parcelable {
    private String username;
    private String imageUrl;
    private String content;
    private List<String> imagePaths;
    private long createTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "FindBean{" +
                "username='" + username + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", content='" + content + '\'' +
                ", imagePaths=" + imagePaths +
                ", createTime=" + createTime +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.imageUrl);
        dest.writeString(this.content);
        dest.writeStringList(this.imagePaths);
        dest.writeLong(this.createTime);
    }

    public FindBean() {
    }

    protected FindBean(Parcel in) {
        this.username = in.readString();
        this.imageUrl = in.readString();
        this.content = in.readString();
        this.imagePaths = in.createStringArrayList();
        this.createTime = in.readLong();
    }

    public static final Parcelable.Creator<FindBean> CREATOR = new Parcelable.Creator<FindBean>() {
        @Override
        public FindBean createFromParcel(Parcel source) {
            return new FindBean(source);
        }

        @Override
        public FindBean[] newArray(int size) {
            return new FindBean[size];
        }
    };


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FindBean findBean = (FindBean) o;

        if (createTime != findBean.createTime) return false;
        if (username != null ? !username.equals(findBean.username) : findBean.username != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(findBean.imageUrl) : findBean.imageUrl != null)
            return false;
        if (content != null ? !content.equals(findBean.content) : findBean.content != null)
            return false;
        return imagePaths != null ? imagePaths.equals(findBean.imagePaths) : findBean.imagePaths == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (imagePaths != null ? imagePaths.hashCode() : 0);
        result = 31 * result + (int) (createTime ^ (createTime >>> 32));
        return result;
    }
}
