package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.inter.ISetUpMessagePageAPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.view.inter.ISetUpMessagePageAView;

public class SetUpMessagePageAPresenterImpl extends BasePresenter<ISetUpMessagePageAView> implements ISetUpMessagePageAPresenter {

    private boolean notifyComment = true;
    private boolean notifyFollow = true;
    private boolean notifyLike = true;
    private boolean notifySystem = true;
    private boolean notifyCollect = true;
    private boolean notifyAll = true;
    private final SharedPreferences.Editor editor;

    public boolean isNotifyComment() {
        return notifyComment;
    }

    public void setNotifyComment(boolean notifyComment) {
        this.notifyComment = notifyComment;
        editor.putBoolean(InValues.send(R.string.notify_comment), notifyComment);
        editor.apply();
    }

    public boolean isNotifyFollow() {
        return notifyFollow;
    }

    public void setNotifyFollow(boolean notifyFollow) {
        this.notifyFollow = notifyFollow;
        editor.putBoolean(InValues.send(R.string.notify_follow), notifyFollow);
        editor.apply();
    }

    public boolean isNotifyLike() {
        return notifyLike;
    }

    public void setNotifyLike(boolean notifyLike) {
        this.notifyLike = notifyLike;
        editor.putBoolean(InValues.send(R.string.notify_like), notifyLike);
        editor.apply();
    }

    public boolean isNotifySystem() {
        return notifySystem;
    }

    public void setNotifySystem(boolean notifySystem) {
        this.notifySystem = notifySystem;
        editor.putBoolean(InValues.send(R.string.notify_system), notifySystem);
        editor.apply();
    }

    public boolean isNotifyCollect() {
        return notifyCollect;
    }

    public void setNotifyCollect(boolean notifyCollect) {
        this.notifyCollect = notifyCollect;
        editor.putBoolean(InValues.send(R.string.notify_collect), notifyCollect);
        editor.apply();
    }

    public boolean isNotifyAll() {
        return notifyAll;
    }

    public void setNotifyAll(boolean notifyAll) {
        this.notifyAll = notifyAll;
        editor.putBoolean(InValues.send(R.string.notify_all), notifyAll);
        editor.apply();
    }

    public SetUpMessagePageAPresenterImpl() {
        editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_notify_setup));
        getMessageNotify();
    }

    public void getMessageNotify() {
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_notify_setup));
        notifyCollect = sharedPreferences.getBoolean(InValues.send(R.string.notify_collect), true);
        notifyComment = sharedPreferences.getBoolean(InValues.send(R.string.notify_comment), true);
        notifyFollow = sharedPreferences.getBoolean(InValues.send(R.string.notify_follow), true);
        notifyLike = sharedPreferences.getBoolean(InValues.send(R.string.notify_like), true);
        notifySystem = sharedPreferences.getBoolean(InValues.send(R.string.notify_system), true);
        notifyAll = sharedPreferences.getBoolean(InValues.send(R.string.notify_all), true);
    }
}
