package com.lyb.wechat.ui.fragment;

import android.support.v4.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by 18348 on 2016/8/25.
 */
public class BaseFragment extends Fragment {
    private CompositeSubscription subscription;

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
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
