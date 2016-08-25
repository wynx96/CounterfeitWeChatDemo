package com.lyb.wechat.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by 18348 on 2016/8/25.
 */
public class BaseActivity extends AppCompatActivity {
    private CompositeSubscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public CompositeSubscription getSubscription() {
        if (subscription == null) {
            subscription = Subscriptions.from();
        }
        return subscription;
    }

    public void addSubscription(Subscription subscription) {
        getSubscription().add(subscription);
    }

    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
