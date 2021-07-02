package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.inter.IPostBarDetailPageAPresenter;
import com.example.spb.view.inter.IPostBarDetailPageAView;

public class PostBarDetailPageAPresenterImpl extends BasePresenter<IPostBarDetailPageAView> implements IPostBarDetailPageAPresenter {

    private boolean userFollowKey = false;
    private String commentText = "";

    public PostBarDetailPageAPresenterImpl() {

    }

    public boolean isUserFollowKey() {
        return userFollowKey;
    }

    public void setUserFollowKey(boolean userFollowKey) {
        this.userFollowKey = userFollowKey;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
