package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Dto.CommentDto;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.CommentModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: CommentModelImpl
 * @date 2022-01-30 14:53
 */
public class CommentModelImpl extends SpbModelAbstrate implements CommentModel {
    @Override
    public void addComment(CommentDto comment, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", comment.getPb_one_id())
                .add("comment_art", comment.getComment_art())
                .add("comment_user", comment.getComment_user())
                .add("comment_touser", comment.getComment_touser())
                .add("cache_account", comment.getCache_account())
                .build();
        sendHttp(InValues.send(R.string.addComment), requestBody, callBack);
    }

    @Override
    public void queryCommentList(String pbId, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", pbId)
                .build();
        sendHttp(InValues.send(R.string.queryCommentList), requestBody, callBack);
    }

    @Override
    public void queryCommentOne(String pbId, String commentId, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", pbId)
                .add("comment_id", commentId)
                .build();
        sendHttp(InValues.send(R.string.queryCommentOne), requestBody, callBack);
    }

    @Override
    public void deleteComment(String pbId, String commentId, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("comment_id", commentId)
                .add("pb_one_id", pbId)
                .build();
        sendHttp(InValues.send(R.string.deleteComment), requestBody, callBack);
    }
}
